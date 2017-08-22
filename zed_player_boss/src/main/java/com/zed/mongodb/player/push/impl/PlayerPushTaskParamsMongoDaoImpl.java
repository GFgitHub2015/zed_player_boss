package com.zed.mongodb.player.push.impl;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.zed.domain.player.push.PlayerTaskParams;
import com.zed.mongo.MongoDBFactory;
import com.zed.mongo.dao.BaseMongoDaoImpl;
import com.zed.mongodb.player.push.PlayerPushTaskParamsMongoDao;

@Repository("playerPushTaskParamsMongoDao")
public class PlayerPushTaskParamsMongoDaoImpl extends BaseMongoDaoImpl<PlayerTaskParams> implements PlayerPushTaskParamsMongoDao {
	
	private MongoCollection<Document> dbCollection = MongoDBFactory.getInstance().getMsgDB().getCollection("PlayerTaskParams");

	@Override
	public void addPlayerTaskParams(PlayerTaskParams playerTaskParams) {
		save(playerTaskParams);
	}

	@Override
	public PlayerTaskParams getByTaskId(String taskId) {
		Document document = dbCollection.find(Filters.eq("taskId",taskId)).first();
		if (document != null) {
			String jsonStr = JSON.toJSONString(document);
			if (StringUtils.isNotBlank(jsonStr)) {
				PlayerTaskParams ptp = PlayerTaskParams.getPlayerTaskParams(jsonStr);
				if (ptp != null) {
					return ptp;
				}
			}
		}
		return null;
	}

	@Override
	public void deleteByTaskId(String... taskIds) {
		dbCollection.updateMany(Filters.all("taskId", taskIds), Updates.set("status", -1));
	}

	@Override
	public void updatePlayerTaskParams(PlayerTaskParams playerTaskParams) {
		Document document = dbCollection.find(Filters.eq("taskId",playerTaskParams.getTaskId())).first();;
		document.putAll(playerTaskParams.forMap());
		dbCollection.findOneAndReplace(Filters.eq("_id",document.getObjectId("_id")), document);
	}
}
