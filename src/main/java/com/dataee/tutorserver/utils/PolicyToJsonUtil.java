package com.dataee.tutorserver.utils;

import com.dataee.tutorserver.entity.Policy;
import com.dataee.tutorserver.entity.PolicyAction;
import com.dataee.tutorserver.entity.PolicyResource;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * 将Policy序列化
 *
 * @author JinYue
 * @CreateDate 2019/5/13 0:20
 */
public class PolicyToJsonUtil {

    public static String serializer(Policy policy) {
        Gson gson = getPolicyCustomGsonBulider();
        return gson.toJson(policy);
    }

    /**
     * 自定义Gson适配Policy
     *
     * @return
     */
    private static Gson getPolicyCustomGsonBulider() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PolicyAction.class, new JsonSerializer<PolicyAction>() {
                    @Override
                    public JsonElement serialize(PolicyAction action, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(action.toJson());
                    }
                })
                .registerTypeAdapter(PolicyResource.class, new JsonSerializer<PolicyResource>() {
                    @Override
                    public JsonElement serialize(PolicyResource resource, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(resource.toJson());
                    }
                })
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson;
    }
}
