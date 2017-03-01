package platform.contract.model;

import java.io.Serializable;
import java.util.Date;

public class PfPurchaseOrderDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int			order_detail_id;
	private int			pur_order_id;
	private String			product_name;
	private String			product_size;
	private double			unit_price;
	private double			number;
	private String			unit;
	private double			money;
	private Date			delivery_date;
	private double			delivery_num;
	private double			no_delivery_num;
	private double			Storage_num;
	private String			htmx;
	private String			product_artno;
	private String			remark;
	private double 		stay_back_num;
	private double 		stay_storage_num;
	private double 		stay_check_num;
	private double      retired_back_num;
	private double      stay_detail_num;
	
	public double getRetired_back_num() {
		return retired_back_num;
	}

	public void setRetired_back_num(double retired_back_num) {
		this.retired_back_num = retired_back_num;
	}

	public double getStay_detail_num() {
		return stay_detail_num;
	}

	public void setStay_detail_num(double stay_detail_num) {
		this.stay_detail_num = stay_detail_num;
	}

	public double getDelivery_num() {
		return delivery_num;
	}

	public void setDelivery_num(double delivery_num) {
		this.delivery_num = delivery_num;
	}

	public double getNo_delivery_num() {
		return no_delivery_num;
	}

	public void setNo_delivery_num(double no_delivery_num) {
		this.no_delivery_num = no_delivery_num;
	}

	public double getStorage_num() {
		return Storage_num;
	}

	public void setStorage_num(double storage_num) {
		Storage_num = storage_num;
	}

	public double getStay_back_num() {
		return stay_back_num;
	}

	public void setStay_back_num(double stay_back_num) {
		this.stay_back_num = stay_back_num;
	}

	public double getStay_storage_num() {
		return stay_storage_num;
	}

	public void setStay_storage_num(double stay_storage_num) {
		this.stay_storage_num = stay_storage_num;
	}

	public double getStay_check_num() {
		return stay_check_num;
	}
	public void setStay_check_num(double stay_check_num) {
		this.stay_check_num = stay_check_num;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getRemark() {
		if(remark==null){
			return "";
		}
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getOrder_detail_id() {
		return order_detail_id;
	}
	public void setOrder_detail_id(int order_detail_id) {
		this.order_detail_id = order_detail_id;
	}
	public int getPur_order_id() {
		return pur_order_id;
	}
	public void setPur_order_id(int pur_order_id) {
		this.pur_order_id = pur_order_id;
	}
	public String getProduct_name() {
		if(product_name==null){
			return "";
		}
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_size() {
		return product_size;
	}
	public void setProduct_size(String product_size) {
		this.product_size = product_size;
	}
	public double getUnit_price() {
		return unit_price;
	}
	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}
	public double getNumber() {
		return number;
	}
	public void setNumber(double number) {
		this.number = number;
	}
	public String getUnit() {
		if(unit==null){
			return "";
		}
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public Date getDelivery_date() {
		return delivery_date;
	}
	public void setDelivery_date(Date delivery_date) {
		this.delivery_date = delivery_date;
	}
	public void setBe_entered_num(int no_delivery_num) {
		this.no_delivery_num = no_delivery_num;
	}
	public String getHtmx() {
		if(htmx==null){
			return "";
		}
		return htmx;
	}
	public void setHtmx(String htmx) {
		this.htmx = htmx;
	}
	public String getProduct_artno() {
		if(product_artno==null){
			return "";
		}
		return product_artno;
	}
	public void setProduct_artno(String product_artno) {
		this.product_artno = product_artno;
	}
}
