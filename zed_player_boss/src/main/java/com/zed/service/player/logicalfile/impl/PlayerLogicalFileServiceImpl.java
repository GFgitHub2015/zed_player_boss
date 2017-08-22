package com.zed.service.player.logicalfile.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.zed.api.subtitle.bean.VideoSubtitleBean;
import com.zed.api.subtitle.service.VideoSubtitleApiService;
import com.zed.common.ErrorCode;
import com.zed.common.exception.AppErrorException;
import com.zed.common.util.CommUtil;
import com.zed.dao.player.logicalfile.PlayerLogicalFileDao;
import com.zed.dao.player.video.PlayerVideoDestFileDao;
import com.zed.domain.account.account.Account;
import com.zed.domain.player.logicalfile.PlayerLogicalDownloadTimes;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.domain.player.logicalfile.PlayerLogicalPlayTimes;
import com.zed.domain.player.logicalfile.PlayerVideoResources;
import com.zed.domain.player.video.PlayerVideoDestFile;
import com.zed.exception.AppValidationException;
import com.zed.redis.player.video.PlayerVideoDestFileRedisDao;
import com.zed.service.account.account.AccountService;
import com.zed.service.common.signature.SignatureService;
import com.zed.service.player.es.PlayerVideoElasticsearch2Service;
import com.zed.service.player.logicalfile.PlayerLogicalDownloadTimesService;
import com.zed.service.player.logicalfile.PlayerLogicalFileService;
import com.zed.service.player.logicalfile.PlayerLogicalPlayTimesService;
import com.zed.service.player.player.PlayerVideoDestFileService;
import com.zed.system.page.Page;

@Service("playerLogicalFileService")
public class PlayerLogicalFileServiceImpl implements PlayerLogicalFileService{
	
	@Resource(name="playerLogicalFileDao")
	private PlayerLogicalFileDao playerLogicalFileDao;
	
	@Resource(name="playerVideoElasticsearch2Service")
	private PlayerVideoElasticsearch2Service playerVideoElasticsearch2Service;
	
	@Resource(name="signatureService")
	private SignatureService signatureService;
	
    @Resource(name="accountService")
    private AccountService accountService;
	
	@Resource(name="playerLogicalPlayTimesService")
	private PlayerLogicalPlayTimesService playerLogicalPlayTimesService;
	
	@Resource(name="playerLogicalDownloadTimesService")
	private PlayerLogicalDownloadTimesService playerLogicalDownloadTimesService;
	
	@Resource(name="playerVideoDestFileRedisDao")
	private PlayerVideoDestFileRedisDao playerVideoDestFileRedisDao;
	
	@Resource(name="playerVideoDestFileDao")
	private PlayerVideoDestFileDao playerVideoDestFileDao;
	
	@Resource(name="playerVideoDestFileService")
	private PlayerVideoDestFileService playerVideoDestFileService;
	
	@Resource(name = "videoSubtitleApiService")
	private VideoSubtitleApiService videoSubtitleApiService;
	
	@Override
	public PlayerLogicalFile findById(String playerLogicalFileId) throws AppValidationException {
		return (PlayerLogicalFile) playerLogicalFileDao.findById(playerLogicalFileId);
	}

	@Override
	public Page<HashMap> findByPage(Page<HashMap> page) throws AppValidationException {
//		findAllDataByPage(page.getPageNo(),page.getPageSize());
		page = playerVideoElasticsearch2Service.findByPage(page);
		List<Map<String, Object>> mapList = page.getObjectResult();
		if (mapList != null && !mapList.isEmpty() && mapList.size()>0) {
			List<HashMap> hashMapList = new ArrayList<HashMap>();
			for (Map<String, Object> map : mapList) {
				HashMap h = new HashMap();
				h.putAll(map);
				if (h.get("fileId") != null) {
					String fileId = (String) h.get("fileId");
					PlayerLogicalFile playerLogicalFile = this.findById(fileId);
					if (playerLogicalFile != null) {
						if (h.get("imgUrl")!=null) {
							h.put("imgUrl", h.get("imgUrl"));
						}
						if (h.get("duration")!=null) {
							Long duration = (Long) h.get("duration");
							h.put("duration", change(duration));
						}
						if (h.get("fileSize")!=null) {
							Long fileSize = (Long) h.get("fileSize");
							h.put("fileSize", convertFileSize(fileSize));
							
						}
						if (h.get("uid") != null) {
							Account account = accountService.findById((String)h.get("uid"));
							if(null != account){
								h.put("nickName", account.getNickName());
							}else{
								h.put("nickName", "未知");
							}
						}else{
							h.put("nickName", "未知");
						}
						
						List<Map<String, Object>> playUrlList = findVideoPlayUrls(playerLogicalFile.getSourceFileId());
						for (Map<String, Object> objectMap : playUrlList) {
							h.put(objectMap.get("descType").toString().equals("1")?"sd":"hd", objectMap.get("url"));
						}
						
						h.put("playTimes", this.getPlayTimes(fileId));
						h.put("downloadTimes", this.getDownloadTimes(fileId));
						h.put("subtitleCount", this.videoSubtitleApiService.getSubtitlesCountByFileId(fileId));
						h.put("subtitleFileCount", this.videoSubtitleApiService.getSubtitleFilesCountByFileId(fileId));
						hashMapList.add(h);
					}
				}
			}
			page.setResult(hashMapList);
		}else{
			page.setResult(new ArrayList<HashMap>());
		}
		return page;
	}
	
