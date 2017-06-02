
public class Prisioner {
	private int[] strategy;
	private int score;
	private double aptitude;
	int indexLastSquare;
	
	public Prisioner(){
		strategy = new int[(int) Math.pow(2, 2*3)];
		for(int j = 0; j < strategy.length; j++){
			strategy[j] = randomInt(0, 1);
		}
		score = 0;
		aptitude = 0;
		indexLastSquare = 0;
	}
	
	public int getIndexLastSquare() {
		return indexLastSquare;
	}

	public void setIndexLastSquare(int indexLastSquare) {
		this.indexLastSquare = indexLastSquare;
	}

	public void sumScore(int score){
		this.score += score;
	}
	
	public int[] getStrategy() {
		return strategy;
	}


	public int getScore() {
		return score;
	}
	
	public double getAptitude() {
		return aptitude;
	}

	public void calculateAptitude(int totalScore) {
		aptitude = (double) score/totalScore;
	}

	private int randomInt(int m, int n){
        return (int) Math.floor(Math.random()*(n-m+1)+m);
    }
}
