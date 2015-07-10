package cn.bblink.bbdoctor.domain.po;

import cn.bblink.bbdoctor.domain.core.AbstractPo;

public class Doctor extends AbstractPo{
	
	private String uniqueCode;//仁心号
	private String avatar;//头像图片
	private String realName;//医生真实名
	private String hosName;//医院名
	private String hosRoom;//医院科室
	private String introduce;//自我介绍
	private String mobile;//手机
	private String title;//职称
	private String qrCode;//二维码图片
	private String isFinish;//是否已完善资料 

	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getHosName() {
		return hosName;
	}
	public void setHosName(String hosName) {
		this.hosName = hosName;
	}
	public String getHosRoom() {
		return hosRoom;
	}
	public void setHosRoom(String hosRoom) {
		this.hosRoom = hosRoom;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getQrCode() {
		return qrCode;
	}
	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}
	public String getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
}
