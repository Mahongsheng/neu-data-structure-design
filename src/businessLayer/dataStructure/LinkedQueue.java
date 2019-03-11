package businessLayer.dataStructure;
import businessLayer.exception.QueueUnderFlowError;

/**
 * 这是一个运用链式结构实现的队列
 * @param <E>
 * @author 软英1702 马洪升 20175188
 */
public class LinkedQueue<E> {
     class Node {
        private E data;
        private Node next;

        public Node(){
            data = null;
            next = null;
        }

        public Node(E data,Node next) {
            this.data = data;
            this.next = next;
        }

         public E getData() {
             return data;
         }

         public void setData(E data) {
             this.data = data;
         }

         public Node getNext() {
             return next;
         }

         public void setNext(Node next) {
             this.next = next;
         }
     }
    //队未
    private Node rear;
    //队头
    private Node front;
    //队列长度
    private int size = 0;

    public LinkedQueue() {//初始化数据
        Node node = new Node();
        front = rear = node;
    }
    /**
     * 将一个对象追加到队列尾部
     * @param
     */
    public void enQueue(E e) {
        Node node = new Node(e,null);
        rear.next = node;
        rear = node;
        size++;
    }

    /**
     * 队列头部出队
     * @return 返回出列的队头元素
     */
    public E deQueue() {
        try {
            if(rear == front) {
                throw new QueueUnderFlowError();
            }
            else {
                Node temp = front.next;
                front.next = temp.next;
                if(temp.next == null){
                    front = rear;
                }
                size--;
                return temp.data;
            }
        }catch (QueueUnderFlowError exception){
            System.out.println("队列为空！无法继续删除。");
            return null;
        }
    }

    /**
     * 队列长度
     * @return int型的数据
     */
    public int size() {
        return size;
    }

    /**
     * 判断是否为空
     * @return int型的数据
     */
    public boolean isEmpty() {
        return rear == front;
    }

    /**
     * 获取队头元素但是不出队
     * @return 返回队头元素
     */
    public Object getQueue() {
        return front.next.data;
    }

    /**
     * 打印队列中数据
     */
    public void printAll() {
        Node temp = front.next;
        while(temp != null) {
            System.out.print(temp.data+"\t");
            temp = temp.next;
        }
        System.out.println();
    }

    public LinkedQueueIterator getIterator(){
        LinkedQueueIterator linkedQueueIterator = new LinkedQueueIterator(front);
        return linkedQueueIterator;
    }
    //测试
//    public static void main(String[] args) {
//        LinkedQueue<Integer> l=new LinkedQueue();
//        System.out.println(l.size());
//        System.out.println(l.isEmpty());
//        for(int i=0;i<5;i++){
//            l.enQueue(i);
//        }
//        l.printAll();
//        System.out.println(l.deQueue());
//        System.out.println(l.deQueue());
//        System.out.println(l.deQueue());
//        l.printAll();
////    	System.out.println(l.getQueue());
//    }
}