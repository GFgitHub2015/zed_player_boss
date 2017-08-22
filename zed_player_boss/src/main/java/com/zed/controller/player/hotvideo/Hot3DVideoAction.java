package com.zed.controller.player.hotvideo;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.zed.common.util.CommUtil;
import com.zed.common.util.DateUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.hotvideo.Hot3DVideo;
import com.zed.domain.player.logicalfile.PlayerVideoResources;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.es.PlayerVideoElasticsearch2Service;
import com.zed.service.player.hotvideo.Hot3DVideoService;
import com.zed.service.player.logicalfile.PlayerLogicalFileService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;

/**
 * @date : 2016年12月28日 下午5:06:28
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Controller("hot3DVideoAction")
@Scope(value="prototype")
public class Hot3DVideoAction extends BaseAction {

	private static final long serialVersionUID = 878028140782182480L;
	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.hotvideo");
	private static final String cdnUrl = SystemConfig.getProperty("image.cdn.url");//图片cdn加速地址
	
	private String uid;
	private String videoName ;//片名
	private String fileId ;//影片ID
	private String playTime ;//播放次数
	private String iconUrl ;//海报地址
	private Timestamp startTime ;//上线时间
	private Long videoState ;//状态
	private String hotReason ;//推荐理由
	private String createUser ;//创建人
	private Timestamp createTime ;//创建时间
	private Hot3DVideo hotVideo;
	private Page<Hot3DVideo> page = new Page<Hot3DVideo>();
	private String playSort ;//排序
	private String downSort ;//排序
	private String tagType ;
	private String areaCode;
	private File imageUpload;
	private String imageUploadContentType; 								// 上传的文件的数据类型
	private String imageUploadFileName; 											// 上传的文件的名称 
	private List<PlayerCountry> playerCountryList;							//所有的地区国家码集合
	
	@Resource(name = "hot3DVideoServiceImpl")
	private Hot3DVideoService hotVideoService;
	
	@Autowired
	private UploadService uploadService;

	@Resource(name="playerLogicalFileService")
	private PlayerLogicalFileService playerLogicalFileService;

	@Resource(name="playerVideoElasticsearch2Service")
	private PlayerVideoElasticsearch2Service playerVideoElasticsearch2Service;
	
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	
	/**
	 * @date : 2016年12月28日 下午5:10:30
	 * @author : Iris.Xiao
	 * @return
	 * @description : 列表
	*/
	public String list(){
		List<String> orderby= new ArrayList<String>();
		Map<String,Object> map = new HashMap<String,Object>();
		//添加排序
		if(!CommUtil.isEmpty(playSort)){
			orderby.add("b.times "+playSort);//播放次数排序
		}
		if(!CommUtil.isEmpty(downSort)){
			orderby.add("c.times "+downSort);//下载次数排序
		}
		orderby.add(" a.CREATE_TIME DESC");//默认创建时间排序
		page.setSorting(StringUtils.join(orderby, ","));
		if (!CommUtil.isEmpty(videoName)) {
			map.put("videoNameLike", videoName);
		}
		if (videoState!=null) {
			map.put("videoState", String.valueOf(videoState));
		}
		if (tagType!=null) {
			map.put("tagType", tagType);
		}
		if (areaCode!=null) {
			map.put("countryCode", areaCode);
		}
		page.setParamsMap(map);
		hotVideoService.findByPage(page);
		playerCountryList = playerCountryService.findAll();
		return SUCCESS;
	}

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 详情
	*/
	public String detail(){
		hotVideo = hotVideoService.getHotViedeo(uid);
		playerCountryList = playerCountryService.findAll();
		return SUCCESS;
	}

	/**
	 * @date : 2016年12月28日 下午5:10:14
	 * @author : Iris.Xiao
	 * @return
	 * @description : 添加
	*/
	public String addHotVideo(){
		String fileId = hotVideo.getFileId();
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Hot3DVideo result = hotVideoService.getHotViedeoByFileId(hotVideo.getFileId());
			if (CommUtil.isEmpty(fileId)||result!=null) {
				setResultDispatch(GConstantAlert.GS_HOT_VIDEO_EXISTS, GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			if (imageUpload != null) {
				//上传热词背景图片
				String url = uploadService.put(imageUpload,remotePath, imageUploadFileName);
				if (!CommUtil.isEmpty(url)) {
					url = hotVideoService.replaceCdnUrl(url, cdnUrl);
					//添加热词背景图片
					hotVideo.setIconUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}else if (!CommUtil.isEmpty(iconUrl)){
				hotVideo.setIconUrl(iconUrl);
			}
			else{
				setResultDispatch(GConstantAlert.GS_HOT_VIDEO_ICON_ERROR, GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			hotVideo.setCreateUser(sessionAdmin.getAdminId());
			hotVideo.setCreateTime(DateUtil.getCurTime());
			hotVideo.setVideoState(0L);
			if(!CommUtil.isEmpty(areaCode)&&areaCode.contains(",")){
				areaCode = areaCode.substring(0, areaCode.lastIndexOf(","));
			}
			String[] areaCodes = areaCode.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String ac : areaCodes) {//添加多个国家,这里备用,现在只有一个国家
				hotVideo.setUid(CommUtil.getUUID());
				hotVideo.setCountryCode(ac);
				hotVideoService.addHotViedeo(hotVideo);
				OperationLog oLog = new OperationLog(getIp(), "热门3D影片推荐列表", "添加:" + hotVideo.getFileId()+", 国家码："+ac ,new Date() , sessionAdmin.getAdminId());
				logList.add(oLog);
			}
			if (!logList.isEmpty()&&logList.size()>0) {
				logService.addBatch(logList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("Hot3DVideoAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * @date : 2016年12月28日 下午5:10:08
	 * @author : Iris.Xiao
	 * @return
	 * @description : 删除
	*/
	public String deleteHotVideo(){

		Admin sessionAdmin = null;
		try {
			Date newdate = new Date();
			sessionAdmin = getSessionAdmin();
			String[] uids = uid.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : uids) {
				OperationLog oLog = new OperationLog(getIp(), "热门3D影片推荐列表", "删除:" +id ,newdate , sessionAdmin.getAdminId());
				logList.add(oLog);
			}
			hotVideoService.deleteHotViedeo(uids);
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("Hot3DVideoAction[deleteHotVideo] failed: ",ex);
			}
			return ERROR;
		}
	}

	/**
	 * @date : 2016年12月28日 下午5:10:00
	 * @author : Iris.Xiao
	 * @return
	 * @description : 修改
	*/
	public String updateHotVideo(){

		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			hotVideo.setUpdateTime(DateUtil.getCurTime());
			hotVideo.setUpdateUser(sessionAdmin.getAdminId());
			if (imageUpload != null) {
				//上传热词背景图片
				String url = uploadService.put(imageUpload,remotePath, imageUploadFileName);
				if (!CommUtil.isEmpty(url)) {
					url = hotVideoService.replaceCdnUrl(url, cdnUrl);
					//添加热词背景图片
					hotVideo.setIconUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}else if (!CommUtil.isEmpty(iconUrl)){
				hotVideo.setIconUrl(iconUrl);
			}
			hotVideoService.updateHotViedeo(hotVideo);
			OperationLog oLog = new OperationLog(getIp(), "热门3D影片推荐列表", "修改:" + hotVideo.getFileId() ,new Date() , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("Hot3DVideoAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 添加跳转
	*/
	public String addPage(){
		playerCountryList = playerCountryService.findAll();
		return SUCCESS;
	}

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 修改跳转
	*/
	public String updatePage(){
		hotVideo = hotVideoService.getHotViedeo(uid);
		playerCountryList = playerCountryService.findAll();
		return SUCCESS;
	}
	

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 状态修改
	*/
	public String updateState(){
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] uids = uid.split(",");
			List<Hot3DVideo> listToUpdate = hotVideoService.findAllHotVideoByUidsWithStatus(uids);
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (Hot3DVideo hotVideoLocal : listToUpdate) {
				hotVideoLocal.setVideoState(videoState);
				hotVideoLocal.setUpdateTime(new Timestamp(newdate.getTime()));
				hotVideoLocal.setUpdateUser(sessionAdmin.getAdminId());
				OperationLog oLog = new OperationLog(getIp(), "热门3D影片推荐列表", "状态修改:" + hotVideoLocal.getVideoName()+(videoState.equals("1")?"上架":"下架"),newdate , sessionAdmin.getAdminId());
				logList.add(oLog);
				
			}
			/*HotVideo hotVideoLocal = hotVideoService.getHotViedeo(uid);
			if(CommUtil.isEmpty(uid)||videoState==null){
				throw new Exception("参数错误");
			}*/
			//上线之前先把其它的下线,只允许一条上线
//			if(videoState==1){
//				Page<Hot3DVideo> upStatusPage = new Page<Hot3DVideo>();
//				upStatusPage.getParamsMap().put("videoState", "1");
//				hotVideoService.findByPage(upStatusPage);
//				List<Hot3DVideo> upStatusList = upStatusPage.getResult();
//				if(upStatusList!=null&&upStatusList.size()>0){
//					for (Hot3DVideo hot3dVideo : upStatusList) {
//						hot3dVideo.setVideoState(-1L);//设置为下线
//					}
//					hotVideoService.updateHotViedeo(upStatusList);
//				}
//			}
			
			hotVideoService.updateHotViedeo(listToUpdate);
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			if (videoState==1) {
				setSuccessDispatch(GConstantAlert.GS_UP_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_DOWN_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("HotVideoAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}
	}

	/**
	 * @date : 2017年1月5日 下午3:29:30
	 * @author : Iris.Xiao
	 * @throws IOException
	 * @description : 根据fileid找到资源信息,验证是否有该资源
	*/
	public void getResourceInfo() throws IOException{
    	response.setContentType("text/html;charset=utf-8");
    	String returnResult="";
//    	PlayerVideoResources resource =null;
    	PlayerVideoResources resource2 =null;
    	try {
//    		PlayerLogicalFile pvr = playerLogicalFileService.findById(fileId);
//    		if(pvr!=null){
//    	    	resource = playerVideoResourcesService.findById(pvr.getSourceFileId());
//    		}
    		resource2 = playerVideoElasticsearch2Service.findByFileId(fileId);
//    		Page<HashMap> curPage = new Page<HashMap>();
//    		curPage = playerVideoElasticsearch2Service.findByPage(curPage);
//    		List lit = curPage.getResult();
//    		System.out.println(lit.size());
		} catch (Exception e) {
			e.printStackTrace();
			Log.getLogger(this.getClass()).error("getResourceInfo failed: "+fileId,e);
		}
    	returnResult = JSON.toJSONString(resource2);
        response.getWriter().println(returnResult);
        response.getWriter().flush();
        response.getWriter().close();
    }
	
	@Override
	public String doExecute() throws Exception {
		return null;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getPlayTime() {
		return playTime;
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Long getVideoState() {
		return videoState;
	}

	public void setVideoState(Long videoState) {
		this.videoState = videoState;
	}

	public String getHotReason() {
		return hotReason;
	}

	public void setHotReason(String hotReason) {
		this.hotReason = hotReason;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Hot3DVideo getHotVideo() {
		return hotVideo;
	}

	public void setHotVideo(Hot3DVideo hotVideo) {
		this.hotVideo = hotVideo;
	}

	public Page<Hot3DVideo> getPage() {
		return page;
	}

	public void setPage(Page<Hot3DVideo> page) {
		this.page = page;
	}


	public File getImageUpload() {
		return imageUpload;
	}

	public void setImageUpload(File imageUpload) {
		this.imageUpload = imageUpload;
	}

	public String getImageUploadContentType() {
		return imageUploadContentType;
	}

	public void setImageUploadContentType(String imageUploadContentType) {
		this.imageUploadContentType = imageUploadContentType;
	}

	public String getImageUploadFileName() {
		return imageUploadFileName;
	}

	public void setImageUploadFileName(String imageUploadFileName) {
		this.imageUploadFileName = imageUploadFileName;
	}

	public String getPlaySort() {
		return playSort;
	}

	public void setPlaySort(String playSort) {
		this.playSort = playSort;
	}

	public String getDownSort() {
		return downSort;
	}

	public void setDownSort(String downSort) {
		this.downSort = downSort;
	}

	public String getTagType() {
		return tagType;
	}

	public void setTagType(String tagType) {
		this.tagType = tagType;
	}

	public List<PlayerCountry> getPlayerCountryList() {
		return playerCountryList;
	}

	public void setPlayerCountryList(List<PlayerCountry> playerCountryList) {
		this.playerCountryList = playerCountryList;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
