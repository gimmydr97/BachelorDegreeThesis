package ListaEsitiFlussoOrdinativi;


	import java.util.ArrayList;
	import java.util.List;
	import com.fasterxml.jackson.annotation.JsonProperty;



	
	public class ListaEsitiFlussoOrdinativi {
		
		  	
		    private int numRisultati ;
		    private int numPagine ;
		    private int risultatiPerPagina;
		    private int pagina;
		    private String dataUploadDa;
		    private String dataUploadA;
		    @JsonProperty("infoEsiti")
		    private List<infoEsiti> l= new ArrayList<>();
		    
		    public ListaEsitiFlussoOrdinativi() {}
		    
		    public ListaEsitiFlussoOrdinativi(int numRisultati, int numPagine, int risultatiPerPagina, int pagina,
					String dataUploadDa, String dataUploadA, List<infoEsiti> l) {
				super();
				this.numRisultati = numRisultati;
				this.numPagine = numPagine;
				this.risultatiPerPagina = risultatiPerPagina;
				this.pagina = pagina;
				this.dataUploadDa = dataUploadDa;
				this.dataUploadA = dataUploadA;
				this.l = l;
			}



			public int getNumRisultati() {
				return numRisultati;
			}



			public void setNumRisultati(int numRisultati) {
				this.numRisultati = numRisultati;
			}



			public int getNumPagine() {
				return numPagine;
			}



			public void setNumPagine(int numPagine) {
				this.numPagine = numPagine;
			}



			public int getRisultatiPerPagina() {
				return risultatiPerPagina;
			}



			public void setRisultatiPerPagina(int risultatiPerPagina) {
				this.risultatiPerPagina = risultatiPerPagina;
			}



			public int getPagina() {
				return pagina;
			}



			public void setPagina(int pagina) {
				this.pagina = pagina;
			}



			public String getDataUploadDa() {
				return dataUploadDa;
			}



			public void setDataUploadDa(String dataUploadDa) {
				this.dataUploadDa = dataUploadDa;
			}



			public String getDataUploadA() {
				return dataUploadA;
			}



			public void setDataUploadA(String dataUploadA) {
				this.dataUploadA = dataUploadA;
			}



			public List<infoEsiti> getL() {
				return l;
			}



			public void setL(List<infoEsiti> l) {
				this.l = l;
			}



			@Override
		    public String toString() {
		        String s ="{\n" + "numRisultati:" + numRisultati + ",\n" + "numPagine:" + numPagine + ",\n" 
		        			+ "risultatiPerPagina:" + risultatiPerPagina + ",\n"
		        			+ "pagina:" + pagina + ",\n" + "dataUploadDa:" + dataUploadDa + ",\n"
		        			+ "dataUploadA:" + dataUploadA + ",\n"
		        			+ "rtisultati: [\n";
		     for(int i =0; i < l.size();i++){ 
		    	 s = s + l.get(i).toString();
		    	 if(l.size() != i) s = s +",";
		    	}
		      s = s + "]";
		       return s;
		    }
	}

