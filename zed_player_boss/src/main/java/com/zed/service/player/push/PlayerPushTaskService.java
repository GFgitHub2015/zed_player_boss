package com.zed.service.player.push;

import java.util.HashMap;

import com.zed.domain.player.push.PlayerPushTask;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerPushTaskService {
	
	public Page<HashMap> findByPage(Page<HashMap> page);
	//根据taskId获取对象
	public PlayerPushTask findById(String taskId) throws AppValidationException;
	//添加对象
	public void add(PlayerPushTask playerPushTask) throws AppValidationException;
	//修改对象
	public void update(PlayerPushTask playerPushTask) throws AppValidationException;
	//根据taskIds删除对象(可批量)
	public void delete(String ... taskIds) throws AppValidationException;
	//修改对象的状态
	public void updateStatus(PlayerPushTask playerPushTask) throws AppValidationException;
}
