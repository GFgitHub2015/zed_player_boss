package com.zed.service.iosplayer.playerhotuser.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.common.exception.AppErrorException;
import com.zed.dao.iosplayer.playerhotuser.IosPlayerRelationHotUserDao;
import com.zed.domain.account.account.Account;
import com.zed.domain.iosplayer.playerhotuser.IosPlayerRelationHotUser;
import com.zed.domain.params.YoutubeParams;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.domain.player.playerhotuser.YouTubeHotUser;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.account.account.AccountService;
import com.zed.service.iosplayer.playerhotuser.IosPlayerRelationHotUserService;
import com.zed.service.player.logicalfile.PlayerLogicalFileService;
import com.zed.service.player.playeruser.PlayerUserService;
import com.zed.service.player.youtube.YouTuBeService;
import com.zed.system.page.Page;
import com.zed.system.page.YouTubePage;

@Service("iosPlayerRelationHotUserService")
public class IosPlayerRelationHotUserServiceImpl implements IosPlayerRelationHotUserService {

	@Resource(name="iosPlayerRelationHotUserDao")
	private IosPlayerRelationHotUserDao playerRelationHotUserDao;
	@Resource(name="accountService")
	private AccountService accountService;
	@Resource(name="playerUserService")
	private PlayerUserService playerUserService;
	@Resource(name="playerLogicalFileService")
	private PlayerLogicalFileService playerLogicalFileService;
	@Resource(name="youTuBeService")
	private YouTuBeService youTuBeService;
	
	@Override
	public Page<IosPlayerRelationHotUser> findByPage(Page<IosPlayerRelationHotUser> page) throws AppValidationException {
		try {
			page = playerRelationHotUserDao.findByPage(page);
			StringBuffer channelsStr = new StringBuffer();
			if(null != page ) {
				List<IosPlayerRelationHotUser> result = page.getResult();
				if(null != result && result.size() > 0) {
					for (IosPlayerRelationHotUser playerRelationHotUser : result) {
	                	String userId = playerRelationHotUser.getUserId();
	                	channelsStr.append(userId).append(",");
	                	//当为GBox用户时需要关联查询Account基本信息
	                	if(playerRelationHotUser.getOrigin() == 0) {
	                		Account account = accountService.findById(userId);
	 	                    if(null != account) {
	 	                    	playerRelationHotUser.setIconUrl(account.getIconUrl());
	 	                    }
	                	}
	                }
					
					Log.getLogger(this.getClass()).info("查询订阅数开始．．．．．．．．．．．．．．．");
					List<Map<String, Object>> channelsMap = new ArrayList<Map<String, Object>>();
					//根据channelsId查询出对应的订阅数
					channelsMap = youTuBeService.findChannelsBySubscriptionsForIOS(channelsStr.toString());
					Log.getLogger(this.getClass()).info("查询订阅数结束．．．．．．．．．．．．．．．");
					for (IosPlayerRelationHotUser hotUserForChannel : result) {
						for(Map<String, Object> mp : channelsMap){
							String channelId = mp.get("channelId").toString();
							String userId = hotUserForChannel.getUserId();
							if(channelId.equals(userId)){//如果channelId一致，则取map中的订阅数(subscriberCount)
								hotUserForChannel.setSubscriberCount(mp.get("subscriberCount").toString());
								break;
							}
						}
					}
					page.setResult(result);
				} else {
					page.setResult(new ArrayList<IosPlayerRelationHotUser>());
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
			List<IosPlayerRelationHotUser> playerRelationHotUsers = new ArrayList<IosPlayerRelationHotUser>();
			for (String userId : userIds) {
				PlayerLogicalFile playerLogicalFile = new PlayerLogicalFile();
				IosPlayerRelationHotUser playerHotUser = new IosPlayerRelationHotUser();
				if(status == IosPlayerRelationHotUser.Status.DISABLE.getStatus()) {  //禁用
					playerHotUser.setUserId(userId);
					playerHotUser.setStatus(IosPlayerRelationHotUser.Status.DISABLE.getStatus());
					
					playerLogicalFile.setUserId(userId);
					playerLogicalFile.setStatus(IosPlayerRelationHotUser.Status.DISABLE.getStatus());
				} else if(status ==  IosPlayerRelationHotUser.Status.ENABLE.getStatus()) {  //启用所有
					playerHotUser.setUserId(userId);
					playerHotUser.setStatus(IosPlayerRelationHotUser.Status.ENABLE.getStatus());
					
					playerLogicalFile.setUserId(userId);
					playerLogicalFile.setStatus(IosPlayerRelationHotUser.Status.ENABLE.getStatus());
					
					//更新账户
					Account account = new Account();
					account.setUserId(userId);
					account.setStatus(IosPlayerRelationHotUser.Status.ENABLE.getStatus());
					accounts.add(account);
				} else if (status ==  IosPlayerRelationHotUser.Status.DISABLE_ALL.getStatus()) {  //禁用所有
					playerHotUser.setUserId(userId);
					playerHotUser.setStatus(IosPlayerRelationHotUser.Status.DISABLE_ALL.getStatus());
					
					playerLogicalFile.setUserId(userId);
					playerLogicalFile.setStatus(IosPlayerRelationHotUser.Status.DISABLE.getStatus());
					
					//更新账户
					Account account = new Account();
					account.setUserId(userId);
					account.setStatus(IosPlayerRelationHotUser.Status.DISABLE.getStatus());
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
		return youTuBeService.findHotUserByNameForIOS(youtubeParam);
	}

	@Override
	public Long getLastSort() throws AppValidationException {
		IosPlayerRelationHotUser playerHotUser_ = playerRelationHotUserDao.getLastSort();
    	if(null != playerHotUser_) {
    		return playerHotUser_.getSort() + 1L;
    	} else {
    		return 1L;
    	}
	}

	@Override
	public void addHotUser(IosPlayerRelationHotUser playerHotUser) {
		playerRelationHotUserDao.add(playerHotUser);
	}

	@Override
	public IosPlayerRelationHotUser findByUserId(String userId) {
		return (IosPlayerRelationHotUser) playerRelationHotUserDao.findByUserId(userId);
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
			IosPlayerRelationHotUser playerHotUser = new IosPlayerRelationHotUser();
			playerHotUser.setUserId(userId);
			playerHotUser.setSort(sort);
			List<IosPlayerRelationHotUser> playerRelationHotUsers = playerRelationHotUserDao.findBySort(sort);
			if(playerRelationHotUsers != null  && !playerRelationHotUsers.isEmpty()) {
				for(IosPlayerRelationHotUser playerHotUser_ : playerRelationHotUsers) {
					if(playerHotUser_.getSort() - sort > 1) {
						break;
					} else {
						sort = sort + 1;
						playerHotUser_.setSort(sort);
					}
				}
			} else {
				playerRelationHotUsers =  new ArrayList<IosPlayerRelationHotUser>();
			}
			playerRelationHotUsers.add(playerHotUser);
			playerRelationHotUserDao.updateSortByUserId(playerRelationHotUsers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
