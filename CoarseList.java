
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoarseList{
    private Node head;
    private Lock lock = new ReentrantLock();

    public boolean insert(int item){
        lock.lock();
        Node pred=head;
        try {
            Node curr=head.next;
            while (curr.key < item){
                pred = curr;
                curr = pred.next;
            }
            if (curr.key==item){return false;}
            Node node = new Node(item);
            node.next=curr;
            pred.next=node;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean remove(int item){
        lock.lock();
        Node pred=head;
        try {
            Node curr=head.next;
            while (curr.key < item){
                pred = curr;
                curr = pred.next;
            }
            if (curr.key==item){
                pred.next = curr.next;
                return true;
            } else return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean contains(int item){
        lock.lock();
        Node pred=head;
        try {
            Node curr=head.next;
            while (curr.key < item){
                pred = curr;
                curr = pred.next;
            }
            if (curr.key==item){return false;}
            Node node = new Node(item);
            node.next=curr;
            pred.next=node;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args)
    {
        
    }
}