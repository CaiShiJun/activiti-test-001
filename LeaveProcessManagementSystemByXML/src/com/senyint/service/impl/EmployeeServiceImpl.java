package com.senyint.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senyint.mybatis.entity.Employee;
import com.senyint.mybatis.mapper.EmployeeMapper;
import com.senyint.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;

	/**使用用户名作为查询条件，查询用户对象
	 * @throws Exception */
	@Override
	public Employee findEmployeeByName(String name) throws Exception {
		Employee employee = employeeMapper.findEmployeeByName(name==null?"":name);
		return employee;
	}
	
	/**
	 * 使用用户在Employee表中的主键id作为查询条件，查询用户对象
	 */
	@Override
	public Employee findEmployeeById(String id) throws Exception {
		Employee employee = employeeMapper.findEmployeeById(id==null?"":id);
		return employee;
	}
	
}
