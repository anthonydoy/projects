import java.awt.*;
import java.awt.geom.*;

public class Scorpion extends Creature
{
    private java.awt.geom.Ellipse2D.Double _s;
    private int xDir = CentipedeConstants.BLOCK_SIZE;
    
    public Scorpion(GamePanel p, int x, int y)
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
        if(newX > CentipedeConstants.BLOCK_SIZE*(CentipedeConstants.BOARD_WIDTH-2) || newX < 0){
            newX -= xDir;
            xDir *= -1;
        }
        super.xPos = newX;
        _s.setFrame(newX, super.yPos, _s.getWidth(), _s.getHeight());
        //check for ship collision
        super.checkShipHit();
        //check for poisoning shrooms
        Shroom s;
        s = super.indexField();
        if(s != null && s.health != 0) s.poison();
    }
    public void fill (java.awt.Graphics2D aBrush){
        aBrush.setColor(Color.BLUE);
        aBrush.fill(_s);
    }
}
