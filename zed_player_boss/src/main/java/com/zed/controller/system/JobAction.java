package com.zed.controller.system;

import java.util.Map;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.listener.JobListener;
import com.zed.listener.Log;

/**
 * 定时任务管理
 */
@Controller("jobAction")
@Scope("prototype")
public class JobAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private Map<String, String> map = JobListener.getJobStatusMap();
	private String action;	//定时任务动作（停止，启动）
	private String jobName;	//定时任务名称
	
	@Override
	public String doExecute() {
		return null;
	}

	public String list() {
		return SUCCESS;
	}
	
	/**
	 * 修改定时任务状态
	 */
	public String change() {
		try {
			if (action != null && action.equals("start")) {
				JobListener.getScheduler().resumeJob(new JobKey(jobName, Scheduler.DEFAULT_GROUP));
			} else if (action != null && action.equals("stop")) {
				JobListener.getScheduler().pauseJob(new JobKey(jobName, Scheduler.DEFAULT_GROUP));
			}
			map.put(jobName, action);
		} catch (Exception ex) {
//			ex.printStackTrace();
			Log.getLogger(this.getClass()).error("JobAction[change] failed: ",ex);
		}
		return "redirect";
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
}
