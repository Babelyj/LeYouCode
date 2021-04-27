package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/4/15
 * Time: 21:37
 * Description: No Description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouGoodsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouGoodsWebApplication.class);
    }
}