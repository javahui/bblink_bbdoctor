package cn.bblink.bbdoctor.manage.action.core;

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
import cn.bblink.common.exception.BusinessException;
import cn.bblink.common.utils.web.HttpServletLocalThread;
import cn.bblink.common.utils.web.ServletUtil;
import cn.bblink.common.vo.Constant;
import cn.bblink.sys.sys.po.UserInfo;

public class AbstractAction {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource protected BbDoctorDao bbDoctorDao;
	
	@Resource protected HttpServletRequest request;
	@Resource protected HttpServletResponse response;
	@Resource protected HttpSession session;

	/**
	 * 返回默认的列表路径
	 */
	public final String INDEX_PATH = "redirect:./list.do";

	/**
	 * 获取当前登录用户对象
	 * @return
	 * @throws BusinessException
	 */
	public UserInfo getCurrentUser() throws BusinessException{
		HttpServletRequest request = HttpServletLocalThread.getRequest();
		HttpServletResponse response = HttpServletLocalThread.getResponse();
		UserInfo userInfo = new UserInfo();
		userInfo = (UserInfo)ServletUtil.getSession(request, response, Constant.SESSION_BACK_USER);
		if (userInfo == null){
			userInfo = new UserInfo();
		}
		return userInfo;
	}
	
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