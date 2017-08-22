package com.zed.service.player.video;

import java.util.List;

import com.zed.domain.player.video.PlayerRelationVideo;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface IPlayerRelationVideoService {

	Page<PlayerRelationVideo> findByPage(Page<PlayerRelationVideo> page) throws AppValidationException;
	
	PlayerRelationVideo findByFileId(String fileId) throws AppValidationException;
	
	void addVideo(PlayerRelationVideo playerVideoIntegration) throws AppValidationException;
	
	String replaceCdnUrl(String srcUrl,String cdnUrl) throws AppValidationException;
	
	void deleteByUid(String... uid) throws AppValidationException;
	
	PlayerRelationVideo getYouTubeVideoDetail(String userId, String fileId) throws AppValidationException;
	
	void updateStatus(List<PlayerRelationVideo> playerVideoIntegrationList) throws AppValidationException;
	
	void updateVideo(PlayerRelationVideo playerVideoIntegration) throws AppValidationException;
	
	List<PlayerRelationVideo> findAllRelationVideoByUids(String... uids) throws AppValidationException;
	
	PlayerRelationVideo findByUid(String uid) throws AppValidationException;
	
}
