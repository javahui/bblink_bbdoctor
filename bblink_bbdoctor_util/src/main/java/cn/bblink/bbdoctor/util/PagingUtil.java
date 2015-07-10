package cn.bblink.bbdoctor.util;


/**
 * @description 分页工具
 * @author ShiJie Chi
 * @created 2010-4-9 下午22:17:00	
 */
public class PagingUtil{

	public final int DEFAULT_PAGESIZE = 20;
	
	private int pagesize;
	
	private int totalRecordsCount;
	
	/**
	 * 最大页码
	 */
	private int maxPage;
	
	public PagingUtil(int pagesize,int recordsCount){
		this.pagesize = pagesize == 0 ? DEFAULT_PAGESIZE : pagesize;	
		this.totalRecordsCount = recordsCount;
		
		calculateMaxPage();
	}
	
	private void calculateMaxPage(){
		maxPage = totalRecordsCount / pagesize;
		if(totalRecordsCount > 0 && totalRecordsCount % pagesize > 0){
			++maxPage;
		}
	}
	
	public boolean overMaxPage(int curPage){
		return curPage > maxPage;
	}
	
	public int getCurPageStartRowNum(int curPage){
		int startRowNum = 0;
		if(curPage > 0 && curPage <= maxPage){
			startRowNum = pagesize * (curPage - 1);
		}
		return startRowNum;
	}
	public int getMaxPage(){
		return maxPage;
	}
	
	public int getPagesize(){
		return pagesize;
	}
	
	public int getTotalRecordsCount(){
		return totalRecordsCount;
	}
	
}
