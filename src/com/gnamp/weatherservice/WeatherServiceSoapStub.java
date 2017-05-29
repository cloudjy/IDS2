 package com.gnamp.weatherservice;
 
 import java.net.URL;
 import java.rmi.RemoteException;
 import java.util.Enumeration;
 import java.util.Properties;
 import java.util.Vector;
 import javax.xml.namespace.QName;
 import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
 import org.apache.axis.client.Stub;
 import org.apache.axis.constants.Style;
 import org.apache.axis.constants.Use;
 import org.apache.axis.description.OperationDesc;
 import org.apache.axis.description.ParameterDesc;
 import org.apache.axis.encoding.DeserializerFactory;
 import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
 import org.apache.axis.encoding.ser.ArraySerializerFactory;
 import org.apache.axis.encoding.ser.BeanDeserializerFactory;
 import org.apache.axis.encoding.ser.BeanSerializerFactory;
 import org.apache.axis.encoding.ser.EnumDeserializerFactory;
 import org.apache.axis.encoding.ser.EnumSerializerFactory;
 import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
 import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
 import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
 import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;
 
 public class WeatherServiceSoapStub
   extends Stub
   implements WeatherServiceSoap
 {
   private Vector cachedSerClasses = new Vector();
   private Vector cachedSerQNames = new Vector();
   private Vector cachedSerFactories = new Vector();
   private Vector cachedDeserFactories = new Vector();
   static OperationDesc[] _operations = new OperationDesc[5];
   
   static
   {
     _initOperationDesc1();
   }
   
   private static void _initOperationDesc1()
   {
     OperationDesc oper = new OperationDesc();
     oper.setName("QueryWeatherUpdateTime");
     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
     oper.setReturnClass(String.class);
     oper.setReturnQName(new QName("http://ipub.tenhz.com/gnamp", "QueryWeatherUpdateTimeResult"));
     oper.setStyle(Style.WRAPPED);
     oper.setUse(Use.LITERAL);
     _operations[0] = oper;
     
     oper = new OperationDesc();
     oper.setName("QueryWeather");
     ParameterDesc param = new ParameterDesc(new QName("http://ipub.tenhz.com/gnamp", "city"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
     param.setOmittable(true);
     oper.addParameter(param);
     oper.setReturnType(new QName("http://ipub.tenhz.com/gnamp", "CityWeather"));
     oper.setReturnClass(CityWeather.class);
     oper.setReturnQName(new QName("http://ipub.tenhz.com/gnamp", "QueryWeatherResult"));
     oper.setStyle(Style.WRAPPED);
     oper.setUse(Use.LITERAL);
     _operations[1] = oper;
     
     oper = new OperationDesc();
     oper.setName("QueryWeather2");
     param = new ParameterDesc(new QName("http://ipub.tenhz.com/gnamp", "city"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
     param.setOmittable(true);
     oper.addParameter(param);
     param = new ParameterDesc(new QName("http://ipub.tenhz.com/gnamp", "province"), (byte)1, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
     param.setOmittable(true);
     oper.addParameter(param);
     oper.setReturnType(new QName("http://ipub.tenhz.com/gnamp", "CityWeather"));
     oper.setReturnClass(CityWeather.class);
     oper.setReturnQName(new QName("http://ipub.tenhz.com/gnamp", "QueryWeather2Result"));
     oper.setStyle(Style.WRAPPED);
     oper.setUse(Use.LITERAL);
     _operations[2] = oper;
     
     oper = new OperationDesc();
     oper.setName("QueryCityVersion");
     oper.setReturnType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
     oper.setReturnClass(String.class);
     oper.setReturnQName(new QName("http://ipub.tenhz.com/gnamp", "QueryCityVersionResult"));
     oper.setStyle(Style.WRAPPED);
     oper.setUse(Use.LITERAL);
     _operations[3] = oper;
     
     oper = new OperationDesc();
     oper.setName("QueryAllCitys");
     oper.setReturnType(new QName("http://ipub.tenhz.com/gnamp", "ArrayOfCity"));
     oper.setReturnClass(City.class);
     oper.setReturnQName(new QName("http://ipub.tenhz.com/gnamp", "QueryAllCitysResult"));
     param = oper.getReturnParamDesc();
     param.setItemQName(new QName("http://ipub.tenhz.com/gnamp", "City"));
     oper.setStyle(Style.WRAPPED);
     oper.setUse(Use.LITERAL);
     _operations[4] = oper;
   }
   
   public WeatherServiceSoapStub()
     throws AxisFault
   {
     this(null);
   }
   
   public WeatherServiceSoapStub(URL endpointURL, javax.xml.rpc.Service service)
     throws AxisFault
   {
     this(service);
     this.cachedEndpoint = endpointURL;
   }
   
   public WeatherServiceSoapStub(javax.xml.rpc.Service service)
     throws AxisFault
   {
     if (service == null) {
       this.service = new org.apache.axis.client.Service();
     } else {
       this.service = service;
     }
     ((org.apache.axis.client.Service)this.service).setTypeMappingVersion("1.2");
     
 
 
     Class beansf = BeanSerializerFactory.class;
     Class beandf = BeanDeserializerFactory.class;
     Class enumsf = EnumSerializerFactory.class;
     Class enumdf = EnumDeserializerFactory.class;
     Class arraysf = ArraySerializerFactory.class;
     Class arraydf = ArrayDeserializerFactory.class;
     Class simplesf = SimpleSerializerFactory.class;
     Class simpledf = SimpleDeserializerFactory.class;
     Class simplelistsf = SimpleListSerializerFactory.class;
     Class simplelistdf = SimpleListDeserializerFactory.class;
     QName qName = new QName("http://ipub.tenhz.com/gnamp", "ArrayOfCity");
     this.cachedSerQNames.add(qName);
     Class cls = City.class;
     this.cachedSerClasses.add(cls);
     qName = new QName("http://ipub.tenhz.com/gnamp", "City");
     QName qName2 = new QName("http://ipub.tenhz.com/gnamp", "City");
     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
     
     qName = new QName("http://ipub.tenhz.com/gnamp", "ArrayOfWeather");
     this.cachedSerQNames.add(qName);
     cls = Weather.class;
     this.cachedSerClasses.add(cls);
     qName = new QName("http://ipub.tenhz.com/gnamp", "Weather");
     qName2 = new QName("http://ipub.tenhz.com/gnamp", "Weather");
     this.cachedSerFactories.add(new ArraySerializerFactory(qName, qName2));
     this.cachedDeserFactories.add(new ArrayDeserializerFactory());
     
     qName = new QName("http://ipub.tenhz.com/gnamp", "City");
     this.cachedSerQNames.add(qName);
     cls = City.class;
     this.cachedSerClasses.add(cls);
     this.cachedSerFactories.add(beansf);
     this.cachedDeserFactories.add(beandf);
     
     qName = new QName("http://ipub.tenhz.com/gnamp", "CityWeather");
     this.cachedSerQNames.add(qName);
     cls = CityWeather.class;
     this.cachedSerClasses.add(cls);
     this.cachedSerFactories.add(beansf);
     this.cachedDeserFactories.add(beandf);
     
     qName = new QName("http://ipub.tenhz.com/gnamp", "Weather");
     this.cachedSerQNames.add(qName);
     cls = Weather.class;
     this.cachedSerClasses.add(cls);
     this.cachedSerFactories.add(beansf);
     this.cachedDeserFactories.add(beandf);
   }
   
   protected Call createCall()
     throws RemoteException
   {
     try
     {
       Call _call = super._createCall();
       if (this.maintainSessionSet) {
         _call.setMaintainSession(this.maintainSession);
       }
       if (this.cachedUsername != null) {
         _call.setUsername(this.cachedUsername);
       }
       if (this.cachedPassword != null) {
         _call.setPassword(this.cachedPassword);
       }
       if (this.cachedEndpoint != null) {
         _call.setTargetEndpointAddress(this.cachedEndpoint);
       }
       if (this.cachedTimeout != null) {
         _call.setTimeout(this.cachedTimeout);
       }
       if (this.cachedPortName != null) {
         _call.setPortName(this.cachedPortName);
       }
       Enumeration keys = this.cachedProperties.keys();
       while (keys.hasMoreElements())
       {
         String key = (String)keys.nextElement();
         _call.setProperty(key, this.cachedProperties.get(key));
       }
       synchronized (this)
       {
         if (firstCall())
         {
           _call.setEncodingStyle(null);
           for (int i = 0; i < this.cachedSerFactories.size(); i++)
           {
             Class cls = (Class)this.cachedSerClasses.get(i);
             QName qName = 
               (QName)this.cachedSerQNames.get(i);
             Object x = this.cachedSerFactories.get(i);
             if ((x instanceof Class))
             {
               Class sf = 
                 (Class)this.cachedSerFactories.get(i);
               Class df = 
                 (Class)this.cachedDeserFactories.get(i);
               _call.registerTypeMapping(cls, qName, sf, df, false);
             }
             else if ((x instanceof javax.xml.rpc.encoding.SerializerFactory))
             {
               org.apache.axis.encoding.SerializerFactory sf = 
                 (org.apache.axis.encoding.SerializerFactory)this.cachedSerFactories.get(i);
               DeserializerFactory df = 
                 (DeserializerFactory)this.cachedDeserFactories.get(i);
               _call.registerTypeMapping(cls, qName, sf, df, false);
             }
           }
         }
       }
       return _call;
     }
     catch (Throwable _t)
     {
       throw new AxisFault("Failure trying to get the Call object", _t);
     }
   }
   
   public String queryWeatherUpdateTime()
	        throws RemoteException
	    {
	        Call _call;
	        if(super.cachedEndpoint == null)
	            throw new NoEndPointException();
	        _call = createCall();
	        _call.setOperation(_operations[0]);
	        _call.setUseSOAPAction(true);
	        _call.setSOAPActionURI("http://ipub.tenhz.com/gnamp/QueryWeatherUpdateTime");
	        _call.setEncodingStyle(null);
	        _call.setProperty("sendXsiTypes", Boolean.FALSE);
	        _call.setProperty("sendMultiRefs", Boolean.FALSE);
	        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
	        _call.setOperationName(new QName("http://ipub.tenhz.com/gnamp", "QueryWeatherUpdateTime"));
	        setRequestHeaders(_call);
	        setAttachments(_call);
	        Object _resp;
	        _resp = _call.invoke(new Object[0]);
	        if(_resp instanceof RemoteException)
	            throw (RemoteException)_resp;
	        extractAttachments(_call);
	        try
	        {
	            return (String)_resp;
	        }
	        catch(Exception _exception)
	        {
	            return (String)JavaUtils.convert(_resp, String.class);
	        }
	    }
   
   public CityWeather queryWeather(String city)
	        throws RemoteException
	    {
	        Call _call;
	        if(super.cachedEndpoint == null)
	            throw new NoEndPointException();
	        _call = createCall();
	        _call.setOperation(_operations[1]);
	        _call.setUseSOAPAction(true);
	        _call.setSOAPActionURI("http://ipub.tenhz.com/gnamp/QueryWeather");
	        _call.setEncodingStyle(null);
	        _call.setProperty("sendXsiTypes", Boolean.FALSE);
	        _call.setProperty("sendMultiRefs", Boolean.FALSE);
	        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
	        _call.setOperationName(new QName("http://ipub.tenhz.com/gnamp", "QueryWeather"));
	        setRequestHeaders(_call);
	        setAttachments(_call);
	        Object _resp;
	        _resp = _call.invoke(new Object[] {
	            city
	        });
	        if(_resp instanceof RemoteException)
	            throw (RemoteException)_resp;
	        extractAttachments(_call);
	        try
	        {
	            return (CityWeather)_resp;
	        }
	        catch(Exception _exception)
	        {
	            return (CityWeather)JavaUtils.convert(_resp, CityWeather.class);
	        }
	    }

   
   public CityWeather queryWeather2(String city, String province)
	        throws RemoteException
	    {
	        Call _call;
	        if(super.cachedEndpoint == null)
	            throw new NoEndPointException();
	        _call = createCall();
	        _call.setOperation(_operations[2]);
	        _call.setUseSOAPAction(true);
	        _call.setSOAPActionURI("http://ipub.tenhz.com/gnamp/QueryWeather2");
	        _call.setEncodingStyle(null);
	        _call.setProperty("sendXsiTypes", Boolean.FALSE);
	        _call.setProperty("sendMultiRefs", Boolean.FALSE);
	        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
	        _call.setOperationName(new QName("http://ipub.tenhz.com/gnamp", "QueryWeather2"));
	        setRequestHeaders(_call);
	        setAttachments(_call);
	        Object _resp;
	        _resp = _call.invoke(new Object[] {
	            city, province
	        });
	        if(_resp instanceof RemoteException)
	            throw (RemoteException)_resp;
	        extractAttachments(_call);
	        try
	        {
	            return (CityWeather)_resp;
	        }
	        catch(Exception _exception)
	        {
	            return (CityWeather)JavaUtils.convert(_resp,CityWeather.class);
	        }
	    }

   
   public String queryCityVersion()
	        throws RemoteException
	    {
	        Call _call;
	        if(super.cachedEndpoint == null)
	            throw new NoEndPointException();
	        _call = createCall();
	        _call.setOperation(_operations[3]);
	        _call.setUseSOAPAction(true);
	        _call.setSOAPActionURI("http://ipub.tenhz.com/gnamp/QueryCityVersion");
	        _call.setEncodingStyle(null);
	        _call.setProperty("sendXsiTypes", Boolean.FALSE);
	        _call.setProperty("sendMultiRefs", Boolean.FALSE);
	        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
	        _call.setOperationName(new QName("http://ipub.tenhz.com/gnamp", "QueryCityVersion"));
	        setRequestHeaders(_call);
	        setAttachments(_call);
	        Object _resp;
	        _resp = _call.invoke(new Object[0]);
	        if(_resp instanceof RemoteException)
	            throw (RemoteException)_resp;
	        extractAttachments(_call);
	        try
	        {
	            return (String)_resp;
	        }
	        catch(Exception _exception)
	        {
	            return (String)JavaUtils.convert(_resp, String.class);
	        }
	    }
   
   public City[] queryAllCitys()
	        throws RemoteException
	    {
	        Call _call;
	        if(super.cachedEndpoint == null)
	            throw new NoEndPointException();
	        _call = createCall();
	        _call.setOperation(_operations[4]);
	        _call.setUseSOAPAction(true);
	        _call.setSOAPActionURI("http://ipub.tenhz.com/gnamp/QueryAllCitys");
	        _call.setEncodingStyle(null);
	        _call.setProperty("sendXsiTypes", Boolean.FALSE);
	        _call.setProperty("sendMultiRefs", Boolean.FALSE);
	        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
	        _call.setOperationName(new QName("http://ipub.tenhz.com/gnamp", "QueryAllCitys"));
	        setRequestHeaders(_call);
	        setAttachments(_call);
	        Object _resp;
	        _resp = _call.invoke(new Object[0]);
	        if(_resp instanceof RemoteException)
	            throw (RemoteException)_resp;
	        extractAttachments(_call);
	        try
	        {
	            return (City[])_resp;
	        }
	        catch(Exception _exception)
	        {
	            return (City[])JavaUtils.convert(_resp, City.class);
	        }
	      
	    }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.weatherservice.WeatherServiceSoapStub
 * JD-Core Version:    0.7.0.1
 */