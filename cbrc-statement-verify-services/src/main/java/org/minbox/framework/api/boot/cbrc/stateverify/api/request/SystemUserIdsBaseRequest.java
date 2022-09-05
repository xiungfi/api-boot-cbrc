package org.minbox.framework.api.boot.cbrc.stateverify.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.minbox.framework.api.boot.cbrc.stateverify.entity.SystemUser;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 系统用户编号集合基础请求对象
 *
 * @author 恒宇少年
 */
@ApiModel
@Data
public class SystemUserIdsBaseRequest {
    /**
     * 用户编号列表
     * {@link SystemUser#getUserId()}
     * 支持一次性禁用多个用户
     */
    @ApiModelProperty("用户编号")
    @NotEmpty
    private List<String> userIds;
}
