import java.util.concurrent.RecursiveTask;

public class ParallelRiskCalculator extends RecursiveTask<Double> {
    private static final int THRESHOLD = 1000;
    private final double[] assetValues;
    private final int start;
    private final int end;

    public ParallelRiskCalculator(double[] assetValues, int start, int end) {
        this.assetValues = assetValues;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Double compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            double sum = 0.0;
            for (int i = start; i < end; i++) {
                sum += assetValues[i];
            }
            return sum;
        } else {
            int mid = start + (length / 2);
            ParallelRiskCalculator leftTask = new ParallelRiskCalculator(assetValues, start, mid);
            ParallelRiskCalculator rightTask = new ParallelRiskCalculator(assetValues, mid, end);
            leftTask.fork();
            double rightResult = rightTask.compute();
            double leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }
}