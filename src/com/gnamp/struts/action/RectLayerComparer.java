 package com.gnamp.struts.action;
 
 import com.gnamp.server.model.Rect;
 import java.util.Comparator;
 
 public class RectLayerComparer
   implements Comparator<Rect>
 {
   public int compare(Rect x, Rect y)
   {
     return x.getLayer() < y.getLayer() ? -1 : x.getLayer() > y.getLayer() ? 1 : 0;
   }
   
   public boolean equals(Object obj)
   {
     return equals(obj);
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.RectLayerComparer
 * JD-Core Version:    0.7.0.1
 */