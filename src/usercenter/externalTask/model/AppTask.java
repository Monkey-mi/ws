package usercenter.externalTask.model;

import java.util.Date;

public class AppTask {
	private int			t_id;
	private String			rwdh;
	private String			product_name;
	private String			serial_no;
	private int			state;
	private int			send_company;
	private int			receive_company;
	private float			total_qty;
	private float			produced_qty;
	private float			confirmed_qty;
	private Date			plan_start;
	private Date			plan_complete;
	private String			qc_type;
	private String			scdh;
	private String			product_pic;
	private Date			czsj;
	private String			stopreason;
	private Date			send_time;
	private Date			receive_time;
	private Date			finish_product_time;
	private Date			finish_task_time;
	private float			qualified_qty;
	private Date			stop_time;
	private String			remark;
	private String			operator_file;
	private String			producers_company_name;
	private float			confirmed_qtyVo;
	private float			sendout_qty;
	private String			ddh;
	private int			is_delete;
	private String			bzbh;
	private String			bzmc;

	public int gett_id() {
		return t_id;
	}
	public void sett_id(int t_id) {
		this.t_id = t_id;
	}
	public String getrwdh() {
		return rwdh;
	}
	public void setrwdh(String rwdh) {
		this.rwdh = rwdh;
	}
	public String getproduct_name() {
		return product_name;
	}
	public void setproduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getserial_no() {
		return serial_no;
	}
	public void setserial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public int getstate() {
		return state;
	}
	public void setstate(int state) {
		this.state = state;
	}
	public int getsend_company() {
		return send_company;
	}
	public void setsend_company(int send_company) {
		this.send_company = send_company;
	}
	public int getreceive_company() {
		return receive_company;
	}
	public void setreceive_company(int receive_company) {
		this.receive_company = receive_company;
	}
	public float gettotal_qty() {
		return total_qty;
	}
	public void settotal_qty(float total_qty) {
		this.total_qty = total_qty;
	}
	public float getproduced_qty() {
		return produced_qty;
	}
	public void setproduced_qty(float produced_qty) {
		this.produced_qty = produced_qty;
	}
	public float getconfirmed_qty() {
		return confirmed_qty;
	}
	public void setconfirmed_qty(float confirmed_qty) {
		this.confirmed_qty = confirmed_qty;
	}
	public Date getplan_start() {
		return plan_start;
	}
	public void setplan_start(Date plan_start) {
		this.plan_start = plan_start;
	}
	public Date getplan_complete() {
		return plan_complete;
	}
	public void setplan_complete(Date plan_complete) {
		this.plan_complete = plan_complete;
	}
	public String getqc_type() {
		return qc_type;
	}
	public void setqc_type(String qc_type) {
		this.qc_type = qc_type;
	}
	public String getscdh() {
		return scdh;
	}
	public void setscdh(String scdh) {
		this.scdh = scdh;
	}
	public String getproduct_pic() {
		return product_pic;
	}
	public void setproduct_pic(String product_pic) {
		this.product_pic = product_pic;
	}
	public Date getczsj() {
		return czsj;
	}
	public void setczsj(Date czsj) {
		this.czsj = czsj;
	}
	public String getstopreason() {
		return stopreason;
	}
	public void setstopreason(String stopreason) {
		this.stopreason = stopreason;
	}
	public Date getsend_time() {
		return send_time;
	}
	public void setsend_time(Date send_time) {
		this.send_time = send_time;
	}
	public Date getreceive_time() {
		return receive_time;
	}
	public void setreceive_time(Date receive_time) {
		this.receive_time = receive_time;
	}
	public Date getfinish_product_time() {
		return finish_product_time;
	}
	public void setfinish_product_time(Date finish_product_time) {
		this.finish_product_time = finish_product_time;
	}
	public Date getfinish_task_time() {
		return finish_task_time;
	}
	public void setfinish_task_time(Date finish_task_time) {
		this.finish_task_time = finish_task_time;
	}
	public float getqualified_qty() {
		return qualified_qty;
	}
	public void setqualified_qty(float qualified_qty) {
		this.qualified_qty = qualified_qty;
	}
	public Date getstop_time() {
		return stop_time;
	}
	public void setstop_time(Date stop_time) {
		this.stop_time = stop_time;
	}
	public String getremark() {
		return remark;
	}
	public void setremark(String remark) {
		this.remark = remark;
	}
	public String getoperator_file() {
		return operator_file;
	}
	public void setoperator_file(String operator_file) {
		this.operator_file = operator_file;
	}
	public String getproducers_company_name() {
		return producers_company_name;
	}
	public void setproducers_company_name(String producers_company_name) {
		this.producers_company_name = producers_company_name;
	}
	public float getconfirmed_qtyVo() {
		return confirmed_qtyVo;
	}
	public void setconfirmed_qtyVo(float confirmed_qtyVo) {
		this.confirmed_qtyVo = confirmed_qtyVo;
	}
	public float getsendout_qty() {
		return sendout_qty;
	}
	public void setsendout_qty(float sendout_qty) {
		this.sendout_qty = sendout_qty;
	}
	public String getddh() {
		return ddh;
	}
	public void setddh(String ddh) {
		this.ddh = ddh;
	}
	public int getis_delete() {
		return is_delete;
	}
	public void setis_delete(int is_delete) {
		this.is_delete = is_delete;
	}
	public String getbzbh() {
		return bzbh;
	}
	public void setbzbh(String bzbh) {
		this.bzbh = bzbh;
	}
	public String getbzmc() {
		return bzmc;
	}
	public void setbzmc(String bzmc) {
		this.bzmc = bzmc;
	}
}
