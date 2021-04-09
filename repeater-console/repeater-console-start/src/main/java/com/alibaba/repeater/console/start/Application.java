package com.alibaba.repeater.console.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * <p>
 *
 * @author zhaoyb1990
 */
@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.alibaba.repeater.console.dal.repository")
//@EntityScan("com.alibaba.repeater.console.dal.model")
@ComponentScan("com.alibaba.repeater.console.*")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
