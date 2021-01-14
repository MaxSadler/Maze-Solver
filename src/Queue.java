public class Queue {
    private int head =-1;
    private int tail = -1;
    private int indexes;
    private Node [] queue;

    public Queue(int size){
        this.indexes = size;
        queue = new Node[size];
    }

    public void enQueue(Node node){
        if(tail == -1)
        {
            queue[++tail] = node;
            head++;
        }
        else
        {
            if(!isFull())
            {
                queue[++tail] = node;
            }
        }
    }

    public Node deQueue(){
        if(!isEmpty())
        {
            return queue[head++];
        }
        return null;
    }

    public boolean isFull(){
        return tail == indexes-1;
    }

    public boolean isEmpty(){
        return head == indexes-1;
    }
}
