package com.indusfo.spc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 检测条件实体类
 *
 * @author xuz
 * @date 2019/3/8 11:05 AM
 * @param
 * @return
 */
public class DeteCondD implements Serializable{

	private static final long serialVersionUID = 3060161217047024332L;
	
	/**检测条件*/
	private Integer lDeteCond;

	/**检测条件值*/
	private List<DeteValueD> deteValueList;

	/**顺序号*/
	private Integer lOrder;
	/**检测条件名称*/
	private String vcDeteCond;
	/**仪器类型*/
	private Integer lInsType;
	/**数据类型*/
	private Integer lDataType;
	
	/**计量单位*/
	private Integer lUnit;
	/**计量单位 名称*/
	private String lUnitName;
	/**计量单位 名称 英文*/
	private String lUnitNo;
	
	/**仪器类型 名称*/
	private String lInsTypeName;
	/**数据类型 名称*/
	private String lDataTypeName;
	
	
	/**说明*/
	private String vcRemark;
	/**数据状态 名称*/
	private String lDataStateName;

	public List<DeteValueD> getDeteValueList() {
		return deteValueList;
	}

	public void setDeteValueList(List<DeteValueD> deteValueList) {
		this.deteValueList = deteValueList;
	}

	public String getlUnitNo() {
		return lUnitNo;
	}

	public void setlUnitNo(String lUnitNo) {
		this.lUnitNo = lUnitNo;
	}

	public Integer getlInsType() {
		return lInsType;
	}
	public void setlInsType(Integer lInsType) {
		this.lInsType = lInsType;
	}
	
	public Integer getlDeteCond() {
		return lDeteCond;
	}
	public void setlDeteCond(Integer lDeteCond) {
		this.lDeteCond = lDeteCond;
	}
	public Integer getlOrder() {
		return lOrder;
	}
	public void setlOrder(Integer lOrder) {
		this.lOrder = lOrder;
	}
	public String getVcDeteCond() {
		return vcDeteCond;
	}
	public void setVcDeteCond(String vcDeteCond) {
		this.vcDeteCond = vcDeteCond;
	}
	
	public Integer getlDataType() {
		return lDataType;
	}
	public void setlDataType(Integer lDataType) {
		this.lDataType = lDataType;
	}
	public String getlInsTypeName() {
		return lInsTypeName;
	}
	public void setlInsTypeName(String lInsTypeName) {
		this.lInsTypeName = lInsTypeName;
	}
	public String getlDataTypeName() {
		return lDataTypeName;
	}
	public void setlDataTypeName(String lDataTypeName) {
		this.lDataTypeName = lDataTypeName;
	}
	public String getVcRemark() {
		return vcRemark;
	}
	public void setVcRemark(String vcRemark) {
		this.vcRemark = vcRemark;
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
	public Integer getlUnit() {
		return lUnit;
	}
	public void setlUnit(Integer lUnit) {
		this.lUnit = lUnit;
	}
	public String getlUnitName() {
		return lUnitName;
	}
	public void setlUnitName(String lUnitName) {
		this.lUnitName = lUnitName;
	}

	@Override
	public String toString() {
		return "DeteCondD{" +
				"lDeteCond=" + lDeteCond +
				", deteValueList=" + deteValueList +
				", lOrder=" + lOrder +
				", vcDeteCond='" + vcDeteCond + '\'' +
				", lInsType=" + lInsType +
				", lDataType=" + lDataType +
				", lUnit=" + lUnit +
				", lUnitName='" + lUnitName + '\'' +
				", lUnitNo='" + lUnitNo + '\'' +
				", lInsTypeName='" + lInsTypeName + '\'' +
				", lDataTypeName='" + lDataTypeName + '\'' +
				", vcRemark='" + vcRemark + '\'' +
				", lDataStateName='" + lDataStateName + '\'' +
				'}';
	}
}
