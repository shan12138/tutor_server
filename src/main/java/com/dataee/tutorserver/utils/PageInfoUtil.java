package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.commons.bean.NewPageInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 将PageHelper中的数据进行提取然后重新封装
 *
 * @author JinYue
 * @CreateDate 2019/6/9 11:18
 */
public class PageInfoUtil {

    public static NewPageInfo read(PageInfo pageInfo) {
        NewPageInfo pageInfoResponseBeanBean = new NewPageInfo(pageInfo);
        return pageInfoResponseBeanBean;
    }


    public static NewPageInfo read(List list) {
        PageInfo pageInfo = new PageInfo(list);
        NewPageInfo newPageInfo = new NewPageInfo(pageInfo);
        return newPageInfo;
    }
}
