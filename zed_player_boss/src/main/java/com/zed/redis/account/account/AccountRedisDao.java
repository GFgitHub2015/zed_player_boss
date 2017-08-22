package com.zed.redis.account.account;

public interface AccountRedisDao {
	
	/**
	 * 删除账号缓存
	 * @param userId
	 */
    public void delete(String ... userId);

}
