package com.senyint.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.senyint.mybatis.entity.LeaveBill;

public interface LeaveBillMapper {
	
	List<LeaveBill> findLeaveBillList(@Param(value="userId")String userId);

	void saveLeaveBill(LeaveBill leaveBill);

	LeaveBill findLeaveBillById(Long id);

	void deleteLeaveBillById(Long id);
	
	void updateLeaveBill(LeaveBill leaveBill);
	
}
