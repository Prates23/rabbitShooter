import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;

import javax.security.auth.DestroyFailedException;
import javax.swing.text.html.HTMLDocument.Iterator;


public class gRabbit extends Actors{
	private Hole holeLocked;
	private Point lastDirection;
	protected boolean isHide=false;
	public gRabbit(Map b, Point p) {
		super(b, p);
		generateDirection();
		if(searchHole()!=null)
			this.holeLocked=searchHole();
	}


	

	@Override
	public boolean move(int toX, int toY) {
	//	System.out.println("x:"+lastDirection.x +"y:"+lastDirection.y);
		
		Point newPosition = new Point(position.x + lastDirection.x, position.y + lastDirection.y);
	//	System.out.println("x:"+newPosition.x +"y:"+newPosition.y);
		if((newPosition.x >= 0 && newPosition.x < board.getComps().length) ){
			if((newPosition.y >= 0 && newPosition.y <board.getComps()[0].length)) {
				
				if(board.getComponent(newPosition) instanceof Ground){
					
					isHide=false;
					board.moveComponent(newPosition, position);
					super.newPosition(newPosition);
					return true;
				}
				else if(board.getComponent(newPosition) instanceof Bush){
					isHide=true;
					board.moveComponent(newPosition, position);
					super.newPosition(newPosition);
					return true;
					
				}
				else if(board.getComponent(newPosition) instanceof Hole){
					newPosition=generateHoleExit(newPosition);
					board.moveComponent(newPosition,position);
					super.newPosition(newPosition);
					return true;
				}
			}
			
		}
	
		
		generateDirection();
		return move(toX,toY);

	}
	
	public void generateDirection(){
		int direction = 1+(int) Math.round(Math.random()*7);
	//	System.out.println(direction);
		Point genDirection = new Point();
		
		switch(direction){
			case 1:
				genDirection.x=0;
				genDirection.y=1;
				break;
			case 2:
				genDirection.x=0;
				genDirection.y=-1;
				break;
			case 3:
				genDirection.x=-1;
				genDirection.y=0;
				break;	
			case 4:
				genDirection.x=1;
				genDirection.y=0;
				break;
			case 5:
				genDirection.x=-1;
				genDirection.y=1;
				break;
			case 6:
				genDirection.x=-1;
				genDirection.y=-1;
				break;
			case 7:
				genDirection.x=1;
				genDirection.y=1;
				break;
			case 8:
				genDirection.x=1;
				genDirection.y=-1;
				break;	
			default:
				break;
		}
		this.lastDirection=genDirection;
		
	}
	
	public Hole searchHole(){
		
		if(board.getHoles()==null)
			return null;
		Hole[] holes=board.getHoles();
		int genHole = (int) Math.round(Math.random()*(holes.length-2));
		
		return holes[genHole] ;
	}
	
	public Point generateHoleExit(Point holePosition){
		Hole[] holes=board.getHoles();
		Point holeExit = new Point();
		
		int genHole = (int) Math.round(Math.random()*(holes.length-2));
		Hole saida = holes[genHole];
		if(holes[genHole]==holeLocked){
			return generateHoleExit(holePosition);
		}
		else{
			generateDirection();
			holeExit = new Point(saida.position.x+lastDirection.x,saida.position.y+lastDirection.y);
			if(board.getComponent(holeExit) instanceof Ground){
				return holeExit;
			}
			else if(board.getComponent(holeExit) instanceof Stone){
				return generateHoleExit(holePosition);
			}
			else if(board.getComponent(holeExit) instanceof Bush){
				return holeExit;
			}
			else return generateHoleExit(holePosition);
		}
			
	}
	

	
}


