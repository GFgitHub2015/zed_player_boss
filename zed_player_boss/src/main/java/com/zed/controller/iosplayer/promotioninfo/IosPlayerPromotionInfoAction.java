package com.zed.controller.iosplayer.promotioninfo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.common.util.StringUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.iosplayer.promotioninfo.IosPlayerPromotionInfo;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.iosplayer.promotioninfo.IosPlayerPromotionInfoService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

/**
 * @date : 2017年5月18日 上午10:08:20
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 
*/
@Controller("iosPlayerPromotionInfoAction")
@Scope(value = "prototype")
public class IosPlayerPromotionInfoAction extends BaseAction {

	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.hotvideo");
	private static final String cdnUrl = SystemConfig.getProperty("image.cdn.url");//图片cdn加速地址

	@Autowired
	private UploadService uploadService;
	
	private static final long serialVersionUID = 1L;
	private Page<IosPlayerPromotionInfo> page = new Page<IosPlayerPromotionInfo>();
	private IosPlayerPromotionInfo playerPromotionInfo;
	private String infoId;
	private String title;
	private String status;
	private int topFlag;
	private String topFlagIds;
	private File imageUpload;
	private String areaCode;
	private String imageUploadFileName;

	private String imageUploadContentType;
	private List<PlayerCountry> playerCountryList;							//所有的地区国家码集合

