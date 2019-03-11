package businessLayer.exception;

/**
 * 如果队列为空后仍要删除数据或获取数据，则抛出异常
 * @author 软英1702 马洪升 20175188
 */
public class QueueUnderFlowError extends Exception {
    public QueueUnderFlowError(){}
    public QueueUnderFlowError(String message){super(message);}
}
