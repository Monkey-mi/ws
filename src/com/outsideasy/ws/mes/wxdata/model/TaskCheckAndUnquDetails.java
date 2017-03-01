package com.outsideasy.ws.mes.wxdata.model;

import java.util.List;

import usercenter.externalTask.model.TaskAllCheck;
import usercenter.externalTask.model.TaskAllcheckUnqudetails;

/**
 * @author mishengliang
 * 2016-12-01
 */
public class TaskCheckAndUnquDetails {
	/*private TaskAllcheckUnqudetails unDetails;*/
	List<TaskAllcheckUnqudetails> udlist;
	private TaskAllCheck taskAllCheck;
	
	public TaskAllCheck getTaskAllCheck() {
		return taskAllCheck;
	}
	public void setTaskAllCheck(TaskAllCheck taskAllCheck) {
		this.taskAllCheck = taskAllCheck;
	}
	public List<TaskAllcheckUnqudetails> getUdlist() {
		return udlist;
	}
	public void setUdlist(List<TaskAllcheckUnqudetails> udlist) {
		this.udlist = udlist;
	}
	@Override
	public String toString() {
		return "TaskCheckAndUnquDetails [udlist=" + udlist + ", taskAllCheck="
				+ taskAllCheck + "]";
	}
}
