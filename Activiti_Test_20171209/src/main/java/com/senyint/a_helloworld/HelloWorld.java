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
* @Description: Activiti��������һ����ϰa_helloworld��
* @author Cai ShiJun 
* @date 2017��12��9�� ����11:52:12 
*
 */
public class HelloWorld {
	
	//��ȡ��������
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.��������-�������̶���
	/**
	 * 
	* @Title: deploymentProcessDefinition 
	* @Description: �������̶���
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��9�� ����11:53:03
	 */
	@Test
	public void deploymentProcessDefinition(){
		
		try{
			
			//��ȡ�ֿ�����ʵ��
			Deployment deployment = processEngine.getRepositoryService()	//�����̶���Ͳ��������ص�Service��RepositoryService���������̶��塣
												.createDeployment()		//����һ���������
												.name("helloworld���ų���")		//��Ӳ��������,��������ƿ����Զ��塣
												.addClasspathResource("diagrams/helloworld.bpmn")	//addClasspathResource��ʾ����·���¼�����Դ�ļ���һ��ֻ�ܼ���һ���ļ���
												.addClasspathResource("diagrams/helloworld.png")	//addClasspathResource��ʾ����·���¼�����Դ�ļ���һ��ֻ�ܼ���һ���ļ���
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
			
			String processDefinitionKey = "helloworld";
			
			//��������
			//ʹ�����̶����key��������ʵ��,Ĭ�ϻᰴ�����°汾��������ʵ��.
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService��ִ�й��������������ƽ���ɾ������ʵ���Ȳ�����������ִ�е�����ʵ����ִ�ж�����ص�Service��
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
			String assignee = "����";
			
			//��ѯ������б�
			List<Task> tasks = processEngine.getTaskService()		//TaskService���������������ִ�е����������ص�Service��
											.createTaskQuery()		//���������ѯ����
											.taskAssignee(assignee)		//ָ��������������ˡ�
											.list();
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
			
			String taskId = "7502";
			
			//�������
			processEngine.getTaskService()		//TaskService���������������ִ�е����������ص�Service��
							.complete(taskId);
			
			System.out.println("�������:����ID-"+taskId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
}
