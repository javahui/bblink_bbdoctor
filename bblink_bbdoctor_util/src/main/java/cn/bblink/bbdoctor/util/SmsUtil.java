package cn.bblink.bbdoctor.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 短信发送工具类
 * @author donghui
 *
 */
public class SmsUtil {
	private static Logger log = LoggerFactory.getLogger(SmsUtil.class);
	
	private static ExecutorService exec = Executors.newCachedThreadPool();
	
	private static Map<String, String> utilConfigResource = ResourceBundleUtils.getResourceMap("util_config");
	
	/**
	 * 发验证码
	 * @param phone 手机号
	 * @return 验证码
	 */
	public static String sendActivationCode(final String phone){
		String appKey = utilConfigResource.get("appKey");
		String secretKey = utilConfigResource.get("secretKey");
		String customizeId = utilConfigResource.get("customizeId");
		String gwId = utilConfigResource.get("gwId");
		String channelId = utilConfigResource.get("channelId");
		String smsSign = utilConfigResource.get("smsSign");
		String code = RandomStringUtils.randomNumeric(4);
		String content = "您的孕蜜验证码为" + code + ",15分钟内有效.";
		long timestamp = System.currentTimeMillis();
		
		Map dataMap = new HashMap();
		dataMap.put("mobile", phone);
		dataMap.put("content", content);
		String dataJson = JSON.toJSONString(dataMap);
		String signature = SmsUtil.generateSignature(dataJson, timestamp, appKey, secretKey);
		
		final String apiUrl = utilConfigResource.get("apiUrl");
		final String params = "customizeId=" + customizeId  + "&gwid=" + gwId + "&data=" + dataJson + "&signature=" + signature + 
				"&timestamp=" + timestamp + "&channelId=" + channelId + "&smsSign=" + smsSign;
		exec.execute(new Runnable() {
			@Override
			public void run() {
				String result = HttpURLUtil.doPost(apiUrl, params);
				log.debug(result);
			}
		});
		return code;
	}
	
	private static String generateSignature(String dataJson, long timestamp, String appKey, String secretKey) {
		String md5data = DigestUtils.md5Hex(dataJson + appKey);
		return DigestUtils.md5Hex(timestamp + md5data + secretKey);
	}
	
	
}