package common.account.data;

import java.util.Map;

public interface AppSubAccountMapper {
	/**
	* @Description:通过工号更新子账号禁用状态
	* AppSubAccount
	* updateSubAccountStatu
	* @param map void
	* @author mishengliang
	* 2016-12-29 上午11:34:51
	*/
	public void updateSubAccountStatu(Map<String, Object> map);
}
