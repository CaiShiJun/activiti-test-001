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
* @Description: ��ʼ��ڵ������
* @author Cai ShiJun 
* @date 2017��12��13�� ����3:20:30 
*
 */
public class StartTest {

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
			
			String resourceNameBpmn = "start.bpmn";
			InputStream inputStreamBpmn = this.getClass().getResourceAsStream("start.bpmn");
			String resourceNamePng = "start.png";
			InputStream inputStreamPng = this.getClass().getResourceAsStream("start.png");
			
			//��ȡ�ֿ�����ʵ��,����·������ɲ���
			Deployment deployment = processEngine.getRepositoryService()	//�����̶���Ͳ��������ص�Service��RepositoryService���������̶��塣
												.createDeployment()		//����һ���������
												.name("start��ʼ��ڵ�")		//��Ӳ��������,��������ƿ����Զ��塣
												.addInputStream(resourceNameBpmn, inputStreamBpmn)
												.addInputStream(resourceNamePng, inputStreamPng)
												.deploy();		//��ɲ���
			
			System.out.println("����ID:"+deployment.getId());	//����ID
			System.out.println("��������:"+deployment.getName());	//��������
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//2.��������ʵ��+��ѯ����ʵ�����ж������Ƿ����+��ѯ��ʷ����ʵ��
	/**
	 * 
	* @Title: startProcessInstance 
	* @Description: ��������ʵ��+��ѯ����ʵ�����ж������Ƿ����+��ѯ��ʷ����ʵ��
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��13�� ����3:50:54
	 */
	@Test
	public void startProcessInstance(){
		
		try{
			
			String processDefinitionKey = "start";
			
			//��������
			//ʹ�����̶����key��������ʵ��,Ĭ�ϻᰴ�����°汾��������ʵ��.
			ProcessInstance pi = processEngine.getRuntimeService()		//RuntimeService��ִ�й��������������ƽ���ɾ������ʵ���Ȳ�����������ִ�е�����ʵ����ִ�ж�����ص�Service��
											.startProcessInstanceByKey(processDefinitionKey);	//ʹ�����̶����key��������ʵ��,key��Ӧhelloworld.bpmn�ļ���properties-->Process-->Id:��ֵ,Ҳ����ACT_RE_PROCDEF����KEY�ֶε�ֵ��ʹ�����̶����Key��������ʵ�����ŵ㣺ʹ��Keyֵ������Ĭ���ǰ������°汾�����̶���������
																						//����Ҳ����ʹ��.startProcessInstanceById("����id");ͨ�����̶����id��������ʵ��.

			System.out.println("����ʵ��ID:"+pi.getId());	//����ʵ��ID
			System.out.println("���̶���ID:"+pi.getProcessDefinitionId());	//���̶���ID
			
			//����ʵ��ID
			String processInstanceId = pi.getId();
			
			System.out.println("##########################################################");
			
			/**�ж������Ƿ��������ѯ����ִ�е�ִ�ж����*/
			//�������ѽ���������ʵ����ɾ��������ʱ��������ܲ�ѯ��
			ProcessInstance rpi = processEngine.getRuntimeService()
											.createProcessInstanceQuery()		//��������ʵ����ѯ����
											.processInstanceId(processInstanceId)
											.singleResult();
			
			if(rpi == null){	//˵������ʵ��������
				
				System.out.println(processInstanceId+"����ʵ��������!");
				
				/**��ѯ��ʷ����ȡ���̵������Ϣ*/
				//����ʹ����ʷ�ļ�¼��ѯ
				HistoricProcessInstance hpi = processEngine.getHistoryService()
														.createHistoricProcessInstanceQuery()
														.processInstanceId(processInstanceId)	//ʹ������ʵ��ID��ѯ
														.singleResult();
				if(hpi != null){
					System.out.println("��ʷ����ʵ��ID:"+hpi.getId());
					System.out.println("���̿�ʼʱ��:"+hpi.getStartTime());
					System.out.println("���̽���ʱ��:"+hpi.getEndTime());
					System.out.println("���̳���ʱ��:"+hpi.getDurationInMillis());
				}
				
			}
			
			//ʹ�ö��ԣ���������ж�����ִ���Ƿ���Լ������һ����
			System.out.println("��������ִ��~");
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
}
