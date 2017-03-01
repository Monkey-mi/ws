/**  
* @Title: PfApplicationAccessScoreService.java
* @Package platform.company.service
* @Description: TODO
* @author 舒飞
* @date 2016-9-20 上午11:18:32 
*/ 
package platform.company.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.data.PfApplicationAccessMapper;
import platform.supplierAccess.model.PfSupplierAccessScore;

@Service
public class PfApplicationAccessScoreService {
	@Autowired
	private PfApplicationAccessMapper pfApplicationAccessMapper;
	
	public List<PfSupplierAccessScore> getPfScoreListNotInErp(Map<String,Object> params){
		return pfApplicationAccessMapper.getPfScoreListNotInErp(params);
	}
	public void addPfSupplierAccessScoreFromErp(PfSupplierAccessScore obj){
			pfApplicationAccessMapper.addPfSupplierAccessScoreFromErp(obj);
	}
	public void updatePfSupplierAccessScoreFromErp(PfSupplierAccessScore obj){
			pfApplicationAccessMapper.updatePfSupplierAccessScoreFromErp(obj);
	}
	public void deletePfSupplierAccessScoreFromErp(List<PfSupplierAccessScore> arr){
		for(PfSupplierAccessScore obj:arr){
			pfApplicationAccessMapper.deletePfSupplierAccessScoreFromErp(obj);
		}
	}
	public int getpfAccessScoreCount(Map<String,Object> params){
		return pfApplicationAccessMapper.getpfAccessScoreCount(params);
	}
}
