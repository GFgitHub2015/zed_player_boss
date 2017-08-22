package com.zed.service.player.site;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.site.PlayerSiteNavigate;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerSiteNavigateService {
	
	public PlayerSiteNavigate findById(String playerSiteNavigateId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerSiteNavigate playerSiteNavigate) throws AppValidationException;
	
	public void update(PlayerSiteNavigate playerSiteNavigate) throws AppValidationException;
	
	public void delete(String[] playerSiteNavigateIds) throws AppValidationException;
	//修改状态
	public void updateStatus(PlayerSiteNavigate playerSiteNavigate)  throws AppValidationException;
	
	public List<PlayerSiteNavigate> findAll() throws AppValidationException;


}
