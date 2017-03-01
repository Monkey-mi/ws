/**    
 * 文件名：SupplierForPlateFormService.java    
 *    
 * 版本信息：    
 * 日期：2016-3-28    
 * Copyright 足下 Corporation 2016     
 * 版权所有    
 *    
 */
package platform.company.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.data.PfCompanyMapper;
import platform.company.model.PfCompanyExtraInfo;
import platform.company.model.PfCompanySimpleInfo;
import util.Const;
import util.DataTrans;

/**    
 *     
 * 项目名称：outsideeasy    
 * 类名称：SupplierForPlateFormService    
 * 创建人：mishengliang
 * 创建时间：2016-3-28 下午3:53:45    
 * 修改人：mishengliang
 * 修改时间：2016-3-28 下午3:53:45    
 * 修改备注：    
 * @version     
 *     
 */
@Service
public class CompanyForPlateFormService {

	@Autowired
	private PfCompanyMapper mapper;

	
	/**
	 *通过companyId更新公司信息
	*updateSupplierInfoByCompanyId
	*@param params
	*@return
	*Map<String,Object>
	*@author mishengliang
	*2016-4-14下午3:12:03
	 */
	public void updateSupplierInfoByCompanyId(Map<String, Object> params){
		mapper.updateSupplierInfo(params);
	}
	
	/**
	 * @Description:新增公司信息
	 * CompanyForPlateFormService
	 * addSupplierInfo
	 * @param params void
	 * @author yukai
	 * 2016-8-4 上午9:05:25
	 */
	public void addSupplierInfo(Map<String, Object> params){
		mapper.addSupplierInfo(params);
	}
	
	/**
	 * 根据公司ID获取企业信息表中的申请状态
	*getApplyStsByCompanyId
	*@param companyId
	*void
	*@author mishengliang
	*2016-5-19上午10:18:27
	 */
	public Integer getApplyStsByCompanyId(Integer companyId){
		return mapper.getApplyStsByCompanyId(companyId);
	}
	
	/**
	 * 根据申请状态获取所有的供应商名称、id
	 * @author yangliping
	 * 2016年5月20日13:26:47
	 * */
	public List<PfCompanySimpleInfo> getSupplierNameList(Map<String, Object> params)
	{
		return mapper.getSupplierNameList(params);
	}
	/**
	 * @Description:根据公司名称查找公司
	 * CompanyForPlateFormService
	 * getSupplierByName
	 * @param params
	 * @return List<PfCompanySimpleInfo>
	 * @author yukai
	 * 2016-8-10 下午3:12:24
	 */
	public List<PfCompanySimpleInfo> getSupplierByName(Map<String, Object> params)
	{
		return mapper.getSupplierByName(params);
	}
	
	public List<PfCompanySimpleInfo> getCompanyListByRegId(Map<String, Object> params) {
		return mapper.getCompanyListByRegId(params);
	}

	public PfCompanySimpleInfo getCompanyFromDictionary(
			Map<String, Object> params) {
		return mapper.getCompanyFromDictionary(params);
	}
	
}
