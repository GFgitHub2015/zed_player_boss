package com.zed.service.system.resource.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zed.dao.system.resource.impl.ResourceDaoImpl;
import com.zed.dao.system.resourcerole.ResourceRoleDao;
import com.zed.domain.system.Resource;
import com.zed.domain.system.ResourceRole;
import com.zed.exception.AppValidationException;
import com.zed.service.system.resource.ResourceService;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Service
@SuppressWarnings("unchecked")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
	private ResourceDaoImpl resourceDao;
    
    @Autowired
    private ResourceRoleDao resourceRoleDao;
		
    @Override
	public Resource findById(String resourceId)  throws AppValidationException {
		return (Resource)resourceDao.findById(resourceId);	
		
	}
		
    @Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException{
		return resourceDao.findByPage(page);	
	}
    
    @Override
	public List<Resource> findAll() throws AppValidationException{
		return resourceDao.findAll();	
	}
	
    @Override
	public void add(Resource resource) throws AppValidationException{
		resourceDao.add(resource);	
	}
    
    @Override
	public void update(Resource resource) throws AppValidationException{
		resourceDao.update(resource);	
	}
	
    @Override
	public void delete(String[] resourceId) throws AppValidationException {
		resourceDao.delete(resourceId);	
	}
	
    @Override
	public List<Resource> getAdminResource(String adminId) {
		return resourceDao.findResourceByAdminId(adminId);
		
	}
	
    @Override
	public List<Resource> findListByRoleId(String roleId) throws AppValidationException {
		return resourceDao.findListByRoleId(roleId);
		
	}
	
    @Override
	public void addRoleResource(String roleId,List<String> list,String createdBy, Date createdTime, String updatedBy,Date updatedTime) throws AppValidationException{
		resourceDao.deleteRoleRescour(roleId);
		for (String rId : list) {
			if(!CommUtil.isEmpty(rId)){
				ResourceRole resourceRole = new ResourceRole(); 
				resourceRole.setResourceId(Long.parseLong(rId));
				resourceRole.setRoleId(Long.parseLong(roleId));
				resourceRole.setCreatedBy(createdBy);
				resourceRole.setCreatedTime(createdTime);
				resourceRole.setUpdatedBy(updatedBy);
				resourceRole.setUpdatedTime(updatedTime);
				resourceRoleDao.addResourceRole(resourceRole);
			}
		}
	}
	
	
	
}
