package com.zed.service.player.push.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zed.dao.player.push.PlayerPushTaskScheduleDao;
import com.zed.domain.player.push.PlayerPushTaskSchedule;
import com.zed.exception.AppValidationException;
import com.zed.service.player.push.PlayerPushTaskScheduleService;
import com.zed.system.page.Page;

/**
 * @date : 2017年2月15日 上午11:38:53
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 推送调度
*/
@Service("playerPushTaskScheduleService")
public class PlayerPushTaskScheduleServiceImpl implements PlayerPushTaskScheduleService{
	
	@Resource(name="playerPushTaskScheduleDao")
	private PlayerPushTaskScheduleDao<PlayerPushTaskSchedule> playerPushTaskScheduleDao;
	/**
	 * @date : 2017年2月15日 上午11:41:39
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 列表
	*/
	@Override
	public Page<PlayerPushTaskSchedule> findByPage(Page<PlayerPushTaskSchedule> page){
		return playerPushTaskScheduleDao.findByPage(page);
	}
	@Override
	public PlayerPushTaskSchedule findById(String scheduleId) throws AppValidationException {
		return (PlayerPushTaskSchedule)playerPushTaskScheduleDao.findById(scheduleId);
	}
	@Override
	public void add(PlayerPushTaskSchedule playerPushTaskSchedule) throws AppValidationException {
		playerPushTaskScheduleDao.add(playerPushTaskSchedule);
	}
	@Override
	public void update(PlayerPushTaskSchedule playerPushTaskSchedule) throws AppValidationException {
		playerPushTaskScheduleDao.update(playerPushTaskSchedule);		
	}
	@Override
	public void delete(String... scheduleIds) throws AppValidationException {
		playerPushTaskScheduleDao.delete(scheduleIds);		
	}
	@Override
	public void updateStatus(PlayerPushTaskSchedule playerPushTaskSchedule) throws AppValidationException {
		playerPushTaskScheduleDao.update(playerPushTaskSchedule);		
	}
	@Override
	public List<String> findByTaskId(String taskId) throws AppValidationException {
		return playerPushTaskScheduleDao.findByTaskId(taskId);
	}
}
