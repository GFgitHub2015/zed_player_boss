package com.zed.controller.player.spiderkeyword;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.spiderkeyword.PlayerSpiderKeyWord;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.player.spiderkeyword.PlayerSpiderKeyWordService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;
import com.zed.util.ConstantType;

@Controller("playerSpiderKeyWordAction")
@Scope(value = "prototype")
public class PlayerSpiderKeyWordAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String AREA_CODE = SystemConfig.getProperty("area.code");
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerSpiderKeyWord playerSpiderKeyWord;
	private String keyWordId;
	private String status;
	private String keyword;
	
	@Autowired
	private PlayerSpiderKeyWordService playerSpiderKeyWordService;

	public String list() {
		try {
			HashMap map = new HashMap();
		/*	if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}*/
			if (!CommUtil.isEmpty(keyword)) {
				map.put("keyword", keyword);
			}
			map.put("areaCode", AREA_CODE);
			page.setSorting("sort DESC"); // 排序
			page.setParamsMap(map);
			page = playerSpiderKeyWordService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSpiderKeyWordAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerSpiderKeyWord.setAreaCode(AREA_CODE);
			playerSpiderKeyWord.setKeyword(playerSpiderKeyWord.getKeyword().trim());
			playerSpiderKeyWord.setKeywordId(playerSpiderKeyWord.generateId());
			playerSpiderKeyWord.setCreateTime(new Timestamp(newdate.getTime()));
			playerSpiderKeyWord.setRetry(0);
			playerSpiderKeyWord.setStatus(ConstantType.CommonType.ZERO.getStatus());
			playerSpiderKeyWordService.add(playerSpiderKeyWord);
			OperationLog oLog = new OperationLog(getIp(), "待爬词管理列表", "添加待爬词:" + playerSpiderKeyWord.getKeyword() ,newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_SPIDERKEYWORD_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSpiderKeyWordAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String updatePage() {
		try {
			playerSpiderKeyWord = playerSpiderKeyWordService.findById(keyWordId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSpiderKeyWordAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			playerSpiderKeyWord.setKeyword(playerSpiderKeyWord.getKeyword().trim());
			playerSpiderKeyWord.setUpdateTime(new Timestamp(newdate.getTime()));
			playerSpiderKeyWord.setUpdater(sessionAdmin.getAdminId());
			playerSpiderKeyWordService.update(playerSpiderKeyWord);
			OperationLog oLog = new OperationLog(getIp(), "待爬词管理列表", "修改待爬词:" + playerSpiderKeyWord.getKeyword() ,newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_SPIDERKEYWORD_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSpiderKeyWordAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] keyWordIds=keyWordId.split(",");
			List<OperationLog> operationLogList = new ArrayList<OperationLog>();
			for (String id : keyWordIds) {
				PlayerSpiderKeyWord psk = playerSpiderKeyWordService.findById(id);
				OperationLog oLog = new OperationLog(getIp(), "待爬词管理列表", "删除待爬词:" + psk.getKeyword() ,newdate , sessionAdmin.getAdminId());
				operationLogList.add(oLog);
			}
			playerSpiderKeyWordService.delete(keyWordIds);
			if (operationLogList!=null && operationLogList.size()>0 && !operationLogList.isEmpty()) {
				logService.addBatch(operationLogList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_SPIDERKEYWORD_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSpiderKeyWordAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			playerSpiderKeyWord = playerSpiderKeyWordService.findById(keyWordId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSpiderKeyWordAction[detail] failed: ",ex);
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public PlayerSpiderKeyWord getPlayerSpiderKeyWord() {
		return playerSpiderKeyWord;
	}

	public void setPlayerSpiderKeyWord(PlayerSpiderKeyWord playerSpiderKeyWord) {
		this.playerSpiderKeyWord = playerSpiderKeyWord;
	}

	public String getKeyWordId() {
		return keyWordId;
	}

	public void setKeyWordId(String keyWordId) {
		this.keyWordId = keyWordId;
	}
	
}
