package Client.Client;


import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry; 
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.jdom.JDOMException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import Ispector.IspectorGiornaleCassa;
import Ispector.IspectorRicevuteApplicativo;
import Ispector.IspectorRicevuteServizio;
import ListaAckFlussoOrdinativi.ListaAckFlussoOrdinativi;
import ListaDisponibilitaLiquide.ListaDisponibilitaLiquide;
import ListaEsitiFlussoOrdinativi.ListaEsitiFlussoOrdinativi;
import ListaGiornaliDiCassa.ListaGiornaliDiCassa;
import ListaMessaggiEsitoApplicativo.ListaMessaggiEsitoApplicativo;
import OpWithDB.OpWithDB;
import ReaderXML.ReaderXML;
import UploadFlussoOrdinativi.UploadFlussoOrdinativi;
import org.apache.commons.codec.binary.Hex;

@SuppressWarnings("unused")
@SpringBootApplication
public class ClientApplication {

	
	public static void main(String[] args) throws IOException, NumberFormatException, JDOMException {
		
		SpringApplication.run(ClientApplication.class, args);
		OpWithDB Op = new OpWithDB();
		String operazione = "login";
		boolean passa = false;
		String[] nc = null ;
		
		while (passa == false) {
			switch(operazione){
			
				case "login": 
					
					if(( nc = Op.CercaOperatore("2","2","'arehtbdrt'"))  == null )
							System.out.println("Registrati");
					else {
								passa = true;
								System.out.println("benvenuto "+ nc[0] + " "+ nc[1]);
							}	
				break;
				
				case "register" :
					String s = Op.INSERT("INSERT INTO Operatori (Nome, Cognome, email, password, IDEnte)"
									   + " VALUES ('gian', 'dirito', 'erh', 'arehtbdrt', 2)");
					System.out.println(s);
					if(s == "ok")
					System.out.println("Registrazione effettuata con successo");
				break;
			}
		}
	
		boolean chiudi = false;
		RestTemplate restTemplate = new RestTemplate();
		
		
		
		/*while(chiudi == false) {*/
			
			String Prog =null;
			String DataDa = null;
			String DataA = null;
			String nomefile = null;
			String esercizio = null;
			String TipoOperazione = null;
			String TipoOrdinativo = null;
			String NumOrdinativoDa = null;
			String NumOrdinativoA = null;
			HashMap<String,byte[]> ris = null;
			
			/*IspectorRicevuteServizio ispectorRS=new IspectorRicevuteServizio(nc);
			Thread threadRS = new Thread(ispectorRS);
			threadRS.start();
			
			IspectorRicevuteApplicativo ispectorRA=new IspectorRicevuteApplicativo(nc);
			Thread threadRA = new Thread(ispectorRA);
			threadRA.start();
			
			IspectorGiornaleCassa ispectorGC = new IspectorGiornaleCassa(nc);
			Thread threadGC = new Thread(ispectorGC);
			threadGC.start();*/
			
			operazione = "ListaOPIEnte";
			 switch(operazione ) {
			 	
			 	case("ImportaFlusso"):
			 		String NomeFile = "flusso_opi_importato_18517502.xml";
			 	 	ReaderXML r = new ReaderXML();
			 	 	String[] dati = r.getDati(NomeFile);
			 	 	HttpEntity<byte[]> up = setHeaderAndBodyPOST("flusso_opi_importato_18517502.zip");
			 	 	
			 	 	ResponseEntity<UploadFlussoOrdinativi> response = restTemplate.exchange("http://localhost:8080/v1/"+nc[4]+"/PA/"+nc[2]+"/flusso/",
			 	 																			HttpMethod.POST,up,UploadFlussoOrdinativi.class);
			 	
			 	 	int IDFlussoC = Op.GET_IDENTITY("FlussiCaricati");
				
					Op.INSERT("INSERT INTO FlussiCaricati (IDFlussoC, IDEnte, StrutturaIPA, Esercizio, NomeFile, Stato, DataCaricamento, NumOrdinativiPresenti, FlussoCzip)"
			 	 			+ " VALUES ("+IDFlussoC+", "+nc[2]+", '"+nc[3]+"', "+ dati[0] +", '"+NomeFile+"', 'IMPORTATO', '"+getDate()+"', "+dati[1]+", 0x"+Hex.encodeHexString( up.getBody())+")");
				
			 	 	System.out.println(r.IMPORTOPI(NomeFile, Integer.parseInt(dati[1]), IDFlussoC,Integer.parseInt(nc[2]), nc[3], dati[0]));
			 	 	
			 	 break;
			 	 
			 	case("ListaFlussiImportati"):
			 		if(nomefile != null) { nomefile = " NomeFile= " + nomefile;}
		 			if(DataDa != null) {DataDa = "DataCaricamento >= " + DataDa;}
		 			if(DataA != null)	 {DataA =  "DataCaricamento <= " + DataA;}
			 		if(esercizio != null) {esercizio = "esercizio=" + esercizio;}
			 		
			 		ris = Op.SELECTFC("FlussiCaricati",nomefile,DataDa,DataA,esercizio);
			 		if(ris!= null) {
			 			Iterator<byte[]> iterV = ris.values().iterator();
			 			Iterator<String> iterK = ris.keySet().iterator();
			 			int i = 0;
			 			while(iterK.hasNext()) {
			 				String row =(String)iterK.next();
			 				byte[] bytes =(byte[])iterV.next();
			 				System.out.println(row);
				 			createDownloadedZipFile(bytes,"FlussoCaricoato"+i+".zip");
				 			i=i+1;
			 			}
			 		}
			 	break;
			 	case("ListaOPIEnte"):
			 		
		 			if(DataDa != null) {DataDa = "DataOrdinativo >= " + DataDa;}
		 			if(DataA != null)	 {DataA =  "DataOrdinativo <= " + DataA;}
			 		if(esercizio != null) {esercizio = "esercizio=" + esercizio;}
			 		if(TipoOrdinativo != null) {TipoOrdinativo = "TipoOrdinativo=" + TipoOrdinativo;}
			 		if(TipoOperazione != null) {TipoOperazione = "TipoOperazione=" + TipoOperazione;}
			 		if(NumOrdinativoDa != null) {NumOrdinativoDa = "NumOrdinativo >= " + NumOrdinativoDa;}
		 			if(NumOrdinativoA != null)	 {NumOrdinativoA =  "NumOrdinativo <= " + NumOrdinativoA;}
		 			
			 		ris = Op.SELECTOPI("OPI",DataDa,DataA,esercizio,TipoOrdinativo,TipoOperazione,NumOrdinativoDa,NumOrdinativoA);
			 		if(ris!= null) {
			 			Iterator<byte[]> iterV = ris.values().iterator();
			 			Iterator<String> iterK = ris.keySet().iterator();
			 			int i = 0;
			 			while(iterK.hasNext()) {
			 				String row =(String)iterK.next();
			 				byte[] bytes =(byte[])iterV.next();
			 				System.out.println(row);
				 			createDownloadedZipFile(bytes,"OPI"+i+".xml");
				 			i=i+1;
			 			}
			 		}
			 	break;
			 	case("RicevuteServizio"):
			 		
			 		if(Prog != null) { Prog = " ProgFlusso= " + Prog;}
			 		if(DataDa != null) {DataDa = "DataUpload >= " + DataDa;}
			 		if(DataA != null)	 {DataA =  "DataUpload <= " + DataA;}
			 		
			 		ris = Op.SELECTFS("RicevuteServizio",Prog,DataDa,DataA);
			 		if(ris!= null) {
			 			Iterator<byte[]> iterV = ris.values().iterator();
			 			Iterator<String> iterK = ris.keySet().iterator();
			 			int i = 0;
			 			while(iterK.hasNext()) {
			 				
			 				String row =(String)iterK.next();
			 				byte[] bytes =(byte[])iterV.next();
			 				System.out.println(row);
				 			createDownloadedZipFile(bytes,"RicevutaServizio"+i+".zip");
				 			i=i+1;
			 			}
			 			
			 		}
			 	
			 	break;
			 	
			 	case("RicevuteApplicativo"):
			 		
			 		if(Prog != null) { Prog = " ProgFlusso= " + Prog;}
		 		if(DataDa != null) {DataDa = "DataUpload >= " + DataDa;}
		 		if(DataA != null)	 {DataA =  "DataUpload <= " + DataA;}
		 		
		 		ris = Op.SELECTFS("RicevuteApplicativo",Prog,DataDa,DataA);
		 		if(ris!= null) {
		 			Iterator<byte[]> iterV = ris.values().iterator();
		 			Iterator<String> iterK = ris.keySet().iterator();
		 			int i = 0;
		 			while(iterK.hasNext()) {
		 				
		 				String row =(String)iterK.next();
		 				byte[] bytes =(byte[])iterV.next();
		 				System.out.println(row);
			 			createDownloadedZipFile(bytes,"RicevutAApplicativo"+i+".zip");
			 			i=i+1;
		 			}
		 			
		 		}
			 	break;
			 	
			 	case("GiornaliDiCassa"):
			 		
			 		if(Prog != null) { Prog = " ProgFlusso= " + Prog;}
			 		if(DataDa != null) {DataDa = "DataUpload >= " + DataDa;}
			 		if(DataA != null)	 {DataA =  "DataUpload <= " + DataA;}
			 		
			 		ris = Op.SELECTFS("GiornaliDiCassa",Prog,DataDa,DataA);
			 		if(ris!= null) {
			 			Iterator<byte[]> iterV = ris.values().iterator();
			 			Iterator<String> iterK = ris.keySet().iterator();
			 			int i = 0;
			 			while(iterK.hasNext()) {
			 				
			 				String row =(String)iterK.next();
			 				byte[] bytes =(byte[])iterV.next();
			 				System.out.println(row);
				 			createDownloadedZipFile(bytes,"GiornaleDiCassa"+i+".zip");
				 			i=i+1;
			 			}
		 			
			 		}
			 	break;
		     //////////////////////////////////////
		
		
			/* System.out.println("prova ListaAckFlussoOrdinativi");
			 ResponseEntity<ListaAckFlussoOrdinativi> response1 = restTemplate.exchange("http://localhost:8080/v1/100/PA/101/flusso/ack/"
																					    +"dataProduzioneDa=a&dataProduzioneA=a&download=false&pagina=1",
																						  HttpMethod.GET,
																						  setHeaderGETList(),
																						  ListaAckFlussoOrdinativi.class);
			 System.out.println(response1.getBody().toString());
			
			 System.out.println("prova ListaEsitiFlussoOrdinativi");
			 ResponseEntity<ListaEsitiFlussoOrdinativi> response2 = restTemplate.exchange("http://localhost:8080/v1/100/PA/101/flusso/esitoflusso/"
																						  +"dataUploadDa=a&dataUploadA=a&download=false&pagina=1",
																							HttpMethod.GET,
																							setHeaderGETList(),
																							ListaEsitiFlussoOrdinativi.class);
			System.out.println(response2.getBody().toString());
				
			System.out.println("prova ListaMessaggiEsitoApplicativo");
			ResponseEntity<ListaMessaggiEsitoApplicativo> response3 = restTemplate.exchange("http://localhost:8080/v1/100/PA/101/esitoapplicativo/"
																						  +"dataUploadDa=a&dataUploadA=a&download=false&pagina=1",
																							HttpMethod.GET,
																							setHeaderGETList(),
																							ListaMessaggiEsitoApplicativo.class);
			System.out.println(response3.getBody().toString());
			
			System.out.println("prova ListaGiornaliDiCassaRelativi");
			ResponseEntity<ListaGiornaliDiCassa> response4 = restTemplate.exchange("http://localhost:8080/v1/100/PA/101/giornale/"
																						   +"dataUploadDa=a&dataUploadA=a&download=false&pagina=1",
																							 HttpMethod.GET,
																							 setHeaderGETList(),
																							 ListaGiornaliDiCassa.class);
			System.out.println(response4.getBody().toString());
			
			System.out.println("prova ListaDisponibilitaLiquide");
			ResponseEntity<ListaDisponibilitaLiquide> response5 = restTemplate.exchange("http://localhost:8080/v1/100/PA/101/disponibilita/"
																						   +"dataUploadDa=a&dataUploadA=a&download=false&pagina=1",
																							 HttpMethod.GET,
																							 setHeaderGETList(),
																							 ListaDisponibilitaLiquide.class);
			System.out.println(response5.getBody().toString());
			//////////////////////////////////////
			
			ResponseEntity<byte[]> response6 = restTemplate.exchange("http://localhost:8080/v1/101/PA/103/flusso/104/ack",
																	 HttpMethod.GET,
																	 setHeaderGETDown(),
																	 byte[].class);
			createDownloadedZipFile(response6);
			
			ResponseEntity<byte[]> response7 = restTemplate.exchange("http://localhost:8080/v1/101/PA/103/flusso/104/esitoflusso",
																	 HttpMethod.GET,
																	 setHeaderGETDown(),
																	 byte[].class);
			createDownloadedZipFile(response7);
			
			ResponseEntity<byte[]> response8 = restTemplate.exchange("http://localhost:8080/v1/101/PA/103/esitoapplicativo/104",
																	 HttpMethod.GET,
																	 setHeaderGETDown(),
																	 byte[].class);
			createDownloadedZipFile(response8);
			
			ResponseEntity<byte[]> response9 = restTemplate.exchange("http://localhost:8080/v1/101/PA/103/giornale/104",
					 												 HttpMethod.GET,
					 												 setHeaderGETDown(),
					 												 byte[].class);
			createDownloadedZipFile(response9);
			
			ResponseEntity<byte[]> response10 = restTemplate.exchange("http://localhost:8080/v1/101/PA/103/disponibilita/104",
					 												  HttpMethod.GET,
					 												  setHeaderGETDown(),
					 												  byte[].class);
			createDownloadedZipFile(response10);*/
			 }
	/*	}	*/
	}
	
