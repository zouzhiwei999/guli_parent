package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.vo.SubjectData;
import com.atguigu.edu.listener.ExcelListener;
import com.atguigu.edu.mapper.SubjectMapper;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-19
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void readExcel(MultipartFile file, SubjectService service) {
        InputStream inputStream = null;
        try {
            //获取文件输入字节流
            inputStream = file.getInputStream();

            //调用方法来读取(具体实现在ExcelListener里面)
            EasyExcel.read(inputStream, SubjectData.class,new ExcelListener(service)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    //关闭字节流
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
