package com.zed.service.player.playerhotuser.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.exception.AppErrorException;
import com.zed.dao.player.playerhotuser.IPlayerRelationHotUserDao;
import com.zed.domain.account.account.Account;
import com.zed.domain.params.YoutubeParams;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.domain.player.playerhotuser.PlayerRelationHotUser;
import com.zed.domain.player.playerhotuser.YouTubeHotUser;
import com.zed.exception.AppValidationException;
import com.zed.service.account.account.AccountService;
import com.zed.service.player.logicalfile.PlayerLogicalFileService;
import com.zed.service.player.playerhotuser.IPlayerRelationHotUserService;
import com.zed.service.player.playeruser.PlayerUserService;
import com.zed.service.player.youtube.YouTuBeService;
import com.zed.system.page.Page;
import com.zed.system.page.YouTubePage;

@Service("playerRelationHotUserService")
public class PlayerRelationHotUserServiceImpl implements IPlayerRelationHotUserService {

	@Resource(name="playerRelationHotUserDao")
	private IPlayerRelationHotUserDao playerRelationHotUserDao;
	@Resource(name="accountService")
	private AccountService accountService;
	@Resource(name="playerUserService")
	private PlayerUserService playerUserService;
	@Resource(name="playerLogicalFileService")
	private PlayerLogicalFileService playerLogicalFileService;
	@Resource(name="youTuBeService")
	private YouTuBeService youTuBeService;
	
	@Override
	public Page<PlayerRelationHotUser> findByPage(Page<PlayerRelationHotUser> page) throws AppValidationException {
		try {
			page = playerRelationHotUserDao.findByPage(page);
			if(null != page ) {
				List<PlayerRelationHotUser> result = page.getResult();
				if(null != result && result.size() > 0) {
					for (PlayerRelationHotUser playerRelationHotUser : result) {
	                	String userId = playerRelationHotUser.getUserId();
	                	//当为GBox用户时需要关联查询Account基本信息
	                	if(playerRelationHotUser.getOrigin() == 0) {
	                		Account account = accountService.findById(userId);
	 	                    if(null != account) {
	 	                    	playerRelationHotUser.setIconUrl(account.getIconUrl());
	 	                    	playerRelationHotUser.setTitle(account.getNickName());
	 	                    }
	                	}
	                }
					page.setResult(result);
				} else {
					page.setResult(new ArrayList<PlayerRelationHotUser>());
				}
			} 
		} catch (Exception e) {
		}
		return page;
	}

	@Override
	public void updateStatusBatchByUserId(String[] userIds, Integer status) throws AppValidationException {
		try {
			if(null == status) {
				throw new AppErrorException("批量启用禁用热门用户修改状态值有误");
			}
			if(null == userIds || userIds.length == 0 ) {
				throw new AppErrorException("用户数量为空");
			}
			List<Account> accounts = new ArrayList<Account>();
			List<PlayerLogicalFile> playerLogicalFiles = new ArrayList<PlayerLogicalFile>();
			List<PlayerRelationHotUser> playerRelationHotUsers = new ArrayList<PlayerRelationHotUser>();
			for (String userId : userIds) {
				PlayerLogicalFile playerLogicalFile = new PlayerLogicalFile();
				PlayerRelationHotUser playerHotUser = new PlayerRelationHotUser();
				if(status == PlayerRelationHotUser.Status.DISABLE.getStatus()) {  //禁用
					playerHotUser.setUserId(userId);
					playerHotUser.setStatus(PlayerRelationHotUser.Status.DISABLE.getStatus());
					
					playerLogicalFile.setUserId(userId);
					playerLogicalFile.setStatus(PlayerRelationHotUser.Status.DISABLE.getStatus());
				} else if(status ==  PlayerRelationHotUser.Status.ENABLE.getStatus()) {  //启用所有
					playerHotUser.setUserId(userId);
					playerHotUser.setStatus(PlayerRelationHotUser.Status.ENABLE.getStatus());
					
					playerLogicalFile.setUserId(userId);
					playerLogicalFile.setStatus(PlayerRelationHotUser.Status.ENABLE.getStatus());
					
					//更新账户
					Account account = new Account();
					account.setUserId(userId);
					account.setStatus(PlayerRelationHotUser.Status.ENABLE.getStatus());
					accounts.add(account);
				} else if (status ==  PlayerRelationHotUser.Status.DISABLE_ALL.getStatus()) {  //禁用所有
					playerHotUser.setUserId(userId);
					playerHotUser.setStatus(PlayerRelationHotUser.Status.DISABLE_ALL.getStatus());
					
					playerLogicalFile.setUserId(userId);
					playerLogicalFile.setStatus(PlayerRelationHotUser.Status.DISABLE.getStatus());
					
					//更新账户
					Account account = new Account();
					account.setUserId(userId);
					account.setStatus(PlayerRelationHotUser.Status.DISABLE.getStatus());
					accounts.add(account);
				}
				playerRelationHotUsers.add(playerHotUser);
				playerLogicalFiles.add(playerLogicalFile);
			}
			playerRelationHotUserDao.updateBatch(playerRelationHotUsers);
			playerLogicalFileService.updateStatusBatchByUserId(playerLogicalFiles);
			accountService.updateBatchByUserId(accounts);
		} catch (Exception e) {
		}
	}

	@Override
	public YouTubePage<YouTubeHotUser> findYouTubeUserByName(YoutubeParams youtubeParam) throws AppValidationException {
		return youTuBeService.findHotUserByName(youtubeParam);
	}

	@Override
	public Long getLastSort() throws AppValidationException {
		PlayerRelationHotUser playerHotUser_ = playerRelationHotUserDao.getLastSort();
    	if(null != playerHotUser_) {
    		return playerHotUser_.getSort() + 1L;
    	} else {
    		return 1L;
    	}
	}

	@Override
	public void addHotUser(PlayerRelationHotUser playerHotUser) {
		playerRelationHotUserDao.add(playerHotUser);
	}

	@Override
	public PlayerRelationHotUser findByUserId(String userId) {
		return (PlayerRelationHotUser) playerRelationHotUserDao.findByUserId(userId);
	}

	@Override
	public void deleteByUId(String uId) {
		playerRelationHotUserDao.delete(uId);
	}
	
	/**
	 * @date : 2017年07月03日 上午11:57:28
	 * @param userId  需要更改的热门用户Id
	 * @param sort 需要修改的展示位
	 * @author : X.Long
	 * @description : 修改热门用户展示位，并且保证修改之后的展示位是连续的
	*/
	@Override
	public void updateSortByUserId(String userId, Long sort) {
		try {
			PlayerRelationHotUser playerHotUser = new PlayerRelationHotUser();
			playerHotUser.setUserId(userId);
			playerHotUser.setSort(sort);
			List<PlayerRelationHotUser> playerRelationHotUsers = playerRelationHotUserDao.findBySort(sort);
			if(playerRelationHotUsers != null  && !playerRelationHotUsers.isEmpty()) {
				for(PlayerRelationHotUser playerHotUser_ : playerRelationHotUsers) {
					if(playerHotUser_.getSort() - sort > 1) {
						break;
					} else {
						sort = sort + 1;
						playerHotUser_.setSort(sort);
					}
				}
			} else {
				playerRelationHotUsers =  new ArrayList<PlayerRelationHotUser>();
			}
			playerRelationHotUsers.add(playerHotUser);
			playerRelationHotUserDao.updateSortByUserId(playerRelationHotUsers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
