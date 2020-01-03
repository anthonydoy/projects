import java.awt.geom.*;
import java.awt.Color;

public class Shroom implements Paintable
{
    private java.awt.geom.Arc2D.Double cap;
    private java.awt.geom.Rectangle2D.Double stem;
    public int health;
    public boolean poisoned = false;
    private int xLoc;
    private int yLoc;
    
    public Shroom(double x, double y)
    {
       cap = new Arc2D.Double(x, y, (double)CentipedeConstants.BLOCK_SIZE, (double)CentipedeConstants.BLOCK_SIZE, (double)0, (double)180, Arc2D.OPEN);
       stem = new Rectangle2D.Double((double)(x+(CentipedeConstants.BLOCK_SIZE/3)), (double)(y+(CentipedeConstants.BLOCK_SIZE/2)), 
                                     (double)(CentipedeConstants.BLOCK_SIZE/3), (double)(CentipedeConstants.BLOCK_SIZE/2));
        xLoc = (int) x;
        yLoc = (int) y;
        health = 4;
    }
    
    public int getX(){
        return xLoc;
    }
    
    public int getY(){
        return yLoc;
    }
    
    public void poison(){
        poisoned = true;
    }
    // don't call unless health is 4, 3, 2, or 1
    public void reduceHealth(){
            health -= 1;
            if(health == 3){
                // shrink
            }
            else if(health == 2){
                // shrink
            }
            else if(health == 1){
                // shrink
            }
            // bullet will ignore shroom if health == 0, and it won't be drawn
        }
    
    public void fill(java.awt.Graphics2D brush){
        if(health != 0){
            brush.setColor(Color.GREEN);
            if(poisoned) brush.setColor(Color.RED);
            brush.fill(cap);
            brush.fill(stem);
        }
    }
    public void draw(java.awt.Graphics2D brush){
        if(health != 0){
            brush.setColor(Color.RED);
            brush.draw(cap);
            brush.draw(stem);
        }
    }
}
