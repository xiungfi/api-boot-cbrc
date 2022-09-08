package org.minbox.framework.api.boot.cbrc.stateverify.algorithm;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import com.cxytiandi.encrypt.algorithm.EncryptAlgorithm;
import org.springframework.stereotype.Component;

/**
 @author: xiongfei
 @date: 2022/09/07 15:03
 */
@Component
public class Sm4EncryptAlgorithm implements EncryptAlgorithm {
    /**
     * 加密
     *
     * @param content    加密内容
     * @param encryptKey 加密Key
     * @return 加密内容
     * @throws Exception 加密失败
     */
    @Override
    public String encrypt(String content, String encryptKey) throws Exception {
        SM4 sm4 = SmUtil.sm4(encryptKey.getBytes());
        return sm4.encryptHex(content);
    }

    /**
     * 解密
     *
     * @param encryptStr 解密字符串
     * @param decryptKey 解密Key
     * @return 解密内容
     * @throws Exception 解密失败
     */
    @Override
    public String decrypt(String encryptStr, String decryptKey) throws Exception {
        SM4 sm4 = SmUtil.sm4(decryptKey.getBytes());
        return sm4.decryptStr(encryptStr, CharsetUtil.CHARSET_UTF_8);
    }


}
