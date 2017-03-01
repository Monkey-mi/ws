package com.outsideasy.ws.mes.wxdata;

import javax.jws.WebService;

import com.outsideasy.ws.mes.wxdata.model.AttachedFileWithParams;
import com.outsideasy.ws.mes.wxdata.model.TaskRandomCheckAndFileDetails;

@WebService
public interface MesTaskServiceInter {
	
	/**
	* @Description: 新增 发送者的  平台任务单 以及关联的工段 工序 bom材料。
	* @param json格式的 信息主体 TaskAndGX,该对象包含了 任务单信息，工序 ，工段和bom材料信息
	* @return json格式的 CXFResponse<PfTask>
	* 如果success=true，新增成功，并返回PfTask对象；
	* 如果success=false，新增失败，并返回失败信息errorMessage；
	*/
	public String addMesTaskAndTaskGx(String jsonobj);
	/**
	* @Description: 根据发送者的任务单号获取工段产量
	* @param rwdh
	* @return json格式的 CXFResponse<PfTaskOutput>
	* 如果success=true，新增成功，并返回List<PfTaskOutput>；
	* 如果success=false，新增失败，并返回失败信息errorMessage；
	*/
	public String getPfTaskOutputList(String rwdh);
	/**
	* @Description: 根据发送者的任务单号获取平台任务单
	* @param rwdh
	* @return json格式的 CXFResponse<PfTask>
	* 如果success=true，新增成功，并返回PfTask对象；
	* 如果success=false，新增失败，并返回失败信息errorMessage；
	*/
	public String getMesTaskByrwdh(String rwdh);
	/**
	* @Description:根据发送者的任务单号,获取前一天的,有变化的，未上锁的工段产量。
	* 对于已经读取的数据，上锁
	* @param rwdh
	* @return
	* @author xufeng
	* @date 2016-9-18 
	*/
	public String getPfTaskOutputListByAutoSync(String rwdh);
	/**
	* @Description: 新增任务单附件
	* @param 
	* @return jsonMap
	* 如果成功，success:true，并且返回tf_id
	* 失败，success:false，并且返回message
	* @author xufeng
	* @date 2016-11-28 
	*/
	public String addTaskFileFromWS(AttachedFileWithParams af) throws Exception;
	/**
	* @Description: 逻辑删除任务单文件
	* @param jsonMap
	* @return jsonMap
	* 如果成功，success:true
	* 失败，success:false，并且返回message
	* @author xufeng
	* @date 2016-11-29 
	*/
	public String logicDelTaskFileByWS(String jsonMap);

	/**
	 * @Description: 增加质检记录<包括全检记录和不合格记录>
	 * MesTaskServiceInter
	 * addTaskCheckDetails
	 * @param checkDetails
	 * @return String
	 * @author mishengliang
	 * 2016-12-1 下午4:34:51
	 */
	public String addTaskCheckDetails(String checkDetails);
	
	/**
	* @Description:增加抽检记录
	* MesTaskServiceInter
	* addTaskRandomCheck
	* @param randomCheckDetails
	* @return String
	* @author mishengliang
	* 2016-12-9 下午3:56:41
	*/
	public String addTaskRandomCheck(TaskRandomCheckAndFileDetails taskRandomCheckAndFileDetails) throws Exception;
	
	
	/**
	* @Description:作废抽检记录
	* MesTaskServiceInter
	* deleteRandomCheck
	* @param randomCheckId
	* @return String
	* @author mishengliang
	* 2016-12-9 下午5:25:17
	*/
	public String deleteRandomCheck(String randomCheckId);
}
