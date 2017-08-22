package com.zed.service.player.logicalfile;

import java.util.HashMap;
import java.util.List;

import com.zed.api.subtitle.bean.VideoSubtitleBean;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerLogicalFileService {
	/**
	 * 根据fileId获取对象
	 * @param playerLogicalFileId 资源文件id
	 * @return
	 * @throws AppValidationException
	 */
	public PlayerLogicalFile findById(String playerLogicalFileId) throws AppValidationException;
	/**
	 * 分页查询资源
	 * @param page
	 * @return
	 * @throws AppValidationException
	 */
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	/**
	 * 修改状态（启用或禁用）
	 * @param playerLogicalFile
	 * @throws AppValidationException
	 */
	public void updateStatus(PlayerLogicalFile playerLogicalFile)  throws AppValidationException;
	/**
	 * 批量修改状态（启用或禁用）
	 * @param status 0:禁用 1:启用
	 * @param fileIds 资源文件id
	 * @throws AppValidationException
	 */
	public void updateStatusBatch(String status, String[] fileIds) throws AppValidationException;
	/**
	 * 根据用户id一键分享用户资源
	 * @param userId 用户id
	 * @return
	 * @throws AppValidationException
	 */
	public void updateShareFileStatus(String userId, Integer status) throws AppValidationException;
	/**
     * update:更新
     * @author: Eric 
     * @param playerLogicalFile
     * @throws AppValidationException
     */
	public  void update(PlayerLogicalFile playerLogicalFile,Integer dimension) throws AppValidationException;
	/**
	 * update:更新（资源及字幕）
	 * @author: Eric 
	 * @param playerLogicalFile
	 * @param subtitleBeans
	 * @param delSubtitleIds
	 * @param dimension 2d,3d标签
	 * @throws AppValidationException
	 */
	public void update(PlayerLogicalFile playerLogicalFile, List<VideoSubtitleBean> subtitleBeans, String delSubtitleIds,Integer dimension) throws AppValidationException;
	/**
	 * 删除搜索引擎中失效的数据（包好文件夹）
	 * @throws AppValidationException
	 */
	public void deleteUselessDataFromEs(long pageNo, long pageSize) throws AppValidationException;

	/**
	 * 批量修改状态（启用或禁用）
	 * @param playerLogicalFiles
	 * @throws AppValidationException
	 */
	public void updateStatusBatchByUserId(List<PlayerLogicalFile> playerLogicalFiles) throws AppValidationException;
	
}
