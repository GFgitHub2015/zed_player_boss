package com.zed.controller.iosplayer.video;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.zed.common.util.CommUtil;
import com.zed.common.util.DateUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.iosplayer.video.IosPlayerVideo;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.iosplayer.video.IosPlayerVideoService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;

/**
 * @date : 2017年5月9日 下午3:26:01
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Controller("iosPlayerVideoAction")
@Scope(value="prototype")
public class IosPlayerVideoAction extends BaseAction  {
	private static final long serialVersionUID = -5260875291441683241L;
	private IosPlayerVideo video ;
	private Page<IosPlayerVideo> page = new Page<IosPlayerVideo>();

	private String uid ;//主键
	private String videoId ;//影片ID
	private String videoName ;//片名
	private String videoUrl ;//播放地址
	private Long videoState ;//状态,1:上架,-1下架
	private String videoDuration ;//时长
	private String iconUrl ;//海报地址
	private String createUser ;//创建人
	private Timestamp createTime ;//创建时间
	private String updateUser ;//修改人
	private Timestamp updateTime ;//修改时间
	private String countryCode ;//国家码
	private Long dimension;

	@Resource(name="iosPlayerVideoService")
	private IosPlayerVideoService iosPlayervideoService;
	
	/**
	 * @date : 2017年5月9日 下午3:32:18
	 * @author : Iris.Xiao
	 * @return
	 * @description : 
	*/
	public String list(){
		try {
			List<String> orderby= new ArrayList<String>();
			Map<String,Object> map = new HashMap<String,Object>();
			orderby.add(" a.CREATE_TIME DESC");//默认创建时间排序
			page.setSorting(StringUtils.join(orderby, ","));
			if (!CommUtil.isEmpty(videoName)) {
				map.put("videoName", videoName);
			}
			if (videoUrl!=null) {
				map.put("videoUrl", String.valueOf(videoUrl));
			}
			if (videoState!=null) {
				map.put("videoState", String.valueOf(videoState));
			}
			if (dimension!=null) {
				map.put("dimensionType", String.valueOf(dimension));
			}
			map.put("recommendType", String.valueOf(1));
			page.setParamsMap(map);
			iosPlayervideoService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}


	/**
	 * @date : 2017年5月9日 下午3:32:18
	 * @author : Iris.Xiao
	 * @return
	 * @description : 
	*/
	public String list3d(){
		try {
			List<String> orderby= new ArrayList<String>();
			Map<String,Object> map = new HashMap<String,Object>();
			orderby.add(" a.CREATE_TIME DESC");//默认创建时间排序
			page.setSorting(StringUtils.join(orderby, ","));
			if (!CommUtil.isEmpty(videoName)) {
				map.put("videoName", videoName);
			}
			if (videoUrl!=null) {
				map.put("videoUrl", String.valueOf(videoUrl));
			}
			if (videoState!=null) {
				map.put("videoState", String.valueOf(videoState));
			}
			if (dimension!=null) {
				map.put("dimensionType", String.valueOf(dimension));
			}
			map.put("recommendType", String.valueOf(0));
			page.setParamsMap(map);
			iosPlayervideoService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[list3d] failed: ",ex);
			}
			return ERROR;
		}
	}
	

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 详情
	*/
	public String detail(){
		try {
			video = iosPlayervideoService.getVideo(uid);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 详情
	*/
	public void getYouTubeVideoDetail(){
		try {
			video = iosPlayervideoService.getYouTubeVideoDetail(videoId);
	    	response.setContentType("text/html;charset=utf-8");
	    	String returnResult="";
	    	returnResult = JSON.toJSONString(video);
	        response.getWriter().println(returnResult);
	        response.getWriter().flush();
	        response.getWriter().close();
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[detail] failed: ",ex);
			}
		}
	}
	

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 详情
	*/
	public void updateDimension(){
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
	    	response.setContentType("text/html;charset=utf-8");
			Map<String,String> result = new HashMap<String,String>();
			result.put("result", "true");
			if(CommUtil.isEmpty(uid)){
				result.put("result", "false");
				result.put("message", "参数为空!");
			}else{
				IosPlayerVideo video = iosPlayervideoService.getVideo(uid);
				video.setUpdateUser(sessionAdmin.getAdminId());
				video.setUpdateTime(DateUtil.getCurTime());
				video.setDimensionType(dimension);
				iosPlayervideoService.updateViedeo(video);
				result.put("result", "true");
				result.put("message", "修改成功!!");
			}
	    	String returnResult="";
	    	returnResult = JSON.toJSONString(result);
	        response.getWriter().println(returnResult);
	        response.getWriter().flush();
	        response.getWriter().close();
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[updateDimension] failed: ",ex);
			}
		}
	}

	/**
	 * @date : 2016年12月28日 下午5:10:14
	 * @author : Iris.Xiao
	 * @return
	 * @description : 添加
	*/
	public String addVideo(){
		String videoId = video.getVideoId();
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			if(CommUtil.isEmpty(videoId)){
				setResultDispatch("参数错误!", GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			IosPlayerVideo result = iosPlayervideoService.getVideoByVideoId(videoId);
			if (result!=null) {
				setResultDispatch(GConstantAlert.GS_HOT_VIDEO_EXISTS, GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			if(video.getStartTime()==null){
				video.setStartTime(DateUtil.getCurTime());
			}
			if(CommUtil.isEmpty(video.getCountryCode())){
				video.setCountryCode("0");
			}
			video.setUid(CommUtil.getUUID());
			video.setCreateUser(sessionAdmin.getAdminId());
			video.setCreateTime(DateUtil.getCurTime());
			video.setVideoState(1L);
			iosPlayervideoService.addVideo(video);
			iosPlayervideoService.deletePageList("0",null);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[addVideo] failed: ",ex);
			}
			return ERROR;
		}
	}
	

	/**
	 * @date : 2016年12月28日 下午5:10:14
	 * @author : Iris.Xiao
	 * @return
	 * @description : 添加
	*/
	public String addVideo3d(){
		String videoId = video.getVideoId();
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			if(CommUtil.isEmpty(videoId)){
				setResultDispatch("参数错误!", GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST3D_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			IosPlayerVideo result = iosPlayervideoService.getVideoByVideoId(videoId);
			if (result!=null) {
				setResultDispatch(GConstantAlert.GS_HOT_VIDEO_EXISTS, GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST3D_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			if(video.getStartTime()==null){
				video.setStartTime(DateUtil.getCurTime());
			}
			if(CommUtil.isEmpty(video.getCountryCode())){
				video.setCountryCode("0");
			}
			video.setUid(CommUtil.getUUID());
			video.setCreateUser(sessionAdmin.getAdminId());
			video.setCreateTime(DateUtil.getCurTime());
			video.setVideoState(1L);
			iosPlayervideoService.addVideo(video);
			iosPlayervideoService.delete3dPageList("0",null);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST3D_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[addVideo3d] failed: ",ex);
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
	public String deleteVideo(){

		Admin sessionAdmin = null;
		try {
			Date newdate = new Date();
			sessionAdmin = getSessionAdmin();
			String[] uids = uid.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : uids) {
				OperationLog oLog = new OperationLog(getIp(), "热门影片推荐列表", "删除:" +id ,newdate , sessionAdmin.getAdminId());
				logList.add(oLog);
			}
			iosPlayervideoService.deleteViedeo(uids);
			iosPlayervideoService.deletePageList("0",null);
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[deleteVideo] failed: ",ex);
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
	public String delete3dVideo(){

		Admin sessionAdmin = null;
		try {
			Date newdate = new Date();
			sessionAdmin = getSessionAdmin();
			String[] uids = uid.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : uids) {
				OperationLog oLog = new OperationLog(getIp(), "热门影片推荐列表", "删除:" +id ,newdate , sessionAdmin.getAdminId());
				logList.add(oLog);
			}
			iosPlayervideoService.deleteViedeo(uids);
			iosPlayervideoService.delete3dPageList("0",null);
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST3D_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerVideoAction[delete3dVideo] failed: ",ex);
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
	public String updateVideo(){

		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			video.setUpdateTime(DateUtil.getCurTime());
			video.setUpdateUser(sessionAdmin.getAdminId());
			iosPlayervideoService.updateViedeo(video);
			iosPlayervideoService.deletePageList("0",video);
			OperationLog oLog = new OperationLog(getIp(), "热门影片推荐列表", "修改:" + video.getVideoId() ,new Date() , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VideoAction[add] failed: ",ex);
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
		return SUCCESS;
	}

	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 添加跳转
	*/
	public String addPage3d(){
		return SUCCESS;
	}
	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 修改跳转
	*/
	public String updatePage(){
		video = iosPlayervideoService.getVideo(uid);
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
			List<IosPlayerVideo> listToUpdate = iosPlayervideoService.findAllVideoByUids(uids);
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (IosPlayerVideo videoLocal : listToUpdate) {
				videoLocal.setVideoState(videoState);
				videoLocal.setUpdateTime(new Timestamp(newdate.getTime()));
				videoLocal.setUpdateUser(sessionAdmin.getAdminId());
				OperationLog oLog = new OperationLog(getIp(), "热门影片推荐列表", "状态修改:" + videoLocal.getVideoName()+(videoState.equals("1")?"上架":"下架"),newdate , sessionAdmin.getAdminId());
				logList.add(oLog);
				
			}
			iosPlayervideoService.updateVideo(listToUpdate);
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			if (videoState==1) {
				setSuccessDispatch(GConstantAlert.GS_UP_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_DOWN_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VideoAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}
	}



	/**
	 * @date : 2016年12月28日 下午5:10:21
	 * @author : Iris.Xiao
	 * @return
	 * @description : 状态修改
	*/
	public String update3dState(){
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] uids = uid.split(",");
			List<IosPlayerVideo> listToUpdate = iosPlayervideoService.findAllVideoByUids(uids);
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (IosPlayerVideo videoLocal : listToUpdate) {
				videoLocal.setVideoState(videoState);
				videoLocal.setUpdateTime(new Timestamp(newdate.getTime()));
				videoLocal.setUpdateUser(sessionAdmin.getAdminId());
				OperationLog oLog = new OperationLog(getIp(), "热门影片推荐列表", "状态修改:" + videoLocal.getVideoName()+(videoState.equals("1")?"上架":"下架"),newdate , sessionAdmin.getAdminId());
				logList.add(oLog);
				
			}
			iosPlayervideoService.updateVideo(listToUpdate);
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			if (videoState==1) {
				setSuccessDispatch(GConstantAlert.GS_UP_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST3D_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_DOWN_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_VIDEO_LIST3D_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VideoAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}
	}
	

	public IosPlayerVideo getVideo() {
		return video;
	}

	public void setVideo(IosPlayerVideo video) {
		this.video = video;
	}

	public Page<IosPlayerVideo> getPage() {
		return page;
	}

	public void setPage(Page<IosPlayerVideo> page) {
		this.page = page;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Long getVideoState() {
		return videoState;
	}

	public void setVideoState(Long videoState) {
		this.videoState = videoState;
	}

	public String getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(String videoDuration) {
		this.videoDuration = videoDuration;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getDimension() {
		return dimension;
	}

	public void setDimension(Long dimension) {
		this.dimension = dimension;
	}


	@Override
	public String doExecute() throws Exception {
		return null;
	}

}
