package com.atguigu.edu.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author AOA
 * @version 1.0
 * @description: Excel种的字段
 * @date 2023/7/19 22:25
 */
@Data
public class SubjectData {
    @ExcelProperty(index=0)
    private String oneSubjectName;

    @ExcelProperty(index=1)
    private String twoSubjectName;
}
