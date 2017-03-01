package platform.company.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.data.PfInvoiceTitleMapper;

import com.outsideasy.ws.erp.supplier.vo.InvoiceTitleOut;

@Service
public class PfInvoiceTitleService {

	@Autowired
	private PfInvoiceTitleMapper pfInvoiceTitleMapper;
	
	public List<InvoiceTitleOut> getInvoiceTitleOutList(Map<String,Object> params){
		return pfInvoiceTitleMapper.getInvoiceTitleOutList(params);
	}
	
	/**
	 * 通过公司ID获取发票抬头
	 *getInvoiceTitleNames
	 *@param companyId
	 *@return
	 *List<String>
	 *@author mishengliang
	 *2016-4-5下午4:30:02
	 */
	public List<String> getInvoiceTitleNames(Integer companyId){
		return pfInvoiceTitleMapper.getInvoiceTitleNames(companyId);
	}
	
	/**
	 * 删除指定发票抬头
	*deleteInvoiceTitle
	*@param params
	*void
	*@author mishengliang
	*2016-4-8下午7:14:00
	 */
	public void deleteInvoiceTitle(Map<String, Object> params){
		pfInvoiceTitleMapper.deleteInvoiceTitle(params);
	}
	
	/**
	 * 通过抬头ID更新发票抬头
	*updateInvoiceTitleByTitleId
	*@param params
	*void
	*@author mishengliang
	*2016-4-8下午7:14:05
	 */
	public void updateInvoiceTitleByTitleId(Map<String, Object> params){
		pfInvoiceTitleMapper.updateInvoiceTitleByTitleId(params);
	}
	
	/**
	 * 增加发票抬头
	*addInvoiceTitle
	*@param params
	*void
	*@author mishengliang
	*2016-4-8下午7:14:10
	 */
	public void addInvoiceTitle(Map<String, Object> params){
		pfInvoiceTitleMapper.addInvoiceTitle(params);
	}
	
	
	/**重置默认标记
	 * yukai
	 * @param params
	 */
	public void resetDefaultId(Map<String, Object> params){
		pfInvoiceTitleMapper.resetDefaultId(params);
	}

	public void deleteInvoiceTitles(Map<String, Object> params) {
		String arrayStr=params.get("invoice_title_ids").toString();
		String[] arrayinvoice_title_id=arrayStr.split(",");
		for(int i=0;i<arrayinvoice_title_id.length;i++){
			params.put("invoiceTitleId", arrayinvoice_title_id[i]);
			pfInvoiceTitleMapper.deleteInvoiceTitle(params);
		}
	}
}
