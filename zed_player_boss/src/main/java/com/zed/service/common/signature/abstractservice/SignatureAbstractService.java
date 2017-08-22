package com.zed.service.common.signature.abstractservice;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.zed.service.common.aws.abstractservice.AWSAbstractService;
import com.zed.service.common.signature.SignatureService;
import com.zed.service.player.cdn.PlayerCdnService;

public abstract class SignatureAbstractService extends AWSAbstractService implements SignatureService{
	@Resource(name="playerCdnService")
	protected PlayerCdnService playerCdnService;
	
	protected abstract GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String originUrl);
	protected abstract GeneratePresignedUrlRequest getGeneratePresignedPicUrlRequest(String originUrl);
	protected abstract GeneratePresignedUrlRequest getGeneratePresignedPlayUrlRequest(String originUrl);
	protected abstract String getVideoAbsolutePath();
	protected abstract String getPicAbsolutePath();
	protected abstract String getPlayAbsolutePath();
	protected abstract String getCdnHostName() ;
	
	protected String signatureUrl(String originUrl){
		return signatureUrl(6, originUrl);
	}
	
	protected String signaturePicUrl(String originUrl){
		return signaturePicUrl(6, originUrl);
	}
	
	protected String signaturePlayUrl(String originUrl){
		return signaturePlayUrl(6, originUrl);
	}
	
	protected String signatureUrl(int days,String originUrl){
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, days);
        Date expirationDate = cal.getTime();
		GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(originUrl);
		generatePresignedUrlRequest.setExpiration(expirationDate);
		URL url = getConnection().generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}
	
	protected String signaturePicUrl(int days,String originUrl){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date expirationDate = cal.getTime();
		GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedPicUrlRequest(originUrl);
		generatePresignedUrlRequest.setExpiration(expirationDate);
		URL url = getConnection().generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}
	
	protected String signaturePlayUrl(int days,String originUrl){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		Date expirationDate = cal.getTime();
		GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedPlayUrlRequest(originUrl);
		generatePresignedUrlRequest.setExpiration(expirationDate);
		URL url = getConnection().generatePresignedUrl(generatePresignedUrlRequest);
		return url.toString();
	}
	
}
