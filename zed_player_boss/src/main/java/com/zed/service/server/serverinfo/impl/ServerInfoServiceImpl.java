package com.zed.service.server.serverinfo.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zed.dao.server.serverinfo.ServerInfoDao;
import com.zed.domain.server.serverinfo.ServerInfo;
import com.zed.exception.AppValidationException;
import com.zed.service.server.serverinfo.ServerInfoService;
import com.zed.system.page.Page;

@Service
public class ServerInfoServiceImpl implements ServerInfoService {
	
	@Autowired
	private ServerInfoDao serverInfoDao;
	

	@Override
	public ServerInfo findById(String serverInfoId) throws AppValidationException {
		return (ServerInfo) serverInfoDao.findById(serverInfoId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return serverInfoDao.findByPage(page);
	}

	@Override
	public void add(ServerInfo serverInfo) throws AppValidationException {
		serverInfoDao.add(serverInfo);		
	}

	@Override
	public void update(ServerInfo serverInfo) throws AppValidationException {
		serverInfoDao.update(serverInfo);		
	}

	@Override
	public void delete(String[] serverInfoId) throws AppValidationException {
		serverInfoDao.delete(serverInfoId);		
	}
	
}
