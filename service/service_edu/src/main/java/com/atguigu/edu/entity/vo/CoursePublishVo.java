package com.atguigu.edu.entity.vo;

import lombok.Data;

/**
 * @author AOA
 * @version 1.0
 * @description: TODO
 * @date 2023/7/25 14:21
 */
@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private String lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;
}
