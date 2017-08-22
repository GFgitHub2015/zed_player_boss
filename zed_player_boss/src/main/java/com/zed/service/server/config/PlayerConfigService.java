package com.zed.service.server.config;

import java.util.HashMap;

import com.zed.domain.server.config.PlayerConfig;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerConfigService {
	
	public PlayerConfig findById(String id) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerConfig playerConfig) throws AppValidationException;
	
	public void update(PlayerConfig playerConfig) throws AppValidationException;
	
	public void delete(String[] ids) throws AppValidationException;
	
	public Boolean isExist(String version, String packageName) throws AppValidationException;

	
}
