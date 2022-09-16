package ListaAckFlussoOrdinativi;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;



public class ListaAckFlussoOrdinativi {
	
	
	  	
	    private int numRisultati ;
	    private int numPagine ;
	    private int risultatiPerPagina;
	    private int pagina;
	    private String dataProduzioneDa;
	    private String dataProduzioneA;
	    @JsonProperty("infoAck")
	    private List<infoAck> l= new ArrayList<>();
	    
	    public ListaAckFlussoOrdinativi(int numRisultati, int numPagine, int risultatiPerPagina, int pagina,
	    								String dataProduzioneDa, String dataProduzioneA, List<infoAck> risultati){
	        this.numRisultati = numRisultati;
	        this.numPagine = numPagine;
	        this.risultatiPerPagina = risultatiPerPagina;
	        this.pagina = pagina;
	        this.dataProduzioneDa = dataProduzioneDa;
	        this.dataProduzioneA = dataProduzioneA;
	        this.l = risultati;
	    }
	    
	    public ListaAckFlussoOrdinativi(){}
	    
	    public int getNumRisultati() {return numRisultati;}
	    public int getNumPagine() {return numPagine;}
	    public int getRisultatiPerPagina() {return risultatiPerPagina;}
	    public int getPagina() {return pagina;}
	    public String getDataProduzioneDa() {return dataProduzioneDa;}
	    public String getDataProduzioneA() {return dataProduzioneA;}
	    
	    @Override
	    public String toString() {
	        String s =" {\n" + "	numRisultati:" + numRisultati + ",\n" + "	numPagine:" + numPagine + ",\n" 
	        			+ "	risultatiPerPagina:" + risultatiPerPagina + ",\n"
	        			+ "	pagina:" + pagina + ",\n" + "	dataProduzioneDa:" + dataProduzioneDa + ",\n"
	        			+ "	dataProduzioneA:" + dataProduzioneA + ",\n"
	        			+ "	rtisultati: [\n";
	     for(int i =0; i < l.size();i++){ 
	    	 s = s + l.get(i).toString();
	    	 if(l.size() != i+1) s = s +",\n";
	    	}
	      s = s + "\n	]\n}";
	       return s;
	    }
}