	private List<Map<String, Object>> findVideoPlayUrls(String sourceFileId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<PlayerVideoDestFile> list = findPlayerVideoDestFileList(sourceFileId);
		
		Float bitRate = 0f;
		int index = 0;
		for (PlayerVideoDestFile playerVideoDestFile : list) {
			if (playerVideoDestFile.getFileType()==PlayerVideoDestFile.Is.DEST.getStatus()) {
				bitRate += Float.valueOf(playerVideoDestFile.getBitRate());
				index++;
			}
		}
		bitRate = bitRate/index;
		
		for (PlayerVideoDestFile playerVideoDestFile : list) {
			if (playerVideoDestFile.getFileType()==PlayerVideoDestFile.Is.DEST.getStatus()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("destFileId", playerVideoDestFile.getFileId());
				String url = playerVideoDestFile.getFileUrl();
				if (!CommUtil.isEmpty(url)) {
					map.put("url", signatureService.signaturePlay(url));
					map.put("descType", Float.compare(Float.valueOf(playerVideoDestFile.getFrameWidth()),bitRate)<0?"1":"2");
				}
				result.add(map);
			}
		}
		return result;
	}

	private List<PlayerVideoDestFile> findPlayerVideoDestFileList(String sourceFileId){
		List<PlayerVideoDestFile> list = playerVideoDestFileRedisDao.findDestFileIdBySourceFileId(sourceFileId);
		if (list==null || list.size()<=0 || !list.isEmpty()) {
			refreshDestFileListData(sourceFileId);
		}
		return playerVideoDestFileRedisDao.findDestFileIdBySourceFileId(sourceFileId);
	}

