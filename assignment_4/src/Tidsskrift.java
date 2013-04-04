
public class Tidsskrift {

	private String titel;
	private String ISSN;
	private Forlag forlag;
	
	public Tidsskrift( String titel ) {
		this.titel = titel;
        this.ISSN = "";
        this.forlag = new Forlag( "", "" );
	}
	public void setISSN( String ISSN ) { this.ISSN = ISSN; }
	public String getISSN() { return this.ISSN; }
	
	public void setTitel( String titel ) { this.titel = titel; }
	public String getTitel() { return this.titel; }
	
	public void setForlag( Forlag forlag ) { this.forlag = forlag; }
	public Forlag getForlag() { return this.forlag; }
	
    public String toString() {
        String returnString =  "Titel: " + titel + "\n";
        returnString += "ISSN: " + ISSN + "\n";
        returnString += "Forlag: " + ( ( forlag == null ) ? "" : forlag.getName() );

        return returnString;
    }
}
