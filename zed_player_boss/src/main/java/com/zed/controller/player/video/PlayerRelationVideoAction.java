package com.zed.controller.player.video;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.zed.common.util.CommUtil;
import com.zed.common.util.DateUtil;
import com.zed.common.util.StringUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.domain.player.video.PlayerRelationVideo;
import com.zed.domain.player.video.PlayerVideoDestFile;
import com.zed.domain.system.Admin;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.hotvideo.IHotVideoService;
import com.zed.service.player.logicalfile.PlayerLogicalFileService;
import com.zed.service.player.player.PlayerVideoDestFileService;
import com.zed.service.player.video.IPlayerRelationVideoService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;

/**
 * @date : 2017年06月28日 上午11:57:28
 * @author : X.Long
 * @version : 1.0
 * @description : 
*/
@Controller("playerRelationVideoAction")
@Scope(value="prototype")
public class PlayerRelationVideoAction extends BaseAction {

	private static final long serialVersionUID = 9054015430261468461L;
	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.hotvideo");
	private static final String cdnUrl = SystemConfig.getProperty("image.cdn.url"); //图片cdn加速地址
	
	@Resource(name = "playerRelationVideoService")
	private IPlayerRelationVideoService playerRelationVideoService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	@Autowired
	private UploadService uploadService;
	@Resource(name = "hotvideoServiceImpl")
	private IHotVideoService hotVideoService;
	@Resource(name = "playerVideoDestFileService")
	private PlayerVideoDestFileService playerVideoDestFileService;
	@Resource(name="playerLogicalFileService")
	private PlayerLogicalFileService playerLogicalFileService;
	
	private Page<PlayerRelationVideo> page = new Page<PlayerRelationVideo>();
	private List<PlayerCountry> playerCountryList;
	private PlayerRelationVideo playerRelationVideo;
	private PlayerVideoDestFile playerVideoDestFile;
	private File imageUpload;
	private String imageUploadContentType; 	// 上传的文件的数据类型
	private String imageUploadFileName; 	// 上传的文件的名称 
	
