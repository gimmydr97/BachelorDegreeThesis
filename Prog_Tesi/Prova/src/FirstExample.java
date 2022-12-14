//STEP 1. Import required packages
import java.sql.*;

public class FirstExample {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "sap.jdbc4.sqlanywhere.IDriver";  
   static final String DB_URL = "jdbc:sqlanywhere:DSN=DBS";

   //  Database credentials
   static final String USER = "gianmaria";
   static final String PASS = "golem.123";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("sap.jdbc4.sqlanywhere.IDriver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT IDEnte, nome, password, Ente FROM Cliente";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("IDEnte");
         String name = rs.getString("nome");
         String password = rs.getString("password");
         String Ente = rs.getString("Ente");

         //Display values
         System.out.print("ID: " + id);
         System.out.print(", Name: " + name);
         System.out.print(", passw: " + password);
         System.out.println(", Ente: " + Ente);
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
}//end FirstExample
