package com.zed.controller.player.screen;

import com.zed.common.util.StringUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.playerpromotioninfo.PlayerPromotionInfo;
import com.zed.domain.player.screen.PlayerScreen;
import com.zed.domain.system.Admin;
import com.zed.domain.system.OperationLog;
import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.player.screen.PlayerScreenService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import com.zed.system.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.*;

/**
 * Created by yuw on 2016/12/15.
 */
@Controller("playerScreenAction")
@Scope("prototype")
public class PlayerScreenAction extends BaseAction {


    private static final long serialVersionUID = -692471364881784680L;

    private static final String remotePath = SystemConfig.getProperty("aws.remotepath.player.screen");

    @Autowired
    private PlayerScreenService playerScreenService;
    @Autowired
    private UploadService uploadService;

    private Page<PlayerScreen> page;

    private PlayerScreen playerScreen;

    private String infoId;

    private String status;

    private String startDate;

    private String endDate;

    private File imageUpload;

    private String imageUploadFileName;

    private String imageUploadContentType;


    public String list(){
        if(null == page){
            page = new Page<>();
        }
        Map<String,Object> map = new HashMap<>();
        page.setSorting(" update_time desc");
        if(StringUtil.isNotEmpty(startDate)){
            map.put("startTime",startDate+" 00:00:00");
        }
        if(StringUtil.isNotEmpty(endDate)){
            map.put("endTime",endDate+" 23:59:59");
        }
        page.setParamsMap(map);
        page = playerScreenService.findByPage(page);
        return SUCCESS;
    }

    public String addPage() {
        return SUCCESS;
    }


    public String add(){
        try {
            if (playerScreen.getEndTime().before(playerScreen.getStartTime())) {
                setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_ONE, GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
                return GConstantRedirect.GS_RESULT;
            }

            if(checkTime(playerScreen)){
                setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_TWO,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
                return GConstantRedirect.GS_RESULT;
            }
            if (null != imageUpload) {
                String imageUrl = uploadService.put(imageUpload, remotePath, imageUploadFileName);
                if (StringUtil.isNotEmpty(imageUrl)) {
                    playerScreen.setImageUrl(imageUrl);
                }
            }
            playerScreenService.add(playerScreen);
            setSuccessDispatch(GConstantAlert.GS_INSERT_SUCCESS,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
            return GConstantRedirect.GS_OK;
        }catch (Exception ex){
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerScreenAction[add] failed: ",ex);
            }
            return ERROR;
        }
    }


    public String updatePage() {
        try {
            playerScreen = playerScreenService.findById(infoId);
            return SUCCESS;
        } catch (Exception ex) {
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerScreenAction[updatePage] failed: ",ex);
            }
            return ERROR;
        }
    }


    public String update() {
        try {

            Date newdate = new Date();
            if (playerScreen.getEndTime().getTime() < playerScreen.getStartTime().getTime()) {
                setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_ONE,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
                return GConstantRedirect.GS_RESULT;
            }

            PlayerScreen oldPlayerScreen = playerScreenService.findById(playerScreen.getId());
            if(!(oldPlayerScreen.getStartTime().getTime() == playerScreen.getStartTime().getTime() && oldPlayerScreen.getEndTime().getTime()== playerScreen.getEndTime().getTime())){
                if(checkTime(playerScreen)){
                    setResultDispatch(GConstantAlert.GS_TIME_MESSAGE_TWO,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
                    return GConstantRedirect.GS_RESULT;
                }
            }
            //上传图片
            if(null != imageUpload) {
                String imageUrl = uploadService.put(imageUpload, remotePath, imageUploadFileName);
                if (StringUtil.isNotEmpty(imageUrl)) {
                    playerScreen.setImageUrl(imageUrl);
                }
            }
            playerScreenService.update(playerScreen);
            setSuccessDispatch(GConstantAlert.GS_UPDATE_SUCCESS,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
            return GConstantRedirect.GS_OK;

        } catch (Exception ex) {
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerScreenAction[update] failed: ",ex);
            }
            return ERROR;
        }

    }


    public String delete() {
        try {
            String[] infoIds=infoId.split(",");
            playerScreenService.delete(infoIds);
            setSuccessDispatch(GConstantAlert.GS_DELETE_SUCCESS,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
            return GConstantRedirect.GS_OK;
        } catch (Exception ex) {
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerScreenAction[delete] failed: ",ex);
            }
            return ERROR;
        }
    }


    public String detail() {
        try {
            playerScreen = playerScreenService.findById(infoId);
            return SUCCESS;
        } catch (Exception ex) {
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerScreenAction[detail] failed: ",ex);
            }
            return ERROR;
        }
    }


    public String updateStatus() {
        try {
            playerScreenService.updateStatus(infoId,Integer.parseInt(status));
            if (status.equals("1")) {
                setSuccessDispatch(GConstantAlert.GS_START_SUCCESS,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
            }else{
                setSuccessDispatch(GConstantAlert.GS_FORBIDDEN_SUCCESS,GConstantRedirect.GS_PLAYER_SCREEN_LIST_ACTION);
            }
            return GConstantRedirect.GS_OK;
        } catch (Exception ex) {
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
                Log.getLogger(this.getClass()).error("PlayerScreenAction[updateStatus] failed: ",ex);
            }
            return ERROR;
        }

    }


    private boolean checkTime(PlayerScreen playerScreen){
        boolean checkTime = false;
        List<PlayerScreen> list = playerScreenService.findAll();
        if(null != list){
            for(PlayerScreen screen : list){
                boolean start = (playerScreen.getStartTime().getTime() >= screen.getStartTime().getTime()) && (playerScreen.getStartTime().getTime() <= screen.getEndTime().getTime());
                boolean end = (playerScreen.getEndTime().getTime() >= screen.getStartTime().getTime()) && (playerScreen.getEndTime().getTime()<= screen.getEndTime().getTime());
                if(start || end){
                   checkTime = true;
                    break;
                }
            }
        }
        return checkTime;
    }

    @Override
    public String doExecute() throws Exception {
        return null;
    }

    public Page<PlayerScreen> getPage() {
        return page;
    }

    public void setPage(Page<PlayerScreen> page) {
        this.page = page;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public PlayerScreen getPlayerScreen() {
        return playerScreen;
    }

    public void setPlayerScreen(PlayerScreen playerScreen) {
        this.playerScreen = playerScreen;
    }

    public String getInfoId() {
        return infoId;
    }

    public void setInfoId(String infoId) {
        this.infoId = infoId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
