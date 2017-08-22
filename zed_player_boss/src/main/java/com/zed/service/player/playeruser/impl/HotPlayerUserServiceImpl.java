package com.zed.service.player.playeruser.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.common.exception.AppErrorException;
import com.zed.common.util.DateUtil;
import com.zed.common.util.StringUtil;
import com.zed.dao.player.playeruser.IHotPlayerUserDao;
import com.zed.domain.account.account.Account;
import com.zed.domain.player.playeruser.HotPlayerUser;
import com.zed.domain.player.playeruser.PlayerUser;
import com.zed.domain.player.playeruser.PlayerUserHot;
import com.zed.domain.player.playeruserhotsort.PlayerUserHotSort;
import com.zed.redis.player.playeruser.IHotPlayerUserRedisDao;
import com.zed.service.account.account.AccountService;
import com.zed.service.player.es.PlayerUserHotElasticsearchService;
import com.zed.service.player.es.PlayerVideoElasticsearch2Service;
import com.zed.service.player.logicalfile.PlayerLogicalFileService;
import com.zed.service.player.playeruser.HotPlayerUserService;
import com.zed.service.player.playeruser.PlayerUserHotSortService;
import com.zed.service.player.playeruser.PlayerUserService;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;
import com.zed.util.ConstantType;

/**
 * @description : 热门用户
*/
@Service("hotPlayerUserService")
public class HotPlayerUserServiceImpl implements HotPlayerUserService{

	@Resource(name="hotPlayerUserDaoImpl")
	private IHotPlayerUserDao<HotPlayerUser> hotPlayerUserDao;

	@Resource(name="accountService")
	private AccountService accountService;

	@Resource(name="hotPlayerUserRedisDaoImpl")
	private IHotPlayerUserRedisDao hotPlayerUserRedisDao;

	@Resource(name="playerUserService")
	private PlayerUserService playerUserService;
	
	@Resource(name="playerLogicalFileService")
	private PlayerLogicalFileService playerLogicalFileService;
	
	@Resource(name="playerVideoElasticsearch2Service")
	private PlayerVideoElasticsearch2Service playerVideoElasticsearch2Service;
	
	@Resource(name="playerUserHotSortService")
	private PlayerUserHotSortService playerUserHotSortService;
	
	@Resource(name="playerUserHotElasticsearchService")
	private PlayerUserHotElasticsearchService playerUserHotElasticsearchService;
	/**
	 * @date : 2017年2月10日 下午4:46:13
	 * @author : Iris.Xiao
	 * @param page
	 * @return
	 * @description : 热门用户列表
	*/
	public Page<HashMap> findByPage(Page<HashMap> page){
		if (page != null) {
			if (page.getParamsMap() != null) {
				Map<String, Object> param = page.getParamsMap();
//				Long totalCount = 0l;
				List<HashMap> hashMapList = new LinkedList<HashMap>();
				if (param.get("countryCode") != null) {
					page = playerUserHotSortService.findByPage(page);
					
					Page<HashMap> newPage = new Page<HashMap>();
					newPage.setParamsMap(page.getParamsMap());
					newPage.setPageSize(page.getPageSize()*page.getPageNo());
					newPage.setPageNo(1);
					newPage = playerUserHotSortService.findByPage(newPage);
					List<HashMap> newResultSort = newPage.getResult();
					
					Long totalCount = newPage.getTotalCount();
					Set<String> idSet = new HashSet<String>();
					for (HashMap hashMap : newResultSort) {
						idSet.add(hashMap.get("user_id").toString());
					}
					String userIdStr = "";
					StringBuilder sb = new StringBuilder();
					if (!idSet.isEmpty()) {
						for (String uid : idSet) {
							sb.append(uid);
							sb.append(",");
						}
					}
					if (StringUtils.isNotBlank(sb.toString())) {
						userIdStr=sb.toString().substring(0, sb.toString().lastIndexOf(","));
					}
					if (StringUtils.isNotBlank(userIdStr)) {
						page.getParamsMap().put("userIdNotToMatch", userIdStr);
					}
					
					
					List<HashMap> resultSort = page.getResult();
					Long resultSize = (long) resultSort.size();
					Long pageSize = page.getPageSize()-resultSize;
					
					if (pageSize>0) {
						page.setPageSize(pageSize);
						playerUserHotElasticsearchService.findByPage(page);
						page.setTotalCount(page.getTotalCount()+totalCount);
						List<Map<String, Object>> result = page.getObjectResult();
						int index = 0;
						Map<Integer, HashMap> sortedMapObject = new HashMap<Integer, HashMap>();
						for (HashMap hmap: resultSort) {
							if (hmap.containsKey("sort")&&hmap.get("sort")!=null) {
								Integer s = Integer.valueOf(hmap.get("sort").toString())-1;
								sortedMapObject.put(s, hmap);
							}
						}
						
						for (int i = 0; i < 10&&index<result.size(); i++) {
							if (sortedMapObject.containsKey(i)) {
								HashMap hashMap = sortedMapObject.get(i);
								addMapToLinckedList(hashMapList, hashMap);
							}else{
								Map<String, Object> hm = result.get(index);
								index++;
								addMapToLinckedList(hashMapList, hm);
							}
						}
					}else{
						for (int i = 0; i < resultSort.size(); i++) {
							for (HashMap hashMap : resultSort) {
								if (hashMap.get("sort")!=null) {
									Integer sort = Integer.valueOf(hashMap.get("sort").toString())-1;
									if (sort==i) {
										addMapToLinckedList(hashMapList, hashMap);
//										resultSort.remove(hashMap);
									}
								}
							}
						}
					}
				}else{
					playerUserHotElasticsearchService.findByPage(page);
					List<Map<String, Object>> result = page.getObjectResult();
					for (int i = 0; i < result.size(); i++) {
						Map<String, Object> hm = result.get(i);
						addMapToLinckedList(hashMapList, hm);
					}
				}
				page.setResult(hashMapList);
			}
		}
		return page;
	}

