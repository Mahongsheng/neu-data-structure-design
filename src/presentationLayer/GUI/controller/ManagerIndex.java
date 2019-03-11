package presentationLayer.GUI.controller;

import businessLayer.graph.Edge;
import businessLayer.graph.Graph;
import businessLayer.graph.ScenicVertex;
import businessLayer.parking.Car;
import businessLayer.parking.ParkingLog;
import businessLayer.parking.ParkingLot;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistenceLayer.connectDB.CarSrvc;

import java.io.IOException;
import java.security.spec.ECGenParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 管理员界面
 * @author 软英1702 马洪升 20175188
 */
public class ManagerIndex {
    /**
     * 五个tableView相关联的ObservableList
     */
    ObservableList<ScenicVertex> scenicVertices = FXCollections.observableArrayList();
    ObservableList<Edge> edges = FXCollections.observableArrayList();
    ObservableList<Car> carInLot = FXCollections.observableArrayList();
    ObservableList<Car> carInWaiting = FXCollections.observableArrayList();
    ObservableList<Car> carLogs = FXCollections.observableArrayList();
    public static Stage stage = new Stage();
    public static Stage stage1 = new Stage();
    static ParkingLot parkingLotIn = new ParkingLot();

    @FXML
    private TableColumn<ScenicVertex, String> rest;

    @FXML
    private TableView<ScenicVertex> manageScenic;

    @FXML
    private Button addScenic;

    @FXML
    private Button deleteScenic;

    @FXML
    private TextField distance;

    @FXML
    private TableColumn<ScenicVertex, String> toliet;

    @FXML
    private TableView<Edge> manageEdge;

    @FXML
    private TextField oneName;

    @FXML
    private TableColumn<ScenicVertex, Integer> way;

    @FXML
    private Button deleteEdge;

    @FXML
    private TableColumn<ScenicVertex, Integer> pop;

    @FXML
    private TableColumn<ScenicVertex, String> intro;

    @FXML
    private TableColumn<ScenicVertex, String> name;

    @FXML
    private TextField otherName;

    @FXML
    private TextField timeCost;

    @FXML
    private Button addEdge;

    @FXML
    private TableColumn<Edge, String> edgeName1;

    @FXML
    private TableColumn<Edge, String> edgeName2;

    @FXML
    private TableColumn<Edge, Integer> edgeDistance;

    @FXML
    private TableColumn<Edge, Float> edgeTimeCost;

    @FXML
    private TextArea noticeContent;

    @FXML
    private Button launch;

    @FXML
    private TableView<Car> parking;

    @FXML
    private TableView<Car> waiting;

    @FXML
    private TableView<Car> parkingLog;

    @FXML
    private TableColumn<Car, String> carID;

    @FXML
    private TableColumn<Car, String> arriveTime;

    @FXML
    private TableColumn<Car, String> arriveTime2;

    @FXML
    private TableColumn<Car, String> arriveTime1;

    @FXML
    private TableColumn<Car, String> carID2;

    @FXML
    private TableColumn<Car, String> carID1;

    @FXML
    private TableColumn<Car, String> leaveTime2;

    @FXML
    private TableColumn<Car, String> stayTime2;

    @FXML
    private TableColumn<Car, Integer> cost2;

    @FXML
    private TableColumn<Car, Integer> position1;

    @FXML
    private TableColumn<Car, Integer> position;

    @FXML
    private Button park;

    @FXML
    private TextField inputCarID;

    @FXML
    private Button leaveCar;

    @FXML
    private Button back;

