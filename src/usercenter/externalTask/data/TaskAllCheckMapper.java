package usercenter.externalTask.data;

import java.util.List;
import java.util.Map;

import usercenter.externalTask.model.TaskAllCheck;


public interface TaskAllCheckMapper {
	public List<TaskAllCheck> getTaskAllCheckList(Map<String,Object> params);
	public void addTaskAllCheck(TaskAllCheck obj);
	public void updateTaskAllCheck(TaskAllCheck obj);
	public void deleteTaskAllCheck(TaskAllCheck obj);
}
