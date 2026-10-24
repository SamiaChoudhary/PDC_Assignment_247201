public class TradeEvent {
    protected String senderId;
    protected long timestamp;
    protected double basePrice;
    public TradeEvent(String senderId, long timestamp, double basePrice) {
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.basePrice = basePrice;
    }
    public void dispatch() {
        System.out.println("[GENERIC TRADE EVENT] Sender: " + senderId + 
                           " | Timestamp: " + timestamp + 
                           " | Base Price: $" + String.format("%.2f", basePrice));
    }
}