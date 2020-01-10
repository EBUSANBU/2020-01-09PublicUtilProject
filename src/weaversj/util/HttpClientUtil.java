package weaversj.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	private final String 	url;					//接口地址
	private final String 	requestContent;			//请求参数  K3FormId=&K3FormId=&K3FormId=&K3FormId=
	private final int 		SO_TIMEOUT;				//读取超时
	private final int 		CONNECTION_TIMEOUT;		//连接超时
	private final String 	setContentType;			//类型 Content-Typ
	private final String 	charset;				//编码
	public  final int 		rspCode;				//状态码
	public  final String    body;					//响应数据
	public  final List<NameValuePair> params;		//list 数据

	
	public static class Builder{
		private final String 	url;													//接口地址
		private final String 	requestContent;											//请求参数  K3FormId=&K3FormId=&K3FormId=&K3FormId=
		private int 	SO_TIMEOUT			= 30000;									//读取超时
		private int 	CONNECTION_TIMEOUT 	= 30000;									//连接超时
		private String 	setContentType		= "application/x-www-form-urlencoded";		//类型 Content-Typ
		private String 	charset				= "UTF-8";									//编码
		private int 	rspCode				= 0;										//状态码
		private String 	body				= "";										//响应数据
		private final List<NameValuePair> params;
		private boolean flag = true;													//调用dopost 标识
		public Builder(String url,String requestContent){
			this.url 			= url;
			this.requestContent = requestContent;
			this.params = null;
		}
		public Builder(String url,List<NameValuePair> params){
			this.url 			= url;
			this.requestContent = "";
			this.params = params;
			this.flag = false;
		}

		public Builder setSO_TIMEOUT(int SO_TIMEOUT){
			this.SO_TIMEOUT = SO_TIMEOUT;
			return this;
		}
		public Builder setCONNECTION_TIMEOUT(int CONNECTION_TIMEOUT){
			this.CONNECTION_TIMEOUT = CONNECTION_TIMEOUT;
			return this;
		}
		public Builder setContentType(String setContentType){
			this.setContentType = setContentType;
			return this;
		}
		public Builder setCharset(String charset){
			this.charset = charset;
			return this;
		}
		public HttpClientUtil build() throws Exception{
			newDefaultHttpClient();
			return new HttpClientUtil(this);
		}
		
		/**
		 * 传入字符串 doPost
		 * @return
		 * @throws Exception
		 */
		public void newDefaultHttpClient() throws Exception {
			DefaultHttpClient httpclient = new DefaultHttpClient();
		    httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, this.CONNECTION_TIMEOUT);
		    //读取超时
		    httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,this.SO_TIMEOUT);
		    if(this.flag)
		    	doPost(httpclient);
		    else
		    	doPost2(httpclient);
		}
		
		private void doPost(DefaultHttpClient httpclient) throws ClientProtocolException, IOException {
			// 目标地址
			HttpPost httppost = new HttpPost(this.url);
			// 构造最简单的字符串数据
			StringEntity reqEntity = new StringEntity(this.requestContent);
			
			// 设置类型
			reqEntity.setContentType(this.setContentType+";charset="+this.charset);
			// 设置请求的数据
			httppost.setEntity(reqEntity);
			// 执行
			HttpResponse httpresponse = httpclient.execute(httppost);
			rspCode = httpresponse.getStatusLine().getStatusCode(); 
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
		}
		private void doPost2(DefaultHttpClient httpclient) throws ClientProtocolException, IOException{
			HttpPost httpPost =   new HttpPost(this.url);
	        String s = "";
            httpPost.setEntity(new UrlEncodedFormEntity(this.params,this.charset));
            httpPost.setHeader("Content-type", this.setContentType+";charset="+this.charset); 
            HttpResponse response = httpclient.execute(httpPost);
            rspCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
		}
	}
	private HttpClientUtil(Builder builder){
		url					=	builder.url;
		requestContent		=	builder.requestContent;
		SO_TIMEOUT			=	builder.SO_TIMEOUT;
		CONNECTION_TIMEOUT	=	builder.CONNECTION_TIMEOUT;
		setContentType		=	builder.setContentType;
		charset				=	builder.charset;
		rspCode				=	builder.rspCode;
		body				=	builder.body;
		params				=	builder.params;
	}
}
