

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoreClass {
	
	boolean itf;
	String class_name;
	List<String> ext = new ArrayList<String>();
	List<String> imp = new ArrayList<String>();
	
	Set<String> assoct=new HashSet<String>();
	Set<String> mul=new HashSet<String>();
	
	List<StoreVariable> sv=new ArrayList<StoreVariable>();
	
	List<StoreMethod> sm=new ArrayList<StoreMethod>();
	
	
	
	
	public boolean isItf() {
		return itf;
	}

	public void setItf(boolean itf) {
		this.itf = itf;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public List<String> getExt() {
		return ext;
	}

	public void setExt(List<String> ext) {
		this.ext = ext;
	}

	public List<String> getImp() {
		return imp;
	}

	public void setImp(List<String> imp) {
		this.imp = imp;
	}

	public Set<String> getAssoct() {
		return assoct;
	}

	public void setAssoct(Set<String> assoct) {
		this.assoct = assoct;
	}

	public Set<String> getMul() {
		return mul;
	}

	public void setMul(Set<String> mul) {
		this.mul = mul;
	}

	public List<StoreVariable> getSv() {
		return sv;
	}

	public void setSv(List<StoreVariable> sv) {
		this.sv = sv;
	}

	public List<StoreMethod> getSm() {
		return sm;
	}

	public void setSm(List<StoreMethod> sm) {
		this.sm = sm;
	}

	@Override
	public String toString() {
		return "StoreClass [itf=" + itf + ", class_name=" + class_name + ", ext=" + ext + ", imp=" + imp + ", assoct="
				+ assoct + ", mul=" + mul + ", sv=" + sv + ", sm=" + sm + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assoct == null) ? 0 : assoct.hashCode());
		result = prime * result + ((class_name == null) ? 0 : class_name.hashCode());
		result = prime * result + ((ext == null) ? 0 : ext.hashCode());
		result = prime * result + ((imp == null) ? 0 : imp.hashCode());
		result = prime * result + (itf ? 1231 : 1237);
		result = prime * result + ((mul == null) ? 0 : mul.hashCode());
		result = prime * result + ((sm == null) ? 0 : sm.hashCode());
		result = prime * result + ((sv == null) ? 0 : sv.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreClass other = (StoreClass) obj;
		if (assoct == null) {
			if (other.assoct != null)
				return false;
		} else if (!assoct.equals(other.assoct))
			return false;
		if (class_name == null) {
			if (other.class_name != null)
				return false;
		} else if (!class_name.equals(other.class_name))
			return false;
		if (ext == null) {
			if (other.ext != null)
				return false;
		} else if (!ext.equals(other.ext))
			return false;
		if (imp == null) {
			if (other.imp != null)
				return false;
		} else if (!imp.equals(other.imp))
			return false;
		if (itf != other.itf)
			return false;
		if (mul == null) {
			if (other.mul != null)
				return false;
		} else if (!mul.equals(other.mul))
			return false;
		if (sm == null) {
			if (other.sm != null)
				return false;
		} else if (!sm.equals(other.sm))
			return false;
		if (sv == null) {
			if (other.sv != null)
				return false;
		} else if (!sv.equals(other.sv))
			return false;
		return true;
	}
	
	
	
	
	

}
