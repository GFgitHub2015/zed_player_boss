package com.zed.dao.server.serverinfo.impl;

import org.springframework.stereotype.Repository;

import com.zed.dao.server.base.abstractdao.AbstractServerPageDao;
import com.zed.dao.server.serverinfo.ServerInfoDao;
import com.zed.domain.server.serverinfo.ServerInfo;

@Repository
public class ServerInfoDaoImpl extends AbstractServerPageDao<ServerInfo> implements ServerInfoDao<ServerInfo> {


}
