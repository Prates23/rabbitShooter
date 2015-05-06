import java.awt.Point;


public class Clock extends Bonus{
	public final int clockTime=5;
	public Clock(Map b, Point p) {
		super(b, p);
		// TODO Auto-generated constructor stub
	}

	
	
	public int getExtendedTime(){
		return this.clockTime;
	}
}
