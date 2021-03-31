package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Created with IntelliJ IDEA.
 * User: Bable
 * Date: 2021/3/2
 * Time: 21:46
 * Description: No Description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouSerchApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouSerchApplication.class);
    }
}