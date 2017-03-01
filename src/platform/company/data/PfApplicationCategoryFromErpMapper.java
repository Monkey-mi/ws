/**  
* @Title: PfApplicationCategoryFromErpMapper.java
* @Package platform.company.data
* @Description: TODO
* @author 舒飞
* @date 2016-9-19 下午6:49:49 
*/ 
package platform.company.data;

import java.util.Map;

public interface PfApplicationCategoryFromErpMapper {
	public void updateApplicationCategoryFromERP(Map<String, Object> params);
	public void addApplicationCategoryFromERP(Map<String, Object> params);
	public void deleteApplicationCategoryFromERP(int params); 
	public int getCategoryIdFromOut(String lbbh);
}
