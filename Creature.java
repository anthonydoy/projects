
/**
 * Abstract class Creature - write a description of the class here
 *
 * @author (your name here)
 * @version (version number or date here)
 */
public abstract class Creature
{
    // instance variables - replace the example below with your own
    protected GamePanel _panel;
    protected boolean flea;
    protected boolean spider;
    protected boolean scorpion;
    protected int xPos;
    protected int yPos;

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public Creature(GamePanel p){
        _panel = p;
    }
    public int getX(){
        return xPos;
    }
    public int getY(){
        return yPos;
    }
    public boolean isFlea(){
        return flea;
    }
    public boolean isSpider(){
        return spider;
    }
    public boolean isScorpion(){
        return scorpion;
    }
    public void checkShipHit(){
        _panel.checkShipHit(xPos, yPos);
    }
    public void die(){
        _panel.creatureDie();
    }
    public Shroom indexField(){
        Shroom rv = null;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                rv = _panel.indexField(xPos/CentipedeConstants.STEP_SIZE + i, 
                                            yPos/CentipedeConstants.STEP_SIZE + i);
                if(rv != null && rv.health != 0)
                                 return rv;
            }
        }
        return rv;
    }
    public void removeShroom(Shroom s){
        _panel.removeShroom(s);
    }
    public void addShroom(int x, int y){
        _panel.addShroom(x, y);
    }
    
    // Will be different for each creature
    public abstract void move();
    public abstract void fill(java.awt.Graphics2D aBrush);
}
