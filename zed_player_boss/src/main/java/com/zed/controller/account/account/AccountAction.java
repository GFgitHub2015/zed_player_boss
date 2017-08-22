package com.zed.controller.account.account;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.account.account.Account;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.account.account.AccountService;
import com.zed.service.common.upload.UploadService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("accountAction")
@Scope(value = "prototype")
public class AccountAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.account.ico");
	private Page<HashMap> page = new Page<HashMap>();
	private Account account;
	private String userId;
	private String searchkey;
	private String status;
	private String password;
	private File upLoadPicture;
	private String upLoadPictureContentType; 								// 上传的文件的数据类型
	private String upLoadPictureFileName; 									// 上传的文件的名称 
	private String nickName;
	private String phone;
	private String areaCode;
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private UploadService uploadService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" create_time desc"); // 排序
			if (!CommUtil.isEmpty(searchkey)) {
				map.put("searchkey", searchkey);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("areaCode", areaCode);
			}
			page.setParamsMap(map);
			page = accountService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[list] failed: ",ex);
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
				//上传用户头像
				String url = uploadService.put(upLoadPicture,remotePath, upLoadPictureFileName);
				if (!CommUtil.isEmpty(url)) {
					//添加用户头像
					account.setIconUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_ACCOUNT_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			account.setCreateTime(new Timestamp(newdate.getTime()));
			OperationLog operationLog = new OperationLog(getIp(), "用户管理", "添加用户信息:"+account.getNickName(), newdate, sessionAdmin.getAdminId());
			accountService.add(account);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_ACCOUNT_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		return SUCCESS;
	}

	public String updatePage() {
		try {
			account = accountService.findById(userId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updatePassWordPage() {
		try {
			account = accountService.findById(userId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[updatePassWordPage] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updatePassWord() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			Account acc = accountService.findById(userId);
			acc.setPassword(password);
			OperationLog operationLog = new OperationLog(getIp(), "用户管理", "修改用户密码:"+acc.getNickName(), newdate, sessionAdmin.getAdminId());
			if (acc.getExps()==null) {
				acc.setExps(new BigDecimal(0));
			}
			if (acc.getLevel()==null) {
				acc.setLevel(new BigDecimal(0));
			}
			accountService.update(acc);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_ACCOUNT_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[updatePassWord] failed: ",ex);
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
				//上传用户头像
				String url = uploadService.put(upLoadPicture,remotePath, upLoadPictureFileName);
				if (!CommUtil.isEmpty(url)) {
					//添加用户头像
					account.setIconUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED,GConstantRedirect.GS_ACCOUNT_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			OperationLog operationLog = new OperationLog(getIp(), "用户管理", "修改用户信息:"+account.getNickName(), newdate, sessionAdmin.getAdminId());
			if (account.getExps()==null) {
				account.setExps(new BigDecimal(0));
			}
			if (account.getLevel()==null) {
				account.setLevel(new BigDecimal(0));
			}
			account.setUpdateTime(new Timestamp(newdate.getTime()));
			accountService.update(account);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_ACCOUNT_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] userIds=userId.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : userIds) {
				Account acc = accountService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "用户管理", "删除用户信息:"+acc.getNickName(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			accountService.delete(userIds);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_ACCOUNT_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			account = accountService.findById(userId);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			Account acc = accountService.findById(userId);
			acc.setStatus(Integer.valueOf(status));
			OperationLog oLog = new OperationLog(getIp(), "用户管理", "修改用户使用状态:" + acc.getStatus()+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			accountService.update(acc);
			logService.add(oLog);//记录操作日志
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_ACCOUNT_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_ACCOUNT_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("AccountAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String getNickNameFlag() {
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			
			String fieldValue = request.getParameter("fieldValue");
			String fieldId = request.getParameter("fieldId");
			JSONObject json = new JSONObject();
			Boolean flag = Boolean.TRUE;
			if (!CommUtil.isEmpty(fieldValue)) {
				flag = accountService.findByNickName(fieldValue);
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
				Log.getLogger(this.getClass()).error("AccountAction[getNickNameFlag] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
			return NONE;
		}
	}
	public String getPhoneFlag() {
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			
			String fieldValue = request.getParameter("fieldValue");
			String fieldId = request.getParameter("fieldId");
			JSONObject json = new JSONObject();
			Boolean flag = Boolean.TRUE;
			if (!CommUtil.isEmpty(fieldValue)) {
				flag = accountService.findByPhone(fieldValue);
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
				Log.getLogger(this.getClass()).error("AccountAction[getPhoneFlag] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
			return NONE;
		}
	}
	
	public String getFlag() {
		PrintWriter out = null;
		String result = "";
		try{
			Account account = accountService.findById(userId);
			out = response.getWriter();
			String fieldValue = request.getParameter("fieldValue");
			String fieldId = request.getParameter("fieldId");
			JSONObject json = new JSONObject();
			Boolean flag = Boolean.FALSE;
			if (!CommUtil.isEmpty(fieldValue)) {
				Account acc = accountService.findById(fieldValue);
				if (acc!=null) {
					flag = Boolean.TRUE;
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
				Log.getLogger(this.getClass()).error("AccountAction[getFlag] failed: ",ex);
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchkey() {
		return searchkey;
	}

	public void setSearchkey(String searchkey) {
		this.searchkey = searchkey;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
}
