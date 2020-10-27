package com.dataee.tutorserver.tutoradminserver.messagemanage.dao;

import com.dataee.tutorserver.entity.MessageInformation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/5/11 19:30
 */
@Mapper
@Repository
public interface MessageManageMapper {
    @Insert("insert into message_information(person_id, person_role, content, remark) values(" +
            "#{personId}, #{personRole}, #{content}, #{remark})")
    int addToMessageTable(MessageInformation messageInformation);

    @Insert("insert into message_information(person_id, person_role, content) values(#{personId}, #{personRole}, #{content})")
    void addToMessage(MessageInformation messageInformation);
}
