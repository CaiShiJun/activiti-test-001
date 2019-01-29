package com.senyint.mybatis.mapper;

import com.senyint.mybatis.entity.Employee;

public interface EmployeeMapper {
	
	Employee findEmployeeByName(String name) throws Exception;
	
	Employee findEmployeeById(String id) throws Exception;
	
}
