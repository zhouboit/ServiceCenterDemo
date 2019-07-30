package com.hirain.dt.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.SSLContext;
import javax.swing.event.ListSelectionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.LinkedList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

public class HttpClientDemo {
	
	@Test
	public void get() {
		CloseableHttpClient httpclient = HttpClients.custom().build();
        try {
        	//测试路径，实际使用请根据环境确认
        	String url = "";//http://api.hirain.com
            HttpGet httpGet = new HttpGet(url);
            //headers
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
//            httpGet.addHeader("token", "");
            CloseableHttpResponse response = httpclient.execute(httpGet);
            //response
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8"); 
            System.out.println(jsonStr);
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
	@Test
	public void post() {
		CloseableHttpClient httpclient = HttpClients.custom().build();
        try {
        	//测试路径，实际使用请根据环境确认
        	String url = "";//http://api.hirain.com
            HttpPost httpPost = new HttpPost(url);
            //parameter
//            JSONObject jsonObject = JSONObject.parseObject("{'pageSize':10,'pageNum':1}");
//            StringEntity stringEntity = new StringEntity(jsonObject.toString());
//            httpPost.setEntity(stringEntity);
            //headers
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            CloseableHttpResponse response = httpclient.execute(httpPost);
            //response
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8"); 
            System.out.println(jsonStr); 
        }catch(Exception e)
        {
        	e.printStackTrace();
        }finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}