	private void addMapToLinckedList(List<HashMap> hashMapList, Map<String, Object> map) {
		String userId = null;
		if (map.containsKey("userId")) {
			userId = map.get("userId").toString();
		}
		if (map.containsKey("user_id")) {
			userId = map.get("user_id").toString();
		}
		map.put("userId", userId);
		if(!StringUtil.isEmpty(userId)){
			Account account = accountService.findById(userId);
			if(null != account){
				map.put("iconUrl", account.getIconUrl());
				map.put("nickName", account.getNickName());
				map.put("phone", account.getPhone());
				if (!map.containsKey("countryCode")) {
					map.put("countryCode", account.getAreaCode());
				}
				map.put("userStatus", account.getStatus());
			}
			if (!map.containsKey("sort")) {
				PlayerUserHotSort puhs = playerUserHotSortService.findByUserId(userId);
				if (puhs!=null) {
					map.put("sort", puhs.getSort());
				}else{
					map.put("sort", null);
				}
			}
			PlayerUserHot hotPlayerUser = playerUserHotElasticsearchService.findByUserId(userId);
			if (hotPlayerUser!=null) {
				map.put("status", hotPlayerUser.getStatus());
				if (!map.containsKey("user_role_status")) {
					map.put("user_role_status", hotPlayerUser.getUserRoleStatus());
				}
				if (!map.containsKey("follow_me_count")) {
					map.put("follow_me_count", hotPlayerUser.getFollowMeCount());
				}
				if (!map.containsKey("i_follow_count")) {
					map.put("i_follow_count", hotPlayerUser.getiFollowCount());
				}
				if (!map.containsKey("share_count")) {
					map.put("share_count", hotPlayerUser.getShareCount());
				}
			}else{
				map.put("status", null);
			}
			if (!map.containsKey("userRoleStatus")) {
				map.put("userRoleStatus", map.get("user_role_status"));
			}
			if (!map.containsKey("followMeCount")) {
				map.put("followMeCount",  map.get("follow_me_count"));
			}
			if (!map.containsKey("iFollowCount")) {
				map.put("iFollowCount",  map.get("i_follow_count"));
			}
			if (!map.containsKey("shareCount")) {
				map.put("shareCount",  map.get("share_count"));
			}
			HashMap hm = new HashMap();
			hm.putAll(map);
			hashMapList.add(hm);
		}
	}
	
