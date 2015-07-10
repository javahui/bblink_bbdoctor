package cn.bblink.bbdoctor.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liucy
 * http远程访问
 */
public class HttpURLUtil {
	
	protected static final Logger log = LoggerFactory.getLogger(HttpURLUtil.class);
	
	private static final String SERVLET_POST = "POST";  
	
	//读取超时 默认10秒
	private static final int READ_TIMEOUT = 100000;
	private static final int CONNECT_TIMEOUT = 100000;
	
	/**
	 * http远程访问
	 * @param httpUrl HTTP请求URL 
	 * @param argument HTTP请求的参数
	 * @return HTTP响应的返回数据
	 * @throws IOException
	 */
	public static String doPost(String httpUrl, String argument) {
		//构造一个URL对象
		URL url = null;
		String returnStr = null;
		HttpURLConnection urlConn = null;
		DataOutputStream out = null;
		
		try{
			url = new URL(httpUrl);
			//使用HttpURLConnection打开连接  
		    urlConn = (HttpURLConnection) url.openConnection();    
		    //初始化配置
		    initURLConn(urlConn);
		    // 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成，  
		    // 要注意的是connection.getOutputStream会隐含的进行connect。  
		    urlConn.connect();
		    //DataOutputStream写入流
		    out = new DataOutputStream(urlConn.getOutputStream());
		    //要上传的参数 
		    if(argument != null)
		    	out.write(argument.getBytes("UTF-8"));
		    //刷新、关闭 
		    out.flush();
		    out.close();
		    
		    //数据接收,获取数据
		    returnStr = BufStreamUtil.readStrStream(urlConn.getInputStream());
		}
		catch(Exception e){
			log.error("HttpURLUtil error", e);
		}
		finally{
			IOUtils.closeQuietly(out);
			if (urlConn != null){
				urlConn.disconnect();
			}
		}
		
		return returnStr;
	}
	
	/**
	 * 初始化配置
	 * @param urlConn
	 * @throws IOException
	 */
	private static void initURLConn(HttpURLConnection urlConn) throws IOException{
		//读取超时
	    urlConn.setReadTimeout(READ_TIMEOUT);
	    //连接超时
	    urlConn.setConnectTimeout(CONNECT_TIMEOUT);
	    //因为这个是post请求,设立需要设置为true  
	    urlConn.setDoOutput(true);  
	    urlConn.setDoInput(true);  
	    //设置以POST方式  
	    urlConn.setRequestMethod(SERVLET_POST);
	    // Post 请求不能使用缓存  
	    urlConn.setUseCaches(false);  
	    urlConn.setInstanceFollowRedirects(true);  
	    // 配置本次连接的Content-type，配置为application/x-www-form-urlencoded的  
	    urlConn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	}
}
