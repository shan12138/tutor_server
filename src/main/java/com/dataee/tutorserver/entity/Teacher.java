package com.dataee.tutorserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author ChenShanShan
 * @CreateDate 2019/4/26 20:51
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {

    /**
     * ID编号
     */
    private Integer teacherId;

    /**
     * 教职员学号
     */
    private String jobNumber;

    /**
     * 姓名
     */
    private String teacherName;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 性别，0为女，1为男
     */
    private Integer sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 紧急联系人
     */
    private String emergencyContact;

    /**
     * 联系人电话
     */
    private String emergencyTelephone;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 银行卡卡号
     */
    private String aliPayAccount;

    /**
     * 开户行
     */
    private String openBankName;

    /**
     * 银行卡图片
     */
    private String aliPayPicture;

    /**
     * 大学
     */
    private String college;

    /**
     * 专业
     */
    private String major;


    /**
     * 授课范围
     */
    private List<String> teachingArea;

    /**
     * 面试结果
     */
    private String interviewResult;

    /**
     * 家教经验
     */
    private String tutorExperience;

    /**
     * 荣誉
     */
    private String honour;

    /**
     * 自我评价
     */
    private String evaluation;

    /**
     * 备注
     */
    private String remark;

    /**
     * 合同
     */
    private List<ContractPdf> contractPdf;

    /**
     * 头像
     */
    private String headPicture;

    /**
     * 学生证图片
     */
    private String studentIdCardPicture;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 被这个老师邀请
     */
    private Teacher teacher;


    /**
     * 目前身份
     */
    private String currentStatus;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 标签
     */
    private TeacherLabel teacherLabel;

   private  List<Product> products;

   private  Integer teacherLevel;

   private  Partner  partner;





}
