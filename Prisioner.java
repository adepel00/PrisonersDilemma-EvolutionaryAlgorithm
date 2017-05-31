
public class Prisioner {
	private int[] history;
	private int[] strategy;
	private int score;
	
	public Prisioner(){
		history = new int[3];
		for(int i = 0; i < history.length; i++){
			history[i] = randomInt(1, 0);
		}
		strategy = new int[(int) Math.pow(2, 2*history.length)];
		for(int j = 0; j < strategy.length; j++){
			strategy[j] = randomInt(1, 0);
		}
		score = 0;
	}
	
	public void sumScore(int score){
		this.score += score;
	}
	
	public int[] getHistory() {
		return history;
	}	
	
	public int[] getStrategy() {
		return strategy;
	}


	public int getScore() {
		return score;
	}
	
	private int randomInt(int m, int n){
        return (int) Math.floor(Math.random()*(n-m+1)+m);
    }
}
