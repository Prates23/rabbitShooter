import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;


public class TopTenScore {
	private Score[] topTen;
	private int nScores;
	private final char separator='\u00f6';
	public TopTenScore(){
		topTen = new Score[10];
		nScores=0;
	}
	
	public void addScore(Score s){
		Score backup;
		int z=topTen.length-1;
		
		for(int i=0; i<=nScores; i++) {
			if(topTen[i]==null) {
				topTen[i]=s;
				nScores++;
				break;
			}
			else if(topTen[i].getScore()<s.getScore()) {
				backup=topTen[i];
				while(z-1!=i){
					topTen[z]=topTen[z-1];
					z--;
				}
				topTen[i+1]=backup;
				// roda o array para a direita
				topTen[i]=s;
				nScores++;
				break;
			}
		}
	}

	
	public void ReadScoreFile(BufferedReader br) throws IOException{
		String line=null;
		
		while((line=br.readLine())!=null){
			
			StringTokenizer st= new StringTokenizer(line, separator+"", false);
			String name= st.nextToken();
			int point = Integer.parseInt(st.nextToken());
			Score sc = new Score(name,point);
			addScore(sc);
		}
	}
	
	public Score[] getTopTen(){
		return this.topTen;
	}
	
	public void saveTopTen() throws IOException{
		PrintWriter pr = new PrintWriter(new FileWriter("topTenScores.txt"));
		for(int i=0 ; i<topTen.length ; i++){
			if(topTen[i]!=null)
				pr.println(topTen[i].getPlayerName() + separator + topTen[i].getScore());
			
		}
		
		pr.flush();
		pr.close();
	}
	
}
