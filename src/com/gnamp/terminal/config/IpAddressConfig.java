 package com.gnamp.terminal.config;
 
 public abstract class IpAddressConfig
   extends NetworkConfig
 {
   public boolean mDhcp = true;
   public String mIp = "";
   public String mMask = "";
   public String mGate = "";
   public String mDns = "";
   
   public boolean getDhcp()
   {
     return this.mDhcp;
   }
   
   public void setDhcp(boolean dhcp)
   {
     this.mDhcp = dhcp;
   }
   
   public String getIp()
   {
     return this.mIp;
   }
   
   public void setIp(String ip)
   {
     this.mIp = (ip == null ? "" : ip);
   }
   
   public String getMask()
   {
     return this.mMask;
   }
   
   public void setMask(String mask)
   {
     this.mMask = (mask == null ? "" : mask);
   }
   
   public String getGate()
   {
     return this.mGate;
   }
   
   public void setGate(String gate)
   {
     this.mGate = (gate == null ? "" : gate);
   }
   
   public String getDns()
   {
     return this.mDns;
   }
   
   public void setDns(String dns)
   {
     this.mDns = (dns == null ? "" : dns);
   }
   
   public String toString()
   {
     return 
     
 
 
       "dhcp=" + this.mDhcp + ", ip=" + this.mIp + ", mask=" + this.mMask + ", gate=" + this.mGate + ", dns" + this.mDns + ".";
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof IpAddressConfig))) {
       return false;
     }
     IpAddressConfig o = (IpAddressConfig)obj;
     if (this.mDhcp != o.mDhcp) {
       return false;
     }
     if (!(this.mIp == null ? "" : this.mIp.trim()).equals(o.mIp == null ? "" : o.mIp.trim())) {
       return false;
     }
     if (!(this.mMask == null ? "" : this.mMask.trim()).equals(o.mMask == null ? "" : o.mMask.trim())) {
       return false;
     }
     if (!(this.mGate == null ? "" : this.mGate.trim()).equals(o.mGate == null ? "" : o.mGate.trim())) {
       return false;
     }
     if (!(this.mDns == null ? "" : this.mDns.trim()).equals(o.mDns == null ? "" : o.mDns.trim())) {
       return false;
     }
     return true;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.IpAddressConfig
 * JD-Core Version:    0.7.0.1
 */