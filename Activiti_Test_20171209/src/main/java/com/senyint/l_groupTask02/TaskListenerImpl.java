package com.senyint.l_groupTask02;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {
	
	/**ָ�����������������İ�����*/
	@Override
	public void notify(DelegateTask delegateTask) {
		
		//String assignee = "����";
		
		//ָ����������İ����ˣ�Ҳ����ָ��������İ����ˡ�
		//��������ͨ����ȥ��ѯ���ݿ⣬����һ������İ����˲�ѯ��ȡ��Ȼ��ͨ��setAssignee()�ķ���ָ������İ����ˡ�
		//delegateTask.setAssignee(assignee);
		
		
		//������
		delegateTask.addCandidateUser("����");
		delegateTask.addCandidateUser("����");
		
		List<String> candidateUsers = new ArrayList<>();
		candidateUsers.add("��а");
		candidateUsers.add("����");
		candidateUsers.add("�ϵ�");
		candidateUsers.add("��ؤ");
		delegateTask.addCandidateUsers(candidateUsers);
		
	}

}
