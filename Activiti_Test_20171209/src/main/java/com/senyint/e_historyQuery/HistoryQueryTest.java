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
* @Description: ��ʷ��ѯ������
* @author Cai ShiJun 
* @date 2017��12��12�� ����4:14:20 
*
 */
public class HistoryQueryTest {
	
	//��ȡ��������
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	
	//1.��ѯ��ʷ����ʵ�������Ұ���ĳ�����̶���Ĺ���һ��ִ���˶��ٴ����̣�
	/**
	 * 
	* @Title: queryHistoricProcessInstance 
	* @Description: ��ѯ��ʷ����ʵ�������Ұ���ĳ�����̶���Ĺ���һ��ִ���˶��ٴ����̣�
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��12�� ����4:16:59
	 */
	@Test
	public void queryHistoricProcessInstance(){
		
		try {
			
			String processDefinitionKey = "helloworld";
			
			//��ȡ��ʷ����ʵ����������ʷ����ʵ���ļ���
			List<HistoricProcessInstance> hpiList = processEngine.getHistoryService()		//����ʷ���ݣ���ʷ����ص�Service
																.createHistoricProcessInstanceQuery()	//������ʷ����ʵ����ѯ
																.processDefinitionKey(processDefinitionKey)		//�������̶����key��ѯ
																//.processInstanceId(processInstanceId)			//��������ʵ����ID��ѯ
																.orderByProcessInstanceStartTime().desc()		//�������̿�ʼʱ�併������
																.list();		//���ؽ����
			
			//�����鿴���
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
	
	
	//2.��ѯ��ʷ���ĳһ�����̵�ִ��һ�������˶��ٸ����
	/**
	 * 
	* @Title: queryHistoricActivityInstance 
	* @Description: ��ѯ��ʷ���ĳһ�����̵�ִ��һ�������˶��ٸ����
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��12�� ����4:42:51
	 */
	@Test
	public void queryHistoricActivityInstance(){
		
		try {
			
			//����ʵ��ID
			String processInstanceId = "27501";
			
			List<HistoricActivityInstance> haiList = processEngine.getHistoryService()
																.createHistoricActivityInstanceQuery()		//������ʷ�ʵ����ѯ
																.processInstanceId(processInstanceId)		//ʹ������ʵ��ID��ѯ
																.orderByHistoricActivityInstanceEndTime().asc()		//��������
																//.listPage(firstResult, maxResults)		//��ҳ����
																.list();		//ִ�в�ѯ
			
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
	
	
	//���ӹ��ܣ���ѯ��ʷ����
	/**
	 * 
	* @Title: queryHistoryTask 
	* @Description: ���ӹ��ܣ���ѯ��ʷ����
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��11�� ����12:34:23
	 */
	@Test
	public void queryHistoryTask(){
		
		try {
			
			//��ʷ���������
			//String taskAssignee = "����";
			
			String processInstanceId = "27501";
			
			//ͨ������ʵ��ID��ѯ����ʵ��
			List<HistoricTaskInstance> list = processEngine.getHistoryService()		//����ʷ���ݣ���ʷ����ص�Service
														.createHistoricTaskInstanceQuery()	//������ʷ����ʵ����ѯ
														.processInstanceId(processInstanceId)
														//.taskAssignee(taskAssignee)		//ָ����ʷ����İ����ˣ���ѯ��ʷ����
														.orderByHistoricTaskInstanceStartTime().asc()
														.list();
			if(list != null && list.size() > 0){
				for(HistoricTaskInstance task : list){
					System.out.println("����ID:"+task.getId());
					System.out.println("����ʵ��ID:"+task.getProcessInstanceId());
					System.out.println("����İ�����:"+task.getAssignee());
					System.out.println("ִ�ж���ID:"+task.getExecutionId());
					System.out.println("����ʼʱ��:"+task.getStartTime());
					System.out.println("�������ʱ��:"+task.getEndTime());
					System.out.println("�������ٺ���:"+task.getDurationInMillis());
					System.out.println("##########################################");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//��ѯ��ʷ�����̱�����ʹ�����̱��������ƣ�
	/**
	 * 
	* @Title: getHisVariables 
	* @Description: ��ѯ��ʷ�����̱�����ʹ�����̱��������ƣ�
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��12�� ����3:08:01
	 */
	@Test
	public void getHisVariables(){
		
		try {
			
			//String variableName = "�������";
			String processInstanceId = "27501";
			
			List<HistoricVariableInstance> list = processEngine.getHistoryService()
															.createHistoricVariableInstanceQuery()		//����һ����ʷ�����̱�����ѯ����
															//.variableName(variableName)		//ָ�����̱������Ʋ�ѯ
															.processInstanceId(processInstanceId)
															.list();
			
			if(list != null && list.size() > 0){
				for(HistoricVariableInstance hvi : list){
					System.out.println("��������:"+hvi.getVariableName());
					System.out.println("����ֵ:"+hvi.getValue());
					System.out.println("����ʵ��:"+hvi.getProcessInstanceId());
					System.out.println(":"+hvi.getId());
					System.out.println("#####################################");
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}
