package com.zed.redis.server.serverinfo.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.common.util.JsonUtils;
import com.zed.domain.server.serverinfo.ServerInfo;
import com.zed.redis.server.serverinfo.ServerInfoRedisDao;
import com.zed.util.RedisKey;

@Repository("serverInfoRedisDao")
public class ServerInfoRedisDaoImpl implements ServerInfoRedisDao {
	
	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public List<ServerInfo> findAllServerInfoBy(String origin) {
		Set<String>  jsonStrSet= cacheRedis.opsForSet().members(String.format(RedisKey.KEY_SERVER_INFO_ORIGIN_SET, origin));
		List<ServerInfo> list = new ArrayList<ServerInfo>();
		for (String string : jsonStrSet) {
			list.add(JsonUtils.jsonToObj(string,ServerInfo.class));
		}
		return list;
	}

	@Override
	public void addServerInfo(ServerInfo serverInfo) {
		cacheRedis.opsForSet().add(String.format(RedisKey.KEY_SERVER_INFO_ORIGIN_SET, serverInfo.getOrigin().toString()), JsonUtils.objToJson(serverInfo));		
	}

	@Override
	public void addServerInfoList(List<ServerInfo> serverInfoList) {
		Map<String, List<ServerInfo>> mapList = new HashMap<String, List<ServerInfo>>();
		for (ServerInfo serverInfo : serverInfoList) {
			if (!mapList.containsKey(serverInfo.getOrigin().toString())) {
				mapList.put(serverInfo.getOrigin().toString(), new ArrayList<ServerInfo>());
			}
			mapList.get(serverInfo.getOrigin().toString()).add(serverInfo);
		}
		for (Map.Entry<String, List<ServerInfo>> entry: mapList.entrySet()) {
			List<ServerInfo> list = entry.getValue();
			String[] array = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				array[i] = JsonUtils.objToJson(list.get(i));
			}
			cacheRedis.opsForSet().add(String.format(RedisKey.KEY_SERVER_INFO_ORIGIN_SET, entry.getKey()), array);
		}
	}

}
