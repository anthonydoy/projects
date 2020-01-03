
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CentipedeApp extends JFrame
{
    // instance variables - replace the example below with your own
    private GamePanel _gamePanel;

    public CentipedeApp(){
     super("Centipede");
     this.setResizable(false);
     _gamePanel = new GamePanel(); 
     _gamePanel.setPreferredSize(new java.awt.Dimension(CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_WIDTH, CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_HEIGHT));
     this.setSize(CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_WIDTH, CentipedeConstants.BLOCK_SIZE*CentipedeConstants.BOARD_HEIGHT);
     this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
     this.add(_gamePanel);  
     this.setVisible(true);
    }
    
    public static void main(String[] args){
        CentipedeApp app = new CentipedeApp();
    }
}
