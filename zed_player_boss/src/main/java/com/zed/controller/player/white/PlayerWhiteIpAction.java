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
import com.zed.domain.player.white.PlayerWhiteIp;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.white.PlayerWhiteIpService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("playerWhiteIpAction")
@Scope(value = "prototype")
public class PlayerWhiteIpAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerWhiteIp playerWhiteIp;
	private String whiteIp;
	private String areaCode;
	
	@Resource(name="playerWhiteIpService")
	private PlayerWhiteIpService playerWhiteIpService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" area_code ASC"); // 排序
			if (!CommUtil.isEmpty(whiteIp)) {
				map.put("ip", whiteIp);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("areaCode", areaCode);
			}
			page.setParamsMap(map);
			page = playerWhiteIpService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerWhiteIpAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			PlayerWhiteIp pwi = new PlayerWhiteIp();
			pwi.setIp(whiteIp);
			pwi.setAreaCode(areaCode);
			OperationLog operationLog = new OperationLog(getIp(), "ip白名单管理", "添加ip白名单:"+pwi.getAreaCode()+"-"+pwi.getIp(), newdate, sessionAdmin.getAdminId());
			playerWhiteIpService.add(pwi);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_WHITEIP_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerWhiteIpAction[add] failed: ",ex);
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
			String[] ips=whiteIp.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String ipStr : ips) {
				PlayerWhiteIp pwi = playerWhiteIpService.findByIp(ipStr);
				OperationLog operationLog = new OperationLog(getIp(), "ip白名单管理", "删除ip白名单:"+pwi.getAreaCode()+"-"+pwi.getIp(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerWhiteIpService.delete(ips);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_WHITEIP_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerWhiteIpAction[delete] failed: ",ex);
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
				PlayerWhiteIp pwi = playerWhiteIpService.findByIp(fieldValue);
				if (pwi != null) {
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
				Log.getLogger(this.getClass()).error("PlayerWhiteIpAction[getFlag] failed: ",ex);
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

	public PlayerWhiteIp getPlayerWhiteIp() {
		return playerWhiteIp;
	}

	public void setPlayerWhiteIp(PlayerWhiteIp playerWhiteIp) {
		this.playerWhiteIp = playerWhiteIp;
	}

	public String getWhiteIp() {
		return whiteIp;
	}

	public void setWhiteIp(String whiteIp) {
		this.whiteIp = whiteIp;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
}
