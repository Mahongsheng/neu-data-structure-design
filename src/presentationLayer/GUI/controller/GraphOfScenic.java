package presentationLayer.GUI.controller;

import businessLayer.graph.Edge;
import businessLayer.graph.Graph;
import businessLayer.graph.ScenicVertex;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.HashMap;
/**
 * 输出景点分布图模块
 * @author 软英1702 马洪升 20175188
 */
public class
GraphOfScenic{
    /**
     * 不同的pane代表不同的景点分布图
     * pane1和pane2中我们将路线标红进行输出
     */
    private AnchorPane pane = new AnchorPane();
    private AnchorPane pane1 = new AnchorPane();
    private AnchorPane pane2 = new AnchorPane();

    public AnchorPane getPane() {
        return pane;
    }
    public AnchorPane getPane1() {
        return pane1;
    }

    public AnchorPane getPane2() {
        return pane2;
    }

    public void start() {

        pane.setPrefHeight(747);
        pane.setPrefWidth(500);

        Graph graph = new Graph();
        ScenicVertex[] sVs = graph.vertexList;
        Edge[] es = graph.edgeList;
        HashMap<String, ScenicVertex> hashMap = new HashMap<>();

        for (ScenicVertex scenicVertex : sVs) {
            if (scenicVertex != null) {
                hashMap.put(scenicVertex.getName(), scenicVertex);
                Label label = new Label(scenicVertex.getName());
                label.setLayoutX(scenicVertex.getX());
                label.setLayoutY(scenicVertex.getY());
                pane.getChildren().add(label);
            }
        }
        for (Edge edge : es) {
            if (edge != null) {
                ScenicVertex one = hashMap.get(edge.getOneScenic());
                ScenicVertex other = hashMap.get(edge.getOtherScenic());

                Line line = new Line();
                line.setStartX(one.getX());
                line.setStartY(one.getY());
                line.setEndX(other.getX());
                line.setEndY(other.getY());
                Label label = new Label(String.valueOf(edge.getWeight()));
                label.setLayoutX((one.getX() + other.getX()) / 2);
                label.setLayoutY((one.getY() + other.getY()) / 2);
                pane.getChildren().add(line);
                pane.getChildren().add(label);
            }
        }
    }

    public void start(String[] strings) {

        pane2.setPrefHeight(520);
        pane2.setPrefWidth(500);

        Graph graph = new Graph();
        ScenicVertex[] sVs = graph.vertexList;
        Edge[] es = graph.edgeList;
        HashMap<String, ScenicVertex> hashMap = new HashMap<>();

        for (ScenicVertex scenicVertex : sVs) {
            if (scenicVertex != null) {
                hashMap.put(scenicVertex.getName(), scenicVertex);
                Label label = new Label(scenicVertex.getName());
                label.setLayoutX(scenicVertex.getX() - 50);
                label.setLayoutY(scenicVertex.getY());
                pane2.getChildren().add(label);
            }
        }
        for (Edge edge : es) {
            if (edge != null) {
                ScenicVertex one = hashMap.get(edge.getOneScenic());
                ScenicVertex other = hashMap.get(edge.getOtherScenic());
                int exit = 0;
                for (int i = 0; i < strings.length;i++){
                    if (i == (strings.length -1)){
                        break;
                    }
                    if ((strings[i].equals(one.getName()) && strings[i+1].equals(other.getName())
                            || (strings[i].equals(other.getName()) && strings[i+1].equals(one.getName())))){
                        Line line = new Line();
                        line.setStartX(one.getX() - 50);
                        line.setStartY(one.getY());
                        line.setEndX(other.getX() - 50);
                        line.setEndY(other.getY());
                        line.setStroke(Color.RED);
//                        line.setFill(Color.RED);
                        Label label = new Label(String.valueOf(edge.getWeight()));
                        label.setLayoutX((one.getX() + other.getX() - 100) / 2);
                        label.setLayoutY((one.getY() + other.getY()) / 2);
                        pane2.getChildren().add(line);
                        pane2.getChildren().add(label);
                        exit = 1;
                    }
                }
                if (exit == 1){
                    continue;
                }
                Line line = new Line();
//                line.setStroke(Color.RED);
                line.setStartX(one.getX() - 50);
                line.setStartY(one.getY());
                line.setEndX(other.getX() - 50);
                line.setEndY(other.getY());
                Label label = new Label(String.valueOf(edge.getWeight()));
                label.setLayoutX((one.getX() + other.getX() - 100) / 2);
                label.setLayoutY((one.getY() + other.getY()) / 2);
                pane2.getChildren().add(line);
                pane2.getChildren().add(label);
            }
        }
    }

    public void other() {

        pane1.setPrefHeight(520);
        pane1.setPrefWidth(500);

        Graph graph = new Graph();
        ScenicVertex[] sVs = graph.vertexList;
        Edge[] es = graph.edgeList;
        HashMap<String, ScenicVertex> hashMap = new HashMap<>();

        for (ScenicVertex scenicVertex : sVs) {
            if (scenicVertex != null) {
                hashMap.put(scenicVertex.getName(), scenicVertex);
                Label label = new Label(scenicVertex.getName());
                label.setLayoutX(scenicVertex.getX() - 50);
                label.setLayoutY(scenicVertex.getY());
                pane1.getChildren().add(label);
            }
        }
        for (Edge edge : es) {
            if (edge != null) {
                ScenicVertex one = hashMap.get(edge.getOneScenic());
                ScenicVertex other = hashMap.get(edge.getOtherScenic());

                Line line = new Line();
                line.setStartX(one.getX() - 50);
                line.setStartY(one.getY());
                line.setEndX(other.getX() - 50);
                line.setEndY(other.getY());
                Label label = new Label(String.valueOf(edge.getWeight()));
                label.setLayoutX((one.getX() + other.getX() - 100) / 2);
                label.setLayoutY((one.getY() + other.getY()) / 2);
                pane1.getChildren().add(line);
                pane1.getChildren().add(label);
            }
        }
    }

}
