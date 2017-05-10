package com.deng.recipes.api.ws.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by hcdeng on 2017/5/10.
 */
@SpringBootApplication(scanBasePackages={"com.deng.recipes.api.ws.controller"})
public class RestApiBoot {
    public static void main(String[] args) {
        SpringApplication.run(RestApiBoot.class, args);
    }
}
