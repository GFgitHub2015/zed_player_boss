package com.zed.controller.server.modelinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.server.modelinfo.PlayerModelInfo;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.server.modelinfo.PlayerModelInfoService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("playerModelInfoAction")
@Scope(value = "prototype")
public class PlayerModelInfoAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerModelInfo playerModelInfo;
	private String model;
	
	@Autowired
	private PlayerModelInfoService playerModelInfoService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" model DESC"); // 排序
			if (!CommUtil.isEmpty(model)) {
				map.put("model", model);
			}
			page.setParamsMap(map);
			page = playerModelInfoService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerModelInfoAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			if (playerModelInfoService.isExist(playerModelInfo.getModel().trim())) {
				setResultDispatch(GConstantAlert.GS_AGAIN_SUCCESS,GConstantRedirect.GS_PLAYER_MODEL_INFO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			OperationLog operationLog = new OperationLog(getIp(), "机型播放模式管理", "添加机型播放模式:"+playerModelInfo.getModel(), newdate, sessionAdmin.getAdminId());
			playerModelInfoService.add(playerModelInfo);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_MODEL_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerModelInfoAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String updatePage() {
		try {
			playerModelInfo = playerModelInfoService.findById(model);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerModelInfoAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			OperationLog operationLog = new OperationLog(getIp(), "机型播放模式管理", "修改机型播放模式:"+playerModelInfo.getModel(), newdate, sessionAdmin.getAdminId());
			playerModelInfoService.update(playerModelInfo);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_MODEL_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerModelInfoAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] models=model.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String m : models) {
				PlayerModelInfo pmi = playerModelInfoService.findById(m);
				OperationLog operationLog = new OperationLog(getIp(), "机型播放模式管理", "删除机型播放模式:"+pmi.getModel(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerModelInfoService.delete(models);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_MODEL_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerModelInfoAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			playerModelInfo = playerModelInfoService.findById(model);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerModelInfoAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateAllCache() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			Boolean flag = playerModelInfoService.updateAll();
			OperationLog oLog = new OperationLog(getIp(), "机型播放模式管理", "强制同步机型播放模式",newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			if (flag) {
				setSuccessDispatch(GConstantAlert.GS_SYNCHRON_SUCCESS,GConstantRedirect.GS_PLAYER_MODEL_INFO_LIST_ACTION);
				return GConstantRedirect.GS_OK;
			}else{
				setResultDispatch(GConstantAlert.GS_SYNCHRON_FAILED,GConstantRedirect.GS_PLAYER_MODEL_INFO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerModelInfoAction[updateAllCache] failed: ",ex);
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

	public PlayerModelInfo getPlayerModelInfo() {
		return playerModelInfo;
	}

	public void setPlayerModelInfo(PlayerModelInfo playerModelInfo) {
		this.playerModelInfo = playerModelInfo;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}
