package com.zed.service.account.account.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.account.account.AccountDao;
import com.zed.domain.account.account.Account;
import com.zed.domain.player.playeruser.PlayerUserHot;
import com.zed.exception.AppValidationException;
import com.zed.redis.account.account.AccountRedisDao;
import com.zed.service.account.account.AccountService;
import com.zed.service.player.es.PlayerUserHotElasticsearchService;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;
import com.zed.util.MD5;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	@Resource(name="accountDao")
	private AccountDao accountDao;
	@Resource(name="accountRedisDao")
	private AccountRedisDao accountRedisDao;
	@Resource(name="playerUserHotElasticsearchService")
	private PlayerUserHotElasticsearchService playerUserHotElasticsearchService;

	@Override
	public Account findById(String userId) throws AppValidationException {
		return (Account) accountDao.findById(userId);
	}

	@Override
	public void add(Account account) throws AppValidationException {
		account.setUserId(account.generateId());
		account.setUserType(1);
		account.setOrigin(2);
		account.setExps(BigDecimal.ZERO);
		account.setLevel(BigDecimal.ONE);
		account.setAmount(BigDecimal.ZERO);
		account.setScores(BigDecimal.ZERO);
		if (!CommUtil.isEmpty(account.getPassword())) {
			String pass;
			try {
				pass = MD5.getInstance().getHexMD5(account.getPassword());
			} catch (Exception e) {
				throw new AppValidationException("密码MD5加密出错!=======>"+e.getMessage());
			}
			account.setPassword(pass.substring(6, 22));
		}
		accountDao.add(account);
	}

	@Override
	public void update(Account account) throws AppValidationException {
		if (!CommUtil.isEmpty(account.getPassword())) {
			String pass = "";
			try {
				pass = MD5.getInstance().getHexMD5(account.getPassword());
			} catch (Exception e) {
				throw new AppValidationException("密码MD5加密出错!=======>"+e.getMessage());
			}
			account.setPassword(pass.substring(6, 22));
		}
		accountDao.update(account);
		accountRedisDao.delete(account.getUserId());
		//修改ES中的热门用户
		PlayerUserHot playerUserHot = playerUserHotElasticsearchService.findByUserId(account.getUserId());
		if (playerUserHot!=null) {
			playerUserHot.setCountryCode(Integer.valueOf(account.getAreaCode()));
			playerUserHotElasticsearchService.add(playerUserHot);
		}
	}

	@Override
	public void delete(String... userIds) throws AppValidationException {
		accountDao.delete(userIds);
		accountRedisDao.delete(userIds);
		//删除ES中的热门用户
		playerUserHotElasticsearchService.deleteByIds(userIds);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
		return accountDao.findByPage(page);
	}

	@Override
	public Boolean findByNickName(String nickName) throws AppValidationException {
		Boolean flag = Boolean.TRUE;
		Account account = accountDao.findByNickName(nickName);
		if (account != null) {
			flag = Boolean.FALSE;
		}
		return flag;
	}

	@Override
	public Boolean findByPhone(String phone) throws AppValidationException {
		Boolean flag = Boolean.TRUE;
		Account account = accountDao.findByPhone(phone);
		if (account != null) {
			flag = Boolean.FALSE;
		}
		return flag;
	}
	
	public void updateBatchByUserId(List<Account> accounts) throws AppValidationException {
		if(accounts != null  && accounts.size() > 0 ){
			accountDao.updateBatch(accounts);
		}
	}
	
}
