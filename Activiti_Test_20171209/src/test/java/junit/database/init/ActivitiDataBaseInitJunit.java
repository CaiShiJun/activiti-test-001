package junit.database.init;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * 
* @ClassName: ActivitiDataBaseInitJunit 
* @Description: ��ʼ��Activiti���ݿ��õ�Junit��Ԫ������
* @author Cai ShiJun 
* @date 2017��12��9�� ����8:00:00 
*
 */
public class ActivitiDataBaseInitJunit {

	/**
	 * 
	* @Title: createActivitiTables 
	* @Description: ʹ�ô��봴����������Ҫ��23�ű�
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��9�� ����8:01:40
	 */
	@Test
	public void createActivitiTables(){
		
		try{
		
			//1.����Activiti���ö����ʵ��
			ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
			
			//2.�������ݿ�������Ϣ
			//�������ݿ�����
			processEngineConfiguration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
			//�������ݿ��ַ
			processEngineConfiguration.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
			//�������ݿ������û���
			processEngineConfiguration.setJdbcUsername("scott_mytest_004");
			//�������ݿ���������
			processEngineConfiguration.setJdbcPassword("oracle");
			//�������ݿ⽨�����
			/**
			 * DB_SCHEMA_UPDATE_TRUE = "true"	//��������ڱ�ʹ��������ھ�ֱ��ʹ�á�
			 * DB_SCHEMA_UPDATE_FALSE = "false"	//��������ڱ���׳��쳣��
			 * DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop"	//ÿ�ζ���ɾ�����ٴ����µı�
			 */
			processEngineConfiguration.setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
			
			//3.ʹ�����ö��󴴽���������ʵ����������ݿ����ӵȻ�����Ϣ�Ƿ���ȷ��	�������ĺ��Ķ���ProcessEngine����
			ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
			
			System.out.println(processEngine);
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		
	}
	
	/**
	 * 
	* @Title: createActivitiTablesByXml 
	* @Description: ʹ�������ļ�activiti.cfg.xml������������Ҫ��23�ű�
	* @throws 
	* @author Cai ShiJun 
	* @date 2017��12��9�� ����10:38:46
	 */
	@Test
	public void createActivitiTablesByXml(){
		
		try{
			//1.����classpath����Ϊactiviti.cfg.xml�ļ����������������������ϵͳ���ݿ���Զ�������
			ProcessEngine processEngine =  ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
			
			System.out.println(processEngine);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	

}
