package presentationLayer.GUI.controller;


import businessLayer.graph.Graph;
import businessLayer.graph.ScenicVertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javax.imageio.ImageReader;
import java.io.IOException;
/**
 * 添加景点模块
 * @author 软英1702 马洪升 20175188
 */
public class AddScenic {

    @FXML
    private TextField pop;

    @FXML
    private Button addNow;

    @FXML
    private ToggleGroup restApprove;

    @FXML
    private RadioButton restY;

    @FXML
    private TextField intro;

    @FXML
    private TextField name;

    @FXML
    private RadioButton tolietN;

    @FXML
    private ToggleGroup tolietApprove;

    @FXML
    private RadioButton restN;

    @FXML
    private RadioButton tolietY;

    @FXML
    private TextField X;

    @FXML
    private TextField Y;
    /**
     * 添加新景点
     * @param event
     */
    @FXML
    void addNow(ActionEvent event) {

        ManagerIndex.stage.close();
        String names = name.getText();
        String intros = intro.getText();
        String pops = pop.getText();
        boolean rest = true;
        boolean toilet = true;


        if (restY.isSelected()) {
            rest = true;
        } else if (restN.isSelected()) {
            rest = false;

        }

        if (tolietY.isSelected()) {
            toilet = true;
        } else if (tolietN.isSelected()) {
            toilet = false;
        }

        String Xx = X.getText();
        String Yy = Y.getText();

        ScenicVertex scenicVertex = new ScenicVertex(names, intros, Integer.parseInt(pops),
                rest, toilet, Integer.parseInt(Xx), Integer.parseInt(Yy));

        Graph graph = new Graph();
        graph.addScenicVertex(scenicVertex.getName(),scenicVertex.getIntroduction(),scenicVertex.getPopularity(),
                scenicVertex.isHasRestArea(),scenicVertex.isHasToilet(),scenicVertex.getX(),scenicVertex.getY());

        try{
            AnchorPane anchorPane;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/ManagerIndex.fxml"));
            anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);
            CallGUI.primaryStage.setScene(scene);
            CallGUI.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

