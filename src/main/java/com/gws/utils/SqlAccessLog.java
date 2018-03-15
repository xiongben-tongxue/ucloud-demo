/*
 * Copyright (C) 2016  HangZhou YuShi Technology Co.Ltd  Holdings Ltd. All rights reserved
 *
 * 本代码版权归杭州宇石科技所有，且受到相关的法律保护。
 * 没有经过版权所有者的书面同意，
 * 任何其他个人或组织均不得以任何形式将本文件或本文件的部分代码用于其他商业用途。
 *
 */
package com.gws.utils;

/**
 * sql访问日志
 *
 * @version 
 * @author liuyi  2016年7月18日 上午10:27:26
 * 
 */
public class SqlAccessLog {
	/**当前访问数据源*/
    private  String dataSource;
    /**当前生成SQL*/
    private  String sql;
    /**调用DAO类名*/
    private  String JpaRepositoryClass;
    /**是否分表*/
    private Boolean isSharding= false;
	/**sql执行时间*/
	private String accessTime;
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getJpaRepositoryClass() {
		return JpaRepositoryClass;
	}
	public void setJpaRepositoryClass(String jpaRepositoryClass) {
		JpaRepositoryClass = jpaRepositoryClass;
	}

	public String getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}
	
	public Boolean getIsSharding() {
		return isSharding;
	}
	public void setIsSharding(Boolean isSharding) {
		this.isSharding = isSharding;
	}
	@Override
	public String toString() {
		return "dataSource=[" + dataSource + "]`sql=" + sql + "`JpaRepositoryClass=" + JpaRepositoryClass 
				+ "`accessTime=" + accessTime+"`isSharding=" + isSharding;
	}
	
}
