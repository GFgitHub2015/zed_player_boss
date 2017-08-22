package com.zed.controller.player.white;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.white.PlayerWhiteAreaCode;
import com.zed.domain.player.white.PlayerWhiteIp;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.white.PlayerWhiteAreaCodeService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("playerWhiteAreaCodeAction")
@Scope(value = "prototype")
public class PlayerWhiteAreaCodeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerWhiteAreaCode playerWhiteAreaCode;
	private String whiteAreaCodeId;
	private String areaCode;
	
	@Resource(name="playerWhiteAreaCodeService")
	private PlayerWhiteAreaCodeService playerWhiteAreaCodeService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" area_code ASC"); // 排序
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("areaCode", areaCode);
			}
			page.setParamsMap(map);
			page = playerWhiteAreaCodeService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerWhiteAreaCodeAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			PlayerWhiteAreaCode pwac = new PlayerWhiteAreaCode();
			pwac.setAreaCode(areaCode);
			pwac.setWhiteAreaCodeId(pwac.generateId());
			OperationLog operationLog = new OperationLog(getIp(), "地区编号白名单管理", "添加地区编号白名单:"+pwac.getAreaCode(), newdate, sessionAdmin.getAdminId());
			playerWhiteAreaCodeService.add(pwac);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_WHITEAREACODE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerWhiteAreaCodeAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] whiteAreaCodeIds=whiteAreaCodeId.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : whiteAreaCodeIds) {
				PlayerWhiteAreaCode pwa = playerWhiteAreaCodeService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "地区编号白名单管理", "删除地区编号白名单:"+pwa.getAreaCode(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerWhiteAreaCodeService.delete(whiteAreaCodeIds);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_WHITEAREACODE_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerWhiteAreaCodeAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * flag ：true 表示不存在相同的ip地址 false 表示存在相同的ip地址
	 * @return
	 */
	public String getFlag() {
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			String fieldValue = request.getParameter("fieldValue");
			String fieldId = request.getParameter("fieldId");
			JSONObject json = new JSONObject();
			Boolean flag = Boolean.TRUE;
			if (!CommUtil.isEmpty(fieldValue)) {
				PlayerWhiteAreaCode pwac = playerWhiteAreaCodeService.findByAreaCode(fieldValue);
				if (pwac != null) {
					flag = Boolean.FALSE;
				}
			}
			JSONArray ja = new JSONArray();
			ja.add(0,fieldId);
			ja.add(1,flag);
			json.put("data", ja.toString());
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerWhiteAreaCodeAction[getFlag] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
			return NONE;
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

	public PlayerWhiteAreaCode getPlayerWhiteAreaCode() {
		return playerWhiteAreaCode;
	}

	public void setPlayerWhiteAreaCode(PlayerWhiteAreaCode playerWhiteAreaCode) {
		this.playerWhiteAreaCode = playerWhiteAreaCode;
	}

	public String getWhiteAreaCodeId() {
		return whiteAreaCodeId;
	}

	public void setWhiteAreaCodeId(String whiteAreaCodeId) {
		this.whiteAreaCodeId = whiteAreaCodeId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
