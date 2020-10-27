package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/11/1 17:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action {
    private String name;
    private String description;
}
