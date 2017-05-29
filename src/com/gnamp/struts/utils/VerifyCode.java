 package com.gnamp.struts.utils;
 
 import java.awt.Color;
 import java.awt.Font;
 import java.awt.Graphics;
 import java.awt.Graphics2D;
 import java.awt.image.BufferedImage;
 import java.util.Random;
 import org.apache.log4j.Logger;
 
 public class VerifyCode
 {
   private static final int WIDTH = 60;
   private static final int HEIGHT = 30;
   private static final int FONT_HEIGHT = 15;
   private static final int FONT_WIDTH = 15;
   private static final int FONT_LOCATION_Y = 22;
   private static final int FONT_DISTANCE = 3;
   private static final int FONT_SIZE = 18;
   
   public VerifyCode()
   {
     try
     {
       getVerifyCodeImg();
     }
     catch (Exception e)
     {
       LOG.error(e.getMessage(), e);
     }
   }
   
   protected static final Logger LOG = Logger.getLogger(VerifyCode.class);
   private static final char[] ch = "0123456789".toCharArray();
   private static final Font[] font = { getFont("Verdana"), getFont("Microsoft Sans Serif"), 
     getFont("Comic Sans MS"), getFont("Arial"), getFont("宋体") };
   private static final Color[] color = { Color.BLACK, Color.RED, Color.DARK_GRAY, 
     Color.GREEN, Color.ORANGE, Color.PINK };
   private String verifyCode;
   private BufferedImage verifyImage;
   
   public BufferedImage getVerifyImage()
   {
     return this.verifyImage;
   }
   
   public String getVerifyCode()
   {
     return this.verifyCode;
   }
   
   private static Font getFont(String fontName)
   {
     return new Font(fontName, 3, 18);
   }
   
   private void getVerifyCodeImg()
   {
     this.verifyImage = new BufferedImage(60, 30, 1);
     Graphics g = this.verifyImage.getGraphics();
     Random r = new Random();
     Color c = new Color(255, 255, 255);
     g.setColor(c);
     
     g.fillRect(0, 0, 60, 30);
     
     StringBuffer sb = new StringBuffer();
     
 
     int len = ch.length;
     double oldrot = 0.0D;
     double rot = -0.25D + Math.abs(Math.toRadians(r.nextInt(25)));
     for(int i = 0; i < 4; i++)
     {
         int index = r.nextInt(len);
         g.setColor(color[r.nextInt(color.length)]);
         g.setFont(font[r.nextInt(font.length)]);
         Graphics2D g2 = (Graphics2D)g;
         g2.rotate(-oldrot, 15D, 22D);
         oldrot = rot;
         g2.rotate(rot, i * 15 + 3, 22D);
         g.drawString((new StringBuilder()).append(ch[index]).toString(), i * 15 + 3, 22);
         sb.append(ch[index]);
     }
     this.verifyCode = sb.toString();
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.VerifyCode
 * JD-Core Version:    0.7.0.1
 */