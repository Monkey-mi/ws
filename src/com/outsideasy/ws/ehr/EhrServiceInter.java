package com.outsideasy.ws.ehr;

import javax.jws.WebService;

@WebService
public interface EhrServiceInter {
	/**
	* @Description:子账号禁用
	* EhrServiceInter
	* forbidAccount
	* @param jsonobj
	* @return String
	* @author mishengliang
	* 2016-12-29 上午11:26:59
	*/
	public String forbidSubAccount(String jsonobj);
}
