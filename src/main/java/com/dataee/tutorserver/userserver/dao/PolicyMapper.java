package com.dataee.tutorserver.userserver.dao;

import com.dataee.tutorserver.entity.Policy;
import com.dataee.tutorserver.entity.PolicyAction;
import com.dataee.tutorserver.entity.PolicyResource;
import com.dataee.tutorserver.entity.PolicyStatement;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 自定义权限数据操作
 *
 * @author JinYue
 * @CreateDate 2019/5/11 18:19
 */
@Mapper
@Repository
public interface PolicyMapper {

    /**
     * 实现policy查找
     *
     * @param roleId
     * @param key
     * @param service
     * @return
     */
    @Select("select id, policy_statement_id, #{keyword} as keyword from policy where state = 1 and role_id = #{roleId} and " +
            "keyword = #{keyword} and policy_statement_id in (select policy_statement.id from policy_statement where service = #{serviceName} and state =1);")
    @Results(id = "policyMapper", value = {
            @Result(column = "policy_id", property = "id"),
            @Result(column = "{statementId=policy_statement_id, keyword = keyword}", property = "statements",
                    many = @Many(select = "queryStatementById")),
    })
    Policy queryPolicyByRoleAndService(@Param("roleId") Integer roleId, @Param("keyword") String key, @Param("serviceName") String service);

    @Select("select id, effect, policy_action_id as actionId, policy_resource_id, #{keyword} as keyword from policy_statement where state=1 and id = #{statementId}")
    @Results(id = "statementMapper", value = {
            @Result(column = "actionId", property = "actions",
                    many = @Many(select = "queryActionsById")),
            @Result(column = "{resourceId = policy_resource_id, keyword=keyword}", property = "resources",
                    many = @Many(select = "queryResourcesByIdAndKey"))
    })
    List<PolicyStatement> queryStatementById(@Param("statementId") Integer id, @Param("keyword") String keyword);

    @Select("select policy_service_actions.id, service_name, action_name from policy_actions,policy_service_actions where " +
            "policy_actions.state = 1 and policy_actions.id = policy_service_actions.policy_actions_id and " +
            "policy_actions.id = #{actionId} and policy_service_actions.state = 1")
    List<PolicyAction> queryActionsById(@Param("actionId") Integer actionId);


    @Select("select por.id, service_name, region, account_id, relative_id from policy_resource as pr, policy_service_resources as por " +
            "where pr.state = 1 and pr.id = #{resourceId} and por.policy_resource_id = pr.id and por.state = 1 and pr.keyword = #{keyword}")
    List<PolicyResource> queryResourcesByIdAndKey(@Param("resourceId") Integer resourceId, @Param("keyword") String keyword);
}
