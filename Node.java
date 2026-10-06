import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {
    Node(int item){key=item;next=null;}
    public int key;
    public Node next;
    private Lock lock = new ReentrantLock();

    public void lock(){
        lock.lock();
    }
    
    public void unlock(){
        lock.unlock();
    }
}