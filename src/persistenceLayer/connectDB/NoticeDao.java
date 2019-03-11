package persistenceLayer.connectDB;

import businessLayer.admin.Admin;
import businessLayer.graph.Notice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * DAO模式连接数据库类
 * @author 软英1702 马洪升 20175188
 */
public class NoticeDao {

    static final String JDBC_DRIVER = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:ScenicSpotManagementSystem.db";

    /**
     * 我们使用这个方法来向数据库中添加公告。
     *
     * @param time
     * @param content
     */
    public void insert(String time, String content) {
        Connection conn = null;
        Statement stmt = null;
        try {
            // 导入驱动
            Class.forName(JDBC_DRIVER);
            // 连接通道
            conn = DriverManager.getConnection(DB_URL);
            // 创建SQL语句，添加数据
            String sql = "INSERT INTO Notice " + "(time,content) VALUES ( '" + time + "','" + content + "')";
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
     * 我们使用这个方法去在数据库中查询到所有的公告并存储到列表中。
     *
     * @return list
     */
    public ArrayList<Notice> search() {//查询所有
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ArrayList<Notice> list = new ArrayList<Notice>();

        try {

            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL);

            String sql = "SELECT * FROM Notice";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            //一行一条数据
            while (rs.next()) {
                Notice notice = new Notice();
                notice.setTime(rs.getString(1));
                notice.setContent(rs.getString(2));
                list.add(notice);
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

}