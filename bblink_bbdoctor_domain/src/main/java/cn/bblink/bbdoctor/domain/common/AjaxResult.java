package cn.bblink.bbdoctor.domain.common;

import cn.bblink.bbdoctor.domain.core.AbstractBean;

public class AjaxResult extends AbstractBean{
	
	private boolean success;
	private String message = "";
	private Object data = new Object();
	
	public AjaxResult(){}
	
	public static AjaxResult newInstanceFailResult(String message){
		AjaxResult vo = new AjaxResult();
		vo.setSuccess(false);
		vo.setMessage(message);
		return vo;
	}
	
	public static AjaxResult newInstanceSuccessResult(Object data){
		AjaxResult vo = new AjaxResult();
		vo.setSuccess(true);
		vo.setData(data);
		return vo;
	}
	
	public boolean isSuccess() {return success;}
	public void setSuccess(boolean success) {this.success = success;}
	
	public String getMessage() {return message;}
	public void setMessage(String message) {this.message = message;}
	
	public Object getData() {return data;}
	public void setData(Object data) {this.data = data;}
}
