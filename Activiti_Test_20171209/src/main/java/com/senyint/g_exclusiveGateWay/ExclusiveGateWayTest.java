package com.senyint.g_exclusiveGateWay;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 
* @ClassName: ExclusiveGateWayTest 
* @Description: �������ز�����
* @author Cai ShiJun 
* @date 2017��12��13�� ����1:48:14 
*
 */
public class ExclusiveGateWayTest {

	//��ȡ��������
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.��������-�������̶���
	/**
	 * 
	* @Title: deploymentProcessDefinition_inputStream 
	* @Description: �������̶���(��inputStream)
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��13�� ����8:46:52
	 */
	@Test
	public void deploymentProcessDefinition_inputStream(){
		
		try{
			
			String resourceNameBpmn = "exclusiveGateWay.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("exclusiveGateWay.bpmn");
			String resourceNamePng = "exclusiveGateWay.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("exclusiveGateWay.png");
			
			//��ȡ�ֿ�����ʵ��,����·������ɲ���
			Deployment deployment = processEngine.getRepositoryService()	//�����̶���Ͳ��������ص�Service��RepositoryService���������̶��塣
												.createDeployment()		//����һ���������
												.name("exclusiveGateWay��������")		//���Ӳ��������,��������ƿ����Զ��塣
												.addInputStream(resourceNameBpmn, inputStreamBpmn)
												.addInputStream(resourceNamePng, inputStreamPng)
												.deploy();		//��ɲ���
			
			System.out.println("����ID:"+deployment.getId());	//����ID
			System.out.println("��������:"+deployment.getName());	//��������
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//2.��������-��������ʵ��
	/**
	 * 
	* @Title: startProcessInstance 
	* @Description: ��������ʵ��
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����12:19:06
	 */
	@Test
	public void startProcessInstance(){
		
		try{
			
			String processDefinitionKey = "exclusiveGateWay";
			
			//��������
			//ʹ�����̶����key��������ʵ��,Ĭ�ϻᰴ�����°汾��������ʵ��.
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService��ִ�й����������������ƽ���ɾ������ʵ���Ȳ�����������ִ�е�����ʵ����ִ�ж�����ص�Service��
											.startProcessInstanceByKey(processDefinitionKey);	//ʹ�����̶����key��������ʵ��,key��Ӧhelloworld.bpmn�ļ���properties-->Process-->Id:��ֵ,Ҳ����ACT_RE_PROCDEF����KEY�ֶε�ֵ��ʹ�����̶����Key��������ʵ�����ŵ㣺ʹ��Keyֵ������Ĭ���ǰ������°汾�����̶���������
																						//����Ҳ����ʹ��.startProcessInstanceById("����id");ͨ�����̶����id��������ʵ��.

			System.out.println("����ʵ��ID:"+pi.getId());	//����ʵ��ID
			System.out.println("���̶���ID:"+pi.getProcessDefinitionId());	//���̶���ID
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//3.�鿴����-��ѯ��ǰ�˵ĸ�������
	/**
	 * 
	* @Title: findMyPersonalTask 
	* @Description: ��ѯ��ǰ�˵ĸ�������
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����12:50:09
	 */
	@Test
	public void findMyPersonalTask(){
		
		try {
			
			//ָ�����������
			String assignee = "��С��";
			
			//��ѯ������б�
			List<Task> tasks = processEngine.getTaskService()		//TaskService�����������������ִ�е����������ص�Service��
											.createTaskQuery()		//���������ѯ����
											/**��ѯ����(where����)*/
											.taskAssignee(assignee)		//ָ��������������ˡ�
											//.taskCandidateUser(candidateUser)	//������İ����˲�ѯ
											//.processDefinitionId(processDefinitionId)	//ʹ�����̶���ID��ѯ
											//.processInstanceId(processInstanceId)	//ʹ������ʵ��ID��ѯ
											//.executionId(executionId)	//ʹ��ִ�ж���ID��ѯ
											/**����*/
											.orderByTaskCreateTime().asc()		//ʹ�ô���ʱ�����������
											/**���ؽ����*/
											//.singleResult()	//����Ψһ�����
											//.count()		//���ؽ����������
											//.listPage(firstResult, maxResults)	//��ҳ��ѯ
											.list();	//���ؽ�����б�
			//��׳���ж�
			if(tasks != null && tasks.size() > 0){
			
				//�������ϲ鿴����
				for(Task task : tasks){
					
					System.out.println("����ID:"+task.getId());		//����ID
					System.out.println("��������:"+task.getName());		//��������
					System.out.println("����Ĵ���ʱ��:"+task.getCreateTime());		//����Ĵ���ʱ��
					System.out.println("����İ�����:"+task.getAssignee());		//����İ�����
					System.out.println("����ʵ����ID:"+task.getProcessInstanceId());		//����ʵ����ID
					System.out.println("ִ�ж����ID:"+task.getExecutionId());		//ִ�ж����ID
					System.out.println("���̶����ID:"+task.getProcessDefinitionId());		//���̶����ID
					System.out.println("*************************************");
					
				}
			
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//4.��������-����ҵĸ�������
	/**
	 * 
	* @Title: completeMyPersonalTask 
	* @Description: ����ҵĸ�������
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����1:18:15
	 */
	@Test
	public void completeMyPersonalTask(){
		
		try {
			
			String taskId = "50004";
			
			//��������ͬʱ�������ж���������̱�����ʹ�����̱�������ָ������������һ�����ߡ����̱�����Ӧbpmn�ļ��еı���ʽ���磺${money >= 500 && money <=1000}��
			Map<String,Object> variables = new HashMap<String,Object>();
			variables.put("money", 800);
			
			//�������
			processEngine.getTaskService()		//TaskService�����������������ִ�е����������ص�Service��
							.complete(taskId, variables);		//��������ͬʱ�������̱�����
			
			System.out.println("�������:����ID-"+taskId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
}