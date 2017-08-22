package com.zed.job.player;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import com.zed.listener.Log;
import com.zed.service.player.promotioninfo.PlayerPromotionInfoService;
import com.zed.util.SpringContextUtils;

/**
 * PlayerPromotionInfo定时任务
 */
@Component
public class PlayerPromotionInfoJob implements Job {


	public void execute(JobExecutionContext arg0) throws JobExecutionException {
    	try{
    		PlayerPromotionInfoService playerPromotionInfoService = (PlayerPromotionInfoService) SpringContextUtils.getBean("playerPromotionInfoService");
//    		playerPromotionInfoService.updateJob();
    	}catch(Exception ex){
    		Log.getLogger(PlayerPromotionInfoJob.class).error("PlayerPromotionInfoJob[execute] error:"+ex.getMessage());
    	}
	}


	
}