package org.minbox.framework.api.boot.cbrc.stateverify.entity;

import com.google.errorprone.annotations.NoAllocation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author: xiongfei
 * @date: 2022/09/05 18:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AppInfo {
    private String username;
    private String password;
    private String clientId;
    private String clientSecret;
    private String accessToken;
    private String refreshToken;
}
