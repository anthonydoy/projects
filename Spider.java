import java.awt.*;
import java.awt.geom.*;

public class Spider extends Creature
{
    private java.awt.geom.Ellipse2D.Double _s;
    private int xDir = CentipedeConstants.BLOCK_SIZE;
    private int yDir = CentipedeConstants.BLOCK_SIZE;
    
    public Spider(GamePanel p, int x, int y)
    {
        super(p);
        super.spider = true;
        super.xPos = x;
        super.yPos = y;
        _s = new Ellipse2D.Double((double)x, (double)y, CentipedeConstants.BLOCK_SIZE, CentipedeConstants.BLOCK_SIZE);
    }

    
    public void move()
    {
        int newX = super.xPos + xDir;
        int newY = super.yPos + yDir;
        if(newX <= 0 || newX > (CentipedeConstants.BLOCK_SIZE-1)*CentipedeConstants.BOARD_WIDTH){
            newX -= xDir;
            xDir*=-1;
        }
        if(newY <= 0 || newY > (CentipedeConstants.BLOCK_SIZE-1)*CentipedeConstants.BOARD_HEIGHT){
            newY -= yDir;
            yDir*=-1;
        }
        super.xPos = newX;
        super.yPos = newY;
        _s.setFrame(newX, newY, _s.getWidth(), _s.getHeight());
        //check for ship collision
        super.checkShipHit();
        //check for eating shrooms
        Shroom s;
        s = super.indexField();
        if(s != null) super.removeShroom(s);
    }
    public void fill (java.awt.Graphics2D aBrush){
        aBrush.setColor(Color.GRAY);
        aBrush.fill(_s);
    }
}
