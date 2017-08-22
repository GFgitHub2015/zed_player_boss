package com.zed.dao.player.video;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.domain.player.video.PlayerVideoDestFile;
import com.zed.exception.AppValidationException;

public interface PlayerVideoDestFileDao<T extends Serializable> extends PageDao<PlayerVideoDestFile> {

	/**
	 * 根据参数获取播放资源文件集合
	 * @param params
	 * @return
	 * @throws AppValidationException
	 */
	public List<PlayerVideoDestFile> findAllByParams(String sourceFileId, String fileSuffix, String fileType) throws AppValidationException;
	/**
	 * 根据参数获取播放资源文件集合
	 * @param params
	 * @return
	 * @throws AppValidationException
	 */
	public List<PlayerVideoDestFile> findAllBySourceFileIds(String[] sourceFileId, String fileSuffix, String fileType) throws AppValidationException;
	
	/**
	 * 根据参数获取下载文件
	 * @param sourceFileId
	 * @param fileType
	 * @return
	 * @throws AppValidationException
	 */
	public PlayerVideoDestFile getPlayerVideoDestFile(String sourceFileId, String fileType) throws AppValidationException;
	
	public List<PlayerVideoDestFile> findBySourceFileIdWithFileSuffix(Map<String, Object> param)  throws AppValidationException;
	
	/**
	 * @date : 2017年4月5日 下午4:37:12
	 * @author : Iris.Xiao
	 * @param sourceFileId
	 * @param dimenssion
	 * @description : 修改2d,3d标签
	*/
	public void updateVideoDestFileDimension(String sourceFileId,Integer dimension)  throws AppValidationException;
	
}
