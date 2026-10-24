public class TradingEngineApp {
    public static void main(String[] args) {
        System.out.println("=== ENTERPRISE FINANCIAL EXCHANGE DISPATCH ENGINE ===");
        TradeEvent genericEvent = new TradeEvent("GENERIC-GW-01", System.currentTimeMillis(), 100.00);
        TradeEvent fixEvent = new FIXOrderEvent("FIX-NYSE-881", System.currentTimeMillis(), 2500.50, "ORD-993821");
        TradeEvent reutersEvent = new ReutersQuoteEvent("REUTERS-LSE-02", System.currentTimeMillis(), 145.20, 145.15, 145.30);
        TradeEvent[] eventBuffer = { genericEvent, fixEvent, reutersEvent };
        for (TradeEvent event : eventBuffer) {
            event.dispatch(); 
        }
    }
}

