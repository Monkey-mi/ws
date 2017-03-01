package usercenter.externalTask.service;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.mes.wxdata.model.TaskFile;
import usercenter.externalTask.data.TaskFileMapper;

import common.mongodb.model.VideoFileBean;
import common.mongodb.service.FileOptService;



@Service
public class TaskFileService {
	@Autowired
	private TaskFileMapper mapper;
	@Autowired
	private FileOptService fileService;

	/**
	 * 增加视频文件信息
	 * @param arr
	 * @throws Exception 
	 * @author chenlong
	 */
	public Map<String,Object>  addTaskViewFile(TaskFile arr,File file) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		VideoFileBean fileBean = fileService.SaveVideoFile(file);
		 arr.setObject_id(fileBean.getImage_file());//把存储mongoDb的图片文件序号存到数据库中		
		 arr.setView_no(fileBean.getVideo_file());//把存储的视频放入		
         Date date = new Date();
         arr.setFile_time(date);
         map.put("viewNo", fileBean.getImage_file()); //把存放的图片返回       
		 mapper.addTaskViewFile(arr);//存入mysql
		 map.put("tf_id",arr.getTf_id());
		 map.put("filename", arr.getFile_name());
		 map.put("file_time", date);
		 return map;
	}
}
