package com.dataee.tutorserver.commons.commonservice.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.dataee.tutorserver.commons.baseexceptions.BaseServiceException;
import com.dataee.tutorserver.commons.bean.ShortMessageProperties;
import com.dataee.tutorserver.commons.bean.ShortMessageResponse;
import com.dataee.tutorserver.commons.commonservice.IShortMessageService;
import com.dataee.tutorserver.commons.errorInfoenum.ServiceExceptionsEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Random;

/**
 * 短信验证码
 *
 * @author JinYue
 * @CreateDate 2019/4/27 11:28
 */
@Service
public class ShortMessageServiceImpl implements IShortMessageService {
    private final Logger logger = LoggerFactory.getLogger(ShortMessageServiceImpl.class);
    private final int CODE_LENGTH = 6;
    @Autowired
    private ShortMessageProperties properties;

    public void setProperties(ShortMessageProperties properties) {
        this.properties = properties;
    }

    /**
     * 发送验证码短信
     *
     * @param phoneNumber
     */
    @Override
    public String sendMessage(String phoneNumber) throws BaseServiceException {
        DefaultProfile profile = DefaultProfile.getProfile(
                properties.getRegionId(), properties.getAccessKeyId(), properties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain(properties.getDomain());
        request.setSysVersion(properties.getVersion());
        request.setSysAction(properties.getAction());
        request.putQueryParameter("RegionId", properties.getRegionId());
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", properties.getSignName());
        request.putQueryParameter("TemplateCode", properties.getTemplateCode());
        JsonObject param = new JsonObject();
        String verificationCode = this.getVerificationCode();
        param.addProperty("code", verificationCode);
        request.putQueryParameter("TemplateParam", param.toString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());
            ShortMessageResponse responseObj = this.jsonToObject(response.getData());
            if ("OK".equals(responseObj.getCode())) {
                return verificationCode;
            } else {
                throw new BaseServiceException(ServiceExceptionsEnum.MESSAGE_FAIL, responseObj.getMessage());
            }
        } catch (ServerException se) {
            logger.error(se.getMessage(), se);
            throw new BaseServiceException(ServiceExceptionsEnum.MESSAGE_SERVER);
        } catch (ClientException ce) {
            logger.error(ce.getMessage(), ce);
            throw new BaseServiceException(ServiceExceptionsEnum.MESSAGE_CLIENT);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage(), ioe);
            throw new BaseServiceException(ServiceExceptionsEnum.MESSAGE_IOERROR);
        }
    }

    /**
     * 生成指定长度的注册码
     *
     * @return
     */
    private String getVerificationCode() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int len = CODE_LENGTH;
        while (len-- > 0) {
            stringBuilder.append(String.valueOf(random.nextInt(10)));
        }
        return stringBuilder.toString();
    }

    /**
     * 将json数据转换成Object
     *
     * @param jsonString
     * @return
     * @throws IOException
     */
    private ShortMessageResponse jsonToObject(String jsonString) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ShortMessageResponse responseObj = mapper.readValue(jsonString, ShortMessageResponse.class);
        return responseObj;
    }
}
