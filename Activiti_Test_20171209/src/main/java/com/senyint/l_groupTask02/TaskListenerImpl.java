package com.senyint.l_groupTask02;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {
	
	/**指定个人任务和组任务的办理人*/
	@Override
	public void notify(DelegateTask delegateTask) {
		
		//String assignee = "王五";
		
		//指定个人任务的办理人，也可以指定组任务的办理人。
		//个人任务：通过类去查询数据库，将下一个任务的办理人查询获取，然后通过setAssignee()的方法指定任务的办理人。
		//delegateTask.setAssignee(assignee);
		
		
		//组任务：
		delegateTask.addCandidateUser("郭靖");
		delegateTask.addCandidateUser("黄蓉");
		
		List<String> candidateUsers = new ArrayList<>();
		candidateUsers.add("东邪");
		candidateUsers.add("西毒");
		candidateUsers.add("南帝");
		candidateUsers.add("北丐");
		delegateTask.addCandidateUsers(candidateUsers);
		
	}

}
