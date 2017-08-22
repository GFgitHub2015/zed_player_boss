package com.zed.dao.player.base.abstractdao;

import java.util.List;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.dao.player.base.impl.PlayerBaseDaoImpl;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.system.SqlId;
import com.zed.system.page.Page;
import com.zed.util.BeanUtils;


public abstract class AbstractPlayerPageDao<T> extends PlayerBaseDaoImpl<T> implements PageDao<T>{
	
	/**
	 * 查询所有
	 */
	@Override
	public Page<T> findByPage(Page<T> page) throws AppValidationException {
		findByPage(SqlId.SQL_FIND_LIST_BY_PAGE, SqlId.SQL_FIND_COUNT_BY_PAGE, page);
		return page;
	}
	

	/**
	 * 查询所有
	 */
	public Page<T> findByPage(String sqlListId,String sqlCountId,Page<T> page) throws AppValidationException {
		if(page != null){
			Map<String, Object> params = BeanUtils.toMap(page.getObject());
			//2个集合合并
			if(page.getParamsMap() != null && page.getParamsMap().size() > 0){
				params.putAll(page.getParamsMap());
			}
			//是否只查询记录数
			if(!page.isFindCount()){
				if(page.getSorting() != null && !("").equals(page.getSorting())){
					params.put("sorting", page.getSorting());
				}
				params.put("offset", (page.getPageNo() - 1) * page.getPageSize());//从第几条记录开始
				params.put("limit", page.getPageSize());//每一页取的记录数
				List<T> result = findList(sqlListId, params);
				page.setResult(result);
			}
			Object obj = findOne(sqlCountId, params);
			long totalCount = Long.parseLong(findOne(sqlCountId, params).toString());
			page.setTotalCount(totalCount);
		}else{
			Log.getLogger(this.getClass()).error("分页对象page不能为空");
		}
		return page;
	}
}