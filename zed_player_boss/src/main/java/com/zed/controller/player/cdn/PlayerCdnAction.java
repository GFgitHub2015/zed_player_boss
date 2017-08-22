package com.zed.controller.player.cdn;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.cdn.PlayerCdn;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.cdn.PlayerCdnService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("playerCdnAction")
@Scope(value = "prototype")
public class PlayerCdnAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerCdn playerCdn;
	private String cdnId;
	private String cdnName;
	private String cdnStatus;
	
	@Autowired
	private PlayerCdnService playerCdnService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" cdn_name ASC"); // 排序
			if (!CommUtil.isEmpty(cdnName)) {
				map.put("cdnName", cdnName);
			}
			if (!CommUtil.isEmpty(cdnStatus)) {
				map.put("cdnStatus", cdnStatus);
			}
			page.setParamsMap(map);
			page = playerCdnService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerCdnAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerCdn.setCdnId(playerCdn.generateId());
			OperationLog operationLog = new OperationLog(getIp(), "cdn管理", "添加cdn信息:"+playerCdn.getCdnName(), newdate, sessionAdmin.getAdminId());
			playerCdnService.add(playerCdn);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_CDN_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerCdnAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String updatePage() {
		try {
			playerCdn = playerCdnService.findById(cdnId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerCdnAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			OperationLog operationLog = new OperationLog(getIp(), "cdn管理", "修改cdn信息:"+playerCdn.getCdnName(), newdate, sessionAdmin.getAdminId());
			playerCdnService.update(playerCdn);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_CDN_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerCdnAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] cdnIds=cdnId.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : cdnIds) {
				PlayerCdn ppi = playerCdnService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "cdn管理", "删除cdn信息:"+ppi.getCdnName(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerCdnService.delete(cdnIds);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_CDN_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerCdnAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			playerCdn = playerCdnService.findById(cdnId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerCdnAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			PlayerCdn pc = playerCdnService.findById(cdnId);
			pc.setStatus(Integer.valueOf(cdnStatus));
			OperationLog oLog = new OperationLog(getIp(), "cdn管理", "修改cdn使用状态:" + pc.getCdnName()+(cdnStatus.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			playerCdnService.updateStatus(pc);
			logService.add(oLog);//记录操作日志
			if (cdnStatus.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_CDN_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_CDN_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerCdnAction[updateStatus] failed: ",ex);
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

	public PlayerCdn getPlayerCdn() {
		return playerCdn;
	}

	public void setPlayerCdn(PlayerCdn playerCdn) {
		this.playerCdn = playerCdn;
	}

	public String getCdnId() {
		return cdnId;
	}

	public void setCdnId(String cdnId) {
		this.cdnId = cdnId;
	}

	public String getCdnName() {
		return cdnName;
	}

	public void setCdnName(String cdnName) {
		this.cdnName = cdnName;
	}

	public String getCdnStatus() {
		return cdnStatus;
	}

	public void setCdnStatus(String cdnStatus) {
		this.cdnStatus = cdnStatus;
	}

}
