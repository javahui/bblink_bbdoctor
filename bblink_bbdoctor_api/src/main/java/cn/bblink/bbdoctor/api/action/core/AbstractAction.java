package cn.bblink.bbdoctor.api.action.core;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bblink.bbdoctor.dao.core.BbDoctorDao;

public class AbstractAction {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	protected final String LIST_PATH = "redirect:list.do";
	
	@Resource protected BbDoctorDao bbDoctorDao;
	
	@Resource protected HttpServletRequest request;
	@Resource protected HttpServletResponse response;
	@Resource protected HttpSession session;
	
	/**
	 * 得到请求的Map形式
	 */
	protected Map getParamMap() {
		Map paramMap = new HashMap();
		Map requestParameterMap = request.getParameterMap();
		for (Object key : requestParameterMap.keySet()) {
			String[] array = (String[])requestParameterMap.get(key);
			String value = StringUtils.join(array, ",");
			if (StringUtils.isBlank(value)) {
				continue;
			}
			paramMap.put(key, StringUtils.trim(value));
		}
		request.setAttribute("paramMap", paramMap);
		return paramMap;
	}
	
}