package presentationLayer.GUI.controller;

import businessLayer.graph.Graph;
import businessLayer.graph.Notice;
import businessLayer.graph.ScenicVertex;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * 游客界面
 * @author 软英1702 马洪升 20175188
 */
public class VisitorIndex {
    /*
    四个为tableView做关联的ObservableList
     */
    ObservableList<ScenicVertex> findSces = FXCollections.observableArrayList();
    ObservableList<ScenicVertex> sortSces = FXCollections.observableArrayList();
    ObservableList<Notice> notices = FXCollections.observableArrayList();
    ObservableList<String> allVertex = FXCollections.observableArrayList();
    public static Stage stage = new Stage();

    @FXML
    private TextField 景点关键词;

    @FXML
    private Button 景点搜索;
    @FXML
    private TableColumn<ScenicVertex, String> intro1;

    @FXML
    private TableColumn<ScenicVertex, Integer> pop1;

    @FXML
    private TableColumn<ScenicVertex, String> toliet1;

    @FXML
    private TableColumn<ScenicVertex, String> rest1;

    @FXML
    private TableColumn<ScenicVertex, String> name1;

    @FXML
    private TableColumn<ScenicVertex, Integer> wayNum1;

    @FXML
    private TableView<ScenicVertex> searchTable;

    @FXML
    private TableColumn<ScenicVertex, String> intro2;

    @FXML
    private TableColumn<ScenicVertex, Integer> pop2;

    @FXML
    private TableColumn<ScenicVertex, String> toliet2;

    @FXML
    private TableColumn<ScenicVertex, String> rest2;

    @FXML
    private TableColumn<ScenicVertex, String> name2;

    @FXML
    private TableColumn<ScenicVertex, Integer> wayNum2;

    @FXML
    private TableView<ScenicVertex> sortTable;

    @FXML
    private TableColumn<Notice, String> time;

    @FXML
    private TableColumn<Notice, String> content;

    @FXML
    private TableView<Notice> notice;

    @FXML
    private AnchorPane distribution;

    @FXML
    private Button query;

    @FXML
    private ChoiceBox<String> startName;

    @FXML
    private ChoiceBox<String> endName;

    @FXML
    private SplitPane splitPaneQuery;

    @FXML
    private Button queryLoop;

    @FXML
    private Button back;

    /**
     * 初始化该界面
     */
    public void initialize() {
        Graph graph = new Graph();
        for (Notice notice : graph.notices) {
            notices.add(notice);
        }
        notice.setItems(notices);
        time.setCellValueFactory(cellData -> cellData.getValue().timeProperty());
        content.setCellValueFactory(cellData -> cellData.getValue().contentProperty());

        GraphOfScenic graphOfScenic = new GraphOfScenic();
        graphOfScenic.start();
        graphOfScenic.other();
        distribution.getChildren().add(graphOfScenic.getPane());
        splitPaneQuery.getItems().set(1,graphOfScenic.getPane1());

        for (ScenicVertex scenicVertex : graph.vertexList) {
            if (scenicVertex != null) {
                allVertex.add(scenicVertex.getName());
            }
        }

        startName.setItems(allVertex);
        endName.setItems(allVertex);

    }

    /**
     * 找景点
     * @param event
     */
    @FXML
    void searchScenic(ActionEvent event) {
        findSces.clear();
        String name = 景点关键词.getText();
        Graph graph = new Graph();
        graph.findScenic(name);

        searchTable.setItems(findSces);
        ArrayList<ScenicVertex> scenicVertices = graph.ALGraph.findSce;
        for (ScenicVertex scenicVertex : scenicVertices) {
            findSces.add(scenicVertex);
        }
        name1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        intro1.setCellValueFactory(cellData -> cellData.getValue().introductionProperty());
        pop1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPopularity()).asObject());
        rest1.setCellValueFactory(cellData -> cellData.getValue().restProperty());
        toliet1.setCellValueFactory(cellData -> cellData.getValue().toiletProperty());
        wayNum1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumOfNode()).asObject());
    }
    /**
     * 按照热度排序
     * @param event
     */
    @FXML
    void sortByPop(ActionEvent event) {
        sortSces.clear();
        Graph graph = new Graph();
        graph.sortByPop();
        sortTable.setItems(sortSces);
        ArrayList<ScenicVertex> scenicVertices = graph.ALGraph.sortSce;
        for (ScenicVertex scenicVertex : scenicVertices) {
            sortSces.add(scenicVertex);
        }
        name2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        intro2.setCellValueFactory(cellData -> cellData.getValue().introductionProperty());
        pop2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPopularity()).asObject());
        rest2.setCellValueFactory(cellData -> cellData.getValue().restProperty());
        toliet2.setCellValueFactory(cellData -> cellData.getValue().toiletProperty());
        wayNum2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumOfNode()).asObject());

    }
    /**
     * 按照岔路数排序
     * @param event
     */
    @FXML
    void sortByWay(ActionEvent event) {
        sortSces.clear();
        Graph graph = new Graph();
        graph.sortByWay();
        sortTable.setItems(sortSces);
        ArrayList<ScenicVertex> scenicVertices = graph.ALGraph.sortSce;
        for (ScenicVertex scenicVertex : scenicVertices) {
            sortSces.add(scenicVertex);
        }
        name2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        intro2.setCellValueFactory(cellData -> cellData.getValue().introductionProperty());
        pop2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPopularity()).asObject());
        rest2.setCellValueFactory(cellData -> cellData.getValue().restProperty());
        toliet2.setCellValueFactory(cellData -> cellData.getValue().toiletProperty());
        wayNum2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumOfNode()).asObject());

    }
    /**
     * 找最短路径
     * @param event
     */
    @FXML
    void queryShortestPath(ActionEvent event) {
        Graph graph = new Graph();
        String oneName = startName.getValue();
        String otherName = endName.getValue();

        HashMap<String, String> hashMap = graph.findShotestPath(oneName, otherName);
        String path = hashMap.get(otherName);
        String[] result = path.split(" ");
        GraphOfScenic graphOfScenic = new GraphOfScenic();
        graphOfScenic.start(result);
        splitPaneQuery.getItems().set(1,graphOfScenic.getPane2());

    }
    /**
     * 找导游路线图
     * @param event
     */
    @FXML
    void queryLoop(ActionEvent event) {
        Graph graph = new Graph();
        String oneName = startName.getValue();
        String otherName = endName.getValue();
        try {
            String[] result = graph.findGuideLoop(oneName, otherName);
            GraphOfScenic graphOfScenic = new GraphOfScenic();
            graphOfScenic.start(result);
            splitPaneQuery.getItems().set(1,graphOfScenic.getPane2());
        }catch (Exception e){
            try{
                AnchorPane anchorPane = new AnchorPane();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/NoLoop.fxml"));
                anchorPane = loader.load();
                Scene scene = new Scene(anchorPane);
                stage.setTitle("回路不存在");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
    /**
     * 回到上一级界面，即首界面
     * @param event
     */
    @FXML
    void backToIndex(ActionEvent event) {
        AnchorPane rootLayout = new AnchorPane();
        CallGUI.primaryStage.close();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/Index.fxml"));
            rootLayout = (AnchorPane)loader.load();
            Scene scene = new Scene(rootLayout);
            CallGUI.primaryStage.setScene(scene);
            CallGUI.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

