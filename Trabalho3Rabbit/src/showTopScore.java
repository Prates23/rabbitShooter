import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class showTopScore extends JFrame{
	private JLabel scores;
	
	public showTopScore() throws IOException{
		
		BufferedReader br= new BufferedReader( new FileReader("TopTenScores.txt"));
		TopTenScore topScore = new TopTenScore();
		
		topScore.ReadScoreFile(br);
		
		
		
		
		setLayout(new GridLayout(10, 1));
		
		for(int i=0 ; i< topScore.getTopTen().length ; i++){
			if(topScore.getTopTen()[i]==null)
				break;
			scores= new JLabel(topScore.getTopTen()[i].getPlayerName() + " " + topScore.getTopTen()[i].getScore());
			add(scores);
		}
		setFocusable(true);
	}
}
