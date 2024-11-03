import java.util.*;

public class ProbabilitySimulation2 {
    private static long TOTAL_TRIALS = 9999999L;
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();  // Number of threads
    private static final int[] finalCounts = new int[5];

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter range: ");
        TOTAL_TRIALS = sc.nextLong();
        System.out.println("No of Threads = " + NUM_THREADS);

        // Calculate trials per thread
        long trialsPerThread = TOTAL_TRIALS / NUM_THREADS;

        // Array to hold the threads
        Thread[] threads = new Thread[NUM_THREADS];

        // Start timer
        long startTime = System.nanoTime();

        // Start threads
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(new SimulationTask(trialsPerThread));
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i].join();
        }

        // End timer
        long endTime = System.nanoTime();

        // Display final probabilities
        long totalTrials = TOTAL_TRIALS;
        for (int i = 0; i < finalCounts.length; i++) {
            System.out.println("Box " + (i + 1) + " Probability: " + ((double) finalCounts[i] / totalTrials) * 100 + "%");
        }

        // Calculate and display time taken
        long durationInMillis = (endTime - startTime) / 1_000_000;
        System.out.println("Time taken: " + durationInMillis + " milliseconds");
    }

    // Thread task to perform trials and update the finalCounts array
    private static class SimulationTask implements Runnable {
        private final long trials;
        private final int[] localCounts;

        public SimulationTask(long trials) {
            this.trials = trials;
            this.localCounts = new int[5];
        }

        @Override
        public void run() {
            Random random = new Random();

            // Perform the trials
            for (long i = 0; i < trials; i++) {
                int box = generateBox(random);
                localCounts[box - 1]++;
            }

            // Synchronize updates to finalCounts
            synchronized (finalCounts) {
                for (int i = 0; i < finalCounts.length; i++) {
                    finalCounts[i] += localCounts[i];
                }
            }
        }

        // Simulates a random box selection based on given probabilities
        private static int generateBox(Random random) {
            int rand = random.nextInt(100);
            if (rand < 45) {
                return 1; // 45% probability
            } else if (rand < 70) {
                return 2; // 25% probability
            } else if (rand < 85) {
                return 3; // 15% probability
            } else if (rand < 95) {
                return 4; // 10% probability
            } else {
                return 5; // 5% probability
            }
        }
    }
}
