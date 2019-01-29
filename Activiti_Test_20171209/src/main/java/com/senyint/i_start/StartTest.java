package com.senyint.i_start;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 
* @ClassName: StartTest 
* @Description: 开始活动节点测试类
* @author Cai ShiJun 
* @date 2017年12月13日 下午3:20:30 
*
 */
public class StartTest {

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
			
			String resourceNameBpmn = "start.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("start.bpmn");
			String resourceNamePng = "start.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("start.png");
			
			//获取仓库服务的实例,从类路径下完成部署
			Deployment deployment = processEngine.getRepositoryService()	//与流程定义和部署对象相关的Service。RepositoryService：管理流程定义。
												.createDeployment()		//创建一个部署对象。
												.name("start开始活动节点")		//添加部署的名称,这里的名称可以自定义。
												.addInputStream(resourceNameBpmn, inputStreamBpmn)
												.addInputStream(resourceNamePng, inputStreamPng)
												.deploy();		//完成部署。
			
			System.out.println("部署ID:"+deployment.getId());	//部署ID
			System.out.println("部署名称:"+deployment.getName());	//部署名称
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//2.启动流程实例+查询流程实例，判断流程是否结束+查询历史流程实例
	/**
	 * 
	* @Title: startProcessInstance 
	* @Description: 启动流程实例+查询流程实例，判断流程是否结束+查询历史流程实例
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月13日 下午3:50:54
	 */
	@Test
	public void startProcessInstance(){
		
		try{
			
			String processDefinitionKey = "start";
			
			//启动流程
			//使用流程定义的key启动流程实例,默认会按照最新版本启动流程实例.
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService：执行管理，包括启动、推进、删除流程实例等操作。与正在执行的流程实例和执行对象相关的Service。
											.startProcessInstanceByKey(processDefinitionKey);	//使用流程定义的key启动流程实例,key对应helloworld.bpmn文件中properties-->Process-->Id:的值,也就是ACT_RE_PROCDEF表中KEY字段的值。使用流程定义的Key启动流程实例的优点：使用Key值启动，默认是按照最新版本的流程定义启动。
																						//或者也可以使用.startProcessInstanceById("流程id");通过流程定义的id启动流程实例.

			System.out.println("流程实例ID:"+pi.getId());	//流程实例ID
			System.out.println("流程定义ID:"+pi.getProcessDefinitionId());	//流程定义ID
			
			//流程实例ID
			String processInstanceId = pi.getId();
			
			System.out.println("##########################################################");
			
			/**判断流程是否结束，查询正在执行的执行对象表*/
			//当流程已结束后，流程实例被删除，运行时服务对象不能查询。
			ProcessInstance rpi = processEngine.getRuntimeService()
											.createProcessInstanceQuery()		//创建流程实例查询对象
											.processInstanceId(processInstanceId)
											.singleResult();
			
			if(rpi == null){	//说明流程实例结束了
				
				System.out.println(processInstanceId+"流程实例结束了!");
				
				/**查询历史，获取流程的相关信息*/
				//可以使用历史的记录查询
				HistoricProcessInstance hpi = processEngine.getHistoryService()
														.createHistoricProcessInstanceQuery()
														.processInstanceId(processInstanceId)	//使用流程实例ID查询
														.singleResult();
				if(hpi != null){
					System.out.println("历史流程实例ID:"+hpi.getId());
					System.out.println("流程开始时间:"+hpi.getStartTime());
					System.out.println("流程结束时间:"+hpi.getEndTime());
					System.out.println("流程持续时间:"+hpi.getDurationInMillis());
				}
				
			}
			
			//使用断言，检测结果，判断流程执行是否和自己想象的一样。
			System.out.println("流程正常执行~");
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
}
