package Ispector;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.codec.binary.Hex;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import ListaEsitiFlussoOrdinativi.*;
import OpWithDB.OpWithDB;


public class IspectorRicevuteServizio implements Runnable {
	
	private String[] s;
	
	public IspectorRicevuteServizio(String[] s) {
		this.s=s;
	}
	
	public void run() {
		for(int i = 0; i < 1 ; i++) {
			RestTemplate restTemplate = new RestTemplate();
			OpWithDB op = new OpWithDB();
			ResponseEntity<ListaEsitiFlussoOrdinativi>	response;
			try {	//scarico la lista delle ricevute di servizio relative al mio ente non ancora scaricate 
					response = restTemplate.exchange("http://localhost:8080/v1/"+s[4]+"/PA/"+s[2]+"/flusso/esitoflusso/download=false",
													  HttpMethod.GET,setHeaderGETList(),ListaEsitiFlussoOrdinativi.class);
																								
																								
				ListaEsitiFlussoOrdinativi list = response.getBody();
				Iterator<infoEsiti> flusso = list.getL().iterator();
				int j = 2;
				while(j != 0 /*flusso.hasNext()*/) {//per ogni elemento della lista...
					infoEsiti info  = flusso.next();
					int IDFlussoS = op.GET_IDENTITY("FlussiScaricati");
					
					//insersco una riga nella tabella FlussiScaricati
					op.INSERT("INSERT INTO FlussiScaricati (IDFlussoS, IDEnte, StrutturaIPA, Stato, DataUpload) "
							+ "VALUES ("+IDFlussoS+", "+s[2]+", '"+s[3]+"', 'ACQUISITO', '"+ info.getDataUpload()+"' )");
					//scarico la ricevuta di servizio 
					ResponseEntity<byte[]> response1 = restTemplate.exchange("http://localhost:8080/v1/"+s[4]+"/PA/"+s[2]+"/flusso/"
																			 +info.getProgFlusso()+"/esitoflusso",
							 												 HttpMethod.GET,setHeaderGETDown(), byte[].class);
							 
					//inserisco una riga nella tabella RicevuteServizio
					op.INSERT("INSERT INTO RicevuteServizio (IDFlussoS, ProgFlusso, StrutturaIPA, Stato, DataUpload, IDEnte, RicServizioZip ) "
							+ "VALUES ("+IDFlussoS+", "+info.getProgFlusso()+", '"+s[3]+"', 'ACQUISITO', '"+ info.getDataUpload()+"', "+s[2]+", 0x"+ Hex.encodeHexString(response1.getBody())+")");
					

					j--;
				}
		
				
			} catch (RestClientException | IOException e) {e.printStackTrace();}
			
		}
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
	
	
	
}
