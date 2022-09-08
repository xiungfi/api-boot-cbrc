package org.minbox.framework.api.boot.cbrc.stateverify.service;

import org.minbox.framework.api.boot.cbrc.stateverify.common.enums.ResponseCode;
import org.minbox.framework.api.boot.cbrc.stateverify.common.exception.LogicException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 @author: xiongfei
 @date: 2022/09/07 22:30
 */
@Service
public class CbrcStatementVerifyService {


    /**
     * 通过账号,交易开始日期，交易截止日 获取交易明细
     *
     * @param accountNo
     * @return
     * @throws Exception
     */
    @Async
    public void getTransDetailByAccountAndUploadCashFlowProof(String accountNo, String detailStartDate, String detailEndDate, long applyId)
            throws LogicException {

        // TODO
        /**
         * 1. 获取流水
         * 2. 通过流水生成文件
         * 3. 压缩文件
         * 4. 发送文件的hash值上链
         */





    }

    /**
     * 文件保存
     *
     * @param fileName
     * @param fileSavePath
     * @param sourceFile
     * @return
     * @throws IOException
     */
    public void saveFile(String fileName, String fileSavePath, MultipartFile sourceFile) throws LogicException {
        File uploadDir = new File(fileSavePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        File destFile = new File(uploadDir+File.separator+fileName);

        try {
            sourceFile.transferTo(destFile);
        } catch (IOException e) {
           throw new LogicException(ResponseCode.CBRC_FAIL,e.getMessage());
        }
    }
}
