package com.gnamp.struts.vo;

public enum SystemSourceField
{
  SOURCEID {
	@Override
	public String valueToString() {
		return "sourceid";
	}
},  NAME {
	@Override
	public String valueToString() {
		 return "name";
	}
},  DESCP {
	@Override
	public String valueToString() {
		// TODO Auto-generated method stub
		  return "descp";
	}
},  VERSION {
	@Override
	public String valueToString() {
		  return "version";
	}
},  CATID {
	@Override
	public String valueToString() {
		 return "catid";
	}
},  TYPE {
	@Override
	public String valueToString() {
		  return "type";
	}
},  STATE {
	@Override
	public String valueToString() {
		 return "state";
	}
};
  
  public abstract String valueToString();
}


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.SystemSourceField
 * JD-Core Version:    0.7.0.1
 */