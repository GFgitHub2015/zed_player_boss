package com.zed.util;

public class Thumbnail {
	private String image;		//原图
	private String thumbnail;	//缩略图
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	@Override
	public String toString() {
		return "Thumbnail: [image=" + image + ", thumbnail=" + thumbnail + "]";
	}
	
}
