
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener
{
    private ShipProxy _ship;
    private Creature _creature;
    private int level;
    private ArrayList<java.awt.geom.Rectangle2D.Double> Bullets;
    private int nOfBullets;
    private Shroom[][] shroomField;
    private ArrayList<Shroom> shroomList;
    private int numberOfS;
    private ArrayList<BodySeg> CentipedeList;
    private int nOfCent;
    
    private boolean gameOver = false;
    private int lifeCount = 3;
    private int score = 0;
    private int twelveKcount = 1;
    private CentipedeTimerListener _cTimerListener;
    private CreatureTimerListener _creatureTimerListener;
    private KeyUpListener _upKey;
    private KeyDownListener _downKey;
    private KeyLeftListener _leftKey;
    private KeyRightListener _rightKey;
    private KeySpaceListener _spaceKey;
    private Random _generator;
    private Timer _centipedeTimer;
    private Timer _bulletTimer;
    private Timer _creatureTimer;
    
    public GamePanel()
    {
        this.setBackground(Color.BLACK);
        this.setPreferredSize(new java.awt.Dimension(CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_WIDTH, CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_HEIGHT));
        this.setSize(CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_WIDTH, CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_HEIGHT);
        
        // 2d array to represent entire game area (each step is a third of a the block size)
        shroomField = new Shroom[CentipedeConstants.BOARD_WIDTH*3][CentipedeConstants.BOARD_HEIGHT*3];
        
        // list of current mushrooms
        shroomList = new ArrayList<Shroom>();

        for(int i = 0; i<(CentipedeConstants.BOARD_HEIGHT*3);i++){
            for(int j = 0; j<(CentipedeConstants.BOARD_WIDTH*3);j++){
                shroomField[j][i] = null;}}
        
        _generator = new Random();
        _upKey = new KeyUpListener(this);
        _downKey = new KeyDownListener(this);
        _leftKey = new KeyLeftListener(this);
        _rightKey = new KeyRightListener(this);
        _spaceKey = new KeySpaceListener(this);
        _cTimerListener = new CentipedeTimerListener(this);
        _creatureTimerListener = new CreatureTimerListener(this);
        
        numberOfS = 0;
        Shroom newS;
        // add 35 shrooms to board in random positions
        while(numberOfS<35){
            int x = (int) (Math.floor(Math.random()*(CentipedeConstants.BOARD_WIDTH - 1))) * CentipedeConstants.BLOCK_SIZE;
            int y = (int) (Math.floor(Math.random()*(CentipedeConstants.BOARD_HEIGHT - 7)) + 2) * CentipedeConstants.BLOCK_SIZE;
            this.addShroom(x, y);
        }
        
        // 
        
        _centipedeTimer = new Timer(75, _cTimerListener);
        _bulletTimer = new Timer(1, this);
        _creatureTimer = new Timer(5500, _creatureTimerListener);
        
        level = 0;
        this.reset();
    }
    public void reset(){
        _ship = new ShipProxy();
        _ship.setShip(new Ship(7*CentipedeConstants.BLOCK_SIZE, CentipedeConstants.BLOCK_SIZE*(CentipedeConstants.BOARD_HEIGHT-4), this));
        CentipedeList = new ArrayList<BodySeg>();
        Bullets = new ArrayList<java.awt.geom.Rectangle2D.Double>();
        _creature = null;
        if(level < 10) level++;
        nOfCent = level;
        for(int j = 0; j < nOfCent; j++){
            if(j == 0){
                CentipedeList.add(new BodySeg((10-nOfCent)*CentipedeConstants.BLOCK_SIZE, CentipedeConstants.BLOCK_SIZE, this));
                BodySeg temp = CentipedeList.get(0);
                CentipedeList.get(0).setHead();
                for(int i=1; i<(10-nOfCent); i++){
                    temp.setNextSeg(new BodySeg(((10-nOfCent)-i)*CentipedeConstants.BLOCK_SIZE, CentipedeConstants.BLOCK_SIZE, this));
                    temp = (BodySeg) temp.getNextSeg();
                }
                temp.setNextSeg(null);
            }
            else{
                BodySeg newC = new BodySeg(((10-nOfCent)+(2*(j-1)))*CentipedeConstants.BLOCK_SIZE, CentipedeConstants.BLOCK_SIZE, this);
                newC.setHead();
                CentipedeList.add(newC);
            }
        }
        
        // player gains 5 points for every regenerated mushroom
        Shroom cur;
        for(int i=0; i<numberOfS; i++){
            cur = shroomList.get(i);
            if(cur.health == 0){
                score += 5;
                cur.health = 4;
            }
        }
        
        nOfBullets = 0;
        
        repaint();
        _bulletTimer.start();
        _centipedeTimer.start();
        _creatureTimer.start();
    }
    public void addShroom(int x, int y){
        Shroom newS = new Shroom((double)x, (double)y);
        x /= CentipedeConstants.STEP_SIZE;
        y /= CentipedeConstants.STEP_SIZE;
        if (shroomField[x][y] == null){
                // shroom will occupy this entire space
                for(int i = 0; i<3; i++){
                    for(int j = 0; j<3; j++){
                        shroomField[x+i][y+j] = newS;
                    }
                }
                shroomList.add(newS);
                numberOfS++;
        }
    }
    public void removeShroom(Shroom s){
        int ind = shroomList.indexOf(s);
        int x = s.getX() / CentipedeConstants.STEP_SIZE;
        int y = s.getY() / CentipedeConstants.STEP_SIZE;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                shroomField[x+i][y+j] = null;
            }
        }
        shroomList.remove(ind);
        numberOfS--;
    }
    public void paintComponent (java.awt.Graphics aBrush) 
    {
        super.paintComponent(aBrush);
        java.awt.Graphics2D betterBrush = (java.awt.Graphics2D)aBrush;
        
        for(int i=0; i<numberOfS; i++){
            shroomList.get(i).fill(betterBrush);
            shroomList.get(i).draw(betterBrush);
        }
        _ship.fill(betterBrush);
        _ship.draw(betterBrush);
        
        for(int i = 0; i<nOfCent; i++) CentipedeList.get(i).fill(betterBrush);
        for(int i = 0; i<nOfBullets; i++){
            java.awt.geom.Rectangle2D.Double Bullet = Bullets.get(i);
            betterBrush.setColor(Color.RED);
            betterBrush.fill(Bullet);
        }
        if(_creature!= null) _creature.fill(betterBrush);
        // give player new life every 12 thousand points
        if(score - 12000*twelveKcount >= 0){
            twelveKcount++;
            lifeCount++;
        }
        betterBrush.setColor(Color.WHITE);
        betterBrush.drawString("Score: " + Integer.toString(score), 350, 25);        
        betterBrush.drawString("Lives: " + Integer.toString(lifeCount), 350, 45);
        
        if(gameOver) {
        	betterBrush.setFont(new Font("Helvetica", Font.PLAIN, 40));
            betterBrush.drawString("Game Over", CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_WIDTH/4, CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_HEIGHT/2);
            betterBrush.drawString("Final Score: "+ Integer.toString(score), CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_WIDTH/4 -30, CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_HEIGHT/2 + 50);
        }
    }
    
    // will be used by ship so that the ship won't move on top of mushrooms
    // will be used by bullets to check if they have hit a mushroom
    public Shroom indexField(int x, int y){
        return shroomField[x][y];
    }
    
    public void creatureDie(){
        _creature = null;
        _creatureTimer.start();
    }
    
    public void checkShipHit(int x, int y){	
        
        if(_ship.contains(x, y)){
        	_bulletTimer.stop();
            _centipedeTimer.stop();
            this.reset();
        	if (lifeCount > 0)
        	{
        		lifeCount -= 1;
        	
        	if(lifeCount == 0) {
                gameOver = true;
                repaint();
        		_bulletTimer.stop();
                _centipedeTimer.stop();
        	}
        	}
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        for(int b = 0; b < nOfBullets; b++){
            java.awt.geom.Rectangle2D.Double Bullet = Bullets.get(b);
            Bullet.setFrame(Bullet.getX(), Bullet.getY() - CentipedeConstants.STEP_SIZE, Bullet.getWidth(), Bullet.getHeight());
            repaint();
            // check the bounds 
            if(Bullet.getY() == 0){
                Bullets.remove(b);
                nOfBullets--;
                return;
            }
                
            Shroom next = indexField((int)Bullet.getX()/CentipedeConstants.STEP_SIZE, 
                                     (int)Bullet.getY()/CentipedeConstants.STEP_SIZE - 1);
            // check for shroom collision
            if(next != null && next.health != 0){
                next.reduceHealth();
                if(next.health == 0) score++;
                Bullets.remove(b);
                nOfBullets--;
                return;
            }
            
            // need to check for creature collision here
            if(_creature != null){
                int cX = _creature.getX();
                int cY = _creature.getY();
                int bX = (int)Bullet.getX();
                int bY = (int)Bullet.getY();
                if(cY == (bY - CentipedeConstants.BLOCK_SIZE) && bX - cX >= 0 && bX - cX < CentipedeConstants.BLOCK_SIZE){
                    if(_creature.isSpider()) score+=600;
                    if(_creature.isFlea()) score+=200;
                    if(_creature.isScorpion()) score+=1000;
                    this.creatureDie();  
                }
            }
            
            for(int i = 0; i < nOfCent; i++){
                BodySeg cur = CentipedeList.get(i);
                BodySeg prev = null;
                while(cur != null){
                    int cX = cur.getXLocation();
                    int cY = cur.getYLocation();
                    int bX = (int)Bullet.getX();
                    int bY = (int)Bullet.getY();
                    
                    // check for collision with some segement of current "head"
                    if(cY == (bY - CentipedeConstants.BLOCK_SIZE) && bX - cX >= 0 && bX - cX < CentipedeConstants.BLOCK_SIZE){
                        if(prev != null) prev.setNextSeg(null);

                        this.addShroom(cX, cY);
                        BodySeg newHead = (BodySeg)cur.getNextSeg();
                        if(newHead != null){
                            newHead.setHead();
                            nOfCent++;
                            score += 10;

                            CentipedeList.add(newHead);
                        }
                        if(cur.isHead()){
                            score += 90;
                            nOfCent--;
                            CentipedeList.remove(i);
                        }
                        
                        if(CentipedeList.isEmpty()){
                            _bulletTimer.stop();
                            _centipedeTimer.stop();
                            this.reset();
                            return;
                        }
                        
                        Bullets.remove(b);
                        nOfBullets--;
                        return;
                    }
                    prev = cur;
                    cur = (BodySeg)cur.getNextSeg();
                }
            }
        }
    }
    
    public void moveCent(){
        for(int i = 0; i < nOfCent; i++){
            CentipedeList.get(i).move();
            repaint();
        }
        if(_creature != null) _creature.move();
    }
    
    private class CentipedeTimerListener implements ActionListener
    {
        private GamePanel _p;
        
        public CentipedeTimerListener(JPanel p)
        {
            _p = (GamePanel) p;
        }
        public void actionPerformed (ActionEvent e) {
            _p.moveCent();
        }
    }
    private class CreatureTimerListener implements ActionListener
    {
        private GamePanel _p;
        
        public CreatureTimerListener(JPanel p)
        {
            _p = (GamePanel) p;
        }
        public void actionPerformed (ActionEvent e) {
            double i = Math.random();
            int x = (int) (Math.floor(Math.random()*(CentipedeConstants.BOARD_WIDTH - 1))) * CentipedeConstants.BLOCK_SIZE;
            int y = (int) (Math.floor(Math.random()*(CentipedeConstants.BOARD_HEIGHT - 15)) + 2) * CentipedeConstants.BLOCK_SIZE;
            if(i < .5) _creature = new Spider(_p, x, y);
            else if(i < .75) _creature = new Flea(_p, x, y);
            else _creature = new Scorpion(_p, x, y);
            _creatureTimer.stop();
        }
    }
    private class KeySpaceListener extends KeyInteractor 
    {
        public KeySpaceListener(JPanel p)
        {
            super(p,KeyEvent.VK_SPACE);
        }
        
        public void actionPerformed (ActionEvent e) {
            Bullets.add(new Rectangle2D.Double((double)_ship.getX()+(CentipedeConstants.BLOCK_SIZE/2)-1,(double)_ship.getY(), 3, (double)CentipedeConstants.BLOCK_SIZE-2));
            nOfBullets++;
        }
    }
    private class KeyUpListener extends KeyInteractor 
    {
        public KeyUpListener(JPanel p)
        {
            super(p,KeyEvent.VK_UP);
        }
        
        public void actionPerformed (ActionEvent e) {
            _ship.moveUp();
            repaint();
        }
    }
    private class KeyDownListener extends KeyInteractor 
    {
        public KeyDownListener(JPanel p)
        {
            super(p,KeyEvent.VK_DOWN);
        }
        
        public void actionPerformed (ActionEvent e) {
            _ship.moveDown();
            repaint();
        }
    } 
    private class KeyLeftListener extends KeyInteractor 
    {
        public KeyLeftListener(JPanel p)
        {
            super(p,KeyEvent.VK_LEFT);
        }
        
        public void actionPerformed (ActionEvent e) {
            _ship.moveLeft();
            repaint();
        }
    } 
    private class KeyRightListener extends KeyInteractor 
    {
        public KeyRightListener(JPanel p)
        {
            super(p,KeyEvent.VK_RIGHT);
        }
        
        public  void actionPerformed (ActionEvent e) {
            _ship.moveRight();
            repaint();
        }
    }
}
