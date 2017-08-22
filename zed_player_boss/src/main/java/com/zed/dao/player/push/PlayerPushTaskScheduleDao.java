package com.zed.dao.player.push;

import java.io.Serializable;
import java.util.List;

import com.zed.dao.PageDao;
import com.zed.domain.player.push.PlayerPushTaskSchedule;
import com.zed.exception.AppValidationException;

/**
 * @date : 2017年2月15日 上午11:32:07
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 推送调度
*/
public interface PlayerPushTaskScheduleDao<T extends Serializable> extends PageDao<PlayerPushTaskSchedule>  {
	
	//根据taskId获取对象
	public List<String> findByTaskId(String taskId) throws AppValidationException;

}
