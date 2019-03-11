package presentationLayer.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
/**
 * 无导游路线界面
 * @author 软英1702 马洪升 20175188
 */
public class NoLoop {
    @FXML
    private Button 确定;

    @FXML
    void 关闭界面(ActionEvent event) {
        VisitorIndex.stage.close();
    }


}
