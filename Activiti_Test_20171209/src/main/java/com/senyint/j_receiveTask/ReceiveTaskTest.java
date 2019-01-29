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
* @Description: ���ջ��receiveTask�����ȴ����������
* @author Cai ShiJun 
* @date 2017��12��13�� ����4:27:11 
*
 */
public class ReceiveTaskTest {

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
			
			String resourceNameBpmn = "receiveTask.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("receiveTask.bpmn");
			String resourceNamePng = "receiveTask.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("receiveTask.png");
			
			//��ȡ�ֿ�����ʵ��,����·������ɲ���
			Deployment deployment = processEngine.getRepositoryService()	//�����̶���Ͳ��������ص�Service��RepositoryService���������̶��塣
												.createDeployment()		//����һ���������
												.name("receiveTask���ջ�����ȴ����")		//��Ӳ��������,��������ƿ����Զ��塣
												.addInputStream(resourceNameBpmn, inputStreamBpmn)
												.addInputStream(resourceNamePng, inputStreamPng)
												.deploy();		//��ɲ���
			
			System.out.println("����ID:"+deployment.getId());	//����ID
			System.out.println("��������:"+deployment.getName());	//��������
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//2.��������ʵ��+�������̱���+��ȡ���̱���+���ִ��һ��
	/**
	 * 
	* @Title: startProcessInstance 
	* @Description: ��������ʵ��+�������̱���+��ȡ���̱���+���ִ��һ��
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��13�� ����3:50:54
	 */
	@Test
	public void startProcessInstance(){
		
		try{
			
			String processDefinitionKey = "receiveTask";
			
			//2 ��������
			//ʹ�����̶����key��������ʵ��,Ĭ�ϻᰴ�����°汾��������ʵ��.
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService��ִ�й��������������ƽ���ɾ������ʵ���Ȳ�����������ִ�е�����ʵ����ִ�ж�����ص�Service��
											.startProcessInstanceByKey(processDefinitionKey);	//ʹ�����̶����key��������ʵ��,key��Ӧhelloworld.bpmn�ļ���properties-->Process-->Id:��ֵ,Ҳ����ACT_RE_PROCDEF����KEY�ֶε�ֵ��ʹ�����̶����Key��������ʵ�����ŵ㣺ʹ��Keyֵ������Ĭ���ǰ������°汾�����̶���������
																						//����Ҳ����ʹ��.startProcessInstanceById("����id");ͨ�����̶����id��������ʵ��.

			System.out.println("����ʵ��ID:"+pi.getId());	//����ʵ��ID
			System.out.println("���̶���ID:"+pi.getProcessDefinitionId());	//���̶���ID
			
			//����ʵ��ID
			String processInstanceId = pi.getId();
			
			/**��ѯִ�ж���ID*/
			Execution execution1 = processEngine.getRuntimeService()
												.createExecutionQuery()	//����ִ�ж����ѯ
												.processInstanceId(processInstanceId)	//ʹ������ʵ��ID��ѯ
												.activityId("receivetask1")		//��ǰ���id����ӦreceiveTask.bpmn�ļ��ж�ӦreceiveTask��ڵ��Properties-General-Id������ֵ��
												.singleResult();
			
			//ִ�ж���ID
			String execution1Id = execution1.getId();
			
			/**ʹ�����̱������õ������۶��������ҵ�����*/
			processEngine.getRuntimeService()
						.setVariable(execution1Id, "���ܵ������۶�", 21000);
			
			/**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
			processEngine.getRuntimeService()
						.signal(execution1Id);
			
			/**��ѯִ�ж���ID*/
			Execution execution2 = processEngine.getRuntimeService()
												.createExecutionQuery()	//����ִ�ж����ѯ
												.processInstanceId(processInstanceId)	//ʹ������ʵ��ID��ѯ
												.activityId("receivetask2")		//��ǰ���id����ӦreceiveTask.bpmn�ļ��ж�ӦreceiveTask��ڵ��Properties-General-Id������ֵ��
												.singleResult();
			
			//ִ�ж���ID
			String execution2Id = execution2.getId();
			
			/**�����̱����л�ȡ���ܵ������۶��ֵ*/
			Integer value = (Integer) processEngine.getRuntimeService()
													.getVariable(execution2Id, "���ܵ������۶�");
			
			System.out.println("���ϰ巢�Ͷ��ţ����ܵ������۶���"+value);
			
			/**���ִ��һ����������̴��ڵȴ�״̬��ʹ�����̼���ִ��*/
			processEngine.getRuntimeService()
						.signal(execution2Id);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
}
