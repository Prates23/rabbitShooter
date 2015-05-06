import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JLabel;


public class Level {
	Map board;
	private JLabel[][] window;
	private int level=1;
	private RabbitShooter rb;
	public Level(Map b, RabbitShooter rb) throws IOException{
		this.board=b;
		this.rb=rb;
		setLevel();
	}
	
	public int getLevel(){
		return this.level;
	}
	public char[][] grabLevel() throws IOException{
		
		int linha=0,coluna=0;
		String readedLevel = ("Map"+level+".txt");
		char[][] mapa = new char[16][18];
		
		BufferedReader br = new BufferedReader(new FileReader(readedLevel));
		
		String readedLine="";
		
		while((readedLine=br.readLine())!=null){
			
			
				StringTokenizer str = new StringTokenizer(readedLine, " .;,:-?!\t\n");
				
				while(str.hasMoreTokens()){
					if(coluna>=18) 
						coluna=0;
					
					mapa[linha][coluna]=str.nextToken().charAt(0);
					coluna++;
				}
				
				coluna=0;
				linha++;
			
		}
		br.close();
		return mapa;
	}
	
	public void setLevel() throws IOException{
		
		char[][] constructorMap=grabLevel();
		this.window=board.constructMap(constructorMap, board);
	}
	
	public void nextLevel() throws IOException
	{
		if(level<=2)
		{
			level++;	
			setLevel();
		}
		rb.resetTime();
		rb.updateRabbitsCount();
	}
	
	public JLabel[][] getWindow()
	{
		return window;
	}
}