    /**
     * 初始化该界面
     */
    public void initialize() {
        scenicVertices.clear();
        edges.clear();
        carInLot.clear();
        carInWaiting.clear();
        carLogs.clear();


        Graph graph = new Graph();
        for (ScenicVertex scenicVertex : graph.vertexList) {
            if (scenicVertex != null) {
                scenicVertices.add(scenicVertex);
            }
        }
        for (Edge edge : graph.edgeList) {
            if (edge != null) {
                edges.add(edge);
            }
        }

        manageScenic.setItems(scenicVertices);
        manageEdge.setItems(edges);

        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        intro.setCellValueFactory(cellData -> cellData.getValue().introductionProperty());
        pop.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPopularity()).asObject());
        rest.setCellValueFactory(cellData -> cellData.getValue().restProperty());
        toliet.setCellValueFactory(cellData -> cellData.getValue().toiletProperty());
        way.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumOfNode()).asObject());

        edgeName1.setCellValueFactory(cellData -> cellData.getValue().oneScenicProperty());
        edgeName2.setCellValueFactory(cellData -> cellData.getValue().otherScenicProperty());
        edgeDistance.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWeight()).asObject());
        edgeTimeCost.setCellValueFactory(cellData -> new SimpleObjectProperty<Float>(cellData.getValue().getTimeCost()));

        CarSrvc carSrvc = new CarSrvc();
        for (Car car : carSrvc.search()) {
            if (car.isIfWaiting()) {
                carInWaiting.add(car);
            } else {
                if (car.getCost() == 0) {
                    carInLot.add(car);
                } else {
                    carLogs.add(car);
                }
            }
        }

        parking.setItems(carInLot);
        waiting.setItems(carInWaiting);
        parkingLog.setItems(carLogs);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
        carID.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());
        arriveTime.setCellValueFactory(cellData -> new SimpleStringProperty(sdf.format(cellData.getValue().getArriveTime())));
        position.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPosition()).asObject());

        carID1.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());
        arriveTime1.setCellValueFactory(cellData -> new SimpleStringProperty(sdf.format(cellData.getValue().getArriveTime())));
        position1.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPosition()).asObject());

        carID2.setCellValueFactory(cellData -> cellData.getValue().carIdProperty());
        arriveTime2.setCellValueFactory(cellData -> new SimpleStringProperty(sdf.format(cellData.getValue().getArriveTime())));
        leaveTime2.setCellValueFactory(cellData -> new SimpleStringProperty(sdf.format(cellData.getValue().getLeaveTime())));
        stayTime2.setCellValueFactory(cellData -> cellData.getValue().stayTimeProperty());
        cost2.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCost()).asObject());

    }
    /**
     * 添加景点
     * @param event
     */
    @FXML
    void addScenic(ActionEvent event) {
        try {
            AnchorPane anchorPane;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/AddScenic.fxml"));
            anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);
            stage.setTitle("添加路径");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除景点
     * @param event
     */
    @FXML
    void deleteScenic(ActionEvent event) {
        ScenicVertex choose = manageScenic.getSelectionModel().getSelectedItem();
        for (int i = 0; i < scenicVertices.size(); i++) {
            if (choose.getName().equals(scenicVertices.get(i).getName())) {
                scenicVertices.remove(i);
            }
        }


        for (int i = 0; i < edges.size(); i++) {
            if (choose.getName().equals(edges.get(i).getOneScenic()) || choose.getName().equals(edges.get(i).getOtherScenic())) {
                edges.remove(i);
                i--;
            }
        }

        manageScenic.setItems(scenicVertices); // 链接数据库
        manageEdge.setItems(edges);
        Graph graph = new Graph();
        graph.deleteScenicVertex(choose.getName());
    }
    /**
     * 添加路
     * @param event
     */
    @FXML
    void addEdge(ActionEvent event) {
        String oneNames = oneName.getText();
        String otherNames = otherName.getText();
        String distances = distance.getText();
        String timeCosts = timeCost.getText();

        oneName.clear();
        otherName.clear();
        distance.clear();
        timeCost.clear();
        Graph graph = new Graph();
        graph.addScenicPath(oneNames, otherNames, Integer.parseInt(distances), Float.parseFloat(timeCosts));
        initialize();
    }
    /**
     * 删除路
     * @param event
     */
    @FXML
    void deleteEdge(ActionEvent event) {
        Edge choose = manageEdge.getSelectionModel().getSelectedItem();
        for (int i = 0; i < edges.size(); i++) {
            if (choose.getOneScenic().equals(edges.get(i).getOneScenic())) {
                edges.remove(i);
            }
        }
        manageEdge.setItems(edges);
        Graph graph = new Graph();
        graph.deleteScenicPath(choose.getOneScenic(), choose.getOtherScenic());

    }
    /**
     * 发布公告
     * @param event
     */
    @FXML
    void launchNotice(ActionEvent event) {
        String content = noticeContent.getText();
        Graph graph = new Graph();
        graph.addNotice(content);
        noticeContent.clear();
        try {
            AnchorPane anchorPane;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/Succeed.fxml"));
            anchorPane = loader.load();
            Scene scene = new Scene(anchorPane);
            stage.setTitle("发布成功");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 停车
     * @param event
     */
    @FXML
    void parkNewCar(ActionEvent event) {
        try{
            String newCar = inputCarID.getText();
            parkingLotIn.parkCar(newCar);
            initialize();
        }catch (Exception e){
            try {
                AnchorPane anchorPane = new AnchorPane();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/presentationLayer/GUI/view/CarExit.fxml"));
                anchorPane = (AnchorPane)loader.load();
                Scene scene = new Scene(anchorPane);
                stage1.setTitle("车辆重复");
                stage1.setScene(scene);
                stage1.show();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }


    }
    /**
     * 出车
     * @param event
     */
    @FXML
    void leaveCar(ActionEvent event) {
        Car choose = parking.getSelectionModel().getSelectedItem();
        parkingLotIn.leaverCar(choose.getCarId());
        initialize();
    }
    /**
     * 返回首界面
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

//    public static void main(String[] args) {
//        ManagerIndex managerIndex = new ManagerIndex();
//        managerIndex.initialize();
//    }


}

