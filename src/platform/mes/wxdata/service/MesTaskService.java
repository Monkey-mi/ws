package platform.mes.wxdata.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.service.PfCompanyInfoService;
import platform.mes.wxdata.data.MesTaskMapper;
import platform.mes.wxdata.model.PfProcessSection;
import platform.mes.wxdata.model.PfTask;
import platform.mes.wxdata.model.PfTaskBom;
import platform.mes.wxdata.model.PfTaskOutput;
import platform.mes.wxdata.model.PfTaskProcess;
import platform.mes.wxdata.model.TaskAndGX;
import platform.mes.wxdata.model.TaskFile;
import usercenter.externalTask.service.TaskFileService;
import util.Const;
import util.SRMStringUtil;
import util.SysSerialId;
import util.properties.FFMpeg;

import com.outsideasy.ws.common.vo.CXFResponse;
import com.outsideasy.ws.mes.wxdata.model.AttachedFileWithParams;
import common.mongodb.service.FileOptService;


@Service
public class MesTaskService {
	@Autowired
	private MesTaskMapper mapper;
	@Autowired
	private FileOptService foservice;
	@Autowired
	private PfCompanyInfoService pfCompanyInfoService;
	@Autowired
	private TaskFileService taskFileService;
	
	/**获取未终止的任务单
	 * 参数：任务单号 rwdh send_company公司编号
	 * */
	public PfTask getMesTaskListByT_id(Map<String,Object> params) {
		List<PfTask> list=mapper.getMesTaskListByT_id(params);
		return (list!=null && list.size()>0)?list.get(0):null;
	}
	/**新增平台任务单 以及关联的工段 工序 bom材料。
	 * 
	 * 参数：响应对象res； 发送消息的公司编号company_id ；接收到的信息主体  mtAndGx
	 * mtAndGx包含了  平台任务单数据+工序列表+材料bom列表
	 * 其中工序列表中，每一道工序有  所属 工段字段。*/
	public void addMesTaskAndTaskGx(CXFResponse<PfTask> res,int company_id,TaskAndGX mtAndGx) {
		PfTask send_task=mtAndGx.getTask();
		List<PfTaskProcess> gxlist=mtAndGx.getGxlist();
		//判断任务单是否存在
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("send_company", company_id);
		params.put("rwdh", send_task.getRwdh());
		//获取未终止的任务单数量
		int count=mapper.getCountOfPfTask(params);
		if(count>0){
			res.setSuccess(Const.SOAP_FALSE);
			res.setErrorMessage(send_task.getRwdh()+"已经上传");
		}else{
			//获取公司ID
			params.put("cpyname_cn", send_task.getProducers_company_name());
			Integer cpId = pfCompanyInfoService.getCpynameIdByCpName(params);//查询公司ID
			if(cpId == null){
				res.setSuccess(Const.SOAP_FALSE);
				res.setErrorMessage("生产公司不存在");
			}else{
				//保存
				PfTask newtask=new PfTask();
				//接收发送任务单数据
				newtask.setRwdh(send_task.getRwdh());
				newtask.setProduct_name(send_task.getProduct_name());
				newtask.setTotal_qty(send_task.getTotal_qty());
				newtask.setPlan_start(send_task.getPlan_start());
				newtask.setPlan_complete(send_task.getPlan_complete());
				newtask.setDdh(send_task.getDdh());
				newtask.setScdh(send_task.getScdh());
				newtask.setProducers_company_name(send_task.getProducers_company_name());
				newtask.setBzbh(send_task.getBzbh());
				newtask.setBzmc(send_task.getBzmc());
				newtask.setReceive_company(cpId);
				//其他赋值
				String serial_id=SysSerialId.getNewNextSerialId();
				newtask.setSerial_no(serial_id);
				newtask.setState(5);//等待派单
				newtask.setQc_type(1+"");//默认入库质检
				newtask.setCzsj(new Date());
				//按照username设置发送公司
				newtask.setSend_company(company_id);
				mapper.addPfTask(newtask);
				//从数据中获取工段数据
				Map<String,PfProcessSection> gdmap=new HashMap<String,PfProcessSection>();
				for(int j=0;j<gxlist.size();j++){
					PfTaskProcess temppro=gxlist.get(j);
					if(!gdmap.containsKey(temppro.getGdmc())){
						PfProcessSection sec=new PfProcessSection();
						sec.setSection_name(temppro.getGdmc());
						sec.setSx(gdmap.size()+1);
						sec.setT_id(newtask.getT_id());
						sec.setMjbz(temppro.getMjbz());//赋值末级
						gdmap.put(temppro.getGdmc(), sec);
					}
				}
				//保存工段数据 和 工序数据
				String[] array=new String[gdmap.size()];
				gdmap.keySet().toArray(array);
				for(int i=0;i<array.length;i++){
					PfProcessSection tempsec=gdmap.get(array[i]);
					mapper.addPfProcessSection(tempsec);
					for(int j=0;j<gxlist.size();j++){
						PfTaskProcess temppro=gxlist.get(j);
						if(array[i].equals(temppro.getGdmc())){
							temppro.setT_id(newtask.getT_id());
							temppro.setSec_id(tempsec.getSec_id());
							mapper.addPfTaskProcess(temppro);
						}
					}
				}
				List<PfTaskBom> bomlist=mtAndGx.getBomlist();
				for(int i=0;i<bomlist.size();i++){
					PfTaskBom bom=bomlist.get(i);
					bom.setT_id(newtask.getT_id());
					mapper.addPfTaskBom(bom);
				}
				res.setObj(newtask);
			}
		}
	}
	/**获取指定任务单所有产量
	 * 任务单号 rwdh send_company公司编号 */
	public List<PfTaskOutput> getPfTaskOutputList(Map<String,Object> params) {
		PfTask task=getMesTaskListByT_id(params);
		if(task!=null){
			Map<String,Object> params2=new HashMap<String,Object>();
			params2.put("t_id", task.getT_id());
			return mapper.getPfTaskOutputByT_id(params2);
		}else{
			return null;
		}
		
	}
	/**获取未上锁(就是还没有读取)产量
	 * 任务单号 rwdh send_company公司编号*/
	public List<PfTaskOutput> getUnlockedPfTaskOutputList(Map<String,Object> params) {
		PfTask task=getMesTaskListByT_id(params);
		if(task!=null){
			Map<String,Object> params2=new HashMap<String,Object>();
			params2.put("t_id", task.getT_id());
			params2.put("locked", 0);//未锁定
			return mapper.getPfTaskOutputByT_id(params2);
		}else{
			return null;
		}
		
	}
	/**
	* @Description: 锁定产量
	* @param List<PfTaskOutput>
	* @author xufeng
	* @date 2016-9-19 
	*/
	public void updatePfTaskOutput2locked(List<PfTaskOutput> list){
		Map<String,Object> params=new HashMap<String,Object>();
		for(PfTaskOutput temp:list){
			params.put("record_id", temp.getRecord_id());
			mapper.updatePfTaskOutput2locked(params);
		}
	}
	/**
	* @Description: 获取文件数量，用于判断文件是够已经存在
	* @param t_id任务单编号 
	* file_name 文件名
	* file_type 文件类型
	* @return
	* @author xufeng
	* @date 2016-11-30 
	*/
	public int getTaskFileCount(Map<String,Object> params){
		return mapper.getTaskFileCount(params);
	}
	//mes通过添加文件到平台
	public int addTaskFileFromWS(AttachedFileWithParams af,Map<String,Object> params) throws Exception{
		//String uuid=foservice.SaveFile(af.getFileData().getInputStream(), params);
		Map<String, Object> resMap=fosSaveFile(af, params);
		TaskFile taskFile = new TaskFile();
		if(resMap.get("uuid") != null && resMap.get("viewNo") == null){//图片或文本
			String uuid = resMap.get("uuid").toString();
			String filename=af.getFile_name();
			taskFile.setObject_id(uuid);
			taskFile.setT_id(af.getT_id());
			taskFile.setFile_type(af.getFile_type());//文件类型值
			taskFile.setFile_name(filename.substring(0,filename.lastIndexOf(".")));
			taskFile.setSuffix_name(filename.substring(filename.lastIndexOf(".")));
			Date date = new Date();
			taskFile.setFile_time(date);
			mapper.addTaskFile(taskFile);
			if(af.getFile_type()==7){
				params.put("tid", af.getT_id());
				params.put("productPic", uuid);
				mapper.updateProductPicByTid(params);//更新任务单中的图片mogonid
			}
			if(af.getFile_type()==5){//如果是指导文件的话更新任务单
				updateOperator_file(taskFile);
			}
		}else{//上传视频，在视频上传的操作中已经将任务单文件信息保存到对应表中了，所以不许再做插入表中的操作
			taskFile.setTf_id(Integer.parseInt(resMap.get("tf_id").toString()));
		}
		return taskFile.getTf_id();
	}
	
