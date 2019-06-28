package com.cssl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@MapperScan(basePackages="com.cssl.dao")
@SpringBootApplication
public class App {
 public static void main(String[] args)  {
  SpringApplication.run(App.class,args);
 }
}
