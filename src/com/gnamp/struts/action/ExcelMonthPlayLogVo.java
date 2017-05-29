 package com.gnamp.struts.action;
 
 import com.gnamp.struts.vo.IExcelContentVo;
 
 class ExcelMonthPlayLogVo
 {
   String dateTime;
   String deviceId;
   String deviceDescription;
   String cityName;
   String fileName;
   String fileSize;
   String count;
   String playTimes;
   
   @IExcelContentVo(columnIndex=0)
   public String getDateTime()
   {
     return this.dateTime;
   }
   
   public void setDateTime(String dateTime)
   {
     this.dateTime = dateTime;
   }
   
   @IExcelContentVo(columnIndex=1)
   public String getDeviceId()
   {
     return this.deviceId;
   }
   
   public void setDeviceId(String deviceId)
   {
     this.deviceId = deviceId;
   }
   
   @IExcelContentVo(columnIndex=2)
   public String getDeviceDescription()
   {
     return this.deviceDescription;
   }
   
   public void setDeviceDescription(String description)
   {
     this.deviceDescription = description;
   }
   
   @IExcelContentVo(columnIndex=3)
   public String getCityName()
   {
     return this.cityName;
   }
   
   public void setCityName(String cityName)
   {
     this.cityName = cityName;
   }
   
   @IExcelContentVo(columnIndex=4)
   public String getFileName()
   {
     return this.fileName;
   }
   
   public void setFileName(String fileName)
   {
     this.fileName = fileName;
   }
   
   @IExcelContentVo(columnIndex=5)
   public String getFileSize()
   {
     return this.fileSize;
   }
   
   public void setFileSize(String size)
   {
     this.fileSize = size;
   }
   
   @IExcelContentVo(columnIndex=6)
   public String getCount()
   {
     return this.count;
   }
   
   public void setCount(String count)
   {
     this.count = count;
   }
   
   @IExcelContentVo(columnIndex=7)
   public String getPlayTimes()
   {
     return this.playTimes;
   }
   
   public void setPlayTimes(String times)
   {
     this.playTimes = times;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.ExcelMonthPlayLogVo
 * JD-Core Version:    0.7.0.1
 */