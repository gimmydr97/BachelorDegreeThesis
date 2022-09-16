package OpWithDB;

//STEP 1. Import required packages
import java.sql.*;
import java.util.HashMap;

public class OpWithDB {
	 static  String JDBC_DRIVER;
	 static  String DB_URL;
	 static  String USER;
	 static  String PASS;
	 
	public OpWithDB() {
	 // JDBC driver name and database URL
	  JDBC_DRIVER = "sap.jdbc4.sqlanywhere.IDriver";  
	  DB_URL = "jdbc:sqlanywhere:DSN=DBS";

	 //  Database credentials
	  USER = "gianmaria";
	  PASS = "golem.123";
	}
	
	public int GET_IDENTITY(String Tab) {
		 Connection conn = null;
		 Statement stmt = null;
		 Integer i = null;
		 try{
		    //STEP 2: Register JDBC driver
		    Class.forName("sap.jdbc4.sqlanywhere.IDriver");
		
		    //STEP 3: Open a connection
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		
		    //STEP 4: Execute a query
		    stmt = conn.createStatement();
		    
		    String  sql = "SELECT GET_IDENTITY( '"+Tab+"' )";
		    ResultSet rs = stmt.executeQuery(sql);
		    rs.next();
		    i = rs.getInt(1);
		    //STEP 5: Extract data from result set
		    //STEP 6: Clean-up environment
		    rs.close();
		    stmt.close();
		    conn.close();
		    
		 }catch(SQLException se){se.printStackTrace(); }
		  catch(Exception e){e.printStackTrace(); }
		  
		 finally{
		    
			try{
		     
				if(stmt!=null) stmt.close();
				
		    }catch(SQLException se2){}
			
		    try{
		    	
		       if(conn!=null) conn.close();
		       
		    }catch(SQLException se){ se.printStackTrace(); }
		    
		 }
		 return i;
	 	}
	
	public String[] CercaOperatore(String IDOperatore, String IDEnte, String password) {
		Connection conn = null;
		 Statement stmt = null;
		 String Request = "";
		 String[] s = new String[5];
		 try{
		    //STEP 2: Register JDBC driver
		    Class.forName("sap.jdbc4.sqlanywhere.IDriver");
		
		    //STEP 3: Open a connection
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		
		    //STEP 4: Execute a query
		    stmt = conn.createStatement();
		    Request = "SELECT o.Nome, o.Cognome, e.IDEnte, e.StrutturaIPA, e.CodiceA2A  "
		    		+ "FROM Operatori AS o INNER JOIN Enti AS e ON  e.IDEnte = o.IDEnte "
		    		+ "WHERE o.IDOperatore="+IDOperatore+" AND o.IDEnte="+IDEnte+" AND o.password="+password;
		    
		    ResultSet rs = stmt.executeQuery(Request);
		 while(rs.next()) { 
			 
		  s[0] =rs.getString("Nome");
		  s[1] = rs.getString("Cognome");
		  s[2] = String.valueOf(rs.getInt("IDEnte")) ;
		  s[3] = rs.getString("StrutturaIPA");
		  s[4] = rs.getString("CodiceA2A");
		  
		 }
		    
		    //STEP 6: Clean-up environment
		    rs.close();
		    stmt.close();
		    conn.close();
		    
		 }catch(SQLException se){se.printStackTrace(); }
		  catch(Exception e){e.printStackTrace(); }
		  
		 finally{
		    
			try{
		     
				if(stmt!=null) stmt.close();
				
		    }catch(SQLException se2){}
			
		    try{
		    	
		       if(conn!=null) conn.close();
		       
		    }catch(SQLException se){ se.printStackTrace(); }
		    
		 }
		 return s;
	}
	
