package usercenter.externalTask.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import usercenter.externalTask.data.AppTaskMapper;
import usercenter.externalTask.model.AppTask;


@Service
public class AppTaskService {
	@Autowired
	private AppTaskMapper mapper;


	public List<AppTask> getAppTaskList(Map<String,Object> params) {
		return mapper.getAppTaskList(params);
	}
	public void addAppTask(AppTask[] arr) {
		for(AppTask obj: arr) {
			mapper.addAppTask(obj);
		}
	}
	public void updateAppTask(AppTask[] arr) {
		for(AppTask obj: arr) {
			mapper.updateAppTask(obj);
		}
	}
	public void deleteAppTask(AppTask[] arr) {
		for(AppTask obj: arr) {
			mapper.deleteAppTask(obj);
		}
	}
	
	/**
	 * @Description: 通过任务单号获取Tid
	 * AppTaskService
	 * getTidByRwdh
	 * @param rwdh
	 * @return Integer
	 * @author mishengliang
	 * 2016-12-2 上午9:23:20
	 */
	public Integer getTidByRwdh(String rwdh){
		return mapper.getTidByRwdh(rwdh);
	}
	
	/**
	* @Description:根据任务单号获取任务信息
	* AppTaskService
	* getTaskMapByRwdh
	* @param rwdh
	* @return Map<String,Object>
	* @author mishengliang
	* 2017-1-2 上午10:32:20
	*/
	public Map<String, Object> getTaskMapByRwdh(String rwdh){
		return mapper.getTaskMapByRwdh(rwdh);
	}
	
	/**
	* @Description:根据任务单号更新任务信息
	* AppTaskService
	* updateTaskInfoByRwdh
	* @param map void
	* @author mishengliang
	* 2017-1-2 上午10:37:00
	*/
	public void updateTaskInfoByRwdh(Map<String, Object> map){
		 mapper.updateTaskInfoByRwdh(map);
	}
}
