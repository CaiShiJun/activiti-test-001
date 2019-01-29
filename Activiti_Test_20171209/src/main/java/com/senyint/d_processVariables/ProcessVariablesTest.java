package com.senyint.d_processVariables;

import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 
* @ClassName: ProcessVariablesTest 
* @Description: 流程变量测试类
* @author Cai ShiJun 
* @date 2017年12月11日 下午5:46:31 
*
 */
public class ProcessVariablesTest {
	
	//获取流程引擎
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//部署流程定义（InputStream）
	/**
	 * 
	* @Title: deploymentProcessDefinition_inputStream 
	* @Description: 部署流程定义（InputStream）
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月11日 下午5:55:02
	 */
	@Test
	public void deploymentProcessDefinition_inputStream(){
		
		try{
			
			String resourceNameBpmn = "processVariables.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
			String resourceNamePng = "processVariables.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
			
			//获取仓库服务的实例,从类路径下完成部署
			Deployment deployment = processEngine.getRepositoryService()	//与流程定义和部署对象相关的Service。RepositoryService：管理流程定义。
												.createDeployment()		//创建一个部署对象。
												.name("ProcessDefinition流程定义")		//添加部署的名称,这里的名称可以自定义。
												.addInputStream(resourceNameBpmn, inputStreamBpmn)	//使用资源文件的名称（要求：与资源文件的名称要一致）和输入流来完成部署。
												.addInputStream(resourceNamePng, inputStreamPng)	//使用资源文件的名称（要求：与资源文件的名称要一致）和输入流来完成部署。
												.deploy();		//完成部署。
			
			System.out.println("部署ID:"+deployment.getId());	//部署ID
			System.out.println("部署名称:"+deployment.getName());	//部署名称
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//2.启动流程-启动流程实例
	/**
	 * 
	* @Title: startProcessInstance 
	* @Description: 启动流程实例
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 上午12:19:06
	 */
	@Test
	public void startProcessInstance(){
		
		try{
			
			String processDefinitionKey = "processVariables";
			
			//启动流程
			//使用流程定义的key启动流程实例,默认会按照最新版本启动流程实例.
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService：执行管理，包括启动、推进、删除流程实例等操作。与正在执行的流程实例和执行对象相关的Service。
											.startProcessInstanceByKey(processDefinitionKey);	//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中properties-->Process-->Id:的值,也就是ACT_RE_PROCDEF表中KEY字段的值。使用流程定义的Key启动流程实例的优点：使用Key值启动，默认是按照最新版本的流程定义启动。
																						//或者也可以使用.startProcessInstanceById("流程id");通过流程定义的id启动流程实例.

			System.out.println("流程实例ID:"+pi.getId());	//流程实例ID
			System.out.println("流程定义ID:"+pi.getProcessDefinitionId());	//流程定义ID
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//模拟流程变量的设置和获取的场景
	/**
	 * 
	* @Title: setAndGetVariables 
	* @Description: 模拟流程变量的设置和获取的场景
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月11日 下午7:03:40
	 */
	@Test
	public void setAndGetVariables(){
		
		try {
			
			/**与流程实例，执行对象（正在执行）*/
			RuntimeService runtimeService = processEngine.getRuntimeService();
			
			/**与任务（正在执行）*/
			TaskService taskService = processEngine.getTaskService();
			
			/**设置流程变量*/
			//=======================================================
			//通过Execution设置一个变量
			//runtimeService.setVariable(executionId, variableName, value);		//表示使用执行对象ID 和 流程变量的名称，设置流程变量的值（一次只能设置一个值）
			//通过Execution设置多个变量
			//runtimeService.setVariables(executionId, variables);		//表示使用执行对象ID 和 Map集合设置流程变量，Map集合的key就是流程变量的名称，Map集合的value就是流程变量的值（一次可以设置多个值）。
			//通过Task设置一个变量
			//taskService.setVariable(taskId, variableName, value);		//表示使用任务对象ID 和 流程变量的名称，设置流程变量的值（一次只能设置一个值）
			//通过Task设置多个变量
			//taskService.setVariables(taskId, variables);		//表示使用任务对象ID 和 Map集合设置流程变量，Map集合的key就是流程变量的名称，Map集合的value就是流程变量的值（一次可以设置多个值）。
			//在启动流程实例的同时，可以设置流程变量，用Map集合
			//runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)
			//在完成任务的同时，可以设置流程变量，用Map集合
			//taskService.complete(taskId, variables);
			
			/**获取流程变量*/
			//=========================================================
			//获取变量的方法
			//通过Execution获取一个变量
			//runtimeService.getVariable(executionId, variableName);		//使用执行对象ID和流程变量的名称，获取流程变量的值
			//通过Execution获取所有变量的信息，存放到Map集合中
			//runtimeService.getVariables(executionId);		//使用执行对象ID，获取所有的流程变量，将流程变量放置到Map集合中，Map集合的key就是流程变量的名称，Map集合的value就是流程变量的值。
			//通过Execution获取指定流程变量名称的变量值的信息，存放到Map集合中
			//runtimeService.getVariables(executionId, variableNames);		//使用执行对象ID，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map集合中。
			
			//通过Task获取一个变量
			//taskService.getVariable(taskId, variableName);	//使用任务ID和流程变量的名称，获取流程变量的值
			//通过Task获取所有变量的信息，存放到Map集合中
			//taskService.getVariables(taskId);		//使用任务ID，获取所有的流程变量，将流程变量放置到Map集合中，Map集合的key就是流程变量的名称，Map集合的value就是流程变量的值。
			//通过Task获取指定流程变量名称的变量值的信息，存放到Map集合中
			//taskService.getVariables(taskId, variableNames);		//使用任务ID，获取流程变量的值，通过设置流程变量的名称存放到集合中，获取指定流程变量名称的流程变量的值，值存放到Map集合中。
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//设置流程变量
	/**
	 * 
	* @Title: setProcessVariables 
	* @Description: 设置流程变量
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月11日 下午6:45:34
	 */
	@Test
	public void setProcessVariables(){
		
		try {
			
			//获取任务执行的Service（正在执行）
			TaskService taskService = processEngine.getTaskService();
			//指定办理人
			String assigneeUser = "";
			//流程实例ID
			String processInstanceId = "32502";
			
			Task task = taskService.createTaskQuery()	//创建任务查询
								.taskAssignee(assigneeUser)		//指定办理人
								.processInstanceId(processInstanceId)		//指定流程实例ID
								.singleResult();
			
			/** 一：设置流程变量，使用基本数据类型，变量中存放基本数据类型 */
			
			taskService.setVariableLocal(task.getId(), "请假人", "蔡诗珺");		//Local代表与当前任务ID绑定，数据库表中的TASK_ID_就会存储任务ID，只有该任务ID的任务才能获取该变量的数据。
			taskService.setVariable(task.getId(), "请假原因", "回家探亲");
			taskService.setVariableLocal(task.getId(), "请假天数", 6);
			
			Map<String,Date> variablesMap = new LinkedHashMap<>();
			variablesMap.put("请假日期", new Date());
			taskService.setVariables(task.getId(), variablesMap);
			
			/** 二：变量中存放JavaBean对象，前提：让JavaBean对象实现 implements java.io.Serializable */
			/**
			 * 当一个JavaBean（实现序列号Serializable）放置到流程变量中，要求JavaBean的属性不能再发生变化，如果发生变化，在获取的时候，会抛出异常。
			 * 解决方案：
			 * 			在JavaBean对象中添加：
			 * 				private static final long serialVersionUID = 6757393795687480331L;
			 * 				同时实现 implements java.io.Serializable
			 */
			Person person = new Person();
			person.setId(1001);
			person.setName("翠花");
			taskService.setVariable(task.getId(), "人员信息", person);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//获取流程变量
	/**
	 * 
	* @Title: getProcessVariables 
	* @Description: 获取流程变量
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月11日 下午8:44:24
	 */
	@Test
	public void getProcessVariables(){
		
		try {
			
			//获取执行的Service
			TaskService taskService = processEngine.getTaskService();
			//指定办理人
			String assigneeUser = "李大大";
			//流程实例ID
			String processInstanceId = "27501";
			
			Task task = taskService.createTaskQuery()		//创建任务查询
								.taskAssignee(assigneeUser)		//指定办理人
								.processInstanceId(processInstanceId)		//指定流程实例ID
								.singleResult();
			
			/** 一：获取流程变量，使用基本数据类型，变量中存放基本数据类型 */
			String stringValue = (String) taskService.getVariable(task.getId(), "请假人");
			Integer integerValue = (Integer) taskService.getVariableLocal(task.getId(), "请假天数");
			Map<String,Object> valuesMap = taskService.getVariables(task.getId());
			Date dateValue = (Date) valuesMap.get("请假日期");
			
			System.out.println("请假人:"+stringValue);
			System.out.println("请假天数:"+integerValue);
			System.out.println("请假日期:"+dateValue);
			
			/** 二：变量中存放JavaBean对象，前提：让JavaBean对象实现 implements java.io.Serializable */
			/**
			 * 获取流程变量时注意：
			 * 			流程变量如果是JavaBean对象，除了要求实现 implements java.io.Serializable 之外，同时要求流程变量对象的属性不能发生变化，否则抛出异常。
			 * 解决方案：
			 * 			在设置流程变量的时候，在JavaBean的对象中使用：
			 * 			private static final long serialVersionUID = 6757393795687480331L;
			 */
			Person person = (Person) taskService.getVariable(task.getId(), "人员信息");
			System.out.println(person);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//4.办理任务-完成我的个人任务
	/**
	 * 
	* @Title: completeMyPersonalTask 
	* @Description: 完成我的个人任务
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 上午1:18:15
	 */
	@Test
	public void completeMyPersonalTask(){
		
		try {
			
			String taskId = "27504";
			
			//完成任务
			processEngine.getTaskService()		//TaskService：任务管理。与正在执行的任务管理相关的Service。
							.complete(taskId);
			
			System.out.println("完成任务:任务ID-"+taskId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	
	//查询历史的流程变量（使用流程变量的名称）
	/**
	 * 
	* @Title: getHisVariables 
	* @Description: 查询历史的流程变量（使用流程变量的名称）
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月12日 下午3:08:01
	 */
	@Test
	public void getHisVariables(){
		
		try {
			
			String variableName = "请假天数";
			
			List<HistoricVariableInstance> list = processEngine.getHistoryService()
															.createHistoricVariableInstanceQuery()		//创建一个历史的流程变量查询对象
															.variableName(variableName)		//指定流程变量名称查询
															.list();
			
			if(list != null && list.size() > 0){
				for(HistoricVariableInstance hvi : list){
					System.out.println("变量名称:"+hvi.getVariableName());
					System.out.println("变量值:"+hvi.getValue());
					System.out.println("流程实例:"+hvi.getProcessInstanceId());
					System.out.println(":"+hvi.getId());
					System.out.println("#####################################");
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
