package cn.bblink.bbdoctor.util;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 属性文件工具类
 * @author donghui
 */
public class ResourceBundleUtils {

	public static Map getResourceMap(String resourceBundleFileName){
		ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleFileName);
		Map map = new HashMap();
		for (String key : resourceBundle.keySet()){
			map.put(key, resourceBundle.getString(key));
		}
		return map;
	}
	
	public static String getResourceValue(String resourceBundleFileName, String key){
		ResourceBundle resourceBundle = ResourceBundle.getBundle(resourceBundleFileName);
		return resourceBundle.getString(key);
	}
	
}
