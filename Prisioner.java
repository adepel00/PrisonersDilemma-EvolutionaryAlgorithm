import java.util.Arrays;
import java.util.Random;

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
	
	public void mutate(double PROB_MUTATION) {
		for(int i = 0; i < strategy.length; i++){
			double aleatorio = randomDouble(1, 0);
			if(aleatorio <= PROB_MUTATION){
				if(strategy[i] == 0){
					strategy[i] = 1;
				}else{
					strategy[i] = 0;
				}
			}
		}
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
	
	private static double randomDouble(double m, double n){
    	Random r = new Random();
    	return n + (m - n) * r.nextDouble();
    }
	
	@Override
	public String toString(){
		StringBuilder string = new StringBuilder();
		for(int i = 0; i < strategy.length; i++){
			string.append(strategy[i]);
		}
		string.append("\n");
		return string.toString();
	}

	public void setScore(int score) {
		this.score = score;
	}

}
