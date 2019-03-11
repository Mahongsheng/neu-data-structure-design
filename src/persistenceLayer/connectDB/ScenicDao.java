package persistenceLayer.connectDB;

import businessLayer.graph.Notice;
import businessLayer.graph.ScenicVertex;

import java.sql.*;
import java.util.ArrayList;
/**
 * DAO模式连接数据库类
 * @author 软英1702 马洪升 20175188
 */
public class ScenicDao {
    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:ScenicSpotManagementSystem.db";

    /**
     * 我们使用这个方法来向数据库中添加景点。
     * @param
     * @param
     */
    public void insert(String name, String intro, int pop, boolean rest, boolean toilet, int x, int y) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 导入驱动
            Class.forName(JDBC_DRIVER);
            // 连接通道
            conn = DriverManager.getConnection(DB_URL);
            // 创建SQL语句，添加数据
            String sql = "INSERT INTO Scenic " + "(name,introduction,pop,rest,toilet,X,Y) VALUES ( '" + name + "','" + intro + "','" + pop + "','" + rest +
                    "','" + toilet + "','" + x + "','" + y +  "')";
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
     * 我们使用这个方法去在数据库中查询到所有的景点并存储到列表中。
     *
     * @return list
     */
    public ArrayList<ScenicVertex> search() {//查询所有
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<ScenicVertex> list = new ArrayList<ScenicVertex>();

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "SELECT * FROM Scenic";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //一行一条数据
            while (rs.next()) {
                ScenicVertex scenicVertex = new ScenicVertex(rs.getString(1),rs.getString(2),
                        rs.getInt(3),rs.getBoolean(4),rs.getBoolean(5),rs.getInt(6),
                        rs.getInt(7));
                list.add(scenicVertex);
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
     * 删除景点。
     * @param name
     */
    public void deleteByName(String name) {
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "DELETE FROM Scenic WHERE name = '" + name + "'";

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
