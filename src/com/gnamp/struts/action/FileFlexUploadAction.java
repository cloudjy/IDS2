package com.gnamp.struts.action;

import com.gnamp.server.handle.CategoryHandle;
import com.gnamp.server.handle.FileHandle;
import com.gnamp.server.model.File;

public class FileFlexUploadAction extends AbstractFlexUploadAction {

	private static final long serialVersionUID = 1L;
	private int categoryId;
	private boolean autoCheck;

	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public boolean isAutoCheck() {
		return this.autoCheck;
	}

	public void setAutoCheck(boolean autoCheck) {
		this.autoCheck = autoCheck;
	}

	protected boolean dispatcher(String[] fileName, java.io.File[] files) throws Exception {
		FileHandle fileHandle = new FileHandle(this);
		if ((this.categoryId > 0) && (new CategoryHandle(this).read(this.categoryId) == null)) {
			this.categoryId = 0;
		}
		for (int index = 0; index < files.length; index++) {
			File file = fileHandle.upload(files[index].getAbsolutePath(), fileName[index], fileName[index],
					this.categoryId, getFileType(fileName[index]), "", getFileFlag(fileName[index]));

			logEvent(getEnglish("uploadfile"), getEnglish("uploadfile") + ":[" + fileName[index] + "]");
			if (this.autoCheck) {
				fileHandle.check(file);
			}
		}
		return true;
	}

	protected int getFileType(String fileName) {
		return UploadUtils.getFileType(fileName);
	}

	protected int getFileFlag(String fileName) {
		return UploadUtils.getFileFlag(fileName);
	}
}

/*
 * Location: C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * 
 * Qualified Name: com.gnamp.struts.action.FileFlexUploadAction
 * 
 * JD-Core Version: 0.7.0.1
 * 
 */