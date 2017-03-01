package platform.company.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import platform.basicdata.user.data.PfRegAccoutMapper;
import platform.company.data.PfCompanyInfoMapper;
import platform.company.data.PfCompanyMapper;
import platform.company.model.PfCompanySimpleInfo;
import usercenter.saleManage.data.AdmittanceInviteMapper;
import usercenter.saleManage.model.AccessRecord;
import usercenter.saleManage.model.AdmittanceInvite;
import usercenter.saleManage.model.CustomerFiles;
import usercenter.subAccount.model.SubAccount;
import usercenter.supplierFiles.data.SupplierFilesMapper;
import usercenter.supplierFiles.data.SupplierOwnerInfoMapper;
import usercenter.supplierFiles.data.SupplierRegaccountInfoMapper;
import usercenter.supplierFiles.model.SupplierFiles;
import usercenter.supplierFiles.model.SupplierOwnerInfo;
import usercenter.supplierFiles.model.SupplierRegaccountInfo;
import util.DataTrans;
import util.MyJsonUtil;

import com.outsideasy.ws.common.vo.CXFResponse;
import com.outsideasy.ws.erp.supplier.vo.SupplierFileIn;
import com.outsideasy.ws.erp.supplier.vo.SupplierFileOut;

@Service
public class PfCompanyInfoService {
	@Autowired
	private PfCompanyInfoMapper mapper;
	@Autowired
	private SupplierFilesMapper supplierFilesMapper;
	@Autowired
	private CompanyForPlateFormService cfpService;
	@Autowired
	private SupplierRegaccountInfoMapper sriMapper;
	@Autowired
	private SupplierOwnerInfoMapper soiMapper;
	@Autowired
	private PfCompanyMapper pfMapper;
	@Autowired
	private AdmittanceInviteMapper aiMapper;
	@Autowired
	private PfRegAccoutMapper pfRegAccoutMapper;
	/**
	 *ws获取供应商信息 新 */
	public List<CustomerFiles> getPfCustomerFilesList(Map<String, Object> params) {
		return mapper.getPfCustomerFilesList(params);
	}
	/**
	 *ws获取供应商信息 */
	public List<SupplierFileOut> getSupplierFileOutList(Map<String, Object> params) {
		return mapper.getSupplierFileOutList(params);
	}
	/**
	 *ws获取供准入申请信息*/
	public List<SupplierFileOut> getAccessApplicationRecord(Map<String, Object> params) {
		return mapper.getAccessApplicationRecord(params);
	}
	/**ws更新供应商信息
	 * updateSupplierFileByWS*/
	public void updateSupplierFileByWS(SupplierFileIn[] arr){
		for(SupplierFileIn obj: arr) {
			mapper.updateSupplierFileByWS(obj);
		}
	}
	
	/**ws批量更新供应商状态
	 * updateSupplierFileStateByWS*/
	public void updateSupplierFileStateByWS(Map<String, Object> params){
		mapper.updateSupplierFileStateByWS(params);
	}
	
