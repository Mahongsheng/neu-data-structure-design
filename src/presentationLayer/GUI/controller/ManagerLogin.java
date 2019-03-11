package presentationLayer.GUI.controller;

import businessLayer.admin.AdminOpr;
import businessLayer.dataStructure.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * 管理员登录界面
 * @author 软英1702 马洪升 20175188
 */
public class ManagerLogin {
    private AnchorPane anchorPane;
    public static Stage stage = new Stage();

    @FXML
    private TextField adminName;

    @FXML
    private PasswordField pwd;

    @FXML
    private Button login;
    /**
     * 登录
     * @param event
     */
    @FXML
    void login(ActionEvent event) {
        String name = adminName.getText();
        String password = pwd.getText();

        AdminOpr adminOpr = new AdminOpr();
        if (adminOpr.login(name,password)){
            try {
                CallGUI.primaryStage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/ManagerIndex.fxml"));
                anchorPane = loader.load();
                Scene scene = new Scene(anchorPane);
                CallGUI.primaryStage.setScene(scene);
                CallGUI.primaryStage.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        }else {
            try{
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/LoginAlert.fxml"));
                anchorPane = loader.load();
                Scene scene = new Scene(anchorPane);
                stage.setTitle("密码错误");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
