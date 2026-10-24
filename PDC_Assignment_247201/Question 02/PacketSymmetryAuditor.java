public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        String networkPacketPayload = "RADAR"; 
        int length = networkPacketPayload.length();
        TransactionStack stack = new TransactionStack(length);
        RequestQueue queue = new RequestQueue(length);
        for (int i = 0; i < length; i++) {
            char ch = networkPacketPayload.charAt(i);
            stack.push(ch);
            queue.enqueue(ch);
        }
        boolean isSymmetrical = true;
        while (!stack.isEmpty() && !queue.isEmpty()) {
            char stackChar = stack.pop();
            char queueChar = queue.dequeue();
            if (stackChar != queueChar) {
                isSymmetrical = false;
                break;
            }
        }
        System.out.println("SECURITY GATEWAY PACKET SYMMETRY AUDIT ");
        System.out.println("Ingested Payload: " + networkPacketPayload);
        if (isSymmetrical) {
            System.out.println("VERDICT: [PASSED] Packet exhibits bi-directional payload symmetry (Palindrome Verified).");
        } else {
            System.out.println("VERDICT: [FAILED] Symmetry violation detected! Untrusted packet payload.");
        }
    }
}