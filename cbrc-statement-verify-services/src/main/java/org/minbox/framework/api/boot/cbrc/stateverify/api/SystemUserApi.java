package org.minbox.framework.api.boot.cbrc.stateverify.api;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.cxytiandi.encrypt.springboot.annotation.Encrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.minbox.framework.api.boot.cbrc.stateverify.api.base.BaseApi;
import org.minbox.framework.api.boot.cbrc.stateverify.api.request.*;
import org.minbox.framework.api.boot.cbrc.stateverify.api.request.*;
import org.minbox.framework.api.boot.cbrc.stateverify.api.response.CurrentUserResponse;
import org.minbox.framework.api.boot.cbrc.stateverify.common.constants.UrlConstants;
import org.minbox.framework.api.boot.cbrc.stateverify.common.enums.Status;
import org.minbox.framework.api.boot.cbrc.stateverify.common.exception.LogicException;
import org.minbox.framework.api.boot.cbrc.stateverify.common.model.ApiResponse;
import org.minbox.framework.api.boot.cbrc.stateverify.converter.SystemUserStruct;
import org.minbox.framework.api.boot.cbrc.stateverify.entity.SystemUser;
import org.minbox.framework.api.boot.cbrc.stateverify.service.SystemUserService;
import org.minbox.framework.mybatis.pageable.Page;
import org.minbox.framework.mybatis.pageable.request.PageableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 系统用户接口定义
 *
 * @author 恒宇少年
 */
@RestController
@RequestMapping(value = UrlConstants.USER)
@Api(tags = "系统用户")
public class SystemUserApi extends BaseApi {
    /**
     * 系统用户业务逻辑
     */
    @Autowired
    private SystemUserService systemUserService;

    /**
     * 分页查询系统用户列表
     *
     * @return {@link ApiResponse}
     * @throws LogicException
     */
    @GetMapping(value = UrlConstants.URL_FILTERS + "/pageable")
    @ApiOperation(value = "条件分页查询用户列表")
    public ApiResponse<Page<SystemUser>> findByPageable(@Valid SelectSystemUserByParamRequest request) throws LogicException {
        Page<SystemUser> page =
                PageableRequest.of(request.getPage(), request.getSize()).request(() -> systemUserService.findByParams(request));
        return ApiResponse.success().data(page);
    }

    /**
     * 分页查询系统用户列表2,使用db2数据库
     *
     * @return {@link ApiResponse}
     * @throws LogicException
     */
    @GetMapping(value = UrlConstants.URL_FILTERS + "/pageable2")
    @ApiOperation(value = "条件分页查询用户列表2")
    @DS("db2")
    public ApiResponse<Page<SystemUser>> findByPageabledb2(@Valid SelectSystemUserByParamRequest request) throws LogicException {
        Page<SystemUser> page =
                PageableRequest.of(request.getPage(), request.getSize()).request(() -> systemUserService.findByParams(request));
        return ApiResponse.success().data(page);
    }
    /**
     * 启用系统用户
     * 根据用户编号{@link SystemUser#getUserId()}支持一次性启用多个
     *
     * @return {@link ApiResponse}
     * @throws LogicException logic exception
     */
    @PostMapping(value = UrlConstants.URL_ACTIONS + "/enable")
    public ApiResponse enableSystemUser(@Valid @RequestBody EnableSystemUserRequest request) throws LogicException {
        systemUserService.updateUserStatus(request.getUserIds(), Status.ENABLE);
        return ApiResponse.success();
    }

    /**
     * 禁用系统用户
     * 根据用户编号{@link SystemUser#getUserId()}支持一次性禁用多个
     *
     * @return {@link ApiResponse}
     * @throws LogicException logic exception
     */
    @PostMapping(value = UrlConstants.URL_ACTIONS + "/disable")
    public ApiResponse disableSystemUser(@Valid @RequestBody DisableSystemUserRequest request) throws LogicException {
        systemUserService.updateUserStatus(request.getUserIds(), Status.DISABLE);
        return ApiResponse.success();
    }

    /**
     * 逻辑删除系统用户
     * 根据用户编号{@link SystemUser#getUserId()}支持一次性删除多个
     *
     * @return {@link ApiResponse}
     * @throws LogicException logic exception
     */
    @DeleteMapping
    public ApiResponse removeSystemUser(@Valid @RequestBody RemoveSystemUserRequest request) throws LogicException {
        systemUserService.updateUserStatus(request.getUserIds(), Status.DELETE);
        return ApiResponse.success();
    }

    /**
     * 查询当前登录用户基本信息
     *
     * @return {@link SystemUser}
     * @throws LogicException
     */
    @GetMapping
    @ApiOperation(value = "获取当前用户信息")
    public ApiResponse<CurrentUserResponse> getCurrentUserInfo() throws LogicException {
        String username = getCurrentUserName();
        SystemUser systemUser = systemUserService.findByUsernameAndCheck(username);
        CurrentUserResponse response = SystemUserStruct.INSTANCE.fromSystemUser(systemUser);
        // TODO 用户角色暂未从数据库查询
        globalLogging.debug("获取当前登录用户：{}，获取基本信息完成.", username);
        return ApiResponse.success().data(response);
    }

    /**
     * 添加系统用户
     *
     * @param request {@link AddSystemUserRequest}
     * @return {@link ApiResponse}
     * @throws LogicException
     */
    @PostMapping
    @ApiOperation(value = "添加用户")
    public ApiResponse<String> addSystemUser(@Valid @RequestBody AddSystemUserRequest request) throws LogicException {
        SystemUser systemUser = SystemUserStruct.INSTANCE.fromAddSystemUserRequest(request);
        String userId = systemUserService.addUser(systemUser);
        globalLogging.debug("用户：{}，添加成功，用户编号为：{}", request.getUsername(), userId);
        return ApiResponse.success().data(userId);
    }

    @GetMapping (value = "/test")
    @ApiOperation(value = "添加用户")
    public ApiResponse<String> test() throws LogicException {
        return ApiResponse.success().data("2");
    }

    @Encrypt
    @GetMapping (value = "/test2")
    @ApiOperation(value = "添加用户")
    public ApiResponse<String> test2() throws LogicException {
        return ApiResponse.success().data("2");
    }
}
