package org.minbox.framework.api.boot.cbrc.stateverify;

import org.minbox.framework.api.boot.autoconfigure.swagger.annotation.EnableApiBootSwagger;
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
public class CbrcStateVerifyApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(CbrcStateVerifyApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CbrcStateVerifyApplication.class, args);
        logger.info("「流水验真」服务启动成功.");
    }
}
