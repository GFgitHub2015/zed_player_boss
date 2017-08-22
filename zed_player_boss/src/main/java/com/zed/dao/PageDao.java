package com.zed.dao;

import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PageDao<T> extends BaseDao<T>{
	
	public Page<T> findByPage(Page<T> page) throws AppValidationException;
	
}