package org.mybatis.generator.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.MyBatis3CommonConfig;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MyBatisCodeHelper {

	private static MyBatis3CommonConfig myBatis3CommonConfig = new MyBatis3CommonConfig();

	static {
		Properties pro = new Properties();
		InputStream in = MyBatisCodeHelper.class.getClassLoader()
				.getResourceAsStream("tools.properties");
		try {
			pro.load(in);
			myBatis3CommonConfig.setMysqlJar(pro.getProperty("mysqljar"));
			myBatis3CommonConfig.setMysqlJdbc(pro.getProperty("mysql-jdbc"));
			myBatis3CommonConfig.setUserName(pro.getProperty("username"));
			myBatis3CommonConfig.setPassWord(pro.getProperty("password"));
			myBatis3CommonConfig.setBeanTargetProject(pro
					.getProperty("beanTargetProject"));
			myBatis3CommonConfig.setXmlTargetProject(pro
					.getProperty("xmlbeanTargetProject"));
			myBatis3CommonConfig.setDaoTargetProject(pro
					.getProperty("daoTargetProject"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void myBatis3CodeCreate(String tempateFilePath,
			String codeListpath) throws IOException, XMLParserException,
			InvalidConfigurationException, SQLException, InterruptedException {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File(tempateFilePath);

		BufferedReader reader = null;

		try {

			InputStream input = new FileInputStream(new File(codeListpath));

			reader = new BufferedReader(new InputStreamReader(input));

			String[] params = reader.readLine().split(";");

			while (params != null) {

				ConfigurationParser cp = new ConfigurationParser(warnings);
				Configuration config = cp.parseConfiguration(configFile);

				List<String> pathList = config.getClassPathEntries();

				if (pathList != null && !pathList.isEmpty()) {
					pathList.clear();
				}
				pathList.add(myBatis3CommonConfig.getMysqlJar());

				List<Context> list = config.getContexts();

				if (list != null && !list.isEmpty()) {
					Context context = list.get(0);
					JDBCConnectionConfiguration jdbcConfig = context
							.getJdbcConnectionConfiguration();
					jdbcConfig.setConnectionURL(myBatis3CommonConfig
							.getMysqlJdbc());
					jdbcConfig.setUserId(myBatis3CommonConfig.getUserName());
					jdbcConfig.setPassword(myBatis3CommonConfig.getPassWord());

					JavaModelGeneratorConfiguration javaConfig = context
							.getJavaModelGeneratorConfiguration();

					javaConfig.setTargetPackage(params[0]);
					javaConfig.setTargetProject(myBatis3CommonConfig
							.getBeanTargetProject());

					SqlMapGeneratorConfiguration sqlMapConfig = context
							.getSqlMapGeneratorConfiguration();

					sqlMapConfig.setTargetPackage(params[1]);

					sqlMapConfig.setTargetProject(myBatis3CommonConfig
							.getXmlTargetProject());

					JavaClientGeneratorConfiguration javaClientConfig = context
							.getJavaClientGeneratorConfiguration();

					javaClientConfig.setTargetPackage(params[2]);

					javaClientConfig.setTargetProject(myBatis3CommonConfig
							.getDaoTargetProject());

					List<TableConfiguration> tables = context
							.getTableConfigurations();

					for (int i = 0; i < tables.size(); i++) {
						tables.get(i).setTableName(params[3]);
						tables.get(i).setDomainObjectName(params[4]);
					}

					DefaultShellCallback callback = new DefaultShellCallback(
							overwrite);
					MyBatisGenerator myBatisGenerator = new MyBatisGenerator(
							config, callback, warnings);
					myBatisGenerator.generate(null);

					String read = reader.readLine();

					if (read != null) {
						params = read.split(";");
					} else {
						params = null;
					}

				}
			}
		} finally {
			try {
				reader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
