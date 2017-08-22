package com.zed.controller.iosplayer.slidershow;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.common.util.StringUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.iosplayer.slidershow.IosPlayerSliderShow;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.slidershow.PlayerSliderShow;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.iosplayer.slidershow.IosPlayerSliderShowService;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;

import net.sf.json.JSONObject;

@Controller("iosPlayerSliderShowAction")
@Scope(value = "prototype")
public class IosPlayerSliderShowAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;	private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.slidershow");

	private Page<HashMap> page = new Page<HashMap>();
	private IosPlayerSliderShow playerSliderShow;
	private String sliderShowId;
	private String type;
	private Integer status;
	private String areaCode;
	private String creater;
	private String origin;
	private File upLoadPicture;
	private String upLoadPictureContentType; 								// 上传的文件的数据类型
	private String upLoadPictureFileName; 									// 上传的文件的名称 
	private List<PlayerCountry> playerCountryList;							//所有的地区国家码集合
	private String description;
	
	@Autowired
	private UploadService uploadService;
	@Resource(name="iosPlayerSliderShowService")
	private IosPlayerSliderShowService playerSliderShowService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" last_time DESC"); // 排序
			if (StringUtil.isNotBlank(description)) {
				map.put("description", description);
			}
			if (StringUtil.isNotBlank(areaCode)) {
				map.put("areaCode", areaCode);
			}
			map.put("status", PlayerSliderShow.Status.DELETE.getStatus());
			map.put("origin", PlayerSliderShow.Origin.PLAYER.getOrigin());
			page.setParamsMap(map);
			page = playerSliderShowService.findByPage(page);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSliderShowAction[list] failed: ",ex);
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
				//上传背景图片
				String url = uploadService.put(upLoadPicture,remotePath, upLoadPictureFileName);
				if (!CommUtil.isEmpty(url)) {
					//添加背景图片
					playerSliderShow.setImgUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_SLIDER_SHOW_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			playerSliderShow.setSliderShowId(playerSliderShow.generateId());
			OperationLog operationLog = new OperationLog(getIp(), "轮播图IOS管理", "添加轮播图信息IOS:"+playerSliderShow.getSliderShowId()+"," , newdate, sessionAdmin.getAdminId());
			playerSliderShow.setAreaCode(null);
			playerSliderShow.setCreater(sessionAdmin.getAdminId());
			playerSliderShow.setLastTime(new Timestamp(newdate.getTime()));
			playerSliderShowService.add(playerSliderShow);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_SLIDER_SHOW_LIST_ACTION);
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSliderShowAction[add] failed: ",ex);
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
				Log.getLogger(this.getClass()).error("PlayerSliderShowAction[addPage] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String updatePage() {
		try {
			playerSliderShow = playerSliderShowService.findById(sliderShowId);
			playerCountryList = playerCountryService.findAll();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSliderShowAction[updatePage] failed: ",ex);
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
				//上传背景图片
				String url = uploadService.put(upLoadPicture,remotePath, upLoadPictureFileName);
				if (!CommUtil.isEmpty(url)) {
					//添加背景图片
					playerSliderShow.setImgUrl(url);
				} else {
					setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED, GConstantRedirect.GS_PLAYER_SLIDER_SHOW_LIST_ACTION);
					return GConstantRedirect.GS_RESULT;
				}
			}
			playerSliderShow.setCreater(sessionAdmin.getAdminId());
			playerSliderShow.setLastTime(new Timestamp(newdate.getTime()));
			OperationLog operationLog = new OperationLog(getIp(), "轮播图管理IOS", "修改轮播图信息IOS:"+playerSliderShow.getSliderShowId(), newdate, sessionAdmin.getAdminId());
			playerSliderShowService.update(playerSliderShow);
			operationLogService.add(operationLog);
			setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_SLIDER_SHOW_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSliderShowAction[update] failed: ",ex);
			}
			return ERROR;
		}

	}

	public String delete() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String[] sliderShowIds=sliderShowId.split(",");
			List<OperationLog> logList = new ArrayList<OperationLog>();
			for (String id : sliderShowIds) {
				IosPlayerSliderShow psn = playerSliderShowService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "轮播图管理", "删除轮播图信息:"+psn.getSliderShowId(), newdate, sessionAdmin.getAdminId());
				logList.add(operationLog);
			}
			playerSliderShowService.delete(sliderShowIds);
			
			if (logList!=null && logList.size()>0 && !logList.isEmpty()) {
				logService.addBatch(logList);//记录操作日志
			}
			
			setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_SLIDER_SHOW_LIST_ACTION);
			return GConstantRedirect.GS_OK;

		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSliderShowAction[delete] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	/**
	 * flag ：false 表示不存在 true 表示存在
	 * @return
	 */
	public String isExist() {
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			String ac = request.getParameter("areaCode");
			String sort = request.getParameter("sort");
			String id = request.getParameter("sliderShowId");
			JSONObject json = new JSONObject();
			Boolean flag = Boolean.FALSE;
			Set<String> areaCodeSet = new HashSet<String>();
			if (StringUtils.isNotBlank(sort)&&StringUtils.isNotBlank(ac)) {
//				ac = ac.substring(0, ac.lastIndexOf(","));
				String[] areaCodes = ac.split(",");
				if (!CommUtil.isEmpty(id)) {
					for (String acs : areaCodes) {
						flag = playerSliderShowService.getBySortAndAreaCode(id, Integer.valueOf(sort), acs);
						if (flag) {
							PlayerCountry pc = playerCountryService.findByCountryCode(acs);
							if (pc != null) {
								areaCodeSet.add(pc.getCountryNameZh());
							}
						}
					}
				}else{
					for (String acs : areaCodes) {
						flag = playerSliderShowService.getBySortAndAreaCode(null, Integer.valueOf(sort), acs);
						if (flag) {
							PlayerCountry pc = playerCountryService.findByCountryCode(acs);
							if (pc != null) {
								areaCodeSet.add(pc.getCountryNameZh());
							}
						}
					}
				}
			}else{
				if (CommUtil.isEmpty(id)) {
					id=null;
				}
				flag = playerSliderShowService.getBySortAndAreaCode(id, Integer.valueOf(sort), ac);
				if (flag) {
					PlayerCountry pc = playerCountryService.findByCountryCode(ac);
					if (pc != null) {
						areaCodeSet.add(pc.getCountryNameZh());
					}
				}
			}
			json.put("data", flag);
			json.put("areaCodeNames", areaCodeSet.toString());
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSliderShowAction[isExist] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		return NONE;
	}
	
	/**
	 * @date : 2017年06月30日 上午10:10:08
	 * @author : X.Long
	 * @return
	 * @description : 根据slider_show_id更新轮播图状态信息
	*/
	public String updateStatusByUid() {
		try {
			playerSliderShowService.update(playerSliderShow);
			if (playerSliderShow.getStatus().equals(1)) {
                setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
            } else {
                setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
            }
            return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerSliderShowAction[updatePlayTimeById] failed: ",ex);
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public IosPlayerSliderShow getPlayerSliderShow() {
		return playerSliderShow;
	}

	public void setPlayerSliderShow(IosPlayerSliderShow playerSliderShow) {
		this.playerSliderShow = playerSliderShow;
	}

	public String getSliderShowId() {
		return sliderShowId;
	}

	public void setSliderShowId(String sliderShowId) {
		this.sliderShowId = sliderShowId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
