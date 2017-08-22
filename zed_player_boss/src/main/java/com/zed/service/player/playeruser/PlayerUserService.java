package com.zed.service.player.playeruser;

import java.util.HashMap;
import java.util.List;

import com.zed.domain.player.playeruser.PlayerUser;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerUserService {
	
	/**
	 * 根据用户id获取用户
	 * @param userId
	 * @return
	 * @throws AppValidationException
	 */
	public PlayerUser findById(String userId) throws AppValidationException;
	/**
	 * 新建网盘用户
	 * @param playerUser 新建网盘用户
	 * @throws AppValidationException
	 */
	public void add(PlayerUser playerUser) throws AppValidationException;
	/**
	 * 更新用户
	 * @param playerUser
	 * @throws AppValidationException
	 */
	public void update(PlayerUser playerUser,String curUserId) throws AppValidationException;
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
	 * 一键分享用户资源
	 * @param userId
	 * @param status
	 * @throws AppValidationException
	 */
	public void updateShareFileStatus(String userId, String status) throws AppValidationException;
	
	/**
	 * 获取所有的热门用户
	 * @return
	 * @throws AppValidationException
	 */
	public List<PlayerUser> findAllHotUser() throws AppValidationException;
	
	/**
	 * 网盘用户分页查询
	 * @param page
	 * @return
	 * @throws AppValidationException
	 */
	public Page<HashMap> findPlayerUserByPage(Page<HashMap> page) throws AppValidationException;
	
}
