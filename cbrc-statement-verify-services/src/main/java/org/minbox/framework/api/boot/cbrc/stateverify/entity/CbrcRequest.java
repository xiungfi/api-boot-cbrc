package org.minbox.framework.api.boot.cbrc.stateverify.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: xiongfei
 * @date: 2022/09/07 16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CbrcRequest implements Serializable {

    /**
     * 1流水验真(需要将流水发送给贷款行)，2其他用途（只需要将流水发送给客户）
     */
    private int applyUseType;
    /**
     * 申请用途备注
     */
    private String applyUseMessage;
    /**
     * 账户
     */
    private String userAccount;
    /**
     * 明细类型,1全部明细，2收入明细，3支出明细
     */
    private String detailType;
    /**
     *明细开始时间	yyyy-MM-dd
     */
    private String detailStartDate;
    /**
     *明细结束时间	yyyy-MM-dd
     */
    private String detailEndDate;
    /**
     *本人是否需要流水标志	1是，2否，申请用途为1流水验真时，该值必填
     */
    private String detailNeedFlag;
    /**
     *发送方式	1邮件，2短信，是否需要流水标志为1时，必填。注：具体发送方式可根据银行实际情况确定
     */
    private int contactType;
    /**
     *具体联系方式	发送方式为1时为邮箱地址，发送方式为2时为手机号码
     */
    private String contactMessage;
    /**
     *业务流水号	明细申请唯一主键
     */
    private long applyId;
    /**
     *接收行对应接口映射值	用于调用贷款行文件接收接口
     */
    private String bankMappingCode;
    /**
     *加密后的接收行标志	使用国密4加密的贷款行的统一信用码，用于调用贷款行文件接收接口
     */
    private String bankCode;


    private String smsCode;

    /**
     * 从银监获取到的用户手机
     */
    private String telephone;

}
