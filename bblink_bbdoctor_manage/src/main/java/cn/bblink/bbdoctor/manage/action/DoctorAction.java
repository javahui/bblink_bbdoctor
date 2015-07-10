package cn.bblink.bbdoctor.manage.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.bblink.bbdoctor.manage.action.core.AbstractAction;
import cn.bblink.bbdoctor.util.ExportXlsUtils;
import cn.bblink.bbdoctor.util.domain.XlsExportBean;
import cn.bblink.common.vo.Page;
import cn.bblink.erp.po.StockIn;
import cn.bblink.erp.vo.StockInVo;
import cn.bblink.sys.sys.po.UserInfo;

/**
 * 采购入库
 * @author donghui
 */
@Controller
@RequestMapping("doctor")
public class DoctorAction extends AbstractAction{
	
	/**
	 * 列表
	 */	
	@RequestMapping("list")
	public void list(HttpServletRequest request) {
		Map<String, Object> paramMap = super.getParamMap();
		Page page = super.bbDoctorDao.paging("doctor.manageList", paramMap);
		page.buildUrl(request);
		request.setAttribute("pageParam", page);
	}
	
	/**
	 * 新增页面
	 */
	@RequestMapping("add")
	public void add(HttpServletRequest request) {}

	/**
	 * 查看页面
	 */
	@RequestMapping("view")
	public void view(@RequestParam Long id, HttpServletRequest request) {
		Map record = super.bbDoctorDao.selectOne("doctor.byId", id);
		request.setAttribute("record", record);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("save")
	public String save(StockInVo stockInVo, HttpServletRequest request) {
		UserInfo loginUser = 	super.getCurrentUser();
		stockInService.save(loginUser.getUserId(), stockInVo, STOCK_IN_TYPE);
		return super.INDEX_PATH;
	}
	
	/**
	 * 跳转到修改页面(只能是没有入库的记录可以修改)
	 */
	@RequestMapping("edit")
	public String edit(@RequestParam Long id, HttpServletRequest request) {
		Map stockIn = super.daoTemplate.selectOne("STOCK_IN.editById", id);
		String stockInStatus = MapUtils.getString(stockIn, "stockInStatus");
		if (StockIn.Status.YES.en.toString().equals(stockInStatus)) {
			log.warn("已入库不能进行修改:[{}]", stockIn);
			return super.INDEX_PATH;
		}
		List<Map> details = super.daoTemplate.selectList("STOCK_IN_DETAIL.editByid", id);
		for (Map map : details) {
			Long supplierId = MapUtils.getLong(map, "supplierId");
			Long goodsId = MapUtils.getLong(map, "goodsId");
			List goodsList = this.getGoodsBySupplierId(supplierId);
			List modelList = this.getModelByGoodsId(goodsId);
			map.put("goodsList", goodsList);
			map.put("modelList", modelList);
		}
		request.setAttribute("stockIn", stockIn);
		request.setAttribute("stockInDetail", details);
		return "erp/stockInBuy/edit";
	}
	
	/**
	 * 保存修改
	 */
	@RequestMapping("update")
	public String update(StockInVo stockInBuyVo, HttpServletRequest request) {
		UserInfo loginUser = 	super.getCurrentUser();
		stockInService.update(loginUser.getUserId(), stockInBuyVo, STOCK_IN_TYPE);
		return super.INDEX_PATH;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("delete")
	public String delete(@RequestParam Long id, HttpServletRequest request) {
		super.bbDoctorDao.delete("doctor.delete");
		return super.INDEX_PATH;
	}
	
	/**
	 * 报表
	 */
	@RequestMapping("xls")
	public ResponseEntity<byte[]> xls(@RequestParam List<Long> ids, HttpServletRequest request) {
		XlsExportBean bean = stockInService.xls(ids, STOCK_IN_TYPE);
		return ExportXlsUtils.exportXls(bean);
	}
	
	/**
	 * 入库状态记录
	 */
	@ModelAttribute("statusMap")
	public Map<String, Object> levelMap(){
		return StockIn.Status.getMap();
	}	

	
	
}