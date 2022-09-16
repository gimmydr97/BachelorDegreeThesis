package ReaderXML;

import java.io.*;
import java.util.*;

import org.apache.commons.codec.binary.Hex;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import OpWithDB.OpWithDB;



public class ReaderXML {
	
	  public ReaderXML() {}
	  
	  public String[]  getDati(String nameFile){
		  
		  String[] s = new String[2];
		  
		  try {
	    	
		      //Creo un SAXBuilder e con esco costruisco un document
		      SAXBuilder builder = new SAXBuilder();
		      File f = new File(nameFile);
		      Document document = builder.build(f);
		      
		      //Prendo la radice
		      Element root = document.getRootElement();
		     
		      //Estraggo i figli dalla radice
		      List<?>children = root.getChildren();
		      Iterator<?> iterator = children.iterator();
		      
		      //scorro la testata che e' sempre il primo figlio di questo tipo di file xml
		      Element item = (Element)iterator.next();
		      
		      //arrivo al child esercizio
		      item = (Element)iterator.next();
		      s[0]=item.getText();
		      
		      //conto il numero di mandati
		      int i = 0;
		      while(iterator.hasNext()){
		    	item = (Element)iterator.next();
		        i=i+1;
		      }
		      s[1] = String.valueOf(i);
		    }
		  catch (Exception e) {
			  System.err.println("Errore durante la lettura dal file");
			  e.printStackTrace();
		  }
		  return s;
	  }
	  
	  public String IMPORTOPI(String fileName, int nOpi, int IDFlussoC,int IDEnte, String StrutturaIPA, String Esercizio) throws JDOMException {
		for(int j = 0 ; j < nOpi ; j++) {
			  try {
			  
				  //Creo un SAXBuilder e con esso costruisco un document
			      SAXBuilder builder = new SAXBuilder();
			      File f = new File(fileName);
			      Document documentR = builder.build(f);
			      //Creazione dell'oggetto XMLOutputter
			      XMLOutputter outputter = new XMLOutputter();
			      //Imposto il formato dell'outputter come "bel formato"
			      outputter.setFormat(Format.getPrettyFormat());
			      //inizializzo un fos
			      FileOutputStream FOS = new FileOutputStream("versione_ordinativo_temp_"+j+".xml");
			      //produco l'output sul file scelto
			      outputter.output(documentR, FOS);
			      //chiudo il fos
			      FOS.flush();
			      FOS.close();
			      //creo un nuovo document che conterra' un unico opi 
			      File OPI = new File("versione_ordinativo_temp_"+j+".xml");
			      Document documentOPI = builder.build(OPI);
			      
			      //Prendo la radice
			      Element root = documentOPI.getRootElement();
			     
			      //Estraggo i figli dalla radice
			      List<?>children = root.getChildren();
			      Iterator<?> iterator = children.iterator();
			      Element item=null;
			      //scorro la testata che e' sempre il primo figlio di questo tipo di file xml
			       item = (Element)iterator.next();
			      //scorro il child esercizio
			      item = (Element)iterator.next();
			      //aggiungo alla listaDaRimuovere tutti i child mandato o reversale tranne il child j 
			      int i = 0;
			      HashMap<Integer,Element> listaDaRimuovere = new HashMap<Integer,Element>();
			      
			      while(iterator.hasNext()){
			    	  
			    	item = (Element)iterator.next();
			    	if(j != i) {listaDaRimuovere.put(i,item);}
			        i=i+1;
			       
			       }
			      //rimuovo tutti i child della listaDaRimuovere
			      for(i=0; i <= listaDaRimuovere.size(); i++) {
			    	   if(j != i) {
				    		root.removeContent(listaDaRimuovere.get(i));
				    	}
			      }   
			     String  TipoOrdinativo, TipoOperazione, DataOrdinativo ;
			     float Importo;
			     byte[] Filexml;
			     List<?>children1 = root.getChildren();
			     Iterator<?> iterator1 = children1.iterator();
			     
			     //scorro la testata che e' sempre il primo figlio di questo tipo di file xml
			     item = (Element)iterator1.next();
			     //scorro il child esercizio
			     item = (Element)iterator1.next();
			     //arrivo al mandato o al reversale
			     item = (Element)iterator1.next();
			     //setto il tipo di opi
			     TipoOrdinativo = item.getName();
			     //setto il tipo dell'ordinativo
 			     TipoOperazione = item.getChildText("tipo_operazione");
			     //setto la data dell'ordinativo
 			     DataOrdinativo = item.getChildText("data_"+TipoOrdinativo);
 			     //setto l'importo dell'opi
 			     Importo =Float.parseFloat(item.getChildText("importo_"+TipoOrdinativo));
 			     //inizializzo un oggetto di tipo OpWithDB
			     OpWithDB op  = new OpWithDB();
			     //creo un nuovo fos 
			     FileOutputStream FOS1 = new FileOutputStream("versione_ordinativo_temp_"+j+".xml");
			     //modofico il file aggiornandolo al nuovo documentOPI
			     outputter.output(documentOPI, FOS1);
			     //chiudo il fos
			     FOS1.flush();
			     FOS1.close();
			     //salvo il file in formato byte[] e lo elimino 
			     Filexml = fileToByte("versione_ordinativo_temp_"+j+".xml");
			    
			     //iserisco l'opi nel database
			     op.INSERT("INSERT INTO OPI (IDFlussoC, IDEnte, StrutturaIPA, Esercizio, TipoOrdinativo, TipoOperazione, Stato, Versione, DataOrdinativo, Importo, SubContenuti, OPIxml)"
			      		  + " VALUES ("+IDFlussoC+", "+IDEnte+", '"+StrutturaIPA+"', '"+Esercizio+"' ,'"+TipoOrdinativo+"', '"+TipoOperazione+"',"
			      		  		+ " 'IMPORTATO', 1, '"+DataOrdinativo+"', "+Importo+", 1, 0x"+Hex.encodeHexString(Filexml)+")");
			     
			      
			      
			    }
			    catch (IOException e) {
			      System.err.println("Errore durante il parsing del documento");
			      e.printStackTrace();
			    }
			}
				return "ok";
				
	  }
	  
	  byte[] fileToByte(String namefile) throws IOException {
		 
		  File file = new File(namefile);
		  //init array with file length
		  byte[] bytesArray = new byte[(int) file.length()]; 

		  FileInputStream fis = new FileInputStream(file);
		  fis.read(bytesArray); 
		  fis.close();
		  file.delete();
		  return bytesArray;
		  
		
	
		
	 }
	  
	
}

