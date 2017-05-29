 package com.gnamp.terminal.config;
 
 import java.util.Comparator;
 
 public class TimeBoundInt
 {
   public int mStart;
   public int mStop;
   public int mValue;
   
   public String toString()
   {
     return toHHmm();
   }
   
   public String toHHmm()
   {
     return String.format("[%02d:%02d,%02d:%02d,%d]", new Object[] {
       Integer.valueOf(this.mStart / 3600), Integer.valueOf(this.mStart % 3600 / 60), 
       Integer.valueOf(this.mStart / 3600), Integer.valueOf(this.mStart % 3600 / 60), 
       Integer.valueOf(this.mValue) });
   }
   
   public String toHHmmss()
   {
     return String.format("[%02d:%02d:%02d,%02d:%02d:%02d,%d]", new Object[] {
       Integer.valueOf(this.mStart / 3600), Integer.valueOf(this.mStart % 3600 / 60), Integer.valueOf(this.mStart % 60), 
       Integer.valueOf(this.mStart / 3600), Integer.valueOf(this.mStart % 3600 / 60), Integer.valueOf(this.mStart % 60), 
       Integer.valueOf(this.mValue) });
   }
   
   public static Comparator mComparator = new Comparator() {

       public int compare(TimeBoundInt o1, TimeBoundInt o2)
       {
           if(o1 == o2)
               return 0;
           if(o1 != null && o2 != null)
               return o1.mStart != o2.mStart ? o1.mStart - o2.mStart : o1.mStop - o2.mStop;
           else
               return o1 != null ? 1 : -1;
       }

       public  int compare(Object obj, Object obj1)
       {
           return compare((TimeBoundInt)obj, (TimeBoundInt)obj1);
       }

   };
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.terminal.config.TimeBoundInt
 * JD-Core Version:    0.7.0.1
 */