package com.wjl.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
/*用户登录（shiro中叫认证）时会自动调用doGetAuthenticationInfo方法，
认证成功后访问需要权限（shiro中叫授权）的页面时会自动调用doGetAuthorizationInfo方法*/
public class ShiroRealm extends AuthorizingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		String username=upToken.getUsername();
		System.out.println("username: "+username);
		if("wjl".equals(username)){
			
		}else if("admin".equals(username)){
			
		}else{
			throw new UnknownAccountException();
		}
		//用户名（通常就是username）和密码(credentials)需要在数据库获取
		Object principal=username;
		//这个密码是"123"使用MD5加密1024次后的密文
		Object credentials="64c8b1e43d8ba3115ab40bcea57f010b";
		String realmName=getName();
		/*后台传人的credentials（密码）会在shiro框架中自动与从前台传来的token中的密码进行匹配，
		如果在realm中使用了加密算法，则会将前台的密码加密后再自动与后台传入的密码进行匹配，
		如果不同会自动抛出IncorrectCredentialsException异常*/
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principal, credentials, realmName);
		return info;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//principals中存有之前认证返回的info中的参数principal的集合信息
		Object pricipal=principals.getPrimaryPrincipal();
		//被授予的角色信息可能需要从数据库表或配置文件中读取
		Set<String> roles=new HashSet<String>();
		roles.add("user");
		if("admin".equals(pricipal)){
			roles.add("admin");
		}
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
		System.out.println("into authorize");
		return info;
	}
	
	//得到明文密码"123"使用MD5加密1024次后的密文
	public static void main(String[] args) {
		String hashAlgorithmName="MD5";
		//加密前的明文密码
		Object credentials="123";
		//盐值可以让相同的密码加密后产生不同的密文，所以更加安全（这里暂时不使用）
		Object salt=null;
		//加密的次数（多次加密后密文的长度不变）
		int hashIterations=1024;
		Object result=new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println("EncryptedCode: "+result);
	}

}
