package com.xiaoyuan.schoolcircle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaoyuan.schoolcircle.mapper")   // 明确扫描 mapper 包
public class SchoolcircleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolcircleApplication.class, args);
	}
}