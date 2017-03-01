package platform.company.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import platform.company.data.PfAttchedMapper;

import com.outsideasy.ws.erp.supplier.vo.AttchedOut;

@Service
public class PfAttchedService {

	@Autowired
	private PfAttchedMapper pfAttchedMapper;
	
	public List<AttchedOut> getAttchedOutList(Map<String,Object> params){
		return pfAttchedMapper.getAttchedOutList(params);
	}
	
	public void  updateAttchedById(Map<String,Object> params) {
		pfAttchedMapper.updateAttchedById(params);
	}
	
}
