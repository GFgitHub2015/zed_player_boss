package com.zed.service.player.videoclass;

import java.util.HashMap;

import com.zed.domain.player.videoclass.PlayerVideoClasses;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerVideoClassesService {

	public PlayerVideoClasses findById(String classId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerVideoClasses playerVideoClasses) throws AppValidationException;
	
	public void update(PlayerVideoClasses playerVideoClasses) throws AppValidationException;
	
	public void delete(String[] classIds) throws AppValidationException;
	
	public boolean getBySortAndClassKey(String classId, Integer sort, String classKey) throws AppValidationException;
	
	public boolean getByDescriptionAndClassKey(String classId, String description, String classKey) throws AppValidationException;
	
	public boolean getByClasskey(String classKey) throws AppValidationException;
	
	public Integer getLastSort(Integer initSort) throws AppValidationException;
	
	public void deleteBySort(Integer sort) throws AppValidationException;
}
