package businessLayer.parking;

import persistenceLayer.connectDB.CarSrvc;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 记录停车日志
 * @author 软英1702 马洪升 20175188
 */
public class ParkingLog {
    private CarSrvc carSrvc = new CarSrvc();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
    public void findAllLog(){
        List<Car> cars =  carSrvc.search();
        for(Car car: cars){
            System.out.println("车牌号：" + car.getCarId());
            System.out.println("到达时间：" + sdf.format(car.getArriveTime()));
            System.out.println("离开时间：" + sdf.format(car.getLeaveTime()));
            System.out.println("总驻留时长：" + car.getStayTime());
            System.out.println("消费：" + car.getCost());
        }
    }
}
