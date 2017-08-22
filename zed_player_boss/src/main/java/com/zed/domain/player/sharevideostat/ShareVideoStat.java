package com.zed.domain.player.sharevideostat;

import com.zed.domain.common.base.BaseModel;

/**
 * @date : 2017年4月6日 上午11:50:59
 * @author : Iris.Xiao
 * @version : 1.0
 * @description : 影片分享统计
*/
public class ShareVideoStat  extends BaseModel{
	private static final long serialVersionUID = -3074494269669962338L;
	private String shareId ;//主键
	private String fileId ;//文件id
	private String channel ;//渠道
	private String packageName ;//包名
	private Long shareCount ;//分享次数
	private Long sharePv ;//浏览量
	private Long shareUv ;//独立访客
	private Long downloadCount ;//下载次数
	private String fileName ;//文件名
	private String appname;
	private String statDate;
	
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public String getShareId() {
		return shareId;
	}
	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Long getShareCount() {
		return shareCount;
	}
	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}
	public Long getSharePv() {
		return sharePv;
	}
	public void setSharePv(Long sharePv) {
		this.sharePv = sharePv;
	}
	public Long getShareUv() {
		return shareUv;
	}
	public void setShareUv(Long shareUv) {
		this.shareUv = shareUv;
	}
	public Long getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