	/**
	* @date : 2017年7月5日 下午3:29:30
	 * @author : X.Long
	 * @description : 
	*/
	public String list() {
		try {
			page.setSorting(" start_time desc");  // 降序排序
			Map<String, Object> map = new HashMap<String, Object>();
			if(null != playerRelationVideo) {
				String fileName = playerRelationVideo.getFileName();
				Integer status = playerRelationVideo.getStatus();
				Integer origin = playerRelationVideo.getOrigin();
				Integer dimensionType = playerRelationVideo.getDimensionType();
				String countryCode = playerRelationVideo.getCountryCode();
				if (StringUtil.isNotBlank(fileName)) {
					map.put("fileName", fileName);
				}
				if (null != status) {
					map.put("status", status);
				}
				if (null != origin) {
					map.put("origin", origin);
				}
				if (null != dimensionType) {
					map.put("dimensionType", dimensionType);
				}
				if (StringUtil.isNotBlank(countryCode)) {
					map.put("countryCode", countryCode);
				}
			}
			page.setParamsMap(map);
			playerRelationVideoService.findByPage(page);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationVideoAction[list] failed: ", ex);
			}
			return ERROR;
		}
	}

	/**
	 * @date : 2017年7月5日 下午3:29:30
	 * @author : X.Long
	 * @description : 跳转到新增GBox 2D/3D页面
	*/
	public String addPage() {
		playerCountryList = playerCountryService.findAll();
		return SUCCESS;
	}
	
	/**
	 * @date : 2017年7月5日 下午3:29:30
	 * @author : X.Long
	 * @description : 跳转到修改页面
	*/
	public String updatePage() {
		playerRelationVideo = playerRelationVideoService.findByUid(playerRelationVideo.getUid());
		playerCountryList = playerCountryService.findAll();
		return SUCCESS;
	}

	
	/**
	 * @date : 2017年7月5日 下午3:29:30
	 * @author : X.Long
	 * @description : 跳转到新增YouTube 2D/3D页面
	*/
	public String addYouTbPage() {
		playerCountryList = playerCountryService.findAll();
		return SUCCESS;
	} 
	
	/**
	 * @date : 2017年7月5日 下午3:29:30
	 * @author : X.Long
	 * @throws IOException
	 * @description : 添加GBox 2D/3D视频
	*/
	public String addVideo() {
		try {
			if (imageUpload != null) {
				//上传热词背景图片
				String url = uploadService.put(imageUpload,remotePath, imageUploadFileName);
				if (!CommUtil.isEmpty(url)) {
					url = hotVideoService.replaceCdnUrl(url, cdnUrl);
					//添加热词背景图片
					playerRelationVideo.setIconUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			playerRelationVideo.setStatus(PlayerRelationVideo.Status.ENABLE.getStatus());
			playerRelationVideo.setOrigin(PlayerRelationVideo.Orgin.GBOX.getOrign());
			playerRelationVideo.setCreateUser(getSessionAdmin().getAdminId());
			playerRelationVideo.setUpdateUser(getSessionAdmin().getAdminId());
			playerRelationVideo.setUpdateTime(DateUtil.getCurTime());
			if(playerRelationVideo.getDimensionType() == 2) {
				String countreysCode = playerRelationVideo.getCountryCode().substring(0, playerRelationVideo.getCountryCode().lastIndexOf(","));
				String[] codes = countreysCode.split(",");
				for (String code : codes) {
					playerRelationVideo.setUid(CommUtil.getUUID());
					playerRelationVideo.setCountryCode(code);
					playerRelationVideoService.addVideo(playerRelationVideo);
				}
			} else {
				playerRelationVideo.setUid(CommUtil.getUUID());
				playerRelationVideoService.addVideo(playerRelationVideo);
			}
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationVideoAction[addGBox3DVideo] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * @date : 2017年07月03日 上午11:57:28
	 * @author : X.Long
	 * @description : 根据uid删除GBox/YouTube 2D/3D视频
	*/
	public String deleteByUid() {
		try {
			String[] uids = playerRelationVideo.getUid().split(",");
			playerRelationVideoService.deleteByUid(uids);
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS, GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationVideoAction[deleteByUid] failed: ", ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * @date : 2017年06月28日 上午11:57:28
	 * @author : X.Long
	 * @description : 根据videoId调用YouTube第三方API获取影片详细信息
	*/
	public void getYouTubeVideoDetail() {
		try {
			playerRelationVideo = playerRelationVideoService.getYouTubeVideoDetail(getSessionAdmin().getAdminId(), playerRelationVideo.getFileId());
	        response.getWriter().println(JSON.toJSONString(playerRelationVideo));
	        response.getWriter().flush();
	        response.getWriter().close();
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationVideoAction[getYouTubeVideoDetail] failed: ",ex);
			}
		}
	}
	
	/**
	 * @date : 2017年07月10日 上午10:10:08
	 * @author : X.Long
	 * @description : 新增YouTube 2D/3D影片信息
	*/
	public String addYouTubeVideo() {
		try {
			Admin sessionAdmin = getSessionAdmin();
			playerRelationVideo.setStatus(PlayerRelationVideo.Status.ENABLE.getStatus());
			playerRelationVideo.setOrigin(PlayerRelationVideo.Orgin.YOUTUBE.getOrign());
			playerRelationVideo.setCreateUser(sessionAdmin.getAdminId());
			playerRelationVideo.setUpdateUser(sessionAdmin.getAdminId());
			playerRelationVideo.setUpdateTime(DateUtil.getCurTime());
			if(playerRelationVideo.getDimensionType() == 2) {
				String countreysCode = playerRelationVideo.getCountryCode().substring(0, playerRelationVideo.getCountryCode().lastIndexOf(","));
				String[] codes = countreysCode.split(",");
				for (String code : codes) {
					playerRelationVideo.setUid(CommUtil.getUUID());
					playerRelationVideo.setCountryCode(code);
					playerRelationVideoService.addVideo(playerRelationVideo);
				}
			}else{
				playerRelationVideo.setUid(CommUtil.getUUID());
				playerRelationVideoService.addVideo(playerRelationVideo);
			}
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationVideoAction[addVideo3d] failed: ",ex);
			}
			return ERROR;
			
		}
	}
	
	/**
	 * @date : 2017年7月5日 下午3:29:30
	 * @author : X.Long
	 * @throws IOException
	 * @description : 根据video_id找到资源信息,验证是否有该资源
	*/
	public void getResourceInfo() {
		try {
			PlayerLogicalFile playerLogicalFile = playerLogicalFileService.findById(playerRelationVideo.getFileId());
			playerVideoDestFile = playerVideoDestFileService.getPlayerVideoDestFile(playerLogicalFile.getSourceFileId(), "0");
	        response.getWriter().println(JSON.toJSONString(playerVideoDestFile)); 
	        response.getWriter().flush();
	        response.getWriter().close();
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationVideoAction[getResourceInfo] failed: ", ex);
			}
		}
	}
	
	/**
	 * @date : 2017年07月10日 上午10:10:08
	 * @author : X.Long
	 * @description : 上下架实现
	*/
	public String updateStatus() {
		try {
			String[] uids = playerRelationVideo.getUid().split(",");
			List<PlayerRelationVideo> playerRelationVideoList = playerRelationVideoService.findAllRelationVideoByUids(uids);
			for (PlayerRelationVideo playerRelationVideo : playerRelationVideoList) {
				playerRelationVideo.setStatus(this.playerRelationVideo.getStatus());
				playerRelationVideo.setUpdateUser(getSessionAdmin().getAdminId());
				playerRelationVideo.setUpdateTime(DateUtil.getCurTime());
			}
			playerRelationVideoService.updateStatus(playerRelationVideoList);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationVideoAction[addVideo3d] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * @date : 2017年07月10日 上午10:10:08
	 * @author : X.Long
	 * @return
	 * @description : 根据uid修改GBox信息
	*/
	public String updateVideo() {
		try {
			if (imageUpload != null) {
				//上传热词背景图片
				String url = uploadService.put(imageUpload,remotePath, imageUploadFileName);
				if (!CommUtil.isEmpty(url)) {
					url = hotVideoService.replaceCdnUrl(url, cdnUrl);
					playerRelationVideo.setIconUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_HOTVIDEO_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			playerRelationVideo.setUpdateUser(getSessionAdmin().getAdminId());
			playerRelationVideo.setUpdateTime(DateUtil.getCurTime());
			playerRelationVideoService.updateVideo(playerRelationVideo);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationVideoAction[addVideo3d] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	@Override
	public String doExecute() throws Exception {
		return null;
	}

	public PlayerRelationVideo getPlayerRelationVideo() {
		return playerRelationVideo;
	}

	public void setPlayerRelationVideo(PlayerRelationVideo playerRelationVideo) {
		this.playerRelationVideo = playerRelationVideo;
	}

	public Page<PlayerRelationVideo> getPage() {
		return page;
	}

	public void setPage(Page<PlayerRelationVideo> page) {
		this.page = page;
	}

	public List<PlayerCountry> getPlayerCountryList() {
		return playerCountryList;
	}

	public void setPlayerCountryList(List<PlayerCountry> playerCountryList) {
		this.playerCountryList = playerCountryList;
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

	public PlayerVideoDestFile getPlayerVideoDestFile() {
		return playerVideoDestFile;
	}

	public void setPlayerVideoDestFile(PlayerVideoDestFile playerVideoDestFile) {
		this.playerVideoDestFile = playerVideoDestFile;
	}

}
