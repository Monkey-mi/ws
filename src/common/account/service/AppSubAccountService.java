package common.account.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.mes.wxdata.model.PfTaskOutput;
import util.Const;
import util.MyJsonUtil;

import com.outsideasy.ws.common.vo.CXFResponse;

import common.account.data.AppSubAccountMapper;

@Service
public class AppSubAccountService {
	@Autowired
	private AppSubAccountMapper appSubAccountMapper;
	
	public void updateSubAccountStatu(String empNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enabled", 1);//0:可用 1：禁用
		map.put("empNo", empNo);
		appSubAccountMapper.updateSubAccountStatu(map);
	}
}
