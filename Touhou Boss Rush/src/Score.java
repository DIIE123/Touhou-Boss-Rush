
public class Score implements Comparable <Score>{
	private int score;
	private String name;
	
	public Score(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public int compareTo(Score o) {
		// TODO Auto-generated method stub
		return o.getScore() - this.getScore();
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getScore() {
		return this.score;
	}



}
