package com.dataee.tutorserver.userserver.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/6/22 11:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBean {
    private String userId;
    private String username;
    private String telephone;
    private Integer state;
}
