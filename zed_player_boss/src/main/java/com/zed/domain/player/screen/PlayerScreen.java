package com.zed.domain.player.screen;

import com.zed.util.CommUtil;

import java.util.Date;

/**
 * Created by yuw on 2016/12/15.
 */
public class PlayerScreen{
    private String id;
    private String imageUrl;
    private String link;
    private Integer linkType;
    private Date startTime;
    private Date endTime;
    private Integer status;
    private Date createTime;
    private Date updateTime;

    public String getId() {
        if(null == id){
            id = CommUtil.random();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        if(null == createTime){
            createTime = new Date();
        }
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        if(null == updateTime){
            updateTime = new Date();
        }
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }
}
