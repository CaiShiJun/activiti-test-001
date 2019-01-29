package com.senyint.web.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.internal.engine.ValueContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.senyint.mybatis.entity.Employee;
import com.senyint.mybatis.entity.LeaveBill;
import com.senyint.service.LeaveBillService;
import com.senyint.service.WorkflowService;
import com.senyint.web.form.WorkflowBean;

@Controller
@RequestMapping("/workflow")
public class WorkflowAction{
	
	@Autowired
	private WorkflowService workflowService;
	
	@Autowired
	private LeaveBillService leaveBillService;


	/**
	 * 
	* @Title: deployHome 
	* @Description: 部署管理首页显示
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 下午4:50:56
	 */
	@RequestMapping("/deployHome")
	public String deployHome(Model model){
		//1:查询部署对象信息，对应表（act_re_deployment）
		List<Deployment> depList = workflowService.findDeploymentList();
		//2:查询流程定义的信息，对应表（act_re_procdef）
		List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
		//放置到上下文对象中
		/*ValueContext.putValueContext("depList", depList);
		ValueContext.putValueContext("pdList", pdList);*/
		model.addAttribute("depList", depList);
		model.addAttribute("pdList", pdList);
		
		return "deployHome";
	}
	
	/**
	 * 
	* @Title: newdeploy 
	* @Description: 发布流程
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 下午12:34:47
	 */
	@RequestMapping("/newdeploy")
	public String newdeploy(WorkflowBean workflowBean){
		//获取页面传递的值
		//1：获取页面上传递的zip格式的文件，格式是File类型
		MultipartFile file = workflowBean.getFile();
		//文件名称
		String filename = workflowBean.getFilename();
		
		if(filename!=null && !"".equals(filename) && file!=null){
			//完成部署
			workflowService.saveNewDeploye(file,filename);
		}
		return "list";
	}
	
	/**
	 * 
	* @Title: delDeployment 
	* @Description: 删除部署信息
	* @param workflowBean
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 下午8:20:03
	 */
	@RequestMapping("/delDeployment")
	public String delDeployment(WorkflowBean workflowBean){
		//1：获取部署对象ID
		String deploymentId = workflowBean.getDeploymentId();
		//2：使用部署对象ID，删除流程定义
		workflowService.deleteProcessDefinitionByDeploymentId(deploymentId);
		return "list";
	}
	
	/**
	 * 
	* @Title: viewImage 
	* @Description: 查看流程图
	* @param workflowBean
	* @param response
	* @return
	* @throws Exception 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 下午7:46:10
	 */
	@RequestMapping("/viewImage")
	public String viewImage(WorkflowBean workflowBean,HttpServletResponse response) throws Exception{
		//1：获取页面传递的部署对象ID和资源图片名称
		//部署对象ID
		String deploymentId = workflowBean.getDeploymentId();
		//资源图片名称
		String imageName = workflowBean.getImageName();
		//2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
		InputStream in = workflowService.findImageInputStream(deploymentId,imageName);
		//3：从response对象获取输出流
		OutputStream out = response.getOutputStream();
		//4：将输入流中的数据读取出来，写到输出流中
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();
		//将图写到页面上，用输出流写
		return null;
	}
	
	/**
	 * 
	* @Title: startProcess 
	* @Description: 启动流程
	* @param workflowBean
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月17日 下午8:21:27
	 */
	@RequestMapping("/startProcess")
	public String startProcess(WorkflowBean workflowBean){
		
		// workflowBean 中只有 id（申请单ID）参数需要填写 
		
		//更新请假状态，启动流程实例，让启动的流程实例关联业务
		workflowService.saveStartProcess(workflowBean);
		
		return "home";
	}
	
	
	
	/**
	 * 
	* @Title: listTask 
	* @Description: 任务管理首页显示
	* @param workflowBean
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月18日 下午4:40:51
	 */
	@RequestMapping("/listTask")
	public String listTask(Model model){
		
		//1：从Session中获取当前用户名
		//从 shiro 中获取当前登录的用户
		Subject subject = SecurityUtils.getSubject();
		Employee employee = (Employee) subject.getPrincipal();
		String name = employee.getName();
		
		//2：使用当前用户名查询正在执行的任务表，获取当前任务的集合List<Task>
		List<Task> listTask = workflowService.findTaskListByName(name); 
		model.addAttribute("listTask", listTask);
		
		return "home";
	}
	
