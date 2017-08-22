package com.zed.dao.player.video;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.player.video.PlayerRelationVideo;

public interface IPlayerRelationVideoDao<T extends Serializable> extends PageDao<PlayerRelationVideo> {

	PlayerRelationVideo findByFileId(String fileId);
	
	PlayerRelationVideo findByUid(String uid);
	
}
