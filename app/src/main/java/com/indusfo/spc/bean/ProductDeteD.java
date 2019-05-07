package com.indusfo.spc.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 产品检测单表T_B41_DETE 的映射实体类
 *
 * @author xuz
 * @date 2019/3/8 11:05 AM
 * @param
 * @return
 */
public class ProductDeteD implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3569442399953284280L;
	// 产品检测单ID
	private Integer lDeteId;

	// 来源ID
	private Integer lSourceId;

	// 来源ID（数据字典翻译）
	private String lSourceIdName;

	// 计量单位
	private Integer lUnit;

	// 计量单位（数据字典翻译）
	private String lUnitName;

	// 单位翻译
	private String lUnitNo;

	// 产品ID
	private Integer lProduct;

	// 工艺版本id
	private Integer lProVer;

	// 工艺流程id
	private Integer lProFlow;
	/**工艺目标对象*/
	private ProcessTargetD processTargetD;
	/**工艺目标对象*/
	private List<ProcessTargetD> processTargetDList;
	/**工艺目标id*/
    private Integer lProTarget;

	// 批次
	private Integer lBatchId;

	// 批次编号
	private String lBatchCode;
	// 判定结果
	private Integer lResult;

	// 判定结果（数据字典翻译）
	private String lResultName;

	// 制单人
	private Integer lUserId;
	// 工序id
	private Integer lProId;

	// 工序名称
	private String vcProName;

	// 制单时间
	private String dCreateTime;

	// 说明
	private String vcRemark;

	// 数据状态
	private Integer lDataState;

	// 数据状态（数据字典翻译）
	private String lDataStateName;

	// 公司产品编码
	private String vcProductNo;

	// 产品名称
	private String vcProductName;

	// 规格型号
	private String vcModel;

	// 客户名称
	private String vcCustomerName;

	// 客户型号
	private String vcModelCust;

	// 毛胚重量
	private String vcBlankWeight;

	// 产品重量
	private String vcProductWeight;

	// 版本号
	private String vcVersion;

	// 每组样本数
	private Integer lSwatch;

	// 品名材质
	private String vcProjectName;

	// 用户名
	private String vcUserCode;

	// 用户中文名称
	private String vcUserName;

	// 图片地址
	private String vcPicAd;

	// 来源单号
	private String Sbill;

	public Integer getlDeteId() {
		return lDeteId;
	}

	public void setlDeteId(Integer lDeteId) {
		this.lDeteId = lDeteId;
	}

	public Integer getlSourceId() {
		return lSourceId;
	}

	public void setlSourceId(Integer lSourceId) {
		this.lSourceId = lSourceId;
	}

	public String getlSourceIdName() {
		return lSourceIdName;
	}

	public void setlSourceIdName(String lSourceIdName) {
		this.lSourceIdName = lSourceIdName;
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

	public String getlUnitNo() {
		return lUnitNo;
	}

	public void setlUnitNo(String lUnitNo) {
		this.lUnitNo = lUnitNo;
	}

	public Integer getlProduct() {
		return lProduct;
	}

	public void setlProduct(Integer lProduct) {
		this.lProduct = lProduct;
	}

	public Integer getlProVer() {
		return lProVer;
	}

	public void setlProVer(Integer lProVer) {
		this.lProVer = lProVer;
	}

	public Integer getlProFlow() {
		return lProFlow;
	}

	public void setlProFlow(Integer lProFlow) {
		this.lProFlow = lProFlow;
	}

	public Integer getlBatchId() {
		return lBatchId;
	}

	public void setlBatchId(Integer lBatchId) {
		this.lBatchId = lBatchId;
	}

	public Integer getlResult() {
		return lResult;
	}

	public void setlResult(Integer lResult) {
		this.lResult = lResult;
	}

	public String getlResultName() {
		return lResultName;
	}

	public void setlResultName(String lResultName) {
		this.lResultName = lResultName;
	}

	public Integer getlUserId() {
		return lUserId;
	}

	public void setlUserId(Integer lUserId) {
		this.lUserId = lUserId;
	}

	public Integer getlProId() {
		return lProId;
	}

	public void setlProId(Integer lProId) {
		this.lProId = lProId;
	}

	public String getVcProName() {
		return vcProName;
	}

	public void setVcProName(String vcProName) {
		this.vcProName = vcProName;
	}

	public String getdCreateTime() {
		return dCreateTime;
	}

	public void setdCreateTime(String dCreateTime) {
		this.dCreateTime = dCreateTime;
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

	public String getVcProductNo() {
		return vcProductNo;
	}

	public void setVcProductNo(String vcProductNo) {
		this.vcProductNo = vcProductNo;
	}

	public String getVcProductName() {
		return vcProductName;
	}

	public void setVcProductName(String vcProductName) {
		this.vcProductName = vcProductName;
	}

	public String getVcModel() {
		return vcModel;
	}

	public void setVcModel(String vcModel) {
		this.vcModel = vcModel;
	}

	public String getVcCustomerName() {
		return vcCustomerName;
	}

	public void setVcCustomerName(String vcCustomerName) {
		this.vcCustomerName = vcCustomerName;
	}

	public String getVcModelCust() {
		return vcModelCust;
	}

	public void setVcModelCust(String vcModelCust) {
		this.vcModelCust = vcModelCust;
	}

	public String getVcBlankWeight() {
		return vcBlankWeight;
	}

	public void setVcBlankWeight(String vcBlankWeight) {
		this.vcBlankWeight = vcBlankWeight;
	}

	public String getVcProductWeight() {
		return vcProductWeight;
	}

	public void setVcProductWeight(String vcProductWeight) {
		this.vcProductWeight = vcProductWeight;
	}

	public String getVcVersion() {
		return vcVersion;
	}

	public void setVcVersion(String vcVersion) {
		this.vcVersion = vcVersion;
	}

	public Integer getlSwatch() {
		return lSwatch;
	}

	public void setlSwatch(Integer lSwatch) {
		this.lSwatch = lSwatch;
	}

	public String getVcProjectName() {
		return vcProjectName;
	}

	public void setVcProjectName(String vcProjectName) {
		this.vcProjectName = vcProjectName;
	}

	public String getVcUserCode() {
		return vcUserCode;
	}

	public void setVcUserCode(String vcUserCode) {
		this.vcUserCode = vcUserCode;
	}

	public String getVcUserName() {
		return vcUserName;
	}

	public void setVcUserName(String vcUserName) {
		this.vcUserName = vcUserName;
	}

	public String getVcPicAd() {
		return vcPicAd;
	}

	public void setVcPicAd(String vcPicAd) {
		this.vcPicAd = vcPicAd;
	}

	public String getlBatchCode() {
		return lBatchCode;
	}

	public void setlBatchCode(String lBatchCode) {
		this.lBatchCode = lBatchCode;
	}

	public String getSbill() {
		return Sbill;
	}

	public void setSbill(String sbill) {
		Sbill = sbill;
	}

    public Integer getlProTarget() {
        return lProTarget;
    }

    public void setlProTarget(Integer lProTarget) {
        this.lProTarget = lProTarget;
    }

	public ProcessTargetD getProcessTargetD() {
		return processTargetD;
	}

	public void setProcessTargetD(ProcessTargetD processTargetD) {
		this.processTargetD = processTargetD;
	}

	public List<ProcessTargetD> getProcessTargetDList() {
		return processTargetDList;
	}

	public void setProcessTargetDList(List<ProcessTargetD> processTargetDList) {
		this.processTargetDList = processTargetDList;
	}

	@Override
	public String toString() {
		return "ProductDeteD{" +
				"lDeteId=" + lDeteId +
				", lSourceId=" + lSourceId +
				", lSourceIdName='" + lSourceIdName + '\'' +
				", lUnit=" + lUnit +
				", lUnitName='" + lUnitName + '\'' +
				", lUnitNo='" + lUnitNo + '\'' +
				", lProduct=" + lProduct +
				", lProVer=" + lProVer +
				", lProFlow=" + lProFlow +
				", processTargetD=" + processTargetD +
				", processTargetDList=" + processTargetDList +
				", lProTarget=" + lProTarget +
				", lBatchId=" + lBatchId +
				", lBatchCode='" + lBatchCode + '\'' +
				", lResult=" + lResult +
				", lResultName='" + lResultName + '\'' +
				", lUserId=" + lUserId +
				", lProId=" + lProId +
				", vcProName='" + vcProName + '\'' +
				", dCreateTime='" + dCreateTime + '\'' +
				", vcRemark='" + vcRemark + '\'' +
				", lDataState=" + lDataState +
				", lDataStateName='" + lDataStateName + '\'' +
				", vcProductNo='" + vcProductNo + '\'' +
				", vcProductName='" + vcProductName + '\'' +
				", vcModel='" + vcModel + '\'' +
				", vcCustomerName='" + vcCustomerName + '\'' +
				", vcModelCust='" + vcModelCust + '\'' +
				", vcBlankWeight='" + vcBlankWeight + '\'' +
				", vcProductWeight='" + vcProductWeight + '\'' +
				", vcVersion='" + vcVersion + '\'' +
				", lSwatch=" + lSwatch +
				", vcProjectName='" + vcProjectName + '\'' +
				", vcUserCode='" + vcUserCode + '\'' +
				", vcUserName='" + vcUserName + '\'' +
				", vcPicAd='" + vcPicAd + '\'' +
				", Sbill='" + Sbill + '\'' +
				'}';
	}
}