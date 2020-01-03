
import java.awt.*;
import java.awt.geom.*;

public class Flea extends Creature
{
    private java.awt.geom.Ellipse2D.Double _f;
    private int yDir = CentipedeConstants.BLOCK_SIZE;
    
    public Flea(GamePanel p, int x, int y)
    {
        super(p);
        super.spider = true;
        super.xPos = x;
        super.yPos = y;
        _f = new Ellipse2D.Double((double)x, (double)y, CentipedeConstants.BLOCK_SIZE, CentipedeConstants.BLOCK_SIZE);
    }

    
    public void move()
    {
        int newY = super.yPos + yDir;
        if(newY > CentipedeConstants.BLOCK_SIZE*(CentipedeConstants.BOARD_HEIGHT-2)){
            super.die();
        }
        super.yPos = newY;
        _f.setFrame(super.xPos, newY, _f.getWidth(), _f.getHeight());
        //check for ship collision
        super.checkShipHit();
        //check for dropping shrooms
        double r = Math.random();
        Shroom s;
        s = super.indexField();
        if(r < .12 && super.yPos > CentipedeConstants.BLOCK_SIZE*(CentipedeConstants.BOARD_HEIGHT*1 / 2) 
           && super.yPos < (CentipedeConstants.BLOCK_SIZE)*(CentipedeConstants.BOARD_HEIGHT-5))
           if(s == null) super.addShroom(super.xPos, super.yPos);
           else if(s.health == 0) s.health = 4;
    }
    public void fill (java.awt.Graphics2D aBrush){
        aBrush.setColor(Color.YELLOW);
        aBrush.fill(_f);
    }
}
