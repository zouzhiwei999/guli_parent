package com.atguigu.edu.service;

import com.atguigu.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-07-19
 */
public interface SubjectService extends IService<Subject> {

    void readExcel(MultipartFile file ,SubjectService service);
}
