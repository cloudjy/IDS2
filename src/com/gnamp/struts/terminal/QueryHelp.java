 package com.gnamp.struts.terminal;
 
 import com.gnamp.server.db.DbShell;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 import java.util.ArrayList;
 import java.util.Collection;
 import java.util.HashMap;
 import java.util.Iterator;
 import java.util.List;
 import org.springframework.jdbc.core.RowMapper;
 
 public class QueryHelp
 {
   public static List<Status> readAll(Collection<Long> idCollection)
   {
     List<Status> allResult = new ArrayList();
     if ((idCollection == null) || (idCollection.size() <= 0)) {
       return allResult;
     }
     DbShell shell = new DbShell();
     
     int count = 0;
     String idString = "";
     for (Iterator localIterator = idCollection.iterator(); localIterator.hasNext();)
     {
       long id = ((Long)localIterator.next()).longValue();
       if (0L != id)
       {
         idString = idString + (idString.length() > 0 ? "," : "") + id;
         count++;
         if ((count >= 64) && (idString.length() > 0))
         {
           _query(shell, idString, allResult);
           idString = "";
           count = 0;
         }
       }
     }
     if ((count > 0) && (idString.length() > 0)) {
       _query(shell, idString, allResult);
     }
     return allResult;
   }
   
   private static List<Status> _query(DbShell shell, String idString, List<Status> result)
   {
     if ((result == null) || (idString.length() == 0) || (result == null)) {
       return result;
     }
     String query = "SELECT DEV_ID, NAME, ONLINE_STATE, LOGON_TIME, LOGOFF_TIME FROM tb_terminal WHERE DEV_ID IN (" + 
       idString + ")";
     
     RowMapper<Status> rowMapper = new RowMapper()
     {
       public Status mapRow(ResultSet rs, int rowNum)
         throws SQLException
       {
         Status terminal = new Status();
         terminal.setDeviceId(rs.getLong("DEV_ID"));
         terminal.setDeviceName(rs.getString("NAME"));
         terminal.setOnlineState(rs.getInt("ONLINE_STATE"));
         terminal.setLogonTime(rs.getTimestamp("LOGON_TIME"));
         terminal.setLogoffTime(rs.getTimestamp("LOGOFF_TIME"));
         return terminal;
       }
     };
     List<Status> l = shell.query(query, new HashMap(), rowMapper);
     if ((l != null) && (l.size() > 0)) {
       result.addAll(l);
     }
     return result;
   }
 }


/* Location:           C:\Users\Administrator\Desktop\0\gnamp-struts.jar
 * Qualified Name:     com.gnamp.struts.terminal.QueryHelp
 * JD-Core Version:    0.7.0.1
 */