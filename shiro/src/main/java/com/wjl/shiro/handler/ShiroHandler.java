package com.wjl.shiro.handler;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wjl.shiro.service.ShiroService;


@Controller
@RequestMapping("shiro")
public class ShiroHandler {
	
	@Autowired
	private ShiroService shiroService;
	
	@RequestMapping("/login")
	public String login(@RequestParam("username") String username,@RequestParam("password") String password){
		System.out.println("into login");
		Subject curUser=SecurityUtils.getSubject();
		if(!curUser.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			/*开启"记住我"功能，表示在用户在第一次登录（认证）成功后"记住"用户，当用户再次（可能之前关闭了浏览器）
			     重新直接访问需要当前用户身份才能访问的页面时，可以直接打开该页面而不需要再次登录，
			     但是对于敏感页面通常仍需要设置成必须登录（认证）才能访问，即使开启"记住我"也没用；
			  "记住我"功能是利用在浏览器中写cookie实现*/
			token.setRememberMe(true);
			try{  //执行login方法时会依次执行realm中的doGetAuthenticationInfo和doGetAuthorizationInfo方法
				curUser.login(token);
			}catch(UnknownAccountException uae){
				System.out.println("账户不存在");
			}catch(IncorrectCredentialsException ice){
				System.out.println("密码错误");
			}catch(LockedAccountException lae){
				System.out.println("账户已锁定");
			}catch(AuthenticationException ae){
				System.out.println("登录异常:"+ae.getMessage());
			}
		}
		//必须使用redirect返回，不然即使认证失败也会跳转到该页面
		return "redirect:/list.jsp";
	}
	
	@RequestMapping("/testAuthWithMethod")
	public String testAuthAndSessionWithMethod(HttpSession session){
		session.setAttribute("mysession", "mysessionvalue");
		shiroService.testAuthAndSessionWithMethod();
		return "redirect:/list.jsp";
	}
}
