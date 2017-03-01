package usercenter.supplierFiles.data;

import java.util.List;
import java.util.Map;

import usercenter.saleManage.model.CheckFactoryCycle;
import usercenter.supplierFiles.model.SupplierFiles;



public interface SupplierFilesMapper {
	
	/**
	 * 通过拥有者ID查询 主账号 供应商列表信息
	*getSupplierFilseByOwnerId
	*@param params
	*@return
	*List<Map<String,Object>>
	*@author mishengliang
	*2016-7-28上午11:17:54
	 */
	public List<Map<String, Object>> getSupplierFilseForMain(Map<String, Object> params);
	
	/**
	 * @Description: 通过拥有者ID查询 主账号 供应商总数
	 * SupplierFilesMapper
	 * getSupplierFilseForMainTotal
	 * @param params
	 * @return Integer
	 * @author mishengliang
	 * 2016-8-14 下午4:12:59
	 */
	public Integer getSupplierFilseForMainTotal(Map<String, Object> params);
	
	/**
	 * @Description: 通过拥有者ID查询 子账号 供应商列表信息
	 * SupplierFilesMapper
	 * getSupplierFilseForSub
	 * @param params
	 * @return List<Map<String,Object>>
	 * @author mishengliang
	 * 2016-8-10 上午11:27:39
	 */
	public List<Map<String, Object>> getSupplierFilseForSub(Map<String, Object> params);
	
	/**
	 * @Description: 通过拥有者ID查询 子账号 供应商总数
	 * SupplierFilesMapper
	 * getSupplierFilseForSubTotal
	 * @param params
	 * @return Integer
	 * @author mishengliang
	 * 2016-8-14 下午4:15:10
	 */
	public Integer getSupplierFilseForSubTotal(Map<String, Object> params);
	
	/**
	 * @Description: 查询验厂周期基础信息表
	 * SupplierFilesMapper
	 * getCheckCycle
	 * @return List<CheckFactoryCycle>
	 * @author mishengliang
	 * 2016-8-1 下午3:40:23
	 */
	public List<CheckFactoryCycle> getCheckCycle();
	
	/**
	 * @Description: 通过ID改变供应商状态
	 * SupplierFilesMapper
	 * updateSupplierStatusById
	 * @param params void
	 * @author mishengliang
	 * 2016-8-14 下午3:07:48
	 */
	public void updateSupplierStatusById(Map<String, Object> params);
	
	/**
	 * @Description: 逻辑删除供应商
	 * SupplierFilesMapper
	 * deleteSupplierById
	 * @param params void
	 * @author mishengliang
	 * 2016-8-20 下午2:59:19
	 */
	public void deleteSupplierById(Map<String, Object> params);
	
	public void addSupplierFiles(Map<String, Object> params);


	public SupplierFiles checkSupplierExist(Map<String, Object> params);
	public SupplierFiles checkSupplierOfBrother(Map<String, Object> params);
	
	/**
	 * @Description: 获取供应商采购分类
	 * SupplierFilesMapper
	 * getSupplierCategory
	 * @param params
	 * @return List<Map<String,Object>>
	 * @author mishengliang
	 * 2016-8-17 下午3:01:32
	 */
	public List<Map<String, Object>> getSupplierCategory(Map<String, Object> params);
	
	/**
	 * @Description: 获取免检供应商
	 * SupplierFilesMapper
	 * getExemptCheckSupplier
	 * @param params
	 * @return List<SupplierFiles>
	 * @author mishengliang
	 * 2016-8-22 下午4:45:44
	 */
	public List<SupplierFiles> getExemptCheckSupplier(Map<String, Object> params);
	
	/**
	 * @Description: 获取免检供应商总数
	 * SupplierFilesMapper
	 * getExemptCheckSupplierTotal
	 * @param params
	 * @return Integer
	 * @author mishengliang
	 * 2016-8-24 上午11:12:41
	 */
	public Integer getExemptCheckSupplierTotal(Map<String, Object> params);
	
	/**
	 * @Description: 设置免检供应商
	 * SupplierFilesMapper
	 * siteNoCheckSupplierFile
	 * @param params void
	 * @author mishengliang
	 * 2016-8-24 下午4:56:37
	 */
	public void siteNoCheckSupplierFile(Map<String, Object> params);
	
	/**
	 * @Description: 恢复验厂
	 * SupplierFilesMapper
	 * regainCheckFac
	 * @param params void
	 * @author mishengliang
	 * 2016-8-24 下午5:40:43
	 */
	public void regainCheckFac(Map<String, Object> params);
}
