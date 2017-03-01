package platform.mes.wxdata.data;

import java.util.List;
import java.util.Map;

import platform.mes.wxdata.model.PfProcessSection;
import platform.mes.wxdata.model.PfTask;
import platform.mes.wxdata.model.PfTaskBom;
import platform.mes.wxdata.model.PfTaskOutput;
import platform.mes.wxdata.model.PfTaskProcess;
import platform.mes.wxdata.model.TaskFile;



public interface MesTaskMapper {
	/**按照任务单号和公司编号，获取未终止的任务单
	 * 参数：send_company rwdh
	 * */
	public List<PfTask> getMesTaskListByT_id(Map<String,Object> params);
	/**
	* @Description: 新增任务单
	* @param 任务单对象
	* 隐藏返回信息：主键反写到对象中
	* @author xufeng
	* @date 2016-4-15 
	*/
	public void addPfTask(PfTask obj);
	/**
	* @Description: 按照任务单号和公司编号，获取未终止的任务单数量
	* @param send_company rwdh
	* @return 未终止的任务单数量
	* @author xufeng
	* @date 2016-4-15 
	*/
	public int getCountOfPfTask(Map<String,Object> params);
	/**按照任务单主键 获取工段产量
	 * 参数：t_id 任务单ID
	 * locked 是否上锁
	 * */
	public List<PfTaskOutput> getPfTaskOutputByT_id(Map<String,Object> params);
	
	/**
	* @Description: 更新为产量记录为锁定
	* @param record_id
	* @author xufeng
	* @date 2016-9-19 
	*/
	public void updatePfTaskOutput2locked(Map<String,Object> params);
	/**新增工序*/
	public void addPfTaskProcess(PfTaskProcess obj);
	
	/**新增工段*/
	public void addPfProcessSection(PfProcessSection obj);
	/**新增材料bom*/
	public void addPfTaskBom(PfTaskBom obj);
	
	public void addTaskFile(TaskFile obj);
	/**
	 @Description:跟新指导文件上传状态
	 * @author chenlong
	 * @param params map
	 * @date 2016-5-19
	 */
	public void updateOperator_file(Map<String,Object> map);
	
	/**
	* @Description: 按照文件id获取文件
	* @param tf_id
	* @return
	* @author xufeng
	* @date 2016-11-30 
	*/
	public TaskFile getTaskFileBytf_id(Map<String,Object> params); 
	
	/**
	* @Description: 更新文件 为逻辑删除
	* @param tf
	* @author xufeng
	* @date 2016-11-30 
	*/
	public void updateTaskFile2LogicDel(TaskFile tf);
	
	/**
	* @Description: 获取文件数量，用于判断文件是够已经存在
	* @param t_id任务单编号 
	* file_name 文件名
	* file_type 文件类型
	* @return
	* @author xufeng
	* @date 2016-11-30 
	*/
	public int getTaskFileCount(Map<String,Object> params);
	
	/**
	* @Description:更新任务单中的图片ID
	* MesTaskMapper
	* updateProductPicByTid
	* @param params void
	* @author mishengliang
	* 2016-12-15 下午2:59:45
	*/
	public void updateProductPicByTid(Map<String,Object> params);
}
