package com.zed.dao.version.base.abstractdao;

import java.util.List;
import java.util.Map;

import com.zed.dao.PageDao;
import com.zed.dao.version.base.impl.VersionBaseDaoImpl;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.system.SqlId;
import com.zed.system.page.Page;
import com.zed.util.BeanUtils;


public abstract class AbstractVersionPageDao<T> extends VersionBaseDaoImpl<T> implements PageDao<T>{
	
	/**
	 * 查询所有
	 */
	@Override
	public Page<T> findByPage(Page<T> page) throws AppValidationException {
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
				List<T> result = findList(SqlId.SQL_FIND_LIST_BY_PAGE, params);
				page.setResult(result);
			}
			long totalCount = Long.parseLong(findOne(SqlId.SQL_FIND_COUNT_BY_PAGE, params).toString());
			page.setTotalCount(totalCount);
		}else{
			Log.getLogger(this.getClass()).error("分页对象page不能为空");
		}
		return page;
	}
}