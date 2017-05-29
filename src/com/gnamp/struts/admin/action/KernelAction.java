 package com.gnamp.struts.admin.action;
 
 import com.gnamp.server.handle.KernelHandle;
 import com.gnamp.server.model.Kernel;
 import com.gnamp.server.query.Condition;
 import com.gnamp.server.query.Result;
 import com.gnamp.struts.utils.JsonUtils;
 import com.gnamp.struts.utils.MapTool;
 import com.gnamp.struts.vo.PageBean;
 import java.util.List;
 import java.util.Map;
 import java.util.Stack;
 import org.apache.log4j.Logger;
 
 public class KernelAction
   extends JSONAction
 {
   Logger log = Logger.getLogger(Kernel.class);
   PageBean page = new PageBean();
   
   public PageBean getPage()
   {
     return this.page;
   }
   
   public void setPage(PageBean page)
   {
     this.page = page;
   }
   
   protected Stack<String> errors = new Stack();
   private List<String> versions;
   private Kernel kernel;
   
   public List<String> getVersions()
   {
     return this.versions;
   }
   
   public void setVersions(List<String> versions)
   {
     this.versions = versions;
   }
   
   public Kernel getKernel()
   {
     return this.kernel;
   }
   
   public void setKernel(Kernel kernel)
   {
     this.kernel = kernel;
   }
   
   public void jsonlist()
   {
     try
     {
       if (this.page.getPageSize() == -1)
       {
         List<Kernel> kernels = KernelHandle.readAll();
         this.page.setTotalRows(kernels.size());
         this.page.setPageSize(kernels.size());
         response(JSONSuccessString(JSONArrayToString(kernels), new Map[] { new MapTool().putObject("page", this.page) }));
       }
       else
       {
         Result<Kernel> kernels = KernelHandle.readPage(
           new Condition((this.page.getCurrentPage() - 1) * this.page.getPageSize(), this.page.getPageSize()));
         
         this.page.setTotalRows(kernels.getTotal());
         response(JSONSuccessString(JSONArrayToString(kernels.getResult()), new Map[] {
           new MapTool().putObject("page", this.page) }));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("queryerror"));
       this.log.error(e.getMessage());
     }
   }
   
   public void validateSearchBean()
   {
     if (this.kernel == null)
     {
       addFieldError("kernel", getText("kernelisnull"));
       return;
     }
     if (this.kernel.getKernelVersion() == null) {
       addFieldError("kernel", getText("kernelversionisnull"));
     }
   }
   
   public void searchBean()
   {
     try
     {
       JsonUtils.writeSuccessData(this.response, 
         KernelHandle.read(this.kernel.getKernelVersion()));
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("queryerror"));
       this.log.error(e.getMessage());
     }
   }
   
   public void validateEdit()
   {
     if (this.kernel == null) {
       addFieldError("kernel", getText("kernelisnull"));
     }
     if (KernelHandle.read(this.kernel.getKernelVersion()) == null) {
       addFieldError("kernel", getText("kernelnotexists"));
     }
   }
   
   public void edit()
   {
     KernelHandle handle = new KernelHandle(this);
     try
     {
       Kernel tempKernel = KernelHandle.read(this.kernel.getKernelVersion());
       tempKernel.setDescription(this.kernel.getDescription());
       if (handle.modify(tempKernel)) {
         JsonUtils.writeSuccess(this.response);
       } else {
         JsonUtils.writeErrorMessage(this.response, getText("editerror"));
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorMessage(this.response, getText("editerror"));
       this.log.error(e.getMessage());
     }
   }
   
   public void validateRemove()
   {
     if (this.versions == null) {
       addFieldError("versions", getText("versionsisnull"));
     } else if (this.versions.size() == 0) {
       addFieldError("versions", getText("versionslengtherror"));
     }
   }
   
   public void kernelUsed()
   {
     try
     {
       KernelHandle handle = new KernelHandle(this);
       for (String version : this.versions) {
         if (handle.kernelUsed(version)) {
           throw new Exception();
         }
       }
       response(JSONSuccessString());
     }
     catch (Exception e)
     {
       response(JSONErrorString(getText("kernelusedisdelete")));
       this.log.error(e.getMessage());
     }
   }
   
   public void remove()
   {
     try
     {
       KernelHandle handle = new KernelHandle(this);
       for (String version : this.versions) {
         if (!handle.remove(version)) {
           this.errors.push(version);
         }
       }
       if (this.errors.size() > 0) {
         JsonUtils.writeErrorData(this.response, getText("removeerror") + " VERSION:" + this.errors);
       } else {
         JsonUtils.writeSuccess(this.response);
       }
     }
     catch (Exception e)
     {
       JsonUtils.writeErrorData(this.response, this.errors);
       this.log.error(e.getMessage());
     }
   }
   
   public String preedit()
   {
     return "preedit";
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.admin.action.KernelAction
 * JD-Core Version:    0.7.0.1
 */