	/**
	 * @Description:新增供应商
	 * SupplierFilesService
	 * addSupplierFiles
	 * @param params void
	 * @author wuqia
	 * 2016-8-10 下午2:46:16
	 */
	@Transactional
	public void addSupplierFiles(Map<String, Object> params) {
		Map<String, Object> companyParams=new HashMap<String, Object>();
		Map<String, Object> supplierParams=new HashMap<String, Object>();
		Map<String, Object> brotherParams=new HashMap<String, Object>();
		Map<String, Object> inviteParams=new HashMap<String, Object>();
		Map<String, Object> subSupplierParams=new HashMap<String, Object>();
		String companyStr=params.get("companyStr").toString();
		int companyId=DataTrans.transToIntegerFromString(params.get("companyId").toString());
		int reg_id=DataTrans.transToIntegerFromString(params.get("login_id").toString());
		int login_type=1;
		String[] companyArray=companyStr.split(";");
		PfCompanySimpleInfo companySimpleInfo1=pfMapper.getCompanyBaseInfo(companyId);
		for (int i = 0; i < companyArray.length; i++) {
			companyParams.put("companyName", companyArray[i]);
			PfCompanySimpleInfo companySimpleInfo= cfpService.getSupplierByName(companyParams).get(0);//根据企业名称查company_id,cpyname_cn
			brotherParams.put("company_id", companySimpleInfo.getCompany_id());
			if (login_type==0) {//主账号
				brotherParams.put("reg_id", reg_id);
			}
			else if (login_type==1) {//子账号
				brotherParams.put("sa_id", reg_id);
			}
			SupplierFiles supplierFiles=supplierFilesMapper.checkSupplierOfBrother(brotherParams);
			if (supplierFiles!=null) {
				if(supplierFiles.getFile_status()==0){//是兄弟公司现有供应商
					inviteParams.put("supplier_id", supplierFiles.getSupplier_id());
					inviteParams.put("sender_id", companyId);
					inviteParams.put("accepter_id",  companySimpleInfo.getCompany_id());
					inviteParams.put("invite_status", 0); 
					inviteParams.put("is_fast", 1); 
					aiMapper.addAdmittanceInvite(inviteParams);//新增邀请记录
				}else {
					supplierParams.put("supplier_id", supplierFiles.getSupplier_id());
					supplierParams.put("company_id", companyId);
					SupplierOwnerInfo supplierOwnerInfo=soiMapper.getSupplierOwnerInfoByCompanyId(supplierParams);
					if(supplierOwnerInfo!=null){//是我的备选供应商
						inviteParams.put("supplier_id", supplierFiles.getSupplier_id());
						inviteParams.put("sender_id", companyId);
						inviteParams.put("accepter_id",  companySimpleInfo.getCompany_id());
						inviteParams.put("invite_status", 0); 
						inviteParams.put("is_fast", 0); 
						aiMapper.addAdmittanceInvite(inviteParams);//新增邀请记录
					}else {//不是我的备选供应商
						SupplierOwnerInfo supplierOwnerInfo1=new SupplierOwnerInfo();
						supplierOwnerInfo1.setSupplier_id(supplierFiles.getSupplier_id());
						supplierOwnerInfo1.setCompany_id(companyId);
						supplierOwnerInfo1.setOwner_cpyname(companySimpleInfo1.getCpyname_cn());
						soiMapper.addSupplierOwnerInfo(supplierOwnerInfo1);//供应商与所有者公司建立联系
						inviteParams.put("supplier_id", supplierFiles.getSupplier_id());
						inviteParams.put("sender_id", companyId);
						inviteParams.put("accepter_id",  companySimpleInfo.getCompany_id());
						inviteParams.put("invite_status", 0); 
						inviteParams.put("is_fast", 0); 
						aiMapper.addAdmittanceInvite(inviteParams);//新增邀请记录
					}
				}
			}else {//不是兄弟公司供应商
				supplierParams.put("company_id", companySimpleInfo.getCompany_id());
				supplierParams.put("supplier_cpyname", companySimpleInfo.getCpyname_cn());
				supplierParams.put("file_status", 1);
				supplierParams.put("source_type", 0);
				supplierFilesMapper.addSupplierFiles(supplierParams);//新增备选供应商
				int supplier_id=DataTrans.transToIntegerFromString(supplierParams.get("supplier_id").toString());//获取新增的备选供应商的主键
				if (login_type==0) {//主账号
					SupplierRegaccountInfo supplierRegaccountInfo=new SupplierRegaccountInfo();
					supplierRegaccountInfo.setSupplier_id(supplier_id);
					supplierRegaccountInfo.setReg_id(reg_id);
					sriMapper.addSupplierRegaccountInfo(supplierRegaccountInfo);//供应商与所有者主账号建立联系
				}
				else if (login_type==1) {//子账号
					subSupplierParams.put("supplier_id", supplier_id);
					subSupplierParams.put("is_delete", 0);
					subSupplierParams.put("sa_id", reg_id);
					sriMapper.addSubaccountSupplierInfo(subSupplierParams);//供应商与所有者子账号建立联系
					SubAccount subAccount=sriMapper.getSubAccountBysa_id(subSupplierParams);//根据子账号ID找到主账号ID
					SupplierRegaccountInfo supplierRegaccountInfo=new SupplierRegaccountInfo();
					supplierRegaccountInfo.setSupplier_id(supplier_id);
					supplierRegaccountInfo.setReg_id(subAccount.getReg_id());
					sriMapper.addSupplierRegaccountInfo(supplierRegaccountInfo);//供应商与所有者主账号建立联系
				}
				SupplierOwnerInfo supplierOwnerInfo=new SupplierOwnerInfo();
				supplierOwnerInfo.setSupplier_id(supplier_id);
				supplierOwnerInfo.setCompany_id(companyId);
				supplierOwnerInfo.setOwner_cpyname(companySimpleInfo1.getCpyname_cn());
				soiMapper.addSupplierOwnerInfo(supplierOwnerInfo);//供应商与所有者公司建立联系
				inviteParams.put("supplier_id", supplier_id);
				inviteParams.put("sender_id", companyId);
				inviteParams.put("accepter_id",  companySimpleInfo.getCompany_id());
				inviteParams.put("invite_status", 0); 
				inviteParams.put("is_fast", 0); 
				aiMapper.addAdmittanceInvite(inviteParams);//新增邀请记录
			}
		}
	}
	@Transactional
	public String checkCompany(Map<String, Object> params) {
		Map<String, Object> companyParams=new HashMap<String, Object>();
		Map<String, Object> supplierParams=new HashMap<String, Object>();
		Map<String, Object> brotherParams=new HashMap<String, Object>();
		String companyStr=params.get("companyStr").toString();
		int companyId=DataTrans.transToIntegerFromString(params.get("companyId").toString());
		String[] companyArray=companyStr.split(";");
		int reg_id=DataTrans.transToIntegerFromString(params.get("login_id").toString());
		Map<String, Object> recordParams=new HashMap<String, Object>();
		Map<String, Object> inviteParams=new HashMap<String, Object>();
		String message="";
		int login_type=1;
		int login_id=reg_id;
		for (int i = 0; i < companyArray.length; i++) {
			companyParams.put("companyName", companyArray[i]);
			List<PfCompanySimpleInfo> companySimpleInfos= cfpService.getSupplierByName(companyParams);//根据企业名称查company_id,cpyname_cn
			if (companySimpleInfos.size()==0) {
				message+=companyArray[i]+"不存在或未认证;";
			}else {
				supplierParams.put("company_id", companySimpleInfos.get(0).getCompany_id());
				supplierParams.put("owner_id", companyId);
				SupplierFiles supplierFiles=supplierFilesMapper.checkSupplierExist(supplierParams);
				if (supplierFiles!=null) {
					message+=companyArray[i]+"已经是你的供应商;";
				}
				/*brotherParams.put("company_id", companySimpleInfos.get(0).getCompany_id());
				brotherParams.put("reg_id", reg_id);
				SupplierFiles supplierFiles1=mapper.checkSupplierOfBrother(brotherParams);
				if (supplierFiles1!=null&&supplierFiles1.getFile_status()==1) {//是兄弟公司备选供应商
					message+=companyArray[i]+"已经是你兄弟公司的备选供应商;";
				}*/
				inviteParams.put("accepter_id", companySimpleInfos.get(0).getCompany_id());
				if (login_type==0) {//主账号
					inviteParams.put("reg_id", login_id);
				}
				else if (login_type==1) {//子账号
					inviteParams.put("sa_id", login_id);
				}
				AdmittanceInvite admittanceInvite= aiMapper.checkInviteExist(inviteParams);
				if (admittanceInvite!=null) {
						message+=companyArray[i]+"你或你的兄弟公司已向对方发送邀请,等待对方接受;";
				}
				recordParams.put("submit_id", companySimpleInfos.get(0).getCompany_id());
				if (login_type==0) {//主账号
					recordParams.put("reg_id", login_id);
				}
				else if (login_type==1) {//子账号
					recordParams.put("sa_id", login_id);
				}
				AccessRecord accessRecord=aiMapper.checkRecordExist(recordParams);
				if (accessRecord!=null) {
					if (accessRecord.getReceive_invite_id()!=null) {
						message+=companyArray[i]+"已接受你或你的兄弟公司的邀请";
					}else {
						message+=companyArray[i]+"已主动申请成为你或你的兄弟公司的供应商";
					}
				}
			}
		}
		return message;
	}
	
