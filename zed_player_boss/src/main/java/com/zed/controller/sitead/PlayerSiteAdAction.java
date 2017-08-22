package com.zed.controller.sitead;

import com.zed.common.util.StringUtil;
import com.zed.controller.system.BaseAction;
import com.zed.domain.player.playeruser.PlayerSiteAd;
import com.zed.exception.AppValidationException;
import com.zed.listener.SystemConfig;
import com.zed.service.common.upload.UploadService;
import com.zed.service.player.playeruser.PlayerSiteAdService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstantAlert;
import com.zed.system.GConstantRedirect;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.Date;

/**
 * Created by Sumail on 2017/6/22.
 */
@Controller("playerSiteAdAction")
@Scope(value = "prototype")
public class PlayerSiteAdAction extends BaseAction{

    private static String remotePath = SystemConfig.getProperty("aws.remotepath.player.site.ad");


    Logger log = LoggerFactory.getLogger(PlayerSiteAdAction.class);

    private static String JS_STR = null;

    @Autowired
    private PlayerSiteAdService playerSiteAdService;

    @Autowired
    private UploadService uploadService;

    private String userId;

    private PlayerSiteAd playerSiteAd;

    private File bannerImage;

    private String bannerImageFileName;

    private File viewImage;

    private String viewImageFileName;

    public String setAdvertisement(){
        playerSiteAd = playerSiteAdService.findSiteAdByUserId(userId);
        if(playerSiteAd == null){
            playerSiteAd = new PlayerSiteAd();
            playerSiteAd.setUserId(userId);
        }
        return SUCCESS;
    }


    public String update(){
        try {
            PlayerSiteAd siteAd = playerSiteAdService.findSiteAdByUserId(playerSiteAd.getUserId());
            boolean isUpdate = siteAd == null ? false : true;
            if (null != bannerImage) {
                String imageUrl = uploadService.put(bannerImage, remotePath, bannerImageFileName);
                if (StringUtil.isNotEmpty(imageUrl)) {
                    playerSiteAd.setBannerImageUrl(imageUrl);
                }
            }
            if(null != viewImage){
                String viewImageUrl = uploadService.put(viewImage,remotePath,viewImageFileName);
                if(StringUtil.isNotEmpty(viewImageUrl)){
                    playerSiteAd.setViewImageUrl(viewImageUrl);
                }
            }
            if (isUpdate) {
                playerSiteAd.setUpdateTime(new Date());
                playerSiteAdService.update(playerSiteAd);
            } else {
                playerSiteAd.setCreateTime(new Date());
                playerSiteAdService.add(playerSiteAd);
            }
            if(JS_STR == null){
                String path = this.getClass().getResource("/../../").getPath();
                String filePath = path + "public/js/ad/zed_load.js";
                File file = new File(filePath);
                if(file.exists()) {
                    JS_STR = FileUtils.readFileToString(file,"UTF-8");
                }
            }
            String jsStr = JS_STR.replace("{appId}",playerSiteAd.getUserId());
            setSuccessDispatch(jsStr,request.getContextPath()+File.separator+"playeruser"+File.separator+GConstantRedirect.GS_PLAYER_USER_LIST_ACTION);
            return GConstantRedirect.GS_OK;
        }catch (Exception ex) {
            log.error("PlayerSiteAdAction update is error :"+ex.getMessage(),ex);
            if (ex instanceof AppValidationException) {
                AppValidationException e = (AppValidationException) ex;
                setErrorDispatch(e.gs_Code, e.gs_PrevLink);
            } else {
                setErrorDispatch(ExceptionConstants.SYSTEMERROR, "");
            }
            return ERROR;
        }
    }

    @Override
    public String doExecute() throws Exception {
        return null;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public PlayerSiteAd getPlayerSiteAd() {
        return playerSiteAd;
    }

    public void setPlayerSiteAd(PlayerSiteAd playerSiteAd) {
        this.playerSiteAd = playerSiteAd;
    }


    public File getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(File bannerImage) {
        this.bannerImage = bannerImage;
    }

    public File getViewImage() {
        return viewImage;
    }

    public void setViewImage(File viewImage) {
        this.viewImage = viewImage;
    }

    public String getBannerImageFileName() {
        return bannerImageFileName;
    }

    public void setBannerImageFileName(String bannerImageFileName) {
        this.bannerImageFileName = bannerImageFileName;
    }

    public String getViewImageFileName() {
        return viewImageFileName;
    }

    public void setViewImageFileName(String viewImageFileName) {
        this.viewImageFileName = viewImageFileName;
    }
}
