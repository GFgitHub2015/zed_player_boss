package com.zed.domain.player.downloadhis;

import java.sql.Timestamp;

public class PlayerUserDownloadHis implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	//使用状态
	public static enum Status{
		DELETE(-1), 					//删除资源
		STATUS_WAIT_DOWNLOADING(0), 	//资源待下载
		STATUS_DOWNLOADING(2), 			//资源下载中
		STATUS_DOWNLOAD_PAUSE(3), 		//资源下载暂停
		STATUS_DOWNLOAD_FAILD(4), 		//资源下载失败
		STATUS_DOWNLOAD_SUCCESS(7); 	//资源下载完成
		private int status;
		Status(int status){
			this.status = status;
		}
		public int getStatus() {
			
			return status;
		}
	}
	
	public static enum Type{
		MAGNET("MAGNET"), 					//磁力链
		TORRENT("TORRENT"), 				//种子链
		TORRENT_FILE("TORRENT_FILE"), 		//种子文件
		UPLOAD_FILE("UPLOAD_FILE"); 		//上传文件
		private String type;
		Type(String type){
			this.type = type;
		}
		public String getType() {
			
			return type;
		}
	}
	
	private String userId;
	private String hisId;
	private Integer status;					//-1：删除资源 0：资源待下载 2：资源下载中 3：资源下载暂停  4：资源下载失败 7：资源下载完成
	private Timestamp createTime;
	private String downloadUrl;
	private String keyword;
	private String type;
	private String fileName;
	private String imgUrl;
	private String star;
	private String director;
	private String name;
	private String score;
	private String year;
	private String downloadUrlMd5;
	private String torrentFiilePath;
	private String keywordId;
	private String pageUrl;
	private String serverName;
	private String btHashCode;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getHisId() {
		return hisId;
	}
	public void setHisId(String hisId) {
		this.hisId = hisId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getStar() {
		return star;
	}
	public void setStar(String star) {
		this.star = star;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getDownloadUrlMd5() {
		return downloadUrlMd5;
	}
	public void setDownloadUrlMd5(String downloadUrlMd5) {
		this.downloadUrlMd5 = downloadUrlMd5;
	}
	public String getTorrentFiilePath() {
		return torrentFiilePath;
	}
	public void setTorrentFiilePath(String torrentFiilePath) {
		this.torrentFiilePath = torrentFiilePath;
	}
	public String getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(String keywordId) {
		this.keywordId = keywordId;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getBtHashCode() {
		return btHashCode;
	}
	public void setBtHashCode(String btHashCode) {
		this.btHashCode = btHashCode;
	}

}
