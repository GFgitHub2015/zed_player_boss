package com.zed.service.iosplayer.videoclass.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.dao.iosplayer.videoclass.IosPlayerVideoClassesDao;
import com.zed.domain.iosplayer.videoclass.IosPlayerVideoClasses;
import com.zed.domain.player.videoclass.PlayerVideoClasses;
import com.zed.exception.AppValidationException;
import com.zed.service.iosplayer.videoclass.IosPlayerVideoClassesService;
import com.zed.system.page.Page;

@Service("iosPlayerVideoClassesService")
public class IosPlayerVideoClassesServiceImpl implements IosPlayerVideoClassesService {
	
	@Resource(name="iosPlayerVideoClassesDao")
	private IosPlayerVideoClassesDao playerVideoClassesDao;

	@Override
	public IosPlayerVideoClasses findById(String classId) throws AppValidationException {
		return (IosPlayerVideoClasses) playerVideoClassesDao.findById(classId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return playerVideoClassesDao.findByPage(page);
	}

	@Override
	public void add(IosPlayerVideoClasses playerVideoClasses) throws AppValidationException {
		
		updateSortByAsc(playerVideoClasses);
		
		playerVideoClassesDao.add(playerVideoClasses);		
	}

	@Override
	public void update(IosPlayerVideoClasses playerVideoClasses) throws AppValidationException {
		IosPlayerVideoClasses oldObj = this.findById(playerVideoClasses.getClassId());
		if (oldObj.getSort()!=playerVideoClasses.getSort()) {
			updateSortByAsc(playerVideoClasses);
		}
		
		playerVideoClassesDao.update(playerVideoClasses);		
	}

	private void updateSortByAsc(IosPlayerVideoClasses playerVideoClasses) {
		List<IosPlayerVideoClasses> playerVideoClassesList = playerVideoClassesDao.findBySortBy(playerVideoClasses.getSort(), " sort asc", ">=");
		if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
			Map<Integer, IosPlayerVideoClasses> map = new HashMap<Integer, IosPlayerVideoClasses>();
			for (IosPlayerVideoClasses pvc : playerVideoClassesList) {
				map.put(pvc.getSort(), pvc);
			}
			map = changeSort(playerVideoClasses.getSort(), map);
			List<IosPlayerVideoClasses> listToUpdate = new ArrayList<IosPlayerVideoClasses>();
			for (Map.Entry<Integer, IosPlayerVideoClasses> entry : map.entrySet()) {
				IosPlayerVideoClasses pvc = entry.getValue();
				if (pvc != null) {
					pvc.setSort(entry.getKey());
					listToUpdate.add(pvc);
				}
			}
			playerVideoClassesDao.updateBatch(listToUpdate);
		}
	}
	
	private Map<Integer, IosPlayerVideoClasses> changeSort(Integer sort, Map<Integer, IosPlayerVideoClasses> mapObject){
		if (sort!=null) {
			if (mapObject.containsKey(sort)) {
				IosPlayerVideoClasses pvc = mapObject.get(sort);
				if (pvc != null) {
					mapObject.remove(sort);
					pvc.setSort(sort);
					sort++;
					if (!mapObject.isEmpty()) {
						Map<Integer, IosPlayerVideoClasses> map = new HashMap<Integer, IosPlayerVideoClasses>();
						map.putAll(mapObject);
						mapObject = new HashMap<Integer, IosPlayerVideoClasses>();
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
		List<IosPlayerVideoClasses> playerVideoClassesList = playerVideoClassesDao.findBySortAndClassKey(sort, classKey);
		if (StringUtils.isNotBlank(classId)) {
			if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
				for (IosPlayerVideoClasses pvc : playerVideoClassesList) {
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
		IosPlayerVideoClasses playerVideoClasses = playerVideoClassesDao.findByClasskey(classKey);
		if (playerVideoClasses != null) {
			flag = Boolean.TRUE;
		}
		return flag;
	}

	@Override
	public Integer getLastSort(Integer initSort) throws AppValidationException {
		IosPlayerVideoClasses playerVideoClasses = playerVideoClassesDao.getLastSort();
		if (playerVideoClasses != null) {
			initSort = playerVideoClasses.getSort();
		}
		initSort++;
		return initSort;
	}

	@Override
	public void deleteBySort(Integer sort) throws AppValidationException {
		List<IosPlayerVideoClasses> playerVideoClassesList = playerVideoClassesDao.findBySortBy(sort, "sort desc", "<=");
		if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
			Set<String> idsSet = new HashSet<String>();
			for (IosPlayerVideoClasses pvc : playerVideoClassesList) {
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
		List<IosPlayerVideoClasses> playerVideoClassesList = playerVideoClassesDao.findByDescriptionAndClassKey(description, classKey);
		if (StringUtils.isNotBlank(classId)) {
			if (playerVideoClassesList != null && !playerVideoClassesList.isEmpty() && playerVideoClassesList.size()>0) {
				for (IosPlayerVideoClasses pvc : playerVideoClassesList) {
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
