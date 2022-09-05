package org.minbox.framework.api.boot.cbrc.stateverify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: xiongfei
 * @date: 2022/09/05 16:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthData {

    private String access_token;

    private String token_type;

    private String refresh_token;

    private Long expires_in;

    private String scope;

    private String jti;

    private String message;

    private Long code;

}
