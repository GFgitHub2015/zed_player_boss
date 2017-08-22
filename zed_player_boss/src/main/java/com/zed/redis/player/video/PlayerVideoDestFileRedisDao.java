package com.zed.redis.player.video;

import java.util.List;

import com.zed.domain.player.video.PlayerVideoDestFile;

public interface PlayerVideoDestFileRedisDao {
	//根据文件名获取文件信息
	public PlayerVideoDestFile getPlayerVideoDestFile(String fileId);
	//添加文件信息
	public void addPlayerVideoDestFile(PlayerVideoDestFile playerVideoDestFile);
	//删除文件信息
	public void deletePlayerVideoDestFile(String fileId);
	//根据sourceFileId获取下载文件和转码播放文件的信息
	public List<PlayerVideoDestFile> findDestFileIdBySourceFileId(String sourceFileId);
	//添加下载和播放文件的信息
	public void addDestFileIdWithSourceFileId(List<PlayerVideoDestFile> playerVideoDestFileList);
	//删除下载文件和转码播放文件的信息
	public void deleteBySourceFileId(String sourceFileId);
	
}
