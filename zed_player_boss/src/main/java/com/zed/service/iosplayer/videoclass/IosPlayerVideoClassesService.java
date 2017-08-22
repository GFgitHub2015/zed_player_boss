package com.zed.service.iosplayer.videoclass;

import java.util.HashMap;

import com.zed.domain.iosplayer.videoclass.IosPlayerVideoClasses;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface IosPlayerVideoClassesService {

	public IosPlayerVideoClasses findById(String classId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(IosPlayerVideoClasses playerVideoClasses) throws AppValidationException;
	
	public void update(IosPlayerVideoClasses playerVideoClasses) throws AppValidationException;
	
	public void delete(String[] classIds) throws AppValidationException;
	
	public boolean getBySortAndClassKey(String classId, Integer sort, String classKey) throws AppValidationException;
	
	public boolean getByDescriptionAndClassKey(String classId, String description, String classKey) throws AppValidationException;
	
	public boolean getByClasskey(String classKey) throws AppValidationException;
	
	public Integer getLastSort(Integer initSort) throws AppValidationException;
	
	public void deleteBySort(Integer sort) throws AppValidationException;
}
