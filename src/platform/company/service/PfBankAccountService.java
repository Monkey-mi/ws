package platform.company.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.data.PfBankAccountMapper;
import platform.company.model.PfBankAccount;

import com.outsideasy.ws.erp.supplier.vo.BankAccountOut;

@Service
public class PfBankAccountService {

	@Autowired
	private PfBankAccountMapper pfBankAccountMapper;
	
	public List<BankAccountOut> getBankAccountOutList(Map<String,Object> params){
		return pfBankAccountMapper.getBankAccountOutList(params);
	}
	
	/**
	 * 通过公司ID获取银行账户信息
	 *getBankAccount
	 *@param companyId
	 *@return
	 *List<BankAccount>
	 *@author mishengliang
	 *2016-4-5下午4:28:39
	 */
	public List<PfBankAccount> getBankAccount(Integer companyId){
		return pfBankAccountMapper.getBankAccount(companyId);
	}
	
	/**
	 * 删除指定银行账户
	*deleteBankAccount
	*@param params
	*void
	*@author mishengliang
	*2016-4-8下午6:32:24
	 */
	public void deleteBankAccount(Map<String, Object> params){
		pfBankAccountMapper.deleteBankAccount(params);
	}
	
	/**
	 * 通过银行账户ID更新信息
	*updateBankAccountByAccountId
	*@param params
	*void
	*@author mishengliang
	*2016-4-8下午6:32:58
	 */
	public void updateBankAccountByAccountId(Map<String, Object> params){
		pfBankAccountMapper.updateBankAccountByAccountId(params);
	}
	
	/**
	 * 添加银行账户
	*addBankAccount
	*@param params
	*void
	*@author mishengliang
	*2016-4-8下午6:33:46
	 */
	public void addBankAccount(Map<String, Object> params){
		pfBankAccountMapper.addBankAccount(params);
	}
	
	/**重置默认标记
	 * yukai
	 * @param params
	 */
	public void resetDefaultId(Map<String, Object> params){
		pfBankAccountMapper.resetDefaultId(params);
	}

	public void deleteBankAccounts(Map<String, Object> params) {
		String arrayStr=params.get("account_ids").toString();
		String[] arrayaccount_id=arrayStr.split(",");
		for(int i=0;i<arrayaccount_id.length;i++){
			params.put("accountId", arrayaccount_id[i]);
			pfBankAccountMapper.deleteBankAccount(params);
		}
	}
}
