package com.dataee.tutorserver.commons.bean;

import com.dataee.tutorserver.commons.baseexceptions.BaseControllerException;
import com.dataee.tutorserver.commons.exceptions.IllegalParameterException;

/**
 * @author JinYue
 * @CreateDate 2019/5/10 0:30
 */
public class RolePolicy {
    private String teacherPolicy;
    private String parentPolicy;
    private String adminPolicy;

    public RolePolicy() {
        String commonUserPolicy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:PutObject\",\n" +
                "                \"oss:PostObject\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:tutor-platform/errorquestions\", \n" +
                "                \"acs:oss:*:*:tutor-platform/errorquestions/*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        this.teacherPolicy = commonUserPolicy;
        this.parentPolicy = commonUserPolicy;
        String administratorPolicy = "{\n" +
                "    \"Version\": \"1\", \n" +
                "    \"Statement\": [\n" +
                "        {\n" +
                "            \"Action\": [\n" +
                "                \"oss:PutObject\",\n" +
                "                \"oss:PostObject\",\n" +
                "                \"oss:DeleteObject\",\n" +
                "                \"oss:DeleteMultipleObjects\"\n" +
                "            ], \n" +
                "            \"Resource\": [\n" +
                "                \"acs:oss:*:*:tutor-platform\", \n" +
                "                \"acs:oss:*:*:tutor-platform/*\" \n" +
                "            ], \n" +
                "            \"Effect\": \"Allow\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        this.adminPolicy = administratorPolicy;
    }

    public String getPolicy(String role) throws BaseControllerException {
        switch (role) {
            case "teacher":
                return teacherPolicy;
            case "parent":
                return parentPolicy;
            case "admin":
                return adminPolicy;
            default:
                throw new IllegalParameterException();
        }
    }
}
