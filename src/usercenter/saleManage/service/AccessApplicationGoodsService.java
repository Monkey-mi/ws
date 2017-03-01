package usercenter.saleManage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.service.PfGoodsService;
import usercenter.saleManage.data.AccessApplicationGoodsMapper;
import usercenter.saleManage.model.AccessApplicationGoods;


@Service
public class AccessApplicationGoodsService {
	@Autowired
	private AccessApplicationGoodsMapper mapper;
	@Autowired
	private PfGoodsService pfGoodsService;


	public List<AccessApplicationGoods> getAccessApplicationGoodsList(Map<String,Object> params) {
		return mapper.getAccessApplicationGoodsList(params);
	}
	public void addAccessApplicationGoods(Map<String,Object> params) {
			mapper.addAccessApplicationGoods(params);
	}
	public void updateAccessApplicationGoods(Map<String,Object> params) {
			mapper.updateAccessApplicationGoods(params);
	}
	public void deleteAccessApplicationGoods(Map<String,Object> params) {
			mapper.deleteAccessApplicationGoods(params);
	}

	public void deleteAccessApplicationGoodses(Map<String,Object> params) {
		Map<String, Object> map=new HashMap<String, Object>();
		if (!"".equals(params.get("goods_ids").toString())) {
			String arrayStr=params.get("goods_ids").toString();
			String[] arrayId=arrayStr.split(",");
			for(int i=0;i<arrayId.length;i++){
				map.put("goodsId", arrayId[i]);
				mapper.deleteAccessApplicationGoods(map);
			}
		}
	}
}
