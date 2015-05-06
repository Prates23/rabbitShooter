
public class Score {
	private int points;
	private String PlayerName;
	private final char separator='\u00f6';
	public Score( String pn , int p){
		this.PlayerName=pn;
		this.points=p;
	}
	
	public String getPlayerName(){
		return this.PlayerName;
		
	}
	
	public int getScore(){
		return this.points;
	}
	
	public String toString(){
		return this.PlayerName+separator+this.points;
	}
}
