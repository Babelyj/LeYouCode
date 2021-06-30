package com.leyou.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/5/13
 * Time: 21:15
 * Description: No Description
 */
@SpringBootApplication
@MapperScan("com.leyou.user.mapper")
public class LeyouUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouUserApplication.class);
    }
}