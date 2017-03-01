package com.outsideasy.ws.erp.supplier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import platform.supplierAccess.model.PfAccessBasetable;
import platform.supplierAccess.model.PfAuthcationInfo;
import platform.supplierAccess.model.PfAuthcationUpdate;
import platform.supplierAccess.model.PfSupplierAccessScore;
import platform.supplierAccess.model.PfSupplierAccessScoreDetails;
import platform.supplierAccess.model.PfSupplierAccessScoreSummary;
import platform.supplierAccess.service.PfAccessBasetableService;
import platform.supplierAccess.service.PfAuthcationInfoService;
import platform.supplierAccess.service.PfAuthcationUpdateService;
import platform.supplierAccess.service.PfSupplierAccessScoreService;
import util.MyJsonUtil;
import util.WebUtil;

import com.mongodb.gridfs.GridFSDBFile;
import com.outsideasy.ws.common.vo.CXFResponse;
import common.model.TreeModel;
import common.mongodb.service.FileOptService;

@WebService
@SOAPBinding(style = Style.RPC)
public class SupplierAccessImpl implements SupplierAccessInter {

	@Autowired
	private PfAccessBasetableService pfAccessBasetableService;
	@Autowired
	private PfSupplierAccessScoreService pfSupplierAccessScoreService;
	@Autowired
	private PfAuthcationUpdateService pfAuthcationUpdateService;
	@Autowired
	private FileOptService fileOptService;
	@Autowired
	private PfAuthcationInfoService pfAuthcationInfoService;
	
	public String getPfAuthcationInfoList(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		CXFResponse<PfAuthcationInfo> lam=new CXFResponse<PfAuthcationInfo>();
		List<PfAuthcationInfo> list=pfAuthcationInfoService.getPfAuthcationInfoList(params);
		lam.setSuccessResponseInfo(list, params);
		return MyJsonUtil.obj2string(lam);
	}
	
	public String getPfAccessBasetableList(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		CXFResponse<PfAccessBasetable> lam=new CXFResponse<PfAccessBasetable>();
		List<PfAccessBasetable> list=pfAccessBasetableService.getPfAccessBasetableList(params);
		lam.setSuccessResponseInfo(list, params);
		return MyJsonUtil.obj2string(lam);
	}

	
	public String getEvaluateItemTree(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		List<TreeModel> list=pfAccessBasetableService.getEvaluateItemTree(params);
		return MyJsonUtil.obj2string(list);
	}

	
	public String getPfSupplierAccessScoreList(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		CXFResponse<PfSupplierAccessScore> lam=new CXFResponse<PfSupplierAccessScore>();
		List<PfSupplierAccessScore> list=pfSupplierAccessScoreService.getPfSupplierAccessScoreList(params);
		lam.setSuccessResponseInfo(list, params);
		return MyJsonUtil.obj2string(lam);
	}

	
	public String addPfSupplierAccessScore(String jsonlist) {
		List<PfSupplierAccessScore> list=MyJsonUtil.str2list(jsonlist,PfSupplierAccessScore.class);
		PfSupplierAccessScore[] array=list.toArray(new PfSupplierAccessScore[list.size()]);
		pfSupplierAccessScoreService.addPfSupplierAccessScore(array);
		return MyJsonUtil.obj2string(array);
	}

	
	public String updatePfSupplierAccessScore(String jsonlist) {
		List<PfSupplierAccessScore> list=MyJsonUtil.str2list(jsonlist,PfSupplierAccessScore.class);
		PfSupplierAccessScore[] array=list.toArray(new PfSupplierAccessScore[list.size()]);
		pfSupplierAccessScoreService.updatePfSupplierAccessScore(array);
		return MyJsonUtil.obj2string(array);
	}

	
	public void deletePfSupplierAccessScore(String jsonlist) {
		List<PfSupplierAccessScore> list=MyJsonUtil.str2list(jsonlist,PfSupplierAccessScore.class);
		PfSupplierAccessScore[] array=list.toArray(new PfSupplierAccessScore[list.size()]);
		pfSupplierAccessScoreService.deletePfSupplierAccessScore(array);
	}

	
	public String getPfSupplierAccessScoreDetailsList(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		CXFResponse<PfSupplierAccessScoreDetails> lam=new CXFResponse<PfSupplierAccessScoreDetails>();
		List<PfSupplierAccessScoreDetails> list=pfSupplierAccessScoreService.getPfSupplierAccessScoreDetailsList(params);
		lam.setSuccessResponseInfo(list, params);
		return MyJsonUtil.obj2string(lam);
	}

	
	public String getPfSupplierAccessScoreSummaryList(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		CXFResponse<PfSupplierAccessScoreSummary> lam=new CXFResponse<PfSupplierAccessScoreSummary>();
		List<PfSupplierAccessScoreSummary> list=pfSupplierAccessScoreService.getPfSupplierAccessScoreSummaryList(params);
		lam.setSuccessResponseInfo(list, params);
		return MyJsonUtil.obj2string(lam);
	}

	
	public void deletePfSupplierAccessScoreForCompanyId(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		pfSupplierAccessScoreService.deletePfSupplierAccessScoreForCompanyId(params);
	}

	
	public String getPfAuthcationUpdateList(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		CXFResponse<PfAuthcationUpdate> lam=new CXFResponse<PfAuthcationUpdate>();
		List<PfAuthcationUpdate> list=pfAuthcationUpdateService.getPfAuthcationUpdateList(params);
		lam.setSuccessResponseInfo(list, params);
		return MyJsonUtil.obj2string(lam);
	}

	
	public byte[] retrieveFileOne(String objID) {
		GridFSDBFile file = fileOptService.retrieveFileOne("fs", objID);
		byte[] buffer = null;
		try {
			InputStream in = null;
			if(file!=null){
				in = file.getInputStream();
			}else{
				File tempfile=new File(WebUtil.getRootPath()+"/newresources/images/tasks/notexist.png");
				in=new FileInputStream(tempfile);
			}
			buffer = IOUtils.toByteArray(in);
	        in.close();
	        return buffer;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public String updateSupplierChangeStateByWS(String jsonmap) {
		Map<String,Object> params=MyJsonUtil.str2map(jsonmap);
		params.put("succ", "true");
		pfAuthcationUpdateService.updateSupplierChangeStateByWS(params);
		return MyJsonUtil.obj2string(params);
	}
	
}
