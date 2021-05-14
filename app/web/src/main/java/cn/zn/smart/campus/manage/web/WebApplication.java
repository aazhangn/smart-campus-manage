package cn.zn.smart.campus.manage.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

;

/**
 *
 * @Author: zhangnan
 * @Date: 2021/05/11 16:23
 */
@SpringBootApplication
@PropertySource(value = "classpath:config/application.properties")
@ComponentScan("cn.zn.smart.campus.manage")
@MapperScan("cn.zn.smart.campus.manage.dao.mapper")
public class WebApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
