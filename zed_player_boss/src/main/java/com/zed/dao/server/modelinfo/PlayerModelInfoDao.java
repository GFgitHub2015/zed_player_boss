package com.zed.dao.server.modelinfo;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.server.modelinfo.PlayerModelInfo;

public interface PlayerModelInfoDao<T extends Serializable> extends PageDao<PlayerModelInfo> {
	
	public List<PlayerModelInfo> findAll();

}
