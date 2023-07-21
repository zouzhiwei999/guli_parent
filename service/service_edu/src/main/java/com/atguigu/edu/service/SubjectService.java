package com.atguigu.edu.service;

import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.vo.SubjectOneVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    List<SubjectOneVO> getAllSubject();
}
