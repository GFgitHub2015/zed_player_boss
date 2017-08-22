package com.zed.dao.iosplayer.videoclass;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.iosplayer.videoclass.IosPlayerVideoClasses;
import com.zed.exception.AppValidationException;

public interface IosPlayerVideoClassesDao<T extends Serializable> extends PageDao<IosPlayerVideoClasses> {
	
	public List<IosPlayerVideoClasses> findBySortAndClassKey(Integer sort, String classKey) throws AppValidationException;
	
	public List<IosPlayerVideoClasses> findByDescriptionAndClassKey(String description, String classKey) throws AppValidationException;
	
	public List<IosPlayerVideoClasses> findBySortBy(Integer sort, String sorting, String comperatorStr) throws AppValidationException;
	
	public IosPlayerVideoClasses findByClasskey(String classKey) throws AppValidationException;
	
	public IosPlayerVideoClasses getLastSort() throws AppValidationException;
	
	public void deleteFromDisk(String[] ids) throws AppValidationException;
	
}
