package com.senyint.service;

import com.senyint.mybatis.entity.Employee;


public interface EmployeeService{

	Employee findEmployeeByName(String name) throws Exception;
	
	Employee findEmployeeById(String id) throws Exception;

}
