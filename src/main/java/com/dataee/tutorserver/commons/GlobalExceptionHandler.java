package com.dataee.tutorserver.commons;

import com.aliyun.oss.ClientException;
import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import com.dataee.tutorserver.commons.exceptions.SQLOperationException;
import com.dataee.tutorserver.utils.FiledErrorsUtil;
import com.dataee.tutorserver.utils.ResultUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常响应控制器
 *
 * @author Jinyue
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private final String COMMON_REASON = "服务器异常，请尽快联系管理员";
    private final String NOT_AUTHC = "没有访问权限";

    /**
     * shiro认证时产生的异常
     *
     * @param uae
     * @return
     */
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity unknownAccountExceptionHandler(AuthenticationException uae) {
        logger.error(uae.getMessage(), uae);
        return ResultUtil.error(HttpStatus.UNPROCESSABLE_ENTITY, ServiceExceptionsEnum.UNKNOWNACCOUNT.toString());
    }

    @ExceptionHandler({AuthorizationException.class})
    public ResponseEntity AuthorizationExceptionHandler(AuthorizationException ae) {
        logger.error(ae.getMessage(), ae);
        return ResultUtil.error(HttpStatus.NOT_IMPLEMENTED, NOT_AUTHC);
    }

    /**
     * 非法参数异常
     *
     * @param ipe
     * @return
     */
    @ExceptionHandler({IllegalParameterException.class})
    public ResponseEntity IllegalParameterExceptionHandler(IllegalParameterException ipe) {
        logger.error(ipe.getMessage(), ipe);
        return ResultUtil.error(HttpStatus.BAD_REQUEST, ipe.getMessage());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException he) {
        logger.error(he.getMessage(), he);
        return ResultUtil.error(HttpStatus.NOT_ACCEPTABLE, he.getMessage());
    }


    /**
     * 控制器异常处理
     *
     * @param bce
     * @return
     */
    @ExceptionHandler({BaseControllerException.class})
    public ResponseEntity controllerExceptionHandler(BaseControllerException bce) {
        logger.error(bce.getMessage(), bce);
        return ResultUtil.error(HttpStatus.UNPROCESSABLE_ENTITY, bce.getMessage());
    }

    /**
     * 业务异常处理
     *
     * @param bse
     * @return
     */
    @ExceptionHandler({BaseServiceException.class})
    public ResponseEntity serviceExceptionJandler(BaseServiceException bse) {
        logger.error(bse.getMessage(), bse);
        return ResultUtil.error(HttpStatus.NOT_ACCEPTABLE, bse.getMessage());
    }

    /**
     * 数据库操作发生错误
     *
     * @param sqloe
     * @return
     */
    @ExceptionHandler({SQLOperationException.class})
    public ResponseEntity SQLOperationExceptionHandler(SQLOperationException sqloe) {
        logger.error(sqloe.getMessage(), sqloe);
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, sqloe.getMessage());
    }

    /**
     * OSS对象存储OSSClient发生异常
     *
     * @param ce
     * @return
     */
    @ExceptionHandler({ClientException.class})
    public ResponseEntity OSSExceptionHandler(ClientException ce) {
        logger.error(ce.getMessage(), ce);
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, ServiceExceptionsEnum.CLIENT_OPERATE_EXCEPTION.toString());
    }

    /**
     * 通用异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity commonExceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultUtil.error(HttpStatus.INTERNAL_SERVER_ERROR, COMMON_REASON);
    }

    /**
     * 捕获所有的校验字段的异常信息
     * 返回错误信息列表的第一个
     *
     * @param validException
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity MethodArgumentNotValidExceptionHandler(Exception validException) {
        String errorInfo = "请求信息异常，请检查后重试";
        BindingResult result = null;
        if (validException instanceof MethodArgumentNotValidException) {
            result = ((MethodArgumentNotValidException) validException).getBindingResult();
            errorInfo = FiledErrorsUtil.getErrorInfo(result);
        } else if (validException instanceof BindException) {
            result = ((BindException) validException).getBindingResult();
            errorInfo = FiledErrorsUtil.getErrorInfo(result);
        }
        return ResultUtil.error(HttpStatus.BAD_REQUEST, errorInfo);
    }

    /**
     * 捕获缺少参数的异常
     *
     * @param missParameterException
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ResponseEntity MissingServletRequestParameterExceptionHandler(MissingServletRequestParameterException missParameterException) {
        logger.error("missParameterException", missParameterException.getMessage());
        return ResultUtil.error(HttpStatus.NOT_FOUND, "请求条件不足，请检查后重试");
    }
}