	/**
	 * 
	* @Title: viewTaskForm 
	* @Description: 打开任务表单
	* @param workflowBean
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月18日 下午11:25:14
	 */
	@RequestMapping("/viewTaskForm")
	public String viewTaskForm(WorkflowBean workflowBean,Model model){
		
		//workflowBean 中只需要传入 taskId任务ID 即可。
		
		String taskId = workflowBean.getTaskId();
		
		//获取任务表单中任务节点的url连接
		String url = workflowService.findTaskFormKeyByTaskId(taskId);
		url += "?taskId="+taskId;
		
		model.addAttribute("url", url);
		
		
		return "home";	//return "viewTaskForm"; 
	}
	
	/**
	 * 
	* @Title: audit 
	* @Description: 准备表单数据
	* @param workflowBean
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月19日 上午1:02:25
	 */
	@RequestMapping("/audit")
	public String audit(WorkflowBean workflowBean,Model model){
		
		//workflowBean 中只需要传入 taskId任务ID 即可。
		
		//获取任务ID
		String taskId = workflowBean.getTaskId();
		
		/**一：使用任务ID，查找请假单ID，从而获取请假单信息*/
		LeaveBill leaveBill = workflowService.findLeaveBillByTaskId(taskId);
		model.addAttribute("leaveBillInfo", leaveBill);
		
		/**二：已知任务ID，查询ProcessDefinitionEntiy对象，从而获取当前任务完成之后的连线名称，并放置到List<String>集合中*/
		List<String> outcomeList = workflowService.findOutComeListByTaskId(taskId);
		model.addAttribute("outcomeList", outcomeList);
		
		/**三：查询所有历史审核人的审核信息，帮助当前人完成审核，返回List<Comment>*/
		List<Comment> commentList = workflowService.findCommentByTaskId(taskId);
		model.addAttribute("commentList", commentList);
		
		return "home";	//return "taskForm";
	}
	
	/**
	 * 
	* @Title: submitTask 
	* @Description: 提交任务
	* @param workflowBean
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月19日 下午2:22:26
	 */
	@RequestMapping("/submitTask")
	public String submitTask(WorkflowBean workflowBean){
		
		//WorkflowBean中应当传入
		//获取任务ID taskId 
		//获取连线的名称 outcome 
		//批注信息 comment 
		//获取请假单ID id 
		
		workflowService.saveSubmitTask(workflowBean);
		
		return "home";	//return "listTask";
	}
	
	/**
	 * 
	* @Title: viewCurrentImage 
	* @Description: 查看当前流程图（查看当前活动节点，并使用红色的框标注）
	* @param workflowBean
	* @param model
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月19日 下午4:29:10
	 */
	@RequestMapping("/viewCurrentImage")
	public String viewCurrentImage(WorkflowBean workflowBean,Model model){
		
		//workflowBean 中只需要传入 taskId任务ID 即可。
		
		//任务ID
		String taskId = workflowBean.getTaskId();
		
		/**一：查看流程图*/
		//1：获取任务ID，获取任务对象，使用任务对象获取流程定义ID，查询流程定义对象
		ProcessDefinition pd = workflowService.findProcessDefinitionByTaskId(taskId);
		
		//workflowAction_viewImage?deploymentId=<s:property value='#deploymentId'/>&imageName=<s:property value='#imageName'/>
		model.addAttribute("deploymentId", pd.getDeploymentId());
		model.addAttribute("imageName", pd.getDiagramResourceName());
		
		/**二：查看当前活动，获取当期活动对应的坐标x,y,width,height，将4个值存放到Map<String,Object>中*/
		Map<String, Object> map = workflowService.findCoordingByTask(taskId);
		model.addAttribute("acs", map);
		
		return "home";	//return "image";
	}
	
	/**
	 *  
	* @Title: viewHisComment 
	* @Description: 查看历史的批注信息
	* @param workflowBean
	* @return 
	* @throws 
	* @author Cai ShiJun 
	* @date 2018年2月19日 下午4:32:16
	 */
	@RequestMapping("/viewHisComment")
	public String viewHisComment(WorkflowBean workflowBean,Model model){
		
		//workflowBean 中只需要传入 id 申请单ID 即可。
		
		//获取申请单ID
		Long id = workflowBean.getId();
		
		//1：使用请假单ID，查询请假单对象，将对象放置到栈顶，支持表单回显
		LeaveBill leaveBill = leaveBillService.findLeaveBillById(id);
		model.addAttribute("leaveBillInfo", leaveBill);
		
		//2：使用请假单ID，查询历史的批注信息
		List<Comment> commentList = workflowService.findCommentByLeaveBillId(id);
		model.addAttribute("commentList", commentList);
		
		return "home";	//return "viewHisComment";
	}
}
