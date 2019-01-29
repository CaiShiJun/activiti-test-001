package com.senyint.d_processVariables;

import java.io.InputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 
* @ClassName: ProcessVariablesTest 
* @Description: ���̱���������
* @author Cai ShiJun 
* @date 2017��12��11�� ����5:46:31 
*
 */
public class ProcessVariablesTest {
	
	//��ȡ��������
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//�������̶��壨InputStream��
	/**
	 * 
	* @Title: deploymentProcessDefinition_inputStream 
	* @Description: �������̶��壨InputStream��
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��11�� ����5:55:02
	 */
	@Test
	public void deploymentProcessDefinition_inputStream(){
		
		try{
			
			String resourceNameBpmn = "processVariables.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
			String resourceNamePng = "processVariables.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
			
			//��ȡ�ֿ�����ʵ��,����·������ɲ���
			Deployment deployment = processEngine.getRepositoryService()	//�����̶���Ͳ��������ص�Service��RepositoryService���������̶��塣
												.createDeployment()		//����һ���������
												.name("ProcessDefinition���̶���")		//��Ӳ��������,��������ƿ����Զ��塣
												.addInputStream(resourceNameBpmn, inputStreamBpmn)	//ʹ����Դ�ļ������ƣ�Ҫ������Դ�ļ�������Ҫһ�£�������������ɲ���
												.addInputStream(resourceNamePng, inputStreamPng)	//ʹ����Դ�ļ������ƣ�Ҫ������Դ�ļ�������Ҫһ�£�������������ɲ���
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
			
			String processDefinitionKey = "processVariables";
			
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
	
	
	//ģ�����̱��������úͻ�ȡ�ĳ���
	/**
	 * 
	* @Title: setAndGetVariables 
	* @Description: ģ�����̱��������úͻ�ȡ�ĳ���
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��11�� ����7:03:40
	 */
	@Test
	public void setAndGetVariables(){
		
		try {
			
			/**������ʵ����ִ�ж�������ִ�У�*/
			RuntimeService runtimeService = processEngine.getRuntimeService();
			
			/**����������ִ�У�*/
			TaskService taskService = processEngine.getTaskService();
			
			/**�������̱���*/
			//=======================================================
			//ͨ��Execution����һ������
			//runtimeService.setVariable(executionId, variableName, value);		//��ʾʹ��ִ�ж���ID �� ���̱��������ƣ��������̱�����ֵ��һ��ֻ������һ��ֵ��
			//ͨ��Execution���ö������
			//runtimeService.setVariables(executionId, variables);		//��ʾʹ��ִ�ж���ID �� Map�����������̱�����Map���ϵ�key�������̱��������ƣ�Map���ϵ�value�������̱�����ֵ��һ�ο������ö��ֵ����
			//ͨ��Task����һ������
			//taskService.setVariable(taskId, variableName, value);		//��ʾʹ���������ID �� ���̱��������ƣ��������̱�����ֵ��һ��ֻ������һ��ֵ��
			//ͨ��Task���ö������
			//taskService.setVariables(taskId, variables);		//��ʾʹ���������ID �� Map�����������̱�����Map���ϵ�key�������̱��������ƣ�Map���ϵ�value�������̱�����ֵ��һ�ο������ö��ֵ����
			//����������ʵ����ͬʱ�������������̱�������Map����
			//runtimeService.startProcessInstanceByKey(processDefinitionKey, variables)
			//����������ͬʱ�������������̱�������Map����
			//taskService.complete(taskId, variables);
			
			/**��ȡ���̱���*/
			//=========================================================
			//��ȡ�����ķ���
			//ͨ��Execution��ȡһ������
			//runtimeService.getVariable(executionId, variableName);		//ʹ��ִ�ж���ID�����̱��������ƣ���ȡ���̱�����ֵ
			//ͨ��Execution��ȡ���б�������Ϣ����ŵ�Map������
			//runtimeService.getVariables(executionId);		//ʹ��ִ�ж���ID����ȡ���е����̱����������̱������õ�Map�����У�Map���ϵ�key�������̱��������ƣ�Map���ϵ�value�������̱�����ֵ��
			//ͨ��Execution��ȡָ�����̱������Ƶı���ֵ����Ϣ����ŵ�Map������
			//runtimeService.getVariables(executionId, variableNames);		//ʹ��ִ�ж���ID����ȡ���̱�����ֵ��ͨ���������̱��������ƴ�ŵ������У���ȡָ�����̱������Ƶ����̱�����ֵ��ֵ��ŵ�Map�����С�
			
			//ͨ��Task��ȡһ������
			//taskService.getVariable(taskId, variableName);	//ʹ������ID�����̱��������ƣ���ȡ���̱�����ֵ
			//ͨ��Task��ȡ���б�������Ϣ����ŵ�Map������
			//taskService.getVariables(taskId);		//ʹ������ID����ȡ���е����̱����������̱������õ�Map�����У�Map���ϵ�key�������̱��������ƣ�Map���ϵ�value�������̱�����ֵ��
			//ͨ��Task��ȡָ�����̱������Ƶı���ֵ����Ϣ����ŵ�Map������
			//taskService.getVariables(taskId, variableNames);		//ʹ������ID����ȡ���̱�����ֵ��ͨ���������̱��������ƴ�ŵ������У���ȡָ�����̱������Ƶ����̱�����ֵ��ֵ��ŵ�Map�����С�
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//�������̱���
	/**
	 * 
	* @Title: setProcessVariables 
	* @Description: �������̱���
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��11�� ����6:45:34
	 */
	@Test
	public void setProcessVariables(){
		
		try {
			
			//��ȡ����ִ�е�Service������ִ�У�
			TaskService taskService = processEngine.getTaskService();
			//ָ��������
			String assigneeUser = "";
			//����ʵ��ID
			String processInstanceId = "32502";
			
			Task task = taskService.createTaskQuery()	//���������ѯ
								.taskAssignee(assigneeUser)		//ָ��������
								.processInstanceId(processInstanceId)		//ָ������ʵ��ID
								.singleResult();
			
			/** һ���������̱�����ʹ�û����������ͣ������д�Ż����������� */
			
			taskService.setVariableLocal(task.getId(), "�����", "��ʫ�B");		//Local�����뵱ǰ����ID�󶨣����ݿ���е�TASK_ID_�ͻ�洢����ID��ֻ�и�����ID��������ܻ�ȡ�ñ��������ݡ�
			taskService.setVariable(task.getId(), "���ԭ��", "�ؼ�̽��");
			taskService.setVariableLocal(task.getId(), "�������", 6);
			
			Map<String,Date> variablesMap = new LinkedHashMap<>();
			variablesMap.put("�������", new Date());
			taskService.setVariables(task.getId(), variablesMap);
			
			/** ���������д��JavaBean����ǰ�᣺��JavaBean����ʵ�� implements java.io.Serializable */
			/**
			 * ��һ��JavaBean��ʵ�����к�Serializable�����õ����̱����У�Ҫ��JavaBean�����Բ����ٷ����仯����������仯���ڻ�ȡ��ʱ�򣬻��׳��쳣��
			 * ���������
			 * 			��JavaBean��������ӣ�
			 * 				private static final long serialVersionUID = 6757393795687480331L;
			 * 				ͬʱʵ�� implements java.io.Serializable
			 */
			Person person = new Person();
			person.setId(1001);
			person.setName("�仨");
			taskService.setVariable(task.getId(), "��Ա��Ϣ", person);
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//��ȡ���̱���
	/**
	 * 
	* @Title: getProcessVariables 
	* @Description: ��ȡ���̱���
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��11�� ����8:44:24
	 */
	@Test
	public void getProcessVariables(){
		
		try {
			
			//��ȡִ�е�Service
			TaskService taskService = processEngine.getTaskService();
			//ָ��������
			String assigneeUser = "����";
			//����ʵ��ID
			String processInstanceId = "27501";
			
			Task task = taskService.createTaskQuery()		//���������ѯ
								.taskAssignee(assigneeUser)		//ָ��������
								.processInstanceId(processInstanceId)		//ָ������ʵ��ID
								.singleResult();
			
			/** һ����ȡ���̱�����ʹ�û����������ͣ������д�Ż����������� */
			String stringValue = (String) taskService.getVariable(task.getId(), "�����");
			Integer integerValue = (Integer) taskService.getVariableLocal(task.getId(), "�������");
			Map<String,Object> valuesMap = taskService.getVariables(task.getId());
			Date dateValue = (Date) valuesMap.get("�������");
			
			System.out.println("�����:"+stringValue);
			System.out.println("�������:"+integerValue);
			System.out.println("�������:"+dateValue);
			
			/** ���������д��JavaBean����ǰ�᣺��JavaBean����ʵ�� implements java.io.Serializable */
			/**
			 * ��ȡ���̱���ʱע�⣺
			 * 			���̱��������JavaBean���󣬳���Ҫ��ʵ�� implements java.io.Serializable ֮�⣬ͬʱҪ�����̱�����������Բ��ܷ����仯�������׳��쳣��
			 * ���������
			 * 			���������̱�����ʱ����JavaBean�Ķ�����ʹ�ã�
			 * 			private static final long serialVersionUID = 6757393795687480331L;
			 */
			Person person = (Person) taskService.getVariable(task.getId(), "��Ա��Ϣ");
			System.out.println(person);
			
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
			
			String taskId = "27504";
			
			//�������
			processEngine.getTaskService()		//TaskService���������������ִ�е����������ص�Service��
							.complete(taskId);
			
			System.out.println("�������:����ID-"+taskId);
			
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
			
			String variableName = "�������";
			
			List<HistoricVariableInstance> list = processEngine.getHistoryService()
															.createHistoricVariableInstanceQuery()		//����һ����ʷ�����̱�����ѯ����
															.variableName(variableName)		//ָ�����̱������Ʋ�ѯ
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
