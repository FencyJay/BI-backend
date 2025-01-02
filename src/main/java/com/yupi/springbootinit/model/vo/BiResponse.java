package com.yupi.springbootinit.model.vo;

import lombok.Data;

@Data
public class BiResponse {
    private String genChart;

    private String genResult;
    // 新生成的图表 id
    private Long chartId;
}
