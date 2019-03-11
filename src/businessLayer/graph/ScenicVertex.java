package businessLayer.graph;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 这是一个结点类，其为景点结点
 * 包含景点名称、介绍、热度、是否有公厕和休息区以及岔路数以及位置（x,y)
 * @author 软英1702 马洪升 20175188
 */
public class ScenicVertex {
    private StringProperty name;
    private StringProperty introduction;
    private IntegerProperty popularity;
    private boolean hasRestArea;
    private boolean hasToilet;
    private int numOfNode;

    private int x;
    private int y;

    private StringProperty rest;
    private StringProperty toilet;

    public ScenicVertex() {
    }

    public ScenicVertex(String name, String introduction, int popularity, boolean hasRestArea, boolean hasToilet, int x, int y) {
        this.name = new SimpleStringProperty(name);
        this.introduction = new SimpleStringProperty(introduction);
        this.popularity = new SimpleIntegerProperty(popularity);
        this.hasRestArea = hasRestArea;
        this.hasToilet = hasToilet;
        this.numOfNode = 0;

        if (hasRestArea){
            rest = new SimpleStringProperty("有");
        }else {
            rest = new SimpleStringProperty("无");;
        }
        if (hasToilet){
            toilet = new SimpleStringProperty("有");;
        }else {
            toilet = new SimpleStringProperty("有");;
        }
        this.x = x;
        this.y = y;

    }

    public ScenicVertex(StringProperty name, StringProperty introduction, IntegerProperty popularity, StringProperty rest, StringProperty toilet) {
        this.name = name;
        this.introduction = introduction;
        this.popularity = popularity;
        this.rest = rest;
        this.toilet = toilet;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getIntroduction() {
        return introduction.get();
    }

    public void setIntroduction(String introduction) {
        this.introduction.set(introduction);
    }

    public int getPopularity() {
        return popularity.get();
    }

    public void setPopularity(int popularity) {
        this.popularity.set(popularity);
    }

    public boolean isHasRestArea() {
        return hasRestArea;
    }

    public void setHasRestArea(boolean hasRestArea) {
        this.hasRestArea = hasRestArea;
    }

    public boolean isHasToilet() {
        return hasToilet;
    }

    public void setHasToilet(boolean hasToilet) {
        this.hasToilet = hasToilet;
    }

    public int getNumOfNode() {
        return numOfNode;
    }

    public void setNumOfNode(int numOfNode) {
        this.numOfNode = numOfNode;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty introductionProperty() {
        return introduction;
    }

    public IntegerProperty popularityProperty() {
        return popularity;
    }

    public String getRest() {
        return rest.get();
    }

    public StringProperty restProperty() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest.set(rest);
    }

    public String getToilet() {
        return toilet.get();
    }

    public StringProperty toiletProperty() {
        return toilet;
    }

    public void setToilet(String toilet) {
        this.toilet.set(toilet);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
