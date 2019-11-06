package com.alicyu.config;

import lombok.Data;

@Data
public class DBConfig1 {
	private String entityPackage;
	private String mapperxmlDir;
	private String mybatiscfg;
	private String url;
	private String username;
	private String password;
	private int minPoolSize;
	private int maxPoolSize;
	private int maxLifetime;
	private int borrowConnectionTimeout;
	private int loginTimeout;
	private int maintenanceInterval;
	private int maxIdleTime;
	private String testQuery;
}