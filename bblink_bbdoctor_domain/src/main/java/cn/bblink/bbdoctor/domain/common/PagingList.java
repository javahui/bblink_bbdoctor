package cn.bblink.bbdoctor.domain.common;

import java.util.ArrayList;
import java.util.List;

import cn.bblink.bbdoctor.domain.core.AbstractBean;
import cn.bblink.bbdoctor.util.PagingUtil;

/**
 * @description 分页VO
 */
public class PagingList<T> extends AbstractBean {

	public static final int DEFAULT_PAGESIZE = 15;
	private PagingUtil pagingUtil;
	private int curPage;
	private int pagesize = DEFAULT_PAGESIZE;
	private int totalRecordsCount;
	
	/**
	 * 最大页码
	 */
	private int maxPage;
	
	private List<T> records = new ArrayList();
	
	
	public PagingList() {}
	
	public PagingList(int pageNo,int pagesize,int totalRecordsCount) {
		if( 0 != pagesize ) this.pagesize = pagesize;
		this.totalRecordsCount = totalRecordsCount;

		calculateMaxPage();
		
		// 设定当前页，预防请求的页大于最大页
		this.curPage = ( (maxPage == 0) ? 0 : ((maxPage < pageNo ) ? maxPage : pageNo));

	}
	
	public void setRecords(List<T> recordsCurPage){
		this.records = recordsCurPage;
	}
	
	public List<T> getRecords(){
		return records;
	}
	
	public int getPreviousPage(){
		int previous = 0;
		if(curPage > 0){
			previous = curPage > 1 ? (curPage - 1) : 1;
		}
		return previous;
	}
	
	public int getNextPage(){
		int next = 0;
		if(curPage > 0){
			next = curPage < pagingUtil.getMaxPage() ? (curPage + 1) : pagingUtil.getMaxPage();
		}
		return next;
	}
	
	public int getCurPage(){
		return curPage;
	}
	
	public int getMaxPage(){
		return this.maxPage;
	}
	
	/**
	 * @return 页大小
	 */
	public int getPagesize(){
		return this.pagesize;
	}
	
	/**
	 * @return 全部记录数
	 */
	public int getTotalRecordsCount(){
		return this.totalRecordsCount;
	}
	
	/**
	 * @return 当前页获得的记录数
	 */
	public int getRecordsCountCurPage(){
		return null != records ? records.size() : 0;
	}

	/**
	 * @return 当前页起始记录数
	 */
	public int getCurPageStartRowNum(){
		
		int startRowNum = 0;
		if(curPage > 0 && curPage <= maxPage){
			startRowNum = pagesize * (curPage - 1);
		}
		
		return startRowNum;
	}
	
	private void calculateMaxPage(){
		maxPage = totalRecordsCount / pagesize;
		if(totalRecordsCount > 0 && totalRecordsCount % pagesize > 0){
			++maxPage;
		}
	}
	
	public boolean isEnd(){
		return (curPage == maxPage) ? true : false;
	}
}
