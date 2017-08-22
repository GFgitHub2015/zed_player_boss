package com.zed.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.zed.job.player.PlayerPromotionInfoJob;

public class JobListener implements ServletContextListener {
	
	private static Scheduler scheduler = null;
	private static Map<String, String> jobStatusMap = new HashMap<String, String>();
	private static String playerPromotionInfoTimer = null;//调度时间
	
	/**
	 * 初始化调度
	 */
	public void contextInitialized(ServletContextEvent context) {
		try {
			playerPromotionInfoTimer = SystemConfig.getProperty("player.total.promotioninfo.timer");
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
			System.out.println("--------start task--------");
//			addPlayerPromotionInfoJob();			//活动推送信息定时任务
			Log.getLogger(this.getClass()).debug("run timer task......");
		} catch (SchedulerException ex) {
			Log.getLogger(this.getClass()).error("JobListener[contextInitialized] failed: " + ex.getMessage());
		}
	}	
	/**
	 * 活动推送信息
	 */
	private void addPlayerPromotionInfoJob(){
		try {
			JobDetail jobDetail = JobBuilder.newJob(PlayerPromotionInfoJob.class).withIdentity("playerPromotionInfoJob", Scheduler.DEFAULT_GROUP).build();
			CronScheduleBuilder verifyTime = CronScheduleBuilder.cronSchedule(playerPromotionInfoTimer);
			Trigger trigger =  TriggerBuilder.newTrigger().withIdentity("trigger2").startNow().withSchedule(verifyTime).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException ex) {
			Log.getLogger(this.getClass()).error("JobListener[addPlayerPromotionInfoJob] error: " + ex.getMessage());
		}
		jobStatusMap.put("playerPromotionInfoJob", "start");
	}
	
	
	/**
	 * 定时任务销毁停止执行
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			scheduler.shutdown();
		} catch (SchedulerException ex) {
			Log.getLogger(this.getClass()).info("JobListener[contextDestroyed] error: " + ex.getMessage());
		} 
	}
	
	public static Scheduler getScheduler() {
		return scheduler;
	}

	public static Map<String, String> getJobStatusMap() {
		return jobStatusMap;
	}
}
