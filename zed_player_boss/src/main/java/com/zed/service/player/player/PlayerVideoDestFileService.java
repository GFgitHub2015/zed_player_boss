package com.zed.service.player.player;

import com.zed.domain.player.video.PlayerVideoDestFile;
import com.zed.exception.AppValidationException;

public interface PlayerVideoDestFileService {

	/**
	 * 根据参数获取下载文件
	 * @param sourceFileId
	 * @param fileType
	 * @return
	 * @throws AppValidationException
	 */
	public PlayerVideoDestFile getPlayerVideoDestFile(String sourceFileId, String fileType) throws AppValidationException;
	
	
	/**
	 * @date : 2017年4月5日 下午4:37:12
	 * @author : Iris.Xiao
	 * @param sourceFileId
	 * @param dimenssion
	 * @description : 修改2d,3d标签
	*/
	public void updateVideoDestFileDimension(String sourceFileId,Integer dimension)  throws AppValidationException;
	
	
	public PlayerVideoDestFile findByFileId(String fileId)  throws AppValidationException;
	
}
