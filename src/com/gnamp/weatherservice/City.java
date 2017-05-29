 package com.gnamp.weatherservice;
 
 import java.io.Serializable;
 import javax.xml.namespace.QName;
 import org.apache.axis.description.ElementDesc;
 import org.apache.axis.description.TypeDesc;
 import org.apache.axis.encoding.Deserializer;
 import org.apache.axis.encoding.Serializer;
 import org.apache.axis.encoding.ser.BeanDeserializer;
 import org.apache.axis.encoding.ser.BeanSerializer;
 
 public class City
   implements Serializable
 {
   private String cityName;
   private String stateName;
   
   public City(String cityName, String stateName)
   {
     this.cityName = cityName;
     this.stateName = stateName;
   }
   
   public String getCityName()
   {
     return this.cityName;
   }
   
   public void setCityName(String cityName)
   {
     this.cityName = cityName;
   }
   
   public String getStateName()
   {
     return this.stateName;
   }
   
   public void setStateName(String stateName)
   {
     this.stateName = stateName;
   }
   
   private Object __equalsCalc = null;
   
   public synchronized boolean equals(Object obj)
   {
     if (!(obj instanceof City)) {
       return false;
     }
     City other = (City)obj;
     if (obj == null) {
       return false;
     }
     if (this == obj) {
       return true;
     }
     if (this.__equalsCalc != null) {
       return this.__equalsCalc == obj;
     }
     this.__equalsCalc = obj;
     
     boolean _equals = 
       ((this.cityName == null) && (other.getCityName() == null)) || (
       (this.cityName != null) && 
       (this.cityName.equals(other.getCityName())) && (
       ((this.stateName == null) && (other.getStateName() == null)) || (
       (this.stateName != null) && 
       (this.stateName.equals(other.getStateName())))));
     this.__equalsCalc = null;
     return _equals;
   }
   
   private boolean __hashCodeCalc = false;
   
   public synchronized int hashCode()
   {
     if (this.__hashCodeCalc) {
       return 0;
     }
     this.__hashCodeCalc = true;
     int _hashCode = 1;
     if (getCityName() != null) {
       _hashCode += getCityName().hashCode();
     }
     if (getStateName() != null) {
       _hashCode += getStateName().hashCode();
     }
     this.__hashCodeCalc = false;
     return _hashCode;
   }
   
   private static TypeDesc typeDesc = new TypeDesc(City.class, true);
   
   static
   {
     typeDesc.setXmlType(new QName("http://ipub.tenhz.com/gnamp", "City"));
     ElementDesc elemField = new ElementDesc();
     elemField.setFieldName("cityName");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "CityName"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
     elemField.setMinOccurs(0);
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("stateName");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "StateName"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
     elemField.setMinOccurs(0);
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
   }
   
   public static TypeDesc getTypeDesc()
   {
     return typeDesc;
   }
   
   public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType)
   {
     return 
       new BeanSerializer(
       _javaType, _xmlType, typeDesc);
   }
   
   public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType)
   {
     return 
       new BeanDeserializer(
       _javaType, _xmlType, typeDesc);
   }
   
   public City() {}
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherservice.City
 * JD-Core Version:    0.7.0.1
 */