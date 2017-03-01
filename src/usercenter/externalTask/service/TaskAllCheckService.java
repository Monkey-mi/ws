package usercenter.externalTask.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usercenter.externalTask.data.TaskAllCheckMapper;
import usercenter.externalTask.model.TaskAllCheck;


@Service
public class TaskAllCheckService {
	@Autowired
	private TaskAllCheckMapper mapper;
	@Autowired
	private AppTaskService appTaskService;


	public List<TaskAllCheck> getTaskAllCheckList(Map<String,Object> params) {
		return mapper.getTaskAllCheckList(params);
	}
	public void addTaskAllCheck(TaskAllCheck taskAllCheck) {
		String rwdh = taskAllCheck.getrwdh();
		Integer Tid = appTaskService.getTidByRwdh(rwdh);
		taskAllCheck.sett_id(Tid);
		mapper.addTaskAllCheck(taskAllCheck);
		
		//更新任务单号的质检数量和合格数量
		Map<String, Object> taskMap  = appTaskService.getTaskMapByRwdh(rwdh);
		if(taskMap != null){
			BigDecimal confirmedQty =  taskAllCheck.getjysl().add((BigDecimal) (taskMap.get("confirmed_qty") == null?new BigDecimal(0):taskMap.get("confirmed_qty")));
			BigDecimal qualifiedQty =  taskAllCheck.gethgsl().add((BigDecimal) (taskMap.get("qualified_qty") == null?new BigDecimal(0):taskMap.get("qualified_qty")));
			taskMap.put("confirmedQty", confirmedQty);
			taskMap.put("qualifiedQty", qualifiedQty);
			taskMap.put("rwdh", rwdh);
			appTaskService.updateTaskInfoByRwdh(taskMap);
		}
	}
	public void updateTaskAllCheck(TaskAllCheck[] arr) {
		for(TaskAllCheck obj: arr) {
			mapper.updateTaskAllCheck(obj);
		}
	}
	public void deleteTaskAllCheck(TaskAllCheck[] arr) {
		for(TaskAllCheck obj: arr) {
			mapper.deleteTaskAllCheck(obj);
		}
	}
}
