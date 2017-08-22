package com.zed.util;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.zed.listener.Log;

public class HttpClientUtil {


	private static HttpClientUtil httpClientUtil = null;
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 * 初始化
	 * 
	 * @return
	 */
	public static HttpClientUtil getInstance() {
		if (httpClientUtil == null)
			httpClientUtil = new HttpClientUtil();
		return httpClientUtil;
	}
	
	
	public static String send(String url, List<NameValuePair> parameters){
		String responseStr = null;
		try{
			//post 请求
			HttpPost httpPost = new HttpPost(url);
			//设置参数
			httpPost.setEntity(new UrlEncodedFormEntity(parameters));
			//发送请求
			HttpResponse httpResponse = httpClient.execute(httpPost);
			//获取返回值
			HttpEntity entity = httpResponse.getEntity();
			responseStr = EntityUtils.toString(entity);
            Log.getLogger(HttpClientUtil.class).info("HttpClientUtil[send] response info:"+responseStr);
            /*
            //16进制解码
            byte[] hexDecode = Hex.decodeHex(responseStr.toCharArray());
            
            //解密
            
			String aesFlag = PropertiesUtil.getProperty("aes.flag");
			if(aesFlag.equals(GConstant.GS_AES_DECRYPT_YES)){
				responseStr = new String(AESUtil.decrypt(hexDecode));
				Log.getLogger(HttpClientUtil.class).info("HttpClientTask[getParameters] decrypt response:"+responseStr);
			}else{
				responseStr = new String(hexDecode);
			}
			*/
            return responseStr;
		}catch(Exception ex){
			Log.getLogger(HttpClientUtil.class).error("HttpClientUtil[send] error:",ex);
			return null;
		}
	}  
  
}
