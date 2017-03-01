package usercenter.saleManage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.service.PfDeviceService;
import usercenter.saleManage.data.AccessApplicationDevicelistMapper;
import usercenter.saleManage.model.AccessApplicationDevicelist;

import com.outsideasy.ws.erp.supplier.vo.DeviceOut;


@Service
public class AccessApplicationDevicelistService {
	@Autowired
	private AccessApplicationDevicelistMapper mapper;
	@Autowired
	private PfDeviceService pfDeviceService;
	public List<DeviceOut> getPfAccessApplicationDevicelistList(Map<String,Object> params) {
		return mapper.getPfAccessApplicationDevicelistList(params);
	}
	public List<AccessApplicationDevicelist> getAccessApplicationDevicelistList(Map<String,Object> params) {
		return mapper.getAccessApplicationDevicelistList(params);
	}
	public void addAccessApplicationDevicelist(Map<String,Object> params) {
			mapper.addAccessApplicationDevicelist(params);
	}
	public void updateAccessApplicationDevicelist(Map<String,Object> params) {
			mapper.updateAccessApplicationDevicelist(params);
	}
	public void deleteAccessApplicationDevicelist(Map<String,Object> params) {
			mapper.deleteAccessApplicationDevicelist(params);
	}
	public void deleteAccessApplicationDevicelists(Map<String,Object> params) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (!"".equals(params.get("devicelist_ids").toString())) {
			String arrayStr=params.get("devicelist_ids").toString();
			String[] arrayId=arrayStr.split(",");
			for(int i=0;i<arrayId.length;i++){
				map.put("deviceId", arrayId[i]);
				mapper.deleteAccessApplicationDevicelist(map);
			}
		}
	}
}
