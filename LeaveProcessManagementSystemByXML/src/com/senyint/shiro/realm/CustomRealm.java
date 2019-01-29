package com.senyint.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.senyint.mybatis.entity.Employee;
import com.senyint.service.EmployeeService;

public class CustomRealm extends AuthorizingRealm {
	
	@Autowired
	private EmployeeService employeeService;
	
	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// 设置realm的名称
	@Override
	public void setName(String name) {
		super.setName(name);
	}

	//realm的认证方法，从数据库查询用户信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		// token是用户输入的用户名和密码 
		// 第一步从token中取出用户名
		String name = (String) token.getPrincipal();
		
		// 第二步：根据用户输入的username从数据库查询
		Employee employee = null;
		try {
			employee = employeeService.findEmployeeByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 如果查询不到返回null
		if(employee==null){
			throw new UnknownAccountException();//没找到帐号  
		}
		
		// 从数据库查询到密码
		String password = employee.getPassword();
				
		//盐
		//String salt = "caishijun";
		
		// 如果查询到返回认证信息AuthenticationInfo
		
		//将activeUser设置simpleAuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(employee, password, this.getName());

		return simpleAuthenticationInfo;

	}
	
	// 用于授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		
		return null;
	}

}
