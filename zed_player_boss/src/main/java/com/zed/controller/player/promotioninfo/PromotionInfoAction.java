package com.zed.controller.player.promotioninfo;

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
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.playerpromotioninfo.PlayerPromotionInfo;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.promotioninfo.PlayerPromotionInfoService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("promotionInfoAction")
@Scope(value = "prototype")
public class PromotionInfoAction extends BaseAction {

	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.hotvideo");
	private static final String cdnUrl = SystemConfig.getProperty("image.cdn.url");//图片cdn加速地址

	@Autowired
	private UploadService uploadService;
	
	private static final long serialVersionUID = 1L;
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerPromotionInfo playerPromotionInfo;
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

	@Autowired
	private PlayerPromotionInfoService playerPromotionInfoService;
	@Autowired
	protected OperationLogService operationLogService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
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
			Page<HashMap> topFlagPage = new Page<HashMap>();
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("topFlag", "1");
			topFlagPage.setParamsMap(param);
			//查询是否有置顶记录
			playerPromotionInfoService.findByPage(topFlagPage);
			List<HashMap> topFlagList = topFlagPage.getResult();
			if(CommUtil.isEmpty(topFlagIds)){
				topFlagIds="";//这里初始值是null
			}
			if(topFlagList!=null&&topFlagList.size()>0){
				for (HashMap hashMap : topFlagList) {
					topFlagIds+=hashMap.get("id").toString()+",";
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
				setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_ONE,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
			//2016年12月30日10:43:07 去掉活动时间限制
//			if(checkTime(playerPromotionInfo)){
//				setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_TWO,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
//				return GConstantRedirect.GS_RESULT;
//			}
//			playerPromotionInfo.setStatus(ConstantType.CommonType.START.getStatus());
			playerPromotionInfo.setContent("");
			//上传图片
			if(null != imageUpload) {
				String imageUrl = uploadService.put(imageUpload, remotePath, imageUploadFileName);
				if (StringUtil.isNotEmpty(imageUrl)) {
					imageUrl = playerPromotionInfoService.replaceCdnUrl(imageUrl, cdnUrl);
					playerPromotionInfo.setImageUrl(imageUrl);
				}
			}
			areaCode = areaCode.substring(0, areaCode.lastIndexOf(","));
			String[] areaCodes = areaCode.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String ac : areaCodes) {
				OperationLog operationLog = new OperationLog(getIp(), "活动推送消息管理", "添加活动推送消息:"+playerPromotionInfo.getTitle()+", 国家码："+ac , newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
				playerPromotionInfo.setId(playerPromotionInfo.generateId());
				playerPromotionInfo.setCountryCode(ac);
				playerPromotionInfoService.add(playerPromotionInfo);
			}
			if (!logList.isEmpty()&&logList.size()>0) {
				operationLogService.addBatch(logList);
			}
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PromotionInfoAction[add] failed: ",ex);
			}
			return ERROR;
		}
	}

	//2016年12月30日10:43:07 去掉活动时间限制
//	private Boolean checkTime(PlayerPromotionInfo p) {
//		Boolean flag = Boolean.FALSE;
//		List<PlayerPromotionInfo> list = playerPromotionInfoService.findAll();
//		for (PlayerPromotionInfo ppi : list) {
//			if (!p.getId().equals(ppi.getId())&&p.getStatus()==ConstantType.CommonType.START.getStatus()&&ppi.getStatus()==ConstantType.CommonType.START.getStatus()&&(ppi.getStartTime().after(p.getStartTime())&&ppi.getStartTime().before(p.getEndTime()))||(ppi.getEndTime().after(p.getStartTime())&&ppi.getEndTime().before(p.getEndTime()))) {
//				flag = Boolean.TRUE;
//			}
//		}
//		return flag;
//	}

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
				Log.getLogger(this.getClass()).error("PromotionInfoAction[addPage] failed: ",ex);
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
				Log.getLogger(this.getClass()).error("PromotionInfoAction[updatePage] failed: ",ex);
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
				setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_ONE,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}

			PlayerPromotionInfo oldPlayerPromotionInfo = playerPromotionInfoService.findById(playerPromotionInfo.getId());
			if(!(oldPlayerPromotionInfo.getStartTime().getTime() == playerPromotionInfo.getStartTime().getTime() && oldPlayerPromotionInfo.getEndTime().getTime() == playerPromotionInfo.getEndTime().getTime())){

				//2016年12月30日10:43:07 去掉活动时间限制
//				if(checkTime(playerPromotionInfo)){
//					setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_TWO,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
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
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PromotionInfoAction[update] failed: ",ex);
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
				PlayerPromotionInfo ppi = playerPromotionInfoService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "活动推送消息管理", "删除活动推送消息:"+ppi.getTitle(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerPromotionInfoService.delete(infoIds);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PromotionInfoAction[delete] failed: ",ex);
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
				Log.getLogger(this.getClass()).error("PromotionInfoAction[detail] failed: ",ex);
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
			List<PlayerPromotionInfo> ppiList = playerPromotionInfoService.findById(ids);
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (PlayerPromotionInfo ppi : ppiList) {
				ppi.setStatus(Integer.valueOf(status));
				OperationLog oLog = new OperationLog(getIp(), "活动推送消息管理", "状态活动推送消息修改:" + ppi.getTitle()+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
				logList.add(oLog);
			}
			playerPromotionInfoService.updateStatus(ppiList);
			if (!logList.isEmpty()&&logList.size()>0) {
				logService.addBatch(logList);//记录操作日志
			}
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PromotionInfoAction[updateStatus] failed: ",ex);
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
			PlayerPromotionInfo ppi = playerPromotionInfoService.findById(infoId);
			//获取被置顶记录的所在地区置顶记录
			PlayerPromotionInfo p = playerPromotionInfoService.findTopByCountryCode(ppi.getCountryCode());
			if (p != null) {
				playerPromotionInfoService.updateTopFlag(infoId, p.getId(),topFlag);
			}else{
				playerPromotionInfoService.updateTopFlag(infoId, null,topFlag);
			}
			OperationLog oLog = new OperationLog(getIp(), "活动置顶管理", "活动置顶状态修改:" + ppi.getId()+":"+ppi.getTitle()+(topFlag==1?"置顶":"取消置顶"),newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			if (topFlag==0) {
				setSuccessDispatch(GConstantAlert.GS_BOTTOM_SUCCESS,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_TOP_SUCCESS,GConstantRedirect.GS_PLAYER_PROMOTION_INFO_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PromotionInfoAction[updateStatus] failed: ",ex);
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

	public PlayerPromotionInfo getPlayerPromotionInfo() {
		return playerPromotionInfo;
	}

	public void setPlayerPromotionInfo(PlayerPromotionInfo playerPromotionInfo) {
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
