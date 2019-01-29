package com.senyint.a_helloworld;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;


/**
 * 
* @ClassName: HelloWorld 
* @Description: Activiti工作流第一个练习a_helloworld。
* @author Cai ShiJun 
* @date 2017年12月9日 下午11:52:12 
*
 */
public class HelloWorld {
	
	//获取流程引擎
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.发布流程-部署流程定义
	/**
	 * 
	* @Title: deploymentProcessDefinition 
	* @Description: 部署流程定义
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月9日 下午11:53:03
	 */
	@Test
	public void deploymentProcessDefinition(){
		
		try{
			
			//获取仓库服务的实例
			Deployment deployment = processEngine.getRepositoryService()	//与流程定义和部署对象相关的Service。RepositoryService：管理流程定义。
												.createDeployment()		//创建一个部署对象。
												.name("helloworld入门程序")		//添加部署的名称,这里的名称可以自定义。
												.addClasspathResource("diagrams/helloworld.bpmn")	//addClasspathResource表示从类路径下加载资源文件，一次只能加载一个文件。
												.addClasspathResource("diagrams/helloworld.png")	//addClasspathResource表示从类路径下加载资源文件，一次只能加载一个文件。
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
			
			String processDefinitionKey = "helloworld";
			
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
	
	
	//3.查看任务-查询当前人的个人任务
	/**
	 * 
	* @Title: findMyPersonalTask 
	* @Description: 查询当前人的个人任务
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 上午12:50:09
	 */
	@Test
	public void findMyPersonalTask(){
		
		try {
			
			//指定任务办理者
			String assignee = "王五";
			
			//查询任务的列表
			List<Task> tasks = processEngine.getTaskService()		//TaskService：任务管理。与正在执行的任务管理相关的Service。
											.createTaskQuery()		//创建任务查询对象。
											.taskAssignee(assignee)		//指定个人任务办理人。
											.list();
			//健壮性判断
			if(tasks != null && tasks.size() > 0){
			
				//遍历集合查看内容
				for(Task task : tasks){
					
					System.out.println("任务ID:"+task.getId());		//任务ID
					System.out.println("任务名称:"+task.getName());		//任务名称
					System.out.println("任务的创建时间:"+task.getCreateTime());		//任务的创建时间
					System.out.println("任务的办理人:"+task.getAssignee());		//任务的办理人
					System.out.println("流程实例的ID:"+task.getProcessInstanceId());		//流程实例的ID
					System.out.println("执行对象的ID:"+task.getExecutionId());		//执行对象的ID
					System.out.println("流程定义的ID:"+task.getProcessDefinitionId());		//流程定义的ID
					System.out.println("*************************************");
					
				}
			
			}
			
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
			
			String taskId = "7502";
			
			//完成任务
			processEngine.getTaskService()		//TaskService：任务管理。与正在执行的任务管理相关的Service。
							.complete(taskId);
			
			System.out.println("完成任务:任务ID-"+taskId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
}
