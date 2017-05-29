 package com.gnamp.webservice;
 
 import java.net.MalformedURLException;
 import java.net.URL;
 import java.util.ArrayList;
 import java.util.Iterator;
 import java.util.List;
 import org.apache.axis.client.Call;
 import org.apache.axis.message.MessageElement;
 import org.apache.axis.types.Schema;
 import org.apache.commons.lang.reflect.MethodUtils;
 
 public class ExchangeRateClient
   extends WebService
 {
   public String getNameSpaceURI()
   {
     return "http://webservice.webxml.com.cn/";
   }
   
   public String getRemoteMethod()
   {
     return "getForexRmbRate";
   }
   
   public List getResult()
	        throws Exception
	    {
	        List result = new ArrayList();
	        Schema schemaResult = (Schema)invoke(new Object[0]);
	        if(schemaResult == null)
	            throw new UnsupportedOperationException("\u4ECE\u670D\u52A1\u5668\u53D6\u5F97\u6C47\u7387\u5217\u8868\u5931\u8D25");
	        MessageElement messageElements[] = schemaResult.get_any();
	        MessageElement amessageelement[];
	        int j = (amessageelement = messageElements).length;
	        for(int i = 0; i < j; i++)
	        {
	            MessageElement element = amessageelement[i];
	            for(Iterator iterator = element.getChildElements(); iterator.hasNext();)
	            {
	                MessageElement m = (MessageElement)iterator.next();
	                m = m.getRealElement();
	                for(Iterator it = m.getChildElements(); it.hasNext();)
	                {
	                    m = (MessageElement)it.next();
	                    ExchangeRate rate = parse(m.getChildElements());
	                    if(rate != null)
	                        result.add(rate);
	                }

	            }

	        }

	        return result;
	    }
   
   private ExchangeRate parse(Iterator<?> elements)
   {
     try
     {
       ExchangeRate rate = null;
       while (elements.hasNext())
       {
         MessageElement m = (MessageElement)elements.next();
         String methodName = m.getName();
         methodName = "set" + methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
         if ((m.getName() != null) && (m.getValue() != null))
         {
           if (rate == null) {
             rate = new ExchangeRate();
           }
           if (("Symbol".equals(m.getName())) || 
             ("Name".equals(m.getName()))) {
             MethodUtils.invokeExactMethod(rate, methodName, 
               new Object[] { m.getValue() }, new Class[] { String.class });
           } else {
             MethodUtils.invokeExactMethod(rate, methodName, 
               new Object[] { Double.valueOf(_doubleValueOf(m.getValue())) }, 
               new Class[] { Double.TYPE });
           }
         }
       }
       return rate;
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     return null;
   }
   
   private double _doubleValueOf(String value)
   {
     if ((value == null) || (value.length() == 0)) {
       return 0.0D;
     }
     return Double.valueOf(value).doubleValue();
   }
   
   protected URL getURL()
     throws MalformedURLException
   {
     return new URL(
       "http://webservice.webxml.com.cn/WebServices/ForexRmbRateWebService.asmx?wsdl");
   }
   
   protected String getNamespaceURI()
   {
     return "http://webservice.webxml.com.cn/";
   }
   
   protected String getSoapactionURI()
   {
     return "http://webxml.com.cn/getForexRmbRate";
   }
   
   protected void addParameter(Call call) {}
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.ExchangeRateClient
 * JD-Core Version:    0.7.0.1
 */