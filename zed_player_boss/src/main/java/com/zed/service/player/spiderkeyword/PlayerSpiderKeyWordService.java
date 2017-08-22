package com.zed.service.player.spiderkeyword;

import java.util.HashMap;

import com.zed.domain.player.spiderkeyword.PlayerSpiderKeyWord;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerSpiderKeyWordService {
	
	public PlayerSpiderKeyWord findById(String keyWordId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerSpiderKeyWord playerSpiderkeyWord) throws AppValidationException;
	
	public void update(PlayerSpiderKeyWord playerSpiderkeyWord) throws AppValidationException;
	
	public void delete(String[] keyWordIds) throws AppValidationException;
	//修改状态
	public void updateStatus(PlayerSpiderKeyWord playerSpiderkeyWord)  throws AppValidationException;

}
