package persistenceLayer.connectDB;

import businessLayer.admin.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO模式连接数据库类
 * @author 软英1702 马洪升 20175188
 */
public class AdminInfoDao {
    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:ScenicSpotManagementSystem.db";

    /**
     * 我们使用这个方法来向数据库中添加管理员用户。
     *
     * @param userName
     * @param pwd
     */
    public void insert(String userName, String pwd) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 导入驱动
            Class.forName(JDBC_DRIVER);
            // 连接通道
            conn = DriverManager.getConnection(DB_URL);
            // 创建SQL语句，添加数据
            String sql = "INSERT INTO Admin " + "(AdminName,Password) VALUES ( '" + userName + "','" + pwd + "')";
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
     * 我们使用这个方法去在数据库中查询到所有的用户并存储到列表中。
     *
     * @return list
     */
    public List<Admin> search() {//查询所有
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Admin> list = new ArrayList<Admin>();

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "SELECT * FROM Admin";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //一行一条数据
            while (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminName(rs.getString(1));
                admin.setPwd(rs.getString(2));
                list.add(admin);
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
     * 因为用户名是唯一的，所以我们使用这个方法根据用户名去修改用户的密码。
     *
     * @param name
     * @param pwd
     */
    public void updateByName(String name, String pwd) {//修改用户密码
        Connection conn = null;
        Statement stmt = null;
        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "UPDATE Admin SET Password = '" + pwd + "' WHERE AdminName = '" + name + "'";
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
     * 因为用户名唯一，所以我们使用这个方法根据姓名删除指定用户。
     *
     * @param name
     */
    public void deleteByName(String name) {
        // 根据姓名删除用户
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "DELETE FROM Admin WHERE AdminName = '" + name + "'";

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
