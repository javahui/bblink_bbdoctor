package cn.bblink.bbdoctor.api.action;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.bblink.bbdoctor.api.action.core.AbstractAction;
import cn.bblink.bbdoctor.domain.common.AjaxResult;
import cn.bblink.bbdoctor.domain.po.Doctor;

@RestController
@RequestMapping(value = "doctor", produces = "text/plain;charset=UTF-8")
public class DoctorAction extends AbstractAction {
	
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		Map paramMap = super.getParamMap();
		List list = super.bbDoctorDao.pagingApi("doctor.list", paramMap);
		return AjaxResult.newInstanceSuccessResult(list).toJson();
	}
	
	/**
	 * 获取一个资源
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public String get(@PathVariable Long id) {
		Doctor record = super.bbDoctorDao.selectOne("doctor.byId", id);
		return AjaxResult.newInstanceSuccessResult(record).toJson();
	}
	
	/**
	 * 保存一个资源
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String save(Doctor doctor) {
		super.bbDoctorDao.insert("doctor.insert", doctor);
		return AjaxResult.newInstanceSuccessResult(doctor).toJson();
	}
	
	/**
	 * 修改资源
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public String update(@PathVariable Long id, Doctor doctor) {
		doctor.setId(id);
		super.bbDoctorDao.update("doctor.update", doctor);
		return AjaxResult.newInstanceSuccessResult(doctor).toJson();
	}
	
	/**
	 * 删除资源
	 */
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		bbDoctorDao.delete("doctor.delete", id);
	}
}