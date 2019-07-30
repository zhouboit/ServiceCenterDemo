package com.hirain.dt.https;

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

public class HttpsClientDemo {
	
	// 客户端证书路径
	private final static String PFX_PATHAPI = "";
	// 客户端证书密码
    private final static String PFX_PWDAPI = "";

    public static CloseableHttpClient getHttpsClient(boolean hasCrt) {
    	if(!hasCrt){
    		return HttpClients.custom().build();
    	}
    	try {
    		KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream instream = new FileInputStream(new File(PFX_PATHAPI));
            keyStore.load(instream, PFX_PWDAPI.toCharArray());
            instream.close();
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, PFX_PWDAPI.toCharArray()).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext
                    , new String[] { "TLSv1" }  // supportedProtocols 
                    , null  // supportedCipherSuites
                    , SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
	}
    
    @Test
    public void httpsGetWithCrt() {
    	//测试路径，实际使用请根据环境确认
    	String url = "";//https://api.hirain.com
    	CloseableHttpClient httpclient = getHttpsClient(true);
        try {
            HttpGet httpGet = new HttpGet(url);
            //headers
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
//            httpGet.addHeader("token", "eyJhbGciOiJIUzI1NiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAK2Ya4_UVBjHv8u8hnDuF971mpBgQkRfiTGdaWe3MpfNTIdLCAmKCKiAqIAihCwG2JiwIRIVWYhfhs6w38Jn2lnYnmk7u-5OspteTk9__Z_nes41Pk_ixuFGqxUqpjk5yNtcHmQhIQd1JEI4Qk1JZFtFDDUONOIgaRzGXDAmFcLsQGM4asLT_mgY93sHjyfBUjQdNRzCxSDsxj04C0bh9AWjYdLvwml0ZmU2hRbZFEf7S3Hvs4-H0QCGnTvRaA2iIIlc-DuRDaSaK8wIQuhAdjOMekkcdIbHg04CI040kmiYBO0g0jIiYYvpMJRNKlpSNImWqi2jtmouun-iAZOHUcfvBEswKcrOVoJB0oW3HQmz9-BsUNQL37EpqbFQCOVscT5MWsT3LUUx1T7TmGdPwTSjo_FwCvzJ_DfCT2JJdWEe4XCXceUhhAUjHsrmibsgcXY7OhN0VzpRdrUTnYo605nguBd08wGbj29vrv6Z3YcPef8Z-USjQSc7O7Tcn44_f6BEeQRLxBQvUGmiOfMYFsiXzCLapDpbJCLbiU5HzfTVhfTGzYVQMNKdztBfqUGjBpqtbIcg5riE-Exg20CLe0k06AUJWGrQKUDS7ZDje9fSb1YBMn1xcSEnGO2puBUdGzU78XB5F6w-cy1BQD8iXWZLZrD2wKSjsADJtkO-2dgAyLfPLqYvHy-ETILhyd3IyBxqY-YIiTVzuWWgdeLeyQIYn1dv8vpmeun5TtU70oODZDficYaJZXMLU8Qs7BqESdA0_EJsR0wfvJysXhjfeja-tr4QMQySwA6GdR5i0mHNbQAUcFUy7PsG3Qg-tgAnt8NNnm9MNh5M1lcn33-9WL-zYCXdD4JeNveOARlWUlLHws7UkZkJmAyionqqoN7l1yDdDgHbo15r6m-7RiSOLSh4huvYHqywZyC2-4NuAVGXaPjrzfH1JzvVsN-Lk_6gnJBrTAhnRULPg_SFLIIVdZhDzGCDUXVgTtcfjq_8XSVhqfmULvmhPKdGgzp5c3hEikEcll9r7vuKCub5pgMhXR3DJz-t7Q_81A8Wg_Oi6pZNFdEe87DrMO7OqY6r43puFemNL8F89wQOzwXd49mVOnKBC-SK-5S5yJOYI-a7yiQn1cF-lpF-WdsH1Wchd7HwQhaFR4zZnmLEQjazsTTxaXVKSF_dGv94ffPi2uT1H3tij3pQKNZCy6KZ-xiDBUlH-45m8DOhWXWSeHvjXnrt1j4IPi38FqstWbH0s5lHlIO1IyjzuTDBeXUCGV998nb1u8nGV_vAvlSfXDJ0xotVK_IsSM2WJVzEmYXMqhWL6rg4vvMo_ffO-MGjyb1v57lLU0J5GD_U6S85y1GrouqZcYtiPNc21bbn-RhBbSaoyS2rQ-L4-qP0xm85_d64sxK1HbQi6Ikq2JkSsGzMcE5HeFh4nHtg5XPFJFbVms-iYpbU59lLK4WK7D77FH92sQafI6Ng4tiHPsNGGuwe-3NBvSYbvb38e7p-980_V_fnC6KkHj-vVRAtWo7rcGg7lKYeZ45jqi9qLP7Ni43Nhz9XeWppq1XSIh1KlqPuLKRXlDA5OC-mJOr7Arka-xoKfUzNIkvX6A6N5eb9h1XN3I7BV-BdtdxTV6VUF7m5UgIRaJgtjBjS2KxeUE0VAMFpnre04ynvUrbOrJW4hpjhYlBUFiQhi7qwDh6jlCwgLmR_d5-I3WZdNFTFaGg7HGamyKawgmLOppGoiYZZtTK-fmV8v6R_Lu3RK9rpd1qPkorWOhMbyaI_CoGkBFd0oSxnWpn9K0Ko2jxmpVaFXe8O_uzww2gphphY5ZOEIKWMytzxCCyqwsrilCnPzPwUVRtKun41vbQ2eXp7cuvRHtlX-p24dbZCcyXAu6k0doV8KFak51AfysO5lhKR6hiYb2LkGxrz2KX7JIWdjezfsLUchaNOVaGihAKxtVEccodBLlJQYTHlmxUtotUmniNXJfwdIlen-MwhdZGWgptrZHsONBBQG5plFVLVtG9e3J3uFa2ulaCWbaTM7X1sWcdH-b5KdZw2SkHi25xjSsHkJPO02fLUxul8eyZ9eie99OJ_c08P3LgFyJ_C_W6_GXeiY8v93lbPgjkRmgqVJ6h3757uJ-fvPJ2_rkWbIfhkQDEjDNIoD-ChFhWkLWmIW61s8HBrO3pHW83DJBgk7-WjAgu0tY88gmIwF-GLH8Z_5ft7027ZADzfOP8fB6Rmbb4XAAA.xc8DbzDzNWMdYPl4SzkY5-9qHHVBQn4FeobVjVpIiwE");
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
    public void httpsPostWithCrt() {
    	//测试路径，实际使用请根据环境确认
    	String url = "";//https://api.hirain.com
    	CloseableHttpClient httpclient = getHttpsClient(true);
        try {
            HttpPost httpPost = new HttpPost("url");
            //参数同http post
//            JSONObject jsonObject = JSONObject.parseObject("");
//            StringEntity stringEntity = new StringEntity(jsonObject.toString());
//            httpPost.setEntity(stringEntity);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            CloseableHttpResponse response = httpclient.execute(httpPost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(jsonStr);
            response.close();	
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
    
    @Test
    public void httpsGetWithoutCrt() {
    	//测试路径，实际使用请根据环境确认
    	String url = "";//https://api.hirain.com
    	CloseableHttpClient httpclient = getHttpsClient(false);
        try {
            HttpGet httpGet = new HttpGet(url);
            //headers
            httpGet.addHeader("Content-Type", "application/json;charset=UTF-8");
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
    public void httpsPostWithoutCrt() {
    //测试路径，实际使用请根据环境确认
    	String url = "";//https://api.hirain.com
    	CloseableHttpClient httpclient = getHttpsClient(false);
        try {
            HttpPost httpPost = new HttpPost("url");
            //参数同http post
//            JSONObject jsonObject = JSONObject.parseObject("");
//            StringEntity stringEntity = new StringEntity(jsonObject.toString());
//            httpPost.setEntity(stringEntity);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
            CloseableHttpResponse response = httpclient.execute(httpPost);
            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(jsonStr);
            response.close();	
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

}
