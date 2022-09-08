package org.minbox.framework.api.boot.cbrc.stateverify.api;

import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cxytiandi.encrypt.springboot.annotation.Decrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.minbox.framework.api.boot.cbrc.stateverify.api.base.BaseApi;
import org.minbox.framework.api.boot.cbrc.stateverify.common.enums.ResponseCode;
import org.minbox.framework.api.boot.cbrc.stateverify.common.exception.LogicException;
import org.minbox.framework.api.boot.cbrc.stateverify.common.model.ApiResponse;
import org.minbox.framework.api.boot.cbrc.stateverify.common.utils.OkHttpUtils;
import org.minbox.framework.api.boot.cbrc.stateverify.entity.CbrcRequest;
import org.minbox.framework.api.boot.cbrc.stateverify.service.CbrcStatementVerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author: xiongfei
 * @date: 2022/09/07 15:18
 */
@RestController
@RequestMapping(value = "/cbrc/statment")
@Api(tags = "银监流水验真")
public class CbrcStatamentVerifyApi extends BaseApi {

    @Autowired
    private CbrcStatementVerifyService cbrcStatementVerifyService;

    // @Autowired
    // private RedisTemplate<Object, Object> redisTemplate;

    @Value("${cbrc.file.save.path}")
    private String fileSavePath;

    @GetMapping("/checkUser")
    @ApiOperation(value = "确认是否我行账户")
    @Decrypt(decyptParam = "data")
    public ApiResponse<String> CheckUser(@RequestParam String token, @RequestParam String data) throws LogicException {

        globalLogging.info("token:{}", token);
        globalLogging.info("data:{}", data);

        JSONObject dataJson = JSON.parseObject(data);
        CbrcRequest cbrcRequest = JSON.toJavaObject(dataJson, CbrcRequest.class);

        String account = cbrcRequest.getUserAccount();
        // TODO 查询账号是否我行用户
        // 如果不是我行用户

        // throw new LogicException(ResponseCode.ACCOUNT_NOT_FOUND, account);

        // 如果是我行账户，
        String URL = "https://xxxxxxx/detail-user/checkUser";

        // 访问银监 通过token获取用户详细信息
        String res = OkHttpUtils.builder().url(URL).addParam("token", token).get().sync();
        JSONObject jsonObject = JSONObject.parseObject(res);
        Integer code = jsonObject.getInteger("code");
        String telephone;
        if (NumberUtil.equals(code, 0)) {
            telephone = jsonObject.getJSONObject("data").getString("telephone");
            cbrcRequest.setTelephone(telephone);
        } else {
            String msg = jsonObject.getString("msg");
            throw new LogicException(ResponseCode.CBRC_TOKEN_VALIDFAIL, token, msg);
        }
        return ApiResponse.success().data(cbrcRequest);
    }

    /**
     *
     * @param cbrcRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/sendsms")
    @ApiOperation(value = "发送短信服务")
    public ApiResponse setSMSCode(@Valid @RequestBody CbrcRequest cbrcRequest) throws LogicException {
        // TODO 发送短信，并存储到redis中，用于短信验证
        return ApiResponse.success();

    }

    /**
     *
     * @param cbrcRequest
     * @return
     * @throws Exception
     */
    @PostMapping("/verifysms")
    @ApiOperation(value = "发送短信服务")
    public ApiResponse VerifySMSCode(@Valid @RequestBody CbrcRequest cbrcRequest) throws LogicException {
        // 从redis取短信验证码，短信验证

        // 生成pdf格式流水文件并上传区块链
        cbrcStatementVerifyService.getTransDetailByAccountAndUploadCashFlowProof(cbrcRequest.getUserAccount(),
            cbrcRequest.getDetailStartDate(), cbrcRequest.getDetailEndDate(), cbrcRequest.getApplyId());

        return ApiResponse.success();

    }

    /**
     *
     * @param file
     * @param applyId
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadFile")
    @ApiOperation(value = "文件接收服务")
    public ApiResponse uploadFile(@RequestPart("file") MultipartFile file, @RequestParam String applyId)
        throws LogicException {

        String fileName = file.getOriginalFilename();

        int a =1/0;

        cbrcStatementVerifyService.saveFile(fileName, fileSavePath, file);

        return ApiResponse.cbrc_success();
    }

    /*  public static void main(String[] args) {
    
        String data="{\"name\":\"xiongfei\",\"address\":\"address\"}";
    
        SM4 sm4 = SmUtil.sm4("d86d7bab3d6ac01a".getBytes());
    
        System.err.println(sm4.encryptHex(data));
    };*/

}
