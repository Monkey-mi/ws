/**  
* @Title: PfApplicationAccessMapper.java
* @Package platform.company.data
* @Description: TODO
* @author 舒飞
* @date 2016-9-20 下午12:10:44 
*/ 
package platform.company.data;

import java.util.List;
import java.util.Map;

import platform.supplierAccess.model.PfSupplierAccessScore;

public interface PfApplicationAccessMapper {
	public List<PfSupplierAccessScore> getPfScoreListNotInErp(Map<String,Object> params);
	public void addPfSupplierAccessScoreFromErp(PfSupplierAccessScore obj);
	public void updatePfSupplierAccessScoreFromErp(PfSupplierAccessScore obj);
	public void deletePfSupplierAccessScoreFromErp(PfSupplierAccessScore obj);
	/**
	* @Title: getpfAccessScoreCount
	* @Description: PfSupplierAccessScoreMapper
	* @param params
	* @return
	* @returnType int    
	* @author 舒飞
	* @date 2016-9-20上午11:04:21
	*/
	public int getpfAccessScoreCount(Map<String, Object> params);
}
