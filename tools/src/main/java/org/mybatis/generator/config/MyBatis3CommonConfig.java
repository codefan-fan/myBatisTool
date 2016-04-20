package org.mybatis.generator.config;

public class MyBatis3CommonConfig {
	/** mysql 驱动jar文件地址 **/
	private String mysqlJar;
	
	/** 数据库地址 **/
	private String mysqlJdbc;
	
	/** 数据库帐号 **/
	private String userName;
	
	/** 数据库密码 **/
	private String passWord;
	
	/** 生成VO的位置 **/
	private String beanTargetProject;
	
	/** 生成mapper-xml位置 **/
	private String xmlTargetProject;
	
	/** 生成dao位置 **/
	private String daoTargetProject;

	public String getMysqlJar() {
		return mysqlJar;
	}

	public void setMysqlJar(String mysqlJar) {
		this.mysqlJar = mysqlJar;
	}

	public String getMysqlJdbc() {
		return mysqlJdbc;
	}

	public void setMysqlJdbc(String mysqlJdbc) {
		this.mysqlJdbc = mysqlJdbc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getBeanTargetProject() {
		return beanTargetProject;
	}

	public void setBeanTargetProject(String beanTargetProject) {
		this.beanTargetProject = beanTargetProject;
	}

    

	public String getXmlTargetProject() {
		return xmlTargetProject;
	}

	public void setXmlTargetProject(String xmlTargetProject) {
		this.xmlTargetProject = xmlTargetProject;
	}

	public String getDaoTargetProject() {
		return daoTargetProject;
	}

	public void setDaoTargetProject(String daoTargetProject) {
		this.daoTargetProject = daoTargetProject;
	}
	
}
