 package com.gnamp.struts.action;
 
 import com.gnamp.server.db.ProgramTable;
 import com.gnamp.server.db.TaskTable;
 import com.gnamp.server.handle.DemandProgramTTSHandle;
 import com.gnamp.server.handle.ProgramTTSHandle;
 
 public class DemandProgramTTSAction
   extends ProgramTTSAction
 {
   private static final long serialVersionUID = -1404467131130874410L;
   
   public String getTaskType()
   {
     return "demand";
   }
   
   private DemandProgramTTSHandle mProgramTTSHandle = null;
   
   protected ProgramTTSHandle<? extends TaskTable<?>, ? extends ProgramTable<?>> _ProgramTTSHandle()
   {
     return this.mProgramTTSHandle == null ? (this.mProgramTTSHandle = new DemandProgramTTSHandle(this)) : this.mProgramTTSHandle;
   }
   
   protected String _ProgramTTSTableName()
   {
     return "tb_demand_program_tts";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.DemandProgramTTSAction
 * JD-Core Version:    0.7.0.1
 */