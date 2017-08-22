package com.zed.service.player.hotkeyword;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.hotkeyword.PlayerHotKeyword;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerHotKeyWordService {
	
	public PlayerHotKeyword findById(String hotKeyWordId) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	//获取所有的地区编号
	public List<String> findAllAreaCode() throws AppValidationException;
	
}
