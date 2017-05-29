 package com.gnamp.struts.action;
 
 import com.gnamp.server.db.ProgramTable;
 import com.gnamp.server.db.TaskTable;
 import com.gnamp.server.handle.PluginProgramTTSHandle;
 import com.gnamp.server.handle.ProgramTTSHandle;
 
 public class PluginProgramTTSAction
   extends ProgramTTSAction
 {
   private static final long serialVersionUID = 6179408881281162438L;
   
   public String getTaskType()
   {
     return "plugin";
   }
   
   private PluginProgramTTSHandle mProgramTTSHandle = null;
   
   protected ProgramTTSHandle<? extends TaskTable<?>, ? extends ProgramTable<?>> _ProgramTTSHandle()
   {
     return this.mProgramTTSHandle == null ? (this.mProgramTTSHandle = new PluginProgramTTSHandle(this)) : this.mProgramTTSHandle;
   }
   
   protected String _ProgramTTSTableName()
   {
     return "tb_plugin_program_tts";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.PluginProgramTTSAction
 * JD-Core Version:    0.7.0.1
 */