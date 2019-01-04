package com.guo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: fristWeb
 * Author: 果子
 * Create Time:2018/12/20 16:48
 */
@SpringBootApplication(scanBasePackages = {"com.guo"})
@MapperScan("com.guo.dao")
public class App {
    public static void main(String[] args){

        SpringApplication.run(App.class,args);
    }
}
