package com.senyint.utils;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.senyint.mybatis.entity.Employee;
import com.senyint.service.EmployeeService;

/**
 * 员工经理任务分配
 *
 */
@Component
public class ManagerTaskHandler implements TaskListener {
	
	@Override
	public void notify(DelegateTask delegateTask) {
		/**懒加载异常*/
//		Employee employee = SessionContext.get();
//		//设置个人任务的办理人
//		delegateTask.setAssignee(employee.getManager().getName());
		/**从新查询当前用户，再获取当前用户对应的领导*/
		
		Subject subject = SecurityUtils.getSubject();
		//取身份信息
		Employee employee = (Employee) subject.getPrincipal();
		
		//登录用户的上级领导 在Employee表中的Name
		String managerName = null;
		
		//如果登录用户的上级领导在Employee表中的主键id为null或空字符串，则将自己在Employee表中的Name设置到任务办理人中
		if(employee.getManagerId()==null || "".equals(employee.getManagerId())){
			managerName = employee.getName();
		} else {
			//如果登录用户的上级领导不为空，则将该上级领导在Employee表中的Name设置到任务办理人中
			try {
				ApplicationContext appCtx = SpringContextUtil.getApplicationContext();
				EmployeeService employeeService = (EmployeeService)SpringContextUtil.getBean("employeeServiceImpl");
				Employee employeeManager = employeeService.findEmployeeById(employee.getManagerId());
				managerName = employeeManager.getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//设置个人任务的办理人
		delegateTask.setAssignee(managerName);
		
	}

}
