package com.zed.system;

public interface SqlId {
	
	//查询
	public static final String SQL_GET_SEQUENCE = "getSequence";
	public static final String SQL_FIND_BY_ID = "findById";
	public static final String SQL_FIND_BY_PARAMS = "findByParams";
	public static final String SQL_FIND_LIST = "findList";
	public static final String SQL_FIND_ALL = "findAll";
	public static final String SQL_FIND_LIST_BY_PAGE = "findListByPage";
	public static final String SQL_FIND_COUNT_BY_PAGE = "findCountByPage";
	
	
	//添加
	public static final String SQL_ADD = "add";
	public static final String SQL_ADD_LIST = "addList";
	
	
	//修改
	public static final String SQL_UPDATE = "update";
	public static final String SQL_UPDATE_List = "updateList";
	
	
	//删除
	public static final String SQL_DELETE_BY_IDS = "deleteByIds";
	
}
