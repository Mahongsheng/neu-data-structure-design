package presentationLayer.GUI.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
/**
 * 首界面
 * @author 软英1702 马洪升 20175188
 */
public class Index {
    private SplitPane splitPane;
    private AnchorPane anchorPane;
    /**
     * 进入游客界面
     * @param event
     */
    @FXML
    void visitorIndex(ActionEvent event) {
        CallGUI.primaryStage.close();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/VisitorIndex.fxml"));
            splitPane = loader.load();
            Scene scene = new Scene(splitPane);
            CallGUI.primaryStage.setScene(scene);
            CallGUI.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 进入管理员界面
     * @param event
     */
    @FXML
    void managerIndex(ActionEvent event) {
        CallGUI.primaryStage.close();
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/ManagerLogin.fxml"));
            anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);
            CallGUI.primaryStage.setScene(scene);
            CallGUI.primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
