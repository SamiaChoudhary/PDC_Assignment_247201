import java.util.Arrays;
import java.util.Random;
public class ForkJoinPortfolioApp {
    private static final int DATASET_SIZE = 10_000;
    private static final double RISK_CAP = 500.0;
    private static final double PENALTY_FACTOR = 1.05;
    public static void main(String[] args) {
        double[] assetsParallel = new Random(42).doubles(DATASET_SIZE, 100.0, 1000.0).toArray();
        double[] assetsSerial = assetsParallel.clone();
        long startParallel = System.nanoTime();
        double initialRisk = Arrays.stream(assetsParallel).parallel().sum();
        assetsParallel = Arrays.stream(assetsParallel)
                               .parallel()
                               .map(val -> val > RISK_CAP ? val * PENALTY_FACTOR : val)
                               .toArray();
        double postStressRisk = Arrays.stream(assetsParallel).parallel().sum();

        double parallelTime = (System.nanoTime() - startParallel) / 1e6;

        System.out.printf("Parallel Initial Risk: %.2f%n", initialRisk);
        System.out.printf("Parallel Post-Stress Risk: %.2f%n", postStressRisk);
        System.out.printf("Parallel Time: %.3f ms%n%n", parallelTime);
        long startSerial = System.nanoTime();
        double serialInitialRisk = Arrays.stream(assetsSerial).sum();
        for (int i = 0; i < assetsSerial.length; i++) {
            if (assetsSerial[i] > RISK_CAP) {
                assetsSerial[i] *= PENALTY_FACTOR;
            }
        }
        double serialPostStressRisk = Arrays.stream(assetsSerial).sum();
        double serialTime = (System.nanoTime() - startSerial) / 1e6;
        System.out.printf("Serial Initial Risk: %.2f%n", serialInitialRisk);
        System.out.printf("Serial Post-Stress Risk: %.2f%n", serialPostStressRisk);
        System.out.printf("Serial Time: %.3f ms%n", serialTime);
    }
}