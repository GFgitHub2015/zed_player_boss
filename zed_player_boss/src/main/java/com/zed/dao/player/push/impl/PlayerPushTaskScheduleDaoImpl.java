package com.zed.dao.player.push.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zed.dao.player.base.abstractdao.AbstractPlayerPageDao;
import com.zed.dao.player.push.PlayerPushTaskScheduleDao;
import com.zed.domain.player.push.PlayerPushTaskSchedule;
import com.zed.exception.AppValidationException;

/**
 * @date : 2017年2月15日 上午11:34:23
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 推送调度
*/
@Repository("playerPushTaskScheduleDao")
public class PlayerPushTaskScheduleDaoImpl<T> extends AbstractPlayerPageDao<PlayerPushTaskSchedule> implements PlayerPushTaskScheduleDao<PlayerPushTaskSchedule> {

	@Override
	public List<String> findByTaskId(String taskId) throws AppValidationException {
		List<Object> objectList = findMore("findByTaskId", taskId);
		 List<String> result = new ArrayList<String>();
		for (Object object : objectList) {
			if (object instanceof String) {
				result.add((String)object);
			}
		}
		return result;
	}

}
