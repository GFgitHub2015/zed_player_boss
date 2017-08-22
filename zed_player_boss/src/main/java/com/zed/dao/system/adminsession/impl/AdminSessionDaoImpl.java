package com.zed.dao.system.adminsession.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.system.adminsession.AdminSessionDao;
import com.zed.dao.system.base.abstractdao.AbstractPageDao;
import com.zed.domain.system.AdminSession;

@Repository
public class AdminSessionDaoImpl<T> extends AbstractPageDao<AdminSession> implements AdminSessionDao{
	
}
