package com.zed.service.account.account;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.account.account.Account;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface AccountService {
	
	/**
	 * 根据用户id获取用户基本信息
	 * @param userId
	 * @return
	 * @throws AppValidationException
	 */
	public Account findById(String userId) throws AppValidationException;
	
	/**
	 * 新建用户
	 * @param account 新建用户
	 * @throws AppValidationException
	 */
	public void add(Account account) throws AppValidationException;
	/**
	 * 更新用户信息
	 * @param account
	 * @throws AppValidationException
	 */
	public void update(Account account) throws AppValidationException;
	/**
	 * 逻辑删除用户
	 * @param userIds
	 * @throws AppValidationException
	 */
	public void delete(String ... userIds) throws AppValidationException;
	/**
	 * 分页查询
	 * @param page
	 * @return
	 * @throws AppValidationException
	 */
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException;
	/**
	 * 判断用户名是否存在
	 * @param nickName 用户昵称
	 * @param userId 用户id
	 * @throws AppValidationException
	 */
	public Boolean findByNickName(String nickName) throws AppValidationException;
	/**
	 * 判断手机号是否存在
	 * @param phone 用户昵称
	 * @param userId 用户id
	 * @throws AppValidationException
	 */
	public Boolean findByPhone(String phone) throws AppValidationException;

	public void updateBatchByUserId(List<Account> accounts) throws AppValidationException;
	
}
