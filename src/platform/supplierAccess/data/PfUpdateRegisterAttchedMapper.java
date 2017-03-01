package platform.supplierAccess.data;

import java.util.List;
import java.util.Map;

import platform.supplierAccess.model.PfUpdateRegisterAttched;


public interface PfUpdateRegisterAttchedMapper {
	public List<PfUpdateRegisterAttched> getPfUpdateRegisterAttchedList(Map<String,Object> params);
	public void addPfUpdateRegisterAttched(PfUpdateRegisterAttched obj);
	public void updatePfUpdateRegisterAttched(PfUpdateRegisterAttched obj);
	public void deletePfUpdateRegisterAttched(PfUpdateRegisterAttched obj);
}
