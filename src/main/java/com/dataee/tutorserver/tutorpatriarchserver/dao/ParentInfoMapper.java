package com.dataee.tutorserver.tutorpatriarchserver.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @author JinYue
 * @CreateDate 2019/6/10 2:17
 */
@Repository
@Mapper
public interface ParentInfoMapper {
    /**
     * 修改教员的头像
     *
     * @param parentId
     * @param path
     * @return
     */
    @Update("update parent set head_picture = #{path} where state != 0 and parent_id = #{parentId}")
    int updateParentHeadportraitByPersonId(@Param("parentId") Integer parentId, @Param("path") String path);

    /**
     * 获取家长头像
     *
     * @param parentId
     * @return
     */
    @Select("select head_picture from parent where state != 0 and parent_id = #{parentId}")
    String queryHeadportrait(@Param("parentId") Integer parentId);
}
