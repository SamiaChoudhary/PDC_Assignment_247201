public class RequestQueue {
    private char[] queueArray;
    private int front;
    private int rear;
    private int size;
    private int capacity;
    public RequestQueue(int capacity) {
        this.capacity = capacity;
        this.queueArray = new char[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }
    public void enqueue(char c) {
        if (size < capacity) {
            rear = (rear + 1) % capacity;
            queueArray[rear] = c;
            size++;
        }
    }
    public char dequeue() {
        if (!isEmpty()) {
            char item = queueArray[front];
            front = (front + 1) % capacity;
            size--;
            return item;
        }
        return '\0';}
    public boolean isEmpty() {
        return size == 0;
    }
}