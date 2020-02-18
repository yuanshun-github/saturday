package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*@MapperScan("com.example.demo.mapper")*/
/* 不加载该类(exclude = {DataSourceAutoConfiguration.class})*/
@SpringBootApplication
@MapperScan("com.example.demo.dao")
@EnableCaching
@EnableTransactionManagement
//添加定时注解
@EnableScheduling
public class SaturdayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaturdayApplication.class, args);
	}

}
