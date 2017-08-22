package com.zed.service.server.serverinfo;

import java.util.HashMap;

import com.zed.domain.server.serverinfo.ServerInfo;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface ServerInfoService {

	public ServerInfo findById(String serverInfoId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(ServerInfo serverInfo) throws AppValidationException;
	
	public void update(ServerInfo serverInfo) throws AppValidationException;
	
	public void delete(String[] serverInfoId) throws AppValidationException;
}
