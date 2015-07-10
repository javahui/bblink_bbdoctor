package cn.bblink.bbdoctor.domain.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.bblink.bbdoctor.domain.core.AbstractBean;

public class XlsExportBean extends AbstractBean {
	
	private List dataList = new ArrayList();//数据记录集
	private String fileName = "demo";//下载文件名
	private List<String> uniqFieldNameList = new ArrayList<>();//去重的属性名	
	private LinkedHashMap<String, String> columnMap = new LinkedHashMap<String, String>();//列信息 (key:中文标题 = value:dataList记录集属性名)
	
	public XlsExportBean(){}
	
	public XlsExportBean(List dataList){
		this.dataList = dataList;
	}
	
	/**
	 * <p>设置列字段和第一行中文标题名</p>
	 * <pre>
	 * 	buildColumn("入库编号", "stockInId");
	 * </pre>
	 * @param titleName 中文标题名
	 * @param propertyName 数据对象属性名
	 */
	public XlsExportBean buildColumn(String titleName, String propertyName){
		columnMap.put(titleName, propertyName);
		return this;
	}
	
	public List getDataList() {return dataList;}
	public void setDataList(List dataList) {this.dataList = dataList;}
	
	public String getFileName() {return fileName;}
	public void setFileName(String fileName) {this.fileName = fileName;}
	
	public List<String> getUniqFieldNameList() {return uniqFieldNameList;}
	public void setUniqFieldNameList(List<String> uniqFieldNameList) {this.uniqFieldNameList = uniqFieldNameList;}
	
	public void setUniqFieldNameList(String... uniqFieldNameList) {this.uniqFieldNameList = Arrays.asList(uniqFieldNameList);}
	
	public Map getColumnMap() {return columnMap;}
	
	
}