package com.outsideasy.ws.ehr;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.beans.factory.annotation.Autowired;

import util.Const;
import util.MyJsonUtil;

import com.outsideasy.ws.common.vo.CXFResponse;

import common.account.service.AppSubAccountService;

@WebService
@SOAPBinding(style = Style.RPC)
public class EhrServiceImpl implements EhrServiceInter {
	@Autowired
	private AppSubAccountService appSubAccountService;
	
	public String forbidSubAccount(String empNo) {
		appSubAccountService.updateSubAccountStatu(empNo);
		CXFResponse<String> res=new CXFResponse<String>();
		res.setSuccess(Const.SOAP_TRUE);
		return MyJsonUtil.obj2string(res);
	}

}
