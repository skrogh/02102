
public class TestComplex {

    public static void main( String args[] ) {
        Complex z1 = new Complex( 1, 2 );
        Complex z2 = new Complex( 4, 5 );

        System.out.println( z1.plus( z2 ) );
        System.out.println( z1.times( z2 ) );
    }
}
