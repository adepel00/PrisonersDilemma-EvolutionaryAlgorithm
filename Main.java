import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {
	
	final static int N_MAX_GENERATIONS = 100;
    final static double PROB_CROSSOVER = 0.8;
    final static double PROB_MUTATION = 0.1;
    final static int SQUARES_ROULETTE = 100;
    
	public static void main(String[] args) {
		
		int n = 2; //number of players
		Prisioner[] prisioners = new Prisioner[n];
		for(int i = 0; i < prisioners.length; i++){
			prisioners[i] = new Prisioner();
		}
		for(int x = 0; x < N_MAX_GENERATIONS; x++){
			//------------------------Selection--------------------------------
			prisioners = selection(prisioners);
			//------------------------Crossover--------------------------------
			prisioners = crossover(prisioners);
			//-------------------------Mutation--------------------------------
			prisioners = mutation(prisioners);
		}
	}

	//------------------------Selection--------------------------------
	private static Prisioner[] selection(Prisioner[] prisioners) {
		//------------------------Tournament-------------------------------
		int[][]	history = new int[3][2];
		for(int i = 0; i < history.length; i++){
			for(int j = 0; j < history[0].length; j++){
				history[i][j] = randomInt(0, 1);
			}
		}
		
		int[] totalScores = {0};
		for(int i = 0; i < prisioners.length - 1; i++){
			for(int j = 0; j < prisioners.length; j++){
				tournament(prisioners[i], prisioners[j], history, totalScores);
			}
		}
		for(int x = 0; x < prisioners.length; x++){
			prisioners[x].calculateAptitude(totalScores[0]);
		}
		
		//------------------------Roulette---------------------------------
		prisioners = roulette(prisioners);
		
		return prisioners;
	}
	
	private static void tournament(Prisioner prisioner1, Prisioner prisioner2, int[][] history, int[] totalScores) {
		for(int x = 0; x < 6; x++){
			StringBuilder index1Bin = new StringBuilder();
			for(int i = 0; i < history.length; i++){
				for(int j = 0; j < history[0].length; j++){
					index1Bin.append(history[i][j]);
				}
			}
			int index1 = Integer.parseInt(index1Bin.toString(), 2);
			int answer1 = prisioner1.getStrategy()[index1];
			
			StringBuilder index2Bin = new StringBuilder();
			for(int i = 0; i < history.length; i++){
				for(int j = history[0].length - 1; j >= 0; j--){
					index2Bin.append(history[i][j]);
				}
			}
			int index2 = Integer.parseInt(index2Bin.toString(), 2);
			int answer2 = prisioner2.getStrategy()[index2];
			
			if(answer1 == 0 && answer2 == 0){
				prisioner1.sumScore(1);
				prisioner2.sumScore(1);
				totalScores[0] += 2;
			}else if(answer1 == 0 && answer2 == 1){
				prisioner1.sumScore(6);
				totalScores[0] += 6;
			}else if(answer1 == 1 && answer2 == 0){
				prisioner2.sumScore(6);
				totalScores[0] += 6;
			}else if(answer1 == 1 && answer2 == 1){
				prisioner1.sumScore(4);
				prisioner2.sumScore(4);
				totalScores[0] += 8;
			}
			
			for(int k = 0; k < history.length - 1; k++){
				for(int t = 0; t < history[0].length; t++){
					history[k][t] = history[k + 1][t];
				}
			}
			history[history.length - 1][0] = answer1;
			history[history.length - 1][1] = answer2;
		}
		
	}
	
	private static Prisioner[] roulette(Prisioner[] prisioners) {
		//---------------------Assign Squares------------------------------
		int lastSquareAssigned = 0;
        for(int j = 0; j < prisioners.length; j++){
     	   int squareNumber = (int) (SQUARES_ROULETTE * prisioners[j].getAptitude());       	   
     	   prisioners[j].setIndexLastSquare(lastSquareAssigned + squareNumber - 1);
     	  lastSquareAssigned = prisioners[j].getIndexLastSquare();
        }
        if(lastSquareAssigned + 1 < SQUARES_ROULETTE){
            int squaresNotAssigned = SQUARES_ROULETTE - (lastSquareAssigned + 1);
            for(int k = bestPrisionerIndex(prisioners); k < prisioners.length; k++){
         	   prisioners[k].setIndexLastSquare(prisioners[k].getIndexLastSquare() + squaresNotAssigned);
            }
        }
		//---------------------Throw Roulette------------------------------
        Prisioner[] prisionersSelected = new Prisioner[prisioners.length];
    	for(int x = 0; x < prisioners.length; x++){
      	   int randomSquare = randomInt(0, SQUARES_ROULETTE - 1);
      	   for(int y = 0; y < prisioners.length; y++){
      		   if(prisioners[y].getIndexLastSquare() >= randomSquare){
      			 prisionersSelected[x] = prisioners[y];
      			 break;
      		   }
      	   }
         }
    	System.out.println(Arrays.toString(prisionersSelected));
        return prisionersSelected;
	}

	private static int bestPrisionerIndex(Prisioner[] prisioners) {
		int indexBestPrisioner = 0;
		for(int i = 0; i < prisioners.length; i++){
			if(prisioners[i].getScore() > prisioners[indexBestPrisioner].getScore()){
				indexBestPrisioner = i;
			}else if(prisioners[i].getScore() == prisioners[indexBestPrisioner].getScore()){
				int random = randomInt(0, 1);
                if(random == 1){
                	indexBestPrisioner = i;
                }
			}
		}
		return indexBestPrisioner;
	}
	//------------------------Crossover--------------------------------
	private static Prisioner[] crossover(Prisioner[] prisioners) {
		ArrayList<Prisioner> prisionersCrossList = getPrisionersCross(prisioners);
        
        Prisioner[] prisionersCross = new Prisioner[prisionersCrossList.size()];
        if(prisionersCross.length > 0){
        	Prisioner[] prisionersCrossed = cross(prisionersCrossList.toArray(prisionersCross));
        
	        int count = 0; //Contador de cruzados añadidos al array de individuos
	        for(int j = 0; j < prisioners.length; j++){
	        	if(prisioners[j] == null && prisionersCrossed.length > count){
	        		prisioners[j] = prisionersCrossed[count];
	        		count++;
	        	}
	        }
        }
		return prisioners;
	}
	
	private static ArrayList<Prisioner> getPrisionersCross(Prisioner[] prisioners){
	   ArrayList<Prisioner> prisionersCross = new ArrayList<>();
	   for(int i = 0; i < prisioners.length; i++){
		   double random = randomDouble(0, 1);
		   if(random <= PROB_CROSSOVER){
			   prisionersCross.add(prisioners[i]);
			   prisioners[i] = null;
		   }		   
	   }
	   
	   if(prisionersCross.size() % 2 != 0){ //Tenemos un número impar de individuos, tenemos que quitar uno
        	int indiceQuitar = randomInt(prisionersCross.size() - 1, 0);
        	for(int j = 0; j < prisioners.length; j++){
            	if(prisioners[j] == null){
            		prisioners[j] = prisionersCross.get(indiceQuitar);
            		break;
            	}
            }
        	prisionersCross.remove(indiceQuitar);
        }
	   
	   return prisionersCross;
    }
	
	private static Prisioner[] cross(Prisioner[] prisionersCross) {
		int cut = randomInt(prisionersCross[1].getStrategy().length - 1, 0);
    	for(int i = 0; i < prisionersCross.length; i++){
    		for(int j = 0; j <= cut; j++){
    			//Intercambio de genes
        		int aux = prisionersCross[i].getStrategy()[j];
        		prisionersCross[i].getStrategy()[j] = prisionersCross[i + 1].getStrategy()[j];
        		prisionersCross[i + 1].getStrategy()[j] = aux;
        	}
    		i++;
    	}
    	return prisionersCross;
	}
	//-------------------------Mutation--------------------------------
	private static Prisioner[] mutation(Prisioner[] prisioners) {
		for(int i = 0; i < prisioners.length; i++){
			prisioners[i].mutate(PROB_MUTATION);
		}
		return prisioners;
	}
	
	
	//----------------------Other functions----------------------------
	private static int randomInt(int m, int n){
        return (int) Math.floor(Math.random()*(n-m+1)+m);
    }
	
	private static double randomDouble(double m, double n){
    	Random r = new Random();
    	return n + (m - n) * r.nextDouble();
    }
	
	//Si se reinicia siempre el historial en cada partida gana la estrategia 0's
}
