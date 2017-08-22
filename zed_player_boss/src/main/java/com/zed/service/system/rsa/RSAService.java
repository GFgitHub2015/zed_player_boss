package com.zed.service.system.rsa;

import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;

import com.zed.exception.AppValidationException;

public interface RSAService {
		
	public RSAPublicKey getPublicKey(HttpServletRequest request) throws AppValidationException;
	
	public String decryptionByKeyPair(HttpServletRequest request,String encryptStr) throws AppValidationException;
	
}