	/**
	* @Description:视频格式和非视频格式的保存方法
	* MesTaskService
	* fosSaveFile
	* @param af
	* @param params
	* @return
	* @throws Exception String
	* @author mishengliang
	* 2017-1-3 下午4:53:28
	*/
	private Map<String, Object> fosSaveFile(AttachedFileWithParams af,Map<String,Object> params) throws Exception{
		Map<String, Object> repMap = new HashMap<String, Object>();
		String uuid = null;
		if(af.getFile_type() != 3){//图片或文本格式
			uuid=foservice.SaveFile(af.getFileData().getInputStream(), params);
		}else{//视频格式
			//获取视频转码文件配置信息
	    	FFMpeg ffmpeg =FFMpeg.getInstance();
	    	//临时文件夹是否存在
			File originvideo_temp_path=new File(ffmpeg.getOriginvideo_temp_path());
			if(!originvideo_temp_path.exists()){
				originvideo_temp_path.mkdirs();
			}
			//重命名，以防重名
			String serialName = SRMStringUtil.getUUID();
			File tempVideoFile = new File(ffmpeg.getOriginvideo_temp_path()+serialName + af.getFile_type());
			//将上传的原始文件写入一个临时位置
	        InputStream in = af.getFileData().getInputStream();
	        FileOutputStream out =new FileOutputStream(tempVideoFile);
	        
	        byte[] buf = new byte[1024];
	        int len;
	        while ((len = in.read(buf)) > 0) {
	           out.write(buf, 0, len);
	        }
	        in.close();
	        out.close();
	        String filename = af.getFile_name();
			TaskFile taskFile = new TaskFile();
			taskFile.setT_id(af.getT_id()); //任务单id
			taskFile.setFile_type(af.getFile_type());//文件类型值	
	        taskFile.setSuffix_name(filename.substring(filename.lastIndexOf(".")));
	        taskFile.setFile_name(filename.substring(0, filename.indexOf(".")));
	        Map<String,Object> map = taskFileService.addTaskViewFile(taskFile, tempVideoFile);
	        //删除临时文件
	        tempVideoFile.delete();
	        repMap.put("viewNo", map.get("viewNo"));
	        repMap.put("tf_id", map.get("tf_id"));
		}
		repMap.put("uuid", uuid);
		return repMap;
	}
	
