package com.dataee.tutorserver.tutoradminserver.filemanage.service.impl;

import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.commonservice.ISavePDFService;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.ParentContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.bean.TeacherContractRequestBean;
import com.dataee.tutorserver.tutoradminserver.filemanage.dao.ContractMapper;
import com.dataee.tutorserver.tutoradminserver.filemanage.service.ISaveContractManageService;
import com.dataee.tutorserver.tutorpatriarchserver.dao.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JinYue
 * @CreateDate 2019/6/29 11:40
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SaveContractManageServiceImpl implements ISaveContractManageService {

    private Logger logger = LoggerFactory.getLogger(SaveContractManageServiceImpl.class);

    @Autowired
    @Qualifier("saveContractService")
    private ISavePDFService<ContractRequestBean> savePDFService;
    @Autowired
    private ContractMapper contractMapper;
    @Autowired
    private StudentMapper studentMapper;
    private final String PARENT = "parent";
    private final String TEACHER = "teacher";

    @Async
    @Override
    public void saveParentContract(ParentContractRequestBean parentContract) {
        parentContract.setRole(PARENT);
        Integer contractId = null;
        try {
            //查重删除已存在的合同
        //    contractMapper.deleteContractByStudentNumberOrStudentId(parentContract.getIdCard(), parentContract.getStudentId());
            contractId = savePDFService.saveFile(parentContract);
        } catch (BaseServiceException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (IllegalParameterException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
//        //是否需要查重
//        contractMapper.deleteStudentNumberByNumber(parentContract.getIdCard());
//        contractMapper.setContractNumber(contractId, parentContract.getIdCard());
//        studentMapper.updateStudentNumber(parentContract.getStudentId(), parentContract.getIdCard());

    }

    @Async
    @Override
    public void saveTeacherContract(TeacherContractRequestBean teacherContract) {
        teacherContract.setRole(TEACHER);
        try {
            savePDFService.saveFile(teacherContract);
        } catch (BaseServiceException e) {
            e.printStackTrace();
        } catch (IllegalParameterException e) {
            e.printStackTrace();
        }
    }
}
