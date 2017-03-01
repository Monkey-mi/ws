package com.outsideasy.ws.mes.wxdata;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.WebServiceContext;

import org.springframework.beans.factory.annotation.Autowired;

import platform.mes.wxdata.model.PfTask;
import platform.mes.wxdata.model.PfTaskOutput;
import platform.mes.wxdata.model.TaskAndGX;
import platform.mes.wxdata.model.TaskFile;
import platform.mes.wxdata.service.MesTaskService;
import usercenter.externalTask.model.TaskAllCheck;
import usercenter.externalTask.model.TaskAllcheckUnqudetails;
import usercenter.externalTask.model.TaskRandomCheck;
import usercenter.externalTask.service.TaskAllCheckService;
import usercenter.externalTask.service.TaskAllcheckUnqudetailsService;
import usercenter.externalTask.service.TaskRandomCheckService;
import util.Const;
import util.FFMpegUtil;
import util.MyJsonUtil;
import util.XmlUtil;

import com.outsideasy.ws.common.vo.CXFResponse;
import com.outsideasy.ws.mes.wxdata.model.AttachedFileWithParams;
import com.outsideasy.ws.mes.wxdata.model.TaskCheckAndUnquDetails;
import com.outsideasy.ws.mes.wxdata.model.TaskRandomCheckAndFileDetails;
import common.sysmodule.model.WsIdentity;
import common.sysmodule.service.WsIdentityService;

@WebService
@SOAPBinding(style = Style.RPC)
public class MesTaskServiceImpl implements MesTaskServiceInter {
	@Autowired
	private MesTaskService mesTaskService;
	@Resource
	private WebServiceContext context;
	@Autowired
	private WsIdentityService wsIdentityService;
	@Autowired
	private TaskAllCheckService taskAllCheckService;
	@Autowired
	private TaskAllcheckUnqudetailsService taskAllcheckUnqudetailsService;	
	@Autowired
	private TaskRandomCheckService taskRandomCheckService;
	
	public String getPfTaskOutputList(String rwdh){
		Map<String,Object> params2=new HashMap<String,Object>();
		params2.put("rwdh", rwdh);
		params2.put("send_company", getsendInfo().getCompany_id());
		List<PfTaskOutput> list=mesTaskService.getPfTaskOutputList(params2);
		CXFResponse<PfTaskOutput> res=new CXFResponse<PfTaskOutput>();
		if(list!=null && list.size()>0){
			res.setSuccess(Const.SOAP_TRUE);
			res.setList(list);
		}else{
			res.setSuccess(Const.SOAP_FALSE);
			res.setErrorMessage("同步失败，生产方未在平台中录入产量");
		}
		return MyJsonUtil.obj2string(res);
	}
	
