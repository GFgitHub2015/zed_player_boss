package com.zed.controller.iosplayer.videoclass;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.common.util.StringUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.iosplayer.videoclass.IosPlayerVideoClasses;
import com.zed.domain.player.slidershow.PlayerSliderShow;
import com.zed.domain.player.videoclass.PlayerVideoClasses;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.iosplayer.videoclass.IosPlayerVideoClassesService;
import com.zed.service.player.youtube.YouTuBeService;
import com.zed.service.system.operationlog.OperationLogService;
import com.zed.system.ExceptionConstants;
import com.zed.system.page.Page;

import net.sf.json.JSONObject;

@Controller("iosPlayerVideoClassesAction")
@Scope(value = "prototype")
public class IosPlayerVideoClassesAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private static final String KEYSWITHSORT = SystemConfig.getProperty("youtube.classes.key.top2");
	private Page<HashMap> page = new Page<HashMap>();
	private String classId;
	private String status;
	private String description;
	private String className;
	private List<Map<String, Object>> mapList;
	
	@Resource(name="iosPlayerVideoClassesService")
	private IosPlayerVideoClassesService playerVideoClassesService;
	@Resource(name="youTuBeService")
	private YouTuBeService youTuBeService;
	@Autowired
	protected OperationLogService operationLogService;
	
	public String list() {
		try {
			HashMap map = new HashMap();
			page.setSorting(" sort asc"); // 排序
			page.setPageSize(100);
			if (StringUtil.isNotBlank(description)) {
				map.put("description", description);
			}
			map.put("status", PlayerSliderShow.Status.START.getStatus());
			//map.put("origin", PlayerSliderShow.Origin.PLAYER.getOrigin());
			page.setParamsMap(map);
			page = playerVideoClassesService.findByPage(page);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerVideoClassesAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}
	
	public String listYouTuBe() {
		try{
			mapList = youTuBeService.findCategoriesByRegionCodesForIOS();
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerVideoClassesAction[listYouTuBe] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String add() {
		Admin sessionAdmin = null;
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			JSONObject json = new JSONObject();
			Integer initSort = 0;
			if (StringUtils.isNotBlank(KEYSWITHSORT)) {
				String[] classKeyArray = KEYSWITHSORT.split(",");
				initSort = classKeyArray.length;
			}
			
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String classKey = request.getParameter("classKey");
			String description = request.getParameter("description");
			IosPlayerVideoClasses pvc = new IosPlayerVideoClasses();
			pvc.setClassKey(PlayerVideoClasses.Prefix.YOUTUBE.getKey()+classKey);
			pvc.setDescription(description);
			pvc.setClassId(pvc.generateId());
			pvc.setSort(playerVideoClassesService.getLastSort(initSort));
			pvc.setOrigin(PlayerVideoClasses.Origin.PLAYER.getOrigin());
			pvc.setStatus(PlayerVideoClasses.Status.START.getStatus());
			pvc.setFixed(PlayerVideoClasses.Fixed.UnFixed.getStatus());
			pvc.setRecommended(PlayerVideoClasses.Recommended.UnRecommended.getStatus());
			pvc.setCreater(sessionAdmin.getAdminId());
			pvc.setCreateTime(new Timestamp(newdate.getTime()));
			pvc.setUpdater(sessionAdmin.getAdminId());
			pvc.setUpdateTime(new Timestamp(newdate.getTime()));
			playerVideoClassesService.add(pvc);
			
			OperationLog operationLog = new OperationLog(getIp(), "视频分类管理", "添加视频分类信息:"+pvc.getDescription(), newdate, sessionAdmin.getAdminId());
			operationLogService.add(operationLog);
			
			json.put("flag", Boolean.TRUE);
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerVideoClassesAction[add] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		return NONE;			
	}
	
	public String initData() {
		Admin sessionAdmin = null;
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			JSONObject json = new JSONObject();
			
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			Boolean flag = Boolean.FALSE;
			if (StringUtils.isNotBlank(KEYSWITHSORT)) {
				String[] classKeyArray = KEYSWITHSORT.split(",");
				Integer size = classKeyArray.length;
				if (size>0) {
					playerVideoClassesService.deleteBySort(size);
					for (int i = 0; i < classKeyArray.length; i++) {
						String[] classKeyWithSort = classKeyArray[i].split(":");
						if (classKeyWithSort.length==3) {
							IosPlayerVideoClasses pvc = new IosPlayerVideoClasses();
							pvc.setClassKey(PlayerVideoClasses.Prefix.PLAYER.getKey()+classKeyWithSort[1]);
							pvc.setDescription(classKeyWithSort[2]);
							pvc.setClassId(pvc.generateId());
							pvc.setSort(Integer.valueOf(classKeyWithSort[0]));
							pvc.setOrigin(PlayerVideoClasses.Origin.PLAYER.getOrigin());
							pvc.setStatus(PlayerVideoClasses.Status.START.getStatus());
							pvc.setFixed(PlayerVideoClasses.Fixed.Fixed.getStatus());
							pvc.setRecommended(PlayerVideoClasses.Recommended.Recommended.getStatus());
							pvc.setCreater(sessionAdmin.getAdminId());
							pvc.setCreateTime(new Timestamp(newdate.getTime()));
							pvc.setUpdater(sessionAdmin.getAdminId());
							pvc.setUpdateTime(new Timestamp(newdate.getTime()));
							playerVideoClassesService.add(pvc);
							
							OperationLog operationLog = new OperationLog(getIp(), "视频分类管理", "初始化视频分类信息:"+pvc.getDescription(), newdate, sessionAdmin.getAdminId());
							operationLogService.add(operationLog);
							flag = Boolean.TRUE;
						}
					}
				}
			}
			json.put("flag", flag);
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerVideoClassesAction[initData] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		return NONE;			
	}

	public String update() {
		Admin sessionAdmin = null;
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			JSONObject json = new JSONObject();
			
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			//String classKey = request.getParameter("classKey");
			String sort = request.getParameter("sort");
			String cid = request.getParameter("classId");
			String description = request.getParameter("description");
			Integer fixed = Integer.valueOf(request.getParameter("fixed"));
			//Integer recommended = Integer.valueOf(request.getParameter("recommended"));
			IosPlayerVideoClasses pvc = playerVideoClassesService.findById(cid);
			/*pvc.setClassKey(PlayerVideoClasses.Prefix.YOUTUBE.getKey()+classKey);*/
			pvc.setDescription(description);
			pvc.setSort(Integer.valueOf(sort));
			pvc.setUpdater(sessionAdmin.getAdminId());
			pvc.setUpdateTime(new Timestamp(newdate.getTime()));
			pvc.setFixed(fixed);
			OperationLog operationLog = new OperationLog(getIp(), "视频分类管理", "修改视频分类信息:"+pvc.getDescription(), newdate, sessionAdmin.getAdminId());
			playerVideoClassesService.update(pvc);
			operationLogService.add(operationLog);

			json.put("flag", Boolean.TRUE);
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerVideoClassesAction[update] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		return NONE;			

	}

	public String delete() {
		Admin sessionAdmin = null;
		PrintWriter out = null;
		String result = "";
		try{
			out = response.getWriter();
			JSONObject json = new JSONObject();
			
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			String id = request.getParameter("classId");
			Boolean flag = Boolean.FALSE;
			if (StringUtils.isNotBlank(id)) {
				IosPlayerVideoClasses pvc = playerVideoClassesService.findById(id);
				OperationLog operationLog = new OperationLog(getIp(), "视频分类管理", "删除视频分类信息:"+pvc.getClassId(), newdate, sessionAdmin.getAdminId());
				playerVideoClassesService.delete(new String[]{id});
				logService.add(operationLog);//记录操作日志
				flag = Boolean.TRUE;
			}
			json.put("flag", flag);
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerVideoClassesAction[delete] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		return NONE;		
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
			String classKey = request.getParameter("classKey");
			String sort = request.getParameter("sort");
			String cid = request.getParameter("classId");
			String description = request.getParameter("description");
			JSONObject json = new JSONObject();
			Boolean sortFlag = Boolean.FALSE;
			Boolean descriptionFlag = Boolean.FALSE;
			if (StringUtils.isNotBlank(classKey)) {
				if (StringUtils.isNotBlank(sort)) {
					if (StringUtils.isNotBlank(cid)) {
						sortFlag = playerVideoClassesService.getBySortAndClassKey(cid, Integer.valueOf(sort), IosPlayerVideoClasses.Prefix.YOUTUBE.getKey()+classKey);
					}else{
						sortFlag = playerVideoClassesService.getBySortAndClassKey(null, Integer.valueOf(sort), IosPlayerVideoClasses.Prefix.YOUTUBE.getKey()+classKey);
					}
				}else{
					sortFlag = playerVideoClassesService.getByClasskey(IosPlayerVideoClasses.Prefix.YOUTUBE.getKey()+classKey);
				}
			}else if(StringUtils.isBlank(classKey)&&StringUtils.isNotBlank(sort)) {
				if (StringUtils.isNotBlank(cid)) {
					sortFlag = playerVideoClassesService.getBySortAndClassKey(cid, Integer.valueOf(sort), null);
				}else{
					json.put("error", "参数错误！");
				}
			}
			
			if (StringUtils.isNotBlank(classKey)) {
				if (StringUtils.isNotBlank(description)) {
					if (StringUtils.isNotBlank(cid)) {
						descriptionFlag = playerVideoClassesService.getByDescriptionAndClassKey(cid, description, IosPlayerVideoClasses.Prefix.YOUTUBE.getKey()+classKey);
					}else{
						descriptionFlag = playerVideoClassesService.getByDescriptionAndClassKey(null, description, IosPlayerVideoClasses.Prefix.YOUTUBE.getKey()+classKey);
					}
				}else{
					descriptionFlag = playerVideoClassesService.getByClasskey(IosPlayerVideoClasses.Prefix.YOUTUBE.getKey()+classKey);
				}
			}else if(StringUtils.isBlank(classKey)&&StringUtils.isNotBlank(description)) {
				if (StringUtils.isNotBlank(cid)) {
					descriptionFlag = playerVideoClassesService.getByDescriptionAndClassKey(cid, description, null);
				}else{
					json.put("error", "参数错误！");
				}
			}
			json.put("sortFlag", sortFlag);
			json.put("descriptionFlag", descriptionFlag);
			result = json.toString();
		}catch(Exception ex){
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerVideoClassesAction[isExist] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		return NONE;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	public void setMapList(List<Map<String, Object>> mapList) {
		this.mapList = mapList;
	}
}
