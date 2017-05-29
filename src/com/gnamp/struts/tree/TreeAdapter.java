 package com.gnamp.struts.tree;
 
 public class TreeAdapter<T>
   implements ITree<T>
 {
   private int id;
   private String name;
   private boolean parent;
   
   public TreeAdapter() {}
   
   public TreeAdapter(int id)
   {
     setId(id);
   }
   
   public TreeAdapter(int id, String name, boolean parent)
   {
     setId(id);
     setName(name);
     setParent(parent);
   }
   
   public void setParent(boolean parent)
   {
     this.parent = parent;
   }
   
   public void setId(int id)
   {
     this.id = id;
   }
   
   public void setName(String name)
   {
     this.name = name;
   }
   
   public int getId()
   {
     return this.id;
   }
   
   public String getName()
   {
     return this.name;
   }
   
   public boolean getIsParent()
   {
     return this.parent;
   }
   
   public boolean equals(Object obj)
   {
     if ((obj instanceof TreeAdapter))
     {
       TreeAdapter<?> adapter = (TreeAdapter)obj;
       return adapter.getId() == getId();
     }
     return super.equals(obj);
   }
   
   public int hashCode()
   {
     return getId();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.tree.TreeAdapter
 * JD-Core Version:    0.7.0.1
 */