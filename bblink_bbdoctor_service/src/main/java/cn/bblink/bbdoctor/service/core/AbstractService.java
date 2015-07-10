package cn.bblink.bbdoctor.service.core;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bblink.bbdoctor.dao.core.BbDoctorDao;

public class AbstractService{

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource protected BbDoctorDao bbDoctorDao;
}
