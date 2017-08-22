package com.zed.service.iosplayer.slidershow.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.dao.iosplayer.slidershow.IosPlayerSliderShowDao;
import com.zed.domain.iosplayer.slidershow.IosPlayerSliderShow;
import com.zed.exception.AppValidationException;
import com.zed.service.iosplayer.slidershow.IosPlayerSliderShowService;
import com.zed.system.page.Page;

@Service("iosPlayerSliderShowService")
public class IosPlayerSliderShowServiceImpl implements IosPlayerSliderShowService {
	
	@Resource(name="iosPlayerSliderShowDao")
	private IosPlayerSliderShowDao playerSliderShowDao;

	@Override
	public IosPlayerSliderShow findById(String playerSliderShowId) throws AppValidationException {
		return (IosPlayerSliderShow) playerSliderShowDao.findById(playerSliderShowId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerSliderShowDao.findByPage(page);
	}

	@Override
	public void add(IosPlayerSliderShow playerSliderShow) throws AppValidationException {
		deleteDuplicate(playerSliderShow);
		playerSliderShowDao.add(playerSliderShow);		
	}

	private void deleteDuplicate(IosPlayerSliderShow playerSliderShow) {
		List<IosPlayerSliderShow> playerSliderShowList = playerSliderShowDao.findBySortAndAreaCode(playerSliderShow.getSort(), playerSliderShow.getAreaCode());
		if (playerSliderShowList != null && !playerSliderShowList.isEmpty() && playerSliderShowList.size()>0) {
			Set<String> playerSliderShowIdsSet = new HashSet<String>();
			for (IosPlayerSliderShow ps : playerSliderShowList) {
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
	public void update(IosPlayerSliderShow playerSliderShow) throws AppValidationException {
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
		List<IosPlayerSliderShow> playerSliderShowList = playerSliderShowDao.findBySortAndAreaCode(sort, areaCode);
		if (StringUtils.isNotBlank(sliderShowId)) {
			if (playerSliderShowList != null && !playerSliderShowList.isEmpty() && playerSliderShowList.size()>0) {
				for (IosPlayerSliderShow ps : playerSliderShowList) {
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
