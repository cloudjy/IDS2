package com.gnamp.struts.vo;

import com.gnamp.struts.tree.ITree;
import java.util.List;

public abstract interface FilterCategory<T>
{
  public abstract void execute(List<ITree<T>> paramList, ITree<T> paramITree);
}


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.vo.FilterCategory
 * JD-Core Version:    0.7.0.1
 */