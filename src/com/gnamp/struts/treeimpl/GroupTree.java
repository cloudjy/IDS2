 package com.gnamp.struts.treeimpl;
 
 import com.gnamp.server.model.Group;
 import com.gnamp.struts.tree.ITree;
 import com.gnamp.struts.tree.ITreeConvert;
 
 public class GroupTree
   implements ITreeConvert<Group>
 {
   int id;
   String name;
   boolean parent;
   
   public void setIsParent(boolean parent)
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
   
   public ITree<Group> convert(Group group)
   {
     setId(group.getGroupId());
     setName(group.getGroupName());
     if (group.getChildNum() <= 0) {
       setIsParent(false);
     } else {
       setIsParent(true);
     }
     return this;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.treeimpl.GroupTree
 * JD-Core Version:    0.7.0.1
 */