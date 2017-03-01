package usercenter.saleManage.model;

import java.util.Date;

public class AccessRecord {
	private int			record_id;
	private int			submit_id;
	private String      receive_name;
	private int			receive_id;
	private Date			create_dt;
	private int			access_status;
	private int         h_id;
	private int         accepter_reg_id;
	private Date			update_dt;
	
	private Integer receive_invite_id;
	public int getRecord_id() {
		return record_id;
	}
	public void setRecord_id(int record_id) {
		this.record_id = record_id;
	}
	public Date getCreate_dt() {
		return create_dt;
	}
	public void setCreate_dt(Date create_dt) {
		this.create_dt = create_dt;
	}
	public int getSubmit_id() {
		return submit_id;
	}
	public void setSubmit_id(int submit_id) {
		this.submit_id = submit_id;
	}
	public int getReceive_id() {
		return receive_id;
	}
	public void setReceive_id(int receive_id) {
		this.receive_id = receive_id;
	}
	public String getReceive_name() {
		return receive_name;
	}
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}
	public int getAccess_status() {
		return access_status;
	}
	public void setAccess_status(int access_status) {
		this.access_status = access_status;
	}
	public int getH_id() {
		return h_id;
	}
	public void setH_id(int h_id) {
		this.h_id = h_id;
	}
	public int getAccepter_reg_id() {
		return accepter_reg_id;
	}
	public void setAccepter_reg_id(int accepter_reg_id) {
		this.accepter_reg_id = accepter_reg_id;
	}
	public Date getUpdate_dt() {
		return update_dt;
	}
	public void setUpdate_dt(Date update_dt) {
		this.update_dt = update_dt;
	}
	public Integer getReceive_invite_id() {
		return receive_invite_id;
	}
	public void setReceive_invite_id(Integer receive_invite_id) {
		this.receive_invite_id = receive_invite_id;
	}
	
}
