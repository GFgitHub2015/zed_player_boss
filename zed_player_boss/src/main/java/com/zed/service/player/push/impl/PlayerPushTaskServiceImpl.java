package com.zed.service.player.push.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.dao.player.push.PlayerPushTaskDao;
import com.zed.domain.player.push.PlayerPushTask;
import com.zed.domain.player.push.PlayerPushTaskSchedule;
import com.zed.domain.player.push.PlayerTaskParams;
import com.zed.exception.AppValidationException;
import com.zed.mongodb.player.push.PlayerPushTargetMongoDao;
import com.zed.service.player.push.PlayerPushTaskScheduleService;
import com.zed.service.player.push.PlayerPushTaskService;
import com.zed.service.player.push.PlayerTaskParamsService;
import com.zed.system.page.Page;

/**
 * @date : 2017年2月15日 上午11:38:34
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 推送
*/
@Service("playerPushTaskService")
public class PlayerPushTaskServiceImpl implements PlayerPushTaskService{

	@Resource(name="playerPushTaskDao")
	private PlayerPushTaskDao playerPushTaskDao;
	@Resource(name="playerTaskParamsService")
	private PlayerTaskParamsService playerTaskParamsService;
	@Resource(name="playerPushTaskScheduleService")
	private PlayerPushTaskScheduleService playerPushTaskScheduleService;
	@Resource(name="playerPushTargetMongoDao")
	private PlayerPushTargetMongoDao playerPushTargetMongoDao;
	/**
	 * @date : 2017年2月15日 上午11:41:39
	 * @author : Iris.Xiao
	 * @param page
	 * @description : 列表
	*/
	public Page<HashMap> findByPage(Page<HashMap> page){
		page = playerPushTaskDao.findByPage(page);
		List<HashMap> result = page.getResult();
		Map<String, Long> mapForCount = new HashMap<String, Long>();
		for (HashMap hashMap : result) {
			if (hashMap.containsKey("task_id")) {
				String taskId = hashMap.get("task_id").toString();
				Long count = 0l;
				if (StringUtils.isNotBlank(taskId)) {
					List<String> scheduleIdList = playerPushTaskScheduleService.findByTaskId(taskId);
					if (scheduleIdList != null && !scheduleIdList.isEmpty() && scheduleIdList.size()>0) {
						String[] scheduleIdArrays = scheduleIdList.toArray(new String[scheduleIdList.size()]);
						count = playerPushTargetMongoDao.findCountByScheduleId(scheduleIdArrays);
					}
				}
				mapForCount.put(taskId, count);
			}
		}
		
		for (HashMap hashMap : result) {
			if (hashMap.containsKey("task_id")) {
				String taskId = hashMap.get("task_id").toString();
				if (StringUtils.isNotBlank(taskId)) {
					if (mapForCount.containsKey(taskId)) {
						hashMap.put("open_count", mapForCount.get(taskId));
					}
					PlayerTaskParams ptp = playerTaskParamsService.findByTaskId(taskId);
					if (ptp != null) {
						hashMap.put("target", ptp.getTarget());
					}
				}
			}
		}
		return playerPushTaskDao.findByPage(page);
	}
	@Override
	public PlayerPushTask findById(String taskId) throws AppValidationException {
		return (PlayerPushTask) playerPushTaskDao.findById(taskId);
	}
	@Override
	public void add(PlayerPushTask playerPushTask) throws AppValidationException {
		playerPushTaskDao.add(playerPushTask);		
	}
	@Override
	public void update(PlayerPushTask playerPushTask) throws AppValidationException {
		playerPushTaskDao.update(playerPushTask);		
	}
	@Override
	public void delete(String... taskIds) throws AppValidationException {
		playerPushTaskDao.delete(taskIds);		
	}
	@Override
	public void updateStatus(PlayerPushTask playerPushTask) throws AppValidationException {
		playerPushTaskDao.update(playerPushTask);		
	}
}
