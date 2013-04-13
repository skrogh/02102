import java.awt.Color;
import java.util.*;

public class Mandelbrot {

    private static int CANVAS_WIDTH = 512;
    private static int CANVAS_HEIGHT = 512;

    private static int MAX = 255;
    private static double sideLength = 2;
    private static double renderCenterX = 0;
    private static double renderCenterY = 0;
    private static int resolution = 512;
    private static ArrayList<double[]> pointList;
    private static Color[] colors;

    private static double zoomfactor = 5;
    private static int cPart = 1;
    private static int rPart = 0;
    private static int colorPart = 2;

    public static void main( String[] args ) {
        pointList = new ArrayList<double[]>();
        setupWindow();
        colors = ColorGenerator.generateColorMapFromFile( "colormaps/mandel.mnd" ); // ColorGenerator.generateRandomColorMap( MAX );
        update();
        render();
        while( true ) {
            if( StdDraw.mousePressed() )
                onMouseClick();
            else {
                try {
                    Thread.sleep( 50 );
                }
                catch( InterruptedException ex ) {
                    Thread.currentThread().interrupt();
                }

            }
        }
    }

    public static int iterate(Complex z0) {
        Complex z = new Complex(z0);
        for (int i = 0; i < MAX; i++) {
            if (z.abs() > 2.0) {
                return i;
            }
            z = z.times(z).plus(z0);
        }
        return MAX - 1;
    }

    public static void render() {
        StdDraw.show( 0 );
        StdDraw.clear();
        double pointWidth = ( sideLength / resolution) / 2;

        for( int i = 0; i < pointList.size(); i++ ) {
            //get custom color based on iterations
            StdDraw.setPenColor( generateColor( (int) pointList.get( i )[ colorPart ] ));
            StdDraw.filledRectangle( pointList.get( i )[ rPart ], pointList.get( i )[ cPart ],
                    pointWidth, pointWidth );
            StdDraw.rectangle( pointList.get( i )[ rPart ], pointList.get( i )[ cPart ],
                    pointWidth, pointWidth );


        }

        StdDraw.show( 0 );
    }

    public static void update() {
        for( int i = 0; i < resolution; i++ ) {
            for( int j = 0; j < resolution; j++ ) {
                double re = renderCenterX - sideLength / 2 + 
                    ( sideLength * i ) / ( resolution - 1 );
                double im = renderCenterY - sideLength / 2 + 
                    ( sideLength * j ) / ( resolution - 1 );
                int heat = iterate( new Complex( re, im ) );

                pointList.add( new double[]{ re, im, heat } );

            }
        }

    }

    public static void setupWindow() {
        StdDraw.setCanvasSize( CANVAS_WIDTH, CANVAS_HEIGHT );	
        setupScales();
    }

    public static void setupScales() {
        StdDraw.setXscale( renderCenterX - sideLength / 2, renderCenterX + sideLength / 2 );
        StdDraw.setYscale( renderCenterY - sideLength / 2, renderCenterY + sideLength / 2 );

    }

    public static Color generateColor( int heat ) {
        return  ( heat <= MAX ) ? colors[ heat ] : new Color( 255, 0, 0 );
    }

    public static void onMouseClick() {
        pointList.clear();
        //dividing by numbers different from 2 to circumvent
        //StdDraws padding "feature"
        renderCenterX = StdDraw.mouseX();
        renderCenterY = StdDraw.mouseY();
        sideLength = sideLength / zoomfactor;
        System.out.println( renderCenterX + " " + renderCenterY );
        setupScales();
        update();
        render();
    }
}
