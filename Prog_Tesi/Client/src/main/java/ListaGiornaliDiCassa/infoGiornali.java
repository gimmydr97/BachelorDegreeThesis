package ListaGiornaliDiCassa;

public class infoGiornali {
	
	private int progGiornale;
	private String dataUpload;
	private boolean download ;
	private String location;
	
	public infoGiornali() {}
	
	public infoGiornali(int progGiornale, String dataUpload, boolean download, String location) {
		super();
		this.progGiornale = progGiornale;
		this.dataUpload = dataUpload;
		this.download = download;
		this.location = location;
	}

	public int getProgGiornale() {
		return progGiornale;
	}

	public void setProgGiornale(int progGiornale) {
		this.progGiornale = progGiornale;
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
		return "{" + "progGiornale:'" + progGiornale + '\'' + ",\n" + "dataUpload:'" + dataUpload + '\''+ ",\n" + "download:'"
				+ download + '\''+ ",\n" + "location:'" + location + '\''+ ",\n" + '}';
	}
	
}
