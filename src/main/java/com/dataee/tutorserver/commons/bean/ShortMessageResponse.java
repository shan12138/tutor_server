package com.dataee.tutorserver.commons.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 消息请求的返回体
 *
 * @author JinYue
 * @CreateDate 2019/4/27 22:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortMessageResponse {
    @JsonProperty("Message")
    private String Message;
    @JsonProperty("RequestId")
    private String RequestId;
    @JsonProperty("BizId")
    private String BizId;
    @JsonProperty("Code")
    private String Code;
}
