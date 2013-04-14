import java.awt.Color;
import java.util.*;
import java.io.*;

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

    private static double zoomfactor = 1;
    private static int cPart = 1;
    private static int rPart = 0;
    private static int colorPart = 2;
    private static Scanner inputScanner;
    private static boolean update_needed;

    public static void main( String[] args ) {
        pointList = new ArrayList<double[]>();
        inputScanner = new Scanner( System.in );
        setupWindow();
        try {
            colors = ColorGenerator.generateColorMapFromFile( "colormaps/mandel.mnd" ); // ColorGenerator.generateRandomColorMap( MAX );
        } catch(Exception ex) {
            //error loading colormap, fail silently and make default colors
            colors = new Color[1];
            colors[0] = new Color( 0, 0, 0 );
        }
        update();
        render();
        while( true ) {
            handleConsoleInput();
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
            if ( update_needed ) {
                update();
                render();
                update_needed = false;
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

    public static void handleConsoleInput() {
//        Scanner inputScanner = new Scanner( System.in );
        try {
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );

        while( br.ready() ) {
            Scanner lineScanner = new Scanner( br.readLine() );
            if ( lineScanner.hasNext() && lineScanner.next().equals( "set" ) ) {
                if ( lineScanner.hasNext() ) {
                    switch( lineScanner.next() ) {
                        case "figuresize":
                            if ( lineScanner.hasNextInt() ) {
                            setFiguresize( lineScanner.nextInt() );
                            break;
                            } else 
                                errorParsingCommand( "set figuresize" );
                            break;
                        case "gridsize":
                            if ( lineScanner.hasNextInt() ) {
                                setGridsize( lineScanner.nextInt() );
                                break;
                            } else
                                errorParsingCommand( "set gridsize" );
                            break;
                        case "sidelength":
                            if ( lineScanner.hasNextInt() ) {
                                setSidelength( lineScanner.nextInt() );
                                break;
                            } else
                                errorParsingCommand( "set sidelength" );
                            break;
                        case "center":
                            if ( lineScanner.hasNextDouble() ) {
                                double newX = lineScanner.nextDouble();
                                if ( lineScanner.hasNextDouble() ) {
                                    double newY = lineScanner.nextDouble();
                                    setCenter( newX, newY );
                                    break;
                                } else
                                    errorParsingCommand( "set center" );
                            } else
                                errorParsingCommand( "set center" );
                            break;
                        case "zoomfactor":
                            if ( lineScanner.hasNextDouble() ) {
                                setZoomfactor( lineScanner.nextDouble() );
                                break;
                            } else 
                                errorParsingCommand( "set zoomfactor" );
                            break;
                        case "colormap":
                            if ( lineScanner.hasNext() ) {
                                setColormap( lineScanner.next() );
                                break;
                            } else
                                errorParsingCommand( "set colormap" );
                            break;
                        default:
                            errorParsingCommand( "set" );
                    }
                } else {
                    noSuchCommand();
                    break;
                }
            } else {
                noSuchCommand();
                break;
              
            }
            lineScanner.close();
        }
        }
        catch( IOException ex) {
            System.out.println( "Input error" );
        }
    }

    public static void setFiguresize( int newSize ) {
        CANVAS_WIDTH = newSize;
        CANVAS_HEIGHT = newSize;
        setupWindow();
        System.out.println( "figuresize set to " + newSize + "x" + newSize );
        update_needed = true;
    }

    public static void setGridsize( int newSize ) {
        resolution = newSize;
        System.out.println( "gridsize set to " + newSize );
        update_needed = true;
    }

    public static void setSidelength( int newSize ) {
        sideLength = newSize;
        setupScales();
        System.out.println( "sidelength set to " + newSize );
        update_needed = true;
    }

    public static void setCenter( double newX, double newY ) {
        renderCenterX = newX;
        renderCenterY = newY;
        setupScales();
        System.out.println( "center set to " + newX + " " + newY + "i" );
        update_needed = true;
    }

    public static void setZoomfactor( double newFactor ) {
        zoomfactor = newFactor;
        System.out.println( "zoomfactor set to " + newFactor );
        
    }

    public static void setColormap( String newMap ) {
        try {
        if ( newMap.equals( "random" ) )
            colors = ColorGenerator.generateRandomColorMap( MAX );
        else
            colors = ColorGenerator.generateColorMapFromFile( newMap );
        } catch( Exception ex ) {
            System.out.println( "error loading colormap" );
        }
        System.out.println( "colormap set to " + newMap );
        update_needed = true;
    }
    public static void noSuchCommand() {
        System.out.println( "No such command" );
    }

    public static void errorParsingCommand( String command ) {
        System.out.println( "Error parsing command" + command );
    }
}
