package com.zed.service.player.push;

import java.util.List;

import com.zed.domain.player.push.PlayerPushTaskSchedule;
import com.zed.exception.AppValidationException;
import com.zed.system.page.Page;

public interface PlayerPushTaskScheduleService {
	public Page<PlayerPushTaskSchedule> findByPage(Page<PlayerPushTaskSchedule> page);
	//根据scheduleId获取对象
	public PlayerPushTaskSchedule findById(String scheduleId) throws AppValidationException;
	//根据taskId获取对象
	public List<String> findByTaskId(String taskId) throws AppValidationException;
	//添加对象
	public void add(PlayerPushTaskSchedule playerPushTaskSchedule) throws AppValidationException;
	//修改对象
	public void update(PlayerPushTaskSchedule playerPushTaskSchedule) throws AppValidationException;
	//根据scheduleIds删除对象(可批量)
	public void delete(String ... scheduleIds) throws AppValidationException;
	//修改对象的状态
	public void updateStatus(PlayerPushTaskSchedule playerPushTaskSchedule) throws AppValidationException;
}
