package com.zed.service.system.operationlog;

import java.util.List;

import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

/**
 * 操作日志接口业务
 */
public interface OperationLogService{

	/**
	 * 根据操作日志记录ID查询
	 */
	public OperationLog findById(String id) throws AppValidationException;

	/**
	 * 查询所有操作日志记录
	 */
	public Page<OperationLog> findByPage(Page<OperationLog> page) throws AppValidationException;

	/**
	 * 新增操作日志记录
	 */
	public void add(OperationLog operationLog) throws AppValidationException;
	/**
	 * 批量新增操作日志记录
	 */
	public void addBatch(List<OperationLog> operationLogs) throws AppValidationException;
}
