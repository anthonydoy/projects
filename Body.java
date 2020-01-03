
/**
 * Write a description of class Head here.
 * 
 * @author (Greg Johnson) 
 * @version (a version number or a date)
 */
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Color;

public class Body
{
    protected java.awt.geom.Ellipse2D.Double _bodySeg;
    protected GamePanel _panel;
    private int _xDir = CentipedeConstants.BLOCK_SIZE;
    private int _yDir = CentipedeConstants.BLOCK_SIZE;
    private java.awt.Color _color;
    private boolean poisoned = false;
    private boolean head = false;
    
    public Body(java.awt.Color c, int x, int y, GamePanel p)
    {
        _color = c;
        _bodySeg = new Ellipse2D.Double((double)x, (double)y, (double)CentipedeConstants.BLOCK_SIZE, (double)CentipedeConstants.BLOCK_SIZE);
        _panel = p;
    }
    public void setLocation(int x, int y){
       _bodySeg.setFrame((double)x, (double)y, (double)CentipedeConstants.BLOCK_SIZE, (double)CentipedeConstants.BLOCK_SIZE);
    }
    public int getXLocation(){
        return (int)_bodySeg.getX();
    }
    public int getYLocation(){
        return (int)_bodySeg.getY();
    }
    public void poison(){
        poisoned = true;
    }
    public void givePanel(GamePanel p){
        _panel = p;
    }
    public void setHead(){
        head = true;
    }
    public boolean isHead(){
        return head;
    }
    public void move(){
        int newXLoc, newYLoc;
        newXLoc = (int) _bodySeg.getX();
        newYLoc = (int)_bodySeg.getY();
        if(!this.poisoned){
            newXLoc += _xDir;
            if (newXLoc < 0){
                newXLoc = 0;
                newYLoc += _yDir;
                _xDir *= -1;}
            else if (newXLoc > (CentipedeConstants.BOARD_WIDTH-1)*CentipedeConstants.BLOCK_SIZE){
                newXLoc = (CentipedeConstants.BOARD_WIDTH-1)*CentipedeConstants.BLOCK_SIZE;
                newYLoc += _yDir;
                _xDir *= -1;}
            
            Shroom nextBlock = _panel.indexField(newXLoc/CentipedeConstants.STEP_SIZE, newYLoc/CentipedeConstants.STEP_SIZE);
            if(nextBlock != null && nextBlock.health != 0){
                if(nextBlock.poisoned){
                    this.poisoned = true;
                    _yDir = CentipedeConstants.BLOCK_SIZE;
                }
                if(newXLoc - _xDir >= 0 && newXLoc - _xDir <= (CentipedeConstants.BOARD_WIDTH-1)*CentipedeConstants.BLOCK_SIZE)
                    newXLoc -= _xDir;
                newYLoc += _yDir;
                _xDir *= -1;
            }
        }
        
        if(this.poisoned) newYLoc += _yDir;
            
        if (newYLoc <= CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_HEIGHT*3 / 4 && _yDir < 0){
            newYLoc = CentipedeConstants.BLOCK_SIZE*((CentipedeConstants.BOARD_HEIGHT*3 / 4) + 2);
            _yDir *= -1;}
        else if (newYLoc > ((CentipedeConstants.BOARD_HEIGHT-4)*CentipedeConstants.BLOCK_SIZE)){
            newYLoc = (CentipedeConstants.BOARD_HEIGHT-5)*CentipedeConstants.BLOCK_SIZE;
            _yDir *= -1;
            if(this.poisoned) this.poisoned = false;}
        
        _panel.checkShipHit(newXLoc, newYLoc);
        this.setLocation(newXLoc, newYLoc);
    }
    public void move(int x, int y){
        setLocation(x, y);
    }
    
    public void fill(Graphics2D aBrush){
        aBrush.setColor(_color);
        aBrush.fill(_bodySeg);
        if(head){
            aBrush.setColor(Color.RED);
            aBrush.draw(_bodySeg);
        }
    }
}

