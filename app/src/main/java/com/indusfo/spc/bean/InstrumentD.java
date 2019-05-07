package com.indusfo.spc.bean;

import java.io.Serializable;

/**
 * 品检仪器存储过程对应poji类
 *
 * @author xuz
 * @date 2019/3/8 11:05 AM
 * @param
 * @return
 */
public class InstrumentD implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6856237955613395285L;

	/**设备id*/
	private Integer lEquipment;
	/**机台号*/
	private String vcEquipment;

	// 品检仪器ID
	private Integer lInstrument;
	// 品检仪器名称
	private String vcInstrument;
	// 驱动文件
	private String vcDrive;
	// 通信类型
	private Integer lCommType;
	// 通信类型(数据字典翻译)
	private String lCommTypeName;
	// 通信参数
	private String vcComm;
	// 仪器类型
	private Integer lInsType;
	// 仪器类型(数据字典翻译)
	private String lInsTypeName;
	// vc_mac
	private String vcMac;
	// 说明
	private String vcRemark;
	// 数据状态
	private Integer lDataState;
	// 数据状态(数据字典翻译)
	private String lDataStateName;

	public Integer getlEquipment() {
		return lEquipment;
	}

	public void setlEquipment(Integer lEquipment) {
		this.lEquipment = lEquipment;
	}

	public Integer getlInstrument() {
		return lInstrument;
	}

	public void setlInstrument(Integer lInstrument) {
		this.lInstrument = lInstrument;
	}

	public String getVcInstrument() {
		return vcInstrument;
	}

	public void setVcInstrument(String vcInstrument) {
		this.vcInstrument = vcInstrument;
	}

	public String getVcDrive() {
		return vcDrive;
	}

	public void setVcDrive(String vcDrive) {
		this.vcDrive = vcDrive;
	}

	public Integer getlCommType() {
		return lCommType;
	}

	public void setlCommType(Integer lCommType) {
		this.lCommType = lCommType;
	}

	public String getlCommTypeName() {
		return lCommTypeName;
	}

	public void setlCommTypeName(String lCommTypeName) {
		this.lCommTypeName = lCommTypeName;
	}

	public String getVcComm() {
		return vcComm;
	}

	public void setVcComm(String vcComm) {
		this.vcComm = vcComm;
	}

	public Integer getlInsType() {
		return lInsType;
	}

	public void setlInsType(Integer lInsType) {
		this.lInsType = lInsType;
	}

	public String getlInsTypeName() {
		return lInsTypeName;
	}

	public void setlInsTypeName(String lInsTypeName) {
		this.lInsTypeName = lInsTypeName;
	}

	public String getVcRemark() {
		return vcRemark;
	}

	public void setVcRemark(String vcRemark) {
		this.vcRemark = vcRemark;
	}

	public Integer getlDataState() {
		return lDataState;
	}

	public void setlDataState(Integer lDataState) {
		this.lDataState = lDataState;
	}

	public String getlDataStateName() {
		return lDataStateName;
	}

	public void setlDataStateName(String lDataStateName) {
		this.lDataStateName = lDataStateName;
	}

	public String getVcMac() {
		return vcMac;
	}

	public void setVcMac(String vcMac) {
		this.vcMac = vcMac;
	}

	public String getVcEquipment() {
		return vcEquipment;
	}

	public void setVcEquipment(String vcEquipment) {
		this.vcEquipment = vcEquipment;
	}

	@Override
	public String toString() {
		return "InstrumentD{" +
				"lEquipment=" + lEquipment +
				", vcEquipment='" + vcEquipment + '\'' +
				", lInstrument=" + lInstrument +
				", vcInstrument='" + vcInstrument + '\'' +
				", vcDrive='" + vcDrive + '\'' +
				", lCommType=" + lCommType +
				", lCommTypeName='" + lCommTypeName + '\'' +
				", vcComm='" + vcComm + '\'' +
				", lInsType=" + lInsType +
				", lInsTypeName='" + lInsTypeName + '\'' +
				", vcMac='" + vcMac + '\'' +
				", vcRemark='" + vcRemark + '\'' +
				", lDataState=" + lDataState +
				", lDataStateName='" + lDataStateName + '\'' +
				'}';
	}
}
