package com.senyint.j_receiveTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 
* @ClassName: ReceiveTaskTest 
* @Description: 接收活动（receiveTask，即等待活动）测试类
* @author Cai ShiJun 
* @date 2017年12月13日 下午4:27:11 
*
 */
public class ReceiveTaskTest {

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
			
			String resourceNameBpmn = "receiveTask.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("receiveTask.bpmn");
			String resourceNamePng = "receiveTask.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("receiveTask.png");
			
			//获取仓库服务的实例,从类路径下完成部署
			Deployment deployment = processEngine.getRepositoryService()	//与流程定义和部署对象相关的Service。RepositoryService：管理流程定义。
												.createDeployment()		//创建一个部署对象。
												.name("receiveTask接收活动（即等待活动）")		//添加部署的名称,这里的名称可以自定义。
												.addInputStream(resourceNameBpmn, inputStreamBpmn)
												.addInputStream(resourceNamePng, inputStreamPng)
												.deploy();		//完成部署。
			
			System.out.println("部署ID:"+deployment.getId());	//部署ID
			System.out.println("部署名称:"+deployment.getName());	//部署名称
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//2.启动流程实例+设置流程变量+获取流程变量+向后执行一步
	/**
	 * 
	* @Title: startProcessInstance 
	* @Description: 启动流程实例+设置流程变量+获取流程变量+向后执行一步
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月13日 下午3:50:54
	 */
	@Test
	public void startProcessInstance(){
		
		try{
			
			String processDefinitionKey = "receiveTask";
			
			//2 启动流程
			//使用流程定义的key启动流程实例,默认会按照最新版本启动流程实例.
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService：执行管理，包括启动、推进、删除流程实例等操作。与正在执行的流程实例和执行对象相关的Service。
											.startProcessInstanceByKey(processDefinitionKey);	//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中properties-->Process-->Id:的值,也就是ACT_RE_PROCDEF表中KEY字段的值。使用流程定义的Key启动流程实例的优点：使用Key值启动，默认是按照最新版本的流程定义启动。
																						//或者也可以使用.startProcessInstanceById("流程id");通过流程定义的id启动流程实例.

			System.out.println("流程实例ID:"+pi.getId());	//流程实例ID
			System.out.println("流程定义ID:"+pi.getProcessDefinitionId());	//流程定义ID
			
			//流程实例ID
			String processInstanceId = pi.getId();
			
			/**查询执行对象ID*/
			Execution execution1 = processEngine.getRuntimeService()
												.createExecutionQuery()	//创建执行对象查询
												.processInstanceId(processInstanceId)	//使用流程实例ID查询
												.activityId("receivetask1")		//当前活动的id，对应receiveTask.bpmn文件中对应receiveTask活动节点的Properties-General-Id的属性值。
												.singleResult();
			
			//执行对象ID
			String execution1Id = execution1.getId();
			
			/**使用流程变量设置当日销售额，用来传递业务参数*/
			processEngine.getRuntimeService()
						.setVariable(execution1Id, "汇总当日销售额", 21000);
			
			/**向后执行一步，如果流程处于等待状态，使得流程继续执行*/
			processEngine.getRuntimeService()
						.signal(execution1Id);
			
			/**查询执行对象ID*/
			Execution execution2 = processEngine.getRuntimeService()
												.createExecutionQuery()	//创建执行对象查询
												.processInstanceId(processInstanceId)	//使用流程实例ID查询
												.activityId("receivetask2")		//当前活动的id，对应receiveTask.bpmn文件中对应receiveTask活动节点的Properties-General-Id的属性值。
												.singleResult();
			
			//执行对象ID
			String execution2Id = execution2.getId();
			
			/**从流程变量中获取汇总当日销售额的值*/
			Integer value = (Integer) processEngine.getRuntimeService()
													.getVariable(execution2Id, "汇总当日销售额");
			
			System.out.println("给老板发送短信：汇总当日销售额是"+value);
			
			/**向后执行一步，如果流程处于等待状态，使得流程继续执行*/
			processEngine.getRuntimeService()
						.signal(execution2Id);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
}
