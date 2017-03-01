package test.platform.mes.wxdata;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import common.account.service.AppSubAccountService;

import test.base.BaseTest;

public class EhrServiceTest extends BaseTest {
	@Autowired
	private AppSubAccountService appSubAccountService;
	
	@Test
	public void updateSubAccountStatuTest(){
		String empNoString = "111111";
		appSubAccountService.updateSubAccountStatu(empNoString);
	}
}
