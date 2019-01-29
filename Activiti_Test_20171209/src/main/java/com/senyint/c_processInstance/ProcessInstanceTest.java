package com.senyint.c_processInstance;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 
* @ClassName: ProcessInstanceTest 
* @Description: 流程实例测试类
* @author Cai ShiJun 
* @date 2017年12月10日 下午9:03:29 
*
 */
public class ProcessInstanceTest {

	//获取流程引擎
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.发布流程-部署流程定义
	/**
	 * 
	* @Title: deploymentProcessDefinition_zip 
	* @Description: 部署流程定义(从zip)
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 上午3:28:47
	 */
	@Test
	public void deploymentProcessDefinition_zip(){
		
		try{
			
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
			ZipInputStream zipInputStream = new ZipInputStream(in);
			
			//获取仓库服务的实例,从类路径下完成部署
			Deployment deployment = processEngine.getRepositoryService()	//与流程定义和部署对象相关的Service。RepositoryService：管理流程定义。
												.createDeployment()		//创建一个部署对象。
												.name("ProcessDefinition流程定义")		//添加部署的名称,这里的名称可以自定义。
												.addZipInputStream(zipInputStream)		//指定zip格式的文件完成部署。
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
			String assignee = "张三";
			
			//查询任务的列表
			List<Task> tasks = processEngine.getTaskService()		//TaskService：任务管理。与正在执行的任务管理相关的Service。
											.createTaskQuery()		//创建任务查询对象。
											/**查询条件(where部分)*/
											.taskAssignee(assignee)		//指定个人任务办理人。
											//.taskCandidateUser(candidateUser)	//组任务的办理人查询
											//.processDefinitionId(processDefinitionId)	//使用流程定义ID查询
											//.processInstanceId(processInstanceId)	//使用流程实例ID查询
											//.executionId(executionId)	//使用执行对象ID查询
											/**排序*/
											.orderByTaskCreateTime().asc()		//使用创建时间的升序排列
											/**返回结果集*/
											//.singleResult()	//返回唯一结果集
											//.count()		//返回结果集的数量
											//.listPage(firstResult, maxResults)	//分页查询
											.list();	//返回结果集列表
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
			
			String taskId = "20004";
			
			//完成任务
			processEngine.getTaskService()		//TaskService：任务管理。与正在执行的任务管理相关的Service。
							.complete(taskId);
			
			System.out.println("完成任务:任务ID-"+taskId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//5.查询流程状态（判断流程正在执行，还是结束）
	/**
	 * 
	* @Title: queryProcessState 
	* @Description: 查询流程状态（判断流程正在执行，还是结束）
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月11日 下午12:08:27
	 */
	@Test
	public void queryProcessState(){
		
		try {
			
			String processInstanceId = "20001";
			
			//通过流程实例ID查询流程实例
			ProcessInstance pi = processEngine.getRuntimeService()		//表示正在执行的流程实例和执行对象
											.createProcessInstanceQuery()	//创建流程实例查询，查询正在执行的流程实例
											.processInstanceId(processInstanceId)	//按照流程实例ID查询
											.singleResult();			//返回唯一的结果集
			if(pi != null){
				System.out.println("当前流程在："+pi.getActivityId());
			}else{
				System.out.println("没有查询到对应的流程实例,查询条件填写有误 或 流程已经结束！");
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//附加功能：查询历史任务
	/**
	 * 
	* @Title: queryHistoryTask 
	* @Description: 附加功能：查询历史任务
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月11日 下午12:34:23
	 */
	@Test
	public void queryHistoryTask(){
		
		try {
			
			//历史任务办理人
			String taskAssignee = "张三";
			
			//通过流程实例ID查询流程实例
			List<HistoricTaskInstance> list = processEngine.getHistoryService()		//与历史数据（历史表）相关的Service
														.createHistoricTaskInstanceQuery()	//创建历史任务实例查询
														.taskAssignee(taskAssignee)		//指定历史任务的办理人，查询历史任务
														.list();
			if(list != null && list.size() > 0){
				for(HistoricTaskInstance task : list){
					System.out.println("任务ID:"+task.getId());
					System.out.println("流程实例ID:"+task.getProcessInstanceId());
					System.out.println("任务的办理人:"+task.getAssignee());
					System.out.println("执行对象ID:"+task.getExecutionId());
					System.out.println("任务开始时间:"+task.getStartTime());
					System.out.println("任务结束时间:"+task.getEndTime());
					System.out.println("持续多少毫秒:"+task.getDurationInMillis());
					System.out.println("##########################################");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//附加功能：查询历史流程实例
	/**
	 * 
	* @Title: queryHistoryProcessInstance 
	* @Description: 附加功能：查询历史流程实例
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月11日 下午4:23:27
	 */
	@Test
	public void queryHistoryProcessInstance(){
		
		try {
			
			//流程实例ID
			String processInstanceId = "20001";
			
			HistoricProcessInstance hpi = processEngine.getHistoryService()		//与历史数据（历史表）相关的Service
													.createHistoricProcessInstanceQuery()		//创建历史流程的实例查询
													.processInstanceId(processInstanceId)		//使用流程实例ID查询
													.singleResult();
			
			System.out.println("流程定义ID:"+hpi.getProcessDefinitionId());
			System.out.println("流程实例ID:"+hpi.getId());
			System.out.println("流程开始时间:"+hpi.getStartTime());
			System.out.println("流程结束时间:"+hpi.getEndTime());
			System.out.println("持续多少毫秒:"+hpi.getDurationInMillis());
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
}
