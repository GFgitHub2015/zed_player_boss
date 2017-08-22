package com.zed.mongodb.player.push;

import java.util.Map;

public interface PlayerPushTargetMongoDao {
	/**
	 * 根据调度id获取所有的该调度的记录数量
	 * @param scheduleId
	 * @return
	 */
	public Long findCountByScheduleId(String scheduleId);
	/**
	 * 根据调度id获取所有的该调度的记录数量
	 * @param scheduleId
	 * @return
	 */
	public Long findCountByScheduleId(String[] scheduleIds);
	
	/**
	 * 根据调度id集合批量获取该调度的所有的调度的记录数量
	 * @param scheduleIds
	 * @return
	 */
	public Map<String, Long> findCountMapByScheduleIds(String [] scheduleIds);

}
