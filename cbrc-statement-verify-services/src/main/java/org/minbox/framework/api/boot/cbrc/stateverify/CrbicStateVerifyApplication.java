package org.minbox.framework.api.boot.cbrc.stateverify;

import com.cxytiandi.encrypt.springboot.annotation.EnableEncrypt;
import org.minbox.framework.api.boot.autoconfigure.swagger.annotation.EnableApiBootSwagger;
import org.minbox.framework.logging.spring.context.annotation.client.EnableLoggingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * 启动入口类
 *
 * @author xiongfei
 */
@SpringBootApplication
@EnableApiBootSwagger
@EnableLoggingClient
@EnableEncrypt
public class CrbicStateVerifyApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(CrbicStateVerifyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrbicStateVerifyApplication.class, args);
        logger.info("「银监金综链 流水验真」服务启动成功.");
    }
}
