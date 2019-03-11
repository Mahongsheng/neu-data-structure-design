package presentationLayer.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
/**
 * 车辆已存在提示模块
 * @author 软英1702 马洪升 20175188
 */
public class CarExit {
    @FXML
    private Button 确定;

    @FXML
    void close(ActionEvent event) {
        ManagerIndex.stage1.close();
    }

}
