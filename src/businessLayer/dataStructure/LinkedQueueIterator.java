package businessLayer.dataStructure;

/**
 * 这是一个队列的迭代器
 * @param <E>
 * @author 软英1702 马洪升 20175188
 */
public class LinkedQueueIterator<E> implements java.util.Iterator {
    private LinkedQueue.Node front;

    public LinkedQueueIterator(LinkedQueue.Node front) {
        this.front = front;
    }

    @Override
    public boolean hasNext() {
        if (front.getNext() == null){
            return false;
        }
        return true;
    }

    @Override
    public E next() {
        if (hasNext()){
            E object = (E)front.getNext().getData();
            front = front.getNext();
            return object;
        }
        return null;
    }
}
