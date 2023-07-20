package com.atguigu.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.edu.entity.Subject;
import com.atguigu.edu.entity.vo.SubjectData;
import com.atguigu.edu.service.SubjectService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author AOA
 * @version 1.0
 * @description: 因为输入流传入这里，所以在这里执行存储，需要借用Service服务层来控制,但是该监听器不在spring中管理，所以无法注入，使用构造器
 * @date 2023/7/19 21:40
 */

public class ExcelListener extends AnalysisEventListener<SubjectData> {

    private SubjectService service;
    public ExcelListener () {};
    public ExcelListener (SubjectService service){
        this.service = service;
    };

    //一行一行读取
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "Excel为空");
        }
        //根据闯入分类来确定是否在数据库中已经有,创建方法来判断

        //如果该分类在数据库中没有
        Subject subjectOneResult = this.existsOneSubject(service, subjectData.getOneSubjectName());
        if (subjectOneResult == null){
            subjectOneResult = new Subject();
            subjectOneResult.setParentId("0");
            subjectOneResult.setTitle(subjectData.getOneSubjectName());
            service.save(subjectOneResult);
        }

        //一级的id
        String pid = subjectOneResult.getId();

        Subject subjectTwoResult = this.existsTwoSubject(service, subjectData.getTwoSubjectName(), pid);
        if (subjectTwoResult == null){
            subjectTwoResult = new Subject();
            subjectTwoResult.setParentId(pid);
            subjectTwoResult.setTitle(subjectData.getTwoSubjectName());
            service.save(subjectTwoResult);
        }
    }

    //判断一级分类是否存在 id=0,title=传入的name,返回结果
    private Subject existsOneSubject(SubjectService service, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        wrapper.eq("title", name);
        Subject one = service.getOne(wrapper);
        return one;
    }

    //判断二级分类是否存在 id=指定的id,title=传入的name,返回结果
    private Subject existsTwoSubject(SubjectService service, String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", pid);
        wrapper.eq("title", name);
        Subject two = service.getOne(wrapper);
        return two;
    }

    //之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
