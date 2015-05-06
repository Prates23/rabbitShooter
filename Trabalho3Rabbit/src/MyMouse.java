import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.Timer;


public class MyMouse extends MouseAdapter{

	private Map board;
	private Point toShoot;
	private RabbitShooter rs;
	protected static int countRabbitsKilled=0;
	public MyMouse(Map board,Point toShoot, RabbitShooter rs){
		this.board=board;
		this.toShoot=toShoot;
		this.rs=rs;
	}
	
	public int getRabbitsKilled() {
		return countRabbitsKilled;
	}
	
	public void mouseClicked(MouseEvent arg0) {
		
		super.mouseClicked(arg0);
		
		if(countRabbitsKilled >= 4){
			try {
				countRabbitsKilled=0;
				board.nextLevel();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(board.getComponent(toShoot) instanceof wRabbit ){
			wRabbit white = (wRabbit) board.getComponent(toShoot);
			if(!white.isHide){
				board.setComponent(toShoot);
				board.regen(white,board);
				countRabbitsKilled++;
				rs.updateRabbitsCount();
				rs.updateScore(10);
			}
			
		}
		else if(board.getComponent(toShoot) instanceof gRabbit){
			gRabbit grey = (gRabbit) board.getComponent(toShoot);
			if(!grey.isHide){
				board.setComponent(toShoot);
				board.regen(grey,board);
				countRabbitsKilled++;
				rs.updateRabbitsCount();
				rs.updateScore(20);
			}
		}
		
		if(board.getComponent(toShoot) instanceof Clock){
			Clock clock = (Clock) board.getComponent(toShoot);
			
			board.setComponent(toShoot);
			rs.addClockTime();
		}
		
		if(board.getComponent(toShoot) instanceof Star){
			Star star = (Star) board.getComponent(toShoot);
			board.setComponent(toShoot);
			rs.updateScore(star.getExtraPoints());
			rs.addStarTime();
		}
		
		if(board.getComponent(toShoot) instanceof Bomb1){
			Bomb1 b1 = (Bomb1) board.getComponent(toShoot);
			board.blast1(toShoot);
			rs.addBomb1Time();
		}
		
		if(board.getComponent(toShoot) instanceof Bomb2){
			Bomb2 b2 = (Bomb2) board.getComponent(toShoot);
			board.blast2(toShoot);
			rs.addBomb2Time();
		}
	}

}
