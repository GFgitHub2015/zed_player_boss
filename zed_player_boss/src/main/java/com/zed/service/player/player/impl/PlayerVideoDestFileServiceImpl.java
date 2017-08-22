package com.zed.service.player.player.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.video.PlayerVideoDestFileDao;
import com.zed.domain.player.video.PlayerVideoDestFile;
import com.zed.exception.AppValidationException;
import com.zed.service.player.player.PlayerVideoDestFileService;

@Service("playerVideoDestFileService")
public class PlayerVideoDestFileServiceImpl implements PlayerVideoDestFileService {
	
	@Resource(name="playerVideoDestFileDao")
	private PlayerVideoDestFileDao playerVideoDestFileDao;

	@Override
	public PlayerVideoDestFile getPlayerVideoDestFile(String sourceFileId, String fileType)
			throws AppValidationException {
		return playerVideoDestFileDao.getPlayerVideoDestFile(sourceFileId, fileType);
	}

	/**
	 * @date : 2017年4月5日 下午4:37:12
	 * @author : Iris.Xiao
	 * @param sourceFileId
	 * @param dimenssion
	 * @description : 修改2d,3d标签
	*/
	public void updateVideoDestFileDimension(String sourceFileId,Integer dimension)  throws AppValidationException{
		playerVideoDestFileDao.updateVideoDestFileDimension(sourceFileId, dimension);
	}

	@Override
	public PlayerVideoDestFile findByFileId(String fileId) throws AppValidationException {
		return (PlayerVideoDestFile) playerVideoDestFileDao.findById(fileId);
	}

}
