
// Carsten Nielsen s123161 & Søren Krogh 
// Andersen s123369
//============================================

public class Complex {
    private double re;
    private double im;

    public Complex() {
        re = 0.0d;
        im = 0.0d;
    }

    public Complex( double re, double im ) {
        this.re = re;
        this.im = im;
    }

    public Complex( Complex z ) {
        this.re = z.getRe();
        this.im = z.getIm();
    }


    public double getRe() { return re; }
    public double getIm() { return im; }

    public double abs() {
        return Math.sqrt( re*re + im*im );
    }

    public Complex plus( Complex other ) {
        double newRe = other.getRe() + re;
        double newIm = other.getIm() + im;

        return new Complex( newRe, newIm );
    }

    public Complex times( Complex other ) {
        double oIm = other.getIm();
        double oRe = other.getRe();

        double newRe = re * oRe - im * oIm;
        double newIm = im * oRe + re * oIm;

        return new Complex( newRe, newIm );
    }

    public String toString() {
        return re + " + " + im + "i";
    }
}
