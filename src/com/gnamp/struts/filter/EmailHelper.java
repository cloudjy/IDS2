 package com.gnamp.struts.filter;
 
 import java.io.PrintStream;
 import org.apache.commons.mail.EmailException;
 import org.apache.commons.mail.SimpleEmail;
 
 public class EmailHelper
 {
   private static String mSendEmail = null;
   private static String mSmtpHost = null;
   private static int mSmtpPort = 0;
   private static String mSmtpUser = null;
   private static String mSmtpPassword = null;
   private static String mRecvEmail = null;
   
   public static void initialize()
   {
     mSmtpHost = WatcherConfig.readSmtpHost();
     mSendEmail = WatcherConfig.readSendEmail();
     mSmtpUser = WatcherConfig.readSmtpUser();
     mSmtpPassword = WatcherConfig.readSmtpPassowrd();
     mSmtpPort = WatcherConfig.readSmtpPort();
     mRecvEmail = WatcherConfig.readRecvEmail();
   }
   
   public static boolean valide()
   {
     return (mSmtpHost.length() > 0) && (mSmtpPort > 0) && (mSmtpPort <= 65535) && 
       (valideEmail(mSendEmail)) && 
       (mSmtpUser.length() > 0) && (mSmtpPassword.length() > 0) && 
       (valideEmail(mRecvEmail));
   }
   
   public static String configText()
   {
     return 
     
       "smtp=" + mSmtpHost + ":" + mSmtpPort + ", \r\n" + "send_email=" + mSendEmail + ", user=" + mSmtpUser + ", port=" + mSmtpPassword + ", \r\n" + "recv_email=" + mRecvEmail;
   }
   
   private static boolean valideEmail(String text)
   {
     text = text == null ? "" : text.trim();
     String[] array = text.split("@");
     return (array.length == 2) && (array[0].length() > 0) && (array[1].length() > 0);
   }
   
   public static boolean sendEmail(String recieveEmail, String subject, String message)
   {
     try
     {
       if (!valide()) {
         return false;
       }
       recieveEmail = recieveEmail == null ? "" : recieveEmail.trim();
       if (recieveEmail.length() == 0) {
         recieveEmail = mRecvEmail;
       }
       if (!valideEmail(recieveEmail)) {
         return false;
       }
       SimpleEmail email = new SimpleEmail();
       
       email.setHostName(mSmtpHost);
       email.setSmtpPort(mSmtpPort);
       email.setFrom(mSendEmail, mSmtpUser, "UTF-8");
       email.setAuthentication(mSmtpUser, mSmtpPassword);
       
       email.addTo(recieveEmail, recieveEmail.split("@")[0], "UTF-8");
       
       email.setSubject(subject == null ? "" : subject);
       email.setMsg(message == null ? "" : message);
       
       email.send();
       return true;
     }
     catch (EmailException e)
     {
       System.out.println("The SimpleEmail send failed: " + e.getMessage());
     }
     return false;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.filter.EmailHelper
 * JD-Core Version:    0.7.0.1
 */