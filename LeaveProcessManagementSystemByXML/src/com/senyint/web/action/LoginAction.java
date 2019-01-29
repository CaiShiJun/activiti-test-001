package com.senyint.web.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senyint.mybatis.entity.Employee;
import com.senyint.service.EmployeeService;

@Controller
public class LoginAction{
	
	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * 
	* @Title: login 
	* @Description: 登录login.html页面
	* @param httpSession
	* @param request
	* @param model
	* @return
	* @throws Exception 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月16日 下午8:45:59
	 */
	@RequestMapping("/login")
	public String login(HttpSession httpSession,HttpServletRequest request,Model model) throws Exception{
		return "login";
	}
	
	/**
	 * 
	* @Title: loginValidate 
	* @Description: 验证登录提交的用户名和密码。
	* @param httpSession
	* @param request
	* @param model
	* @param username
	* @param password
	* @throws Exception 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月16日 下午8:46:10
	 */
	@RequestMapping("/loginValidate")
	public String loginValidate(HttpSession httpSession, HttpServletRequest request, Map<String, Object> map, Model model, String username, String password) throws Exception {
		
		//1、使用Shiro框架 收集实体/凭据信息 
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		
		//2、收集了实体/凭据信息之后，我们可以通过SecurityUtils工具类，获取当前的用户
		Subject subject = SecurityUtils.getSubject();
		
		try{
			
			//然后通过调用Subject的login方法提交认证。
			subject.login(token);
			
		}catch(Exception exception){
			String msg = "";
			if (exception != null) {
				if (UnknownAccountException.class.getName().equals(exception.getClass().getName())) {
					System.out.println("UnknownAccountException -- > 账号不存在：");
					msg = "UnknownAccountException -- > 账号不存在：";
				} else if (IncorrectCredentialsException.class.getName().equals(exception)) {
					System.out.println("IncorrectCredentialsException -- > 密码不正确：");
					msg = "IncorrectCredentialsException -- > 密码不正确：";
				} else if ("kaptchaValidateFailed".equals(exception)) {
					System.out.println("kaptchaValidateFailed -- > 验证码错误");
					msg = "kaptchaValidateFailed -- > 验证码错误";
				} else {
					msg = "else >> " + exception;
					System.out.println("else -- >" + exception);
				}
				map.put("shiroLoginFailureMessage", msg);
				return "login";
			}
		}
		
		Employee employee = (Employee) subject.getPrincipal();
		System.out.println(employee);
		
		request.setAttribute("employeeMessage", employee);
		
		return "home";
	}
	
	/**退出系统，由 shiro 实现*/

}
