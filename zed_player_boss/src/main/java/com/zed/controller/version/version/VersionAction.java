package com.zed.controller.version.version;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.domain.version.version.Version;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.service.version.version.VersionService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;
import com.zed.util.FileMD5;
import com.zed.util.FileUpload;
import com.zed.util.UploadUtil;

@Controller("versionAction")
@Scope(value = "prototype")
public class VersionAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String UPLOADPATH = SystemConfig.getProperty("upload.app.path");
	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.install");
	private static final String CDNPATH = SystemConfig.getProperty("app.cdn.url");
	private Page<HashMap> page = new Page<HashMap>();
	private Version version;
	private String versionId;
	private String versionCode;
	private String cdnName;
	private String status;
	private String appType;
	private String channel;
	private String packageName;
	private File upLoadApp;
	private String upLoadAppContentType; 								// 上传的文件的数据类型
	private String upLoadAppFileName; 									// 上传的文件的名称
	
	@Autowired
	private UploadService uploadService;
	@Autowired
	private VersionService versionService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" create_time desc"); // 排序
			if (!CommUtil.isEmpty(versionCode)) {
				map.put("versionCode", versionCode);
			}
			if (!CommUtil.isEmpty(versionId)) {
				map.put("versionId", versionId);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(appType)) {
				map.put("appType", appType);
			}
			if (!CommUtil.isEmpty(packageName)) {
				map.put("packageName", packageName);
			}
			page.setParamsMap(map);
			page = versionService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VersionAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			String savePath = ServletActionContext.getServletContext().getRealPath(UPLOADPATH);
			Date newdate = new Date();
			if (upLoadApp != null) {
				//上传热词背景图片
				String url = uploadService.put(upLoadApp,remotePath+"/"+version.getVersionCode().substring(version.getVersionCode().indexOf("v")+1, version.getVersionCode().length()), version.getAutoUpLoadAppFileName(), Boolean.TRUE);
				if (!CommUtil.isEmpty(url)) {
					//添加下载地址
					if (!StringUtils.isBlank(CDNPATH)) {
						String cdnUrl = CDNPATH+url.substring(url.indexOf("player/install"), url.length());
						version.setDownloadUrl(cdnUrl);
					}else{
						version.setDownloadUrl(url);
					}
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_VERSION_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
				
				//上传至本地服务器计算md5值
				FileUpload fileUpload = new FileUpload();
				fileUpload.setFilePath(savePath);
				fileUpload.setFileName(upLoadAppFileName);
				String resourceImageName=UploadUtil.uploadFile(upLoadApp, fileUpload);
				String md5 = FileMD5.genCid(savePath+File.separator+resourceImageName);
				version.setMd5(md5);
			}
			version.setStatus(1);
			version.setSystemType("ANDROID");
			version.setOrigin(2);
			version.setCreater(sessionAdmin.getAdminId());
			version.setCreateTime(new Timestamp(newdate.getTime()));
			version.setEditer(sessionAdmin.getAdminId());
			version.setUpdateTime(new Timestamp(newdate.getTime()));
			OperationLog operationLog = new OperationLog(getIp(), "版本管理", "添加版本信息："+version.getPackageName()==null?"null":version.getPackageName()+", 版本编码："+version.getVersionCode(), newdate, sessionAdmin.getAdminId());
			versionService.add(version);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_VERSION_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VersionAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String updatePage() {
		try {
			version = versionService.findById(versionId, packageName,channel);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VersionAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			OperationLog operationLog = new OperationLog(getIp(),  "版本管理", "修改版本信息："+version.getPackageName()==null?"null":version.getPackageName()+", 版本编码："+version.getVersionCode(), newdate, sessionAdmin.getAdminId());
			version.setEditer(sessionAdmin.getAdminId());
			version.setUpdateTime(new Timestamp(newdate.getTime()));
			versionService.update(version);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_VERSION_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VersionAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
//			String[] versionIds=versionId.split(",");
//			List<OperationLog> logList = new ArrayList<OperationLog>();
//			for (String id : versionIds) {
				Version v = versionService.findById(versionId, packageName,channel);
				OperationLog operationLog = new OperationLog(getIp(), "版本管理", "删除版本信息："+v.getPackageName()==null?"null":v.getPackageName()+", 版本编码："+v.getVersionCode(), newdate, sessionAdmin.getAdminId());
//				logList.add(operationLog);
//			}
//			versionService.deleteVersion(v);
			versionService.deleteByVersionIdAndPackageName(versionId, packageName,channel);
			logService.add(operationLog);
			/*if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}*/
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_VERSION_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VersionAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			version = versionService.findById(versionId, packageName,channel);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VersionAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			Version v = versionService.findById(versionId, packageName,channel);
			v.setStatus(Integer.valueOf(status));
			OperationLog oLog = new OperationLog(getIp(), "版本管理", "修改版本信息使用状态:" +v.getPackageName()==null?"null":v.getPackageName()+", 版本编码："+v.getVersionCode()+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			versionService.update(v);
			logService.add(oLog);//记录操作日志
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_VERSION_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_VERSION_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VersionAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public void findMaxId(){
		try {
			String count = versionService.findMaxId(versionId, channel);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("result", count);
			outputResult(map, SUCCESS, "");
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("VersionAction[detail] failed: ",ex);
			}
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

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public File getUpLoadApp() {
		return upLoadApp;
	}

	public void setUpLoadApp(File upLoadApp) {
		this.upLoadApp = upLoadApp;
	}

	public String getUpLoadAppContentType() {
		return upLoadAppContentType;
	}

	public void setUpLoadAppContentType(String upLoadAppContentType) {
		this.upLoadAppContentType = upLoadAppContentType;
	}

	public String getUpLoadAppFileName() {
		return upLoadAppFileName;
	}

	public void setUpLoadAppFileName(String upLoadAppFileName) {
		this.upLoadAppFileName = upLoadAppFileName;
	}

	public String getCdnName() {
		return cdnName;
	}

	public void setCdnName(String cdnName) {
		this.cdnName = cdnName;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	

}
