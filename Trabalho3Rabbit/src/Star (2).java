import java.awt.Point;


public class Star extends Bonus{
	public final int extraPoints=100;
	public Star(Map b, Point p) {
		super(b, p);
		// TODO Auto-generated constructor stub
	}
	
	public int getExtraPoints(){
		return extraPoints;
	}

}
