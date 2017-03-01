/**  
* @Title: PfApplicationCategory.java
* @Package platform.company.model
* @Description: TODO
* @author 舒飞
* @date 2016-9-19 下午6:46:59 
*/ 
package platform.company.model;

import java.util.Date;

public class PfApplicationCategory {
	private int id;
	private int category_id;
	private int record_id;
	private Date create_dt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
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
	
	

}
