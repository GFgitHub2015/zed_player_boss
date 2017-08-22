package com.zed.service.player.webmaster.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.util.StringUtil;
import com.zed.domain.account.account.Account;
import com.zed.domain.player.video.PlayerVideoSourceFile;
import com.zed.service.account.account.AccountService;
import com.zed.service.player.player.PlayerVideoSourceFileService;
import com.zed.service.player.playeruser.PlayerUserService;
import com.zed.service.player.webmaster.WebMasterStatService;
import com.zed.system.page.Page;

@Service("webMasterStatService")
public class WebMasterStatServiceImpl implements WebMasterStatService{
	
	@Resource(name="accountService")
	private AccountService accountService;
	@Resource(name="playerUserService")
	private PlayerUserService playerUserService;
	@Resource(name="playerVideoSourceFileService")
	private PlayerVideoSourceFileService playerVideoSourceFileService;

	@Override
	public Page<HashMap> findTrancodingStatByPage(Page<HashMap> page) {
		Page<HashMap> listByPage = playerUserService.findPlayerUserByPage(page);
		if(null != listByPage){
            List<HashMap> result = listByPage.getResult();
            if(null != result){
            	for (HashMap hashMap : result) {
                    if(!StringUtil.isEmpty(hashMap.get("user_id"))){
                    	String userId = (String) hashMap.get("user_id");
                        Account account = accountService.findById(userId);
                        if(null != account){
                        	hashMap.put("nick_name", account.getNickName());
                        	hashMap.put("phone", account.getPhone());
                        	hashMap.put("icon_url", account.getIconUrl());
                        	hashMap.put("area_code", account.getAreaCode());
                        }
                        Map<Integer, Object> statMap = playerVideoSourceFileService.findTransCodeStatByUserId(userId);
                        hashMap.put("wait_transcoding", statMap.get(PlayerVideoSourceFile.Status.STATUS_WAIT_TRANSCODING.getStatus()));
                		hashMap.put("transcoding", statMap.get(PlayerVideoSourceFile.Status.STATUS_TRANSCODING.getStatus()));
                		hashMap.put("transcode_success", statMap.get(PlayerVideoSourceFile.Status.STATUS_TRANSCODE_SUCCESS.getStatus()));
                		hashMap.put("transcode_faild", statMap.get(PlayerVideoSourceFile.Status.STATUS_TRANSCODE_FAILD.getStatus()));
                    }
                }
            	listByPage.setResult(result);
            }
        }
		
		return listByPage;
	}
	
}
