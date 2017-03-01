package usercenter.saleManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usercenter.saleManage.data.AccessApplicationInfoMapper;
import usercenter.saleManage.model.AccessApplicationInfo;
@Service
public class AccessApplicationInfoService {
	@Autowired
	private AccessApplicationInfoMapper mapper;
	
	public List<AccessApplicationInfo> getAccessApplicationInfoList(Map<String, Object> params){
		return mapper.getAccessApplicationInfoList(params);
	}
	
	public void addAccessApplicationInfo(Map<String, Object> params){
		mapper.addAccessApplicationInfo(params);
	}
	public void updateAccessApplicationInfo(Map<String, Object> params){
		mapper.updateAccessApplicationInfo(params);
	}
	
}
