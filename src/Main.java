import presentationLayer.GUI.controller.CallGUI;
import presentationLayer.commandUI.CommandUI;

/**
 * 您可以在这里唤醒命令行界面或者GUI界面
 * 推荐GUI界面~
 *
 * 整个程序前端为javafx，数据库为sqlite
 * 程序采用分层结构，业务层、持久层与表现层
 * 可以进行文件读写也可以进行数据库读写
 *
 * 感谢您的观看指导~
 * 非常感谢！
 * 预祝老师新年快乐！
 * @author 软英1702 马洪升 20175188
 */
public class Main {
    public static void main(String[] args) {
//        CommandUI commandUI = new CommandUI();
//        commandUI.menu();
//        commandUI.parkingMenu();
//        commandUI.adminUI();
        CallGUI callGUI = new CallGUI();
        callGUI.main(args);
    }
}
