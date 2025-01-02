package com.yupi.springbootinit.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ChartMapperTest {

    @Resource
    private ChartMapper chartMapper;


    @Test
    void queryChartData() {

        String querySql = "select * from chart";
        List<Map<String, Object>> stringObjectMap = chartMapper.queryChartData(querySql);
        System.out.println(stringObjectMap);
    }
}