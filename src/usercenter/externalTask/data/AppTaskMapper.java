package usercenter.externalTask.data;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import usercenter.externalTask.model.AppTask;


public interface AppTaskMapper {
	public List<AppTask> getAppTaskList(Map<String,Object> params);
	public void addAppTask(AppTask obj);
	public void updateAppTask(AppTask obj);
	public void deleteAppTask(AppTask obj);
	
	/**
	 * AppTaskMapper
	 * getTidByRwdh
	 * @param rwdh
	 * @return Integer
	 * @author mishengliang
	 * 2016-12-2 上午9:25:01
	 */
	public Integer getTidByRwdh(@Param(value="rwdh")String rwdh);
	
	/**
	* @Description:根据任务单号获取任务信息
	* AppTaskMapper
	* getTaskMapByRwdh
	* @param rwdh
	* @return Integer
	* @author mishengliang
	* 2017-1-2 上午10:29:56
	*/
	public Map<String, Object> getTaskMapByRwdh(@Param(value="rwdh")String rwdh);
	
	/**
	* @Description:根据任务单号更新任务信息
	* AppTaskMapper
	* updateTaskInfoByRwdh
	* @param map void
	* @author mishengliang
	* 2017-1-2 上午10:34:28
	*/
	public void updateTaskInfoByRwdh(Map<String, Object> map);
}
