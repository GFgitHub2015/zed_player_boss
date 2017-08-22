package com.zed.redis.account.account.impl;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.zed.common.util.redis.RedisKey;
import com.zed.domain.account.account.Account;
import com.zed.redis.account.account.AccountRedisDao;

@Repository("accountRedisDao")
public class AccountRedisDaoImpl implements AccountRedisDao {

	@Resource(name="cacheRedis")
	private RedisTemplate<String,String> cacheRedis;

	@Override
	public void delete(final String ... userIds) {
		cacheRedis.execute(new RedisCallback<Account>() {
			@Override
			public Account doInRedis(RedisConnection redisConn)
					throws DataAccessException {
				StringRedisConnection conn =(StringRedisConnection) redisConn;
				Set<String> keySets = new HashSet<String>();
				for (String userId : userIds) {
					keySets.add(String.format(RedisKey.KEY_USER_INFO_HASH,userId));
				}
				if (!keySets.isEmpty()) {
					String[] keys = keySets.toArray(new String[keySets.size()]);
					if (keys.length>0) {
						conn.del(keys);
					}
				}
				return null;
			}
		});
	}
	

}
