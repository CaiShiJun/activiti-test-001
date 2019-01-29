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
* @Description: �������������
* @author Cai ShiJun 
* @date 2017��12��13�� ����6:01:46 
*
 */
public class GroupTaskTest {

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
			
			String resourceNameBpmn = "groupTask.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("groupTask.bpmn");
			String resourceNamePng = "groupTask.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("groupTask.png");
			
			//��ȡ�ֿ�����ʵ��,����·������ɲ���
			Deployment deployment = processEngine.getRepositoryService()	//�����̶���Ͳ��������ص�Service��RepositoryService���������̶��塣
												.createDeployment()		//����һ���������
												.name("groupTask������")		//��Ӳ��������,��������ƿ����Զ��塣
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
			
			//���̶����key
			String processDefinitionKey = "groupTask";
			
			//��������
			//ʹ�����̶����key��������ʵ��,Ĭ�ϻᰴ�����°汾��������ʵ��.
			/**��������ʵ����ͬʱ���������̱�����ʹ�����̱�������ָ������İ����ˣ���Ӧbpmn�ļ��ж�ӦUserTask��Properties-Main config-Candidate users�� #{���̱�����} �����̱�������*/
			Map<String,Object> variables = new HashMap<String,Object>();
			variables.put("userIDs", "���,����,СС");
			
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService��ִ�й��������������ƽ���ɾ������ʵ���Ȳ�����������ִ�е�����ʵ����ִ�ж�����ص�Service��
											.startProcessInstanceByKey(processDefinitionKey,variables);	//ʹ�����̶����key��������ʵ��,key��Ӧ.bpmn�ļ���properties-->Process-->Id:��ֵ,Ҳ����ACT_RE_PROCDEF����KEY�ֶε�ֵ��ʹ�����̶����Key��������ʵ�����ŵ㣺ʹ��Keyֵ������Ĭ���ǰ������°汾�����̶���������
																											//����Ҳ����ʹ��.startProcessInstanceById("����id");ͨ�����̶����id��������ʵ��.

			System.out.println("����ʵ��ID:"+pi.getId());	//����ʵ��ID
			System.out.println("���̶���ID:"+pi.getProcessDefinitionId());	//���̶���ID
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//3.�鿴����-��ѯ��ǰ�˵�������
	/**
	 * 
	* @Title: findMyGroupTask 
	* @Description: ��ѯ��ǰ�˵�������
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��13�� ����8:01:42
	 */
	@Test
	public void findMyGroupTask(){
		
		try {
			
			//ָ�����������
			String candidateUser = "����";
			
			//��ѯ������б�
			List<Task> tasks = processEngine.getTaskService()		//TaskService���������������ִ�е����������ص�Service��
											.createTaskQuery()		//���������ѯ����
											/**��ѯ����(where����)*/
											//.taskAssignee(assignee)		//ָ��������������ˡ�
											.taskCandidateUser(candidateUser)	//������İ����˲�ѯ
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
			String assignee = "���";
			
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
			
			String taskId = "100005";
			
			//�������
			processEngine.getTaskService()		//TaskService���������������ִ�е����������ص�Service��
							.complete(taskId);
			
			System.out.println("�������:����ID-"+taskId);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//��ѯ����ִ�е���������˱�
	/**
	 * 
	* @Title: findRunPersonTask 
	* @Description: ��ѯ����ִ�е���������˱�
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��14�� ����8:25:57
	 */
	@Test
	public void findRunPersonTask(){
		
		try {
			
			//����ID
			String taskId = "142505";
			
			List<IdentityLink> list = processEngine.getTaskService()
												.getIdentityLinksForTask(taskId);
			
			if(list != null && list.size() > 0){
				for(IdentityLink identityLink : list){
					System.out.println("����ID��"+identityLink.getTaskId());
					System.out.println("���ͣ�"+identityLink.getType());
					System.out.println("����ʵ��ID��"+identityLink.getProcessInstanceId());
					System.out.println("��������ˣ�"+identityLink.getUserId());
					System.out.println("###############################################");
				}
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	//��ѯ��ʷ����İ����˱�
	/**
	 * 
	* @Title: findHistoryPersonTask 
	* @Description: ��ѯ��ʷ����İ����˱�
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��14�� ����8:26:09
	 */
	@Test
	public void findHistoryPersonTask(){
		
		try {
			
			//����ʵ��ID
			//String processInstanceId = "";
			//����ID
			String taskId = "127504";
			
			List<HistoricIdentityLink> list = processEngine.getHistoryService()
															.getHistoricIdentityLinksForTask(taskId);
															//.getHistoricIdentityLinksForProcessInstance(processInstanceId);
			
			if(list != null && list.size() > 0){
				for(HistoricIdentityLink identityLink : list){
					
					System.out.println("����ID��"+identityLink.getTaskId());
					System.out.println("���ͣ�"+identityLink.getType());
					System.out.println("����ʵ��ID��"+identityLink.getProcessInstanceId());
					System.out.println("��������ˣ�"+identityLink.getUserId());
					System.out.println("################################################");
					
				}
			}		
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//ʰȡ���񣬽�������ָ���������ָ������İ������ֶ�
	/**
	 * 
	* @Title: claim 
	* @Description: ʰȡ���񣬽�������ָ���������ָ������İ������ֶ�
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��14�� ����9:05:11
	 */
	@Test
	public void claim(){
		
		try {
			
			//����ID
			String taskId = "142505";
			//����İ�����
			String userId = "���";
			
			//�������������������񣬷���ĸ������񣨿������������еĳ�Ա��Ҳ�����Ƿ�������ĳ�Ա����
			processEngine.getTaskService()
						.claim(taskId, userId);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//������������˵�������ǰ�ᣬ֮ǰһ���Ǹ�������
	/**
	 * 
	* @Title: setAssignee 
	* @Description: ������������˵�������ǰ�ᣬ֮ǰһ���Ǹ�������
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��14�� ����9:40:57
	 */
	@Test
	public void setAssignee(){
		
		try {
			
			//����ID
			String taskId = "127504";
			
			processEngine.getTaskService()
						.setAssignee(taskId, null);
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	}
	
	
	//������������ӳ�Ա
	/**
	 * 
	* @Title: addGroupUser 
	* @Description: ������������ӳ�Ա
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��14�� ����2:09:47
	 */
	@Test
	public void addGroupUser(){
		
		try {
			
			//����ID
			String taskId = "127504";
			//��Ա������
			String userId = "��H";
			
			processEngine.getTaskService()
						.addCandidateUser(taskId, userId);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//����������ɾ����Ա
	/**
	 * 
	* @Title: deleteGroupUser 
	* @Description: ����������ɾ����Ա
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��14�� ����2:10:19
	 */
	@Test
	public void deleteGroupUser(){
		
		try {
			
			//����ID
			String taskId = "127504";
			//��Ա������
			String userId = "��H";
			
			processEngine.getTaskService()
						.deleteCandidateUser(taskId, userId);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
}
