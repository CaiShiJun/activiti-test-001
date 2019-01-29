package com.senyint.e_historyQuery;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

/**
 * 
* @ClassName: HistoryQueryTest 
* @Description: 历史查询测试类
* @author Cai ShiJun 
* @date 2017年12月12日 下午4:14:20 
*
 */
public class HistoryQueryTest {
	
	//获取流程引擎
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	
	//1.查询历史流程实例（查找按照某个流程定义的规则一共执行了多少次流程）
	/**
	 * 
	* @Title: queryHistoricProcessInstance 
	* @Description: 查询历史流程实例（查找按照某个流程定义的规则一共执行了多少次流程）
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月12日 下午4:16:59
	 */
	@Test
	public void queryHistoricProcessInstance(){
		
		try {
			
			String processDefinitionKey = "helloworld";
			
			//获取历史流程实例，返回历史流程实例的集合
			List<HistoricProcessInstance> hpiList = processEngine.getHistoryService()		//与历史数据（历史表）相关的Service
																.createHistoricProcessInstanceQuery()	//创建历史流程实例查询
																.processDefinitionKey(processDefinitionKey)		//按照流程定义的key查询
																//.processInstanceId(processInstanceId)			//按照流程实例的ID查询
																.orderByProcessInstanceStartTime().desc()		//按照流程开始时间降序排列
																.list();		//返回结果集
			
			//遍历查看结果
			if(hpiList != null && hpiList.size() > 0){
				for(HistoricProcessInstance hpi : hpiList){
					System.out.println(":"+hpi.getId());
					System.out.println(":"+hpi.getProcessDefinitionId());
					System.out.println(":"+hpi.getStartTime());
					System.out.println(":"+hpi.getEndTime());
					System.out.println(":"+hpi.getDurationInMillis());
					System.out.println("##############################");
				}
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//2.查询历史活动（某一次流程的执行一共经历了多少个活动）
	/**
	 * 
	* @Title: queryHistoricActivityInstance 
	* @Description: 查询历史活动（某一次流程的执行一共经历了多少个活动）
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月12日 下午4:42:51
	 */
	@Test
	public void queryHistoricActivityInstance(){
		
		try {
			
			//流程实例ID
			String processInstanceId = "27501";
			
			List<HistoricActivityInstance> haiList = processEngine.getHistoryService()
																.createHistoricActivityInstanceQuery()		//创建历史活动实例查询
																.processInstanceId(processInstanceId)		//使用流程实例ID查询
																.orderByHistoricActivityInstanceEndTime().asc()		//排序条件
																//.listPage(firstResult, maxResults)		//分页条件
																.list();		//执行查询
			
			if(haiList != null && haiList.size() > 0){
				for(HistoricActivityInstance hai : haiList){
					System.out.println(":"+hai.getActivityId());
					System.out.println(":"+hai.getActivityName());
					System.out.println(":"+hai.getActivityType());
					System.out.println(":"+hai.getProcessInstanceId());
					System.out.println(":"+hai.getAssignee());
					System.out.println(":"+hai.getStartTime());
					System.out.println(":"+hai.getEndTime());
					System.out.println(":"+hai.getDurationInMillis());
					System.out.println("#############################");
				}
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
			//String taskAssignee = "张三";
			
			String processInstanceId = "27501";
			
			//通过流程实例ID查询流程实例
			List<HistoricTaskInstance> list = processEngine.getHistoryService()		//与历史数据（历史表）相关的Service
														.createHistoricTaskInstanceQuery()	//创建历史任务实例查询
														.processInstanceId(processInstanceId)
														//.taskAssignee(taskAssignee)		//指定历史任务的办理人，查询历史任务
														.orderByHistoricTaskInstanceStartTime().asc()
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
			
			//String variableName = "请假天数";
			String processInstanceId = "27501";
			
			List<HistoricVariableInstance> list = processEngine.getHistoryService()
															.createHistoricVariableInstanceQuery()		//创建一个历史的流程变量查询对象
															//.variableName(variableName)		//指定流程变量名称查询
															.processInstanceId(processInstanceId)
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
