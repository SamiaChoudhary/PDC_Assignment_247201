import java.util.concurrent.RecursiveAction;

public class PortfolioThresholdTask extends RecursiveAction {
    private static final int THRESHOLD = 1000;
    private final double[] assetValues;
    private final int start;
    private final int end;
    private final double riskCap;
    public PortfolioThresholdTask(double[] assetValues, int start, int end, double riskCap) {
        this.assetValues = assetValues;
        this.start = start;
        this.end = end;
        this.riskCap = riskCap;
    }
    @Override
    protected void compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            for (int i = start; i < end; i++) {
                if (assetValues[i] > riskCap) {
                    // Apply 5% volatility stress-test penalty
                    assetValues[i] *= 1.05;
                }
            }
        } else {
            int mid = start + (length / 2);
            PortfolioThresholdTask leftTask = new PortfolioThresholdTask(assetValues, start, mid, riskCap);
            PortfolioThresholdTask rightTask = new PortfolioThresholdTask(assetValues, mid, end, riskCap);
            invokeAll(leftTask, rightTask);
        }
    }
}