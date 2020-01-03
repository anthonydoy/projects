
import java.awt.Graphics2D;

public class BodySeg extends Body
{
    private Body _nextSeg;
    
    public BodySeg(int x, int y, GamePanel p){
        this(java.awt.Color.green, x, y, p);
    }
    public BodySeg(java.awt.Color c, int x, int y, GamePanel p){
        super(c, x, y, p);
    }
    public void grow(){
        super.move();
    }
    public void move(){
        int oldXLoc, oldYLoc;
        oldYLoc = (int) _bodySeg.getY();
        oldXLoc = (int) _bodySeg.getX();
        super.move();
        if(_nextSeg != null) _nextSeg.move(oldXLoc, oldYLoc);
    }
    public void move(int x, int y){
        int oldXLoc, oldYLoc;
        oldYLoc = (int) _bodySeg.getY();
        oldXLoc = (int) _bodySeg.getX();
        _panel.checkShipHit(x, y);
        setLocation(x, y);
        if(_nextSeg != null) _nextSeg.move(oldXLoc, oldYLoc);
    }
    public void setNextSeg(Body aSegment){
        _nextSeg = aSegment;
    }
    public Body getNextSeg(){
        return _nextSeg;
    }
    public void fill(Graphics2D aBrush){
        super.fill(aBrush);
        if(_nextSeg != null) _nextSeg.fill(aBrush);
    }
}