	private void refreshDestFileListData(String sourceFileId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("sourceFileId", sourceFileId); 
			//提供播放的格式通过配置文件来配置
			map.put("fileSuffix", "mp4"); 
			List<PlayerVideoDestFile> playerVideoDestFileList = playerVideoDestFileDao.findBySourceFileIdWithFileSuffix(map);
			if (playerVideoDestFileList != null && playerVideoDestFileList.size()>0 && !playerVideoDestFileList.isEmpty()) {
				playerVideoDestFileRedisDao.addDestFileIdWithSourceFileId(playerVideoDestFileList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppErrorException(ErrorCode.CODE_ERROR.getCode(),
					ErrorCode.CODE_ERROR.getMessage());
		}
	}

	private Double getPlayTimes(String fileId) {
		PlayerLogicalPlayTimes  playerLogicalPlayTimes  = playerLogicalPlayTimesService.getPlayerPlayTimesByFileId(fileId);
		if (playerLogicalPlayTimes!=null) {
			return playerLogicalPlayTimes.getTimes();
		}
		return 0d;
	}

	private Double getDownloadTimes(String fileId) {
		PlayerLogicalDownloadTimes playerLogicalDownloadTimes = playerLogicalDownloadTimesService.getPlayerDownloadTimesByFileId(fileId);
		if (playerLogicalDownloadTimes!=null) {
			return playerLogicalDownloadTimes.getTimes();
		}
		return 0d;
	}
	
	private String convertFileSize(Long  fileSize){
		String fileSizeStr ="";
		if(fileSize==null){
			return fileSizeStr;
		}else{
			double  temp =fileSize/1024;
			if(temp<=1){
				return  fileSize+" KB";
			}else if(temp/1024<=1){
				BigDecimal ret =  new BigDecimal(temp);
				return  ret.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()+" MB";
			}else {
				BigDecimal ret =  new BigDecimal(temp/1024);
				return  ret.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()+" GB";
			}
		}
	}
	
	private String change(long second){  
		long h = 0;  
		long d = 0;  
		long s = 0;  
		long temp = second%3600;  
        if(second>3600){  
           h= second/3600;
           if (h<10) {
			
           }
           if(temp!=0){  
        	   if(temp>60){  
        		   d = temp/60;  
        		   if(temp%60!=0){  
        			   s = temp%60;  
        		   }  
        	   }else{  
        		   s = temp;  
        	   }  
           }  
      }else{  
          d = second/60;  
          if(second%60!=0){  
        	  s = second%60;
          }  
      }
      return (h>10?h:(h<=0?"00":("0"+h)))+":"+(d>10?d:(d<=0?"00":("0"+d)))+":"+(s>10?s:(s<=0?"00":"0"+s));  
	}

	@Override
	public void updateStatus(PlayerLogicalFile playerLogicalFile) throws AppValidationException {
			playerLogicalFileDao.updateStatus(playerLogicalFile);
			//删除ES文件
			PlayerVideoResources playerVideoResources2 = playerVideoElasticsearch2Service.findByFileId(playerLogicalFile.getFileId());
			if (playerVideoResources2 != null) {
				playerVideoResources2.setStatus(playerLogicalFile.getStatus());
				playerVideoElasticsearch2Service.add(playerVideoResources2);
			}
	}

	@Override
	public void updateStatusBatch(String status, String[] fileIds) throws AppValidationException {
		int lengthSplit=200;//拆分id数量,避免mysql条件限制
		if(fileIds!=null&&fileIds.length>lengthSplit){
			List<String> fileIdsList = new ArrayList<String>();
			String[] fileIdsNew = null; 
			for (int j = 1; j <= fileIds.length; j++) {
				if(j>0&&j%lengthSplit==0){
					fileIdsList.add(fileIds[j-1]);
					fileIdsNew = fileIdsList.toArray(new String[]{});
					updateStatusBatchSplit(status, fileIdsNew);
					fileIdsList.clear();
				}else if(j== fileIds.length){
					fileIdsList.add(fileIds[j-1]);
					fileIdsNew = fileIdsList.toArray(new String[]{});
					updateStatusBatchSplit(status, fileIdsNew);
					fileIdsList.clear();
				}else{
					fileIdsList.add(fileIds[j-1]);
				}
			}
		}
	}
	
	/**
	 * @date : 2017年7月6日 下午5:17:53
	 * @author : Iris.Xiao
	 * @param status
	 * @param fileIds
	 * @throws AppValidationException
	 * @description : 把修改数量拆分,避免fileIds数组过大
	*/
	public void updateStatusBatchSplit(String status, String[] fileIds) throws AppValidationException {
		List<PlayerLogicalFile> list = playerLogicalFileDao.findListByIds(fileIds);
		for (PlayerLogicalFile playerLogicalFile : list) {
			playerLogicalFile.setStatus(Integer.valueOf(status));
		}
		
		List<PlayerVideoResources> playerVideoResources2List = new ArrayList<PlayerVideoResources>();
		for (PlayerLogicalFile playerLogicalFile : list) {
			PlayerVideoResources playerVideoResources2 = playerVideoElasticsearch2Service.findByFileId(playerLogicalFile.getFileId());
			if (playerVideoResources2 != null) {
				playerVideoResources2.setStatus(Integer.valueOf(status));
				playerVideoResources2List.add(playerVideoResources2);
			}
		}
		if (playerVideoResources2List.size()>0&&!playerVideoResources2List.isEmpty()) {
			playerVideoElasticsearch2Service.add(playerVideoResources2List);
		}
		
		playerLogicalFileDao.updateBatch(list);
	}
	
	
	

	@Override
	public void updateShareFileStatus(String userId, Integer status) throws AppValidationException {
		Page<PlayerLogicalFile> page = new Page<PlayerLogicalFile>();
		page.getParamsMap().put("userId", userId);
		page = playerLogicalFileDao.findByPage(page);
		while(page.isHasNext()){
			if (page.getResult() != null && !page.getResult().isEmpty()) {
				List<PlayerLogicalFile> resultList = page.getResult();
				List<PlayerLogicalFile> folderList = new ArrayList<PlayerLogicalFile>();
				List<PlayerLogicalFile> fileList = new ArrayList<PlayerLogicalFile>();
				for (PlayerLogicalFile playerLogicalFile : resultList) {
					playerLogicalFile.setStatus(status);
					if (playerLogicalFile.getIsFile()==PlayerLogicalFile.Is.FOLDER.getStatus()) {
						folderList.add(playerLogicalFile);
					} else if (playerLogicalFile.getIsFile()==PlayerLogicalFile.Is.FILE.getStatus()) {
						fileList.add(playerLogicalFile);
					}
				}
			}
		}
		
	}

	@Override
    public void update(PlayerLogicalFile playerLogicalFile,Integer dimension) throws AppValidationException {
	    playerLogicalFileDao.update(playerLogicalFile);
        //删除ES文件
        PlayerVideoResources playerVideoResources2 = playerVideoElasticsearch2Service.findByFileId(playerLogicalFile.getFileId());

        //修改3d,2d标签
        if(dimension!=null&&playerVideoResources2!=null){
        	playerVideoResources2.setDimension(dimension);
        	//修改dest数据
        	if(playerLogicalFile!=null&&playerLogicalFile.getSourceFileId()!=null){
            	playerVideoDestFileService.updateVideoDestFileDimension(playerLogicalFile.getSourceFileId(), dimension);
        	}
        }
        if (playerVideoResources2 != null) {
            playerVideoResources2.setStatus(playerLogicalFile.getStatus());
            playerVideoResources2.setFileName(playerLogicalFile.getFileName());
            playerVideoElasticsearch2Service.add(playerVideoResources2);
        }
        
    }
	
    @Override
    public void update(PlayerLogicalFile playerLogicalFile, List<VideoSubtitleBean> subtitleBeans, String delSubtitleIds,Integer dimension)
            throws AppValidationException {
        // 更新logicalfile
        if (playerLogicalFile != null) {
            update(playerLogicalFile, dimension);
        }
        if (StringUtils.isNotBlank(delSubtitleIds)) { 
            // 删除字幕
            videoSubtitleApiService.deleteSubtitles(delSubtitleIds);
        }
        // 更新字幕
        if (subtitleBeans != null && subtitleBeans.size() > 0) {
            for (VideoSubtitleBean subtitleBean : subtitleBeans) {
                videoSubtitleApiService.saveSubtitle(subtitleBean);
            }
        }
    }

	@Override
	public void deleteUselessDataFromEs(long pageNo, long pageSize) throws AppValidationException {
		findAllDataByPage(pageNo, pageSize);
	}

	private void findAllDataByPage(long pageNo, long pageSize) {
		Set<String> fileIdSet = new HashSet<String>();
		Page<HashMap> page = new Page<HashMap>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page = playerVideoElasticsearch2Service.findByPage(page);
		List<Map<String, Object>> mapList = page.getObjectResult();
		if (mapList != null && !mapList.isEmpty() && mapList.size()>0) {
			List<HashMap> hashMapList = new ArrayList<HashMap>();
			for (Map<String, Object> map : mapList) {
				HashMap h = new HashMap();
				h.putAll(map);
				if (h.get("fileId") != null) {
					String fileId = (String) h.get("fileId");
					PlayerLogicalFile playerLogicalFile = this.findById(fileId);
					if (playerLogicalFile != null) {
						if (playerLogicalFile.getIsFile()==PlayerLogicalFile.Is.FOLDER.getStatus()) {
							fileIdSet.add(fileId);
						}
					}else{
						fileIdSet.add(fileId);
					}
				}
			}
		}
		if (fileIdSet != null && fileIdSet.size()>0) {
			String[] idsArray = fileIdSet.toArray(new String[fileIdSet.size()]);
			if (idsArray!=null && idsArray.length>0) {
				playerVideoElasticsearch2Service.deleteByIds(idsArray);
			}
		}
	}

	@Override
	public void updateStatusBatchByUserId(List<PlayerLogicalFile> playerLogicalFiles) throws AppValidationException {
		if(playerLogicalFiles != null  && !playerLogicalFiles.isEmpty()) {
			playerLogicalFileDao.updateStatusBatchByUserId(playerLogicalFiles);
		}
	}

}
