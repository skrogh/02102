
public class Tidsskrift {

	private String titel;
	private String ISSN;
	private Forlag forlag;
	
	public Tidsskrift( String titel ) {
		this.titel = titel;
	}
	
	public void setISSN( String ISSN ) { this.ISSN = ISSN; }
	public String getISSN() { return this.ISSN; }
	
	public void setTitel( String titel ) { this.titel = titel; }
	public String getTitel() { return this.titel; }
	
	public void setForlag( Forlag forlag ) { this.forlag = forlag; }
	public Forlag getForlag() { return this.forlag; }
	
}
