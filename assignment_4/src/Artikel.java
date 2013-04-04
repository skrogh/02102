import java.util.*;

public class Artikel {
    private String[] forfattere;
    private Artikel[] referenceliste;
    private Tidsskrift tidsskrift;
    private String titel;

    public Artikel( String[] forfattere, String titel, Tidsskrift tidsskrift ) {
        this.forfattere = forfattere;
        this.titel = titel;
        this.tidsskrift = tidsskrift;
        Artikel[] emptyRef = {};
        this.referenceliste = emptyRef;
    }

    public void setForfattere( String[] forfattere ) { this.forfattere = forfattere; }
    public String[] getForfattere() { return forfattere; }

    public void setReferenceliste( Artikel[] referenceliste ) { this.referenceliste = referenceliste; }
    public Artikel[] getReferenceliste() { return this.referenceliste; }

    public void setTidsskrift( Tidsskrift tidsskrift ) { this.tidsskrift = tidsskrift; }
    public Tidsskrift getTidsskrift() { return this.tidsskrift; }

    public void setTitel( String titel ) { this.titel = titel; }
    public String getTitel() { return this.titel; }

    public String toString() {
        String forfatterString = Arrays.toString( forfattere );
        String returnString =  "Forfatter(e) : " + forfatterString.substring( 1, forfatterString.length() - 1 ) + "\n";
        String referencelisteString = "";
        if ( referenceliste != null ) {
            for( int i = 0; i < referenceliste.length; i++ ) {
                referencelisteString += referenceliste[i].getTitel();
            }
        }
        returnString += "Referencer : " + referencelisteString + "\n";

        returnString +=  "Tidsskrift: " + tidsskrift.getTitel();

        return returnString;
    }
}