	@Resource(name="iosPlayerPromotionInfoService")
	private IosPlayerPromotionInfoService playerPromotionInfoService;
	@Autowired
	protected OperationLogService operationLogService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	
	public String list() {
		try {
			HashMap<String,Object> map = new HashMap<String,Object>();
			page.setSorting(" CREATE_TIME DESC"); // 排序
			if (!CommUtil.isEmpty(title)) {
				map.put("title", title);
			}
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (areaCode!=null) {
				map.put("countryCode", areaCode);
			}
			page.setParamsMap(map);
			page = playerPromotionInfoService.findByPage(page);
			//再查询是否有置顶记录
			Page<IosPlayerPromotionInfo> topFlagPage = new Page<IosPlayerPromotionInfo>();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("topFlag", "1");
			topFlagPage.setParamsMap(param);
			//查询是否有置顶记录
			playerPromotionInfoService.findByPage(topFlagPage);
			List<IosPlayerPromotionInfo> topFlagList = topFlagPage.getResult();
			if(CommUtil.isEmpty(topFlagIds)){
				topFlagIds="";//这里初始值是null
			}
			if(topFlagList!=null&&topFlagList.size()>0){
				for (IosPlayerPromotionInfo hashMap : topFlagList) {
					topFlagIds+=hashMap.getUid()+",";
				}
			}
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PromotionInfoAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			if (playerPromotionInfo.getEndTime().before(playerPromotionInfo.getStartTime())) {
				setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_ONE,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
//			playerPromotionInfo.setContent("");
			//上传图片
			if(null != imageUpload) {
				String imageUrl = uploadService.put(imageUpload, remotePath, imageUploadFileName);
				if (StringUtil.isNotEmpty(imageUrl)) {
					imageUrl = playerPromotionInfoService.replaceCdnUrl(imageUrl, cdnUrl);
					playerPromotionInfo.setImageUrl(imageUrl);
				}
			}
			String[] areaCodes = areaCode.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String ac : areaCodes) {
				if(CommUtil.isEmpty(ac)){
					continue;
				}
				OperationLog operationLog = new OperationLog(getIp(), "活动推送消息管理", "添加活动推送消息:"+playerPromotionInfo.getTitle()+", 国家码："+ac , newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
				playerPromotionInfo.setUid(playerPromotionInfo.generateId());
				playerPromotionInfo.setCountryCode(ac);
				playerPromotionInfoService.add(playerPromotionInfo);
			}
			//删除缓存
			playerPromotionInfoService.deletePageList("0");
			if (!logList.isEmpty()&&logList.size()>0) {
				operationLogService.addBatch(logList);
			}
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerPromotionInfoAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String addPage() {
		try {
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerPromotionInfoAction[addPage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String updatePage() {
		try {
			playerPromotionInfo = playerPromotionInfoService.findById(infoId);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerPromotionInfoAction[updatePage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String update() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			if (playerPromotionInfo.getEndTime().getTime()<playerPromotionInfo.getStartTime().getTime()) {
				setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_ONE,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}

			IosPlayerPromotionInfo oldPlayerPromotionInfo = playerPromotionInfoService.findById(playerPromotionInfo.getUid());
			if(!(oldPlayerPromotionInfo.getStartTime().getTime() == playerPromotionInfo.getStartTime().getTime() && oldPlayerPromotionInfo.getEndTime().getTime() == playerPromotionInfo.getEndTime().getTime())){

				//2016年12月30日10:43:07 去掉活动时间限制
//				if(checkTime(playerPromotionInfo)){
//					setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_TWO,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
//					return GConstantRedirect.GS_RESULT;
//				}
			}
			//上传图片
			if(null != imageUpload) {
				String imageUrl = uploadService.put(imageUpload, remotePath, imageUploadFileName);
				if (StringUtil.isNotEmpty(imageUrl)) {
					imageUrl = playerPromotionInfoService.replaceCdnUrl(imageUrl, cdnUrl);
					playerPromotionInfo.setImageUrl(imageUrl);
				}
			}
			OperationLog operationLog = new OperationLog(getIp(), "活动推送消息管理", "修改活动推送消息:"+playerPromotionInfo.getTitle(), newdate, sessionAdmin.getAdminId());
			playerPromotionInfoService.update(playerPromotionInfo);

			//删除缓存
			playerPromotionInfoService.deletePageList("0");
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerPromotionInfoAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] infoIds=infoId.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : infoIds) {
				IosPlayerPromotionInfo ppi = playerPromotionInfoService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "活动推送消息管理", "删除活动推送消息:"+ppi.getTitle(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerPromotionInfoService.delete(infoIds);

			//删除缓存
			playerPromotionInfoService.deletePageList("0");
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerPromotionInfoAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			playerPromotionInfo = playerPromotionInfoService.findById(infoId);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerPromotionInfoAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * 批量启用或禁用
	 * @return
	 */
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String [] ids = infoId.split(",");
			List<IosPlayerPromotionInfo> ppiList = playerPromotionInfoService.findById(ids);
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (IosPlayerPromotionInfo ppi : ppiList) {
				ppi.setStatus(Integer.valueOf(status));
				OperationLog oLog = new OperationLog(getIp(), "活动推送消息管理", "状态活动推送消息修改:" + ppi.getTitle()+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
				logList.add(oLog);
			}
			playerPromotionInfoService.updateStatus(ppiList);

			//删除缓存
			playerPromotionInfoService.deletePageList("0");
			if (!logList.isEmpty()&&logList.size()>0) {
				logService.addBatch(logList);//记录操作日志
			}
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerPromotionInfoAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	

	
	/**
	 * @date : 2016年12月29日 下午7:21:38
	 * @author : Iris.Xiao
	 * @return
	 * @description : 置顶与取消置顶
	*/
	public String updateTopFlag() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			IosPlayerPromotionInfo ppi = playerPromotionInfoService.findById(infoId);
			//获取被置顶记录的所在地区置顶记录
			IosPlayerPromotionInfo p = playerPromotionInfoService.findTopByCountryCode(ppi.getCountryCode());
			if (p != null) {
				playerPromotionInfoService.updateTopFlag(infoId, p.getUid(),topFlag);
			}else{
				playerPromotionInfoService.updateTopFlag(infoId, null,topFlag);
			}

			//删除缓存
			playerPromotionInfoService.deletePageList("0");
			OperationLog oLog = new OperationLog(getIp(), "活动置顶管理", "活动置顶状态修改:" + ppi.getUid()+":"+ppi.getTitle()+(topFlag==1?"置顶":"取消置顶"),newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			if (topFlag==0) {
				setSuccessDispatch(GConstantAlert.GS_BOTTOM_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_TOP_SUCCESS,GConstantRedirect.GS_IOS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("IosPlayerPromotionInfoAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}


	@Override
	public String doExecute() throws Exception {
		return null;
	}
	
	public Page<IosPlayerPromotionInfo> getPage() {
		return page;
	}

	public void setPage(Page<IosPlayerPromotionInfo> page) {
		this.page = page;
	}

	public IosPlayerPromotionInfo getPlayerPromotionInfo() {
		return playerPromotionInfo;
	}

	public void setPlayerPromotionInfo(IosPlayerPromotionInfo playerPromotionInfo) {
		this.playerPromotionInfo = playerPromotionInfo;
	}

	public String getInfoId() {
		return infoId;
	}

	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public File getImageUpload() {
		return imageUpload;
	}

	public void setImageUpload(File imageUpload) {
		this.imageUpload = imageUpload;
	}

	public String getImageUploadFileName() {
		return imageUploadFileName;
	}

	public void setImageUploadFileName(String imageUploadFileName) {
		this.imageUploadFileName = imageUploadFileName;
	}

	public String getImageUploadContentType() {
		return imageUploadContentType;
	}

	public void setImageUploadContentType(String imageUploadContentType) {
		this.imageUploadContentType = imageUploadContentType;
	}

	public int getTopFlag() {
		return topFlag;
	}

	public void setTopFlag(int topFlag) {
		this.topFlag = topFlag;
	}

	public String getTopFlagIds() {
		return topFlagIds;
	}

	public void setTopFlagIds(String topFlagIds) {
		this.topFlagIds = topFlagIds;
	}

	public List<PlayerCountry> getPlayerCountryList() {
		return playerCountryList;
	}

	public void setPlayerCountryList(List<PlayerCountry> playerCountryList) {
		this.playerCountryList = playerCountryList;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}


}
