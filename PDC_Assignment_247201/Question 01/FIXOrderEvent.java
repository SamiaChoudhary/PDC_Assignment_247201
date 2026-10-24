public class FIXOrderEvent extends TradeEvent {
    private String clOrdId;
    public FIXOrderEvent(String senderId, long timestamp, double basePrice, String clOrdId) {
        super(senderId, timestamp, basePrice);
        this.clOrdId = clOrdId;
    }
    @Override
    public void dispatch() {
        double fee = basePrice * 0.0015;
        double finalPrice = basePrice + fee;
        System.out.println("[FIX GATEWAY EXECUTION] Client Order ID: " + clOrdId + 
                           " | Sender: " + senderId + 
                           " | Base: $" + String.format("%.2f", basePrice) + 
                           " | Transaction Fee (0.15%): $" + String.format("%.2f", fee) + 
                           " | Total Cost: $" + String.format("%.2f", finalPrice));
    }
}