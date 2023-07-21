package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.vo.SubjectData;
import com.atguigu.edu.entity.vo.SubjectOneVO;
import com.atguigu.edu.entity.vo.SubjectTwoVO;
import com.atguigu.edu.listener.ExcelListener;
import com.atguigu.edu.mapper.SubjectMapper;
import com.atguigu.edu.service.SubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-19
 */
@Service
@Slf4j
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    //读取excel表格中的一级课程和二级课程到Subject中
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

    //获取返回分级显示的一级课程和二级课程
    @Override
    public List<SubjectOneVO> getAllSubject() {
        //为了减少SQL操作，减少数据库压力，把一级课程和二级课程都拿出来后再操作

        //因为在前端页面中，只显示title以及只需要id来进行排序分类，所以弄两个View Object 视图对象实体类:
        // SubjectOneVO   SubjectTwoVO 分别代表
        //   一级课程      和  二级课程

        //1.获取一级课程,parent_id = 0
        //ServiceImpl中定义了baseMapper,不再需要注入SubjectMapper
        QueryWrapper<Subject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", "0");
        List<Subject> subjectList1 = baseMapper.selectList(wrapper1);

        //2.获取二级课程,parent_id != 0
        QueryWrapper<Subject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", "0");
        List<Subject> subjectList2 = baseMapper.selectList(wrapper2);


        //3.将一级课程放进   List<SubjectOneVO>
        List<SubjectOneVO> oneVOArrayList = new ArrayList<>();

        for (Subject subject : subjectList1) {
            SubjectOneVO subjectOneVO = new SubjectOneVO();
            BeanUtils.copyProperties(subject, subjectOneVO);

            //4.将二级课程放进 一级课程对应的 SubjectOneVO 的 children集合:
            //注意：二级课程的list是这个一级课程独有的，要在循环中声明

            //逻辑是：遍历 subjectList2 中的元素.getParentId = subjectList1中的元素.getId，就存入List<SubjectTwoVO>

            List<SubjectTwoVO> twoVOArrayList = new ArrayList<>();

            for (Subject subject1 : subjectList2) {
                if (subject1.getParentId().equals(subject.getId())) {
                    SubjectTwoVO subjectTwoVO = new SubjectTwoVO();
                    BeanUtils.copyProperties(subject1, subjectTwoVO);
                    twoVOArrayList.add(subjectTwoVO);
                    //5.将  SubjectTwoVO集合  放进  SubjectOneVO中的二级课程children子集合中
                    subjectOneVO.setChildren(twoVOArrayList);
                }
            }
            //3(完成).将这个循环内的一级课程放入 一级课程的List
            oneVOArrayList.add(subjectOneVO);
        }

        //6.返回SubjectOneVO的集合
        return oneVOArrayList;

    }
}
