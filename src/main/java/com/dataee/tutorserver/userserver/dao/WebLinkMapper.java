package com.dataee.tutorserver.userserver.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author JinYue
 * @CreateDate 2019/6/18 23:57
 */
@Repository
@Mapper
public interface WebLinkMapper {
    /**
     * 获取连接地址
     *
     * @param keyword
     * @return
     */
    @Select("select address from web_link where keyword = #{keyword} and state = 1")
    String queryWebLink(@Param("keyword") String keyword);
}
