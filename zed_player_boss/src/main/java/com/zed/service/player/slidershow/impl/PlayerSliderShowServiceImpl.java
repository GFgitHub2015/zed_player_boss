package com.zed.service.player.slidershow.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.dao.player.slidershow.PlayerSliderShowDao;
import com.zed.domain.player.slidershow.PlayerSliderShow;
import com.zed.exception.AppValidationException;
import com.zed.service.player.slidershow.PlayerSliderShowService;
import com.zed.system.page.Page;

@Service("playerSliderShowService")
public class PlayerSliderShowServiceImpl implements PlayerSliderShowService {
	
	@Resource(name="playerSliderShowDao")
	private PlayerSliderShowDao playerSliderShowDao;

	@Override
	public PlayerSliderShow findById(String playerSliderShowId) throws AppValidationException {
		return (PlayerSliderShow) playerSliderShowDao.findById(playerSliderShowId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerSliderShowDao.findByPage(page);
	}

	@Override
	public void add(PlayerSliderShow playerSliderShow) throws AppValidationException {
		deleteDuplicate(playerSliderShow);
		playerSliderShowDao.add(playerSliderShow);		
	}

	private void deleteDuplicate(PlayerSliderShow playerSliderShow) {
		List<PlayerSliderShow> playerSliderShowList = playerSliderShowDao.findBySortAndAreaCode(playerSliderShow.getSort(), playerSliderShow.getAreaCode());
		if (playerSliderShowList != null && !playerSliderShowList.isEmpty() && playerSliderShowList.size()>0) {
			Set<String> playerSliderShowIdsSet = new HashSet<String>();
			for (PlayerSliderShow ps : playerSliderShowList) {
				if (!playerSliderShow.getSliderShowId().equals(ps.getSliderShowId())) {
					playerSliderShowIdsSet.add(ps.getSliderShowId());
				}
			}
			String[] playerSliderShowIds = playerSliderShowIdsSet.toArray(new String[playerSliderShowIdsSet.size()]);
			if (playerSliderShowIds.length>0) {
				delete(playerSliderShowIds);
			}
		}
	}

	@Override
	public void update(PlayerSliderShow playerSliderShow) throws AppValidationException {
		deleteDuplicate(playerSliderShow);
		playerSliderShowDao.update(playerSliderShow);		
	}

	@Override
	public void delete(String[] playerSliderShowIds) throws AppValidationException {
		playerSliderShowDao.delete(playerSliderShowIds);		
	}

	@Override
	public boolean getBySortAndAreaCode(String sliderShowId, Integer sort, String areaCode) throws AppValidationException {
		boolean flag = Boolean.FALSE;
		List<PlayerSliderShow> playerSliderShowList = playerSliderShowDao.findBySortAndAreaCode(sort, areaCode);
		if (StringUtils.isNotBlank(sliderShowId)) {
			if (playerSliderShowList != null && !playerSliderShowList.isEmpty() && playerSliderShowList.size()>0) {
				for (PlayerSliderShow ps : playerSliderShowList) {
					if (!sliderShowId.equals(ps.getSliderShowId())) {
						flag = Boolean.TRUE;
					}
				}
			}
		}else{
			if (playerSliderShowList != null && !playerSliderShowList.isEmpty() && playerSliderShowList.size()>0) {
				flag = Boolean.TRUE;
			}
		}
		return flag;
	}

}
