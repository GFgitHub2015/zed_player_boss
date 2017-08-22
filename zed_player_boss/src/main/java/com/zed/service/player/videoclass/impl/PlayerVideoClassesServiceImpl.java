package com.zed.service.player.videoclass.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.dao.player.videoclass.PlayerVideoClassesDao;
import com.zed.domain.player.videoclass.PlayerVideoClasses;
import com.zed.exception.AppValidationException;
import com.zed.service.player.videoclass.PlayerVideoClassesService;
import com.zed.system.page.Page;

@Service("playerVideoClassesService")
public class PlayerVideoClassesServiceImpl implements PlayerVideoClassesService {
	
	@Resource(name="playerVideoClassesDao")
	private PlayerVideoClassesDao playerVideoClassesDao;

	@Override
	public PlayerVideoClasses findById(String classId) throws AppValidationException {
		return (PlayerVideoClasses) playerVideoClassesDao.findById(classId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerVideoClassesDao.findByPage(page);
	}

	@Override
	public void add(PlayerVideoClasses playerVideoClasses) throws AppValidationException {
		
		updateSortByAsc(playerVideoClasses);
		
		playerVideoClassesDao.add(playerVideoClasses);		
	}

	@Override
	public void update(PlayerVideoClasses playerVideoClasses) throws AppValidationException {
		PlayerVideoClasses oldObj = this.findById(playerVideoClasses.getClassId());
		if (oldObj.getSort()!=playerVideoClasses.getSort()) {
			updateSortByAsc(playerVideoClasses);
		}
		
		playerVideoClassesDao.update(playerVideoClasses);		
	}

	private void updateSortByAsc(PlayerVideoClasses playerVideoClasses) {
		List<PlayerVideoClasses> playerVideoClassesList = playerVideoClassesDao.findBySortBy(playerVideoClasses.getSort(), " sort asc", ">=");
		if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
			Map<Integer, PlayerVideoClasses> map = new HashMap<Integer, PlayerVideoClasses>();
			for (PlayerVideoClasses pvc : playerVideoClassesList) {
				map.put(pvc.getSort(), pvc);
			}
			map = changeSort(playerVideoClasses.getSort(), map);
			List<PlayerVideoClasses> listToUpdate = new ArrayList<PlayerVideoClasses>();
			for (Map.Entry<Integer, PlayerVideoClasses> entry : map.entrySet()) {
				PlayerVideoClasses pvc = entry.getValue();
				if (pvc != null) {
					pvc.setSort(entry.getKey());
					listToUpdate.add(pvc);
				}
			}
			playerVideoClassesDao.updateBatch(listToUpdate);
		}
	}
	
	private Map<Integer, PlayerVideoClasses> changeSort(Integer sort, Map<Integer, PlayerVideoClasses> mapObject){
		if (sort!=null) {
			if (mapObject.containsKey(sort)) {
				PlayerVideoClasses pvc = mapObject.get(sort);
				if (pvc != null) {
					mapObject.remove(sort);
					pvc.setSort(sort);
					sort++;
					if (!mapObject.isEmpty()) {
						Map<Integer, PlayerVideoClasses> map = new HashMap<Integer, PlayerVideoClasses>();
						map.putAll(mapObject);
						mapObject = new HashMap<Integer, PlayerVideoClasses>();
						mapObject.putAll(changeSort(sort, map));
					}
					mapObject.put(sort, pvc);
				}
			}
		}
		return mapObject;
	}

	@Override
	public void delete(String[] classIds) throws AppValidationException {
		playerVideoClassesDao.deleteFromDisk(classIds);
	}

	@Override
	public boolean getBySortAndClassKey(String classId, Integer sort, String classKey) throws AppValidationException {
		boolean flag = Boolean.FALSE;
		List<PlayerVideoClasses> playerVideoClassesList = playerVideoClassesDao.findBySortAndClassKey(sort, classKey);
		if (StringUtils.isNotBlank(classId)) {
			if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
				for (PlayerVideoClasses pvc : playerVideoClassesList) {
					if (!classId.equals(pvc.getClassId())) {
						flag = Boolean.TRUE;
					}
				}
			}
		}else{
			if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
				flag = Boolean.TRUE;
			}
		}
		return flag;
	}

	@Override
	public boolean getByClasskey(String classKey) throws AppValidationException {
		boolean flag = Boolean.FALSE;
		PlayerVideoClasses playerVideoClasses = playerVideoClassesDao.findByClasskey(classKey);
		if (playerVideoClasses != null) {
			flag = Boolean.TRUE;
		}
		return flag;
	}

	@Override
	public Integer getLastSort(Integer initSort) throws AppValidationException {
		PlayerVideoClasses playerVideoClasses = playerVideoClassesDao.getLastSort();
		if (playerVideoClasses != null) {
			initSort = playerVideoClasses.getSort();
		}
		initSort++;
		return initSort;
	}

	@Override
	public void deleteBySort(Integer sort) throws AppValidationException {
		List<PlayerVideoClasses> playerVideoClassesList = playerVideoClassesDao.findBySortBy(sort, "sort desc", "<=");
		if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
			Set<String> idsSet = new HashSet<String>();
			for (PlayerVideoClasses pvc : playerVideoClassesList) {
				idsSet.add(pvc.getClassId());
			}
			String[] idsArray = idsSet.toArray(new String[idsSet.size()]);
			if (idsArray.length>0) {
				playerVideoClassesDao.deleteFromDisk(idsArray);
			}
		}
	}

	@Override
	public boolean getByDescriptionAndClassKey(String classId, String description, String classKey)
			throws AppValidationException {
		boolean flag = Boolean.FALSE;
		List<PlayerVideoClasses> playerVideoClassesList = playerVideoClassesDao.findByDescriptionAndClassKey(description, classKey);
		if (StringUtils.isNotBlank(classId)) {
			if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
				for (PlayerVideoClasses pvc : playerVideoClassesList) {
					if (!classId.equals(pvc.getClassId())) {
						flag = Boolean.TRUE;
					}
				}
			}
		}else{
			if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
				flag = Boolean.TRUE;
			}
		}
		return flag;
	}
	
}
