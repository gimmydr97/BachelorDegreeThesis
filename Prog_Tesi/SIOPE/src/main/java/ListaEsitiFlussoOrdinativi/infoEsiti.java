package ListaEsitiFlussoOrdinativi;

public class infoEsiti{
	
	private int progFlusso;
	private String dataUpload;
	private boolean download ;
	private String location;
	
	public infoEsiti(){}
	
	public infoEsiti(int progFlusso, String dataUpload, boolean download, String location) {
		super();
		this.progFlusso = progFlusso;
		this.dataUpload = dataUpload;
		this.download = download;
		this.location = location;
	}



	public int getProgFlusso() {
		return progFlusso;
	}



	public void setProgFlusso(int progFlusso) {
		this.progFlusso = progFlusso;
	}



	public String getDataUpload() {
		return dataUpload;
	}



	public void setDataUpload(String dataUpload) {
		this.dataUpload = dataUpload;
	}



	public boolean isDownload() {
		return download;
	}



	public void setDownload(boolean download) {
		this.download = download;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}



	@Override
	public String toString() {
		return "{" + "progFlusso:'" + progFlusso + '\'' + ",\n" + "dataUpload:'" + dataUpload + '\''+ ",\n" + "download:'"
				+ download + '\''+ ",\n" + "location:'" + location + '\''+ ",\n" + '}';
	}
}