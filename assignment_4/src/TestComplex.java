
public class TestComplex {

    public static void main( String args[] ) {
        Complex z1 = new Complex( 1, 2 );
        Complex z2 = new Complex( 4, 5 );

        assert z1.plus( z2 ).toString().equals( "5.0 + 7.0i" ) : "error calling plus method";
        assert z1.times( z2 ).toString().equals( "-6.0 + 13.0i" ) : "error calling times method";
        assert z2.abs() == Math.sqrt( z2.getRe() * z2.getRe() + z2.getIm() *
                z2.getIm() ) : "error calling abs method";
    }
}
