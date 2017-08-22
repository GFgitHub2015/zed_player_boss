package com.zed.service.player.playeruser.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.util.StringUtil;
import com.zed.dao.player.playeruser.PlayerUserDao;
import com.zed.domain.account.account.Account;
import com.zed.domain.player.playeruser.PlayerUser;
import com.zed.domain.player.spaceapply.PlayerSpaceApply;
import com.zed.exception.AppValidationException;
import com.zed.service.account.account.AccountService;
import com.zed.service.player.es.PlayerUserHotElasticsearchService;
import com.zed.service.player.logicalfile.PlayerLogicalFileService;
import com.zed.service.player.playeruser.HotPlayerUserService;
import com.zed.service.player.playeruser.PlayerUserService;
import com.zed.service.player.spaceapply.PlayerSpaceApplyService;
import com.zed.system.page.Page;
import com.zed.util.ConstantType;

@Service("playerUserService")
public class PlayerUserServiceImpl implements PlayerUserService {
	
	@Resource(name="playerUserDao")
	private PlayerUserDao playerUserDao;
	
	@Resource(name="accountService")
	private AccountService accountService;
	
	@Resource(name="playerLogicalFileService")
	private PlayerLogicalFileService playerLogicalFileService;

	@Resource(name="hotPlayerUserService")
	private HotPlayerUserService hotPlayerUserService;
	@Resource(name="playerUserHotElasticsearchService")
	private PlayerUserHotElasticsearchService playerUserHotElasticsearchService;
	
	@Resource(name="playerSpaceApplyService")
	private PlayerSpaceApplyService playerSpaceApplyService;
	
	@Override
	public void add(PlayerUser playerUser) throws AppValidationException {
		playerUserDao.add(playerUser);
	}

	@Override
	public void update(PlayerUser playerUser,String curUserId) throws AppValidationException {
		//禁用账号,同时禁用资源和热门用户列表
		PlayerUser old = this.findById(playerUser.getUserId());
		int oldStatus = old.getStatus();//原有的状态
		int curStatus = playerUser.getStatus();//现在的状态
		
		playerUserDao.update(playerUser);
		
		//修改热门用户（修改ES热门用户记录）
		/*PlayerUserHot puh = playerUserHotElasticsearchService.findByUserId(playerUser.getUserId());
		if (puh != null) {
			if (playerUser.getShareCount()!=null&&playerUser.getShareCount()>0) {
				puh.setStatus(playerUser.getStatus());
				puh.setFollowMeCount(playerUser.getFollowMeCount());
				puh.setiFollowCount(playerUser.getiFollowCount());
				puh.setShareCount(playerUser.getShareCount().longValue());
				puh.setUserRoleStatus(playerUser.getUserRoleStatus());
				playerUserHotElasticsearchService.add(puh);
			} else {
				playerUserHotElasticsearchService.deleteByIds(puh.getUserId());
			}
		}*/
		
		//如果有变更才修改
		if(oldStatus!=curStatus){
			//启用资源
			if(curStatus==1){
				hotPlayerUserService.updateStatus(playerUser.getUserId(), curUserId, ConstantType.HotUserStatus.ENABLE);
			}else if (curStatus ==0 ){//禁用资源
				hotPlayerUserService.updateStatus(playerUser.getUserId(), curUserId, ConstantType.HotUserStatus.DISABLE_ALL);
			}
			
		}
		
		//修改站长申请记录状态
		if (playerUser.getUserRoleStatus()==2&&playerUser.getStatus()==1) {
			playerSpaceApplyService.updateByUserIdWithStatus(playerUser.getUserId(), 1);
		}
		
		
	}

	@Override
	public void delete(String... userIds) throws AppValidationException {
		playerUserDao.delete(userIds);
		//删除热门用户
		playerUserHotElasticsearchService.deleteByIds(userIds);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		Page<HashMap> listByPage = findPlayerUserByPage(page);
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
                        	hashMap.put("icon_url", account.getAreaCode());
                        }
                        PlayerSpaceApply psa = playerSpaceApplyService.findByUserId(userId);
                        if (psa != null) {
                        	hashMap.put("apply_status", psa.getStatus());
						}else{
							hashMap.put("apply_status", -1);
						}
                    }
                }
            	listByPage.setResult(result);
            }
        }
		return listByPage;
	}

	@Override
	public Page<HashMap> findPlayerUserByPage(Page<HashMap> page) throws AppValidationException {
		return playerUserDao.findByPage(page);
	}

	@Override
	public PlayerUser findById(String userId) throws AppValidationException {
		PlayerUser playerUser = (PlayerUser) playerUserDao.findById(userId);
		if (playerUser != null) {
            Account account = accountService.findById(userId);
            if(null != account){
            	playerUser.setNickName(account.getNickName());
            	playerUser.setPhone(account.getPhone());
            	playerUser.setIconUrl(account.getIconUrl());
            	playerUser.setAreaCode(account.getAreaCode());
            }
		}
		return playerUser;
	}

	@Override
	public void updateShareFileStatus(String userId, String status) throws AppValidationException {
		playerLogicalFileService.updateShareFileStatus(userId, Integer.valueOf(status));
	}

	@Override
	public List<PlayerUser> findAllHotUser() throws AppValidationException {
		return playerUserDao.findAllHotUser();
	}

}
