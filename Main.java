
public class Main {

	public static void main(String[] args) {
		int n = 2; //number of players
		
		Prisioner[] prisioners = new Prisioner[n];
		for(int i = 0; i < prisioners.length; i++){
			prisioners[i] = new Prisioner();
		}
		//------------------------Selection--------------------------------
		prisioners = selection(prisioners);
		//------------------------Crossover--------------------------------
		prisioners = crossover(prisioners);
		//-------------------------Mutation--------------------------------
		prisioners = mutation(prisioners);
	}

	private static Prisioner[] selection(Prisioner[] prisioners) {
		for(int i = 0; i < prisioners.length - 1; i++){
			for(int j = 0; j < prisioners.length; j++){
				tournament(prisioners[i], prisioners[j]);
			}
		}
		return prisioners;
	}
	
	private static void tournament(Prisioner prisioner1, Prisioner prisioner2) {
		
		
	}

	private static Prisioner[] crossover(Prisioner[] prisioners) {
		return prisioners;
	}
	
	private static Prisioner[] mutation(Prisioner[] prisioners) {
		return prisioners;
	}
}
