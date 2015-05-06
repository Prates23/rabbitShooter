import java.awt.Point;


public abstract class Component {
	
	protected Map board;
	protected Point position;
	
	public Component(Map b , Point p){
		this.position=p;
		this.board=b;
	}
	
	public Point getPoint(){
		return this.position;
	}
	
	public Map getBoard(){
		return this.board;
	}
	
	public void newPosition(Point position){
		this.position=position;
	}
	
	

}
