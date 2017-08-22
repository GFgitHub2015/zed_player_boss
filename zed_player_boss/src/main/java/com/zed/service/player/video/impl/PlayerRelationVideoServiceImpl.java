package com.zed.service.player.video.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zed.common.util.CommUtil;
import com.zed.common.util.JsonUtils;
import com.zed.common.util.StringUtil;
import com.zed.dao.player.video.IPlayerRelationVideoDao;
import com.zed.domain.player.video.PlayerRelationVideo;
import com.zed.exception.AppValidationException;
import com.zed.service.player.logicalfile.PlayerLogicalPlayTimesService;
import com.zed.service.player.player.PlayerVideoDestFileService;
import com.zed.service.player.video.IPlayerRelationVideoService;
import com.zed.service.player.youtube.YouTuBeService;
import com.zed.system.page.Page;

@Service("playerRelationVideoService")
public class PlayerRelationVideoServiceImpl <T> implements IPlayerRelationVideoService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "playerRelationVideoDao")
	private IPlayerRelationVideoDao<PlayerRelationVideo> playerRelationVideoDao;
	@Resource(name = "youTuBeService")
	private YouTuBeService youTuBeService;
	@Resource(name = "playerVideoDestFileService")
	private PlayerVideoDestFileService playerVideoDestFileService;
	@Resource(name = "playerLogicalPlayTimesService")
	private PlayerLogicalPlayTimesService playerLogicalPlayTimesService;
	
	@Override
	public Page<PlayerRelationVideo> findByPage(Page<PlayerRelationVideo> page) throws AppValidationException {
		page = playerRelationVideoDao.findByPage(page);
		List<PlayerRelationVideo> result = page.getResult();
		List<PlayerRelationVideo> listToUpdate = new ArrayList<PlayerRelationVideo>();
		for (PlayerRelationVideo playerRelationVideo : result) {
			Integer flag = playerRelationVideo.getFlag();
			Integer origin = playerRelationVideo.getOrigin();
			//自动打标签
			if ((origin!=null && origin ==0) && (flag != null&& flag==0)) {
				updateFlag(playerRelationVideo);
				listToUpdate.add(playerRelationVideo);
			}
		}
		if (listToUpdate != null && !listToUpdate.isEmpty() && listToUpdate.size()>0) {
			playerRelationVideoDao.updateBatch(listToUpdate);
		}
		page.setResult(result);
		return page;
	}

	private void updateFlag(PlayerRelationVideo playerRelationVideo) {
		Boolean notAll = Boolean.FALSE;
		//判断是否是最热视频
		if (!notAll && playerLogicalPlayTimesService.isTheTopTenFile(playerRelationVideo.getFileId())) {
			playerRelationVideo.setFlag(2);
			notAll = Boolean.TRUE;
		}
		
		//判断是否是最新视频(在36小时之内属于最新)
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -36);//查找36小时以内的数据
		long timeThreshold = calendar.getTime().getTime();
		long startTimestamp = playerRelationVideo.getStartTime().getTime();
		if (!notAll && startTimestamp>timeThreshold) {
			playerRelationVideo.setFlag(1);
			notAll = Boolean.TRUE;
		}
		//既不是最新也不是最热
		if (!notAll) {
			playerRelationVideo.setFlag(-1);
		}
	}

	@Override
	public PlayerRelationVideo findByFileId(String fileId) throws AppValidationException {
		return playerRelationVideoDao.findByFileId(fileId);
	}

	public String replaceCdnUrl(String srcUrl, String cdnUrl) throws AppValidationException {
		if(CommUtil.isEmpty(srcUrl)) {
			return null;
		}
		if(CommUtil.isEmpty(cdnUrl)) {
			return srcUrl;
		}
		srcUrl = srcUrl.substring(srcUrl.lastIndexOf("/")+1,srcUrl.length());
		if(!cdnUrl.endsWith("/")) {
			cdnUrl =cdnUrl+"/";
		}
		return cdnUrl + srcUrl;
	}

	@Override
	public void addVideo(PlayerRelationVideo playerVideoIntegration) throws AppValidationException {
		if ((playerVideoIntegration.getOrigin()!=null && playerVideoIntegration.getOrigin() ==0) && (playerVideoIntegration.getFlag() != null&& playerVideoIntegration.getFlag()==0)) {
			updateFlag(playerVideoIntegration);
		}
		playerRelationVideoDao.add(playerVideoIntegration);
	}
	
	@Override
	public void deleteByUid(String... uid) throws AppValidationException {
		for (String id : uid) {
			playerRelationVideoDao.delete(id);
		}
	}

	/**
	 * @date : 2017年06月28日 上午11:57:28
	 * @param userId 用户Id,用于当影片无效时更新用户信息
	 * @param :  fileId 影片Id
	 * @author : X.Long
	 * @return
	 * @description : 
	 * a) 根据fileId验证DB是否存在相同的影片链接,如果存在提示客户端并且不能添加
	 * b) 根据fileId和key调用youtubu第三方API
	 * 如果没有返回信息或者返回的影片类型为直播类型时提示客户端URL无效则不执行添加操作并且修改影片状态为无效状态，如果返回信息则添加影片
	*/
	@Override
	public PlayerRelationVideo getYouTubeVideoDetail(String userId, String fileId) throws AppValidationException {
		PlayerRelationVideo playerVideoIntegration_ = playerRelationVideoDao.findByFileId(fileId);
		String result = youTuBeService.getDetailByVideoId(fileId);
		if(null != playerVideoIntegration_) {
			if(StringUtil.isBlank(result)) {
				playerVideoIntegration_.setUpdateUser(userId);
				playerVideoIntegration_.setUpdateTime(new Timestamp(new Date().getTime()));
				playerVideoIntegration_.setStatus(PlayerRelationVideo.Status.DISABLE.getStatus());
				playerRelationVideoDao.update(playerVideoIntegration_);
			}
			return playerVideoIntegration_;
		} else {
			playerVideoIntegration_ = getObjectFromItems(result);
			playerVideoIntegration_.setFileId(fileId);
			return playerVideoIntegration_;
		}
	}
	
	private PlayerRelationVideo getObjectFromItems(String str) {
		PlayerRelationVideo playerVideoIntegration = new PlayerRelationVideo();
		Map<String,Object> json = JsonUtils.getMapByJsonStr(str);
		Object items = json.get("items");
		if(items != null && items instanceof List) {
			List<Map<String, Object>> itemsList = (List<Map<String, Object>>) items;
			if(itemsList.size()>0){
				Map<String,Object> item = itemsList.get(0);
				Object snippetObj = item.get("snippet");
				if(snippetObj != null && snippetObj instanceof Map){
					Map<String,Object> snippet = (Map<String,Object>) snippetObj;
					playerVideoIntegration.setFileName(snippet.get("title").toString().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "")); //片名
					//描述,替换emoji表情,为utf8mb4编码
					//playerVideoIntegration.setDescription(snippet.get("description").toString().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", ""));
					Object thumbnailsObj = snippet.get("thumbnails");
					if(thumbnailsObj != null && thumbnailsObj instanceof Map){
						Map<String,Object> thumbnails = (Map<String,Object>) thumbnailsObj;
						if(thumbnails.get("standard") != null){
							Map<String,Object> thumbnailsStandard = (Map<String,Object>) thumbnails.get("standard");
							playerVideoIntegration.setIconUrl(thumbnailsStandard.get("url").toString());//海报
						}else{
							if(thumbnails.get("default") != null){
								Map<String,Object> thumbnailsdefault = (Map<String,Object>) thumbnails.get("default");
								playerVideoIntegration.setIconUrl(thumbnailsdefault.get("url").toString());//海报
							}
						}
					}
					Object liveBroadcastContentObj = snippet.get("liveBroadcastContent"); //判断是否直播依据
					if(liveBroadcastContentObj != null){
						playerVideoIntegration.setType(liveBroadcastContentObj.toString());
					}
				}

				Object contentDetailsObj = item.get("contentDetails");
				if(contentDetailsObj != null && contentDetailsObj instanceof Map){
					Map<String,Object> contentDetails = (Map<String,Object>) contentDetailsObj;
					Object dimensionObj = contentDetails.get("dimension");
					if(dimensionObj!=null){
						String dimensionStr = dimensionObj.toString().toLowerCase().replaceAll("d", "");
						if(!CommUtil.isEmpty(dimensionStr)){
							playerVideoIntegration.setDimensionType(Integer.valueOf((dimensionStr))); //2d,3d
						}
					}
				}
			}
		}
		return playerVideoIntegration;
	}

	@Override
	public List<PlayerRelationVideo> findAllRelationVideoByUids(String... uids) throws AppValidationException {
		List<PlayerRelationVideo> result = new ArrayList<PlayerRelationVideo>();
		for (String uid : uids) {
			PlayerRelationVideo playerRelationVideo = playerRelationVideoDao.findByUid(uid);
			result.add(playerRelationVideo);
		}
		return result;
	}

	
	@Override
	public void updateStatus(List<PlayerRelationVideo> playerVideoIntegrationList) throws AppValidationException {
		for (PlayerRelationVideo playerRelationVideo : playerVideoIntegrationList) {
			playerRelationVideoDao.update(playerRelationVideo);
		}
	}

	@Override
	public void updateVideo(PlayerRelationVideo playerVideoIntegration) throws AppValidationException {
		if ((playerVideoIntegration.getOrigin()!=null && playerVideoIntegration.getOrigin() ==0) && (playerVideoIntegration.getFlag() != null&& playerVideoIntegration.getFlag()==0)) {
			updateFlag(playerVideoIntegration);
		}
		playerRelationVideoDao.update(playerVideoIntegration);
	}

	@Override
	public PlayerRelationVideo findByUid(String uid) {
		return playerRelationVideoDao.findByUid(uid);
	}
	
}
