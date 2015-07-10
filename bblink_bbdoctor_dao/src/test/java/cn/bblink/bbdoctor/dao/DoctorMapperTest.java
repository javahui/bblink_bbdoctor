package cn.bblink.bbdoctor.dao;

import java.io.IOException;

import org.junit.Test;

import cn.bblink.common.vo.Page;


public class DoctorMapperTest extends AbstractDaoTest{
	
	
	@Test
	public void list() throws IOException {
		Page paging = super.bbDoctorDao.paging("doctor.managerList", null);
		log.debug(paging.toString());
	}
	
}