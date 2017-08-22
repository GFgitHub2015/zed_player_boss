package com.zed.dao.player.video.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.video.IPlayerRelationVideoDao;
import com.zed.domain.player.video.PlayerRelationVideo;

@Repository("playerRelationVideoDao")
public class PlayerRelationVideoDaoImpl extends AbstractPlayerPageDao<PlayerRelationVideo> implements IPlayerRelationVideoDao<PlayerRelationVideo> {

	@Override
	public PlayerRelationVideo findByFileId(String fileId) {
		return find("findByFileId", fileId);
	}
	
	@Override
	public PlayerRelationVideo findByUid(String uid) {
		return find("findByUid", uid);
	}
	
}
