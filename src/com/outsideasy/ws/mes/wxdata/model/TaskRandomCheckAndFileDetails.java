package com.outsideasy.ws.mes.wxdata.model;

import usercenter.externalTask.model.TaskRandomCheck;

public class TaskRandomCheckAndFileDetails {
	private AttachedFileWithParams af;
	private TaskRandomCheck taskRandomCheck;
	
	public AttachedFileWithParams getAf() {
		return af;
	}
	public void setAf(AttachedFileWithParams af) {
		this.af = af;
	}
	public TaskRandomCheck getTaskRandomCheck() {
		return taskRandomCheck;
	}
	public void setTaskRandomCheck(TaskRandomCheck taskRandomCheck) {
		this.taskRandomCheck = taskRandomCheck;
	}
	
	@Override
	public String toString() {
		return "TaskRandomCheckAndFileDetails [af=" + af + ", taskRandomCheck="
				+ taskRandomCheck + "]";
	}
}
