package com.zed.service.system.rsa.impl;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.zed.exception.AppValidationException;
import com.zed.listener.Log;
import com.zed.listener.RSAKeyPairGenerate;
import com.zed.listener.SystemConfig;
import com.zed.service.system.rsa.RSAService;
import com.zed.system.ExceptionConstants;
import com.zed.system.GConstant;
import com.zed.system.GConstantRedirect;
import com.zed.util.ByteArrayConvert;
import com.zed.util.RSAUtil;

@Service
@SuppressWarnings("unchecked")
public class RSAServiceImpl implements RSAService {
	
	@Override
	public RSAPublicKey getPublicKey(HttpServletRequest request) throws AppValidationException {
		try{
			KeyPair keyPair = null;
			//get public key from Hashtable(init when system load)
			int keyNum = Integer.valueOf(SystemConfig.getProperty("rsaKey.generate.num","5"));
			int keyIndex = new Random().nextInt(keyNum);
			Long nowTime = System.currentTimeMillis();
			request.getSession().setAttribute(GConstant.GS_SESSION_RSA_KEYPAIR_KEYINDEX,keyIndex);
			request.getSession().setAttribute(GConstant.GS_SESSION_RSA_KEYPAIR_KEYINDEX_TIME,nowTime);

			keyPair = (KeyPair)getKeyPair(request);
			return (RSAPublicKey)keyPair.getPublic();
		}catch(Exception ex){
			Log.getLogger(this.getClass()).error("RSAServiceImpl[getPublicKey] failed: ", ex);
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
		}
	}

	@Override
	public String decryptionByKeyPair(HttpServletRequest request,String encryptStr) throws AppValidationException{
		try{
			String decryStr = null;
			byte[] encryptBytes = ByteArrayConvert.hexStrToBytes(encryptStr);
			try{
				String decodedpwd = null;
				KeyPair keyPair = (KeyPair)getKeyPair(request);
				PrivateKey priKey = keyPair.getPrivate();
				byte[] de_pwd = RSAUtil.decrypt(priKey, encryptBytes);
				StringBuffer sb = new StringBuffer();
				sb.append(new String(de_pwd));
				decodedpwd = sb.reverse().toString();
				return decodedpwd;
			}catch(Exception e){
				return null;
			}
		}catch(Exception ex){
			Log.getLogger(this.getClass()).error("RSAServiceImpl[decryptionByKeyPair] failed: " ,ex);
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
		}
	}

	private static KeyPair getKeyPair(HttpServletRequest request) throws AppValidationException{
		try{
			KeyPair keyPair = null;
			//get the key pair from Hashtable
			Object objkeyIndex = request.getSession().getAttribute(GConstant.GS_SESSION_RSA_KEYPAIR_KEYINDEX);
			Object objkeyIndexTime = request.getSession().getAttribute(GConstant.GS_SESSION_RSA_KEYPAIR_KEYINDEX_TIME);
			ArrayList list = getKeyPairList((Long)objkeyIndexTime);
			if(objkeyIndex != null && list !=null ){
				int keyIndex = (Integer)objkeyIndex;
				keyPair = (KeyPair)list.get(keyIndex);
			}else{
				return null;
			}
			return keyPair;
		}catch(Exception ex){
			Log.getLogger(RSAService.class).error("RSAServiceImpl[getKeyPair] failed: " ,ex);
			throw new AppValidationException(ExceptionConstants.SYSTEMERROR, GConstantRedirect.GS_LOGIN_ACTION);
		}
	}

	private static ArrayList getKeyPairList(Long keyIndexTime){
		try{
			Iterator iterNew = RSAKeyPairGenerate.KeyPairListMap.entrySet().iterator();
			Map.Entry entryNew = (Map.Entry) iterNew.next();
			Long timeNew = (Long)entryNew.getKey();
			
			//get key pair list from the current map
			if(keyIndexTime>=timeNew){
				return (ArrayList)RSAKeyPairGenerate.KeyPairListMap.get(timeNew);
			}else{//get key pair list from history map
				if( RSAKeyPairGenerate.KeyPairHistoryListMap!=null && ( (timeNew - keyIndexTime)< (RSAKeyPairGenerate.KeyKeepTime*60*1000) ) ){
					Iterator iterOld = RSAKeyPairGenerate.KeyPairHistoryListMap.entrySet().iterator();
					Map.Entry entryOld = (Map.Entry) iterOld.next();
					Long timeOld = (Long)entryOld.getKey();
					return (ArrayList)RSAKeyPairGenerate.KeyPairHistoryListMap.get(timeOld);
				}else{
					return null;
				}
			}
		}catch(Exception ex){
			Log.getLogger(RSAService.class).error("RSAServiceImpl[getKeyPairList] failed: " ,ex);
			return null;
		}
		
	}
}
