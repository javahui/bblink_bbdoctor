package cn.bblink.bbdoctor.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import cn.bblink.bbdoctor.util.domain.XlsExportBean;

/**
 * 导出xls文件
 * <pre>
 * XlsExportBean bean = new XlsExportBean();
 * bean.setFileName("xxx统计报表")
 * bean.setDataList(list集合);
 * bean.buildColumn("xx名", "name").buildColumn("主键", "id");
 * ExportXlsUtils.exportXls(bean);
 * </pre>
 * @author donghui
 *
 */
public class ExportXlsUtils {

	private static final Logger log = LoggerFactory.getLogger(ExportXlsUtils.class);
	
	private static final int MAX_SHEET_RECORD_NUM = 50000;
	
	/**
	 * 导出xls
	 * @param dataList 数据集只能是List<Map>形式
	 * @param fileName 文件名
	 * @param titles 标题
	 */
	public static ResponseEntity<byte[]> exportXls(XlsExportBean xlsImportBean) {
		List dataList = xlsImportBean.getDataList();
		Map colMap = xlsImportBean.getColumnMap();
		String fileName = xlsImportBean.getFileName();
				
		ExportXlsUtils.uniqDataList(xlsImportBean);
		
		int dataListSize = dataList.size();
		HSSFWorkbook workBook = new HSSFWorkbook();   // 创建Excel文档 
		for (int k = 0; k <= dataListSize / MAX_SHEET_RECORD_NUM; k ++) {
			int fromIndex = k * MAX_SHEET_RECORD_NUM;
			int toIndex = fromIndex + MAX_SHEET_RECORD_NUM;
			if (toIndex > dataListSize) {
				toIndex =  dataListSize;
			}
			List subList = dataList.subList(fromIndex, toIndex);
			// 新建一个工作页
			HSSFSheet sheet = workBook.createSheet();
			//标题
			if (MapUtils.isNotEmpty(colMap)) {
				HSSFRow titleRow = sheet.createRow(0); // 下标为0的行开始  
				HSSFCell[] firstColCell = new HSSFCell[colMap.size()];
				int index = 0;
				for (Object titleName : colMap.keySet()) {
					firstColCell[index] = titleRow.createCell(index);
					firstColCell[index].setCellValue(new HSSFRichTextString(titleName.toString()));
					index ++;
				}
			}
			for (int i = 0; i < subList.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);  // 创建一行 
				Object record = subList.get(i);
				int index = 0;
				for (Object propertyName : colMap.values()) {
					Object value = ExportXlsUtils.getPropertyValue(record, propertyName.toString());
					HSSFCell cell = row.createCell(index);
					cell.setCellValue(value.toString());
					index ++;
				}
			}
		}	
		ByteArrayOutputStream out = new ByteArrayOutputStream();  
		try { workBook.write(out); }
		catch (IOException e) {
			log.error(null, e);
		}
		finally{
			try {workBook.close();} 
			catch (IOException e) {
				log.error("workBook close error", e);
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		try { fileName = new String((fileName+".xls").getBytes("UTF-8"), "ISO-8859-1");} catch (UnsupportedEncodingException e) {}
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.OK);
	}
	
	/**
	 * 做记录去重操作
	 */
	private static void uniqDataList(XlsExportBean xlsImportBean){
		List dataList = xlsImportBean.getDataList();	
		List<String> uniqFieldNameList = xlsImportBean.getUniqFieldNameList();
		if (CollectionUtils.isEmpty(uniqFieldNameList)) {
			return;
		}
		
		//保存要去重的行索引号
		List<Integer> UniqRecordIndex = new ArrayList<Integer>();
		
		for (int i = 0; i < dataList.size()-1; i++) {
			List<Boolean> boolList = new ArrayList<Boolean>();
			int nextIndex = i + 1;
			Object currentRecord = dataList.get(i);
			Object nextRecord = dataList.get(nextIndex);
			for (String uniqKey : uniqFieldNameList) {
				Object currentVal = ExportXlsUtils.getPropertyValue(currentRecord, uniqKey);
				Object nextVal = ExportXlsUtils.getPropertyValue(nextRecord, uniqKey);
				boolean b = (currentVal != null && currentVal.equals(nextVal));
				boolList.add(b);
			}
			
			//如果没有false的话,做一个标识
			Boolean[] array = boolList.toArray(new Boolean[0]);
			if (ExportXlsUtils.and(array)) {
				UniqRecordIndex.add(nextIndex);	
			}
		}
		
		for (Integer index : UniqRecordIndex) {
			Object record = dataList.get(index);
			for (String uniqKey : uniqFieldNameList) {
				try {
					ExportXlsUtils.setPropertyValue(record, uniqKey, "");
				} catch (Exception e) {
					log.warn(e.getMessage());
				}
			}
		}
		
	}
	
	private static Object getPropertyValue(Object bean, String propertyName){
		Object value = new Object();
		try {
			if (bean instanceof Map) {
				Map map = (Map)bean;
				value = MapUtils.getObject(map, propertyName, "");
			} else {
				value = PropertyUtils.getSimpleProperty(bean, propertyName);
			}
		} catch (Exception e) {
			log.warn(null, e);
			return "";
		}
		return value;
	}
	
	private static void setPropertyValue(Object bean, String propertyName, Object propertyValue){
		try {
			if (bean instanceof Map) {
				Map map = (Map)bean;
				map.put(propertyName, propertyValue);
			} else {
				PropertyUtils.setSimpleProperty(bean, propertyName, propertyValue);
			}
		} catch (Exception e) {
			log.warn(null, e);
		}
	}
	
	private static boolean and(Boolean[] array){
		for (Boolean b : array) {
			if (!b) {
				return false;
			}
		}
		return true;
	}
	
}
