package persistenceLayer.connectDB;

import businessLayer.graph.Edge;
import businessLayer.graph.ScenicVertex;

import java.sql.*;
import java.util.ArrayList;
/**
 * DAO模式连接数据库类
 * @author 软英1702 马洪升 20175188
 */
public class EdgeDao {
    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:ScenicSpotManagementSystem.db";

    /**
     * 我们使用这个方法来向数据库中添加边。
     *
     * @param
     * @param
     */
    public void insert(String oneName, String otherName, int weight, float timeCost) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 导入驱动
            Class.forName(JDBC_DRIVER);
            // 连接通道
            conn = DriverManager.getConnection(DB_URL);
            // 创建SQL语句，添加数据
            String sql = "INSERT INTO Edge " + "(oneName,otherName,weight,timeCost) VALUES ( '" + oneName + "','" + otherName + "','" + weight + "','" + timeCost + "')";
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
     * 我们使用这个方法去在数据库中查询到所有的边并存储到列表中。
     *
     * @return list
     */
    public ArrayList<Edge> search() {//查询所有
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Edge> list = new ArrayList<Edge>();

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "SELECT * FROM Edge";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //一行一条数据
            while (rs.next()) {
                Edge edge = new Edge(rs.getString(1),rs.getString(2),rs.getInt(3)
                ,rs.getFloat(4));
                list.add(edge);
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
     * 我们使用这个方法根据姓名删除指定边。
     *
     * @param
     */
    public void deleteByName(String oneName, String otherName) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "DELETE FROM Edge WHERE oneName = '" + oneName + "'" + "and otherName = '" + otherName + "'";
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
