package com.zed.service.system.admin;

import java.util.HashMap;

import com.zed.domain.system.Admin;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface AdminService {

	public Admin findById(String adminId) throws AppValidationException;
			
	public boolean checkPassWord(Admin admin,String inputPwd) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(Admin admin) throws AppValidationException;
	
	public void update(Admin admin) throws AppValidationException;
	
	public void delete(String[] adminId) throws AppValidationException;
	
}
