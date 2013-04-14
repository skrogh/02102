
public class ForlagTest {
    
    public static void main( String args[] ) {
        Forlag testForlag = new Forlag( "University Press", "Denmark" );
        assert testForlag.getNavn() == "University Press" : "fejl i navn";
        assert testForlag.getSted() == "Denmark" : "fejl i sted";

        testForlag.setNavn( "Ændret navn med setNavn()" );
        assert testForlag.getNavn() == "Ændret navn med setNavn()" : 
            "fejl i setNavn()";
        testForlag.setSted( "Ændret sted med setSted()" );
        assert testForlag.getSted() == "Ændret sted med setSted()";

        System.out.println( testForlag.toString() );

    }
}
