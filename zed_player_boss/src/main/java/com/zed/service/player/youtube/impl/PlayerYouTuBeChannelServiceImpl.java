package com.zed.service.player.youtube.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.youtube.PlayerYouTuBeChannelDao;
import com.zed.domain.player.youtube.PlayerYouTuBeChannel;
import com.zed.exception.AppValidationException;
import com.zed.service.player.youtube.PlayerYouTuBeChannelService;
import com.zed.system.page.Page;

@Service("playerYouTuBeChannelService")
public class PlayerYouTuBeChannelServiceImpl implements PlayerYouTuBeChannelService {
	
	@Resource(name="playerYouTuBeChannelDao")
	private PlayerYouTuBeChannelDao playerYouTuBeChannelDao;

	@Override
	public PlayerYouTuBeChannel findById(String channelId) throws AppValidationException {
		return (PlayerYouTuBeChannel) playerYouTuBeChannelDao.findById(channelId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerYouTuBeChannelDao.findByPage(page);
	}

	@Override
	public void add(PlayerYouTuBeChannel playerYouTuBeChannel) throws AppValidationException {
		updateSortByAsc(playerYouTuBeChannel);
		playerYouTuBeChannelDao.add(playerYouTuBeChannel);		
	}

	@Override
	public void update(PlayerYouTuBeChannel playerYouTuBeChannel) throws AppValidationException {
		updateSortByAsc(playerYouTuBeChannel);
		playerYouTuBeChannelDao.update(playerYouTuBeChannel);		
	}
//
//	@Override
//	public void addBatch(List<PlayerYouTuBeChannel> playerYouTuBeChannelList) throws AppValidationException {
//		playerYouTuBeChannelDao.addBatch(playerYouTuBeChannelList);		
//	}
//
//	@Override
//	public void updateBatch(List<PlayerYouTuBeChannel> playerYouTuBeChannelList) throws AppValidationException {
//		playerYouTuBeChannelDao.updateBatch(playerYouTuBeChannelList);		
//	}

	@Override
	public void delete(String[] channelIds) throws AppValidationException {
		playerYouTuBeChannelDao.delete(channelIds);		
	}
	
	private void updateSortByAsc(PlayerYouTuBeChannel playerYouTuBeChannel) {
		List<PlayerYouTuBeChannel> playerYouTuBeChannelList = playerYouTuBeChannelDao.findBySortBy(playerYouTuBeChannel.getSort(), " sort asc", ">=");
		if (playerYouTuBeChannelList != null && !playerYouTuBeChannelList.isEmpty() && playerYouTuBeChannelList.size()>0) {
			Map<Integer, PlayerYouTuBeChannel> map = new HashMap<Integer, PlayerYouTuBeChannel>();
			for (PlayerYouTuBeChannel pyc : playerYouTuBeChannelList) {
				map.put(pyc.getSort(), pyc);
			}
			map = changeSort(playerYouTuBeChannel.getSort(), map);
			List<PlayerYouTuBeChannel> listToUpdate = new ArrayList<PlayerYouTuBeChannel>();
			for (Map.Entry<Integer, PlayerYouTuBeChannel> entry : map.entrySet()) {
				PlayerYouTuBeChannel pyc = entry.getValue();
				if (pyc != null) {
					pyc.setSort(entry.getKey());
					listToUpdate.add(pyc);
				}
			}
			playerYouTuBeChannelDao.updateBatch(listToUpdate);
		}
	}
	
	private Map<Integer, PlayerYouTuBeChannel> changeSort(Integer sort, Map<Integer, PlayerYouTuBeChannel> mapObject){
		if (sort!=null) {
			if (mapObject.containsKey(sort)) {
				PlayerYouTuBeChannel pyc = mapObject.get(sort);
				if (pyc != null) {
					mapObject.remove(sort);
					pyc.setSort(sort);
					sort++;
					if (!mapObject.isEmpty()) {
						Map<Integer, PlayerYouTuBeChannel> map = new HashMap<Integer, PlayerYouTuBeChannel>();
						map.putAll(mapObject);
						mapObject = new HashMap<Integer, PlayerYouTuBeChannel>();
						mapObject.putAll(changeSort(sort, map));
					}
					mapObject.put(sort, pyc);
				}
			}
		}
		return mapObject;
	}

	@Override
	public Integer getLastSort() throws AppValidationException {
		Integer initSort = 0;
		PlayerYouTuBeChannel playerYouTuBeChannel = playerYouTuBeChannelDao.getLastSort();
		if (playerYouTuBeChannel != null) {
			initSort = playerYouTuBeChannel.getSort();
		}
		initSort++;
		return initSort;
	}

	@Override
	public void deleteBySort(Integer sort) throws AppValidationException {
		List<PlayerYouTuBeChannel> playerYouTuBeChannelList = playerYouTuBeChannelDao.findBySortBy(sort, "sort desc", "<=");
		if (playerYouTuBeChannelList != null && !playerYouTuBeChannelList.isEmpty() && playerYouTuBeChannelList.size()>0) {
			Set<String> idsSet = new HashSet<String>();
			for (PlayerYouTuBeChannel pyc : playerYouTuBeChannelList) {
				idsSet.add(pyc.getChannelId());
			}
			String[] idsArray = idsSet.toArray(new String[idsSet.size()]);
			if (idsArray.length>0) {
				playerYouTuBeChannelDao.delete(idsArray);
			}
		}
	}

	@Override
	public Boolean isExist(String channelId) throws AppValidationException {
		Boolean flag = Boolean.FALSE;
		PlayerYouTuBeChannel playerYouTuBeChannel = findById(channelId);
		if (playerYouTuBeChannel != null) {
			flag = Boolean.TRUE;
		}
		return flag;
	}

}
