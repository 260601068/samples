package com.wjl.shiro.service.impl;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.wjl.shiro.service.ShiroService;

/* @service注解必须加在一个接口的实现类上才有效；
 * 如果开启事务后shiro注解无效或异常时，可以将shiro注解加在controller上
 * shiro权限注解方法验证失败后不会自动跳转到shiro中配置的unauthorizedUrl页面，需要在springmvc中配置*/
@Service
public class ShiroServiceImpl implements ShiroService {
	@RequiresRoles({ "admin" })
	public void testAuthAndSessionWithMethod() {
		System.out.println("testAuthWithMethod,time： " + new Date());
		//shiro内置的session不依赖底层的容器（如tomcat）可以在service层访问HttpSession（非侵入式）
		Session session = SecurityUtils.getSubject().getSession();
		Object sessionval = session.getAttribute("mysession");
		System.out.println("mysession: " + sessionval);
	}
}
