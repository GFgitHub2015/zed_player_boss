package com.zed.service.common.signature;

import com.zed.exception.AppValidationException;
import com.zed.service.common.aws.AWSService;

public interface SignatureService extends AWSService{
	/**
	 * 下载url签名
	 * @param url
	 * @return
	 */
	public String signature(String url) throws AppValidationException;
	/**
	 * 图片url签名
	 * @param url
	 * @return
	 */
	public String signaturePic(String url) throws AppValidationException;
	/**
	 * 播放url批量签名
	 * @param url
	 * @return
	 */
	public String signaturePlay(String url) throws AppValidationException;
}
