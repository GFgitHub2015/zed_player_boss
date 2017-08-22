package com.zed.dao.player.video;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.video.PlayerVideoSourceFile;
import com.zed.exception.AppValidationException;

public interface PlayerVideoSourceFileDao<T extends Serializable> extends PageDao<PlayerVideoSourceFile> {

	//获取资源所有格式
	public List<String> findFileSuffix() throws AppValidationException;
	//根据资源id删除关联原始文件
	public void deleteByResId(String ...redIds) throws AppValidationException;
	
	public List<PlayerVideoSourceFile> findAllByResId(String resId) throws AppValidationException;
	
	/**
	 * 根据resIds 获取 status 状态值的数量
	 * @param status
	 * @param resIds
	 * @return
	 * @throws AppValidationException
	 */
	public Long findCountByResIdWithStatus(Integer status, String[] resIds) throws AppValidationException;
}
