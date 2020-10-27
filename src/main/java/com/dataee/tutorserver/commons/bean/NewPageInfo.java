package com.dataee.tutorserver.commons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 重定义分页信息
 *
 * @author JinYue
 * @CreateDate 2019/6/8 23:39
 */
@Data
@AllArgsConstructor
public class NewPageInfo<T> {
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 当前页的数量
     */
    private int size;
    /**
     * 数据总数量
     */
    private long total;


    /**
     * 本页数据
     */
    private List<T> list;

    public NewPageInfo() {
        this.pageNum = 1;
        this.pages = 1;
        this.total = 0;
        this.list = new ArrayList<>();
    }

    public NewPageInfo(com.github.pagehelper.PageInfo pageInfo) {
        this.pageNum = pageInfo.getPageNum();
        this.pages = pageInfo.getPages();
        this.total = pageInfo.getTotal();
        this.size = pageInfo.getSize();
        if (pageInfo.getList() == null) {
            this.list = new ArrayList<>();
        } else {
            this.list = pageInfo.getList();
        }
    }

    public List<T> getList() {
        return list;
    }
}
