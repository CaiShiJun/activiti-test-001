package com.senyint.service;

import java.util.List;

import com.senyint.mybatis.entity.LeaveBill;



public interface LeaveBillService {

	List<LeaveBill> findLeaveBillList(String userId);

	void saveLeaveBill(LeaveBill leaveBill);

	LeaveBill findLeaveBillById(Long id);

	void deleteLeaveBillById(Long id);

}
