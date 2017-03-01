package com.outsideasy.ws.mes.wxdata.model;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  
@XmlAccessorType(XmlAccessType.FIELD)
public class AttachedFileWithParams {

	@XmlMimeType("application/octet-stream")  
    private DataHandler fileData;
	
	private int t_id;
	private String file_name;
	private int file_type;
	public DataHandler getFileData() {
		return fileData;
	}
	public void setFileData(DataHandler fileData) {
		this.fileData = fileData;
	}
	public int getT_id() {
		return t_id;
	}
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public int getFile_type() {
		return file_type;
	}
	public void setFile_type(int file_type) {
		this.file_type = file_type;
	}
	
	
}
