public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
	
		int i=0;
		boolean test=true;
		while (test) {
			i++;
			int sum=0;
			System.out.println();
			System.out.println("==== Setting lot capacity to: "+i+"====");
			for (int j=1;j<=NUM_RUNS;j++) {
				long start = System.currentTimeMillis();
				ParkingLot lot = new ParkingLot(i);
				Simulator sim = new Simulator(lot, hourlyRate, 24*3600 );
				sim.simulate();
				long end = System.currentTimeMillis();
				int duration = (int)((end-start));
				System.out.println(
					"Simulation run "+j+" ("+duration+
					"ms); Queue length at the end of simulation run: "+
					sim.getIncomingQueueSize());
				sum = sum + sim.getIncomingQueueSize();
			}
			double averageQueueLength = sum / NUM_RUNS;
            if (averageQueueLength < THRESHOLD) {
                break;
            }
		}
		return(i);

	}

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			// throw new NullPointerException("Usage: java CapacityOptimizer <hourly rate of arrival>\nExample: java CapacityOptimizer 11");
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			// throw new IllegalArgumentException("The hourly rate of arrival should be a positive integer");
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);
		// Should we check if there are some characters ?

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}
 
