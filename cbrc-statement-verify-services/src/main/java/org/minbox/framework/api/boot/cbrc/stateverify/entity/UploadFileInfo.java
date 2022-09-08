package org.minbox.framework.api.boot.cbrc.stateverify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 @author: xiongfei
 @date: 2022/09/08 10:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UploadFileInfo implements Serializable {

    /**
     * 业务流水号（与浙里金融传给流水行的流水号相同）
     */
    private long applyId;

    /**
     * 流水行行名
     */
    private String applyBank;
    /**
     * 接收行特征值（国密4加密的机构统一信用代码，已使用接收行key加密）
     */
     private String bankCode;
    /**
     * 客户姓名
     */
     private String userName;
    /**
     * 具体的上传文件（文件命方式：业务流水号.zip）
     */
     private MultipartFile file;
    /**
     * 申请用途备注
     */
     private String applyUseMessage;

}
