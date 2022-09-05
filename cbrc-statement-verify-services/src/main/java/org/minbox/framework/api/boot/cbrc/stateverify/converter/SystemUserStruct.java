package org.minbox.framework.api.boot.cbrc.stateverify.converter;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.minbox.framework.api.boot.cbrc.stateverify.api.request.AddSystemUserRequest;
import org.minbox.framework.api.boot.cbrc.stateverify.api.response.CurrentUserResponse;
import org.minbox.framework.api.boot.cbrc.stateverify.entity.SystemUser;

/**
 * 系统用户MapStruct转换接口定义
 *
 * @author 恒宇少年
 */
@Mapper
public interface SystemUserStruct {
    /**
     * get new mapStruct instance
     */
    SystemUserStruct INSTANCE = Mappers.getMapper(SystemUserStruct.class);

    /**
     * 根据系统用户数据实体转换当前登录用户响应实体
     *
     * @param user {@link SystemUser}
     * @return {@link CurrentUserResponse}
     */
    CurrentUserResponse fromSystemUser(SystemUser user);

    /**
     * 根据{@link AddSystemUserRequest}转化系统用户
     *
     * @param request {@link AddSystemUserRequest}
     * @return {@link SystemUser}
     */
    SystemUser fromAddSystemUserRequest(AddSystemUserRequest request);
}
