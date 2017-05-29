 package com.gnamp.struts.action;
 
 import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.gnamp.server.Storage;
import com.gnamp.server.handle.FileHandle;
import com.gnamp.server.model.File;
import com.gnamp.server.model.RectContent;
import com.gnamp.server.model.RectImage;
import com.gnamp.server.model.RectVideo;
 
 final class ImageOutputUtils
 {
   private static void close(Closeable closeable)
   {
     if (closeable != null) {
       try
       {
         closeable.close();
       }
       catch (IOException localIOException) {}
     }
   }
   
   private static void close(ImageInputStream imageInputStream)
   {
     if (imageInputStream != null) {
       try
       {
         imageInputStream.close();
       }
       catch (IOException localIOException) {}
     }
   }
   
   static void writeBufferedImage(HttpServletResponse servletResponse, BufferedImage bufferedImage)
   {
     servletResponse.setHeader("Pragma", "No-cache");
     servletResponse.setHeader("Cache-Control", "no-cache");
     servletResponse.setHeader("Cache-Control", "no-store");
     servletResponse.setDateHeader("Expires", 0L);
     if (bufferedImage != null)
     {
       ServletOutputStream outputStream = null;
       try
       {
         outputStream = servletResponse.getOutputStream();
         servletResponse.setContentType("image/png");
         ImageIO.write(bufferedImage, "png", outputStream);
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
       finally
       {
         if (outputStream != null) {
           close(outputStream);
         }
       }
     }
   }
   
   static void writeBufferedImage(HttpServletResponse servletResponse, String filePath)
   {
     servletResponse.setHeader("Pragma", "No-cache");
     servletResponse.setHeader("Cache-Control", "no-cache");
     servletResponse.setHeader("Cache-Control", "no-store");
     servletResponse.setDateHeader("Expires", 0L);
     if (StringUtils.isBlank(filePath))
     {
       BufferedInputStream inputStream = null;
       ServletOutputStream outputStream = null;
       try
       {
         inputStream = new BufferedInputStream(new FileInputStream(filePath));
         
         outputStream = servletResponse.getOutputStream();
         servletResponse.setContentType("image/jpeg");
         
         byte[] buffer = new byte[2048];
         int len = 0;
         while ((len = inputStream.read(buffer)) > 0) {
           outputStream.write(buffer, 0, len);
         }
       }
       catch (IOException e)
       {
         e.printStackTrace();
       }
       finally
       {
         if (outputStream != null) {
           close(outputStream);
         }
         if (inputStream != null) {
           close(inputStream);
         }
       }
     }
   }
   
   static BufferedImage getOutputPreviewImage(RectContent content, FileHandle fileHandle, int width, int height)
   {
     BufferedImage bufferedImage = null;
     try
     {
       if (content != null)
       {
         String filePath = null;
         int scaleType = 3;
         
         Storage storage = new Storage(fileHandle.getCstmId());
         if ((content instanceof RectImage))
         {
           scaleType = ((RectImage)content).getScaleStyle();
           File file = fileHandle.read(content.getFileId());
           if (file == null) {
        	   return bufferedImage ;
           }
           filePath = storage.getFilePath(file.getFileId());
           if (file.getFlag() == 1) {
             filePath = 
               filePath + java.io.File.separatorChar + String.format("%016X", new Object[] { Long.valueOf((content.getFileId() << 12) + 0L) });
           }
         }
         else if ((content instanceof RectVideo))
         {
           scaleType = ((RectVideo)content).getScaleStyle();
           String[] array = storage.getVideoPreviewPathes(content.getFileId(), 1);
           if ((array == null) || (array.length == 0)) {
            return bufferedImage ;
           }
           filePath = array[0];
         }
         if (!StringUtils.isBlank(filePath))
         {
           BufferedImage srcImage = readFileBufferedImage(filePath);
           if (srcImage != null) {
             if ((width > 0) && (width <= 1920) && (height > 0) && (height <= 1920))
             {
               BufferedImage destImage = 
                 new BufferedImage(width, height, srcImage.getType());
               
               Rectangle srcRect = new Rectangle(srcImage.getWidth(), srcImage.getHeight());
               Rectangle destRect = new Rectangle(width, height);
               rectScale(scaleType, srcRect, destRect);
               
               Graphics2D g = (Graphics2D)destImage.getGraphics();
               try
               {
                 g.setComposite(AlphaComposite.Src);
                 g.drawImage(srcImage, destRect.x, destRect.y, 
                   destRect.x + destRect.width, destRect.y + destRect.height, 
                   srcRect.x, srcRect.y, 
                   srcRect.x + srcRect.width, srcRect.y + srcRect.height, null);
               }
               finally
               {
                 g.dispose();
               }
               bufferedImage = destImage;
             }
             else
             {
               bufferedImage = srcImage;
             }
           }
         }
       }
     }
     catch (Exception e)
     {
       e.printStackTrace();
     }
     label425:
     return bufferedImage;
   }
   
   private static BufferedImage readFileBufferedImage(String src)
   {
     java.io.File srcFile = new java.io.File(src);
     if ((!srcFile.exists()) || (!srcFile.isFile())) {
       return null;
     }
     try
     {
       return ImageIO.read(srcFile);
     }
     catch (IIOException ex)
     {
       FileImageInputStream imageIputStream = null;
       try
       {
         imageIputStream = new FileImageInputStream(srcFile);
         ImageReader imageReader = null;
         Iterator<ImageReader> readers = ImageIO.getImageReaders(imageIputStream);
         while (readers.hasNext())
         {
           imageReader = (ImageReader)readers.next();
           if (imageReader.canReadRaster()) {
             break;
           }
           imageReader = null;
         }
         if (imageReader == null) {
           throw new IllegalStateException("get image reader failed: " + src);
         }
         imageReader.setInput(imageIputStream);
         BufferedImage srcImage = new BufferedImage(imageReader.getWidth(0), imageReader.getHeight(0), 
           2);
         srcImage.getRaster().setRect(imageReader.readRaster(0, null));
         return srcImage;
       }
       catch (IOException e1)
       {
         e1.printStackTrace();
         throw new IllegalStateException("image reader io failed: " + src, e1);
       }
       finally
       {
         if (imageIputStream != null) {
           close(imageIputStream);
         }
       }
     }
     catch (IOException ex)
     {
       ex.printStackTrace();
     }
     return null;
   }
   
   private static int rectScale(int scaleStyle, Rectangle srcRect, Rectangle destRect)
   {
     int srcW = srcRect.width;
     int srcH = srcRect.height;
     int destW = destRect.width;
     int destH = destRect.height;
     if (scaleStyle == 2)
     {
       destRect.x = (srcW < destW ? (destW - srcW) / 2 : 0);
       destRect.y = (srcH < destH ? (destH - srcH) / 2 : 0);
       destRect.width = (srcW < destW ? srcW : destW);
       destRect.height = (srcH < destH ? srcH : destH);
       
       srcRect.x = (srcW < destW ? 0 : (srcW - destW) / 2);
       srcRect.y = (srcH < destH ? 0 : (srcH - destH) / 2);
       srcRect.width = (srcW < destW ? srcW : destW);
       srcRect.height = (srcH < destH ? srcH : destH);
     }
     else if (scaleStyle == 1)
     {
       if (destW * srcH > destH * srcW)
       {
         destRect.x = ((destW - destH * srcW / srcH) / 2);
         destRect.y = 0;
         destRect.width = (destH * srcW / srcH);
         destRect.height = destH;
       }
       else
       {
         destRect.x = 0;
         destRect.y = ((destH - destW * srcH / srcW) / 2);
         destRect.width = destW;
         destRect.height = (destW * srcH / srcW);
       }
     }
     else if (scaleStyle != 3)
     {
       destRect.x = 0;
       destRect.y = 0;
       destRect.width = (srcW < destW ? srcW : destW);
       destRect.height = (srcH < destH ? srcH : destH);
       
       srcRect.x = 0;
       srcRect.y = 0;
       srcRect.width = (srcW < destW ? srcW : destW);
       srcRect.height = (srcH < destH ? srcH : destH);
     }
     return 0;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.action.ImageOutputUtils
 * JD-Core Version:    0.7.0.1
 */