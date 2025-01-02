package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.yupi.springbootinit.api.Moonshot;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.constant.CommonConstant;
import com.yupi.springbootinit.exception.BusinessException;
import com.yupi.springbootinit.model.entity.Chart;
import com.yupi.springbootinit.service.ChartService;
import com.yupi.springbootinit.utils.ExcelUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class BiMessageConsumer {
    @Resource
    private ChartService chartService;

    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {BiMqConstant.BI_QUEUE_NAME}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}",message);
        if(StringUtils.isBlank(message)){
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "消息为空");
        }
        long chartId = Long.parseLong(message);
        Chart chart = chartService.getById(chartId);
        if(chart == null){
            channel.basicNack(deliveryTag,false,false);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"图表为空");
        }
        //先修改图表任务状态为 “执行中”。等执行成功后，修改为 “已完成”、保存执行结果；执行失败后，状态修改为 “失败”，记录任务失败信息。
        Chart updateChart = new Chart();
        updateChart.setId(chart.getId());
        updateChart.setStatus("running");
        boolean save = chartService.updateById(updateChart);
        // 如果提交失败(一般情况下,更新失败可能意味着你的数据库出问题了)
        if (!save){
            channel.basicNack(deliveryTag,false,false);
            handleChartUpdateError(chart.getId(), "更新图表状态为 running 失败");
            return;
        }

        //调用 AI
        String content = Moonshot.doChat(CommonConstant.apiKey, buildUserInput(chart));

        //分割 AI 返回结果
        String[] split = content.split("【【【【【");
        if (split.length < 2) {
            channel.basicNack(deliveryTag,false,false);
            handleChartUpdateError(chart.getId(), "AI 生成错误");
            return;
        }
        String genChar = split[0].trim();
        String genResult = split[1].trim();

        Chart updateChartResult = new Chart();
        updateChartResult.setId(chart.getId());
        updateChartResult.setGenChart(genChar);
        updateChartResult.setGenResult(genResult);
        updateChartResult.setStatus("succeed");

        boolean updateResult = chartService.updateById(updateChartResult);
        if (!updateResult){
            channel.basicNack(deliveryTag,false,false);
            handleChartUpdateError(chart.getId(), "更新图表状态为 succeed 失败");
        }
        //手动消息确认
        channel.basicAck(deliveryTag, false);
    }

    private String buildUserInput(Chart chart){
        String goal = chart.getGoal();
        String chartType = chart.getChartType();
        String csvData = chart.getChartData();

        //用户输入
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("分析目标:").append("\n");
        String userGoal = goal;
        if (StringUtils.isNotBlank(userGoal)){
            userGoal += ",请使用" + chartType;
        }
        stringBuilder.append(userGoal).append("\n");

        //文件压缩
        stringBuilder.append("原始数据:").append(csvData).append("\n");
        String userInput = stringBuilder.toString();
        return userInput;
    }

    private void handleChartUpdateError(long chartId,String executeMessage){
        Chart updateChartResult = new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setStatus("failed");
        updateChartResult.setExecuteMessage(executeMessage);
        boolean updateResult = chartService.save(updateChartResult);
        if (!updateResult){
            log.error("更新图表状态失败" + chartId + "," + executeMessage);
        }
    }

}