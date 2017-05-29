 package com.gnamp.weatherservice;
 
 import java.io.Serializable;
 import java.util.Date;
 import javax.xml.namespace.QName;
 import org.apache.axis.description.ElementDesc;
 import org.apache.axis.description.TypeDesc;
 import org.apache.axis.encoding.Deserializer;
 import org.apache.axis.encoding.Serializer;
 import org.apache.axis.encoding.ser.BeanDeserializer;
 import org.apache.axis.encoding.ser.BeanSerializer;
 
 public class Weather
   implements Serializable
 {
   private Date date;
   private String text;
   private int type;
   private int low;
   private int high;
   
   public Weather(Date date, String text, int type, int low, int high)
   {
     this.date = date;
     this.text = text;
     this.type = type;
     this.low = low;
     this.high = high;
   }
   
   public Date getDate()
   {
     return this.date;
   }
   
   public void setDate(Date date)
   {
     this.date = date;
   }
   
   public String getText()
   {
     return this.text;
   }
   
   public void setText(String text)
   {
     this.text = text;
   }
   
   public int getType()
   {
     return this.type;
   }
   
   public void setType(int type)
   {
     this.type = type;
   }
   
   public int getLow()
   {
     return this.low;
   }
   
   public void setLow(int low)
   {
     this.low = low;
   }
   
   public int getHigh()
   {
     return this.high;
   }
   
   public void setHigh(int high)
   {
     this.high = high;
   }
   
   private Object __equalsCalc = null;
   
   public synchronized boolean equals(Object obj)
   {
     if (!(obj instanceof Weather)) {
       return false;
     }
     Weather other = (Weather)obj;
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
       ((this.date == null) && (other.getDate() == null)) || (
       (this.date != null) && 
       (this.date.equals(other.getDate())) && (
       ((this.text == null) && (other.getText() == null)) || (
       (this.text != null) && 
       (this.text.equals(other.getText())) && 
       (this.type == other.getType()) && 
       (this.low == other.getLow()) && 
       (this.high == other.getHigh()))));
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
     if (getDate() != null) {
       _hashCode += getDate().hashCode();
     }
     if (getText() != null) {
       _hashCode += getText().hashCode();
     }
     _hashCode += getType();
     _hashCode += getLow();
     _hashCode += getHigh();
     this.__hashCodeCalc = false;
     return _hashCode;
   }
   
   private static TypeDesc typeDesc = new TypeDesc(Weather.class, true);
   
   static
   {
     typeDesc.setXmlType(new QName("http://ipub.tenhz.com/gnamp", "Weather"));
     ElementDesc elemField = new ElementDesc();
     elemField.setFieldName("date");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "Date"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("text");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "Text"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
     elemField.setMinOccurs(0);
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("type");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "Type"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("low");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "Low"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
     elemField.setNillable(false);
     typeDesc.addFieldDesc(elemField);
     elemField = new ElementDesc();
     elemField.setFieldName("high");
     elemField.setXmlName(new QName("http://ipub.tenhz.com/gnamp", "High"));
     elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "int"));
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
   
   public Weather() {}
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherservice.Weather
 * JD-Core Version:    0.7.0.1
 */