package com.zed.dao.player.video.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.video.PlayerVideoDestFileDao;
import com.zed.domain.player.video.PlayerVideoDestFile;
import com.zed.exception.AppValidationException;

@Repository("playerVideoDestFileDao")
public class PlayerVideoDestFileDaoImpl extends AbstractPlayerPageDao<PlayerVideoDestFile> implements PlayerVideoDestFileDao<PlayerVideoDestFile> {

	@Override
	public List<PlayerVideoDestFile> findAllByParams(String sourceFileId, String fileSuffix, String fileType)
			throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sourceFileId", sourceFileId);
		params.put("fileSuffix", fileSuffix);
		params.put("fileType", fileType);
		return findList("findAllByParams", params);
	}
	
	@Override
	public List<PlayerVideoDestFile> findAllBySourceFileIds(String[] sourceFileIds, String fileSuffix, String fileType)
			throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sourceFileIds", sourceFileIds);
		params.put("fileSuffix", fileSuffix);
		params.put("fileType", fileType);
		return findList("findAllBySourceFileIds", params);
	}

	@Override
	public PlayerVideoDestFile getPlayerVideoDestFile(String sourceFileId, String fileType)
			throws AppValidationException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sourceFileId", sourceFileId);
		params.put("fileType", fileType);
		return (PlayerVideoDestFile) findOne("getPlayerVideoDestFile", params);
	}

	@Override
	public List<PlayerVideoDestFile> findBySourceFileIdWithFileSuffix(Map<String, Object> param)
			throws AppValidationException {
		return findList("findBySourceFileIdWithFileSuffix", param);
	}

	/**
	 * @date : 2017年4月5日 下午4:37:12
	 * @author : Iris.Xiao
	 * @param sourceFileId
	 * @param dimenssion
	 * @description : 修改2d,3d标签
	*/
	public void updateVideoDestFileDimension(String sourceFileId,Integer dimension)  throws AppValidationException{
		PlayerVideoDestFile destFile = new PlayerVideoDestFile();
		destFile.setSourceFileId(sourceFileId);
		destFile.setDimension(dimension);
		this.update("updateVideoDestFileDimenssion",destFile);
	}

}
