package com.zed.mongodb.player.push.impl;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.zed.domain.player.push.PlayerPushTarget;
import com.zed.mongo.MongoDBFactory;
import com.zed.mongo.dao.BaseMongoDaoImpl;
import com.zed.mongodb.player.push.PlayerPushTargetMongoDao;

@Repository("playerPushTargetMongoDao")
public class PlayerPushTargetMongoDaoImpl extends BaseMongoDaoImpl<PlayerPushTarget> implements PlayerPushTargetMongoDao {

	private MongoCollection<Document> dbCollection = MongoDBFactory.getInstance().getMsgDB().getCollection("PlayerPushTarget");
	
	@Override
	public Long findCountByScheduleId(String scheduleId) {
		FindIterable<Document> documents = dbCollection.find(Filters.eq("scheduleId",scheduleId));
		Long count = 0l;
		if (documents != null) {
			MongoCursor<Document> cur = documents.iterator();
			while(cur.hasNext()){
				cur.next();
				count++;
			}
			return count;
		}
		return count;
	}

	@Override
	public Map<String, Long> findCountMapByScheduleIds(String[] scheduleIds) {
		Map<String, Long> resultMap = new HashMap<String, Long>();
		for (String scheduleId : scheduleIds) {
			Long count = this.findCountByScheduleId(scheduleId);
			resultMap.put(scheduleId, count);
		}
		return resultMap;
	}

	@Override
	public Long findCountByScheduleId(String[] scheduleIds) {
		Map<String, Long> countMap = this.findCountMapByScheduleIds(scheduleIds);
		Long count = 0l;
		for (Map.Entry<String, Long> entry : countMap.entrySet()) {
			count+=entry.getValue();
		}
		return count;
	}

}
