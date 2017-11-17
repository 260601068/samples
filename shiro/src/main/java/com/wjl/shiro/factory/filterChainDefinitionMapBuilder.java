package com.wjl.shiro.factory;

import java.util.LinkedHashMap;

public class filterChainDefinitionMapBuilder {
	//可以从数据库获取shiro的权限控制规则
public LinkedHashMap<String, String> filterChainDefinitionMap(){
	LinkedHashMap<String, String> map=new LinkedHashMap<>();
	map.put("/login.jsp", "anon");
	map.put("/shiro/login", "anon");
	map.put("/shiro/logout", "logout");
	map.put("/user.jsp", "authc,roles[user]");
	map.put("/admin.jsp", "roles[admin]");
	map.put("/**", "authc");
	return map;
}
}
