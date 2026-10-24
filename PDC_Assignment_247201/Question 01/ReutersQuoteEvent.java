public class ReutersQuoteEvent extends TradeEvent {
    private double bidPrice;
    private double askPrice;
    public ReutersQuoteEvent(String senderId, long timestamp, double basePrice, double bidPrice, double askPrice) {
        super(senderId, timestamp, basePrice);
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }
    @Override
    public void dispatch() {
        double spread = askPrice - bidPrice;
        System.out.println("[REUTERS MARKET ANALYTICS] Sender: " + senderId + 
                           " | Timestamp: " + timestamp + 
                           " | Bid: $" + String.format("%.2f", bidPrice) + 
                           " | Ask: $" + String.format("%.2f", askPrice) + 
                           " | Bid-Ask Spread: $" + String.format("%.2f", spread));
    }
}