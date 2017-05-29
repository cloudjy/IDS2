 package com.gnamp.struts.action;
 
 public class Template
   extends Layout
 {
   public int TemplateId;
   public String TemplateName;
   public String Desciption;
   
   public int getTemplateId()
   {
     return this.TemplateId;
   }
   
   public void setTemplateId(int val)
   {
     this.TemplateId = val;
   }
   
   public String getTemplateName()
   {
     return this.TemplateName;
   }
   
   public void setTemplateName(String val)
   {
     this.TemplateName = val;
   }
   
   public String getDesciption()
   {
     return this.Desciption;
   }
   
   public void setDesciption(String val)
   {
     this.Desciption = val;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.Template
 * JD-Core Version:    0.7.0.1
 */