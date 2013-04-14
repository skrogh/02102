
public class ArtikelTest {
    
    public static void main( String[] args ) {
        String[] forfattere = { "fornavn efternavn", "fornavn2 efternavn2" };
        Tidsskrift tidsskrift = new Tidsskrift( "random navn" );
        String titel = "titel";
        Artikel[] referenceliste = {};
        testArtikel( forfattere, referenceliste, tidsskrift, titel ); 

        Forlag UniPress = new Forlag( "University Press", "Denmark" );
        Tidsskrift JoL = new Tidsskrift( "Journal of Logic" );
        JoL.setForlag( UniPress );

        Tidsskrift brain = new Tidsskrift( "Brain" );

        Artikel A = new Artikel( new String[]{ "A. Abe & A. Turing" }, "A", JoL );

        Artikel B = new Artikel( new String[]{ "B. Bim" }, "B", JoL );
        A.setReferenceliste( new Artikel[]{ B } );

        System.out.println( A.toString() );
        System.out.println( B.toString() );

    }

    public static void testArtikel( String[] forfattere, Artikel[] referenceliste, Tidsskrift tidsskrift, String titel ) {
        Artikel test = new Artikel( forfattere, titel, tidsskrift );
        test.setReferenceliste( referenceliste );
        assert test.getTitel() == titel : "fejl i titel";
        assert test.getReferenceliste() == referenceliste : "fejl i referenceliste";
        assert test.getTidsskrift() == tidsskrift : "fejl i tidsskrift";
        assert test.getForfattere() == forfattere : "fejl i forfattere";
        System.out.println( test.toString() );
        System.out.println( test.getReferenceliste() );
        System.out.println( test.getReferenceliste().length );
    }
}
