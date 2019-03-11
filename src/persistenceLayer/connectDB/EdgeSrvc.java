package persistenceLayer.connectDB;

import businessLayer.graph.Edge;
import businessLayer.graph.ScenicVertex;

import java.util.ArrayList;
/**
 * 调用DAO实现方法
 * @author 软英1702 马洪升 20175188
 */
public class EdgeSrvc {
    /**
     * 我们使用这个方法从数据库中获取所有边数据，并放到list集合
     *
     * @return list
     */
    public ArrayList<Edge> search() {
        EdgeDao edgeDao = new EdgeDao();
        return edgeDao.search();
    }

    /**
     * 我们使用这个方法来判断是否要添加的边已存在，判断不存在后进行添加。
     *
     * @param
     * @param
     * @return
     */
    public boolean insert(String oneName, String otherName, int weight, float timeCost) {
        // 如果用户名或密码为空，不进行添加
        if (oneName.isEmpty()) {
            return false;
        }
        EdgeDao edgeDao = new EdgeDao();//创建一个数据库操作类
        edgeDao.insert(oneName, otherName, weight, timeCost);//添加用户
        return true;
    }

    /**
     * 我们使用这个方法来根据用户名来删除特定边。
     *
     * @param
     * @return
     */
    public boolean deleteByName(String oneName, String otherName) {
        EdgeDao edgeDao = new EdgeDao();
        ArrayList<Edge> edges = edgeDao.search();
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).getOneScenic().equals(oneName) && edges.get(i).getOtherScenic().equals(otherName)) {
                edgeDao.deleteByName(oneName,otherName);
                return true;
            }
        }
        return false;
    }
}
