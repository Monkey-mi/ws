package usercenter.externalTask.model;

import java.math.BigDecimal;

public class TaskAllcheckUnqudetails {
	private Integer			detail_id;
	private String			jldh;
	private BigDecimal			jlxh;
	private String			xmmc;
	private BigDecimal			bhgs;

	public Integer getdetail_id() {
		return detail_id;
	}
	public void setdetail_id(Integer detail_id) {
		this.detail_id = detail_id;
	}
	public String getjldh() {
		return jldh;
	}
	public void setjldh(String jldh) {
		this.jldh = jldh;
	}
	public BigDecimal getjlxh() {
		return jlxh;
	}
	public void setjlxh(BigDecimal jlxh) {
		this.jlxh = jlxh;
	}
	public String getxmmc() {
		return xmmc;
	}
	public void setxmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	public BigDecimal getbhgs() {
		return bhgs;
	}
	public void setbhgs(BigDecimal bhgs) {
		this.bhgs = bhgs;
	}
	
	@Override
	public String toString() {
		return "TaskAllcheckUnqudetails [detail_id=" + detail_id + ", jldh="
				+ jldh + ", jlxh=" + jlxh + ", xmmc=" + xmmc + ", bhgs=" + bhgs
				+ "]";
	}
}
