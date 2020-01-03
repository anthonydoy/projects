
public class ShipProxy implements Animatable
{
    private Ship _p;     

    public void setShip(Ship m)
    {
        _p = m;
    }
    public int getX(){
        return _p.getX();
    }
    public int getY(){
        return _p.getY();
    }
    public boolean contains(int x, int y){
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                if(_p.contains((double)x+(i*CentipedeConstants.STEP_SIZE),(double)y+(j*CentipedeConstants.STEP_SIZE)))
                    return true;
            }
        }
        return false;
    }
    public void fill (java.awt.Graphics2D aBrush){
        _p.fill(aBrush);
    }
    public void draw (java.awt.Graphics2D aBrush) {
    _p.draw(aBrush);
    }
    public void moveUp()
    {
        _p.moveUp();
    }
    public void moveDown()
    {
        _p.moveDown();
    }    
    public void moveLeft()
    {
        _p.moveLeft();
    }
    public void moveRight()
    {
        _p.moveRight();
    }
   
}
