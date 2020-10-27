package com.dataee.tutorserver.tutoradminserver.teachermanage.dao;

import com.dataee.tutorserver.tutoradminserver.teachermanage.bean.TeacherTestExcelBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/6/16 12:14
 */
@Repository
@Mapper
public interface TeacherTestMapper {

    /**
     * 导入教师测试题
     *
     * @param teacherTestExcelList
     * @return
     */
    @Insert({"<script>",
            "insert into question(grade, ctype, content, answer, chapter, edition, term, optionFour, optionThree, optionTwo, optionOne, qtype, title) ",
            "values ",
            "<foreach collection='list' item = 'que' index = 'index' separator = ','> ",
            "(#{que.grade},#{que.cType},#{que.content},#{que.answer},#{que.chapter},#{que.edition},#{que.term},#{que.optionFour},#{que.optionThree},#{que.optionTwo},#{que.optionOne},#{que.qType},#{que.title}) ",
            "</foreach> ",
            "</script> "})
    int insertTeacherTest(@Param("list") List<TeacherTestExcelBean> teacherTestExcelList);
}

