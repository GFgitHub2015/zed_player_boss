package com.zed.redis.server.serverinfo;

import java.util.List;

import com.zed.domain.server.serverinfo.ServerInfo;

public interface ServerInfoRedisDao {
	
	public List<ServerInfo> findAllServerInfoBy(String origin);
	
	public void addServerInfo(ServerInfo serverInfo);
	
	public void addServerInfoList(List<ServerInfo> serverInfoList);

}
