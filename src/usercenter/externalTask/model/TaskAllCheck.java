package usercenter.externalTask.model;

import java.math.BigDecimal;
import java.util.Date;

public class TaskAllCheck {
	private Integer			allcheck_id;
	private String			rwdh;
	private Integer			t_id;
	private String			jldh;
	private Date			jlrq;
	private BigDecimal			jysl;
	private BigDecimal			hgsl;
	private String			czym;
	private String			bzsm;
	private Integer			source_type;
	private Date			create_dt;

	public Integer getallcheck_id() {
		return allcheck_id;
	}
	public void setallcheck_id(Integer allcheck_id) {
		this.allcheck_id = allcheck_id;
	}
	public String getrwdh() {
		return rwdh;
	}
	public void setrwdh(String rwdh) {
		this.rwdh = rwdh;
	}
	public Integer gett_id() {
		return t_id;
	}
	public void sett_id(Integer t_id) {
		this.t_id = t_id;
	}
	public String getjldh() {
		return jldh;
	}
	public void setjldh(String jldh) {
		this.jldh = jldh;
	}
	public Date getjlrq() {
		return jlrq;
	}
	public void setjlrq(Date jlrq) {
		this.jlrq = jlrq;
	}
	public BigDecimal getjysl() {
		return jysl;
	}
	public void setjysl(BigDecimal jysl) {
		this.jysl = jysl;
	}
	public BigDecimal gethgsl() {
		return hgsl;
	}
	public void sethgsl(BigDecimal hgsl) {
		this.hgsl = hgsl;
	}
	public String getczym() {
		return czym;
	}
	public void setczym(String czym) {
		this.czym = czym;
	}
	public String getbzsm() {
		return bzsm;
	}
	public void setbzsm(String bzsm) {
		this.bzsm = bzsm;
	}
	public Integer getsource_type() {
		return source_type;
	}
	public void setsource_type(Integer source_type) {
		this.source_type = source_type;
	}
	public Date getcreate_dt() {
		return create_dt;
	}
	public void setcreate_dt(Date create_dt) {
		this.create_dt = create_dt;
	}
	
	@Override
	public String toString() {
		return "TaskAllCheck [allcheck_id=" + allcheck_id + ", rwdh=" + rwdh
				+ ", t_id=" + t_id + ", jldh=" + jldh + ", jlrq=" + jlrq
				+ ", jysl=" + jysl + ", hgsl=" + hgsl + ", czym=" + czym
				+ ", bzsm=" + bzsm + ", source_type=" + source_type
				+ ", create_dt=" + create_dt + "]";
	}
}
