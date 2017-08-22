package com.zed.controller.player.recommendkeyword;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.controller.system.BaseAction;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.recommendkeyword.PlayerRecommendKeyWord;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.recommendkeyword.PlayerRecommendKeyWordService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

@Controller("playerRecommendKeyWordAction")
@Scope(value = "prototype")
public class PlayerRecommendKeyWordAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.keyword.recommend");
	private Page<HashMap> page = new Page<HashMap>();
	private PlayerRecommendKeyWord recommendKeyWord;
	private String recommendKeyWordId;
	private String status;
	private String keyword;
	private String areaCode;
//	private List<String> areaCodeList;
	private File upLoadPicture;
	private String upLoadPictureContentType; 								// 上传的文件的数据类型
	private String upLoadPictureFileName; 									// 上传的文件的名称 
	private List<PlayerCountry> playerCountryList;							//所有的地区国家码集合
	
	@Autowired
	private PlayerRecommendKeyWordService playerRecommendKeyWordService;
	@Autowired
	private UploadService uploadService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;

	public String list() {
		try {
			HashMap map = new HashMap();
			if (!CommUtil.isEmpty(status)) {
				map.put("status", status);
			}
			if (!CommUtil.isEmpty(keyword)) {
				map.put("keyword", keyword);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("areaCode", areaCode);
			}
//			map.put("areaCode", AREA_CODE);
			page.setSorting("sort DESC"); // 排序
			page.setParamsMap(map);
			page = playerRecommendKeyWordService.findByPage(page);
			playerCountryList = playerCountryService.findAll();
//			areaCodeList = playerRecommendKeyWordService.findAllAreaCode();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String urlToSave = "";
			if (upLoadPicture != null) {
				//上传热词背景图片
				String url = uploadService.put(upLoadPicture,remotePath, upLoadPictureFileName);
				if (!CommUtil.isEmpty(url)) {
					//添加热词背景图片
					urlToSave = url;
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			areaCode = areaCode.substring(0, areaCode.lastIndexOf(","));
			String[] areaCodes = areaCode.split(",");
			List<PlayerRecommendKeyWord> listToAdd = new ArrayList<PlayerRecommendKeyWord>();
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String ac : areaCodes) {
				if (playerRecommendKeyWordService.exist(recommendKeyWord.getKeyword().trim(), ac)) {
					setResultDispatch(GConstantAlert.GS_KEYWORD_START_SUCCESS, GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}else{
					PlayerRecommendKeyWord prk = new PlayerRecommendKeyWord();
					prk.setAreaCode(ac);
					prk.setKeywordId(prk.generateId());
					prk.setCreater(sessionAdmin.getAdminId());
					prk.setCreateTime(new Timestamp(newdate.getTime()));
					prk.setDescription(recommendKeyWord.getDescription());
					if (StringUtils.isNotBlank(urlToSave)) {
						prk.setImgUrl(urlToSave);
					}else{
						prk.setImgUrl(recommendKeyWord.getImgUrl());
					}
					prk.setKeyword(recommendKeyWord.getKeyword().trim());
					prk.setSort(recommendKeyWord.getSort());
					prk.setStatus(recommendKeyWord.getStatus());
					listToAdd.add(prk);
					OperationLog oLog = new OperationLog(getIp(), "搜索推荐词管理列表", "添加推荐词:" + prk.getKeyword() +", 国家码:"+prk.getAreaCode(),newdate , sessionAdmin.getAdminId());
					logList.add(oLog);
				}
			}
			playerRecommendKeyWordService.add(listToAdd);
			if (!logList.isEmpty()&&logList.size()>0) {
				logService.addBatch(logList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[add] failed: ",ex);
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
			Log.getLogger(this.getClass()).error("RecommendKeyWordAction[addPage] failed: ",ex);
		}
		return ERROR;
	}
	}

	public String updatePage() {
		try {
			recommendKeyWord = playerRecommendKeyWordService.findById(recommendKeyWordId);
//			areaCodeList = playerRecommendKeyWordService.findAllAreaCode();
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[updatePage] failed: ",ex);
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
				//上传热词背景图片
				String url = uploadService.put(upLoadPicture,remotePath, upLoadPictureFileName);
				if (!CommUtil.isEmpty(url)) {
					//添加热词背景图片
					recommendKeyWord.setImgUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			recommendKeyWord.setAreaCode(recommendKeyWord.getAreaCode().trim());
			recommendKeyWord.setKeyword(recommendKeyWord.getKeyword().trim());
			recommendKeyWord.setUpdateTime(new Timestamp(newdate.getTime()));
			recommendKeyWord.setUpdater(sessionAdmin.getAdminId());
			playerRecommendKeyWordService.update(recommendKeyWord);
			OperationLog oLog = new OperationLog(getIp(), "搜索推荐词管理列表", "修改推荐词:" + recommendKeyWord.getKeyword() ,newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] recommendKeyWordIds=recommendKeyWordId.split(",");
			List<String> recommendKeyWordIdsList = Arrays.asList(recommendKeyWordIds);
			List<OperationLog> operationLogList = new ArrayList<OperationLog>();
			for (String id : recommendKeyWordIdsList) {
				PlayerRecommendKeyWord hkw = playerRecommendKeyWordService.findById(id);
				OperationLog oLog = new OperationLog(getIp(), "搜索推荐词管理列表", "删除推荐词:" + hkw.getKeyword() ,newdate , sessionAdmin.getAdminId());
				operationLogList.add(oLog);
			}
			playerRecommendKeyWordService.delete(recommendKeyWordIds);
			
			if (operationLogList!=null && operationLogList.size()>0 && !operationLogList.isEmpty()) {
				logService.addBatch(operationLogList);//记录操作日志
			}
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_DOWNLOADLIST_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String detail() {
		try {
			recommendKeyWord = playerRecommendKeyWordService.findById(recommendKeyWordId);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[detail] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String [] ids = recommendKeyWordId.split(",");
			List<PlayerRecommendKeyWord> prkList = playerRecommendKeyWordService.findById(ids);
			List<OperationLog> operationLogList = new ArrayList<OperationLog>();
			for (PlayerRecommendKeyWord prk : prkList) {
				prk.setStatus(Integer.valueOf(status));
				prk.setUpdateTime(new Timestamp(newdate.getTime()));
				prk.setUpdater(sessionAdmin.getAdminId());
				OperationLog oLog = new OperationLog(getIp(), "搜索推荐词管理列表", "状态修改:" + prk.getKeyword()+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
				operationLogList.add(oLog);
			}
			playerRecommendKeyWordService.updateStatus(prkList);
			if (!operationLogList.isEmpty()&&operationLogList.size()>0) {
				logService.addBatch(operationLogList);//记录操作日志
			}
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
/*	public String updateAllCache() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			Boolean flag = playerRecommendKeyWordService.updateAllCache(AREA_CODE);
			OperationLog oLog = new OperationLog(getIp(), "搜索推荐词管理列表", "强制同步推荐词缓存数据",newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			if (flag) {
				setSuccessDispatch(GConstantAlert.GS_SYNCHRON_SUCCESS,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
				return GConstantRedirect.GS_OK;
			}else{
				setResultDispatch(GConstantAlert.GS_SYNCHRON_FAILED,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[updateAllCache] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String updateAllData() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			Boolean flag = playerRecommendKeyWordService.updateAllData();
			OperationLog oLog = new OperationLog(getIp(), "搜索推荐词管理列表", "强制更新推荐词数据",newdate , sessionAdmin.getAdminId());
			logService.add(oLog);//记录操作日志
			if (flag) {
				setSuccessDispatch(GConstantAlert.GS_UPDATEALL_SUCCESS,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
				return GConstantRedirect.GS_OK;
			}else{
				setResultDispatch(GConstantAlert.GS_UPDATEALL_FAILED,GConstantRedirect.GS_PLAYER_RECOMMENDKEYWORD_LIST_ACTION);
				return GConstantRedirect.GS_RESULT;
			}
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("RecommendKeyWordAction[updateAllData] failed: ",ex);
			}
			return ERROR;
		}
		
	}*/

	@Override
	public String doExecute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(Page<HashMap> page) {
		this.page = page;
	}

	public PlayerRecommendKeyWord getRecommendKeyWord() {
		return recommendKeyWord;
	}

	public void setRecommendKeyWord(PlayerRecommendKeyWord recommendKeyWord) {
		this.recommendKeyWord = recommendKeyWord;
	}

	public String getRecommendKeyWordId() {
		return recommendKeyWordId;
	}

	public void setRecommendKeyWordId(String recommendKeyWordId) {
		this.recommendKeyWordId = recommendKeyWordId;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public List<PlayerCountry> getPlayerCountryList() {
		return playerCountryList;
	}

	public void setPlayerCountryList(List<PlayerCountry> playerCountryList) {
		this.playerCountryList = playerCountryList;
	}

}
