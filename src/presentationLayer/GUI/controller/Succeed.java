package presentationLayer.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
/**
 * 发布公告成功界面
 * @author 软英1702 马洪升 20175188
 */
public class Succeed {

    @FXML
    private Button 确定;

    @FXML
    void 关闭当前界面(ActionEvent event) {
        ManagerIndex.stage.close();
    }
}
