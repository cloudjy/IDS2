 package com.gnamp.struts.action;
 
 import com.gnamp.server.model.Rect;
 import java.util.Arrays;
 import java.util.Vector;
 
 public abstract class Layout
 {
   public int W;
   public int H;
   public int MainRectId;
   
   public int getW()
   {
     return this.W;
   }
   
   public void setW(int val)
   {
     this.W = val;
   }
   
   public int getH()
   {
     return this.H;
   }
   
   public void setH(int val)
   {
     this.H = val;
   }
   
   public Rect[] getRectWnds()
   {
     return RectWnds();
   }
   
   public int getMainRectId()
   {
     return this.MainRectId;
   }
   
   public void setMainRectId(int val)
   {
     this.MainRectId = val;
   }
   
   public void setRectWnds(Rect[] rects)
   {
     this.Rects.clear();
     if ((rects != null) && (rects.length > 0)) {
       for (int i = 0; i < rects.length; i++) {
         this.Rects.add(rects[i]);
       }
     }
   }
   
   public Rect[] RectWnds()
   {
     if ((this.Rects == null) || (this.Rects.size() <= 0)) {
       return null;
     }
     return (Rect[])this.Rects.toArray(new Rect[0]);
   }
   
   public void Add(Rect rect)
   {
     if (rect != null) {
       this.Rects.add(rect);
     }
   }
   
   public void Remove(Rect rect)
   {
     if (rect != null) {
       this.Rects.remove(rect);
     }
   }
   
   public void ResetLayer()
   {
     if ((this.Rects != null) && (this.Rects.size() > 0))
     {
       Rect[] rects = (Rect[])this.Rects.toArray(new Rect[0]);
       this.Rects.clear();
       
       Arrays.sort(rects, new RectLayerComparer());
       for (int i = 0; i < rects.length; i++) {
         this.Rects.add(rects[i]);
       }
     }
   }
   
   protected Vector<Rect> Rects = new Vector();
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.Layout
 * JD-Core Version:    0.7.0.1
 */