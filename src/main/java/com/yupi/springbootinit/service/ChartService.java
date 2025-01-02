package com.yupi.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.yupi.springbootinit.model.dto.chart.ChartQueryRequest;
import com.yupi.springbootinit.model.entity.Chart;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【chart(图表信息表)】的数据库操作Service
* @createDate 2024-09-12 13:13:24
*/
@Service
public interface ChartService extends IService<Chart> {

    Wrapper<Chart> getQueryWrapper(ChartQueryRequest chartQueryRequest);

}
