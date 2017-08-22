package com.zed.domain.player.playeruser;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sumail on 2017/6/22.
 */
public class PlayerSiteAd implements Serializable {

    private static final long serialVersionUID = 3480368634357103189L;

    private String userId;
    private String bannerClickUrl;
    private String bannerImageUrl;
    private String viewClickUrl;
    private String viewImageUrl;
    private Date createTime;
    private Date updateTime;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBannerClickUrl() {
        return bannerClickUrl;
    }

    public void setBannerClickUrl(String bannerClickUrl) {
        this.bannerClickUrl = bannerClickUrl;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getViewClickUrl() {
        return viewClickUrl;
    }

    public void setViewClickUrl(String viewClickUrl) {
        this.viewClickUrl = viewClickUrl;
    }

    public String getViewImageUrl() {
        return viewImageUrl;
    }

    public void setViewImageUrl(String viewImageUrl) {
        this.viewImageUrl = viewImageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
