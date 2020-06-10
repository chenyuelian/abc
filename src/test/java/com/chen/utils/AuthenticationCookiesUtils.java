package com.chen.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class AuthenticationCookiesUtils {
	public static final Map<String,String> ENV = new HashMap<String,String>();
	//这俩个是固定的，不需要改
	public static final String RESPONSE_HEADER = "Set-Cookie";
	public static final String REQUEST_HEADER = "Cookie";
	//这个cookies名字可能不一样，需要改的地方
	public static final String COOKIE_NAME = "JSESSIONID";
	
	
	//抓取cookies存到 cookies缓存中
	public static void getCookieByResponse(HttpResponse response) {
		//从响应头里面获取指定的头字段
		Header header = response.getFirstHeader(RESPONSE_HEADER);
		//如果header不为空
		if(header != null) {
			//获取头字段的值
			String cookie = header.getValue();
			//如果头字段的值不为空
			if(StringUtils.isNotBlank(cookie)) {
				String[] values = cookie.split(";");
				for (String value : values) {
					//如果包含JSESSIONID那么放入缓存cookies中。
					if(value.contains(COOKIE_NAME)) {
						ENV.put(COOKIE_NAME,value);//JSESSIONID = JSESSIONID=03D24FCE2D72912E597BB54AD8B1719D
					}
				}
			}
		}
	}
	
	//从cookies缓存中取出cookie添加到 请求头中。
	public static void addCookieInRequest(Map<String,Object> headers) {
		String value = ENV.get(COOKIE_NAME);
		//如果cookie不为空添加到请求头中
		if(StringUtils.isNotBlank(value)) {
			headers.put(REQUEST_HEADER,value);
		}
	}
}
