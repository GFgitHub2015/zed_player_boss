package com.zed.domain.player.video;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import com.zed.common.util.JsonUtils;
import com.zed.domain.common.base.BaseModel;

public class PlayerVideoDestFile extends BaseModel{
	
	private static final long serialVersionUID = 1L;
	//是否是文件
	public static enum Is{
		SOURCE(0), 		//原始下载文件夹
		DEST(1); 		//转码文件
		private int status;
		Is(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}

    private String fileId;
    private String resId;
    private String fileSuffix;
    private String fileName;
    private Long fileSize;				//单位kb
    private Long duration;				//单位s(秒)
    private String bitRate;
    private String frameRate;
    private String frameHeight;
    private String frameWidth;
    private Timestamp updateTime;
    private String updater;
    private Short fileType;				//0原文件1播放文件
    private String thumbnailUrl;
    private String fileUrl;
    private String sourceFileId;
    private Short isValid;				//是否可用 0:禁用 1:启用
    private Integer dimension;
    private String description;			//备注
    private String littleImg;			//小图

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl == null ? null : thumbnailUrl.trim();
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl == null ? null : fileUrl.trim();
    }
    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId == null ? null : fileId.trim();
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
    }

    public String getFileSuffix() {
        return fileSuffix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix == null ? null : fileSuffix.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate == null ? null : bitRate.trim();
    }

    public String getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(String frameRate) {
        this.frameRate = frameRate == null ? null : frameRate.trim();
    }

    public String getFrameHeight() {
        return frameHeight;
    }

    public void setFrameHeight(String frameHeight) {
        this.frameHeight = frameHeight == null ? null : frameHeight.trim();
    }

    public String getFrameWidth() {
        return frameWidth;
    }

    public void setFrameWidth(String frameWidth) {
        this.frameWidth = frameWidth == null ? null : frameWidth.trim();
    }

   
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public Short getFileType() {
        return fileType;
    }

    public void setFileType(Short fileType) {
        this.fileType = fileType;
    }

	public String getSourceFileId() {
		return sourceFileId;
	}

	public void setSourceFileId(String sourceFileId) {
		this.sourceFileId = sourceFileId;
	}

	public Short getIsValid() {
		return isValid;
	}

	public void setIsValid(Short isValid) {
		this.isValid = isValid;
	}

	public Integer getDimension() {
		return dimension;
	}

	public void setDimension(Integer dimension) {
		this.dimension = dimension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLittleImg() {
		return littleImg;
	}

	public void setLittleImg(String littleImg) {
		this.littleImg = littleImg;
	}

	public Map<String, Object> forMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fileName", this.getFileName());
		map.put("thumbnailUrl", this.getThumbnailUrl());
		map.put("fileType", this.getFileType());
		map.put("duration", this.getDuration());
		map.put("resId", this.getResId());
		map.put("sourceFileId", this.getSourceFileId());
		map.put("fileSuffix", this.getFileSuffix());
		map.put("fileSize", this.getFileSize());
		map.put("fileUrl", this.getFileUrl());
		map.put("frameHeight", this.getFrameHeight());
		map.put("frameWidth", this.getFrameWidth());
		map.put("dimension", this.getDimension());
		map.put("fileId", this.getFileId());
		map.put("frameRate", this.getFrameRate());
		map.put("littleImg", this.getLittleImg());
		map.put("bitRate", this.getBitRate());
		map.put("isValid", this.getIsValid());
	return map;
	}

	public static PlayerVideoDestFile getPlayerVideoDestFile(String jsonStr){
		return JsonUtils.jsonToObj(jsonStr,PlayerVideoDestFile.class);
	}
	
	public String toJson() {
		return JsonUtils.getJsonStrByMap(this.forMap());
	}
	
}