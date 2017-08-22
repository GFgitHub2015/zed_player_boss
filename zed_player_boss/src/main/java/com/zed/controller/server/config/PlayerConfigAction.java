package com.zed.controller.server.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.server.config.PlayerConfig;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.server.config.PlayerConfigService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("playerConfigAction")
@Scope(value = "prototype")
public class PlayerConfigAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerConfig playerConfig;
	private String id;
	private String version;
	private String packageName;
	
	@Autowired
	private PlayerConfigService playerConfigService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			if (!CommUtil.isEmpty(version)) {
				map.put("version", version);
			}
			if (!CommUtil.isEmpty(packageName)) {
				map.put("packageName", packageName);
			}
			page.setParamsMap(map);
			page = playerConfigService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerConfigAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			if (playerConfigService.isExist(playerConfig.getVersion().trim(), playerConfig.getPackageName().trim())) {
				setResultDispatch(GConstantAlert.GS_AGAIN_SUCCESS,GConstantRedirect.GS_PLAYER_CONFIG_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			OperationLog operationLog = new OperationLog(getIp(), "手机参数配置管理", "添加参数配置信息版本号:"+playerConfig.getVersion()+" , 包名："+playerConfig.getPackageName(), newdate, sessionAdmin.getAdminId());
			playerConfig.setConfigId(playerConfig.generateId());
			playerConfigService.add(playerConfig);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_CONFIG_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerConfigAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String updatePage() {
		try {
			playerConfig = playerConfigService.findById(id);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerConfigAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			OperationLog operationLog = new OperationLog(getIp(), "手机参数配置管理", "修改参数配置信息版本号:"+playerConfig.getVersion()+" , 包名："+playerConfig.getPackageName(), newdate, sessionAdmin.getAdminId());
			playerConfigService.update(playerConfig);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_CONFIG_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerConfigAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] ids=id.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String m : ids) {
				PlayerConfig pmi = playerConfigService.findById(m);
				OperationLog operationLog = new OperationLog(getIp(), "手机参数配置管理", "删除参数配置信息版本号:"+pmi.getVersion()+" , 包名："+pmi.getPackageName(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerConfigService.delete(ids);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_CONFIG_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerConfigAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			playerConfig = playerConfigService.findById(id);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerConfigAction[detail] failed: ",ex);
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

	public PlayerConfig getPlayerConfig() {
		return playerConfig;
	}

	public void setPlayerConfig(PlayerConfig playerConfig) {
		this.playerConfig = playerConfig;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
}
