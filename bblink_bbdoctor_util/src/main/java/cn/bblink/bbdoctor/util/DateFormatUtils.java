package cn.bblink.bbdoctor.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 属性文件工具类
 * @author donghui
 */
public class DateFormatUtils {
	
	/**
	 * string to date
	 * @param strDate
	 * @return
	 */
	public static Date toDate(String strDate){
		if (StringUtils.isBlank(strDate)) {
			return null;
		}
		String[] parsePatterns = {"yyyy-MM-dd", "yyyy/MM/dd", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss"};
		try {
			return DateUtils.parseDate(strDate.trim(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}
}