package com.zed.service.common.signature.impl;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.zed.domain.player.cdn.PlayerCdn;
import com.zed.exception.AppValidationException;
import com.zed.service.common.signature.abstractservice.SignatureAbstractService;

public class SignatureServiceImpl extends SignatureAbstractService{
	
	private String bucketName;
	private String videoAbsolutePath;
	private String picAbsolutePath;
	private String playAbsolutePath;
	private String picAbsoluteSubPath;
	private String cdnHostName;
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public void setVideoAbsolutePath(String videoAbsolutePath) {
		this.videoAbsolutePath = videoAbsolutePath;
	}
	
	public void setPicAbsolutePath(String picAbsolutePath) {
		this.picAbsolutePath = picAbsolutePath;
	}
	
	public void setCdnHostName(String cdnHostName) {
		this.cdnHostName = cdnHostName;
	}

	public void setPlayAbsolutePath(String playAbsolutePath) {
		this.playAbsolutePath = playAbsolutePath;
	}

	public void setPicAbsoluteSubPath(String picAbsoluteSubPath) {
		this.picAbsoluteSubPath = picAbsoluteSubPath;
	}
	
	@Override
	protected String getVideoAbsolutePath() {
		return videoAbsolutePath;
	}
	
	@Override
	protected String getPicAbsolutePath() {
		return picAbsolutePath;
	}
	
	@Override
	protected String getCdnHostName() {
		return cdnHostName;
	}
	
	@Override
	protected String getPlayAbsolutePath() {
		return playAbsolutePath;
	}
	
/*	@Override
	protected GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String originUrl) {
		return new GeneratePresignedUrlRequest(bucketName, videoAbsolutePath+originUrl);
	}
	
	@Override
	protected GeneratePresignedUrlRequest getGeneratePresignedPicUrlRequest(String originUrl) {
		originUrl = originUrl.replaceAll(cdnHostName, "");
		return new GeneratePresignedUrlRequest(bucketName, picAbsolutePath+originUrl);
	}
	
	@Override
	protected GeneratePresignedUrlRequest getGeneratePresignedPlayUrlRequest(String originUrl) {
		originUrl = originUrl.replaceAll(cdnHostName, "");
		return new GeneratePresignedUrlRequest(bucketName, playAbsolutePath+originUrl);
	}*/


	@Override
	protected GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String originUrl) {
		return new GeneratePresignedUrlRequest(bucketName, videoAbsolutePath+originUrl);
	}
	

	@Override
	protected GeneratePresignedUrlRequest getGeneratePresignedPicUrlRequest(String originUrl) {
		if (originUrl.contains(picAbsoluteSubPath)) {
			originUrl = originUrl.substring(originUrl.indexOf(picAbsoluteSubPath), originUrl.length());
		}
		GeneratePresignedUrlRequest generatePresignedUrlRequest = null;
		if (originUrl.startsWith(picAbsolutePath)) {
			generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, originUrl);
		}else{
			generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, picAbsolutePath+originUrl);
		}
		return generatePresignedUrlRequest;
	}
	
	@Override
	protected GeneratePresignedUrlRequest getGeneratePresignedPlayUrlRequest(String originUrl) {
		if (originUrl.contains(picAbsoluteSubPath)) {
			originUrl = originUrl.substring(originUrl.indexOf(picAbsoluteSubPath), originUrl.length());
		}
		GeneratePresignedUrlRequest generatePresignedUrlRequest = null;
		if (originUrl.startsWith(playAbsolutePath)) {
			generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, originUrl);
		}else{
			generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, playAbsolutePath+originUrl);
		}
		return generatePresignedUrlRequest;
	}
	
	@Override
	public String signature(String url)  throws AppValidationException{
		String signUrl = this.signatureUrl(url);
		PlayerCdn playerCdn = playerCdnService.getPlayerCdn();
		url = videoAbsolutePath+url;
		if (url.startsWith(picAbsolutePath)) {
			url = url.replaceAll(picAbsolutePath, "");
		}
		if (playerCdn != null) {
			url = playerCdn.getCdnDomain()+url;
		}else{
			url = getCdnHostName()+url;
		}
		String paramString = signUrl.substring(signUrl.indexOf("?"), signUrl.length());
		return url+paramString;
//		return signatureUrl(url);
	}

	@Override
	public String signaturePic(String picUrl) throws AppValidationException{
		String signUrl = signaturePicUrl(picUrl);
		PlayerCdn playerCdn = playerCdnService.getPlayerCdn();
		if (picUrl.contains(picAbsoluteSubPath)) {
			picUrl = picUrl.substring(picUrl.indexOf(picAbsoluteSubPath), picUrl.length());
		}
		if (picUrl.startsWith(picAbsoluteSubPath)) {
			if (playerCdn != null) {
				picUrl = playerCdn.getCdnDomain()+picUrl;
			}else{
				picUrl = cdnHostName+picUrl;
			}
		}
		return picUrl+signUrl.substring(signUrl.indexOf("?"), signUrl.length());
		
		
		
		/*String signUrl = this.signaturePicUrl(url);
		String paramString = signUrl.substring(signUrl.indexOf("?"), signUrl.length());
		return cdnHostName+paramString;*/
	}

	@Override
	public String signaturePlay(String playUrl) throws AppValidationException{
		String signUrl = signaturePlayUrl(playUrl);
		PlayerCdn playerCdn = playerCdnService.getPlayerCdn();
		if (playUrl.contains(picAbsoluteSubPath)) {
			playUrl = playUrl.substring(playUrl.indexOf(picAbsoluteSubPath), playUrl.length());
		}
		if (playUrl.startsWith(picAbsoluteSubPath)) {
			if (playerCdn != null) {
				playUrl = playerCdn.getCdnDomain()+playUrl;
			}else{
				playUrl = cdnHostName+playUrl;
			}
		}
		return playUrl+signUrl.substring(signUrl.indexOf("?"), signUrl.length());
		
		
		/*String signUrl = this.signaturePlayUrl(url);
		String paramString = signUrl.substring(signUrl.indexOf("?"), signUrl.length());
		return cdnHostName+paramString;*/
	}

}
