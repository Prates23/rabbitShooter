import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class initialMenu extends JFrame{
	private JPanel menuInicial;
	
	private JLabel newGame , topScore , exit;
	
	public initialMenu(){
		menuInicial = new JPanel();
		menuInicial.setLayout(new GridLayout(3,1));
		
		newGame = new JLabel(" NEW GAME");
		topScore = new JLabel(" TOP TEN SCORE ");
		exit = new JLabel(" EXIT ");
		
		menuInicial.add(newGame);
		menuInicial.add(topScore);
		menuInicial.add(exit);
		menuInicial.setFocusable(true);
		menuInicial.setOpaque(false);
		
		setContentPane(menuInicial);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		newGame.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				RabbitShooter game;
				try {
					game = new RabbitShooter();
					initialMenu.this.dispose();
					game.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		});
		
		topScore.addMouseListener(new MouseAdapter(){
			
			public void mouseClicked(MouseEvent arg0){
				try {
					showTopScore sts = new showTopScore();
					initialMenu.this.dispose();
					sts.pack();
					sts.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		exit.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0){
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) throws IOException
	{
		initialMenu im=new initialMenu();
		im.setVisible(true);		
		
	}
}
