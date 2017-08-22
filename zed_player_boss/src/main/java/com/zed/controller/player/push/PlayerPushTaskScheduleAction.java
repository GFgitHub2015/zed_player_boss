package com.zed.controller.player.push;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.push.PlayerPushTaskSchedule;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.push.PlayerPushTaskScheduleService;
import com.zed.system.ExceptionConstants;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("playerPushTaskScheduleAction")
@Scope(value="prototype")
public class PlayerPushTaskScheduleAction extends BaseAction {
	private static final long serialVersionUID = 6804033453952701276L;

	private PlayerPushTaskSchedule pushTaskSchedule;
	private Page<PlayerPushTaskSchedule> page = new Page<PlayerPushTaskSchedule>();
	private String scheduleId;							//调度标识
	private String title;								//标题
	private String taskId;								//任务标识
	private String status;								//调度状态1表示调度中，2表示调度成功，3表示调度失败，4 暂停，5 停止（停止的调度将不会自动再运行）
	private Long scheduleTimeout;						//调度超时时间(以小时为单位)
	private Long retries;								//重试次数
	private String targetStatement;					//查询发送目标的sql
	private String targetType;							//来源于zed_player_push_task
	private String targetId;							//来源于zed_player_push_task
	@Resource(name="playerPushTaskScheduleService")
	private PlayerPushTaskScheduleService playerPushTaskScheduleService;
	
	public String list(){
		try {
			HashMap map = new HashMap();
			page.setSorting(" a.create_time desc"); // 排序
			if (!CommUtil.isEmpty(title)) {
				map.put("title", title);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			page.setParamsMap(map);
			page = playerPushTaskScheduleService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String detail() {
		try {
			pushTaskSchedule = playerPushTaskScheduleService.findById(scheduleId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerPushTopicAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String doExecute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	public PlayerPushTaskSchedule getPushTaskSchedule() {
		return pushTaskSchedule;
	}
	public void setPushTaskSchedule(PlayerPushTaskSchedule pushTaskSchedule) {
		this.pushTaskSchedule = pushTaskSchedule;
	}
	public Page<PlayerPushTaskSchedule> getPage() {
		return page;
	}
	public void setPage(Page<PlayerPushTaskSchedule> page) {
		this.page = page;
	}
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getScheduleTimeout() {
		return scheduleTimeout;
	}
	public void setScheduleTimeout(Long scheduleTimeout) {
		this.scheduleTimeout = scheduleTimeout;
	}
	public Long getRetries() {
		return retries;
	}
	public void setRetries(Long retries) {
		this.retries = retries;
	}
	public String getTargetStatement() {
		return targetStatement;
	}
	public void setTargetStatement(String targetStatement) {
		this.targetStatement = targetStatement;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
