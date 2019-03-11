package businessLayer.parking;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

/**
 * 这是一个汽车类
 * 其包含车牌号，是否到达，到达时间，离开时间，停留时间，花销，位置，是否等待
 * @author 软英1702 马洪升 20175188
 */
public class Car {
    private StringProperty carId;
    private BooleanProperty ifArrive;
    private Date arriveTime;
    private Date leaveTime;
    private int position;
    private StringProperty stayTime;
    private int cost;
    private BooleanProperty ifWaiting;


    public Car() {
    }

    public Car(String carId) {
        this.carId = new SimpleStringProperty(carId);
        this.ifArrive = new SimpleBooleanProperty(true);
        this.arriveTime = new Date();
        this.leaveTime = new Date();
        this.stayTime = new SimpleStringProperty("");
        this.ifWaiting = new SimpleBooleanProperty(true);
        this.cost = 0;
    }

    public String getCarId() {
        return carId.get();
    }

    public StringProperty carIdProperty() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId.set(carId);
    }

    public boolean isIfArrive() {
        return ifArrive.get();
    }

    public BooleanProperty ifArriveProperty() {
        return ifArrive;
    }

    public void setIfArrive(boolean ifArrive) {
        this.ifArrive.set(ifArrive);
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getStayTime() {
        return stayTime.get();
    }

    public StringProperty stayTimeProperty() {
        return stayTime;
    }

    public void setStayTime(String stayTime) {
        this.stayTime.set(stayTime);
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isIfWaiting() {
        return ifWaiting.get();
    }

    public BooleanProperty ifWaitingProperty() {
        return ifWaiting;
    }

    public void setIfWaiting(boolean ifWaiting) {
        this.ifWaiting.set(ifWaiting);
    }

    /**
     * 阶梯型收费。
     * 当停车时间超过一天时，收费20元；
     * 当停车时间大于3小时时，收费10元；
     * 当停车时间小于3小时时，收费5元。
     * @return str
     */
    public String calculateCost() {
        long exp = this.getLeaveTime().getTime() - this.getArriveTime().getTime();
        long days = exp / (1000*60*60*24);
        long hours = (exp - days * (24*60*60*1000)) / (1000*60*60);
        long minutes = (exp-days*(24*60*60*1000)-hours*(60*60*1000))/(1000*60);
        int cost;
        if(days > 0){
            cost = 20;
        }
        else if(hours > 3) {
            cost = 10;
        }
        else{
            cost = 5;
        }
        String str = "车牌号："+this.getCarId()+"离开了停车场\n停留了"+days+"天"+hours+"小时"+minutes+"分\n";
        str += "需缴费"+cost+"元";
        String time = days+"天"+hours+"小时"+minutes+"分";
        this.setStayTime(time);
        this.setCost(cost);
        return str;
    }
}
