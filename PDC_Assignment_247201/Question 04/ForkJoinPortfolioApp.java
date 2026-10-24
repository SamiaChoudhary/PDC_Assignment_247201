import java.util.concurrent.ForkJoinPool;
public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        double[] assets = new double[10000];
        for (int i = 0; i < assets.length; i++) {
            assets[i] = 100.0 + (i % 800);
        }
        ForkJoinPool pool = new ForkJoinPool();
        long start = System.nanoTime();
        double totalRisk = pool.invoke(new ParallelRiskCalculator(assets, 0, assets.length));
        long end = System.nanoTime();
        System.out.println("Total Cumulative Risk: " + totalRisk);
        System.out.println("Risk Calculation Time: " + (end - start) / 1e6 + " ms");
        start = System.nanoTime();
        pool.invoke(new PortfolioThresholdTask(assets, 0, assets.length, 500.0));
        end = System.nanoTime();
        System.out.println("Stress Test Execution Time: " + (end - start) / 1e6 + " ms");
        pool.close();
    }
}