package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息列表类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageInformation {

    /**
     * ID编号
     */
    private Integer id;

    /**
     * 所属用户
     */
    private Integer personId;

    /**
     * 角色
     */
    private Integer personRole;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核时间
     */
    private String time;

    /**
     * 课件所属的id
     */
    private Integer lessonId;

    private Integer isRead;

    /**
     * 状态
     */
    private Integer state;

    public MessageInformation(Integer personId, Integer personRole, String content, String remark) {
        this.personId = personId;
        this.personRole = personRole;
        this.content = content;
        this.remark = remark;
    }

    public MessageInformation(Integer personId, Integer personRole, String content) {
        this.personId = personId;
        this.personRole = personRole;
        this.content = content;
    }

    public MessageInformation(Integer personId, Integer personRole, String content, Integer lessonId) {
        this.personId = personId;
        this.personRole = personRole;
        this.content = content;
        this.lessonId = lessonId;
    }
}