package businessLayer.graph;

import javafx.beans.property.*;

/**
 * 这是一个边类
 * 其包含两个端点的名称，路长和时间花费
 * @author 软英1702 马洪升 20175188
 */
public class Edge {
    private StringProperty oneScenic;
    private StringProperty otherScenic;
    private IntegerProperty weight;
    private FloatProperty timeCost;

    public Edge() {
    }

    public Edge(String oneScenic, String otherScenic, int weight, float timeCost) {
        this.oneScenic = new SimpleStringProperty(oneScenic);
        this.otherScenic = new SimpleStringProperty(otherScenic);
        this.weight = new SimpleIntegerProperty(weight);
        this.timeCost = new SimpleFloatProperty(timeCost);
    }

    public String getOneScenic() {
        return oneScenic.get();
    }

    public StringProperty oneScenicProperty() {
        return oneScenic;
    }

    public void setOneScenic(String oneScenic) {
        this.oneScenic.set(oneScenic);
    }

    public String getOtherScenic() {
        return otherScenic.get();
    }

    public StringProperty otherScenicProperty() {
        return otherScenic;
    }

    public void setOtherScenic(String otherScenic) {
        this.otherScenic.set(otherScenic);
    }

    public int getWeight() {
        return weight.get();
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
    }

    public float getTimeCost() {
        return timeCost.get();
    }

    public FloatProperty timeCostProperty() {
        return timeCost;
    }

    public void setTimeCost(float timeCost) {
        this.timeCost.set(timeCost);
    }
}
