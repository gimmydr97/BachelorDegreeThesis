package ListaAckFlussoOrdinativi;

public class infoAck {
	
	private int progFlusso;
	private String dataProduzione;
	private boolean download ;
	private String location;
	
	public infoAck() {}
	
	public infoAck(	int progFlusso , String dataProduzione , boolean download, String location) {
		this.progFlusso = progFlusso ;
		this.dataProduzione = dataProduzione;
		this.download = download;
		this.location = location ;
	}
	
	public int getProgFlusso() { return progFlusso;}
	public String getdataProduzione() {return dataProduzione;}
	public boolean getDownload() {return download;}
	public String getLocation() {return location;}
	
	@Override
	public String toString() {
		return "\n	 {\n" + "		progFlusso:'" + progFlusso + '\'' + ",\n" + "		dataProduzione:'" + dataProduzione + '\''+ ",\n"
				+ "		download:'" + download + '\''+ ",\n" + "		location:'" + location + '\''+ ",\n" + "	 }";
	}
}
