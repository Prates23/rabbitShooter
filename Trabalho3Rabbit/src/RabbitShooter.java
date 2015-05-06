
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class RabbitShooter extends JFrame {


	private static final long serialVersionUID = 1L;
	private static Level lvl;
	private static Map board;
	private int  rabbitsLeft = 5;
	private int time=60;
	private int clockPopUpTime=20;
	private int starPopUpTime=30;
	private int bomb1PopUpTime=15;
	private int bomb2PopUpTime=5;
	private Clock clk = new Clock(board,new Point());
	private Timer t;
	
	private TopTenScore topSoc = new TopTenScore();
	private Score playerSoc;
	// Menu 
	private JMenuBar menuBar;
	private JDialog jd;
	
	
	// Labels
	private JLabel score;
	private JLabel rabbits;
	private JLabel timeLeft;
	private JLabel myLabel2;
	private JLabel dialogBox = new JLabel("Insira o Nome e Pontuação");
	
	private JTextField jtf;
	
	private int currScore;
	
	private MyMouse myMouse;
	
	public RabbitShooter() throws IOException{
		super("Rabbit Shooter v1.1Beta");
		jd=new JDialog(this, true);
		jd.setLayout(new GridLayout(3, 1));
		jd.add(new JLabel("Insira o seu nome"));
		jtf=new JTextField();
		jd.add(jtf);
		JButton bt=new JButton("Ok");
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println(RabbitShooter.this.jtf.getText());
				RabbitShooter.this.jd.setVisible(false);
				playerSoc= new Score(RabbitShooter.this.jtf.getText(),RabbitShooter.this.currScore);
				topSoc.addScore(playerSoc);
				try {
					topSoc.saveTopTen();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		jd.add(bt);
		jd.pack();
		board=new Map(this);
		myMouse=new MyMouse(null, null, null);
		lvl= new Level(board, this);
		board.setLevel(lvl);
		currScore=0;
		score=new JLabel("          Score: " + currScore);
		menuBar = new JMenuBar();
		menuBar.setLayout(new FlowLayout());
		JLabel myLabel1=new JLabel("Time left - 60");
		menuBar.add(myLabel1);
		myLabel2=new JLabel("          Rabits left - " + (rabbitsLeft-myMouse.getRabbitsKilled()));
		menuBar.add(myLabel2);
		menuBar.add(score);
		JPanel gamePainel = new JPanel();
		
		gamePainel.setLayout(new GridLayout(16,18));
		
		
		
		JLabel[][] window=lvl.getWindow();
		for(int i=0;i<16;i++){
			for(int j=0;j<18;j++) 
				gamePainel.add(window[i][j]);
				
		}
	
		gamePainel.setFocusable(true);
		gamePainel.setOpaque(false);
		setContentPane(gamePainel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		pack();
		t=new Timer(1000, new timer(board, myLabel1));
		gamePainel.addKeyListener(new MyKeys(t, board.getComps()));
		t.start();
	}
	
	private class MyKeys extends KeyAdapter {
		private Timer timer;
		private Component[][] comps;
		private boolean pauseOn;
		
		public MyKeys(Timer t, Component[][] comps) {
			timer=t;
			this.comps=comps;
			pauseOn=false;
		}
		
		public void keyPressed(KeyEvent e) {
			if(e.getKeyChar()=='p') {
				if(pauseOn)
					timer.start();
				else
					timer.stop();
				pauseOn=!pauseOn;
				for(int i=0; i<16; i++) {
					for(int j=0; j<18; j++) {
						if(comps[i][j] instanceof wRabbit)
							((wRabbit) comps[i][j]).isHide=pauseOn;
						else if(comps[i][j] instanceof gRabbit)
							((gRabbit) comps[i][j]).isHide=pauseOn;
					}
				}
			}
		}
		
	}
	
	public void updateScore(int points) {
		currScore+=points;
		score.setText("          Score: " + currScore);
	}
	public void resetTime() {
		time=60;
	}
	public void updateRabbitsCount() {
		myLabel2.setText("          Rabits left - " + (rabbitsLeft-myMouse.getRabbitsKilled()));
	}
	public   class timer implements ActionListener{
		private Map board;
		private JLabel time;
		
		public timer(Map board, JLabel time){
			this.board=board;
			this.time=time;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			RabbitShooter.this.time--;
			RabbitShooter.this.clockPopUpTime--;
			RabbitShooter.this.starPopUpTime--;
			RabbitShooter.this.bomb1PopUpTime--;
			RabbitShooter.this.bomb2PopUpTime--;
			if(RabbitShooter.this.time==-1) {
				RabbitShooter.this.jd.setVisible(true);
			}
			else
				this.time.setText("Time left - " + RabbitShooter.this.time);
			
			if(RabbitShooter.this.clockPopUpTime==0){
				genClock();
			}
			
			if(RabbitShooter.this.starPopUpTime==0){
				genStar();
			}
			
			if(RabbitShooter.this.bomb1PopUpTime==0){
				genBomb1();
			}
			
			if(RabbitShooter.this.bomb2PopUpTime==0){
				genBomb2();
			}
			
			
			boolean whiteR = true;
			boolean greyR=true;
			Component[][] c=board.getComps();
			for(int i=0; i<c.length; i++){
				for(int j=0; j<c[0].length; j++) {
					
					if(c[i][j] instanceof wRabbit && whiteR ){
						((wRabbit) c[i][j]).move(i,j);
						whiteR=false;
					}
					if(c[i][j] instanceof gRabbit && greyR ){
						((gRabbit) c[i][j]).move(i,j);
						greyR=false;
					}
				}
				whiteR=true;
			}
			whiteR=true;
		}
		
		
	}
	
	public void addClockTime(){
			RabbitShooter.this.time+=clk.getExtendedTime() ;
			RabbitShooter.this.clockPopUpTime=20;
	}
	
	public void addStarTime(){
		RabbitShooter.this.starPopUpTime=30;
	}
	
	public void addBomb1Time(){
		RabbitShooter.this.bomb1PopUpTime=15;
	}
	
	public void addBomb2Time(){
		RabbitShooter.this.bomb2PopUpTime=5;
	}
	
	public boolean genClock(){
		int toX = (int) Math.round(Math.random()*15);
		int toY = (int) Math.round(Math.random()*17);
		
		Point genPosition= new Point(toX,toY);
		
		if(board.getComponent(genPosition) instanceof Ground){
			board.setClock(genPosition);
			return true;
		}
		
		return genClock();
	}
	
	public boolean genStar(){
		int toX = (int) Math.round(Math.random()*15);
		int toY = (int) Math.round(Math.random()*17);
		
		Point genPosition= new Point(toX,toY);
		
		if(board.getComponent(genPosition) instanceof Ground){
			board.setStar(genPosition);
			return true;
		}
		
		return genStar();
	}
	
	public boolean genBomb1(){
		int toX = (int) Math.round(Math.random()*15);
		int toY = (int) Math.round(Math.random()*17);
		
		Point genPosition= new Point(toX,toY);
		
		if(board.getComponent(genPosition) instanceof Ground){
			board.setBomb1(genPosition);
			return true;
		}
		
		return genBomb1();
	}
	
	public boolean genBomb2(){
		int toX = (int) Math.round(Math.random()*15);
		int toY = (int) Math.round(Math.random()*17);
		
		Point genPosition= new Point(toX,toY);
		
		if(board.getComponent(genPosition) instanceof Ground){
			board.setBomb2(genPosition);
			return true;
		}
		
		return genBomb2();
	}
	
}
