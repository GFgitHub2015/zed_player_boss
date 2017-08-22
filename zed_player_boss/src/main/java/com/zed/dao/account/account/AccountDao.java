package com.zed.dao.account.account;

import java.io.Serializable;

import com.zed.dao.PageDao;
import com.zed.domain.account.account.Account;
import com.zed.exception.AppValidationException;

public interface AccountDao<T extends Serializable> extends PageDao<Account>  {
/*	*//**
	 * 判断用户昵称或手机号是否存在
	 * @param nickName 用户昵称
	 * @param phone 手机号
	 * @return 0:不存在  1+:存在
	 * @throws AppValidationException
	 *//*
	public Long findByNickNameOrPhone(String nickName, String phone) throws AppValidationException;*/
	
	/**
	 * 根据用户昵称获取用户对象
	 * @param nickName
	 * @return
	 * @throws AppValidationException
	 */
	public Account findByNickName(String nickName) throws AppValidationException;
	
	/**
	 * 根据用户手机号获取用户对象
	 * @param phone
	 * @return
	 * @throws AppValidationException
	 */
	public Account findByPhone(String phone) throws AppValidationException;

}
