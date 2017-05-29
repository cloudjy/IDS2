package com.gnamp.struts.admin.action;

import com.gnamp.server.handle.KernelHandle;
import com.gnamp.server.handle.PackageFile;
import java.io.File;

public class FlexKernelUploadAction
  extends AbstractFlexUploadAction
{
  protected boolean dispatcher(String[] fileName, File[] files)
    throws Exception
  {
    KernelHandle kernelHandle = new KernelHandle(this);
    int errorFlag = 0;
    for (int index = 0; index < files.length; index++)
    {
      if (kernelHandle.exist(PackageFile.getVersion(files[index].getAbsolutePath()))) {
        errorFlag++;
      }
      kernelHandle.upload(files[index].getAbsolutePath(), 
        PackageFile.getVersion(files[index].getAbsolutePath()), fileName[index]);
    }
    if (errorFlag > 0) {
      throw new Exception(getText("uploadfail"));
    }
    return true;
  }
}
