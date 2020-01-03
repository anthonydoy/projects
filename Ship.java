
import java.awt.*;
import java.awt.geom.*;

public class Ship extends Polygon implements Animatable
{
    private int[] xp;
    private int[] yp;
    private int xLoc;
    private int yLoc;
    private int numP;
    private java.awt.geom.Ellipse2D.Double Leye;
    private java.awt.geom.Ellipse2D.Double Reye;
    private java.awt.geom.Rectangle2D.Double Cannon;
    private GamePanel _p;
    
    public Ship(int x, int y, GamePanel p){
        xp = new int[] {x, x+(CentipedeConstants.BLOCK_SIZE/2), x+(CentipedeConstants.BLOCK_SIZE/2), x+CentipedeConstants.BLOCK_SIZE-1};
        yp = new int[] {y+4, y, y+CentipedeConstants.BLOCK_SIZE-1, y+4};
        xLoc = x;
        yLoc = y;
        numP = 4;
        this.addPoint(xp[1], yp[1]);
        this.addPoint(xp[0], yp[0]);
        this.addPoint(xp[2], yp[2]);
        this.addPoint(xp[3], yp[3]);
        Leye = new Ellipse2D.Double((double)x+3, (double)y+3, 3, 3);
        Reye = new Ellipse2D.Double((double)x+9, (double)y+3, 3, 3);
        Cannon = new Rectangle2D.Double((double)x+(CentipedeConstants.BLOCK_SIZE/2)-1,(double)y, 3, (double)CentipedeConstants.BLOCK_SIZE-2);
        _p = p;
    }
    public int getX(){
        return xLoc;
    }
    public int getY(){
        return yLoc;
    }
    public void fill (java.awt.Graphics2D aBrush){
        aBrush.setColor(Color.WHITE);
        aBrush.fillPolygon(this);
        aBrush.setColor(Color.RED);
        aBrush.fill(Leye);
        aBrush.fill(Reye);
        aBrush.fill(Cannon);
    }
    public void draw (java.awt.Graphics2D aBrush) {
        aBrush.setColor(Color.WHITE);
        aBrush.drawPolygon(this);
    }
    public void moveUp()
    {
        if(yLoc > CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_HEIGHT*3 / 4){
            // ensure that no shroom exists in region we will move into
            for(int i = 0; i < 3; i++){
                Shroom next = _p.indexField(xLoc/CentipedeConstants.STEP_SIZE + i, 
                                            yLoc/CentipedeConstants.STEP_SIZE - 1);
                if(next != null && next.health != 0)
                                 return;
                                }
            
            // shift polygon, "eye" ellipses, and cannon
            for(int i = 0; i < numP; i++){
                yp[i] -= CentipedeConstants.STEP_SIZE;}
            yLoc -= CentipedeConstants.STEP_SIZE;
            this.translate(0, -CentipedeConstants.STEP_SIZE);
            
            Leye.setFrame(Leye.getX(), Leye.getY() - CentipedeConstants.STEP_SIZE, Leye.getWidth(), Leye.getHeight());
            Reye.setFrame(Reye.getX(), Reye.getY() - CentipedeConstants.STEP_SIZE, Reye.getWidth(), Reye.getHeight());
            Cannon.setFrame(Cannon.getX(), Cannon.getY() - CentipedeConstants.STEP_SIZE, Cannon.getWidth(), Cannon.getHeight());
            }
    }
    public void moveDown()
    {
        if(yLoc+CentipedeConstants.BLOCK_SIZE < CentipedeConstants.BLOCK_SIZE*(CentipedeConstants.BOARD_HEIGHT-3)){
            // ensure that no shroom exists in region we will move into
            for(int i = 0; i < 3; i++){
                Shroom next = _p.indexField(xLoc/CentipedeConstants.STEP_SIZE + i, 
                                            yLoc/CentipedeConstants.STEP_SIZE + 3);
                if(next != null && next.health != 0)
                                 return;
                                }
                                 
            for(int i = 0; i < numP; i++){
                yp[i] += CentipedeConstants.STEP_SIZE;}
            yLoc += CentipedeConstants.STEP_SIZE;
            this.translate(0, CentipedeConstants.STEP_SIZE);
            
            Leye.setFrame(Leye.getX(), Leye.getY() + CentipedeConstants.STEP_SIZE, Leye.getWidth(), Leye.getHeight());
            Reye.setFrame(Reye.getX(), Reye.getY() + CentipedeConstants.STEP_SIZE, Reye.getWidth(), Reye.getHeight());
            Cannon.setFrame(Cannon.getX(), Cannon.getY() + CentipedeConstants.STEP_SIZE, Cannon.getWidth(), Cannon.getHeight());
            }
    }    
    public void moveLeft()
    {
        if(xLoc > 0){
            // ensure that no shroom exists in region we will move into
            for(int i = 0; i < 3; i++){
                Shroom next = _p.indexField(xLoc/CentipedeConstants.STEP_SIZE - 1, 
                                            yLoc/CentipedeConstants.STEP_SIZE + i);
                if(next != null && next.health != 0)
                                 return;
                                }
                                
            for(int i = 0; i < numP; i++){
                xp[i] -= CentipedeConstants.STEP_SIZE;}
            xLoc -= CentipedeConstants.STEP_SIZE;
            this.translate(-CentipedeConstants.STEP_SIZE, 0);
            
            Leye.setFrame(Leye.getX() - CentipedeConstants.STEP_SIZE, Leye.getY(), Leye.getWidth(), Leye.getHeight());
            Reye.setFrame(Reye.getX() - CentipedeConstants.STEP_SIZE, Reye.getY(), Reye.getWidth(), Reye.getHeight());
            Cannon.setFrame(Cannon.getX() - CentipedeConstants.STEP_SIZE, Cannon.getY(), Cannon.getWidth(), Cannon.getHeight());
            }
    }
    public void moveRight()
    {
        if(xLoc+CentipedeConstants.BLOCK_SIZE < CentipedeConstants.BLOCK_SIZE*(CentipedeConstants.BOARD_WIDTH-1)){
            // ensure that no shroom exists in region we will move into
            for(int i = 0; i < 3; i++){
                Shroom next = _p.indexField(xLoc/CentipedeConstants.STEP_SIZE + 3, 
                                            yLoc/CentipedeConstants.STEP_SIZE + i);
                if(next != null && next.health != 0)
                                 return;
                                }
                                
            for(int i = 0; i < numP; i++){
                xp[i] += CentipedeConstants.STEP_SIZE;}
            xLoc += CentipedeConstants.STEP_SIZE;
            this.translate(CentipedeConstants.STEP_SIZE, 0);
            
            Leye.setFrame(Leye.getX() + CentipedeConstants.STEP_SIZE, Leye.getY(), Leye.getWidth(), Leye.getHeight());
            Reye.setFrame(Reye.getX() + CentipedeConstants.STEP_SIZE, Reye.getY(), Reye.getWidth(), Reye.getHeight());
            Cannon.setFrame(Cannon.getX() + CentipedeConstants.STEP_SIZE, Cannon.getY(), Cannon.getWidth(), Cannon.getHeight());
            }
    }
    
}
