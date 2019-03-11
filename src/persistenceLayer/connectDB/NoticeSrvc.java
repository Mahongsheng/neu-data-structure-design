package persistenceLayer.connectDB;

import businessLayer.graph.Notice;
import businessLayer.parking.Car;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
/**
 * 调用DAO实现方法
 * @author 软英1702 马洪升 20175188
 */
public class NoticeSrvc {
    /**
     * 我们使用这个方法从数据库中获取所有公告，并放到list集合
     *
     * @return list
     */
    public ArrayList<Notice> search() {
        NoticeDao noticeDao = new NoticeDao();
        return noticeDao.search();
    }

    /**
     * 添加公告
     * @param
     * @param
     * @return
     */
    public boolean insert(String time, String content) {
        // 如果用户名或密码为空，不进行添加
        if (time.isEmpty()) {
            return false;
        }
        NoticeDao noticeDao = new NoticeDao();//创建一个数据库操作类
        noticeDao.insert(time, content);//添加用户
        return true;
    }
}
