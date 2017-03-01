package test.platform.mes.wxdata;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import platform.mes.wxdata.data.MesTaskMapper;
import test.base.BaseTest;
import usercenter.externalTask.data.TaskAllCheckMapper;
import usercenter.externalTask.model.TaskAllCheck;
import usercenter.externalTask.model.TaskAllcheckUnqudetails;
import usercenter.externalTask.service.TaskAllCheckService;
import usercenter.externalTask.service.TaskAllcheckUnqudetailsService;

public class MesTaskServiceTest extends BaseTest {
	@Autowired
	private MesTaskMapper mapper;
	@Autowired
	private TaskAllcheckUnqudetailsService taskAllcheckUnqudetailsService;
	@Autowired
	private TaskAllCheckService taskAllCheckService;
	
	@Test
	public void updateProductPicByTidTest(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tid", 60);
		params.put("productPic", 8888);
		mapper.updateProductPicByTid(params);
	}
	
	@Test
	public void addTaskAllCheckTest(){
		TaskAllCheck taskAllCheck = new TaskAllCheck();
		taskAllCheck.setrwdh("764998-1");
		taskAllCheck.sett_id(21);
		taskAllCheck.setjldh("12969197");
		taskAllCheck.setjldh("02");
		taskAllCheck.setjlrq(new Date());
		taskAllCheck.setjysl(new BigDecimal(1256));
		taskAllCheck.sethgsl(new BigDecimal(1163));
		taskAllCheck.setczym("姜爱");
		taskAllCheck.setsource_type(0);
		taskAllCheck.setcreate_dt(new Date());
		
		taskAllCheckService.addTaskAllCheck(taskAllCheck);
	}
	
	@Test
	public void addTaskAllcheckUnqudetails(){
		TaskAllcheckUnqudetails taskAllcheckUnqudetails = new TaskAllcheckUnqudetails();
		taskAllcheckUnqudetails.setjldh("001");
		taskAllcheckUnqudetails.setjlxh(new BigDecimal(123));
		taskAllcheckUnqudetails.setxmmc("002");
		taskAllcheckUnqudetails.setbhgs(new BigDecimal(654321));
		
		taskAllcheckUnqudetailsService.addTaskAllcheckUnqudetails(taskAllcheckUnqudetails);
	}

}
