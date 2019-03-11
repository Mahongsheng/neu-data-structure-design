package businessLayer.dataStructure;
/**
 * 这是一个栈的迭代器
 * @param <E>
 * @author 软英1702 马洪升 20175188
 */
public class StackIterator<E> implements java.util.Iterator{
    private int currPosition = 0;
    private E[] list;
    public StackIterator(E[] list) {
        this.currPosition = 0;
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        if (currPosition >= list.length || list[currPosition] == null){
            return false;
        }
        return true;
    }

    @Override
    public E next() {
        if (hasNext()){
            E object = list[currPosition];
            currPosition++;
            return object;
        }
        return null;
    }
}
