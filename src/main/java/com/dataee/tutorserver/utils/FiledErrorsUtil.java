package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取字段错误
 *
 * @author JinYue
 */
public class FiledErrorsUtil {

    /**
     * 获取控制器中request请求中的字段错误的第一个错误信息
     *
     * @param bindingResult
     * @return
     */
    public static final String getErrorInfo(BindingResult bindingResult) {
        return getErrorList(bindingResult).get(0);
    }

    /**
     * 获取整个错误列表
     *
     * @param bindingResult
     * @return
     */
    public static List<String> getErrorList(BindingResult bindingResult) {
        List<FieldError> errorList = bindingResult.getFieldErrors();
        List<String> errorInfoList = errorList.stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        return errorInfoList;
    }

    /**
     * 检查是否存在字段校验错误
     * 如果有则抛出异常
     * 只抛出第一个错误信息
     *
     * @param bindingResult
     * @throws IllegalParameterException
     */
    public static void getErrorException(BindingResult bindingResult) throws IllegalParameterException {
        if (bindingResult != null && bindingResult.hasErrors()) {
            String reasonPhrase = getErrorInfo(bindingResult);
            throw new IllegalParameterException(reasonPhrase);
        }
    }
}
