package presentationLayer.GUI.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 主界面唤醒模块
 * @author 软英1702 马洪升 20175188
 */
public class CallGUI extends Application {

    public static Stage primaryStage;
    private AnchorPane rootLayout;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("风景区管理系统");
        initialize();
    }
    /**
     * 初始化
     */
    public void initialize(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/Index.fxml"));
            rootLayout = (AnchorPane)loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage(){
        return this.primaryStage;
    }
}
