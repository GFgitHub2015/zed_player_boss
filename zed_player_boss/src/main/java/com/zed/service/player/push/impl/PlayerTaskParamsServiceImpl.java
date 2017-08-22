package com.zed.service.player.push.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.domain.player.push.PlayerPushTask;
import com.zed.domain.player.push.PlayerPushTopic;
import com.zed.domain.player.push.PlayerTaskParams;
import com.zed.exception.AppValidationException;
import com.zed.mongodb.player.push.PlayerPushTaskParamsMongoDao;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.push.PlayerPushTaskService;
import com.zed.service.player.push.PlayerPushTopicService;
import com.zed.service.player.push.PlayerTaskParamsService;
import com.zed.util.DateUtil;

import net.sf.json.JSONObject;

@Service("playerTaskParamsService")
public class PlayerTaskParamsServiceImpl implements PlayerTaskParamsService {
	private static final String SQL_CLUASE = "select CLIENT_ID as targetClientId,DEVICE_TOKEN as targetId from zed_player_client_info where DEVICE_TOKEN is not null";
	@Resource(name="playerPushTaskParamsMongoDao")
	private PlayerPushTaskParamsMongoDao playerPushTaskParamsMongoDao;
	@Resource(name="playerPushTopicService")
	private PlayerPushTopicService playerPushTopicService;
	@Resource(name="playerPushTaskService")
	private PlayerPushTaskService playerPushTaskService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;

	@Override
	public PlayerTaskParams findByTaskId(String taskId) throws AppValidationException {
		PlayerTaskParams ptp = playerPushTaskParamsMongoDao.getByTaskId(taskId);
		if (ptp != null) {
			PlayerPushTask ppt = playerPushTaskService.findById(taskId);
			if (ppt != null) {
				ptp.setStatus(ppt.getStatus());
				playerPushTaskParamsMongoDao.updatePlayerTaskParams(ptp);
			}
			return ptp;
		}
		return null;
	}

	@Override
	public void addPlayerTaskParams(PlayerTaskParams playerTaskParams) throws AppValidationException {
		PlayerPushTask ppt = getPlayerTaskObject(playerTaskParams);
		playerPushTaskService.add(ppt);
		playerPushTaskParamsMongoDao.addPlayerTaskParams(playerTaskParams);
	}

	private PlayerPushTask getPlayerTaskObject(PlayerTaskParams playerTaskParams) {
		PlayerPushTask ppt = playerPushTaskService.findById(playerTaskParams.getTaskId());
		if (ppt==null) {
			ppt = new PlayerPushTask();
			ppt.setTaskId(playerTaskParams.getTaskId());
			ppt.setCreateTime(playerTaskParams.getCreateTime());
			ppt.setCreater(playerTaskParams.getCreater());
		}else{
			ppt.setUpdater(playerTaskParams.getUpdater());
		}
		//设置推送内容
		ppt.setContent(playerTaskParams.getContent());
		//设置推送消息的描述（不包含在消息推送的内容中，只提供给运营人员查看）
		ppt.setDescription(playerTaskParams.getDescription());
		//设置消息提示类型
		ppt.setReminderType(playerTaskParams.getReminderType());
		String countryCode = playerTaskParams.getAreaCode();
		if (playerTaskParams.getPicked().equals("0")) {
			//立即推送 
			playerTaskParams.setPushTime(new Timestamp((new Date()).getTime()));
			ppt.setStartTime(playerTaskParams.getPushTime());
		}else{
			//设置指定国家的当地时区的指定时间
			String zoneId = playerCountryService.findZoneIdByCountryCode(countryCode);
			if (StringUtils.isNotBlank(zoneId)) {
				Date localDate = new Date(playerTaskParams.getPushTime().getTime());
				Date zonedate = DateUtil.string2TimezoneDefault(localDate, zoneId);
				ppt.setStartTime(new Timestamp(zonedate.getTime()));
			}
		}
		//设置推送标题
		ppt.setTitle(playerTaskParams.getTitle());
		//设置任务出事状态
		ppt.setStatus(PlayerPushTask.Status.WAITING.getStatus());
		playerTaskParams.setStatus(PlayerPushTask.Status.WAITING.getStatus());
		
		//设置推送图标
		if (playerTaskParams.getIcon().equals("1")) {
			ppt.setMessageIcon(playerTaskParams.getMessageIcon());
		}
		
		//设置托是哪个目标类型、生成目标群体查询SQL语句
		Map<String, String[]> mapWhereCluase = new HashMap<String, String[]>();
		if (playerTaskParams.getTarget()!=null) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("countryCode", countryCode);
			if (playerTaskParams.getTarget()==2) {
				//部分人推送
				if (playerTaskParams.getTopicType() != null) {
					if (playerTaskParams.getTopicType()==1) {
						//版本
						if (StringUtils.isNotBlank(playerTaskParams.getVersion())) {
							String [] versionArr = playerTaskParams.getVersion().trim().split(",");
							params.put("appVersion", versionArr);
							//组装SQL语句
							mapWhereCluase.put("APP_VERSION", versionArr);
						}
						
					} else if (playerTaskParams.getTopicType()==2) {
						//渠道
						if (StringUtils.isNotBlank(playerTaskParams.getChannel())) {
							String [] channelArr = playerTaskParams.getChannel().trim().split(",");
							params.put("channel", channelArr);
							//组装SQL语句
							mapWhereCluase.put("CHANNEL", channelArr);
						}
						
					} else if (playerTaskParams.getTopicType()==3) {
						//语言
						if (StringUtils.isNotBlank(playerTaskParams.getLanguage())) {
							String [] languageArr = playerTaskParams.getLanguage().trim().split(",");
							params.put("sysLanguage", languageArr);
							//组装SQL语句
							mapWhereCluase.put("SYS_LANGUAGE", languageArr);
						}
					}
					setTopicTypeAndCondition(ppt, params);
				}
			}else if(playerTaskParams.getTarget()==3){
				//指定一个人推送
				ppt.setTargetType(PlayerPushTask.TargetType.USER.getTargetType());
				if (StringUtils.isNotBlank(playerTaskParams.getUserId())) {
					mapWhereCluase.put("USER_ID", new String[]{playerTaskParams.getUserId()});
				}
			}else{
				setTopicTypeAndCondition(ppt, params);
			}
		}else{
			ppt.setTargetType(PlayerPushTask.TargetType.TOPIC.getTargetType());
		}
		//设置推送目标群体的查询sql语句
		ppt.setTargetStatement(getSqlCluase(mapWhereCluase));
		