	/**
	 * @date : 2017年2月13日 上午10:26:37
	 * @author : Iris.Xiao
	 * @param user
	 * @description : 启用禁用热门用户
	*/
	public void updateStatus(String userId,String curUserId , ConstantType.HotUserStatus status){
		if(status==null){
			throw new AppErrorException("修改状态值有误");
		}
		if(CommUtil.isEmpty(userId)){
			throw new AppErrorException("启用禁用热门用户值userId有误");
		}
//		HotPlayerUser hotPlayerUser = hotPlayerUserDao.findById(userId);
		PlayerUserHot puh = playerUserHotElasticsearchService.findByUserId(userId);
		Integer oldStatus = null;//原有的状态
		Timestamp time = DateUtil.getCurTime();
		//修改数据库
		if(puh==null){
			PlayerUser playerUser = playerUserService.findById(userId);
			if (playerUser.getShareCount()!=null&&playerUser.getShareCount()>0) {
            	 puh = new PlayerUserHot();
            	 puh.setStatus(status.getStatus());
            	 puh.setFollowMeCount(playerUser.getFollowMeCount());
            	 puh.setiFollowCount(playerUser.getiFollowCount());
            	 puh.setShareCount(playerUser.getShareCount().longValue());
            	 puh.setUserRoleStatus(playerUser.getUserRoleStatus());
            	 playerUserHotElasticsearchService.add(puh);
			}
			/*puh = new PlayerUserHot();
			puh.setUserId(userId);
			puh.setStatus(status.getStatus());
			playerUserHotElasticsearchService.add(puh);*/
		}else{
			oldStatus = puh.getStatus();
			PlayerUser playerUser = playerUserService.findById(userId);
			if (playerUser.getShareCount()!=null&&playerUser.getShareCount()>0) {
				puh.setStatus(status.getStatus());
				puh.setFollowMeCount(playerUser.getFollowMeCount());
				puh.setiFollowCount(playerUser.getiFollowCount());
				puh.setShareCount(playerUser.getShareCount().longValue());
				puh.setUserRoleStatus(playerUser.getUserRoleStatus());
				playerUserHotElasticsearchService.add(puh);
			} else {
				playerUserHotElasticsearchService.deleteByIds(puh.getUserId());
			}
			
//			hotPlayerUser.getStatus();
//			hotPlayerUser.setCreateUser(curUserId);
//			puh.setStatus(status.getStatus());
//			playerUserHotElasticsearchService.add(puh);
		}
		//修改缓存
		if(status==ConstantType.HotUserStatus.DISABLE){//禁用
			hotPlayerUserRedisDao.disableHotUser(userId);
		}else if(status==ConstantType.HotUserStatus.ENABLE){//启用
			hotPlayerUserRedisDao.enableHotUser(userId);
			//启用资源,如果是之前是全部禁用还要启用资源
			if(oldStatus!=null&&oldStatus==ConstantType.HotUserStatus.DISABLE_ALL.getStatus()){
				//启用用户资源
				updateLogicalFileStatus(userId, ConstantType.CommonType.ONE);
				//启用登陆
//				PlayerUser pu = playerUserService.findById(userId);
//				pu.setStatus(ConstantType.CommonType.ONE.getStatus());
//				playerUserService.update(pu);
			}
			
		}else if(status==ConstantType.HotUserStatus.DISABLE_ALL){//禁用全部
			hotPlayerUserRedisDao.disableHotUser(userId);
			//禁用资源,如果没有全部禁用过,则还要禁用资源,oldStatus为空也要禁用
			if(oldStatus==null||oldStatus!=ConstantType.HotUserStatus.DISABLE_ALL.getStatus()){
				//禁用用户资源
				updateLogicalFileStatus(userId, ConstantType.CommonType.ZERO);
				//禁止登陆
//				PlayerUser pu = playerUserService.findById(userId);
//				pu.setStatus(ConstantType.CommonType.ZERO.getStatus());
//				playerUserService.update(pu);
			}
		}
		
		//修改排序记录
		PlayerUserHotSort puhs = playerUserHotSortService.findByUserId(userId);
		if (puhs != null) {
			if(status==ConstantType.HotUserStatus.DISABLE){//禁用
				String[] userArray = new String[]{userId};
				playerUserHotSortService.deleteByUserId(userArray);
			}
		}
		
	}
	
