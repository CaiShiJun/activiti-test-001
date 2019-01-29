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
* @Description: ����ʵ��������
* @author Cai ShiJun 
* @date 2017��12��10�� ����9:03:29 
*
 */
public class ProcessInstanceTest {

	//��ȡ��������
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.��������-�������̶���
	/**
	 * 
	* @Title: deploymentProcessDefinition_zip 
	* @Description: �������̶���(��zip)
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����3:28:47
	 */
	@Test
	public void deploymentProcessDefinition_zip(){
		
		try{
			
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
			ZipInputStream zipInputStream = new ZipInputStream(in);
			
			//��ȡ�ֿ�����ʵ��,����·������ɲ���
			Deployment deployment = processEngine.getRepositoryService()	//�����̶���Ͳ��������ص�Service��RepositoryService���������̶��塣
												.createDeployment()		//����һ���������
												.name("ProcessDefinition���̶���")		//��Ӳ��������,��������ƿ����Զ��塣
												.addZipInputStream(zipInputStream)		//ָ��zip��ʽ���ļ���ɲ���
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
			
			String taskId = "20004";
			
			//�������
			processEngine.getTaskService()		//TaskService���������������ִ�е����������ص�Service��
							.complete(taskId);
			
			System.out.println("�������:����ID-"+taskId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//5.��ѯ����״̬���ж���������ִ�У����ǽ�����
	/**
	 * 
	* @Title: queryProcessState 
	* @Description: ��ѯ����״̬���ж���������ִ�У����ǽ�����
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��11�� ����12:08:27
	 */
	@Test
	public void queryProcessState(){
		
		try {
			
			String processInstanceId = "20001";
			
			//ͨ������ʵ��ID��ѯ����ʵ��
			ProcessInstance pi = processEngine.getRuntimeService()		//��ʾ����ִ�е�����ʵ����ִ�ж���
											.createProcessInstanceQuery()	//��������ʵ����ѯ����ѯ����ִ�е�����ʵ��
											.processInstanceId(processInstanceId)	//��������ʵ��ID��ѯ
											.singleResult();			//����Ψһ�Ľ����
			if(pi != null){
				System.out.println("��ǰ�����ڣ�"+pi.getActivityId());
			}else{
				System.out.println("û�в�ѯ����Ӧ������ʵ��,��ѯ������д���� �� �����Ѿ�������");
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
			String taskAssignee = "����";
			
			//ͨ������ʵ��ID��ѯ����ʵ��
			List<HistoricTaskInstance> list = processEngine.getHistoryService()		//����ʷ���ݣ���ʷ����ص�Service
														.createHistoricTaskInstanceQuery()	//������ʷ����ʵ����ѯ
														.taskAssignee(taskAssignee)		//ָ����ʷ����İ����ˣ���ѯ��ʷ����
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
	
	
	//���ӹ��ܣ���ѯ��ʷ����ʵ��
	/**
	 * 
	* @Title: queryHistoryProcessInstance 
	* @Description: ���ӹ��ܣ���ѯ��ʷ����ʵ��
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��11�� ����4:23:27
	 */
	@Test
	public void queryHistoryProcessInstance(){
		
		try {
			
			//����ʵ��ID
			String processInstanceId = "20001";
			
			HistoricProcessInstance hpi = processEngine.getHistoryService()		//����ʷ���ݣ���ʷ����ص�Service
													.createHistoricProcessInstanceQuery()		//������ʷ���̵�ʵ����ѯ
													.processInstanceId(processInstanceId)		//ʹ������ʵ��ID��ѯ
													.singleResult();
			
			System.out.println("���̶���ID:"+hpi.getProcessDefinitionId());
			System.out.println("����ʵ��ID:"+hpi.getId());
			System.out.println("���̿�ʼʱ��:"+hpi.getStartTime());
			System.out.println("���̽���ʱ��:"+hpi.getEndTime());
			System.out.println("�������ٺ���:"+hpi.getDurationInMillis());
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
}
