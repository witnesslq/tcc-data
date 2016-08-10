package com.framework.common.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @author: 罗尧
 * @since 15/1/27 10:41
 * Email:johnny_lx@yahoo.com
 */
public class HttpClientUtil {


    /**
     * http get 请求
     *
     * @param url
     * @return
     */
    public static String sendGet(String url) throws Exception{

        String responseBody = "";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);        //
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Integer.valueOf(5000))
                .setConnectTimeout(Integer.valueOf(2000)).build();  //设置请求和传输超时时间
        httpGet.setConfig(requestConfig);
        //发送请求1
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        responseBody = httpclient.execute(httpGet, responseHandler);

        //请求方式2
//      CloseableHttpResponse httpResponse = httpclient.execute(httpGet);
//      HttpEntity entity = httpResponse.getEntity();
//     	responseBody = EntityUtils.toString(entity,"UTF-8");
        return responseBody;
        
    }

    /**
     * http post 请求
     *
     * @param url
     * @param map     key value  参数名称  参数值
     * @param charset
     * @return
     */
    public static String sendPost(String url, Map<String, String> map, String charset) throws Exception{
        String responseBody = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);        //
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Integer.valueOf(5000))
                .setConnectTimeout(Integer.valueOf(2000)).build();  //设置请求和传输超时时间
        httpPost.setConfig(requestConfig);
        
        if (null != map) {
        //解析参数
        List<NameValuePair> nameValues = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
              NameValuePair nvp = new BasicNameValuePair(entry.getKey(), entry.getValue());
              nameValues.add(nvp);
        }
        //post请求
        //设置参数和编码
        if (StringUtil.isNotNull(charset)) {
              httpPost.setEntity(new UrlEncodedFormEntity(nameValues, charset));
        } else {
              httpPost.setEntity(new UrlEncodedFormEntity(nameValues, "UTF-8"));
        }
        //发送请求1
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        responseBody = httpClient.execute(httpPost, responseHandler);

        //发送请求方式2
//      CloseableHttpResponse httpresponse= httpClient.execute(httpPost);
//      HttpEntity entity = httpresponse.getEntity();
//      responseBody = EntityUtils.toString(entity,"UTF-8");
        }
        return responseBody;
    }
    
}
