package usercenter.saleManage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usercenter.saleManage.data.AccessApplicationMetarialMapper;
import usercenter.saleManage.model.AccessApplicationMetarial;

@Service
public class AccessApplicationMaterialService {
	@Autowired
	private AccessApplicationMetarialMapper mapper;
	
	public List<AccessApplicationMetarial> getAccessApplicationMetarialList(Map<String, Object>params){
		return mapper.getAccessApplicationMetarialList(params);
	}
	public void addAccessApplicationMaterial(Map<String, Object>params){
		mapper.addAccessApplicationMetarial(params);
	}
	public void updateAccessApplicationMaterial(Map<String, Object>params){
		mapper.updateAccessApplicationMetarial(params);
	}
	public void deleteAccessApplicationMaterial(Map<String, Object>params){
		mapper.deleteAccessApplicationMetarial(params);
	}	
	public void deleteAccessApplicationMaterials(Map<String, Object>params){
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> map1=new HashMap<String, Object>();
		if (!"".equals(params.get("mateial_ids").toString())) {
			String arrayStr=params.get("mateial_ids").toString();
			String[] arrayId=arrayStr.split(",");
			for(int i=0;i<arrayId.length;i++){
				map1.put("materialId", arrayId[i]);
				mapper.deleteAccessApplicationMetarial(map1);
			}
		}
	}
}
