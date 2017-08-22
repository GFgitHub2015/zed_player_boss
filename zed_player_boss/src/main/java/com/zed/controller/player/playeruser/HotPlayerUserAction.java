package com.zed.controller.player.playeruser;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.playeruser.PlayerUser;
import com.zed.domain.player.playeruserhotsort.PlayerUserHotSort;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.account.account.AccountService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.playeruser.HotPlayerUserService;
import com.zed.service.player.playeruser.PlayerUserHotSortService;
import com.zed.service.player.playeruser.PlayerUserService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;
import com.zed.util.ConstantType.HotUserStatus;

/**
 * @date : 2017年2月10日 下午4:10:01
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Controller("hotPlayerUserAction")
@Scope(value = "prototype")
public class HotPlayerUserAction  extends BaseAction {
	private static final long serialVersionUID = 3716191357100170962L;
	private Page<HashMap> page = new Page<HashMap>();
	private String userRoleStatus;
	private String userId;
	private String status;
	private String areaCode;
	private List<PlayerCountry> playerCountryList;							//所有的地区国家码集合
	private PlayerUserHotSort playerUserHotSort;
	@Resource(name="playerUserService")
	private PlayerUserService playerUserService;
	@Resource(name="hotPlayerUserService")
	private HotPlayerUserService hotPlayerUserService;
	@Resource(name="accountService")
	private AccountService accountService;
	@Autowired
	protected OperationLogService operationLogService;
	@Resource(name="playerUserHotSortService")
	private PlayerUserHotSortService playerUserHotSortService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;

	public String list() {
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			page.setSorting(" sort asc"); // 排序
			if (!CommUtil.isEmpty(userRoleStatus)) {
				map.put("userRoleStatus", userRoleStatus);
			}
			if (!CommUtil.isEmpty(userId)) {
				map.put("userId", userId);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("countryCode", areaCode);
			}
			page.setParamsMap(map);
			page = hotPlayerUserService.findByPage(page);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("HotPlayerUserAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}
	

	/**
	 * @date : 2017年2月13日 上午10:18:03
	 * @author : Iris.Xiao
	 * @return
	 * @description : 启用禁用热门用户
	*/
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			OperationLog oLog = new OperationLog(getIp(), "热门用户管理", "修改热门用户状态:" +userId+":"+ status ,newdate , sessionAdmin.getAdminId());
			hotPlayerUserService.updateStatus(userId , sessionAdmin.getAdminId(),HotUserStatus.valueOf(status));
			logService.add(oLog);//记录操作日志
			if (status.equals("ENABLE")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_HOT_USER_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_HOT_USER_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("HotPlayerUserAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	
	/**
	 * @date : 2017年2月13日 上午10:18:03
	 * @author : Iris.Xiao
	 * @return
	 * @description : 批量启用禁用热门用户
	*/
	public String updateStatusBatch() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			OperationLog oLog = new OperationLog(getIp(), "热门用户管理", "批量修改热门用户使用状态:" +userId+":"+ status,newdate , sessionAdmin.getAdminId());
			if(!CommUtil.isEmpty(userId)){
				logService.add(oLog);//记录操作日志
				String[] userIds=userId.split(",");
				hotPlayerUserService.updateStatusBatch(userIds,sessionAdmin.getAdminId(),HotUserStatus.valueOf(status));
			}
			if (HotUserStatus.valueOf(status).getStatus() == 1) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("HotPlayerUserAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String updatePage() {
		try {
			playerUserHotSort = playerUserHotSortService.findByUserId(userId);
			if (playerUserHotSort == null) {
				PlayerUser pu = playerUserService.findById(userId);
				if (pu != null) {
					playerUserHotSort = new PlayerUserHotSort();
					playerUserHotSort.setUserSortId(playerUserHotSort.generateId());
					playerUserHotSort.setUserId(userId);
					playerUserHotSort.setCountryCode(pu.getAreaCode());
					playerUserHotSortService.addPlayerUserHotSort(playerUserHotSort);
				}else{
					setResultDispatch(GConstantAlert.GS_LTE2012, GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			if (playerUserHotSortService.findByCountryCodeWithSort(playerUserHotSort.getCountryCode(), playerUserHotSort.getSort())!=null) {
				setResultDispatch(GConstantAlert.GS_SORT_EXSIT, GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			OperationLog operationLog = new OperationLog(getIp(), "热门用户管理", "修改热门用户id:"+playerUserHotSort.getUserId()+", countryCode:"+playerUserHotSort.getCountryCode()+", sort:"+playerUserHotSort.getSort(), newdate, sessionAdmin.getAdminId());
			playerUserHotSortService.updateSort(playerUserHotSort);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String updateAllHotUser(){
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			hotPlayerUserService.updateAllHotUser();
			setSuccessDispatch(GConstantAlert.GS_UPDATEALL_SUCCESS,GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerUserAction[updateAllHotUser] failed: ",ex);
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

	public String getUserRoleStatus() {
		return userRoleStatus;
	}

	public void setUserRoleStatus(String userRoleStatus) {
		this.userRoleStatus = userRoleStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PlayerUserHotSort getPlayerUserHotSort() {
		return playerUserHotSort;
	}

	public void setPlayerUserHotSort(PlayerUserHotSort playerUserHotSort) {
		this.playerUserHotSort = playerUserHotSort;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<PlayerCountry> getPlayerCountryList() {
		return playerCountryList;
	}

	public void setPlayerCountryList(List<PlayerCountry> playerCountryList) {
		this.playerCountryList = playerCountryList;
	}
}
