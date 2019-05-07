package com.indusfo.spc.bean;

import java.io.Serializable;

/**
 * 检测项目
 *
 * @author xuz
 * @date 2019/3/8 11:05 AM
 * @param
 * @return
 */
public class DetePro implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2277809530629987708L;
	/**
	 * 
	 */
	
	/**检测项目ID*/
	private Integer lDeteProj;
	/**检测项目编码*/
	private String vcDeteProj;
	/**检测项目名称*/
	private String vcDeteName;
	/**计量单位 数据字典101003*/
	private Integer lDeteUnit;
	/**计量单位 数据字典101003*/
	private String lDeteUnitName;
	/**计量单位 英文 数据字典101003*/
	private String lDeteUnitNo;
	/**项目标准*/
	private String vcStandard;
	/**仪器类型 数据字典101006*/
	private Integer lInsType;
	/**仪器类型 数据字典101006*/
	private String lInsTypeName;
	/**说明*/
	private String vcRemark;
	/**数据状态*/
	private Integer lDataState;
	/**数据状态*/
	private String lDataStateName;

	public String getlDeteUnitNo() {
		return lDeteUnitNo;
	}

	public void setlDeteUnitNo(String lDeteUnitNo) {
		this.lDeteUnitNo = lDeteUnitNo;
	}

	public Integer getlDeteProj() {
		return lDeteProj;
	}
	public void setlDeteProj(Integer lDeteProj) {
		this.lDeteProj = lDeteProj;
	}
	public String getVcDeteProj() {
		return vcDeteProj;
	}
	public void setVcDeteProj(String vcDeteProj) {
		this.vcDeteProj = vcDeteProj;
	}
	public String getVcDeteName() {
		return vcDeteName;
	}
	public void setVcDeteName(String vcDeteName) {
		this.vcDeteName = vcDeteName;
	}
	public Integer getlDeteUnit() {
		return lDeteUnit;
	}
	public void setlDeteUnit(Integer lDeteUnit) {
		this.lDeteUnit = lDeteUnit;
	}
	public String getlDeteUnitName() {
		return lDeteUnitName;
	}
	public void setlDeteUnitName(String lDeteUnitName) {
		this.lDeteUnitName = lDeteUnitName;
	}
	public String getVcStandard() {
		return vcStandard;
	}
	public void setVcStandard(String vcStandard) {
		this.vcStandard = vcStandard;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DetePro{" +
				"lDeteProj=" + lDeteProj +
				", vcDeteProj='" + vcDeteProj + '\'' +
				", vcDeteName='" + vcDeteName + '\'' +
				", lDeteUnit=" + lDeteUnit +
				", lDeteUnitName='" + lDeteUnitName + '\'' +
				", lDeteUnitNo='" + lDeteUnitNo + '\'' +
				", vcStandard='" + vcStandard + '\'' +
				", lInsType=" + lInsType +
				", lInsTypeName='" + lInsTypeName + '\'' +
				", vcRemark='" + vcRemark + '\'' +
				", lDataState=" + lDataState +
				", lDataStateName='" + lDataStateName + '\'' +
				'}';
	}
}
