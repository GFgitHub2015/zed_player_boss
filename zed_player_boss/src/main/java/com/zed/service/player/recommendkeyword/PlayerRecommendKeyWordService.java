package com.zed.service.player.recommendkeyword;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.recommendkeyword.PlayerRecommendKeyWord;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerRecommendKeyWordService {
	
	public PlayerRecommendKeyWord findById(String recommendKeyWordId) throws AppValidationException;
	
	public List<PlayerRecommendKeyWord> findById(String []recommendKeyWordIds) throws AppValidationException;
	
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;

	public void add(PlayerRecommendKeyWord hotKeyWord) throws AppValidationException;
	public void add(List<PlayerRecommendKeyWord> hotKeyWordList) throws AppValidationException;
	
	public void update(PlayerRecommendKeyWord hotKeyWord) throws AppValidationException;
	
	public void delete(String[] recommendKeyWordIds) throws AppValidationException;
	//修改状态
	public void updateStatus(PlayerRecommendKeyWord recommendKeyWord)  throws AppValidationException;
	//批量修改状态
	public void updateStatus(List<PlayerRecommendKeyWord> recommendKeyWordList)  throws AppValidationException;
	//获取所有的地区编号
	public List<String> findAllAreaCode() throws AppValidationException;
	//判断推荐热词是否存在
	public Boolean exist(String keyword, String areaCode) throws AppValidationException;
	

}
