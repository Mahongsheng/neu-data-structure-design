package businessLayer.exception;
/**
 * 如果栈为空后仍要删除数据或获取数据，则抛出异常
 * @author 软英1702 马洪升 20175188
 */
public class StackUnderFlowError extends Exception{
    public StackUnderFlowError(){}
    public StackUnderFlowError(String message){super(message);}
}
