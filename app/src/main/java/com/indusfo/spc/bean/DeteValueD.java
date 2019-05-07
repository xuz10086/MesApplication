package com.indusfo.spc.bean;

import java.io.Serializable;

/**
 * 检测条件值返回值封装类
 * @author 贺闻博
 * 
 *   2018年10月10日
 */
public class DeteValueD implements Serializable{
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		//检测条件值id
		private Integer lValueId;
		//检测条件值
        private String vcValue;
		//检测条件id
        private Integer lDeteCond;
        // 翻译
		private  String vcDeteCond;
		// 工艺目标ID
        private Integer lProTarget;
        // 翻译
		private String vcProTarget;
		//说明
        private String vcRemark;
		//数据状态
        private Integer lDataState;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getlValueId() {
		return lValueId;
	}

	public void setlValueId(Integer lValueId) {
		this.lValueId = lValueId;
	}

	public String getVcValue() {
		return vcValue;
	}

	public void setVcValue(String vcValue) {
		this.vcValue = vcValue;
	}

	public Integer getlDeteCond() {
		return lDeteCond;
	}

	public void setlDeteCond(Integer lDeteCond) {
		this.lDeteCond = lDeteCond;
	}

	public String getVcDeteCond() {
		return vcDeteCond;
	}

	public void setVcDeteCond(String vcDeteCond) {
		this.vcDeteCond = vcDeteCond;
	}

	public Integer getlProTarget() {
		return lProTarget;
	}

	public void setlProTarget(Integer lProTarget) {
		this.lProTarget = lProTarget;
	}

	public String getVcProTarget() {
		return vcProTarget;
	}

	public void setVcProTarget(String vcProTarget) {
		this.vcProTarget = vcProTarget;
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

	@Override
	public String toString() {
		return "DeteValueD{" +
				"lValueId=" + lValueId +
				", vcValue='" + vcValue + '\'' +
				", lDeteCond=" + lDeteCond +
				", vcDeteCond='" + vcDeteCond + '\'' +
				", lProTarget=" + lProTarget +
				", vcProTarget='" + vcProTarget + '\'' +
				", vcRemark='" + vcRemark + '\'' +
				", lDataState=" + lDataState +
				'}';
	}
}
