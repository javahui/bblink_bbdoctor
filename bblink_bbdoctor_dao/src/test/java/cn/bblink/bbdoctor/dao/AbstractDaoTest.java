package cn.bblink.bbdoctor.dao;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.bblink.bbdoctor.dao.core.BbDoctorDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring-dao.xml" })
public abstract class AbstractDaoTest{
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource protected BbDoctorDao bbDoctorDao;
}
