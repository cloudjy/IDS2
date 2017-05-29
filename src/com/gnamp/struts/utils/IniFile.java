 package com.gnamp.struts.utils;
 
 import java.io.BufferedReader;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public class IniFile
 {
   public static String getProfileString(String file, String section, String variable, String defaultValue)
     throws IOException
   {
     FileInputStream inputStream = null;
     UnicodeReader unicodeReader = null;
     BufferedReader bufferedReader = null;
     try
     {
       inputStream = new FileInputStream(file);
       unicodeReader = new UnicodeReader(inputStream, null);
       bufferedReader = new BufferedReader(unicodeReader);
       
       Pattern patternFind = Pattern.compile("\\[\\s*" + section + "\\s*\\]");
       Pattern patternSection = Pattern.compile("\\[.*\\]");
       
       String lineString = "";
       String valueString = "";
       
       boolean isInFindSection = false;
       while ((lineString = bufferedReader.readLine()) != null)
       {
         lineString = lineString.trim();
         if (patternSection.matcher(lineString).find())
         {
           isInFindSection = patternFind.matcher(lineString).find();
         }
         else if (isInFindSection)
         {
           String[] strArray = lineString.split("=");
           if ((strArray != null) && (strArray.length >= 2) && (strArray[0] != null))
           {
             valueString = strArray[0].trim();
             if (valueString.equalsIgnoreCase(variable))
             {
               valueString = lineString.substring(lineString.indexOf("=") + 1).trim();
               return valueString;
             }
           }
         }
       }
     }
     finally
     {
       try
       {
         if (bufferedReader != null) {
           bufferedReader.close();
         } else if (unicodeReader != null) {
           unicodeReader.close();
         } else if (inputStream != null) {
           inputStream.close();
         }
       }
       catch (IOException localIOException1) {}
     }
     try
     {
       if (bufferedReader != null) {
         bufferedReader.close();
       } else if (unicodeReader != null) {
         unicodeReader.close();
       } else if (inputStream != null) {
         inputStream.close();
       }
     }
     catch (IOException localIOException2) {}
     return defaultValue;
   }
   
   public static boolean setProfileString(String file, String section, String variable, String value)
     throws IOException
   {
     String bomEncoding = null;
     String fileContent = "";
     
     FileInputStream inputStream = null;
     UnicodeReader unicodeReader = null;
     BufferedReader bufferedReader = null;
     try
     {
       inputStream = new FileInputStream(file);
       unicodeReader = new UnicodeReader(inputStream, null);
       bufferedReader = new BufferedReader(unicodeReader);
       
       Pattern patternFind = Pattern.compile("\\[\\s*" + section + "\\s*\\]");
       Pattern patternSection = Pattern.compile("\\[.*\\]");
       
       boolean valueSaved = false;
       
       String lineString = "";
       boolean isInFindSection = false;
       while ((lineString = bufferedReader.readLine()) != null)
       {
         String trimLine = lineString.trim();
         if (patternSection.matcher(trimLine).find())
         {
           isInFindSection = patternFind.matcher(trimLine).find();
         }
         else if (isInFindSection)
         {
           String[] strArray = trimLine.split("=");
           if ((strArray != null) && (strArray.length >= 2) && (strArray[0] != null))
           {
             String valueString = strArray[0].trim();
             if (valueString.equalsIgnoreCase(variable))
             {
               lineString = variable + "=" + value;
               valueSaved = true;
             }
           }
         }
         fileContent = fileContent + lineString + "\r\n";
       }
       if (!valueSaved)
       {
         fileContent = fileContent + "[" + section + "]\r\n";
         fileContent = fileContent + variable + "=" + value + "\r\n";
       }
       bomEncoding = unicodeReader.getBomEncoding();
       if (bomEncoding != null) {
         bomEncoding = bomEncoding.trim();
       }
     }
     finally
     {
       try
       {
         if (bufferedReader != null) {
           bufferedReader.close();
         } else if (unicodeReader != null) {
           unicodeReader.close();
         } else if (inputStream != null) {
           inputStream.close();
         }
       }
       catch (IOException localIOException) {}
     }
     FileOutputStream outputStream = null;
     try
     {
       byte[] bom = null;
       if ((bomEncoding != null) && (bomEncoding.length() > 0)) {
         if (bomEncoding.equalsIgnoreCase("UTF-32BE")) {
           bom = new byte[] { 0, 0, -2, -1 };
         } else if (bomEncoding.equalsIgnoreCase("UTF-32LE")) {
           bom = new byte[] { -1, -2 };
         } else if (bomEncoding.equalsIgnoreCase("UTF-8")) {
           bom = new byte[] { -17, -69, -65 };
         } else if (bomEncoding.equalsIgnoreCase("UTF-16BE")) {
           bom = new byte[] { -2, -1 };
         } else if (bomEncoding.equalsIgnoreCase("UTF-16LE")) {
           bom = new byte[] { -1, -2 };
         }
       }
       outputStream = new FileOutputStream(file, false);
       if ((bom != null) && (bom.length > 0)) {
         outputStream.write(bom);
       }
       if ((bomEncoding != null) && (bomEncoding.length() > 0)) {
         outputStream.write(fileContent.getBytes(bomEncoding));
       } else {
         outputStream.write(fileContent.getBytes());
       }
       return true;
     }
     finally
     {
       try
       {
         if (outputStream != null) {
           outputStream.close();
         }
       }
       catch (IOException localIOException3) {}
     }
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.utils.IniFile
 * JD-Core Version:    0.7.0.1
 */