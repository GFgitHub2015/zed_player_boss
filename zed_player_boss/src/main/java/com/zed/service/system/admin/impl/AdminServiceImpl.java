package com.zed.service.system.admin.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zed.dao.system.admin.impl.AdminDaoImpl;
import com.zed.domain.system.Admin;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.system.admin.AdminService;
import com.zed.system.page.Page;
import com.zed.util.MD5;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
	private AdminDaoImpl adminDao;
		
	public Admin findById(String adminId)  throws AppValidationException {
		return (Admin)adminDao.findById(adminId);		
	}
	
	@Override
	public boolean checkPassWord(Admin admin,String inputPwd) throws AppValidationException{
		boolean flag = false;

		try{
			if (admin != null )
			{
				String dbPwd = admin.getAdminPwd();
				MD5 md5 = null;
				try {
					md5 = MD5.getInstance();
				} catch (Exception e) {
					return flag;
				}
				String md5Pwd = md5.getHexMD5(inputPwd);
				if (dbPwd.equals(md5Pwd)) {
					flag = true;
				} else {
					flag = false;
				}
			}
			return flag;
		}catch (Exception ex) {
			Log.getLogger(this.getClass()).error("AdminServiceImpl[checkPassWord] failed: " + ex.getMessage());
			return flag;
		}
	}
	

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException{
		return adminDao.findByPage(page);
	}
	
	@Override
	public void add(Admin admin) throws AppValidationException{
		adminDao.add(admin);
	}
	
	@Override
	public void update(Admin admin) throws AppValidationException{
		adminDao.update(admin);	
	}
	
	@Override
	public void delete(String[] adminId) throws AppValidationException {
		adminDao.delete(adminId);	
	}

	
}
