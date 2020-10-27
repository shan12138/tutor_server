package com.dataee.tutorserver.commons.bean;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author JinYue
 * @CreateDate 2019/5/30 23:01
 */
@Data
@AllArgsConstructor
public class ExcelListener extends AnalysisEventListener {
    /**
     * 存储文件每行的数据信息
     */
    private List<Object> datas;

    public ExcelListener() {
        this.datas = new ArrayList<>();
    }

    /**
     * 将excel每一行封装后装入list中
     *
     * @param object
     * @param context
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        try {
            datas.add(object);
        } catch (Exception e) {
            System.out.println("文件信息格式不合法");
            System.out.println(e.getMessage() + ":" + e);
        }
        //    后面还可以写一些业务逻辑，现在为了通用，不做处理
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("---------------------------- " + datas.size());
        //datas.clear();
    }
}
