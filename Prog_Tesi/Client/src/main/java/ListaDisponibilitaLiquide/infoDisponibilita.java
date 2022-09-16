package ListaDisponibilitaLiquide;

public class infoDisponibilita {
	
	private int progDisponibilita;
	private String dataUpload;
	private boolean download ;
	private String location;
	
	public infoDisponibilita() {}
	
	public infoDisponibilita(int progDisponibilita, String dataUpload, boolean download, String location) {
		super();
		this.progDisponibilita = progDisponibilita;
		this.dataUpload = dataUpload;
		this.download = download;
		this.location = location;
	}

	public int getProgDisponibilita() {
		return progDisponibilita;
	}

	public void setProgDisponibilita(int progDisponibilita) {
		this.progDisponibilita = progDisponibilita;
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
		return "{" + "progDisponibilita:'" + progDisponibilita + '\'' + ",\n" + "dataUpload:'" + dataUpload + '\''+ ",\n" + "download:'"
				+ download + '\''+ ",\n" + "location:'" + location + '\''+ ",\n" + '}';
	}
	
}