	public String INSERT(String Request) {
	 Connection conn = null;
	 Statement stmt = null;
	 try{
	    //STEP 2: Register JDBC driver
	    Class.forName("sap.jdbc4.sqlanywhere.IDriver");
	
	    //STEP 3: Open a connection
	    conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
	    //STEP 4: Execute a query
	    stmt = conn.createStatement();
	    
	    stmt.executeQuery(Request);
	    
	    //STEP 6: Clean-up environment
	    
	    stmt.close();
	    conn.close();
	    
	 }catch(SQLException se){se.printStackTrace(); }
	  catch(Exception e){e.printStackTrace(); }
	  
	 finally{
	    
		try{
	     
			if(stmt!=null) stmt.close();
			
	    }catch(SQLException se2){}
		
	    try{
	    	
	       if(conn!=null) conn.close();
	       
	    }catch(SQLException se){ se.printStackTrace(); }
	    
	 }
	 return "ok";
 	}
 
	public HashMap<String,byte[]> SELECTFS(String Tab, String Prog, String DataUploadDa, String DataUploadA) {
	 Connection conn = null;
	 Statement stmt = null;
	 HashMap<String,byte[]> ris = null;
	 try{
	    //STEP 2: Register JDBC driver
	    Class.forName("sap.jdbc4.sqlanywhere.IDriver");
	
	    //STEP 3: Open a connection
	    conn = DriverManager.getConnection(DB_URL,USER,PASS);
	
	    //STEP 4: Execute a query
	    stmt = conn.createStatement();
	    String sql = "SELECT * FROM " + Tab ;
	    
	    if(Prog != null || DataUploadDa!=null || DataUploadA!=null) {
	    	sql = sql + " WHERE ";
	    	if(Prog != null) { sql = sql + Prog + " AND ";}
	    	if(DataUploadDa != null) {sql = sql + DataUploadDa + " AND ";}
	    	if(DataUploadA != null)	{sql = sql + DataUploadA + " AND ";}
	    	sql = sql + " 1 = 1 ";
	    }
	    
	    ResultSet rs = stmt.executeQuery(sql);
	    
	    //STEP 5: Extract data from result set
	    ris = formaRisultSELECT(Tab,rs);
	    
	    //STEP 6: Clean-up environment
	    rs.close();
	    stmt.close();
	    conn.close();
	    
	 }catch(SQLException se){se.printStackTrace(); }
	  catch(Exception e){e.printStackTrace(); }
	  
	 finally{
	    
		try{
	     
			if(stmt!=null) stmt.close();
			
	    }catch(SQLException se2){}
		
	    try{
	    	
	       if(conn!=null) conn.close();
	       
	    }catch(SQLException se){ se.printStackTrace(); }
	    
	 }
	 return ris;
 	}
	
	public HashMap<String,byte[]> SELECTFC(String Tab, String nomefile, String DataCaricamentoDa, String DataCaricamentoA,String esercizio) {
		 Connection conn = null;
		 Statement stmt = null;
		 HashMap<String,byte[]> ris = null;
		 try{
		    //STEP 2: Register JDBC driver
		    Class.forName("sap.jdbc4.sqlanywhere.IDriver");
		
		    //STEP 3: Open a connection
		    conn = DriverManager.getConnection(DB_URL,USER,PASS);
		
		    //STEP 4: Execute a query
		    stmt = conn.createStatement();
		    String sql = "SELECT * FROM " + Tab ;
		    
		    if(nomefile != null || DataCaricamentoDa!=null || DataCaricamentoA!=null || esercizio!=null ) {
		    	sql = sql + " WHERE ";
		    	if(nomefile != null) { sql = sql + nomefile + " AND ";}
		    	if(DataCaricamentoDa != null) {sql = sql + DataCaricamentoDa + " AND ";}
		    	if(DataCaricamentoA != null)	{sql = sql + DataCaricamentoA + " AND ";}
		    	if(esercizio != null)	{sql = sql + esercizio + " AND ";}
		    	sql = sql + " 1 = 1 ";
		    }
		    System.out.println(sql);
		    ResultSet rs = stmt.executeQuery(sql);
		    
		    //STEP 5: Extract data from result set
		    ris = formaRisultSELECT(Tab,rs);
		    
		    //STEP 6: Clean-up environment
		    rs.close();
		    stmt.close();
		    conn.close();
		    
		 }catch(SQLException se){se.printStackTrace(); }
		  catch(Exception e){e.printStackTrace(); }
		  
		 finally{
		    
			try{
		     
				if(stmt!=null) stmt.close();
				
		    }catch(SQLException se2){}
			
		    try{
		    	
		       if(conn!=null) conn.close();
		       
		    }catch(SQLException se){ se.printStackTrace(); }
		    
		 }
		 return ris;
	 	}
	
