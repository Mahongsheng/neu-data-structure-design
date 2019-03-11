package persistenceLayer.connectDB;

import businessLayer.admin.Admin;
import businessLayer.parking.Car;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * 调用DAO实现方法
 * @author 软英1702 马洪升 20175188
 */
public class CarSrvc {
    /**
     * 我们使用这个方法从数据库中获取所有车辆数据，并放到list集合
     *
     * @return list
     */
    public ArrayList<Car> search() {
        CarDao carDao = new CarDao();
        return carDao.search();
    }

    /**
     * 我们使用这个方法来判断是否要添加的车辆已存在，判断不存在后进行添加。
     *
     * @param
     * @param
     * @return
     */
    public boolean insert(String carID, Timestamp arriveTime, Timestamp leaveTime, int position, String stayTime, int cost, boolean ifWaiting) {
        if (carID.isEmpty()) {
            return false;
        }
        CarDao carDao = new CarDao();//创建一个数据库操作类
        List<Car> list = carDao.search();//读取数据库所有的车辆数据，并放到list集合
        //检测用户名是否已存在
        for (int i = 0; i < list.size(); i++) {
            //如果用户名已存在，不添加
            if (list.get(i).getCarId().equals(carID)) {
                return false;
            }
        }
        carDao.insert(carID, arriveTime, leaveTime, position, stayTime, cost, ifWaiting);//添加用户
        return true;
    }

    /**
     * 我们使用这个方法来更改车辆离开信息。
     *
     * @param
     * @param
     * @return
     */
    public boolean updateByCarID(String carID, Timestamp leaveTime, String stayTime, int cost) {
        if (!carID.isEmpty()) {
            CarDao carDao = new CarDao();
            List<Car> list = carDao.search();
            //查找到要更新的车辆
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCarId().equals(carID)) {
                    carDao.updateByCarID(carID, leaveTime, stayTime, cost);
                    return true;
                }
            }
        }
        //System.out.println("end");
        return false;
    }

    /**
     * 我们使用这个方法来更改车辆离开信息。
     *
     * @param
     * @param
     * @return
     */
    public boolean updateByCarID(String carID, int position) {
        if (!carID.isEmpty()) {
            CarDao carDao = new CarDao();
            List<Car> list = carDao.search();
            //查找到要更新的车辆
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCarId().equals(carID)) {
                    carDao.updatePosition(carID, position);
                    return true;
                }
            }
        }
        //System.out.println("end");
        return false;
    }


    public boolean updateWaiting(String carID, boolean ifWaiting) {
        if (!carID.isEmpty()) {
            CarDao carDao = new CarDao();
            List<Car> list = carDao.search();
            //查找到要更新的车辆
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCarId().equals(carID)) {
                    carDao.updateWaiting(carID, ifWaiting);
                    return true;
                }
            }
        }
        //System.out.println("end");
        return false;
    }

}
