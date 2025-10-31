package com.shxy.w202350766.suiestation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.shxy.w202350766.suiestation.mapper")
public class SuiestationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuiestationApplication.class, args);
	}

}
