package ListaMessaggiEsitoApplicativo;

public class infoEsitoApplicativo {
	
	private int progEsitoApplicativo;
	private String dataUpload;
	private boolean download ;
	private String location;
	
	public infoEsitoApplicativo() {
		
	}
	public infoEsitoApplicativo(int progEsitoApplicativo, String dataUpload, boolean download, String location) {
		super();
		this.progEsitoApplicativo = progEsitoApplicativo;
		this.dataUpload = dataUpload;
		this.download = download;
		this.location = location;
	}
	public int getProgEsitoApplicativo() {
		return progEsitoApplicativo;
	}
	public void setProgEsitoApplicativo(int progEsitoApplicativo) {
		this.progEsitoApplicativo = progEsitoApplicativo;
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
		return "{" + "progEsitoApplicativo:'" + progEsitoApplicativo + '\'' + ",\n" + "dataUpload:'" + dataUpload + '\''+ ",\n" + "download:'"
				+ download + '\''+ ",\n" + "location:'" + location + '\''+ ",\n" + '}';
	}
}
