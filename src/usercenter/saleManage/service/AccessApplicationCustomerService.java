package usercenter.saleManage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.service.PfMainCustomerService;
import usercenter.saleManage.data.AccessApplicationCustomerMapper;
import usercenter.saleManage.model.AccessApplicationCustomer;


@Service
public class AccessApplicationCustomerService {
	@Autowired
	private AccessApplicationCustomerMapper mapper;
	@Autowired 
	private PfMainCustomerService pfMainCustomerService;

	public List<AccessApplicationCustomer> getAccessApplicationCustomerList(Map<String,Object> params) {
		return mapper.getAccessApplicationCustomerList(params);
	}
	public void addAccessApplicationCustomer(Map<String,Object> params) {
			mapper.addAccessApplicationCustomer(params);
	}
	public void updateAccessApplicationCustomer(Map<String,Object> params) {
			mapper.updateAccessApplicationCustomer(params);
	}
	public void deleteAccessApplicationCustomer(Map<String,Object> params) {
			mapper.deleteAccessApplicationCustomer(params);
	}
	public void deleteAccessApplicationCustomers(Map<String,Object> params) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (!"".equals(params.get("customer_ids").toString())) {
			String arrayStr=params.get("customer_ids").toString();
			String[] arrayId=arrayStr.split(",");
			for(int i=0;i<arrayId.length;i++){
				map.put("customerId", arrayId[i]);
				mapper.deleteAccessApplicationCustomer(map);
			}
		}
	}
}
