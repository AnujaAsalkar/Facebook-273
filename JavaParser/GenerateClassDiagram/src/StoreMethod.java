

import java.util.ArrayList;
import java.util.List;

public class StoreMethod {
	
	String method_modifier;
	String return_type;
	String method_name;
	String params; /*=new ArrayList<String>();*/
	StringBuilder body;
		
	public String getMethod_modifier() {
		return method_modifier;
	}


	public void setMethod_modifier(String method_modifier) {
		this.method_modifier = method_modifier;
	}





	public String getReturn_type() {
		return return_type;
	}





	public void setReturn_type(String return_type) {
		this.return_type = return_type;
	}





	public String getMethod_name() {
		return method_name;
	}





	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}





	public String getParams() {
		return params;
	}





	public void setParams(String params) {
		this.params = params;
	}





	public StringBuilder getBody() {
		return body;
	}





	public void setBody(StringBuilder body) {
		this.body = body;
	}





	@Override
	public String toString() {
		return "StoreMethod [method_modifier=" + method_modifier + ", return_type=" + return_type + ", method_name="
				+ method_name + ", params=" + params + ", body=" + body + "]";
	}
	
	
	
}
