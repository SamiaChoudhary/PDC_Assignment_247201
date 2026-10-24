public class AsyncWebEngineApp {
    public static void main(String[] args) {
        System.out.println("Master Application Driver initialized on Thread: " + Thread.currentThread().getName());

        TelemetryThread telemetryWorker = new TelemetryThread();
        telemetryWorker.setName("Telemetry-Worker");

        LogProcessorTask logTask = new LogProcessorTask();
        Thread logWorker = new Thread(logTask, "Log-Worker");

        System.out.println("Starting asynchronous workers...");
        telemetryWorker.start();
        logWorker.start();

        System.out.println("Active State (" + telemetryWorker.getName() + "): " + telemetryWorker.isAlive());
        System.out.println("Active State (" + logWorker.getName() + "): " + logWorker.isAlive());
    }
}