	public  HashMap<String, byte[]> SELECTOPI(String Tab, String DataOrdinativoDa, String DataOrdinativoA, String esercizio,
											  String tipoOrdinativo,String tipoOperazione, String numOrdinativoDa, String numOrdinativoA) {
			Connection conn = null;
			Statement stmt = null;
			HashMap<String,byte[]> ris = null;
			 try{
			    //STEP 2: Register JDBC driver
			    Class.forName("sap.jdbc4.sqlanywhere.IDriver");
			
			    //STEP 3: Open a connection
			    conn = DriverManager.getConnection(DB_URL,USER,PASS);
			
			    //STEP 4: Execute a query
			    stmt = conn.createStatement();
			    String sql = "SELECT * FROM " + Tab ;
			    
			    if(DataOrdinativoDa!=null || DataOrdinativoA!=null || esercizio!=null || tipoOrdinativo!=null || 
			    	 tipoOperazione!=null || numOrdinativoDa!=null || numOrdinativoA!=null ) {
			    	sql = sql + " WHERE ";
			    	if(DataOrdinativoDa != null) {sql = sql + DataOrdinativoDa + " AND ";}
			    	if(DataOrdinativoA != null)	{sql = sql + DataOrdinativoA + " AND ";}
			    	if(esercizio != null)	{sql = sql + esercizio + " AND ";}
			    	if(tipoOrdinativo != null) { sql = sql + tipoOrdinativo + " AND ";}
			    	if(tipoOperazione != null) { sql = sql + tipoOperazione + " AND ";}
			    	if(numOrdinativoDa != null) {sql = sql + numOrdinativoDa + " AND ";}
			    	if(numOrdinativoA != null)	{sql = sql + numOrdinativoA + " AND ";}
			    	sql = sql + " 1 = 1 ";
			    }
			    System.out.println(sql);
			    ResultSet rs = stmt.executeQuery(sql);
			    
			    //STEP 5: Extract data from result set
			    ris = formaRisultSELECT(Tab,rs);
			    
			    //STEP 6: Clean-up environment
			    rs.close();
			    stmt.close();
			    conn.close();
			    
			 }catch(SQLException se){se.printStackTrace(); }
			  catch(Exception e){e.printStackTrace(); }
			  
			 finally{
			    
				try{
			     
					if(stmt!=null) stmt.close();
					
			    }catch(SQLException se2){}
				
			    try{
			    	
			       if(conn!=null) conn.close();
			       
			    }catch(SQLException se){ se.printStackTrace(); }
			    
			 }
			 return ris;
	}
	private HashMap<String,byte[]> formaRisultSELECT(String Tab, ResultSet rs) throws SQLException {
	 String s = "";
	 HashMap<String,byte[]> ris = new HashMap<String, byte[]>(); 
	 switch(Tab) {
	 
	 	case("FlussiCaricati"):
			while(rs.next()){
				
		     int idFlussoC  = rs.getInt(1);
		     int idEnte = rs.getInt(2);
		     String StrutturaIPA = rs.getString(3);
		     int Esercizio = rs.getInt(4);
		     String NomeFile = rs.getString(5);
		     String Stato = rs.getString(6);
		     String DataCaricamento = rs.getString(7);
		     int NumOrdPresenti = rs.getInt(8);
		     byte[]FlussoCZip = rs.getBytes(9);
		  
		    s = "IDFlussoC: " + String.valueOf(idFlussoC) +
		    		 ", IDEnte: " + String.valueOf(idEnte) +
		    		 ", StrutturaIPA: " + StrutturaIPA + 
		    		 ", Esercizio: " + Esercizio +
		    		 ", NomeFile: " + NomeFile +
		    		 ", Stato: " + Stato + 
		    		 ", DataCaricamento: " + DataCaricamento + 
		    		 ", NumeroOrdinativiPresenti: " + String.valueOf(NumOrdPresenti);
		    ris.put(s,FlussoCZip);
		    }
	 	break;
	 	
	 	case("OPI"):
	 		while(rs.next()){
	 			 int idEnte = rs.getInt(13);
			     int NumOrdinativo  = rs.getInt(1);
			     String StrutturaIPA = rs.getString(3);
			     int Esercizio = rs.getInt(4);
			     String TipoOrdinativo = rs.getString(5);
			     String Stato = rs.getString(6);
			     String Versione = rs.getString(7);
			     String DataOrdinativo = rs.getString(8);
			     float Importo = rs.getFloat(9);
			     int SubContenuti = rs.getInt(10);
			     String TipoOperazione = rs.getString(11);
			     byte[]OPIZip = rs.getBytes(12);
	
			    s = 
			    		"  IDEnte: " + String.valueOf(idEnte) +
			    		", StrutturaIPA: " + StrutturaIPA +
			    		", NumOrdinativo: " + String.valueOf(NumOrdinativo) +
			    		", Esercizio: " + String.valueOf(Esercizio) +
			    		", TipoOrdinativo: " + TipoOrdinativo +
			    		", Stato: " + Stato +
			    		", Versione: " + Versione +
			    		", DataOrdinativo: " + DataOrdinativo + 
			    		", Importo: " + String.valueOf(Importo) +
			    		", SubContenuti: " + String.valueOf(SubContenuti) +
			    		", TipoOperazione: " + TipoOperazione ;
			    ris.put(s,OPIZip);
			 }
	 	break;
	 	
	 	case("RicevuteServizio"): 
	 		
	 		while(rs.next()){
				
			     int ProgFlusso  = rs.getInt(1);
			     String StrutturaIPA = rs.getString(3);
			     String Stato = rs.getString(4);
			     String DataUpload = rs.getString(5);
			     int IDEnte = rs.getInt(6);
			     byte[]RicServizioZip = rs.getBytes(8);
			     
			    
			     s = "Ente: " + String.valueOf(IDEnte) +
			    	 ", StrutturaIPA: " + StrutturaIPA +
			     	 ", ProgFlusso: " + String.valueOf(ProgFlusso) +
			     	 ", Stato: " + Stato +
			    	 ", DataUpload: " + DataUpload;
			     ris.put(s,RicServizioZip);
			 }
	 	break;
	 
	 	case("RicevuteApplicativo"): 
	 		while(rs.next()){
				
	 			int ProgEsitoApplicativo  = rs.getInt(1);
			     String StrutturaIPA = rs.getString(3);
			     String Stato = rs.getString(4);
			     String DataUpload = rs.getString(5);
			     int IDEnte = rs.getInt(6);
			     byte[]RicApplZip = rs.getBytes(8);
			     
			    
			     s = "Ente: " + String.valueOf(IDEnte) +
			    	", StrutturaIPA: " + StrutturaIPA +
			     	", ProgEsitoApplicativo: " + String.valueOf(ProgEsitoApplicativo) +
			     	", Stato: " + Stato +
			    	", DataUpload: " + DataUpload;
			     ris.put(s,RicApplZip);
			 }
	 	break;
	 	
	 	case("GiornaliDiCassa"): 
	 		while(rs.next()){
				
	 			int ProgGiornaleCassa  = rs.getInt(1);
			     String StrutturaIPA = rs.getString(3);
			     String Stato = rs.getString(4);
			     String DataUpload = rs.getString(5);
			     int IDEnte = rs.getInt(6);
			     byte[]GiornCassaZip = rs.getBytes(8);
			     
			    
			     s = "Ente: " + String.valueOf(IDEnte) +
			    	 ", StrutturaIPA: " + StrutturaIPA +
			     	 ", ProgGiornaleCassa: " + String.valueOf(ProgGiornaleCassa) +
			     	 ", Stato: " + Stato +
			    	 ", DataUpload: " + DataUpload;
			     
			     ris.put(s,GiornCassaZip);
			 }
	 	break;
	 		
	 }
	return ris;
 }
	
 
 
}
