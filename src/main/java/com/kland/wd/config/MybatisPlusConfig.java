package com.kland.wd.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Component;

@MapperScan("com.kland.wd.mapper")
@Component
public class MybatisPlusConfig {
}
