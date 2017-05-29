package com.gnamp.struts.tree;

@Deprecated
public abstract interface ITreeConvert<T>
  extends ITree<T>
{
  public abstract ITree<T> convert(T paramT);
}


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.tree.ITreeConvert
 * JD-Core Version:    0.7.0.1
 */