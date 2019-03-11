package businessLayer.parking;

import businessLayer.dataStructure.LinkedQueue;
import businessLayer.dataStructure.LinkedQueueIterator;
import businessLayer.dataStructure.Stack;
import businessLayer.dataStructure.StackIterator;
import persistenceLayer.connectDB.CarSrvc;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 这是一个停车场
 * 其包含的主要方法如下：
 * @method parkCar 停车， leaverCar 出车， isParkingAll 停车场是否满，isWaitEmpty便道是否为空，
 * ifCarIDExist 车牌号是否已存在，putIntoList 放入车牌号，chgPositionInWaiting 改变便道中车的位置，
 * printAllParkingCars 输出所有车辆
 * 除此之外还有很多为了实现停车和出车而封装的方法，主要是考虑到模块性，考虑到代码的简洁性和可读性
 * @author 软英1702 马洪升 20175188
 */
public class ParkingLot {
    private static Stack<Car> parkingLot = new Stack<>(5);//停车场
    private static Stack<Car> tempParkingLot = new Stack<>(4);//临时停车场
    private static LinkedQueue<Car> waitingCars = new LinkedQueue<>();//便道
    private static String[] carIDs = new String[5];//所有车牌号
    private static int tempPosition = 0;//便道的位置
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
    private CarSrvc carSrvc = new CarSrvc();//调用数据库

    /**
     * 初始化
     * 从数据库中读取数据
     */
    public ParkingLot() {
        ArrayList<Car> cars = carSrvc.search();
        TreeMap<Date, Car> treeMap = new TreeMap<>();
        for (Car car : cars) {
            if (car.isIfWaiting()) {
                waitingCars.enQueue(car);
                tempPosition++;
            } else {
                if (car.getCost() == 0){
                    treeMap.put(car.getArriveTime(), car);
                }
            }
        }
        Iterator iterator = treeMap.entrySet().iterator();
        int count = 0;
        while (iterator.hasNext()){
            Map.Entry<Date, Car> entry = (Map.Entry)iterator.next();
            parkingLot.push(entry.getValue());
            carIDs[count] = entry.getValue().getCarId();
            count++;
        }

    }

    /**
     * 停车场是否满
     * @return
     */
    public boolean isParkingAll() {
        if (parkingLot.getDepth() == 5) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否等待队列为空
     * @return
     */
    public boolean isWaitEmpty() {
        if (waitingCars.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 停车
     * @param carID
     */
    public void parkCar(String carID) {
        String str;
        Car car = new Car(carID);
        if (isParkingAll()) {
            tempPosition++;
            car.setPosition(tempPosition);
            waitingCars.enQueue(car);
            System.out.println("车牌号：" + carID + "进入便道等待\n" + "地点： " + car.getPosition());
            Timestamp arrTime = new Timestamp(car.getArriveTime().getTime());
            Timestamp leaveTime = new Timestamp(car.getLeaveTime().getTime());
            carSrvc.insert(car.getCarId(), arrTime, leaveTime, car.getPosition(), "", 0, true);

        } else {
            if (ifCarIDExist(carID)) {
                System.out.println("车辆已存在。");
            } else {
                putIntoList(carID);
                car.setArriveTime(new Date());
                car.setLeaveTime(new Date());
                System.out.println(parkingLot.getDepth());
                car.setPosition(parkingLot.getDepth() + 1);
                parkingLot.push(car);
                str = sdf.format(car.getArriveTime());
                str += "\n" + "车牌号" + car.getCarId() + "的车进入停车场";
                str += " 位置为： " + car.getPosition();
                System.out.println(str);

            }
            Timestamp arrTime = new Timestamp(car.getArriveTime().getTime());
            Timestamp leaveTime = new Timestamp(car.getLeaveTime().getTime());
            carSrvc.insert(car.getCarId(), arrTime, leaveTime, car.getPosition(), "", 0, false);
        }
    }

    /**
     * 出车
     * @param carID
     */
    public void leaverCar(String carID) {
        if (ifCarIDExistThenDelete(carID)) {
            String str = "";
            while (!parkingLot.isEmpty() && !parkingLot.peek().getCarId().equals(carID)) {
                tempParkingLot.push(parkingLot.pop());
            }
            Car leaveCar = parkingLot.pop();
            leaveCar.setLeaveTime(new Date());
            leaveCar.setIfArrive(false);
            str = leaveCar.calculateCost();
            System.out.println(str);
            Timestamp leaveTime = new Timestamp(leaveCar.getLeaveTime().getTime());
            carSrvc.updateByCarID(carID, leaveTime, leaveCar.getStayTime(), leaveCar.getCost());
            while (!tempParkingLot.isEmpty()) {
                Car temp = tempParkingLot.pop();
                temp.setPosition(parkingLot.getDepth() + 1);
                carSrvc.updateByCarID(temp.getCarId(), temp.getPosition());
                parkingLot.push(temp);
            }
            if (!isWaitEmpty()) {
                Car car = waitingCars.deQueue();
                if (ifCarIDExist(car.getCarId())) {
                    System.out.println("车辆已存在。");
                } else {
                    putIntoList(car.getCarId());
                    str += "";
                    chgPositionInWaiting();
                    car.setPosition(parkingLot.getDepth() + 1);
                    tempPosition--;
                    parkingLot.push(car);
                    str += sdf.format(car.getArriveTime()) + "\n" + "车牌号为" + car.getCarId() + "的车进入停车场";
                    str += " 位置为： " + car.getPosition();
                    System.out.println(str);
                    carSrvc.updateWaiting(car.getCarId(), false);

                }
            }
        } else {
            System.out.println("您输入的车辆不存在。");
        }
    }

    /**
     * 判断车牌号是否已经存在
     * @param carID
     * @return
     */
    public boolean ifCarIDExist(String carID) {
        for (int i = 0; i < carIDs.length; i++) {
            if (carIDs[i] != null) {
                if (carIDs[i].equals(carID)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 如果车牌号存在那么就在所有车牌号中删除掉它
     * （所有车牌号中包含等待队列中的，我们只在车加入到停车场时才进行车牌号重复判断）
     * @param carID
     * @return
     */
    public boolean ifCarIDExistThenDelete(String carID) {
        for (int i = 0; i < carIDs.length; i++) {
            if (carIDs[i] != null) {
                if (carIDs[i].equals(carID)) {
                    carIDs[i] = null;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 放入车牌号
     * @param carID
     */
    public void putIntoList(String carID) {
        for (int i = 0; i < carIDs.length; i++) {
            if (carIDs[i] == null) {
                carIDs[i] = carID;
                return;
            }
        }
    }

    /**
     * 改变便道中车的位置
     */
    public void chgPositionInWaiting() {
        LinkedQueueIterator<Car> linkedQueueIterator = waitingCars.getIterator();
        int i = 1;
        while (linkedQueueIterator.hasNext()) {
            Car car = linkedQueueIterator.next();
            car.setPosition(i);
            carSrvc.updateByCarID(car.getCarId(), car.getPosition());
            i++;
        }


    }

    /**
     * 输出所有记录
     */
    public void printAllParkingCars() {
        StackIterator<Car> stackIterator = parkingLot.getIterator();
        while (stackIterator.hasNext()) {
            Car car = stackIterator.next();
            System.out.println("车牌号： " + car.getCarId() + "  " + "到达时间： " + sdf.format(car.getArriveTime()) + "  " + "位置： " + car.getPosition());
        }
    }

}
