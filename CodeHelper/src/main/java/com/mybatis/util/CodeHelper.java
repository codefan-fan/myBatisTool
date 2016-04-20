package com.mybatis.util;

import java.io.IOException;
import java.sql.SQLException;

import org.mybatis.generator.api.MyBatisCodeHelper;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

public class CodeHelper {

	public static void main(String[] args) {

            String path = CodeHelper.class.getClassLoader()
			.getResource("generatorConfig.xml").getPath();	
            
            String codePath = CodeHelper.class.getClassLoader()
			.getResource("code.txt").getPath();	          
			
			try {
				MyBatisCodeHelper.myBatis3CodeCreate(path,codePath);
				System.out.print("代码生成完毕");
			} catch (IOException e) {

				e.printStackTrace();
			} catch (XMLParserException e) {

				e.printStackTrace();
			} catch (InvalidConfigurationException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		
	}

}
