package persistenceLayer.connectDB;

import businessLayer.admin.Admin;
import businessLayer.parking.Car;

import java.net.CacheRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * DAO模式连接数据库类
 * @author 软英1702 马洪升 20175188
 */
public class CarDao {
    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:ScenicSpotManagementSystem.db";

    /**
     * 我们使用这个方法来向数据库中添加停车场中的车辆信息。
     *
     * @param
     * @param
     */
    public void insert(String carID, Timestamp arriveTime, Timestamp leaveTime, int position, String stayTime, int cost, boolean ifWaiting) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 导入驱动
            Class.forName(JDBC_DRIVER);
            // 连接通道
            conn = DriverManager.getConnection(DB_URL);
            // 创建SQL语句，添加数据

            String waiting = "";

            if (ifWaiting) {
                waiting = "是";
            } else {
                waiting = "否";
            }

            String sql = "INSERT INTO Car " + "(carID, arriveTime, leaveTime, position, stayTime, cost, ifWaiting) VALUES ( '" + carID + "','" + arriveTime +
                    "','" + leaveTime + "','" + position + "','" + stayTime + "','" + cost + "','" + waiting + "')";
            //执行SQL语句
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 我们使用这个方法去在数据库中查询到所有的车辆信息并存储到列表中。
     *
     * @return list
     */
    public ArrayList<Car> search() {//查询所有
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Car> list = new ArrayList<Car>();

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "SELECT * FROM Car";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //一行一条数据
            while (rs.next()) {
                Car car = new Car(rs.getString(1));
                car.setArriveTime(rs.getDate(2));
                car.setLeaveTime(rs.getDate(3));
                car.setPosition(rs.getInt(4));
                car.setStayTime(rs.getString(5));
                car.setCost(rs.getInt(6));
                boolean ifWaiting = true;
                if (rs.getString(7).equals("是")) {
                    ifWaiting = true;
                } else {
                    ifWaiting = false;
                }
                car.setIfWaiting(ifWaiting);
                list.add(car);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return list; // 返还列表。
    }

    /**
     * 因为车辆ID是唯一的，所以我们使用这个方法去修改车辆离开的信息
     *
     * @param
     * @param
     */
    public void updateByCarID(String carID, Timestamp leaveTime, String stayTime, int cost) {
        Connection conn = null;
        Statement stmt = null;
        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);
            Timestamp leaveTimes = new Timestamp(leaveTime.getTime());

            String sql = "UPDATE Car SET leaveTime = '" + leaveTimes + "' WHERE carID = '" + carID + "'";
            String sql1 = "UPDATE Car SET stayTime = '" + stayTime + "' WHERE carID = '" + carID + "'";
            String sql2 = "UPDATE Car SET cost = '" + cost + "' WHERE carID = '" + carID + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql1);
            stmt.executeUpdate(sql2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 因为车辆ID是唯一的，所以我们使用这个方法去修改车辆位置信息
     *
     * @param
     * @param
     */
    public void updatePosition(String carID, int position) {
        Connection conn = null;
        Statement stmt = null;
        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "UPDATE Car SET position = '" + position + "' WHERE carID = '" + carID + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 因为车辆ID是唯一的，所以我们使用这个方法去修改车辆位置信息
     *
     * @param
     * @param
     */
    public void updateWaiting(String carID, boolean ifWaiting) {
        Connection conn = null;
        Statement stmt = null;
        try {
            String ifWaitings;
            if (ifWaiting) {
                ifWaitings = "是";
            } else {
                ifWaitings = "否";
            }
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);

            String sql = "UPDATE Car SET ifWaiting = '" + ifWaitings + "' WHERE carID = '" + carID + "'";
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}