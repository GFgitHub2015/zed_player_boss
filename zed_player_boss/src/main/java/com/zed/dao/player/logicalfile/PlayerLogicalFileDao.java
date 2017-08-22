package com.zed.dao.player.logicalfile;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.exception.AppValidationException;

public interface PlayerLogicalFileDao<T extends Serializable> extends PageDao<PlayerLogicalFile> {

	//修改状态
	public void updateStatus(PlayerLogicalFile playerLogicalFile) throws AppValidationException;
	//根据fileIds获取集合
	public List<PlayerLogicalFile> findListByIds(String[] fileIds) throws AppValidationException;
	
	public void updateStatusBatchByUserId(List<PlayerLogicalFile> playerLogicalFiles) throws AppValidationException;
	
}
