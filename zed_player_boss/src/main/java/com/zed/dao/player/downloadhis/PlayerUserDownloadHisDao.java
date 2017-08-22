package com.zed.dao.player.downloadhis;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.downloadhis.PlayerUserDownloadHis;
import com.zed.exception.AppValidationException;

public interface PlayerUserDownloadHisDao<T extends Serializable> extends PageDao<PlayerUserDownloadHis> {

	//根据用户userId获取用户下载资源原点记录hisId集合
	public List<String> findHisIdByUserId(String userId) throws AppValidationException;
}
