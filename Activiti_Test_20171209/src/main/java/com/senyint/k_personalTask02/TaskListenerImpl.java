package com.senyint.k_personalTask02;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {
	
	/**ָ�����������������İ�����*/
	@Override
	public void notify(DelegateTask delegateTask) {
		
		String assignee = "����";
		
		//ָ����������İ����ˣ�Ҳ����ָ��������İ����ˡ�
		//��������ͨ����ȥ��ѯ���ݿ⣬����һ������İ����˲�ѯ��ȡ��Ȼ��ͨ��setAssignee()�ķ���ָ������İ����ˡ�
		delegateTask.setAssignee(assignee);
		
	}

}
