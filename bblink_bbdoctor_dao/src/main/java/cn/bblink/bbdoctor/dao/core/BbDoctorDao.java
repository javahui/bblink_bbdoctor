package cn.bblink.bbdoctor.dao.core;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.bblink.common.vo.Page;

@Component
public class BbDoctorDao{	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource private SqlSessionTemplate bbDoctorSqlSession;

	/**
	 * 分页
	 */
	public <E> Page<E> paging(String statement, Map<String, Object> parameter) {
		Long totalCount = (Long)this.selectOne(statement + "Count", parameter);
		if (totalCount == 0) {
			return new Page();
		}
		int page = MapUtils.getIntValue(parameter, "page");
		Integer pageSize = MapUtils.getInteger(parameter, "pageSize", Page.DEFAULT_PAGE_SIZE);
		Page pageVo = new Page(totalCount, pageSize, page);
		parameter.put("page", page);//第几页
		if (!parameter.containsKey("pageSize")) {
			parameter.put("pageSize", 20);
		}
		pageVo.setItems(this.selectList(statement, parameter));
		return pageVo;
	}
	
	public <E> List<E> pagingApi(String statement, Map<String, Object> paramMap) {
		Long page = MapUtils.getLong(paramMap, "index");
		if (page != null) {
			Integer pageSize = MapUtils.getInteger(paramMap, "pageSize", 5);
			paramMap.put("pageSize", pageSize);
		}
		return this.selectList(statement, paramMap);
	}
	
	public <E> List<E> selectList(String statement) {
		return bbDoctorSqlSession.selectList(statement);
	}
	
	public <E> List<E> selectList(String statement, Object parameter) {
		return bbDoctorSqlSession.selectList(statement, parameter);
	}

	public <T> T selectOne(String statement) {
		return bbDoctorSqlSession.selectOne(statement);
	}

	public <T> T selectOne(String statement, Object parameter) {
		return bbDoctorSqlSession.selectOne(statement, parameter);
	}
	
	public int insert(String statement) {
		return bbDoctorSqlSession.insert(statement);
	}
	
	public int insert(String statement, Object parameter) {
		return bbDoctorSqlSession.insert(statement, parameter);
	}

	public int update(String statement) {
		return bbDoctorSqlSession.update(statement);
	}
	
	public int update(String statement, Object parameter) {
		return bbDoctorSqlSession.update(statement, parameter);
	}

	public int delete(String statement) {
		return bbDoctorSqlSession.delete(statement);
	}
	
	public int delete(String statement, Object parameter) {
		return bbDoctorSqlSession.delete(statement, parameter);
	}
	
}	