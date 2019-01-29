package com.senyint.l_groupTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 
* @ClassName: PersonalTaskTest 
* @Description: 个人任务测试类
* @author Cai ShiJun 
* @date 2017年12月13日 下午6:01:46 
*
 */
public class GroupTaskTest {

	//获取流程引擎
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.发布流程-部署流程定义
	/**
	 * 
	* @Title: deploymentProcessDefinition_inputStream 
	* @Description: 部署流程定义(从inputStream)
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月13日 上午8:46:52
	 */
	@Test
	public void deploymentProcessDefinition_inputStream(){
		
		try{
			
			String resourceNameBpmn = "groupTask.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("groupTask.bpmn");
			String resourceNamePng = "groupTask.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("groupTask.png");
			
			//获取仓库服务的实例,从类路径下完成部署
			Deployment deployment = processEngine.getRepositoryService()	//与流程定义和部署对象相关的Service。RepositoryService：管理流程定义。
												.createDeployment()		//创建一个部署对象。
												.name("groupTask组任务")		//添加部署的名称,这里的名称可以自定义。
												.addInputStream(resourceNameBpmn, inputStreamBpmn)
												.addInputStream(resourceNamePng, inputStreamPng)
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
			
			//流程定义的key
			String processDefinitionKey = "groupTask";
			
			//启动流程
			//使用流程定义的key启动流程实例,默认会按照最新版本启动流程实例.
			/**启动流程实例的同时，设置流程变量，使用流程变量用来指定任务的办理人，对应bpmn文件中对应UserTask的Properties-Main config-Candidate users的 #{流程变量名} 的流程变量名。*/
			Map<String,Object> variables = new HashMap<String,Object>();
			variables.put("userIDs", "大大,中中,小小");
			
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService：执行管理，包括启动、推进、删除流程实例等操作。与正在执行的流程实例和执行对象相关的Service。
											.startProcessInstanceByKey(processDefinitionKey,variables);	//使用流程定义的key启动流程实例,key对应.bpmn文件中properties-->Process-->Id:的值,也就是ACT_RE_PROCDEF表中KEY字段的值。使用流程定义的Key启动流程实例的优点：使用Key值启动，默认是按照最新版本的流程定义启动。
																											//或者也可以使用.startProcessInstanceById("流程id");通过流程定义的id启动流程实例.

			System.out.println("流程实例ID:"+pi.getId());	//流程实例ID
			System.out.println("流程定义ID:"+pi.getProcessDefinitionId());	//流程定义ID
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//3.查看任务-查询当前人的组任务
	/**
	 * 
	* @Title: findMyGroupTask 
	* @Description: 查询当前人的组任务
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月13日 下午8:01:42
	 */
	@Test
	public void findMyGroupTask(){
		
		try {
			
			//指定任务办理者
			String candidateUser = "中中";
			
			//查询任务的列表
			List<Task> tasks = processEngine.getTaskService()		//TaskService：任务管理。与正在执行的任务管理相关的Service。
											.createTaskQuery()		//创建任务查询对象。
											/**查询条件(where部分)*/
											//.taskAssignee(assignee)		//指定个人任务办理人。
											.taskCandidateUser(candidateUser)	//组任务的办理人查询
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
			String assignee = "大大";
			
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
			
			String taskId = "100005";
			
			//完成任务
			processEngine.getTaskService()		//TaskService：任务管理。与正在执行的任务管理相关的Service。
							.complete(taskId);
			
			System.out.println("完成任务:任务ID-"+taskId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//查询正在执行的任务办理人表
	/**
	 * 
	* @Title: findRunPersonTask 
	* @Description: 查询正在执行的任务办理人表
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月14日 上午8:25:57
	 */
	@Test
	public void findRunPersonTask(){
		
		try {
			
			//任务ID
			String taskId = "142505";
			
			List<IdentityLink> list = processEngine.getTaskService()
												.getIdentityLinksForTask(taskId);
			
			if(list != null && list.size() > 0){
				for(IdentityLink identityLink : list){
					System.out.println("任务ID："+identityLink.getTaskId());
					System.out.println("类型："+identityLink.getType());
					System.out.println("流程实例ID："+identityLink.getProcessInstanceId());
					System.out.println("任务办理人："+identityLink.getUserId());
					System.out.println("###############################################");
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	//查询历史任务的办理人表
	/**
	 * 
	* @Title: findHistoryPersonTask 
	* @Description: 查询历史任务的办理人表
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月14日 上午8:26:09
	 */
	@Test
	public void findHistoryPersonTask(){
		
		try {
			
			//流程实例ID
			//String processInstanceId = "";
			//任务ID
			String taskId = "127504";
			
			List<HistoricIdentityLink> list = processEngine.getHistoryService()
															.getHistoricIdentityLinksForTask(taskId);
															//.getHistoricIdentityLinksForProcessInstance(processInstanceId);
			
			if(list != null && list.size() > 0){
				for(HistoricIdentityLink identityLink : list){
					
					System.out.println("任务ID："+identityLink.getTaskId());
					System.out.println("类型："+identityLink.getType());
					System.out.println("流程实例ID："+identityLink.getProcessInstanceId());
					System.out.println("任务办理人："+identityLink.getUserId());
					System.out.println("################################################");
					
				}
			}		
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//拾取任务，将组任务分给个人任务，指定任务的办理人字段
	/**
	 * 
	* @Title: claim 
	* @Description: 拾取任务，将组任务分给个人任务，指定任务的办理人字段
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月14日 上午9:05:11
	 */
	@Test
	public void claim(){
		
		try {
			
			//任务ID
			String taskId = "142505";
			//任务的办理人
			String userId = "大大";
			
			//将组任务分配给个人任务，分配的个人任务（可以是组任务中的成员，也可以是非组任务的成员）。
			processEngine.getTaskService()
						.claim(taskId, userId);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//将个人任务回退到组任务，前提，之前一定是个组任务
	/**
	 * 
	* @Title: setAssignee 
	* @Description: 将个人任务回退到组任务，前提，之前一定是个组任务
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月14日 上午9:40:57
	 */
	@Test
	public void setAssignee(){
		
		try {
			
			//任务ID
			String taskId = "127504";
			
			processEngine.getTaskService()
						.setAssignee(taskId, null);
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	}
	
	
	//向组任务中添加成员
	/**
	 * 
	* @Title: addGroupUser 
	* @Description: 向组任务中添加成员
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月14日 下午2:09:47
	 */
	@Test
	public void addGroupUser(){
		
		try {
			
			//任务ID
			String taskId = "127504";
			//成员办理人
			String userId = "大H";
			
			processEngine.getTaskService()
						.addCandidateUser(taskId, userId);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//从组任务中删除成员
	/**
	 * 
	* @Title: deleteGroupUser 
	* @Description: 从组任务中删除成员
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月14日 下午2:10:19
	 */
	@Test
	public void deleteGroupUser(){
		
		try {
			
			//任务ID
			String taskId = "127504";
			//成员办理人
			String userId = "大H";
			
			processEngine.getTaskService()
						.deleteCandidateUser(taskId, userId);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
}
