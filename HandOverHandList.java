
import contention.abstractions.AbstractCompositionalIntSet;

public class HandOverHandList extends AbstractCompositionalIntSet{
    private Node head;

    public HandOverHandList(){
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
                while (curr.key < x){
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                if (curr.key==x){return false;}
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
                while (curr.key < x){
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                if (curr.key==x){
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
                while (curr.key < x){
                    pred.unlock();
                    pred = curr;
                    curr = pred.next;
                    curr.lock();
                }
                return (curr.key==x);
            } finally {
                curr.unlock();
            }
        } finally {
            pred.unlock();
        }
    }

    ///??????
    @Override
    public int size() {
        head.lock();
        int counter = 0;
        Node pred=head;
        Node curr=pred.next;
        try {
            curr.lock();
            try {
                while (curr.next != head.next){
                    pred.unlock();
                    counter++;
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

    //jsp
    @Override
    public void clear() {
        //jhbvgfghjk
    }

}