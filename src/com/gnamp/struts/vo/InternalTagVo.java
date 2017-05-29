 package com.gnamp.struts.vo;
 
 public class InternalTagVo
 {
   private int id;
   private String name;
   private int type;
   private int state;
   private int catId;
   private String filename;
   
   public int getId()
   {
     return this.id;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   public String getName()
   {
     return this.name;
   }
   
   public void setName(String name)
   {
     this.name = name;
   }
   
   public int getType()
   {
     return this.type;
   }
   
   public void setType(int type)
   {
     this.type = type;
   }
   
   public int getState()
   {
     return this.state;
   }
   
   public void setState(int state)
   {
     this.state = state;
   }
   
   public int getCatId()
   {
     return this.catId;
   }
   
   public void setCatId(int catId)
   {
     this.catId = catId;
   }
   
   public String getFilename()
   {
     return this.filename;
   }
   
   public void setFilename(String filename)
   {
     this.filename = filename;
   }
   
   public boolean equals(Object obj)
   {
     if ((obj instanceof InternalTagVo))
     {
       InternalTagVo vo = (InternalTagVo)obj;
       return (vo.getCatId() == getCatId()) && (vo.getId() == getId());
     }
     return super.equals(obj);
   }
   
   public int hashCode()
   {
     return getId();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.InternalTagVo
 * JD-Core Version:    0.7.0.1
 */