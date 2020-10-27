package com.dataee.tutorserver.tutorpatriarchserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 注册时的家长信息
 *
 * @Author ChenShanShan
 * @CreateDate 2019/5/10 17:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollParentInfoRequestBean {
    private List<EnrollChildInfoRequestBean> childInfo;
}
