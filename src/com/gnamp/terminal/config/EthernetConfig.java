 package com.gnamp.terminal.config;
 
 public final class EthernetConfig
   extends IpAddressConfig
 {
   public String toString()
   {
     return "[ethernet] " + super.toString();
   }
   
   public boolean equals(Object obj)
   {
     if (this == obj) {
       return true;
     }
     if ((obj == null) || (!(obj instanceof EthernetConfig))) {
       return false;
     }
     return super.equals(obj);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.EthernetConfig
 * JD-Core Version:    0.7.0.1
 */