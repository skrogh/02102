

/*======================================================
 * Button
 * Provides rectangular buttons that can be placed on
 * an StdDraw frame and be pressed with the mouse
 * =====================================================
 */

public class Button extends GameObject {
    // xPos and yPos inherited from GameObject correspond to center
    private int halfWidth;
    private int halfHeight;
    
    private boolean is_pressed;

    private String buttonText;

    public Button( int xPos, int yPos, int halfWidth, int halfHeight, String buttonText ) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.halfWidth = halfWidth;
        this.halfHeight = halfHeight;
        this.buttonText = buttonText;
    }
    
    public void render() {
        if ( !is_pressed )
            StdDraw.setPenColor( StdDraw.LIGHT_GRAY );
        else
            StdDraw.setPenColor( StdDraw.RED );
        StdDraw.filledRectangle( xPos, yPos, halfWidth, halfHeight ); 
        StdDraw.setPenColor( StdDraw.BLACK );
        StdDraw.text( xPos, yPos, buttonText );
        System.out.println("derpomania");
    }

    public void update() {
        int lx = xPos - halfWidth;
        int rx = xPos + halfWidth;
        int ty = yPos - halfHeight;
        int by = yPos + halfHeight;

        int mx = (int) StdDraw.mouseX();
        int my = (int) StdDraw.mouseY();

        if ( lx < mx && rx > mx && ty < my && by > my && StdDraw.mousePressed() )
            is_pressed = true;
        else
            is_pressed =false;
    }

    public boolean isPressed() {
        return is_pressed;
    }
}
