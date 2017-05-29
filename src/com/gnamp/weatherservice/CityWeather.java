 package com.gnamp.weatherservice;
 
 import java.io.Serializable;
 import java.lang.reflect.Array;
 import java.util.Arrays;
 import java.util.Calendar;
 import javax.xml.namespace.QName;
 import org.apache.axis.description.ElementDesc;
 import org.apache.axis.description.TypeDesc;
 import org.apache.axis.encoding.Deserializer;
 import org.apache.axis.encoding.Serializer;
 import org.apache.axis.encoding.ser.BeanDeserializer;
 import org.apache.axis.encoding.ser.BeanSerializer;
 
 public class CityWeather
   implements Serializable
 {
   private String cityName;
   private String stateName;
   private Calendar updateTime;
   private Weather[] weathers;
   
   public CityWeather(String cityName, String stateName, Calendar updateTime, Weather[] weathers)
   {
     this.cityName = cityName;
     this.stateName = stateName;
     this.updateTime = updateTime;
     this.weathers = weathers;
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
   
   public Calendar getUpdateTime()
   {
     return this.updateTime;
   }
   
   public void setUpdateTime(Calendar updateTime)
   {
     this.updateTime = updateTime;
   }
   
   public Weather[] getWeathers()
   {
     return this.weathers;
   }
   
   public void setWeathers(Weather[] weathers)
   {
     this.weathers = weathers;
   }
   
   private Object __equalsCalc = null;
   
   public synchronized boolean equals(Object obj)
   {
     if (!(obj instanceof CityWeather)) {
       return false;
     }
     CityWeather other = (CityWeather)obj;
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
       (this.stateName.equals(other.getStateName())) && (
       ((this.updateTime == null) && (other.getUpdateTime() == null)) || (
       (this.updateTime != null) && 
       (this.updateTime.equals(other.getUpdateTime())) && (
       ((this.weathers == null) && (other.getWeathers() == null)) || (
       (this.weathers != null) && 
       (Arrays.equals(this.weathers, other.getWeathers())))))))));
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
     if (getUpdateTime() != null) {
       _hashCode += getUpdateTime().hashCode();
     }
     if (getWeathers() != null) {
       for (int i = 0; 
             i < Array.getLength(getWeathers()); i++)
       {
         Object obj = Array.get(getWeathers(), i);
         if ((obj != null) && 
           (!obj.getClass().isArray())) {
           _hashCode += obj.hashCode();
         }
       }
     }
     this.__hashCodeCalc = false;
     return _hashCode;
   }
   
   private static TypeDesc typeDesc = new TypeDesc(CityWeather.class, true);
   
   static
   {
     typeDesc.setXmlType(new QName("http://ipub.tenhz.com/gnamp", "CityWeather"));
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
     elemField = new ElementDesc();
     elemField.setFieldName("updateTime");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "UpdateTime"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("weathers");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "Weathers"));
     elemField.setXmlType(new QName("http://ipub.tenhz.com/gnamp", "Weather"));
     elemField.setMinOccurs(0);
     elemField.setNillable(false);
     elemField.setItemQName(new QName("http://ipub.tenhz.com/gnamp", "Weather"));
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
   
   public CityWeather() {}
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherservice.CityWeather
 * JD-Core Version:    0.7.0.1
 */