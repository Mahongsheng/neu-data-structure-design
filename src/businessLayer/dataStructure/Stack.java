package businessLayer.dataStructure;
import businessLayer.exception.StackUnderFlowError;

/**
 * 这是一个运用顺序结构实现的栈
 * @param <E>
 * @author 软英1702 马洪升 20175188
 */
public class Stack<E> {
    private E[] list;
    private static final int DEFAULT_MAX_CAPACITY = 10;
    private int capacity;
    private int top;

    public Stack() {
        this.list = (E[]) new Object[DEFAULT_MAX_CAPACITY];
        this.capacity = DEFAULT_MAX_CAPACITY;
        this.top = 0;
    }

    public Stack(int capacity){
        this.list = (E[])new Object[capacity];
        this.capacity = capacity;
        this.top = 0;
    }

    /**
     * 加入方法
     * @param e
     */
    public void push(E e){
        try{
            if(top >= capacity){
                throw new StackOverflowError();
            }
            list[top] = e;
            top++;
        }catch(StackOverflowError exception){
            System.out.println("栈已满！无法继续加入。");
        }
    }

    /**
     * 后进先出，删除最后进入的元素
     * @return
     */
    public E pop(){
        E e;
        try{
            if(top == 0){
                throw new StackUnderFlowError();
            }
            top--;
            e = list[top];
            list[top] = null;
            return e;
        }catch(StackUnderFlowError exception){
            exception.printStackTrace();
            System.out.println("栈为空！无法进行删除。");
            return null;
        }
    }

    /**
     * 得到当前位置元素
     * @param index
     * @return
     */
    public E get(int index){
        return list[index];
    }

    /**
     * 得到最后的元素但是不进行删除
     * @return
     */
    public E peek(){
        E e;
        try{
            if(top == 0){
                throw new StackUnderFlowError();
            }
            e = list[top-1];
            return e;
        }catch(StackUnderFlowError exception){
            System.out.println("栈为空！无法继续获取。");
            return null;
        }
    }

    /**
     * 得到目前已存容量
     * @return
     */
    public int getDepth(){
        return top;
    }

    /**
     * 是否为空
     * @return
     */
    public boolean isEmpty(){
        if(top == 0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 是否已满
     * @return
     */
    public boolean isFull(){
        if(top == capacity){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 得到当前位置
     * @return
     */
    public int getFirstFree() {
        return top;
    }

    /**
     * toString方法
     * @return
     */
    public String toString() {
        String elments = "栈中元素为:\n";
        int i = 0;
        for(i = 0; i < top; i++) {
            elments += list[i].toString();
            elments += "\n";
        }
        return elments;
    }

    /**
     * 返回一个迭代器
     * @return
     */
    public StackIterator getIterator(){
        StackIterator stackIterator = new StackIterator(list);
        return stackIterator;
    }
//    public static void main(String[] args){
//        Stack stack = new Stack();
//        stack.push(1);
//        stack.push(2);
//        System.out.print(stack.peek());
//        System.out.print(stack.pop());
//        System.out.print(stack.getDepth());
//        System.out.println(stack.pop());
//    }
}
