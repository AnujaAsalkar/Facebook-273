

public class StoreVariable {
	
	String access;
	String ty;
	String var_name;
	
	public StoreVariable() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public String getAccess() {
		return access;
	}



	public void setAccess(String access) {
		this.access = access;
	}



	public String getTy() {
		return ty;
	}



	public void setTy(String ty) {
		this.ty = ty;
	}



	public String getVar_name() {
		return var_name;
	}



	public void setVar_name(String var_name) {
		this.var_name = var_name;
	}



	@Override
	public String toString() {
		return "StoreVariable [access=" + access + ", ty=" + ty + ", var_name=" + var_name + "]";
	}
	

}
