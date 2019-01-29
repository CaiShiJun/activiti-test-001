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
* @Description: 流程定义测试
* @author Cai ShiJun 
* @date 2017年12月10日 上午2:54:53 
*
 */
public class ProcessDefinitionTest {
	
	//获取流程引擎
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	//1.发布流程-部署流程定义
	/**
	 * 
	* @Title: deploymentProcessDefinition 
	* @Description: 部署流程定义(从classpath)
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月9日 下午11:53:03
	 */
	@Test
	public void deploymentProcessDefinition_classpath(){
		
		try{
			
			//获取仓库服务的实例,从类路径下完成部署
			Deployment deployment = processEngine.getRepositoryService()	//与流程定义和部署对象相关的Service。RepositoryService：管理流程定义。
												.createDeployment()		//创建一个部署对象。
												.name("ProcessDefinition流程定义")		//添加部署的名称,这里的名称可以自定义。
												.addClasspathResource("diagrams/helloworld.bpmn")	//addClasspathResource表示从类路径下加载资源文件，一次只能加载一个文件。
												.addClasspathResource("diagrams/helloworld.png")	//addClasspathResource表示从类路径下加载资源文件，一次只能加载一个文件。
												.deploy();		//完成部署。
			
			System.out.println("部署ID:"+deployment.getId());	//部署ID
			System.out.println("部署名称:"+deployment.getName());	//部署名称
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	//1.发布流程-部署流程定义
	/**
	 * 
	* @Title: deploymentProcessDefinition_zip 
	* @Description: 部署流程定义(从zip)
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 上午3:28:47
	 */
	@Test
	public void deploymentProcessDefinition_zip(){
		
		try{
			
			InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloworld.zip");
			ZipInputStream zipInputStream = new ZipInputStream(in);
			
			//获取仓库服务的实例,从类路径下完成部署
			Deployment deployment = processEngine.getRepositoryService()	//与流程定义和部署对象相关的Service。RepositoryService：管理流程定义。
												.createDeployment()		//创建一个部署对象。
												.name("ProcessDefinition流程定义")		//添加部署的名称,这里的名称可以自定义。
												.addZipInputStream(zipInputStream)		//指定zip格式的文件完成部署。
												.deploy();		//完成部署。
			
			System.out.println("部署ID:"+deployment.getId());	//部署ID
			System.out.println("部署名称:"+deployment.getName());	//部署名称
		
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	
	//2.查看流程定义
	/**
	 * 		id:(key):(version):(随机值)
	 * 		name:对应流程文件process节点的name属性
	 * 		key:对应流程文件process节点的id属性
	 * 		version:发布时自动生成的。如果是第一次发布的流程,version默认从1开始;
	 * 				如果当前流程引擎中已存在相同key的流程,则找到当前key对应的最高版本号,在最高版本号的基础上加1。
	 */
	/**
	 * 
	* @Title: findProcessDefinition 
	* @Description: 查询流程定义
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 上午3:41:05
	 */
	@Test
	public void findProcessDefinition(){
		
		try {
			
			List<ProcessDefinition> pdList = processEngine.getRepositoryService()	//获取仓库服务对象,与流程定义和部署对象相关的Service
											.createProcessDefinitionQuery()		//创建一个流程定义(act_re_procdef)的查询	
											/**添加查询条件,where条件*/
											//.deploymentId(deploymentId)		//使用部署对象ID查询
											//.processDefinitionName(processDefinitionName)		//使用流程定义的名称查询
											//.processDefinitionNameLike(processDefinitionNameLike)		//使用流程定义的名称模糊查询
											//.processDefinitionId(processDefinitionId)			//使用流程定义ID查询
											//.processDefinitionKey(processDefinitionKey)		//使用流程定义的Key查询
											
											/**排序*/
											.orderByProcessDefinitionVersion().asc()	//按照版本的升序排列
											//.orderByProcessDefinitionName().desc()		//按照流程定义的名称降序排列
											
											/**返回的结果集*/
											//.count()	//返回结果集的数量
											//.listPage(firstResult, maxResults)	//分页查询
											//.singleResult()	//返回唯一结果集(一个对象)
											.list();	//返回一个集合列表,封装流程定义
			
			if(pdList != null && pdList.size() > 0){
				
				//遍历集合,查看内容
				for(ProcessDefinition pd : pdList){
	
					System.out.println("流程定义的ID:"+pd.getId());			//流程定义的key:版本:随机生成数
					System.out.println("流程定义的名称:"+pd.getName());		//对应helloworld.bpmn文件中的name属性值
					System.out.println("流程定义的key:"+pd.getKey());		//对应helloworld.bpmn文件中的id属性值
					System.out.println("流程定义的版本:"+pd.getVersion());	//当流程定义的key值相同的情况下,版本升级,默认从1开始
					System.out.println("流程定义的资源(bpmn文件)名称:"+pd.getResourceName());
					System.out.println("流程定义的资源(png文件)名称:"+pd.getDiagramResourceName());
					System.out.println("部署对象ID:"+pd.getDeploymentId());
					System.out.println("*****************************************");
					
				}
			
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//3.删除部署到activiti中的流程定义
	/**
	 * 
	* @Title: deleteProcessDefinition 
	* @Description: 删除流程定义
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 下午12:20:25
	 */
	@Test
	public void deleteProcessDefinition(){
		
		try {
			
			//删除发布消息,使用部署ID,完成删除
			String deploymentId = "115001";
			
			//获取仓库服务对象
			RepositoryService repositoryService = processEngine.getRepositoryService();
			
			//普通删除,即不带级联的删除,只能删除没有启动的流程,如果当前规则下有正在执行的流程,则抛出异常
			//repositoryService.deleteDeployment(deploymentId);
			//级联删除,不管流程是否启动都可以删除,会删除和当前规则相关的所有信息,正在执行的信息,也包括历史信息
			//相当于:repositoryService.deleteDeploymentCascade(deploymentId);
			repositoryService.deleteDeployment(deploymentId, true);
			
			System.out.println("删除成功!");
			
		} catch (Exception e) {

			e.printStackTrace();
			
		}
		
	}
	
	
	//4.查看流程附件(查看流程图片)
	/**
	 * 
	* @Title: viewImage 
	* @Description: 查看流程图
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 下午1:00:25
	 */
	@Test
	public void viewImage(){
		
		try {
			
			//将生成图片放到文件夹下
			//从仓库中找需要展示的文件
			String deploymentId = "15001";
			List<String> names = processEngine.getRepositoryService()
											.getDeploymentResourceNames(deploymentId);
			
			//定义获取图片资源名称
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
				
				//通过部署ID和文件名称获取图片.png文件的输入流
				InputStream in = processEngine.getRepositoryService()
											.getResourceAsStream(deploymentId, imageName);
				
				//将图片生成到C盘的桌面目录下
				File f = new File("C:/Users/CaiShiJun/Desktop/"+imageName);
				
				//将输入流的图片写到C盘的桌面目录下
				FileUtils.copyInputStreamToFile(in, f);
				
			}
			
		} catch (Exception e) {

			e.printStackTrace();

		}
		
	}

	
	//附加功能：查询最新版本的流程定义
	/**
	 * 
	* @Title: findLastVersionProcessDefinition 
	* @Description: 附加功能：查询最新版本的流程定义
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 下午7:19:36
	 */
	@Test
	public void findLastVersionProcessDefinition(){
		
		try {
			
			//查询,把最大的版本都排到后面
			List<ProcessDefinition> list = processEngine.getRepositoryService()
														.createProcessDefinitionQuery()
														.orderByProcessDefinitionVersion().asc()		//使用流程定义的版本升序排列
														.list();
			
			//过滤出最新的版本
			/**
			 * Map<String,ProcessDefinition>
			 * Map集合的key:流程定义的key
			 * Map集合的value:流程定义的对象
			 * Map集合的特点:当Map集合key值相同的情况下,后一次的值将替换前一次的值
			 */
			Map<String,ProcessDefinition> map = new LinkedHashMap<String,ProcessDefinition>();
			if(list != null && list.size() > 0){
				for(ProcessDefinition pd : list){
					map.put(pd.getKey(), pd);
				}
			}
			List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());
			
			//显示
			if(pdList != null && pdList.size() > 0){
				
				//遍历集合,查看内容
				for(ProcessDefinition pd : pdList){
	
					System.out.println("流程定义的ID:"+pd.getId());			//流程定义的key:版本:随机生成数
					System.out.println("流程定义的名称:"+pd.getName());		//对应helloworld.bpmn文件中的name属性值
					System.out.println("流程定义的key:"+pd.getKey());		//对应helloworld.bpmn文件中的id属性值
					System.out.println("流程定义的版本:"+pd.getVersion());	//当流程定义的key值相同的情况下,版本升级,默认从1开始
					System.out.println("流程定义的资源(bpmn文件)名称:"+pd.getResourceName());
					System.out.println("流程定义的资源(png文件)名称:"+pd.getDiagramResourceName());
					System.out.println("部署对象ID:"+pd.getDeploymentId());
					System.out.println("*****************************************");
					
				}
			
			}
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
	
	//附加功能：删除流程定义（删除key相同的所有不同版本的流程定义）
	/**
	 * 
	* @Title: deleteProcessDefinitionByKey 
	* @Description: 附加功能：删除流程定义（删除key相同的所有不同版本的流程定义）
	* @throws 
	* @author Cai ShiJun 
	* @date 2017年12月10日 下午7:59:24
	 */
	@Test
	public void deleteProcessDefinitionByKey(){
		
		try {
			
			//流程定义的key
			String processDefinitionKey = "helloworld";
			
			//1.查询指定key的所有版本的流程定义:先使用流程定义的key查询流程定义,查询出所有的版本
			List<ProcessDefinition> list = processEngine.getRepositoryService()
														.createProcessDefinitionQuery()
														.processDefinitionKey(processDefinitionKey)		//指定流程定义的key查询
														.list();
			
			//2.循环删除
			//遍历,获取每个流程定义的部署ID
			if(list != null && list.size() > 0){
				
				for(ProcessDefinition pd : list){
					
					//获取部署ID
					String deploymentId = pd.getDeploymentId();
					
					processEngine.getRepositoryService()
								.deleteDeployment(deploymentId,true);
					
				}
				
			}
			System.out.println("删除成功!");
			
		} catch (Exception e) {

			e.printStackTrace();
		
		}
		
	}
	
}
