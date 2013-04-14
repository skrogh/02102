//===========================================
//Forlag
// Indeholder navn på - og geografisk pla-
// cering af et forlag.
// Carsten Nielsen s123161 & Søren Krogh 
// Andersen s123369
//============================================

public class Forlag {

	private String navn;
	private String sted;
	
	public Forlag( String navn, String sted ) {
		this.navn = navn;
		this.sted = sted;
	}

    public String getNavn() { return this.navn; }
    public String getSted() { return this.sted; }
    
    public void setNavn( String navn ) { this.navn = navn; }
    public void setSted( String sted ) { this.sted = sted; }

    public String toString() {
        String returnString = "Navn: " + navn + "\n";
        returnString += "Sted: " + sted;
        
        return returnString;
    }
	
}
