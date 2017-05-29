 package com.gnamp.struts.action;
 
 import com.gnamp.server.model.Rect;
 
 public class Program
   extends Layout
 {
   protected int TaskId;
   protected int ProgramId;
   protected int TemplateId;
   protected String ProgramName;
   protected String Desciption;
   protected Rect RefRect;
   protected boolean bInteractive;
   
   public void Remove(Rect rect)
   {
     super.Remove(rect);
     if (rect == this.RefRect) {
       this.RefRect = null;
     }
   }
   
   public int getTaskId()
   {
     return this.TaskId;
   }
   
   public void setTaskId(int val)
   {
     this.TaskId = val;
   }
   
   public int getProgramId()
   {
     return this.ProgramId;
   }
   
   public void setProgramId(int val)
   {
     this.ProgramId = val;
   }
   
   public int getTemplateId()
   {
     return this.TemplateId;
   }
   
   public void setTemplateId(int val)
   {
     this.TemplateId = val;
   }
   
   public String getProgramName()
   {
     return this.ProgramName;
   }
   
   public void setProgramName(String val)
   {
     this.ProgramName = val;
   }
   
   public String getDesciption()
   {
     return this.Desciption;
   }
   
   public void setDesciption(String val)
   {
     this.Desciption = val;
   }
   
   public boolean getInteractive()
   {
     return this.bInteractive;
   }
   
   public void setInteractive(boolean val)
   {
     this.bInteractive = val;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.Program
 * JD-Core Version:    0.7.0.1
 */