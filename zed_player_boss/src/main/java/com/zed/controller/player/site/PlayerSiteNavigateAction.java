package com.zed.controller.player.site;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.site.PlayerSiteNavigate;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.site.PlayerSiteNavigateService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("playerSiteNavigateAction")
@Scope(value = "prototype")
public class PlayerSiteNavigateAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.site.hotsite");
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerSiteNavigate playerSiteNavigate;
	private String siteNavigateId;
	private String title;
	private String status;
	private String areaCode;
	private File upLoadPicture;
	private String upLoadPictureContentType; 								// 上传的文件的数据类型
	private String upLoadPictureFileName; 									// 上传的文件的名称 
	private List<PlayerCountry> playerCountryList;							//所有的地区国家码集合
	
	@Autowired
	private UploadService uploadService;
	@Autowired
	private PlayerSiteNavigateService playerSiteNavigateService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" update_time DESC"); // 排序
			if (!CommUtil.isEmpty(title)) {
				map.put("title", title);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("countryCode", areaCode);
			}
			page.setParamsMap(map);
			page = playerSiteNavigateService.findByPage(page);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSiteNavigateAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			if (upLoadPicture != null) {
				//上传热词背景图片
				String url = uploadService.put(upLoadPicture,remotePath, upLoadPictureFileName);
				if (!CommUtil.isEmpty(url)) {
					//添加热词背景图片
					playerSiteNavigate.setImgUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_SITE_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			areaCode = areaCode.substring(0, areaCode.lastIndexOf(","));
			String[] areaCodes = areaCode.split(",");
			for (String ac : areaCodes) {
				playerSiteNavigate.setSiteNavigateId(playerSiteNavigate.generateId());
				OperationLog operationLog = new OperationLog(getIp(), "热门网站管理", "添加热门网站信息:"+playerSiteNavigate.getTitle()+", 国家码："+ac , newdate, sessionAdmin.getAdminId());
				playerSiteNavigate.setCountryCode(ac);
				playerSiteNavigate.setCreater(sessionAdmin.getAdminId());
				playerSiteNavigate.setUpdater(sessionAdmin.getAdminId());
				playerSiteNavigate.setCreateTime(new Timestamp(newdate.getTime()));
				playerSiteNavigate.setUpdateTime(new Timestamp(newdate.getTime()));
				playerSiteNavigateService.add(playerSiteNavigate);
				operationLogService.add(operationLog);
			}
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_SITE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSiteNavigateAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		try {
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSiteNavigateAction[addPage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String updatePage() {
		try {
			playerSiteNavigate = playerSiteNavigateService.findById(siteNavigateId);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSiteNavigateAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			if (upLoadPicture != null) {
				//上传热词背景图片
				String url = uploadService.put(upLoadPicture,remotePath, upLoadPictureFileName);
				if (!CommUtil.isEmpty(url)) {
					//添加热词背景图片
					playerSiteNavigate.setImgUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_SITE_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			playerSiteNavigate.setUpdater(sessionAdmin.getAdminId());
			playerSiteNavigate.setUpdateTime(new Timestamp(newdate.getTime()));
			OperationLog operationLog = new OperationLog(getIp(), "热门网站管理", "修改热门网站信息:"+playerSiteNavigate.getTitle(), newdate, sessionAdmin.getAdminId());
			playerSiteNavigateService.update(playerSiteNavigate);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_SITE_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSiteNavigateAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] siteNavigateIds=siteNavigateId.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : siteNavigateIds) {
				PlayerSiteNavigate psn = playerSiteNavigateService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "热门网站管理", "删除热门网站信息:"+psn.getTitle(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerSiteNavigateService.delete(siteNavigateIds);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_SITE_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSiteNavigateAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			playerSiteNavigate = playerSiteNavigateService.findById(siteNavigateId);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSiteNavigateAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			PlayerSiteNavigate psn = playerSiteNavigateService.findById(siteNavigateId);
			psn.setStatus(Integer.valueOf(status));
			psn.setUpdater(sessionAdmin.getAdminId());
			psn.setUpdateTime(new Timestamp(newdate.getTime()));
			OperationLog oLog = new OperationLog(getIp(), "热门网站管理", "修改热门网站使用状态:" + psn.getTitle()+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			playerSiteNavigateService.updateStatus(psn);
			logService.add(oLog);//记录操作日志
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_SITE_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_SITE_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSiteNavigateAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}

	@Override
	public String doExecute() throws Exception {
		return null;
	}
	public Page<HashMap> getPage() {
		return page;
	}
	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public PlayerSiteNavigate getPlayerSiteNavigate() {
		return playerSiteNavigate;
	}

	public void setPlayerSiteNavigate(PlayerSiteNavigate playerSiteNavigate) {
		this.playerSiteNavigate = playerSiteNavigate;
	}

	public String getSiteNavigateId() {
		return siteNavigateId;
	}

	public void setSiteNavigateId(String siteNavigateId) {
		this.siteNavigateId = siteNavigateId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public File getUpLoadPicture() {
		return upLoadPicture;
	}

	public void setUpLoadPicture(File upLoadPicture) {
		this.upLoadPicture = upLoadPicture;
	}

	public String getUpLoadPictureContentType() {
		return upLoadPictureContentType;
	}

	public void setUpLoadPictureContentType(String upLoadPictureContentType) {
		this.upLoadPictureContentType = upLoadPictureContentType;
	}

	public String getUpLoadPictureFileName() {
		return upLoadPictureFileName;
	}

	public void setUpLoadPictureFileName(String upLoadPictureFileName) {
		this.upLoadPictureFileName = upLoadPictureFileName;
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
