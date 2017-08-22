package com.zed.dao.player.videoclass;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.videoclass.PlayerVideoClasses;
import com.zed.exception.AppValidationException;

public interface PlayerVideoClassesDao<T extends Serializable> extends PageDao<PlayerVideoClasses> {
	
	public List<PlayerVideoClasses> findBySortAndClassKey(Integer sort, String classKey) throws AppValidationException;
	
	public List<PlayerVideoClasses> findByDescriptionAndClassKey(String description, String classKey) throws AppValidationException;
	
	public List<PlayerVideoClasses> findBySortBy(Integer sort, String sorting, String comperatorStr) throws AppValidationException;
	
	public PlayerVideoClasses findByClasskey(String classKey) throws AppValidationException;
	
	public PlayerVideoClasses getLastSort() throws AppValidationException;
	
	public void deleteFromDisk(String[] ids) throws AppValidationException;
	
}
