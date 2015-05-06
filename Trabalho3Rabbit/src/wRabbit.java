import java.awt.Point;

import javax.security.auth.DestroyFailedException;


public class wRabbit extends Actors{
	private boolean isMoving = false;
	protected boolean isHide=false;
	public wRabbit(Map b, Point p) {
		super(b, p);
		
	}

	

	@Override
	public boolean move(int toX, int toY) {
		if(!isMoving){
			isMoving=true;
			return false;
		}
		else
			isMoving=false;
		
		int x=toX , y=toY;
		toX = -1+(int) Math.round(Math.random()*2);
		toY = -1+(int) Math.round(Math.random()*2);
		
		Point newPosition = new Point(position.x + toX, position.y + toY);
		
		if((newPosition.x >= 0 && newPosition.x < board.getComps().length) ){
			if((newPosition.y >= 0 && newPosition.y <board.getComps()[0].length)) {
				if(board.getComponent(newPosition) instanceof Bush){
					isHide=true;
					board.moveComponent(newPosition, position);
					super.newPosition(newPosition);
					return true;
					
				}
				else if(board.getComponent(newPosition) instanceof Hole){
					return move(x,y);
				}
				else if(board.getComponent(newPosition) instanceof Stone){
					return move(x,y);
				}
				else if(board.getComponent(newPosition) instanceof Ground){
					isHide=false;
					board.moveComponent(newPosition, position);
					super.newPosition(newPosition);
					return true;
				}
				else if(board.getComponent(newPosition) instanceof gRabbit){
					return move(x,y);
				}
			}
		}
		
		return this.move(x,y);
	}
	

	


}
