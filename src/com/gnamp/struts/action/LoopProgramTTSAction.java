 package com.gnamp.struts.action;
 
 import com.gnamp.server.db.ProgramTable;
 import com.gnamp.server.db.TaskTable;
 import com.gnamp.server.handle.LoopProgramTTSHandle;
 import com.gnamp.server.handle.ProgramTTSHandle;
 
 public class LoopProgramTTSAction
   extends ProgramTTSAction
 {
   private static final long serialVersionUID = 2787120946769896608L;
   
   public String getTaskType()
   {
     return "loop";
   }
   
   private LoopProgramTTSHandle mProgramTTSHandle = null;
   
   protected ProgramTTSHandle<? extends TaskTable<?>, ? extends ProgramTable<?>> _ProgramTTSHandle()
   {
     return this.mProgramTTSHandle == null ? (this.mProgramTTSHandle = new LoopProgramTTSHandle(this)) : this.mProgramTTSHandle;
   }
   
   protected String _ProgramTTSTableName()
   {
     return "tb_loop_program_tts";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.LoopProgramTTSAction
 * JD-Core Version:    0.7.0.1
 */