// package linkedlists.lockbased;

import contention.abstractions.AbstractCompositionalIntSet;
import java.util.concurrent.atomic.AtomicBoolean;

public class HandOverHandListBasedSet extends AbstractCompositionalIntSet{
    private Node head;
    
    private static class Node {
        Node(int item){value=item;next=null;}
        public int value;
        public Node next;
        // private final Lock lock = new ReentrantLock();

        // atomic single bit lock
        private final AtomicBoolean lock = new AtomicBoolean(false);

        public void lock(){
            // lock.lock();
            while (!lock.compareAndSet(false, true)) {
            }
        }
        
        public void unlock(){
            // lock.unlock();
            lock.set(false);
        }
    }

    public HandOverHandListBasedSet(){
        head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);
    };

    @Override
    public boolean addInt(int x){
        head.lock();
        Node pred=head;
        Node curr=pred.next;
        try {
            curr.lock();
            try {
                while (curr.value < x){
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                if (curr.value==x){return false;}
                Node node = new Node(x);
                node.next=curr;
                pred.next=node;
                return true;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    @Override
    public boolean removeInt(int x) {
        head.lock();
        Node pred=head;
        Node curr=pred.next;
        try {
            curr.lock();
            try {
                while (curr.value < x){
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                if (curr.value==x){
                    pred.next=curr.next;
                    return true;
                }
                return false;
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    @Override
    public boolean containsInt(int x) {
        head.lock();
        Node pred=head;
        Node curr=pred.next;
        try {
            curr.lock();
            try {
                while (curr.value < x){
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                return (curr.value==x);
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    @Override
    public int size() {
        int counter = 0;
        head.lock();
        Node pred=head;
        Node curr=pred.next;
        try {
            curr.lock();
            try {
                while (curr.value != Integer.MAX_VALUE){
                    counter++;
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
        return counter;
    }

    @Override
    public void clear() {
        head.lock();
        Node curr = head.next;
        try {
            curr.lock();
            try {
                head.next = new Node(Integer.MAX_VALUE);
            } finally {
                curr.unlock();
            }
        } finally {
            head.unlock();
        }
    }

}