package com.senyint.b_processDefinition;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * 
* @ClassName: ProcessDefinitionTest 
* @Description: ���̶������
* @author Cai ShiJun 
* @date 2017��12��10�� ����2:54:53 
*
 */
public class ProcessDefinitionTest {
	
	//��ȡ��������
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.��������-�������̶���
	/**
	 * 
	* @Title: deploymentProcessDefinition 
	* @Description: �������̶���(��classpath)
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��9�� ����11:53:03
	 */
	@Test
	public void deploymentProcessDefinition_classpath(){
		
		try{
			
			//��ȡ�ֿ�����ʵ��,����·������ɲ���
			Deployment deployment = processEngine.getRepositoryService()	//�����̶���Ͳ��������ص�Service��RepositoryService���������̶��塣
												.createDeployment()		//����һ���������
												.name("ProcessDefinition���̶���")		//��Ӳ��������,��������ƿ����Զ��塣
												.addClasspathResource("diagrams/helloworld.bpmn")	//addClasspathResource��ʾ����·���¼�����Դ�ļ���һ��ֻ�ܼ���һ���ļ���
												.addClasspathResource("diagrams/helloworld.png")	//addClasspathResource��ʾ����·���¼�����Դ�ļ���һ��ֻ�ܼ���һ���ļ���
												.deploy();		//��ɲ���
			
			System.out.println("����ID:"+deployment.getId());	//����ID
			System.out.println("��������:"+deployment.getName());	//��������
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
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
	
	
	//2.�鿴���̶���
	/**
	 * 		id:(key):(version):(���ֵ)
	 * 		name:��Ӧ�����ļ�process�ڵ��name����
	 * 		key:��Ӧ�����ļ�process�ڵ��id����
	 * 		version:����ʱ�Զ����ɵġ�����ǵ�һ�η���������,versionĬ�ϴ�1��ʼ;
	 * 				�����ǰ�����������Ѵ�����ͬkey������,���ҵ���ǰkey��Ӧ����߰汾��,����߰汾�ŵĻ����ϼ�1��
	 */
	/**
	 * 
	* @Title: findProcessDefinition 
	* @Description: ��ѯ���̶���
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����3:41:05
	 */
	@Test
	public void findProcessDefinition(){
		
		try {
			
			List<ProcessDefinition> pdList = processEngine.getRepositoryService()	//��ȡ�ֿ�������,�����̶���Ͳ��������ص�Service
											.createProcessDefinitionQuery()		//����һ�����̶���(act_re_procdef)�Ĳ�ѯ	
											/**��Ӳ�ѯ����,where����*/
											//.deploymentId(deploymentId)		//ʹ�ò������ID��ѯ
											//.processDefinitionName(processDefinitionName)		//ʹ�����̶�������Ʋ�ѯ
											//.processDefinitionNameLike(processDefinitionNameLike)		//ʹ�����̶��������ģ����ѯ
											//.processDefinitionId(processDefinitionId)			//ʹ�����̶���ID��ѯ
											//.processDefinitionKey(processDefinitionKey)		//ʹ�����̶����Key��ѯ
											
											/**����*/
											.orderByProcessDefinitionVersion().asc()	//���հ汾����������
											//.orderByProcessDefinitionName().desc()		//�������̶�������ƽ�������
											
											/**���صĽ����*/
											//.count()	//���ؽ����������
											//.listPage(firstResult, maxResults)	//��ҳ��ѯ
											//.singleResult()	//����Ψһ�����(һ������)
											.list();	//����һ�������б�,��װ���̶���
			
			if(pdList != null && pdList.size() > 0){
				
				//��������,�鿴����
				for(ProcessDefinition pd : pdList){
	
					System.out.println("���̶����ID:"+pd.getId());			//���̶����key:�汾:���������
					System.out.println("���̶��������:"+pd.getName());		//��Ӧhelloworld.bpmn�ļ��е�name����ֵ
					System.out.println("���̶����key:"+pd.getKey());		//��Ӧhelloworld.bpmn�ļ��е�id����ֵ
					System.out.println("���̶���İ汾:"+pd.getVersion());	//�����̶����keyֵ��ͬ�������,�汾����,Ĭ�ϴ�1��ʼ
					System.out.println("���̶������Դ(bpmn�ļ�)����:"+pd.getResourceName());
					System.out.println("���̶������Դ(png�ļ�)����:"+pd.getDiagramResourceName());
					System.out.println("�������ID:"+pd.getDeploymentId());
					System.out.println("*****************************************");
					
				}
			
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//3.ɾ������activiti�е����̶���
	/**
	 * 
	* @Title: deleteProcessDefinition 
	* @Description: ɾ�����̶���
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����12:20:25
	 */
	@Test
	public void deleteProcessDefinition(){
		
		try {
			
			//ɾ��������Ϣ,ʹ�ò���ID,���ɾ��
			String deploymentId = "115001";
			
			//��ȡ�ֿ�������
			RepositoryService repositoryService = processEngine.getRepositoryService();
			
			//��ͨɾ��,������������ɾ��,ֻ��ɾ��û������������,�����ǰ������������ִ�е�����,���׳��쳣
			//repositoryService.deleteDeployment(deploymentId);
			//����ɾ��,���������Ƿ�����������ɾ��,��ɾ���͵�ǰ������ص�������Ϣ,����ִ�е���Ϣ,Ҳ������ʷ��Ϣ
			//�൱��:repositoryService.deleteDeploymentCascade(deploymentId);
			repositoryService.deleteDeployment(deploymentId, true);
			
			System.out.println("ɾ���ɹ�!");
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	}
	
	
	//4.�鿴���̸���(�鿴����ͼƬ)
	/**
	 * 
	* @Title: viewImage 
	* @Description: �鿴����ͼ
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����1:00:25
	 */
	@Test
	public void viewImage(){
		
		try {
			
			//������ͼƬ�ŵ��ļ�����
			//�Ӳֿ�������Ҫչʾ���ļ�
			String deploymentId = "15001";
			List<String> names = processEngine.getRepositoryService()
											.getDeploymentResourceNames(deploymentId);
			
			//�����ȡͼƬ��Դ����
			String imageName = null;
			if(names != null && names.size() > 0){
				for(String name : names){
					System.out.println("name:"+name);
					if(name.indexOf(".png") >= 0){
						imageName = name;
					}
				}
			}
			System.out.println("imageName:"+imageName);
			
			if(imageName != null){
				
				//ͨ������ID���ļ����ƻ�ȡͼƬ.png�ļ���������
				InputStream in = processEngine.getRepositoryService()
											.getResourceAsStream(deploymentId, imageName);
				
				//��ͼƬ���ɵ�C�̵�����Ŀ¼��
				File f = new File("C:/Users/CaiShiJun/Desktop/"+imageName);
				
				//����������ͼƬд��C�̵�����Ŀ¼��
				FileUtils.copyInputStreamToFile(in, f);
				
			}
			
		} catch (Exception e) {

			e.printStackTrace();

		}
		
	}

	
	//���ӹ��ܣ���ѯ���°汾�����̶���
	/**
	 * 
	* @Title: findLastVersionProcessDefinition 
	* @Description: ���ӹ��ܣ���ѯ���°汾�����̶���
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����7:19:36
	 */
	@Test
	public void findLastVersionProcessDefinition(){
		
		try {
			
			//��ѯ,�����İ汾���ŵ�����
			List<ProcessDefinition> list = processEngine.getRepositoryService()
														.createProcessDefinitionQuery()
														.orderByProcessDefinitionVersion().asc()		//ʹ�����̶���İ汾��������
														.list();
			
			//���˳����µİ汾
			/**
			 * Map<String,ProcessDefinition>
			 * Map���ϵ�key:���̶����key
			 * Map���ϵ�value:���̶���Ķ���
			 * Map���ϵ��ص�:��Map����keyֵ��ͬ�������,��һ�ε�ֵ���滻ǰһ�ε�ֵ
			 */
			Map<String,ProcessDefinition> map = new LinkedHashMap<String,ProcessDefinition>();
			if(list != null && list.size() > 0){
				for(ProcessDefinition pd : list){
					map.put(pd.getKey(), pd);
				}
			}
			List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
			
			//��ʾ
			if(pdList != null && pdList.size() > 0){
				
				//��������,�鿴����
				for(ProcessDefinition pd : pdList){
	
					System.out.println("���̶����ID:"+pd.getId());			//���̶����key:�汾:���������
					System.out.println("���̶��������:"+pd.getName());		//��Ӧhelloworld.bpmn�ļ��е�name����ֵ
					System.out.println("���̶����key:"+pd.getKey());		//��Ӧhelloworld.bpmn�ļ��е�id����ֵ
					System.out.println("���̶���İ汾:"+pd.getVersion());	//�����̶����keyֵ��ͬ�������,�汾����,Ĭ�ϴ�1��ʼ
					System.out.println("���̶������Դ(bpmn�ļ�)����:"+pd.getResourceName());
					System.out.println("���̶������Դ(png�ļ�)����:"+pd.getDiagramResourceName());
					System.out.println("�������ID:"+pd.getDeploymentId());
					System.out.println("*****************************************");
					
				}
			
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//���ӹ��ܣ�ɾ�����̶��壨ɾ��key��ͬ�����в�ͬ�汾�����̶��壩
	/**
	 * 
	* @Title: deleteProcessDefinitionByKey 
	* @Description: ���ӹ��ܣ�ɾ�����̶��壨ɾ��key��ͬ�����в�ͬ�汾�����̶��壩
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��10�� ����7:59:24
	 */
	@Test
	public void deleteProcessDefinitionByKey(){
		
		try {
			
			//���̶����key
			String processDefinitionKey = "helloworld";
			
			//1.��ѯָ��key�����а汾�����̶���:��ʹ�����̶����key��ѯ���̶���,��ѯ�����еİ汾
			List<ProcessDefinition> list = processEngine.getRepositoryService()
														.createProcessDefinitionQuery()
														.processDefinitionKey(processDefinitionKey)		//ָ�����̶����key��ѯ
														.list();
			
			//2.ѭ��ɾ��
			//����,��ȡÿ�����̶���Ĳ���ID
			if(list != null && list.size() > 0){
				
				for(ProcessDefinition pd : list){
					
					//��ȡ����ID
					String deploymentId = pd.getDeploymentId();
					
					processEngine.getRepositoryService()
								.deleteDeployment(deploymentId,true);
					
				}
				
			}
			System.out.println("ɾ���ɹ�!");
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
}
