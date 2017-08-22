package com.zed.controller.player.black;

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
import com.zed.domain.player.black.PlayerBlackAreaCode;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.black.PlayerBlackAreaCodeService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("playerBlackAreaCodeAction")
@Scope(value = "prototype")
public class PlayerBlackAreaCodeAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerBlackAreaCode playerBalckAreaCode;
	private String blackId;
	private String areaCode;
	
	@Resource(name="playerBlackAreaCodeService")
	private PlayerBlackAreaCodeService playerBlackAreaCodeService;
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
			page = playerBlackAreaCodeService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerBlackAreaCodeAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			PlayerBlackAreaCode pwac = new PlayerBlackAreaCode();
			pwac.setAreaCode(areaCode);
			pwac.setBlackId(pwac.generateId());
			OperationLog operationLog = new OperationLog(getIp(), "地区编号黑名单管理", "添加地区编号黑名单:"+pwac.getAreaCode(), newdate, sessionAdmin.getAdminId());
			playerBlackAreaCodeService.add(pwac);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_BLACKAREACODE_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerBlackAreaCodeAction[add] failed: ",ex);
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
			String[] blackIds=blackId.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : blackIds) {
				PlayerBlackAreaCode pwa = playerBlackAreaCodeService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "地区编号黑名单管理", "删除地区编号黑名单:"+pwa.getAreaCode(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerBlackAreaCodeService.delete(blackIds);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_BLACKAREACODE_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerBlackAreaCodeAction[delete] failed: ",ex);
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
				PlayerBlackAreaCode pwac = playerBlackAreaCodeService.findByAreaCode(fieldValue);
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
				Log.getLogger(this.getClass()).error("PlayerBlackAreaCodeAction[getFlag] failed: ",ex);
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

	public PlayerBlackAreaCode getPlayerBalckAreaCode() {
		return playerBalckAreaCode;
	}

	public void setPlayerBalckAreaCode(PlayerBlackAreaCode playerBalckAreaCode) {
		this.playerBalckAreaCode = playerBalckAreaCode;
	}

	public String getBlackId() {
		return blackId;
	}

	public void setBlackId(String blackId) {
		this.blackId = blackId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
