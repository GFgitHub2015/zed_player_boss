package com.zed.service.player.player.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.video.PlayerVideoSourceFileDao;
import com.zed.domain.player.video.PlayerVideoSourceFile;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.downloadhis.PlayerUserDownloadHisService;
import com.zed.service.player.player.PlayerVideoService;
import com.zed.service.player.player.PlayerVideoSourceFileService;

@Service("playerVideoSourceFileService")
public class PlayerVideoSourceFileServiceImpl implements PlayerVideoSourceFileService {
	
	@Resource(name="playerVideoSourceFileDao")
	private PlayerVideoSourceFileDao playerVideoSourceFileDao;
	@Resource(name="playerVideoService")
	private PlayerVideoService playerVideoService;
	@Resource(name="playerUserDownloadHisService")
	private PlayerUserDownloadHisService playerUserDownloadHisService;
	
	
	@Override
	public Map<Integer, Object> findTransCodeStatByUserId(String userId) throws AppValidationException {
		Map<Integer, Object> result= new HashMap<Integer, Object>();
		Set<String> hisIdSet = playerUserDownloadHisService.findHisIdByUserId(userId);
		if (hisIdSet != null && !hisIdSet.isEmpty() && hisIdSet.size()>0) {
			Set<String> resIdSet = playerVideoService.findResIdListByHisIds(hisIdSet);
			if (resIdSet != null && !resIdSet.isEmpty() && resIdSet.size()>0) {
				String[] resIdArray = resIdSet.toArray(new String[resIdSet.size()]);
				Long status_wait_transcoding_count = findCountByResIdWithStatus(PlayerVideoSourceFile.Status.STATUS_WAIT_TRANSCODING.getStatus(),resIdArray);
				Long status_transcoding_count = findCountByResIdWithStatus(PlayerVideoSourceFile.Status.STATUS_TRANSCODING.getStatus(),resIdArray);
				Long status_transcode_success_count = findCountByResIdWithStatus(PlayerVideoSourceFile.Status.STATUS_TRANSCODE_SUCCESS.getStatus(),resIdArray);
				Long status_transcode_faild_count = findCountByResIdWithStatus(PlayerVideoSourceFile.Status.STATUS_TRANSCODE_FAILD.getStatus(),resIdArray);
				result.put(PlayerVideoSourceFile.Status.STATUS_WAIT_TRANSCODING.getStatus(), status_wait_transcoding_count);
				result.put(PlayerVideoSourceFile.Status.STATUS_TRANSCODING.getStatus(), status_transcoding_count);
				result.put(PlayerVideoSourceFile.Status.STATUS_TRANSCODE_SUCCESS.getStatus(), status_transcode_success_count);
				result.put(PlayerVideoSourceFile.Status.STATUS_TRANSCODE_FAILD.getStatus(), status_transcode_faild_count);
				return result;
			}
		}
		result.put(PlayerVideoSourceFile.Status.STATUS_WAIT_TRANSCODING.getStatus(), 0l);
		result.put(PlayerVideoSourceFile.Status.STATUS_TRANSCODING.getStatus(), 0l);
		result.put(PlayerVideoSourceFile.Status.STATUS_TRANSCODE_SUCCESS.getStatus(), 0l);
		result.put(PlayerVideoSourceFile.Status.STATUS_TRANSCODE_FAILD.getStatus(), 0l);
		return result;
	}
	
	private Long findCountByResIdWithStatus(Integer status, String[] resIds) throws AppValidationException {
		Long count = 0l;
		if (resIds != null && resIds.length>0) {
			count = playerVideoSourceFileDao.findCountByResIdWithStatus(status, resIds);
		}
		return count;
	}

}
