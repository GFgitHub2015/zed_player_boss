package com.zed.service.system.operationlog.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zed.dao.system.operationlog.impl.OperationLogDaoImpl;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.page.Page;

/**
 * 操作日志业务
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

	@Autowired
	private OperationLogDaoImpl<OperationLog> operationLogDao;
	
	/**
	 * 根据用户ID查询
	 */
	@Override
	public OperationLog findById(String id) throws AppValidationException {
		return operationLogDao.findById(id);
	}

	/**
	 * 查询所有用户
	 */
	@Override
	public Page<OperationLog> findByPage(Page<OperationLog> page) throws AppValidationException {
		return operationLogDao.findByPage(page);
	}
	
	/**
	 * 新增用户
	 */
	@Override
	public void add(OperationLog operationLog) throws AppValidationException {
		operationLogDao.add(operationLog);
	}

	@Override
	public void addBatch(List<OperationLog> operationLogs) throws AppValidationException {
		operationLogDao.addBatch(operationLogs);		
	}

}
