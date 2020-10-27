package com.dataee.tutorserver.commons.bean;

import lombok.Data;

/**
 * 分页条件
 *
 * @author JinYue
 * @CreateDate 2019/5/23 23:26
 */
@Data
public class  Page {
    /**
     * 当前页的数据量
     */
    private Integer limit;
    /**
     * 当前要访问的页
     */
    private Integer page;

    public Page() {
        this.limit = 10;
        this.page = 1;
    }

    public Page(Integer limit, Integer page) {
        if (limit <= 0 || page <= 0) {
            this.limit = 10;
            this.page = 1;
        } else {
            this.limit = limit;
            this.page = page;
        }
    }
}
