import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class Map extends JPanel {

	private RabbitShooter rb;
	private Component[][] components;
	protected JLabel[][] window = new JLabel[16][18];
	private Ground ground;
	private Bush bush;
	private Clock clk;
	private Star str;
	private Bomb1 b1;
	private Bomb2 b2;
	
	
	private Hole[] holes;
	private int countHoles=0;
	private int countRabbits=0;
	private ImageIcon Ground= new ImageIcon("ground.png");
	private ImageIcon Bush= new ImageIcon("bush.png");
	private ImageIcon Stone= new ImageIcon("rock.png");
	private ImageIcon Hole = new ImageIcon("hole.png");
	private ImageIcon wRabbit = new ImageIcon("rabbit1.png");
	private ImageIcon gRabbit = new ImageIcon("rabbit2.png");
	private ImageIcon Clock = new ImageIcon("clock.png");
	private ImageIcon Star = new ImageIcon ("star.png");
	private ImageIcon Bomb1 = new ImageIcon("bomb1.png");
	private ImageIcon Bomb2 = new ImageIcon("bomb2.png");
	private ImageIcon Blast = new ImageIcon ("blast.png");
	private Level lvl;
	
	public Map(RabbitShooter rb){
		this.rb=rb;
		setLayout(new GridLayout(16, 18));
		for(int i=0; i<16 ; i++){
			for(int j=0 ; j<18; j++){
				window[i][j]=new JLabel();
				add(window[i][j]);
			}
		}
		holes=new Hole[1];
	}
	
	public void setLevel(Level l)
	{
		lvl=l;
	}
	
	public JLabel[][] constructMap(char[][] sketch,Map mp){
		
		components= new Component[16][18];
		
		for(int i=0 ; i<16 ; i++){
			for(int j=0 ; j<18 ; j++){
				
				switch(sketch[i][j]){
					case 'B':
						bush = new Bush (mp,new Point(i,j));
						components[i][j]= bush;
						window[i][j].setIcon(Bush);
						break;
					case 'G':
						this.ground = new Ground (mp,new Point(i,j));
						components[i][j] = ground;
						window[i][j].setIcon(Ground);
						break;
					case'H':
						components[i][j] = new Hole (mp,new Point(i,j));
						addHole((Hole) components[i][j]);
						window[i][j].setIcon(Hole);
						
						break;
					case'S':
						components[i][j] = new Stone (mp,new Point(i,j));
						window[i][j].setIcon(Stone);
						break;
					case'W':
						
						components[i][j] = new wRabbit (mp,new Point(i,j));
						window[i][j].setIcon(wRabbit);
						countRabbits++;
						break;
					case 'C':
						components[i][j] = new gRabbit(mp, new Point(i,j));
						window[i][j].setIcon(gRabbit);
						countRabbits++;
						break;
					
				
				}
				
				window[i][j].addMouseListener(new MyMouse(mp,new Point(i,j), rb));
				
			}
		}
		clk = new Clock(mp,new Point());
		str= new Star(mp,new Point());
		b1= new Bomb1(mp,new Point());
		b2 = new Bomb2(mp,new Point());
		return window;
	}
	
	public void moveComponent(Point newPos, Point oldPos){
		
		
		if(components[newPos.x][newPos.y] instanceof Bush){
			
				
			if(window[oldPos.x][oldPos.y].getIcon() == wRabbit ){
				Component backup=components[oldPos.x][oldPos.y]; // coelho
				components[oldPos.x][oldPos.y]=ground;
				components[newPos.x][newPos.y]=backup;
				window[oldPos.x][oldPos.y].setIcon(Ground);
				
			}
			
			else if(window[oldPos.x][oldPos.y].getIcon() == gRabbit ){
				Component backup=components[oldPos.x][oldPos.y]; // coelho
				components[oldPos.x][oldPos.y]=ground;
				components[newPos.x][newPos.y]=backup;
				window[oldPos.x][oldPos.y].setIcon(Ground);
			}
			else if(window[oldPos.x][oldPos.y].getIcon() == Bush){
				Component backup=components[oldPos.x][oldPos.y]; // coelho
				components[oldPos.x][oldPos.y]=components[newPos.x][newPos.y];
				components[newPos.x][newPos.y]=backup;
				
			}
			

		}
		else if(components[newPos.x][newPos.y] instanceof Ground){
				
				if(window[oldPos.x][oldPos.y].getIcon() == Bush){
					Component backup=components[oldPos.x][oldPos.y];
					components[oldPos.x][oldPos.y]=bush;
					components[newPos.x][newPos.y]=backup; //coelho
					if(backup instanceof wRabbit)
						window[newPos.x][newPos.y].setIcon(wRabbit);
					else
						window[newPos.x][newPos.y].setIcon(gRabbit);
				}
				else if(window[oldPos.x][oldPos.y].getIcon() == wRabbit){
					Component backup=components[newPos.x][newPos.y];
					components[newPos.x][newPos.y]=components[oldPos.x][oldPos.y];
					components[oldPos.x][oldPos.y]=backup;
					window[newPos.x][newPos.y].setIcon(window[oldPos.x][oldPos.y].getIcon());
					window[oldPos.x][oldPos.y].setIcon(Ground);
				}
				else if(window[oldPos.x][oldPos.y].getIcon() == gRabbit){
					Component backup=components[newPos.x][newPos.y];
					components[newPos.x][newPos.y]=components[oldPos.x][oldPos.y];
					components[oldPos.x][oldPos.y]=backup;
					window[newPos.x][newPos.y].setIcon(window[oldPos.x][oldPos.y].getIcon());
					window[oldPos.x][oldPos.y].setIcon(Ground);
				}
		}
		else if(components[newPos.x][newPos.y] instanceof Hole){// caso de ser buraco a seguir
			
			Component backup=components[newPos.x][newPos.y];
			components[newPos.x][newPos.y]=components[oldPos.x][oldPos.y];
			components[oldPos.x][oldPos.y]=backup;
			window[newPos.x][newPos.y].setIcon(window[oldPos.x][oldPos.y].getIcon());
			window[oldPos.x][oldPos.y].setIcon(Ground);
			
			
		}
		
				
			
		
	}
	public boolean addHole(Hole hole){
		
		if(countHoles<holes.length){
			holes[countHoles]=hole;
			countHoles++;
		}
		else{
			Hole[] aux=new Hole[holes.length+1];

			System.arraycopy(holes, 0, aux, 0, countHoles);

			aux[countHoles]=hole;
			countHoles++;
			holes=aux;

			return true;
		}
		
		return false;
	}
	
	public Hole[] getHoles(){
		if(countHoles==0)
			return null;
		
		return this.holes;
	}
	
	public Component[][] getComps(){
		return this.components;
	}
	
	public Component getComponent(Point position){
		return this.components[position.x][position.y];
	}
	
	public void regen(Component killed,Map board){
		
		int regenX =(int) Math.round(Math.random()*(components.length-1));
		int regenY =(int) Math.round(Math.random()*(components[0].length-1));
		Point regenPoint = new Point(regenX,regenY);
		
		if(components[regenX][regenY] instanceof Ground){
			if(killed instanceof wRabbit){
				components[regenX][regenY] = killed;
				window[regenX][regenY].setIcon(wRabbit);
				
			}
			else if(killed instanceof gRabbit){
				components[regenX][regenY] = killed;
				window[regenX][regenY].setIcon(gRabbit);
			}
		}
		else
			regen(killed,board);
		
		killed.newPosition(regenPoint);
	}
	
	public void regen(Component killed){
		int regenX =(int) Math.round(Math.random()*(components.length-1));
		int regenY =(int) Math.round(Math.random()*(components[0].length-1));
		Point regenPoint = new Point(regenX,regenY);
		
		if(components[regenX][regenY] instanceof Ground){
			if(killed instanceof wRabbit){
				components[regenX][regenY] = killed;
				window[regenX][regenY].setIcon(wRabbit);
				
			}
			else if(killed instanceof gRabbit){
				components[regenX][regenY] = killed;
				window[regenX][regenY].setIcon(gRabbit);
			}
		}
		else
			regen(killed);
		
		killed.newPosition(regenPoint);
	}
	public void setComponent(Point position){
		components[position.x][position.y]=ground;
		window[position.x][position.y].setIcon(Ground);
	}
	
	public void setClock(Point position){
		clk.position.x=position.x;
		clk.position.y=position.y;
		components[position.x][position.y]= clk;
		window[position.x][position.y].setIcon(Clock);
	}
	
	public void setStar(Point position){
		str.position.x=position.x;
		str.position.y=position.y;
		components[position.x][position.y]= str;
		window[position.x][position.y].setIcon(Star);
	}
	
	public void setBomb1(Point position){
		b1.position.x=position.x;
		b1.position.y=position.y;
		components[position.x][position.y]= b1;
		window[position.x][position.y].setIcon(Bomb1);
	}
	
	public void setBomb2(Point position){
		b2.position.x=position.x;
		b2.position.y=position.y;
		components[position.x][position.y]= b2;
		window[position.x][position.y].setIcon(Bomb2);
	}
	
	public void nextLevel() throws IOException
	{
		lvl.nextLevel();
	}
	public void restartLevel() throws IOException
	{
		lvl.setLevel();
	}
	
	public void blast1(Point position){
		int yi=position.y-5;
		int yf=position.y+5;
		while(yi<0)
			yi++;
		while(yf>=18)
			yf--;
		while(yf>=yi) {
			window[position.x][yi].setIcon(Blast);
			new Timer(1000, new AL(position.x, yi, window)).start();
			if(components[position.x][yi] instanceof wRabbit) {
				this.regen(components[position.x][yi]);
				rb.updateScore(10);
				MyMouse.countRabbitsKilled++;
				rb.updateRabbitsCount();
			}
			else if(components[position.x][yi] instanceof gRabbit) {
				this.regen(components[position.x][yi]);
				rb.updateScore(20);
				MyMouse.countRabbitsKilled++;
				rb.updateRabbitsCount();
			}
			else if(components[position.x][yi] instanceof Bomb2) {
				blast2(new Point(position.x, yi));
				rb.addBomb2Time();
			}
			components[position.x][yi]=ground;
			yi++;
		}
	}
	
	public void blast2(Point position){
		int yi=position.y-2;
		int yf=position.y+2;
		int xi=position.x-2;
		int xf=position.x+2;
		while(yi<0)
			yi++;
		while(yf>=18)
			yf--;
		while(xi<0)
			xi++;
		while(xf>=16)
			xf--;
		int xib=xi;
		while(yf>=yi) {
			while(xf>=xi) {
				window[xi][yi].setIcon(Blast);
				new Timer(1000, new AL(xi, yi, window)).start();
				if(components[xi][yi] instanceof wRabbit) {
					this.regen(components[xi][yi]);
					rb.updateScore(10);
					MyMouse.countRabbitsKilled++;
					rb.updateRabbitsCount();
				}
				else if(components[xi][yi] instanceof gRabbit) {
					this.regen(components[xi][yi]);
					rb.updateScore(20);
					MyMouse.countRabbitsKilled++;
					rb.updateRabbitsCount();
				}
				else if(components[xi][yi] instanceof Bomb1) {
					blast1(new Point(xi, yi));
					rb.addBomb1Time();
				}
				components[xi][yi]=ground;
				xi++;
			}
			xi=xib;
			yi++;
		}
	}
	
	private class AL implements ActionListener {
		private int a, b;
		private JLabel [][] window;
		
		public AL(int a1, int b1, JLabel [][] w) {
			a=a1;
			b=b1;
			window=w;
		}
		
		public void actionPerformed(ActionEvent e) {
			window[a][b].setIcon(Ground);
			((Timer) e.getSource()).stop();
		}
		
	}
	
}
