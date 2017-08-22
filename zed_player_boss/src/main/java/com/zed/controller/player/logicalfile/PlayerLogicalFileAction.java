/**
 * 用户资源信息列表
 */
package com.zed.controller.player.logicalfile;

import java.io.File;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.zed.api.aws.service.S3UploadApiService;
import com.zed.api.subtitle.bean.VideoSubtitleBean;
import com.zed.api.subtitle.service.VideoSubtitleApiService;
import com.zed.config.SubtitleConfig;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.country.PlayerCountry;
import com.zed.domain.player.logicalfile.PlayerLogicalFile;
import com.zed.domain.player.video.PlayerVideoDestFile;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.service.player.country.PlayerCountryService;
import com.zed.service.player.logicalfile.PlayerLogicalFileService;
import com.zed.service.player.player.PlayerVideoDestFileService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import com.zed.util.CommUtil;
import com.zed.util.CompressUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller("playerLogicalFileAction")
@Scope(value = "prototype")
public class PlayerLogicalFileAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
    private Page<HashMap> page = new Page<HashMap>();
	private String status;
	private String fileId;
	private String fileName;
	private String userId;
	private String areaCode;
	private List<PlayerCountry> playerCountryList;							//所有的地区国家码集合
	private PlayerLogicalFile playerLogicalFile;
	private String resId;
	private List<VideoSubtitleBean> videoSubtitleBeans;
	private List<File> file;
	private List<Integer> fileIndex;
	private List<String> fileFileName;
	private String delSubtitleIds;
	/**
	 * 视频数量
	 */
	private int videoCount;
	/**
	 * 字幕数量
	 */
	private int subtitleCount;
	@Resource(name="playerLogicalFileService")
	private PlayerLogicalFileService playerLogicalFileService;
	@Resource(name="playerCountryService")
	private PlayerCountryService playerCountryService;
	@Resource(name="videoSubtitleApiService")
	private VideoSubtitleApiService videoSubtitleApiService;
	@Resource(name="playerVideoDestFileService")
    private PlayerVideoDestFileService playerVideoDestFileService;
	@Resource
    private S3UploadApiService s3UploadApiService;
	@Resource
	private SubtitleConfig subtitleConfig;
	
	private Integer dimension;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public String list() {
		try {
			HashMap map = new HashMap();
//			page.setSorting("year DESC"); // 排序
			if (!CommUtil.isEmpty(fileName)) {
				map.put("fileName", fileName);
			}
			if (!CommUtil.isEmpty(userId)) {
				map.put("userId", userId);
			}
			if (!CommUtil.isEmpty(areaCode)) {
				map.put("countryCode", areaCode);
			}
			if (!CommUtil.isEmpty(fileId)) {
				map.put("fileId", fileId);
			}
			page.setParamsMap(map);
			page = playerLogicalFileService.findByPage(page);
			playerCountryList = playerCountryService.findAll();
			videoCount = videoSubtitleApiService.getSubtitlesCountByFileId(null);
			subtitleCount = videoSubtitleApiService.getSubtitleFilesCountByFileId(null);
			return SUCCESS;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerLogicalFileAction[list] failed: ",ex);
			}
			return ERROR;
		}
	}

	public String updateStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			PlayerLogicalFile pvr = playerLogicalFileService.findById(fileId);
			pvr.setStatus(Integer.valueOf(status));
			OperationLog oLog = new OperationLog(getIp(), "用户资源信息管理列表", "修改用户资源信息状态:" + pvr.getFileName()+(status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			playerLogicalFileService.updateStatus(pvr);
			logService.add(oLog);//记录操作日志
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_LOGICALFILE_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_LOGICALFILE_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerLogicalFileAction[updateStatus] failed: ",ex);
			}
			return ERROR;
		}

	}
	
	public String updateBatchStatus() {
		Admin sessionAdmin = null;
		try {
			sessionAdmin = getSessionAdmin();
			Date newdate = new Date();
			OperationLog oLog = new OperationLog(getIp(), "批量视频信息管理列表", "修改视频信息状态:" + (status.equals("1")?"启用":"禁用"),newdate , sessionAdmin.getAdminId());
			playerLogicalFileService.updateStatusBatch(status, fileId.split(","));
			logService.add(oLog);//记录操作日志
			if (status.equals("1")) {
				setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_LOGICALFILE_LIST_ACTION);
			}else{
				setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_LOGICALFILE_LIST_ACTION);
			}
			return GConstantRedirect.GS_OK;
		} catch (Exception ex) {
			if (ex instanceof AppValidationException) {
				AppValidationException e = (AppValidationException) ex;
				setErrorDispatch(e.gs_Code, e.gs_PrevLink);
			} else {
				setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
				Log.getLogger(this.getClass()).error("PlayerLogicalFileAction[updateBatchStatus] failed: ",ex);
			}
			return ERROR;
		}
		
	}
	
	public String updatePage() {
        try {
            playerLogicalFile = playerLogicalFileService.findById(fileId);
            PlayerVideoDestFile playerVideoDestFile = playerVideoDestFileService.getPlayerVideoDestFile(playerLogicalFile.getSourceFileId(), String.valueOf(PlayerVideoDestFile.Is.SOURCE.getStatus()));
            if (playerVideoDestFile != null && !CommUtil.isEmpty(playerVideoDestFile.getResId())) {
                resId = playerVideoDestFile.getResId();
                dimension = playerVideoDestFile.getDimension();
                videoSubtitleBeans = videoSubtitleApiService.query(playerVideoDestFile.getResId());
            }
            return SUCCESS;
        } catch (Exception ex) {
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerLogicalFileAction[updatePage] failed: ", ex);
            }
            return ERROR;
        }
    }

    public String update() {
        Admin sessionAdmin = null;
        try {
            sessionAdmin = getSessionAdmin();
            Date newdate = new Date();
            if (file != null && file.size() > 0) {
                String key = null, remotePath = null, outputPath = null;
                String fileName = null;
                String fileSuffix = null;
                for (int i = 0; i < file.size(); i++) {
                    if (CommUtil.isEmpty(fileFileName.get(i))) {
                        setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED,GConstantRedirect.GS_PLAYER_LOGICALFILE_LIST_ACTION);
                        return GConstantRedirect.GS_RESULT;
                    }
                    // 文件过滤
                    fileSuffix = fileFileName.get(i).substring(fileFileName.get(i).lastIndexOf(".") + 1);
                    if (!SubtitleConfig.ACCEPT_SUBTITLE_FORMAT.contains(fileSuffix.toLowerCase())) {
                        setResultDispatch(GConstantAlert.GS_UPLOAD_FAILED,GConstantRedirect.GS_PLAYER_LOGICALFILE_LIST_ACTION);
                        return GConstantRedirect.GS_RESULT;
                    }
                    // 文件压缩
                    fileName = fileFileName.get(i).substring(0, fileFileName.get(i).lastIndexOf("." + fileSuffix));
                    outputPath = subtitleConfig.getSubtitleCompressTmpPath() + "/" + fileName + ".zip";
                    CompressUtil.doCompress(file.get(i), fileFileName.get(i), "UTF-8", outputPath);
                    remotePath = subtitleConfig.getBucketName() + "/" + subtitleConfig.getSubtitleRootPath() + SubtitleConfig.OTHER_FOLDER;
                    key = s3UploadApiService.uploadFromLocal(outputPath, remotePath, fileName + ".zip", false, null, Base64.encodeBase64String(fileName.getBytes("UTF-8")));
                    videoSubtitleBeans.get(fileIndex.get(i)).setFilePath(key);
                    videoSubtitleBeans.get(fileIndex.get(i)).setResId(resId);
                }
            }
            playerLogicalFileService.update(playerLogicalFile, videoSubtitleBeans, delSubtitleIds,dimension);
            OperationLog oLog = new OperationLog(getIp(), "用户资源管理列表", "修改用户资源:" + playerLogicalFile.getFileId() ,newdate , sessionAdmin.getAdminId());
            logService.add(oLog);//记录操作日志
            setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_LOGICALFILE_LIST_ACTION);
            return GConstantRedirect.GS_OK;
        } catch (Exception ex) {
        	ex.printStackTrace();
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerLogicalFileAction[update] failed: ",ex);
            }
            return ERROR;
        }

    }
    
	/**
	 * flag ：true 表示不存在相同的国家码false 表示存在相同的国家码
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
			Boolean flag = Boolean.FALSE;
			if (!CommUtil.isEmpty(fieldValue)) {
				PlayerLogicalFile pvr = playerLogicalFileService.findById(fieldValue);
				if (pvr != null) {
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
				Log.getLogger(this.getClass()).error("PlayerLogicalFileAction[getFlag] failed: ",ex);
			}
		}finally{
			out.print(result);
			out.flush();
			out.close();
		}
		return NONE;
	}
	
	public String deleteUselessData(){
		Admin sessionAdmin = null;
		try {
            sessionAdmin = getSessionAdmin();
            Date newdate = new Date();
            long pageSize = 10000;
            for (long i = 1; i < 50; i++) {
            	playerLogicalFileService.deleteUselessDataFromEs(i,pageSize);
			}
            OperationLog oLog = new OperationLog(getIp(), "用户资源管理列表", "删除垃圾用户资源!",newdate , sessionAdmin.getAdminId());
            logService.add(oLog);//记录操作日志
            setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_LOGICALFILE_LIST_ACTION);
            return GConstantRedirect.GS_OK;
        } catch (Exception ex) {
        	ex.printStackTrace();
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerLogicalFileAction[deleteUselessData] failed: ",ex);
            }
            return ERROR;
        }
	}
	
	
	@Override
	public String doExecute() throws Exception {
		return null;
	}

    @SuppressWarnings("rawtypes")
    public Page<HashMap> getPage() {
		return page;
	}

	public void setPage(@SuppressWarnings("rawtypes") Page<HashMap> page) {
		this.page = page;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

    public PlayerLogicalFile getPlayerLogicalFile() {
        return playerLogicalFile;
    }

    public void setPlayerLogicalFile(PlayerLogicalFile playerLogicalFile) {
        this.playerLogicalFile = playerLogicalFile;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public List<VideoSubtitleBean> getVideoSubtitleBeans() {
        return videoSubtitleBeans;
    }

    public void setVideoSubtitleBeans(List<VideoSubtitleBean> videoSubtitleBeans) {
        this.videoSubtitleBeans = videoSubtitleBeans;
    }

    public List<File> getFile() {
        return file;
    }

    public void setFile(List<File> file) {
        this.file = file;
    }

    public List<Integer> getFileIndex() {
        return fileIndex;
    }

    public void setFileIndex(List<Integer> fileIndex) {
        this.fileIndex = fileIndex;
    }

    public List<String> getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(List<String> fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getDelSubtitleIds() {
        return delSubtitleIds;
    }

    public void setDelSubtitleIds(String delSubtitleIds) {
        this.delSubtitleIds = delSubtitleIds;
    }

	public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public int getSubtitleCount() {
        return subtitleCount;
    }

    public void setSubtitleCount(int subtitleCount) {
        this.subtitleCount = subtitleCount;
    }

    public Integer getDimension() {
		return dimension;
	}

	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}
	
}
