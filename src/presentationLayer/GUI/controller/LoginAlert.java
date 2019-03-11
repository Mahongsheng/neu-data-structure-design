package presentationLayer.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
/**
 * 密码输入错误提示界面
 * @author 软英1702 马洪升 20175188
 */
public class LoginAlert {
    @FXML
    private Button sure;

    @FXML
    void close(ActionEvent event) {
        ManagerLogin.stage.close();
    }
}
