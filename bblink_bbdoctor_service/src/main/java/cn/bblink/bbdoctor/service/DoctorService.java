package cn.bblink.bbdoctor.service;

import org.springframework.stereotype.Component;

import cn.bblink.bbdoctor.service.core.AbstractService;

@Component
public class DoctorService extends AbstractService{

	public void test(){
		super.bbDoctorDao.update("xxx.xxx");
		super.bbDoctorDao.update("yyy.yyy");
	}
}
