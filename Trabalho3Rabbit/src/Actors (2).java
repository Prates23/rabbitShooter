import java.awt.Point;

import javax.security.auth.Destroyable;


public abstract class Actors extends Component implements Movable{

	public Actors(Map b, Point p) {
		super(b, p);
		
	}
	
		
	public abstract boolean move(int toX,int toY);

}
