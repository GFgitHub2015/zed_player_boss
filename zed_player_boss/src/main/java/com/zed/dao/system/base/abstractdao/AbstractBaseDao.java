package com.zed.dao.system.base.abstractdao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.zed.common.db.SqlId;
import com.zed.dao.BaseDao;
import com.zed.exception.AppValidationException;
import com.zed.util.BeanUtils;
import com.zed.util.CommUtil;

public abstract class AbstractBaseDao<T> implements BaseDao<T> {
	
	protected abstract SqlSession getSqlSessionTemplate();
	
	protected String sqlNamespace = getDefaultSqlNamespace();
	
	protected String getSqlName(String sqlName) {
		return sqlNamespace + "." + sqlName;
	}
	
	protected String getSqlName(String sqlName, String objectName) {
		return sqlNamespace + "." + objectName + "." + sqlName;
	}
	
	protected String getDefaultSqlNamespace() {
		Class<?> genericClass = BeanUtils.getGenericClass(this.getClass());
		return genericClass == null ? null : "mapper." + genericClass.getSimpleName();
	}

	@Override
	public void add(T object) throws AppValidationException {
		add(SqlId.SQL_ADD, object);
	}
	
	@Override
	public void addBatch(Collection<T> list) throws AppValidationException{
		addAll("addBatch", list);
	}
	
	protected void addAll(String sqlName, Collection<T> list) throws AppValidationException{
		getSqlSessionTemplate().insert(getSqlName(sqlName), list);
	}

	protected void add(String sqlName, T object) throws AppValidationException {
		getSqlSessionTemplate().insert(getSqlName(sqlName), object);
	}
	
	@Override
	public void update(T object) throws AppValidationException {
		update(SqlId.SQL_UPDATE, object);
	}
	
	@Override
	public void updateBatch(Collection<T> list) throws AppValidationException{
		updateAll("updateBatch", list);
	}
	
	protected void updateAll(String sqlName, Collection<T> list) throws AppValidationException {
		getSqlSessionTemplate().update(getSqlName(sqlName), list);
	}
	
	protected void update(String sqlName, T object) throws AppValidationException {
		getSqlSessionTemplate().update(getSqlName(sqlName), object);
	}

	protected void update(String sqlName, Map<String,Object> params) throws AppValidationException {
		getSqlSessionTemplate().update(getSqlName(sqlName), params);
	}

	@Override
	public T findById(String id) throws AppValidationException {
		return findById(SqlId.SQL_FIND_BY_ID, id);
	}

	protected T findById(String sqlName, String id) throws AppValidationException {
		T object = getSqlSessionTemplate().selectOne(getSqlName(sqlName), id);
		return object;
	}

	@Override
	public void delete(String[] id) throws AppValidationException {
		delete(SqlId.SQL_DELETE_BY_ID, id);
	}

	protected void delete(String sqlName, String[] id) throws AppValidationException {
		getSqlSessionTemplate().delete(getSqlName(sqlName), id);
	}

	@Override
	public void delete(String id) throws AppValidationException {
		delete(SqlId.SQL_DELETE_BY_ID, id);

	}

	protected void delete(String sqlName, String id) throws AppValidationException {
		getSqlSessionTemplate().delete(getSqlName(sqlName), id);
	}

	protected List<T> findList(String sqlName) throws AppValidationException {
		return getSqlSessionTemplate().selectList(getSqlName(sqlName));
	}

	protected List<T> findList(String sqlName, Map<String, Object> map) throws AppValidationException {
		List<T> list = getSqlSessionTemplate().selectList(getSqlName(sqlName), map);
		return list;
	}

	protected List<T> findList(String sqlName, String str) throws AppValidationException {
		List<T> list = getSqlSessionTemplate().selectList(getSqlName(sqlName), str);
		return list;
	}
	
	protected List<T> findList(String sqlName, String[] str) throws AppValidationException {
		List<T> list = getSqlSessionTemplate().selectList(getSqlName(sqlName), str);
		return list;
	}

	protected List<Object> findMore(String sqlName, Map<String, Object> map) throws AppValidationException {
		List<Object> list = getSqlSessionTemplate().selectList(getSqlName(sqlName), map);
		return list;
	}

	protected List<Object> findMore(String sqlName, String str) throws AppValidationException {
		List<Object> list = getSqlSessionTemplate().selectList(getSqlName(sqlName), str);
		return list;
	}

	protected List<Object> findMore(String sqlName) throws AppValidationException {
		return getSqlSessionTemplate().selectList(getSqlName(sqlName));
	}

	protected List<Map> findMap(String sqlName, Map<String, Object> map) throws AppValidationException {
		List<Map> list = getSqlSessionTemplate().selectList(getSqlName(sqlName), map);
		return list;
	}

	protected List<Map> findMap(String sqlName, String str) throws AppValidationException {
		List<Map> list = getSqlSessionTemplate().selectList(getSqlName(sqlName), str);
		return list;
	}

	protected List<Map> findMap(String sqlName) throws AppValidationException {
		List<Map> list = getSqlSessionTemplate().selectList(getSqlName(sqlName));
		return list;
	}

	protected Object findOne(String sqlName, Map<String, Object> map) throws AppValidationException {
		return getSqlSessionTemplate().selectOne(getSqlName(!CommUtil.isEmpty(sqlName)?sqlName:SqlId.SQL_FIND_COUNT_BY_PAGE), map);
	}

	protected Object findOne(String sqlName, String str) throws AppValidationException {
		return getSqlSessionTemplate().selectOne(getSqlName(sqlName), str);
	}

	protected Object findOne(String sqlName) throws AppValidationException {
		return getSqlSessionTemplate().selectOne(getSqlName(sqlName));
	}

	protected T find(String sqlName, Map<String, Object> map) throws AppValidationException {
		return getSqlSessionTemplate().selectOne(getSqlName(sqlName), map);
	}
	
	protected T find(String sqlName, String str) throws AppValidationException {
		return getSqlSessionTemplate().selectOne(getSqlName(sqlName), str);
	}
	
	protected T find(String sqlName) throws AppValidationException {
		return getSqlSessionTemplate().selectOne(getSqlName(sqlName));
	}
	

	@Override
	public String updateSequence() {
		String sequence = getSqlSessionTemplate().selectOne(getSqlName(SqlId.SQL_GET_SEQUENCE));
		return sequence;
	}

}
