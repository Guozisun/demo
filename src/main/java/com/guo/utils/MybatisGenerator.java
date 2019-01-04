package com.guo.utils;

/**
 * Title:
 * Description:
 * Copyright: 2018 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: miaosha
 * Author: 果子
 * Create Time:2018/12/20 14:42
 */




import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MybatisGenerator {


    public static void main(String[] args) {

        try {

			  /*List<String> warnings = new ArrayList<String>();
			   boolean overwrite = true;
			   File configFile = new File("D:/workspace_06/mybatisGenerator/src/main/resources/config.xml");
			   ConfigurationParser cp = new ConfigurationParser(warnings);
			   Configuration config = cp.parseConfiguration(configFile);
			   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			   myBatisGenerator.generate(null);
			   */

            List<String>warnings = new ArrayList<String>();
            boolean overwrite = true;

            // 指定逆向工程配置文件
            File configFile = new File("src/main/resources/generator.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);

            System.out.println("===================");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}