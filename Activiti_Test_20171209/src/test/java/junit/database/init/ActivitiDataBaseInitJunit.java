package junit.database.init;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

/**
 * 
* @ClassName: ActivitiDataBaseInitJunit 
* @Description: 初始化Activiti数据库用的Junit单元测试类
* @author Cai ShiJun 
* @date 2017年12月9日 下午8:00:00 
*
 */
public class ActivitiDataBaseInitJunit {

	/**
	 * 
	* @Title: createActivitiTables 
	* @Description: 使用代码创建工作流需要的23张表
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月9日 下午8:01:40
	 */
	@Test
	public void createActivitiTables(){
		
		try{
		
			//1.创建Activiti配置对象的实例
			ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
			
			//2.设置数据库连接信息
			//设置数据库驱动
			processEngineConfiguration.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
			//设置数据库地址
			processEngineConfiguration.setJdbcUrl("jdbc:oracle:thin:@127.0.0.1:1521:orcl");
			//设置数据库连接用户名
			processEngineConfiguration.setJdbcUsername("scott_mytest_004");
			//设置数据库连接密码
			processEngineConfiguration.setJdbcPassword("oracle");
			//设置数据库建表策略
			/**
			 * DB_SCHEMA_UPDATE_TRUE = "true"	//如果不存在表就创建表，存在就直接使用。
			 * DB_SCHEMA_UPDATE_FALSE = "false"	//如果不存在表就抛出异常。
			 * DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop"	//每次都先删除表，再创建新的表。
			 */
			processEngineConfiguration.setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
			
			//3.使用配置对象创建流程引擎实例（检查数据库连接等环境信息是否正确）	工作流的核心对象：ProcessEngine对象。
			ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
			
			System.out.println(processEngine);
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		
	}
	
	/**
	 * 
	* @Title: createActivitiTablesByXml 
	* @Description: 使用配置文件activiti.cfg.xml创建工作流需要的23张表
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月9日 下午10:38:46
	 */
	@Test
	public void createActivitiTablesByXml(){
		
		try{
			//1.加载classpath下名为activiti.cfg.xml文件，创建核心流程引擎对象，系统数据库会自动创建表。
			ProcessEngine processEngine =  ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
			
			System.out.println(processEngine);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	

}
