package com.senyint.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senyint.mybatis.entity.Employee;
import com.senyint.mybatis.entity.LeaveBill;
import com.senyint.mybatis.mapper.LeaveBillMapper;
import com.senyint.service.LeaveBillService;

@Service
public class LeaveBillServiceImpl implements LeaveBillService {
	
	@Autowired
	private LeaveBillMapper leaveBillMapper;
	
	/**查询自己的请假单的信息*/
	@Override
	public List<LeaveBill> findLeaveBillList(String userId) {
		List<LeaveBill> list = leaveBillMapper.findLeaveBillList(userId);
		return list;
	}
	
	/**保存请假单*/
	@Override
	public void saveLeaveBill(LeaveBill leaveBill) {
		//获取请假单ID
		Long id = leaveBill.getId();
		/**新增保存*/
		if(id==null){
			//1：从Session中获取当前用户对象，将LeaveBill对象中user与Session中获取的用户对象进行关联

			//从 shiro 中获取当前登录的用户
			Subject subject = SecurityUtils.getSubject();
			Employee employee = (Employee) subject.getPrincipal();
			
			//建立管理关系
			leaveBill.setUser(employee);
			
			//2：保存请假单表，添加一条数据
			leaveBillMapper.saveLeaveBill(leaveBill);
		} else {	/**更新保存*/
			//1：执行update的操作，完成更新
			leaveBillMapper.updateLeaveBill(leaveBill);
		}
		
	}
	
	/**使用请假单ID，查询请假单的对象*/
	@Override
	public LeaveBill findLeaveBillById(Long id) {
		LeaveBill bill = leaveBillMapper.findLeaveBillById(id);
		return bill;
	}
	
	/**使用请假单ID，删除请假单*/
	@Override
	public void deleteLeaveBillById(Long id) {
		leaveBillMapper.deleteLeaveBillById(id);
	}

}
