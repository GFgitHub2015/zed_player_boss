package com.zed.dao.system.operationlog.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.zed.dao.system.base.abstractdao.AbstractPageDao;
import com.zed.dao.system.operationlog.OperationLogDao;
import com.zed.domain.system.OperationLog;


@Repository
public class OperationLogDaoImpl<T extends Serializable> extends AbstractPageDao<OperationLog> implements OperationLogDao<T>{
	
}
