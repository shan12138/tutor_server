package com.dataee.tutorserver.tutoradminserver.filemanage.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 资源列表的返回体
 *
 * @author JinYue
 * @CreateDate 2019/6/16 0:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceListResponseBean {
    private Integer resourceId;
    private String resourceName;
}
