package com.zed.dao;

import java.util.Collection;

import com.zed.exception.AppValidationException;


public interface BaseDao<T>{

	/**
	 * 添加
	 */
	public void add(T object)throws AppValidationException;
	public void addBatch(Collection<T> list)throws AppValidationException;

	/**
	 * 修改
	 */
	public void update(T object) throws AppValidationException;
	/**
	 * 批量修改
	 */
	public void updateBatch(Collection<T> list) throws AppValidationException;
	/**
	 * 根据ID查询
	 */
	public T findById(String id) throws AppValidationException;

	/**
	 * 删除
	 */
	public void delete(String[] id) throws AppValidationException;
	
	public void delete(String id) throws AppValidationException;
	
	
	/**
	 * 获取数据库表的序列
	 */
	public String updateSequence();
}
