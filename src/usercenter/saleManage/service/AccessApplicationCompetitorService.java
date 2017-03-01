package usercenter.saleManage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usercenter.saleManage.data.AccessApplicationCompetitorMapper;
import usercenter.saleManage.model.AccessApplicationCompetitor;


@Service
public class AccessApplicationCompetitorService {
	@Autowired
	private AccessApplicationCompetitorMapper mapper;

	public List<AccessApplicationCompetitor> getAccessApplicationCompetitorList(Map<String,Object> params) {
		return mapper.getAccessApplicationCompetitorList(params);
	}
	public void addAccessApplicationCompetitor(Map<String,Object> params) {
			mapper.addAccessApplicationCompetitor(params);
	}
	public void updateAccessApplicationCompetitor(Map<String,Object> params) {
			mapper.updateAccessApplicationCompetitor(params);
	}
	public void deleteAccessApplicationCompetitor(Map<String,Object> params) {
			mapper.deleteAccessApplicationCompetitor(params);
	}
	public void deleteAccessApplicationCompetitors(Map<String,Object> params) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (!"".equals(params.get("competitor_ids").toString())) {
			String arrayStr=params.get("competitor_ids").toString();
			String[] arrayId=arrayStr.split(",");
			for(int i=0;i<arrayId.length;i++){
				map.put("competitorId", arrayId[i]);
				mapper.deleteAccessApplicationCompetitor(map);
			}
		}
	}
}
