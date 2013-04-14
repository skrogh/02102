
public class TidsskriftTest {
    public static void main( String args[] ) {
        Forlag mockForlag = new Forlag( "University Press", "Denmark" );
        
        Tidsskrift testSkrift = new Tidsskrift( "Journal of Testing" );

        assert testSkrift.getTitel() == "Journal of Testing" : "fejl i titel";

        testSkrift.setISSN( "133742314" );
        assert testSkrift.getISSN() == "133742314" : "fejl i ISSN";

        testSkrift.setForlag( mockForlag );
        assert testSkrift.getForlag() == mockForlag : "fejl i forlag";

        System.out.println( testSkrift.toString() );
    }
}
