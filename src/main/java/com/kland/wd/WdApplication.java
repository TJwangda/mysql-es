package com.kland.wd;

import com.kland.wd.util.SimpleCanalClientExample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class WdApplication {

    public static void main(String[] args) {
        System.out.println("===========项目启动ing==========");
        SpringApplication.run(WdApplication.class, args);
        System.out.println("==================项目启动成功========================");
    }

}
