package persistenceLayer.connectDB;

import businessLayer.admin.Admin;
import businessLayer.graph.Notice;
import businessLayer.graph.ScenicVertex;

import java.util.ArrayList;
import java.util.List;
/**
 * 调用DAO实现方法
 * @author 软英1702 马洪升 20175188
 */
public class ScenicSrvc {
    /**
     * 我们使用这个方法从数据库中获取所有景点，并放到list集合
     *
     * @return list
     */
    public ArrayList<ScenicVertex> search() {
        ScenicDao scenicDao = new ScenicDao();
        return scenicDao.search();
    }

    /**
     * 我们使用这个方法来判断是否要添加的景点已存在，判断不存在后进行添加。
     *
     * @param
     * @param
     * @return
     */
    public boolean insert(String name, String intro, int pop, boolean rest, boolean toilet, int x, int y) {
        // 如果用户名或密码为空，不进行添加
        if (name.isEmpty()) {
            return false;
        }
        ScenicDao scenicDao = new ScenicDao();//创建一个数据库操作类
        scenicDao.insert(name, intro, pop, rest, toilet, x, y);//添加用户
        return true;
    }

    /**
     * 我们使用这个方法来根据用户名来删除特定景点。
     *
     * @param name
     * @return
     */
    public boolean deleteByName(String name) {
        ScenicDao scenicDao = new ScenicDao();
        ArrayList<ScenicVertex> scenicVertices = scenicDao.search();
        for (int i = 0; i < scenicVertices.size(); i++) {
            if (scenicVertices.get(i).getName().equals(name)) {
                scenicDao.deleteByName(name);
                return true;
            }
        }
        return false;
    }
}
