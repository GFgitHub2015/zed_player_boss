package com.zed.dao.server.serverinfo;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.server.serverinfo.ServerInfo;

public interface ServerInfoDao<T extends Serializable> extends PageDao<ServerInfo> {

}