	/**
	 * @date : 2017年2月13日 上午10:26:37
	 * @author : Iris.Xiao
	 * @param user
	 * @description : 批量启用禁用热门用户
	*/
	public void updateStatusBatch(String[] userIds ,String curUserId, ConstantType.HotUserStatus status){
		if(status==null){
			throw new AppErrorException("批量启用禁用热门用户修改状态值有误");
		}
		if(userIds.length==0){
			throw new AppErrorException("批量启用禁用热门用户值userId有误");
		}
		for (String userId : userIds) {
			updateStatus(userId, curUserId, status);
		}
	}
	
	/**
	 * @date : 2017年2月14日 下午2:00:10
	 * @author : Iris.Xiao
	 * @description : 禁用用户资源或启用资源
	*/
	public void updateLogicalFileStatus(String userId,ConstantType.CommonType type){
		if(CommUtil.isEmpty(userId)){
			return;
		}
		HashMap map = new HashMap();
		map.put("userId", userId);
		Page<HashMap> page = new Page<HashMap>();
		page.setParamsMap(map);
//		page.setPageSize(Integer.MAX_VALUE);//查出用户所有数据
		page.setPageSize(10000);//只支持最大10000条数据
		page = playerVideoElasticsearch2Service.findByPage(page);//查出用户所有数据
		List<Map<String, Object>> mapList = page.getObjectResult();
		Set<String> fileIds = new HashSet<String>();//用户所有的fileId
		String[] fileIdArr =null;//用户所有的fileId转换成数组
		Object fileObj = null;
		String fileId = "";
		if (mapList != null && !mapList.isEmpty() && mapList.size()>0) {
			//拿出所有fileId值
			for (Map<String, Object> files : mapList) {
				fileObj = files.get("fileId");
				if(fileObj!=null){
					fileId = fileObj.toString();
					fileIds.add(fileId);
				}
			}
			if(fileIds.size()==0){
				return ;
			}
			fileIdArr = new String[fileIds.size()];
			fileIdArr = fileIds.toArray(fileIdArr);
			//修改资源状态
			playerLogicalFileService.updateStatusBatch(String.valueOf(type.getStatus()), fileIdArr);
		}
	}

	@Override
	public void updateHotUserSort(String userId, Integer sort) {
//		HotPlayerUser hotPlayerUser = hotPlayerUserDao.findById(userId);
		PlayerUserHot hotPlayerUser = playerUserHotElasticsearchService.findByUserId(userId);
		if (hotPlayerUser != null) {
            Account account = accountService.findById(userId);
            if(null != account){
            	PlayerUserHotSort puhs = new PlayerUserHotSort();
            	puhs.setUserSortId(puhs.generateId());
            	puhs.setUserId(userId);
            	puhs.setSort(sort);
            	puhs.setCountryCode(account.getAreaCode());
            	playerUserHotSortService.addPlayerUserHotSort(puhs);
            }
		}
		
	}

	@Override
	public void updateAllHotUser() {
		List<PlayerUser> playerUserList = playerUserService.findAllHotUser();
		if (!playerUserList.isEmpty()) {
			List<PlayerUserHot> result = new ArrayList<PlayerUserHot>();
			for (PlayerUser playerUser : playerUserList) {
				 Account account = accountService.findById(playerUser.getUserId());
		            if(null != account){
		            	PlayerUserHot puh = new PlayerUserHot();
		            	puh.setUserId(playerUser.getUserId());
		            	puh.setCountryCode(Integer.valueOf(account.getAreaCode()));
		            	puh.setFollowMeCount(playerUser.getFollowMeCount());
		            	puh.setiFollowCount(playerUser.getiFollowCount());
		            	puh.setShareCount(playerUser.getShareCount().longValue());
		            	puh.setStatus(playerUser.getStatus());
		            	puh.setUserRoleStatus(playerUser.getUserRoleStatus());
		            	result.add(puh);
		            }
			}
			playerUserHotElasticsearchService.add(result);
		}
		
	}

	@Override
	public Map<String, Object> getShareCountsWithCountryCodes() {
		return playerUserHotElasticsearchService.getShareCountsWithCountryCodes();
	}

}
