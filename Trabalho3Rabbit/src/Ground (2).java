import java.awt.Point;

import javax.security.auth.DestroyFailedException;


public class Ground extends Scenario{

	public Ground(Map b, Point p) {
		super(b, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void destroy() throws DestroyFailedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return false;
	}


}
