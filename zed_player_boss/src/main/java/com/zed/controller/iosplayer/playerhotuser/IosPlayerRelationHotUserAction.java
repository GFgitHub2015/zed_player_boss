package com.zed.controller.iosplayer.playerhotuser;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.zed.common.util.CommUtil;
import com.zed.common.util.StringUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.iosplayer.playerhotuser.IosPlayerRelationHotUser;
import com.zed.domain.params.YoutubeParams;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.playerhotuser.PlayerRelationHotUser;
import com.zed.domain.player.playerhotuser.YouTubeHotUser;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.iosplayer.playerhotuser.IosPlayerRelationHotUserService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.system.page.YouTubePage;

import net.sf.json.JSONObject;

/**
 * @date : 2017年7月03日 下午4:10:01
 * @author : X.Long
 * @version : 1.0
 * @description : 
*/
@Controller("iosPlayerRelationHotUserAction")
@Scope(value = "prototype")
public class IosPlayerRelationHotUserAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Resource(name="iosPlayerRelationHotUserService")
	private IosPlayerRelationHotUserService playerRelationHotUserService;
	
	private Page<IosPlayerRelationHotUser> page = new Page<IosPlayerRelationHotUser>();
	private YouTubePage<YouTubeHotUser> youTubePage = new YouTubePage<YouTubeHotUser>();
	private List<PlayerCountry> playerCountryList; //所有的地区国家码集合
	private IosPlayerRelationHotUser playerRelationHotUser; 
	private String status;
	private YoutubeParams youtubeParam;
	
	/**
	 * @date : 2017年06月28日 上午11:57:28
	 * @description : 显示热门用户列表
	*/
	public String list() {
		try {
			page.setSorting(" sort asc"); // 升序排序
			Map<String, Object> map = new HashMap<String, Object>();
			if(null == playerRelationHotUser) playerRelationHotUser = new IosPlayerRelationHotUser();
			if (StringUtil.isNotBlank(playerRelationHotUser.getUserId())) {
				map.put("userId", playerRelationHotUser.getUserId());
			}
			if (StringUtil.isNotBlank(playerRelationHotUser.getTitle())) {
				map.put("title", playerRelationHotUser.getTitle());
			}
			if (null != playerRelationHotUser.getStatus()) {
				map.put("status", playerRelationHotUser.getStatus());
			}
			if (null !=  playerRelationHotUser.getOrigin()) {
				map.put("origin", playerRelationHotUser.getOrigin());
			}
			page.setParamsMap(map);
			page = playerRelationHotUserService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationHotUserAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	/**
	 * @date : 2017年7月4日 上午10:18:03
	 * @author : X.Long
	 * @return
	 * @description : 启用禁用热门用户、包含对批量的处理
	*/
	public String updateStatusBatchByUserId() {
		try {
			String[] userIds = playerRelationHotUser.getUserId().split(",");
			playerRelationHotUserService.updateStatusBatchByUserId(userIds, PlayerRelationHotUser.Status.valueOf(status).getStatus());
			if (PlayerRelationHotUser.Status.valueOf(status).getStatus() == 1) {
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
				Log.getLogger(this.getClass()).error("PlayerRelationHotUserAction[updateStatusBatchByUserId] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * @date : 2017年07月03日 上午11:57:28
	 * @author : X.Long
	 * @description : 添加GBox热门用户跳转
	*/
	public String addPage() {
		return SUCCESS;
	}
	
	/**
	 * @date : 2017年07月03日 上午11:57:28
	 * @author : X.Long
	 * @description : 根据频道名称查询YouTube热门用户
	*/
	public String addYouTubePage() {
		try {
			youTubePage = playerRelationHotUserService.findYouTubeUserByName(youtubeParam);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationHotUserAction[addYouTbPage] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * @param flag
	 * @description 验证YouTube用户是否已经添加   true表示已经存在,否则相反
	 * @return
	 */
	public void isExist() {
		Boolean bool = false;
		try {
			IosPlayerRelationHotUser playerHotUser_ = playerRelationHotUserService.findByUserId(playerRelationHotUser.getUserId());
			if(null != playerHotUser_) {
				bool = Boolean.TRUE;
			} 
	        response.getWriter().println(JSON.toJSONString(bool));
	        response.getWriter().flush();
	        response.getWriter().close();
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationHotUserAction[isExist] failed: ",ex);
			}
		}
	}
	
	/**
	 * @date : 2017年07月03日 上午11:57:28
	 * @author : X.Long
	 * @description : YouTube热门用户推荐实现
	*/
	public void add() {
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			out = response.getWriter();
			playerRelationHotUser.setUid(CommUtil.getUUID());
			playerRelationHotUser.setStatus(PlayerRelationHotUser.Status.ENABLE.getStatus());
			playerRelationHotUser.setOrigin(PlayerRelationHotUser.UserSource.YOUTUBE.getSource());
			playerRelationHotUser.setSort(playerRelationHotUserService.getLastSort());
			playerRelationHotUserService.addHotUser(playerRelationHotUser);
			json.put("data", Boolean.TRUE);
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationHotUserAction[add] failed: ",ex);
			}
		} finally{
			out.print(json.toString());
			out.flush();
			out.close();
		}	 
	}
	
	/**
	 * @date : 2017年07月03日 上午11:57:28
	 * @author : X.Long
	 * @description : 添加GBox热门用户
	*/
	public String addGBoxUser() {
		try {
			playerRelationHotUser.setUid(CommUtil.getUUID());
			playerRelationHotUser.setStatus(PlayerRelationHotUser.Status.ENABLE.getStatus());
			playerRelationHotUser.setOrigin(PlayerRelationHotUser.UserSource.GBOX.getSource());
			playerRelationHotUser.setSort(playerRelationHotUserService.getLastSort());
			playerRelationHotUserService.addHotUser(playerRelationHotUser);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_HOT3DVIDEO_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationHotUserAction[addGBoxUser] failed: ",ex);
			}
			return ERROR;
		}	
	}
	
	
	/**
	 * @date : 2017年07月03日 上午11:57:28
	 * @author : X.Long
	 * @description : 修改热门用户展示位
	*/
	public void updateSortByUid() {
		try {
			playerRelationHotUserService.updateSortByUserId(playerRelationHotUser.getUserId(), playerRelationHotUser.getSort());
			response.getWriter().println(JSON.toJSONString(Boolean.TRUE));
	        response.getWriter().flush();
	        response.getWriter().close();
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationHotUserAction[updateSortByUid] failed: ", ex);
			}
		}		
	}
	
	/**
	 * @date : 2017年07月03日 上午11:57:28
	 * @author : X.Long
	 * @description : 根据UId删除热门用户
	*/
	public String deleteByUid() {
		try {
			playerRelationHotUserService.deleteByUId(playerRelationHotUser.getUid());
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS, GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
			return GConstantRedirect.GS_OK; 
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerRelationHotUserAction[deleteByUId] failed: ", ex);
			}
			return ERROR;
		}
	}
	
	@Override
	public String doExecute() throws Exception {
		return null;
	}

	public Page<IosPlayerRelationHotUser> getPage() {
		return page;
	}

	public void setPage(Page<IosPlayerRelationHotUser> page) {
		this.page = page;
	}

	public YouTubePage<YouTubeHotUser> getYouTubePage() {
		return youTubePage;
	}

	public void setYouTubePage(YouTubePage<YouTubeHotUser> youTubePage) {
		this.youTubePage = youTubePage;
	}

	public List<PlayerCountry> getPlayerCountryList() {
		return playerCountryList;
	}

	public void setPlayerCountryList(List<PlayerCountry> playerCountryList) {
		this.playerCountryList = playerCountryList;
	}

	public IosPlayerRelationHotUser getPlayerRelationHotUser() {
		return playerRelationHotUser;
	}

	public void setPlayerRelationHotUser(IosPlayerRelationHotUser playerRelationHotUser) {
		this.playerRelationHotUser = playerRelationHotUser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public YoutubeParams getYoutubeParam() {
		return youtubeParam;
	}

	public void setYoutubeParam(YoutubeParams youtubeParam) {
		this.youtubeParam = youtubeParam;
	}

}