	//funzioni ausiliarie 
	public static HttpEntity<byte[]> setHeaderAndBodyPOST(String namefile) throws IOException{
		//set header
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType(MediaType.APPLICATION_JSON, Charset.defaultCharset())));
		headers.setContentType(new MediaType("application", "zip"));
		headers.setContentLength(10000000);
		headers.set("Host","<siope+>");
		//set body
		FileInputStream is = new FileInputStream(namefile); 
		ZipInputStream zis = new ZipInputStream(is);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
	    ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
	    
	    ZipEntry ingresso;
		while ( ( ingresso = zis.getNextEntry()) != null  ) { 
			
				zipOutputStream.putNextEntry(new ZipEntry(ingresso.getName()));
				FileInputStream fileInputStream = new FileInputStream(ingresso.getName());
			
				IOUtils.copy(fileInputStream, zipOutputStream);
				fileInputStream.close();
				zipOutputStream.closeEntry();
	        }

	        if (zipOutputStream != null) {
	            zipOutputStream.finish();
	            zipOutputStream.flush();
	            IOUtils.closeQuietly(zipOutputStream);
	        }
	        IOUtils.closeQuietly(bufferedOutputStream);
	        IOUtils.closeQuietly(byteArrayOutputStream);
	      is.close();
	      zis.close();
		return new HttpEntity<byte[]>( byteArrayOutputStream.toByteArray(),headers);
	}
	
	public static HttpEntity<String> setHeaderGETDown() throws IOException{
		HttpHeaders headersdf = new HttpHeaders();
		headersdf.setAccept(Arrays.asList(new MediaType("application", "zip")));
		headersdf.set("Host","<siope+>");
		return new HttpEntity<String>(headersdf);
	}
	
	public static HttpEntity<String> setHeaderGETList()throws IOException{
		HttpHeaders headersdf = new HttpHeaders();
		headersdf.setAccept(Arrays.asList(new MediaType(MediaType.APPLICATION_JSON, Charset.defaultCharset())));
		headersdf.set("Host","<siope+>");
		return new HttpEntity<String>(headersdf);
	}
	
	public static void createDownloadedZipFile(byte[] bytes,String fileName) throws IOException {
		
		File f = new File(fileName);
		f.createNewFile();
		FileOutputStream fis = new FileOutputStream(f);
		fis.write(bytes);
		fis.flush();
		fis.close();
	}
	
	public static  String getDate() {
		  Calendar calendar = new GregorianCalendar();
		  int ore = calendar.get(Calendar.HOUR)+1;
		  int minuti = calendar.get(Calendar.MINUTE)+1;
		  int secondi = calendar.get(Calendar.SECOND);
		  int millis = calendar.get(Calendar.MILLISECOND);
		  int giorno = calendar.get(Calendar.DAY_OF_MONTH);
		  int mese = calendar.get(Calendar.MONTH)+1;
		  int anno = calendar.get(Calendar.YEAR);
		  return anno + "-" + mese + "-" + giorno + " " + ore +
				 ":" + minuti + ":" + secondi + "." + millis;
	}

}
