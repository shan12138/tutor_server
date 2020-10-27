package com.dataee.tutorserver.tutoradminserver.electricsalemanage.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 电销记录文件映射模型
 *
 * @author JinYue
 * @CreateDate 2019/5/30 22:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleTaskExcelBean extends BaseRowModel {
    @ExcelProperty(index = 0)
    private Integer id;
    @ExcelProperty(index = 1)
    private String name;
    @ExcelProperty(index = 2)
    private String sex;
    @ExcelProperty(index = 3)
    private String telephone;
}