	/**
	* @Description:
	* MesTaskService
	* addFileFromWS
	* @param af
	* @param params
	* @return
	* @throws Exception int
	* @author mishengliang
	* 2016-12-9 下午4:13:34
	*/
	public String addFileFromWS(AttachedFileWithParams af,Map<String,Object> params) throws Exception{
		String mongoId=foservice.SaveFile(af.getFileData().getInputStream(), params);
		return mongoId;
	}
	
	/**
	 * 更新任务单的指导文件
	 */
	public void updateOperator_file(TaskFile taskFile){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("t_id", taskFile.getT_id());
		map.put("operator_file", taskFile.getObject_id());
		mapper.updateOperator_file(map);
	}
	/**
	* @Description: 按照文件id获取文件
	* @param tf_id
	* @return
	* @author xufeng
	* @date 2016-11-30 
	*/
	public TaskFile getTaskFileBytf_id(Map<String,Object> params){
		return mapper.getTaskFileBytf_id(params);
	}
	/**
	* @Description: 更新文件 为逻辑删除
	* @param tf
	* @author xufeng
	* @date 2016-11-30 
	*/
	public void updateTaskFile2LogicDel(TaskFile taskFile){
		taskFile.setIs_delete(1);//废弃
		mapper.updateTaskFile2LogicDel(taskFile);
		taskFile.setObject_id("");
		updateOperator_file(taskFile);
		
	}
}
