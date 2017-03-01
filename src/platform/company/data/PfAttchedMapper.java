package platform.company.data;

import java.util.List;
import java.util.Map;

import com.outsideasy.ws.erp.supplier.vo.AttchedOut;

public interface PfAttchedMapper {
	public List<AttchedOut> getAttchedOutList(Map<String,Object> params);
	public void updateAttchedById(Map<String,Object> params);
}
