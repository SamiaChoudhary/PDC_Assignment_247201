import java.util.Random;
import java.util.concurrent.ForkJoinPool;
public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int size = 10000;
        double[] assets = new double[size];
        Random random = new Random(42);
        for (int i = 0; i < size; i++) {
            assets[i] = 100.0 + (random.nextDouble() * 900.0);
        }
        ForkJoinPool pool = new ForkJoinPool();
        long startTime = System.nanoTime();
        ParallelRiskCalculator riskTask = new ParallelRiskCalculator(assets, 0, assets.length);
        double totalRiskScore = pool.invoke(riskTask);
        long endTime = System.nanoTime();
        double calcTimeMs = (endTime - startTime) / 1e6;
        System.out.printf("Total Cumulative Risk Score: %.2f%n", totalRiskScore);
        System.out.printf("Risk Calculation Execution Time: %.3f ms%n", calcTimeMs);
        double riskCap = 500.0;
        startTime = System.nanoTime();
        PortfolioThresholdTask stressTask = new PortfolioThresholdTask(assets, 0, assets.length, riskCap);
        pool.invoke(stressTask);
        endTime = System.nanoTime();
        double stressTimeMs = (endTime - startTime) / 1e6;

        System.out.printf("Stress Test Adjustment Execution Time: %.3f ms%n", stressTimeMs);
        pool.close();
    }
}