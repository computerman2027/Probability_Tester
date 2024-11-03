import java.util.Random;

public class ProbabilitySimulation {
    public static void main(String[] args) {
        Random random = new Random();
        // int trials = 999999999;
        long trials = 999999999999l;
        // int trials = 1000;
        int[] counts = new int[5];
        
        // for (int i = 0; i < trials; i++) {
        for (long i = 0; i < trials; i++) {
            int box = generateBox(random);
            counts[box - 1]++;
        }
        
        for (int i = 0; i < counts.length; i++) {
            System.out.println("Box " + (i + 1) + " Probability: " + ((double) counts[i] / trials) * 100 + "%");
        }
    }
    
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
