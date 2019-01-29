package com.senyint.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.senyint.mybatis.entity.Employee;
import com.senyint.mybatis.entity.LeaveBill;
import com.senyint.service.LeaveBillService;

@Controller
@RequestMapping("/leavebill")
public class LeaveBillAction {
	
	@Autowired
	private LeaveBillService leaveBillService;

	/**
	 * 
	* @Title: home 
	* @Description: 请假管理首页显示
	* @param request
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 上午12:06:18
	 */
	@RequestMapping("/home")
	public String home(HttpServletRequest request,Model model){
		
		//从 shiro 中获取当前登录的用户
		Subject subject = SecurityUtils.getSubject();
		
		Employee employee = (Employee) subject.getPrincipal();
		
		//1：查询所有的请假信息（对应a_leavebill），返回List<LeaveBill>
		List<LeaveBill> list = leaveBillService.findLeaveBillList(employee.getId()); 
		
		//从 request 中获取 ServletContext上下文对象
		//ServletContext servletContext = request.getServletContext();
		//将所有的请假信息放置到上下文对象中
		//servletContext.setAttribute("list", list);
		
		model.addAttribute("leaveBillList", list);
		
		return "home";
	}
	
	/**
	 * 
	* @Title: input 
	* @Description: 添加请假申请
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 上午12:08:12
	 */
	@RequestMapping("/input")
	public String input(LeaveBill leaveBill,Model model){
		
		//1：获取请假单ID
		Long id = leaveBill.getId();
		
		//修改
		if(id!=null){
			//2：使用请假单ID，查询请假单信息，
			leaveBill = leaveBillService.findLeaveBillById(id);
		}
		//新增 及 修改的保存
		leaveBillService.saveLeaveBill(leaveBill);
		
		//3：将请假单信息放置到栈顶，页面使用struts2的标签，支持表单回显
		model.addAttribute("leaveBillMessage",leaveBill);
		
		return "home";
	}
	
	/**
	 * 
	* @Title: save 
	* @Description: 保存/更新，请假申请
	* @param leaveBill
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 下午10:18:51
	 */
	@RequestMapping("/save")
	public String save(LeaveBill leaveBill) {
		//执行保存
		leaveBillService.saveLeaveBill(leaveBill);
		return "save";
	}

	/**
	 * 
	* @Title: delete 
	* @Description: 删除，请假申请
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 下午10:19:42
	 */
	@RequestMapping("/delete")
	public String delete(LeaveBill leaveBill){
		//1：获取请假单ID
		Long id = leaveBill.getId();
		//执行删除
		leaveBillService.deleteLeaveBillById(id);
		return "save";
	}
	
}