		//获取消息扩展属性
		if (playerTaskParams.getOperation()!=null) {
			if (playerTaskParams.getOperation()==1) {
				//消息扩展属性,需组装成json格式存储 ，需根据type字段值组装
				JSONObject jsonObject = new JSONObject();
				if (playerTaskParams.getType().equals(PlayerPushTask.Type.MOVIE.getType())) {
					//影片推荐
					jsonObject.put("movieId", playerTaskParams.getFileId());
					jsonObject.put("link", "");
				} else if(playerTaskParams.getType().equals(PlayerPushTask.Type.PROMOTION.getType())) {
					//活动推荐
					jsonObject.put("promotionId", "");
					jsonObject.put("link", playerTaskParams.getUrl());
				}
				ppt.setMessageComment(jsonObject.toString());
				//设置推送任务类型
				ppt.setType(playerTaskParams.getType());
			}else{
				//设置推送任务类型
				ppt.setType(PlayerPushTask.Type.OPEN_APPLICATION.getType());
			}
		}
		return ppt;
	}

	private void setTopicTypeAndCondition(PlayerPushTask ppt, Map<String, Object> params) {
		List<PlayerPushTopic> pushTopicList = playerPushTopicService.findListByTopicTypeWithTypeValue(params);
		Set<Integer> topicIds = new HashSet<Integer>();
		StringBuilder sb = new StringBuilder();
		for (PlayerPushTopic playerPushTopic : pushTopicList) {
			sb.append(playerPushTopic.getTopicName());
			sb.append(",");
			topicIds.add(playerPushTopic.getTopicId());
		}
		String tName = sb.toString();
		if (StringUtils.isNotBlank(tName)) {
			tName = sb.toString().substring(0, sb.toString().lastIndexOf(","));
			ppt.setTopicName(tName);
			
			String[] topicNameArray = tName.split(",");
			if (topicNameArray.length==1) {
				//按主题推送时，当推送主题为1个时，targetType必须是3
				ppt.setTargetType(PlayerPushTask.TargetType.TOPIC.getTargetType());
			} else if (topicNameArray.length>1){
				//按主题推送时，当推送主题为多个时，targetType必须是4
				ppt.setTargetType(PlayerPushTask.TargetType.TOPIC_CONDITION.getTargetType());
				StringBuilder conditionSb = new StringBuilder();
				for (Integer topicId : topicIds) {
					conditionSb.append("'");
					conditionSb.append(topicId);
					conditionSb.append("'");
					conditionSb.append(" in");
					conditionSb.append(" topics");
					conditionSb.append(" || ");
				}
				// condition 的 格式 ："'dogs' in topics || 'cats' in topics"
				String conditions = conditionSb.toString().trim().substring(0, conditionSb.toString().trim().lastIndexOf("||")).trim();
				ppt.setCondition(conditions);
			}
		}else{
			ppt.setTargetType(PlayerPushTask.TargetType.TOPIC.getTargetType());
		}
	}
	
	private String getSqlWhereCluase(String key , String ...values){
		StringBuilder sb = new StringBuilder(" AND (");
		for (Object value : values) {
			sb.append(key+" = '"+value+"' OR ");
		}
		String sql = sb.toString().trim().substring(0, sb.toString().trim().lastIndexOf("OR"))+") ";
		return sql;
	}
	
	private String getSqlCluase(Map<String, String[]> map){
		StringBuilder sb = new StringBuilder(SQL_CLUASE.trim()+" ");
		if (map!=null&&!map.isEmpty()) {
			for (Map.Entry<String, String[]> entry : map.entrySet()) {
				sb.append(getSqlWhereCluase(entry.getKey(),entry.getValue()));
			}
		}
		return sb.toString();
	}

	@Override
	public void deletePlayerTaskParams(String ...taskIds) throws AppValidationException {
		playerPushTaskService.delete(taskIds);
		playerPushTaskParamsMongoDao.deleteByTaskId(taskIds);
	}

	@Override
	public void updatePlayerTaskParams(PlayerTaskParams playerTaskParams) {
		PlayerPushTask ppt = getPlayerTaskObject(playerTaskParams);
		playerPushTaskParamsMongoDao.updatePlayerTaskParams(playerTaskParams);
		playerPushTaskService.update(ppt);
	}
	
	
}
