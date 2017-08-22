package com.zed.dao.system.operationlog;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.system.OperationLog;

public interface OperationLogDao<T extends Serializable> extends PageDao<OperationLog>{
	
}