	public String addMesTaskAndTaskGx(String jsonobj) {		
		int company_id=getsendInfo().getCompany_id();
		TaskAndGX mtAndGx=MyJsonUtil.str2obj(jsonobj, TaskAndGX.class);
		CXFResponse<PfTask> res=new CXFResponse<PfTask>();
		res.setSuccess(Const.SOAP_TRUE);
		if(mtAndGx.getTask()==null){
			res.setErroeResponseInfo("发送失败，发送的任务单为空");
		}else if(mtAndGx.getGxlist()==null || (mtAndGx.getGxlist()!=null && mtAndGx.getGxlist().size()==0)){
			res.setErroeResponseInfo("发送失败，发送的工序为空");
		}else if(mtAndGx.getBomlist()==null || (mtAndGx.getBomlist()!=null && mtAndGx.getBomlist().size()==0)){
			res.setErroeResponseInfo("发送失败，发送的bom材料为空");
		}else{
			mesTaskService.addMesTaskAndTaskGx(res,company_id,mtAndGx);
		}
		return MyJsonUtil.obj2string(res);
	}

	
	public String getMesTaskByrwdh(String rwdh) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("rwdh", rwdh);
		params.put("send_company", getsendInfo().getCompany_id());
		PfTask task=mesTaskService.getMesTaskListByT_id(params);
		CXFResponse<PfTask> res=new CXFResponse<PfTask>();
		if(task!=null){
			res.setObj(task);
			res.setSuccess(Const.SOAP_TRUE);
		}else{
			res.setErrorMessage("获取任务单失败，平台中没有该任务单");
			res.setSuccess(Const.SOAP_FALSE);
		}
		return MyJsonUtil.obj2string(res);
	}	

	private WsIdentity getsendInfo(){
		Map<String, Object> message = context.getMessageContext();
		String username=XmlUtil.getSOAPUserName(message);
		List<WsIdentity> wslist=wsIdentityService.getEnabledIdentity(username);
		return wslist.get(0);
	}

	public String getPfTaskOutputListByAutoSync(String rwdh) {
		Map<String,Object> params2=new HashMap<String,Object>();
		params2.put("rwdh", rwdh);
		params2.put("send_company", getsendInfo().getCompany_id());
		List<PfTaskOutput> list=mesTaskService.getUnlockedPfTaskOutputList(params2);
		
		CXFResponse<PfTaskOutput> res=new CXFResponse<PfTaskOutput>();
		if(list!=null && list.size()>0){
			res.setSuccess(Const.SOAP_TRUE);
			res.setList(list);
			mesTaskService.updatePfTaskOutput2locked(list);
		}else{
			res.setSuccess(Const.SOAP_FALSE);
			res.setErrorMessage("同步失败，生产方未在平台中录入产量");
		}
		return MyJsonUtil.obj2string(res);
	}

	public String addTaskFileFromWS(AttachedFileWithParams af) throws Exception {
		Map<String,Object> res=new HashMap<String,Object>();
		res.put("success", Const.SOAP_TRUE);
		if(af==null){
			res.put("success", Const.SOAP_FALSE);
			res.put("message", "没有数据");
		}else{
			String fname=af.getFile_name();
            if(fname.indexOf(",")>0){
            	res.put("success", Const.SOAP_FALSE);
            	res.put("message", "文件名中存在非法字符(英文逗号),请先去除后上传");
            	return MyJsonUtil.obj2string(res);
            }
           if(fname.length()>80){
        	   res.put("success", Const.SOAP_FALSE);
        	   res.put("message", "文件名过长，超过80个字符");
           	   return MyJsonUtil.obj2string(res);
           } 
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("originalFilename", af.getFile_name());
			WsIdentity send_company=getsendInfo();
			params.put(Const.COM_ID, send_company.getCompany_id());
			params.put(Const.COM_NAME, send_company.getCompany_name());
			params.put(Const.USERNAME, "MES");
			params.put(Const.USE_TYPE, af.getFile_type());
			if(af.getFile_type()==8 || af.getFile_type()==7){//7:任务单产品主图; 8:任务单产品图片;
				params.put(Const.ISIMG, 1);
				//判断是否图片
				boolean flag=false;//默认不 是图片
	            //获取文件后缀，与传过来的参数file_name重新组装文件名
	            if(fname.indexOf(".")>0){//有后缀  XX.jpg  XX.RAR
	            	String this_suffix=fname.substring(fname.lastIndexOf("."));
	            	for(String suffix:Const.imgArray){
	            		if(suffix.equalsIgnoreCase(this_suffix)){
	            			 params.put(Const.ISIMG, 1);
	            			flag=true;
	            			break;
	                    }
	            	}          	
	            }
	            if(!flag){
	            	res.put("success", Const.SOAP_FALSE);
	            	res.put("message", "请按照规定的图片格式上传文件"+fname+".");
	            	return MyJsonUtil.obj2string(res);
	        	}
			}if(af.getFile_type() == 3){//3:工艺视频文件;
				params.put(Const.ISIMG, 0);
				//判断是否视频
				boolean flag=false;//默认不 是视频
				if(fname.indexOf(".")>0){
					String this_suffix=fname.substring(fname.lastIndexOf("."));      	
		        	for(String suffix:Const.viewArray){
		        		if(suffix.equalsIgnoreCase(this_suffix)){
		        			flag=true;
		        			break;
		                }
		        	} 
				 }
		        if(!flag){
		        	res.put("message", "请按照规定的视频格式上传文件");
		        	res.put("success", false);
		        	return MyJsonUtil.obj2string(res);
		        }
		         //判断是否可以转化
				boolean canConvertFLV=FFMpegUtil.isConvertFLV(fname);
				String fileType = fname.substring(fname.lastIndexOf(".")); //截取文件格式
				if(!canConvertFLV){
					res.put("success", false);
					res.put("msg", "无法转换"+fileType+"格式");
					return MyJsonUtil.obj2string(res);
				}
			}else{
				params.put(Const.ISIMG, 0);
			}
			//先判断是够已经上传，按照任务单编号t_id+文件名
			Map<String,Object> params2=new HashMap<String,Object>();
			params2.put("t_id", af.getT_id());
			String filename=af.getFile_name(); 
			params2.put("file_name", filename.substring(0,filename.lastIndexOf(".")));
			params2.put("file_type", af.getFile_type());
			int count=mesTaskService.getTaskFileCount(params2);
			if(count>0){
				res.put("success", Const.SOAP_FALSE);
            	res.put("message", af.getFile_name()+"已经上传");
            	return MyJsonUtil.obj2string(res);
			}
			int tf_id=mesTaskService.addTaskFileFromWS(af,params);
			res.put("tf_id", tf_id);
		}
		return MyJsonUtil.obj2string(res);
	}

	public String logicDelTaskFileByWS(String jsonMap) {
		Map<String,Object> res=new HashMap<String,Object>();
		Map<String,Object> req=MyJsonUtil.str2map(jsonMap);
		res.put("success", Const.SOAP_TRUE);
		
		if(req.get("tf_id")!=null){
			
			TaskFile tf=mesTaskService.getTaskFileBytf_id(req);
			if(tf!=null){
				if(tf.getIs_delete()==0){
					mesTaskService.updateTaskFile2LogicDel(tf);
				}
			}else{
				res.put("success", Const.SOAP_FALSE);
				res.put("message", "文件已删除");
			}
		}else{
			res.put("success", Const.SOAP_FALSE);
			res.put("message", "tf_id不允许为空");
		}
		return MyJsonUtil.obj2string(res);
	}
	
	public String addTaskCheckDetails(String checkDetails){
		TaskCheckAndUnquDetails checkDetailsObj = MyJsonUtil.str2obj(checkDetails, TaskCheckAndUnquDetails.class);
		List<TaskAllcheckUnqudetails> udlist = checkDetailsObj.getUdlist();
		TaskAllCheck taskAllCheck = checkDetailsObj.getTaskAllCheck();
		CXFResponse<PfTask> res=new CXFResponse<PfTask>();
		res.setSuccess(Const.SOAP_TRUE);
		
		if(taskAllCheck==null){
			res.setErroeResponseInfo("全检纪录为空");
			res.setSuccess(Const.SOAP_FALSE);
		}else if(taskAllCheck.getjysl() == null || taskAllCheck.gethgsl() == null){
			res.setErroeResponseInfo("全检纪录中检验数量和合格数量不能为空");
			res.setSuccess(Const.SOAP_FALSE);
		}else{
			taskAllCheckService.addTaskAllCheck(taskAllCheck);
			if(udlist.size()==0){//判断是否有不合格记录
				res.setErroeResponseInfo("不合格记录为空");
			}else{
				for(TaskAllcheckUnqudetails unqudetail:udlist){
					taskAllcheckUnqudetailsService.addTaskAllcheckUnqudetails(unqudetail);
				}
			}
		}
		return MyJsonUtil.obj2string(res);
	}

	

	/**
	* @Description:增加质检记录文件  文件类型：50
	* MesTaskServiceImpl
	* addRandomCheckFileFromWS
	* @param af
	* @return
	* @throws Exception String
	* @author mishengliang
	* 2016-12-9 下午3:50:55
	*/
	private String addRandomCheckFileFromWS(AttachedFileWithParams af,TaskRandomCheck taskRandomCheck) throws Exception {
		Map<String,Object> res=new HashMap<String,Object>();
		res.put("success", Const.SOAP_TRUE);
		if(af==null){
			res.put("success", Const.SOAP_FALSE);
			res.put("message", "没有数据");
		}else{
			String fname=af.getFile_name();
			if(fname.indexOf(",")>0){
				res.put("success", Const.SOAP_FALSE);
				res.put("message", "文件名中存在非法字符(英文逗号),请先去除后上传");
				return MyJsonUtil.obj2string(res);
			}
			if(fname.length()>80){
				res.put("success", Const.SOAP_FALSE);
				res.put("message", "文件名过长，超过80个字符");
				return MyJsonUtil.obj2string(res);
			} 
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("originalFilename", af.getFile_name());
			WsIdentity send_company=getsendInfo();
			params.put(Const.COM_ID, send_company.getCompany_id());
			params.put(Const.COM_NAME, send_company.getCompany_name());
			params.put(Const.USERNAME, "MES");
			params.put(Const.USE_TYPE, af.getFile_type());
			String this_suffix = fname.substring(fname.lastIndexOf("."));
			params.put(Const.ISIMG, 0);
			//先判断是够已经上传，按照任务单编号t_id+文件名
			Map<String,Object> params2=new HashMap<String,Object>();
			params2.put("t_id", af.getT_id());
			String filename=af.getFile_name(); 
			params2.put("file_name", filename.substring(0,filename.lastIndexOf(".")));
			params2.put("file_type", af.getFile_type());
			int count=mesTaskService.getTaskFileCount(params2);
			if(count>0){
				res.put("success", Const.SOAP_FALSE);
				res.put("message", af.getFile_name()+"已经上传");
				return MyJsonUtil.obj2string(res);
			}
			String mongoId = mesTaskService.addFileFromWS(af,params);
			
			//向抽检记录中填充数据
			taskRandomCheck.setFile_name(filename.substring(0,filename.lastIndexOf(".")));
			taskRandomCheck.setSuffix_name(this_suffix);
			taskRandomCheck.setMongodb_id(mongoId);
			taskRandomCheck.setCreate_dt(new Date());
			taskRandomCheckService.addTaskRandomCheck(taskRandomCheck);
			res.put("randomCheckId", taskRandomCheck.getId());//增加抽检记录   randomCheckId
		}
		return MyJsonUtil.obj2string(res);
	}

	public String addTaskRandomCheck(TaskRandomCheckAndFileDetails taskRandomCheckAndFileDetails) throws Exception {
		AttachedFileWithParams af = taskRandomCheckAndFileDetails.getAf();
		TaskRandomCheck taskRandomCheck = taskRandomCheckAndFileDetails.getTaskRandomCheck();
		
		String res = addRandomCheckFileFromWS(af,taskRandomCheck);
		return res;
	}
	
	public String deleteRandomCheck(String randomCheckId){
		Map<String,Object> res=new HashMap<String,Object>();
		if(randomCheckId != null && randomCheckId != ""){
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", Integer.parseInt(randomCheckId));
			taskRandomCheckService.deleteRandomCheck(params);
			res.put("success", Const.SOAP_TRUE);
			res.put("message", "记录已作废");
		}else{
			res.put("success", Const.SOAP_FALSE);
			res.put("message", "没有数据");
		}
		return MyJsonUtil.obj2string(res);
	}
}
