 package com.gnamp.webservice;
 
 import java.lang.reflect.InvocationTargetException;
 import java.net.MalformedURLException;
 import java.net.URL;
 import javax.xml.namespace.QName;
 import javax.xml.rpc.ParameterMode;
 import org.apache.axis.client.Call;
 import org.apache.axis.encoding.XMLType;
 import org.apache.axis.message.MessageElement;
 import org.apache.commons.lang.reflect.MethodUtils;
 
 public class StockChinaClient
   extends WebService
 {
   private static final String RESULT_URL = "http://hq.sinajs.cn/list=";
   
   protected String getNamespaceURI()
   {
     return "http://tempuri.org/";
   }
   
   protected URL getURL()
     throws MalformedURLException
   {
     return new URL(getHost() + "StockService.asmx?wsdl");
   }
   
   protected String getSoapactionURI()
   {
     return "http://tempuri.org/getStock";
   }
   
   protected String getRemoteMethod()
   {
     return "getStock";
   }
   
   protected void addParameter(Call call)
   {
     call.addParameter(new QName(getNamespaceURI(), "list"), 
       XMLType.XSD_STRING, 
       ParameterMode.IN);
   }
   
   public Stock getResult(String code)
     throws Exception
   {
     return getStockByList("http://hq.sinajs.cn/list=" + code, code);
   }
   
   private Stock getStockByList(String result, String list)
   {
     String value = WebUtil.getWebCon(result);
     String[] resultArray = value.substring(value.indexOf("\"") + 1, value.lastIndexOf("\"")).split(",");
     if (resultArray.length < 4) {
       return null;
     }
     Stock stock = new Stock();
     stock.setName(resultArray[0]);
     stock.setList(list);
     stock.setCurrentPoint(resultArray[1]);
     stock.setCurrentPrice(resultArray[2]);
     stock.setChangeRate(resultArray[3]);
     return stock;
   }
   
   private Stock parse(MessageElement[] messageElements)
     throws NoSuchMethodException, IllegalAccessException, InvocationTargetException
   {
     if (messageElements == null) {
       return null;
     }
     Stock stock = new Stock();
     for (int i = 0; i < messageElements.length; i++)
     {
       String methodName = "set" + messageElements[i].getName();
       String value = messageElements[i].getValue();
       MethodUtils.invokeExactMethod(stock, methodName, new Object[] { value }, new Class[] { String.class });
     }
     return stock;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.webservice.StockChinaClient
 * JD-Core Version:    0.7.0.1
 */