	@Transactional
	public String getPfSupplierAccessChange(Map<String, Object> params) {
		params.put("bool", true);
		CXFResponse<SubAccount> lam=new CXFResponse<SubAccount>();
		String message="";
		if((params.get("record_id")==null)||(params.get("access_status")==null)||(params.get("company_id")==null)||(params.get("login_id")==null)){
			message="参数缺失！";
			params.put("message", message);
			params.put("bool", false);
			lam.setSuccessResponseInfo(null, params);
			if(!message.equals("")){
				return MyJsonUtil.obj2string(lam);
			}
		}
		String record_ids=params.get("record_id").toString();
		String access_statusStr=params.get("access_status").toString();
		int access_status=Integer.parseInt(access_statusStr);
		String company_id=params.get("company_id").toString();
		String login_id=params.get("login_id").toString();
		//获取子账户名
		params.put("sa_id", login_id);
		List<SubAccount> saList=pfRegAccoutMapper.getPfRegAccoutList(params);
		if(saList.size()==0){
			message="该账户在平台中不存在！";
			params.put("message", message);
			params.put("bool", false);
			lam.setSuccessResponseInfo(null, params);
			if(!message.equals("")){
				return MyJsonUtil.obj2string(lam);
			}
		}
		String auditor_name=saList.get(0).getUsername();
		//修改状态流水表
		mapper.updateAccessApplicationRecord(params);
		//1、判断是合格还是不合格
		if(access_status==3){
			//合格  修改供应商表
			mapper.updateSuppliersInfo(params);
		}else if(access_status==4){
			if((params.get("record_id")==null)){
				message="参数缺失！";
				params.put("bool", false);
				params.put("message", message);
				lam.setSuccessResponseInfo(null, params);
				if(!message.equals("")){
					return MyJsonUtil.obj2string(lam);
				}
			}
			String audit_opinion=params.get("audit_opinion").toString();
			String recordArr[]=record_ids.split(",");
			
			for(String rec:recordArr){
				Map<String, Object> para=new HashMap<String, Object>();
				para.put("record_id", Integer.parseInt(rec.replaceAll("'", "")));
				para.put("audit_status", 1);
				para.put("audit_opinion", audit_opinion);
				para.put("auditor_id", login_id);
				para.put("auditor_name", auditor_name);
				para.put("source_type", 0);
				mapper.addPfAdmittanceAudit(para);
			}
		}
		lam.setSuccessResponseInfo(null, params);
		return MyJsonUtil.obj2string(lam);
		
	}
	
	
	/**
	 * 查询该公司名的id
	 * @author chenlong
	 * @param map
	 * @return
	 * @date 2016-05-29
	 */
	public Integer getCpynameIdByCpName(Map<String,Object> map){
		Integer id  = mapper.getCpynameId(map);
		return id;
	}
}
