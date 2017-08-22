package com.zed.service.server.modelinfo;

import java.util.HashMap;

import com.zed.domain.server.modelinfo.PlayerModelInfo;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerModelInfoService {
	
	public PlayerModelInfo findById(String model) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerModelInfo playerModelInfo) throws AppValidationException;
	
	public void update(PlayerModelInfo playerModelInfo) throws AppValidationException;
	
	public void delete(String[] models) throws AppValidationException;
	
	public Boolean updateAll() throws AppValidationException;
	
	public Boolean isExist(String model) throws AppValidationException;

}
