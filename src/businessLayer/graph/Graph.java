package businessLayer.graph;

import businessLayer.algorithm.ACO;
import businessLayer.algorithm.DFS;
import businessLayer.algorithm.Dijkstra;
import businessLayer.algorithm.Floyd;
import businessLayer.dataStructure.AdjacencyList;
import javafx.beans.property.StringProperty;
import persistenceLayer.connectDB.EdgeSrvc;
import persistenceLayer.connectDB.NoticeSrvc;
import persistenceLayer.connectDB.ScenicSrvc;

import java.io.FileNotFoundException;
import java.security.AlgorithmConstraints;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
/**
 * 这是一个图类
 * 其包含的方法如下：
 * @method initializeALGraph 初始化图, addScenicVertex 加景点, deleteScenicVertex 删景点,
 * addScenicPath 加路, deleteScenicPath 删路, findScenic 找景点, sortByPop 按照热度排序,
 * sortByWay 按照岔路数排序, findShotestPath 找最短路径, findGuideLoop 找导游图, findSho 通过弗洛伊德输出可达矩阵,
 * addNotice 添加公告, updateNotice 更新公告
 */
public class Graph {
    public static AdjacencyList ALGraph;
    public static ArrayList<Notice> notices = new ArrayList<>();
    public static ScenicVertex[] vertexList;
    public static Edge[] edgeList;

    public Graph() {
        initializeALGraph();
    }

    /**
     * 我们将图进行初始化
     */
    public void initializeALGraph() {
        updateNotice();
        ScenicSrvc scenicSrvc = new ScenicSrvc();
        ArrayList<ScenicVertex> s = scenicSrvc.search();
        vertexList = new ScenicVertex[s.size()];
        for (int i = 0; i < s.size(); i++) {
            vertexList[i] = s.get(i);
        }
        EdgeSrvc edgeSrvc = new EdgeSrvc();
        ArrayList<Edge> e = edgeSrvc.search();
        edgeList = new Edge[e.size()];
        for (int i = 0; i < e.size(); i++) {
            edgeList[i] = e.get(i);
        }
        ALGraph = new AdjacencyList(vertexList, edgeList);
    }

    /**
     * 添加景点
     * @param name
     * @param introduction
     * @param popularity
     * @param hasRestArea
     * @param hasToilet
     * @param x
     * @param y
     * @return
     */
    public boolean addScenicVertex(String name, String introduction, int popularity,
                                   boolean hasRestArea, boolean hasToilet, int x, int y) {
        ScenicVertex scenicVertex = new ScenicVertex(name, introduction, popularity, hasRestArea, hasToilet, x, y);
        return ALGraph.addNewVNode(scenicVertex);
    }

    /**
     * 删除景点
     * @param name
     * @return
     */
    public boolean deleteScenicVertex(String name) {
        return ALGraph.deleteVNode(name);
    }

    /**
     * 添加路
     * @param oneScenic
     * @param otherScenic
     * @param weight
     * @param timeCost
     * @return
     */
    public boolean addScenicPath(String oneScenic, String otherScenic, int weight, float timeCost) {
        Edge edge = new Edge(oneScenic, otherScenic, weight, timeCost);
        return ALGraph.addNewENode(edge);
    }

    /**
     * 删除路
     * @param oneName
     * @param otherName
     * @return
     */
    public boolean deleteScenicPath(String oneName, String otherName) {
        return ALGraph.deleteENode(oneName, otherName);

    }

    /**
     * 找景点
     * @param name
     * @return
     */
    public boolean findScenic(String name) {
        if (!ALGraph.findScenic(name)) {
            System.out.println("您输入的景点不存在！");
            return false;
        }
        return true;
    }

    /**
     * 按照热度排序
     */
    public void sortByPop() {
        ALGraph.sortByPop();
    }

    /**
     * 按照岔路数排序
     */
    public void sortByWay() {
        ALGraph.sortByWay();
    }

    /**
     * 找最短路径
     * @param oneName
     * @param otherName
     * @return
     */
    public HashMap findShotestPath(String oneName, String otherName) {
        AdjacencyList.VNode[] vNodes = ALGraph.getAdjacencyList();
        ScenicVertex first = new ScenicVertex();
        ScenicVertex end = new ScenicVertex();
        for (int i = 0; i < vNodes.length; i++) {
            if (vNodes[i] != null) {
                if (vNodes[i].getData().getName().equals(oneName)) {
                    first = vNodes[i].getData();
                }
                if (vNodes[i].getData().getName().equals(otherName)) {
                    end = vNodes[i].getData();
                }
            }
        }
        // 调用迪杰斯特拉算法
        Dijkstra dijkstra = new Dijkstra();
        HashMap<String, String> result = dijkstra.dijkstra(ALGraph, first, end);
        System.out.println(result.get(end.getName()));
        return result;

    }

    /**
     * 找到导游图
     * @param oneName
     * @param otherName
     * @return
     * @throws Exception
     */
    public String[] findGuideLoop(String oneName, String otherName) throws Exception {
        //蚁群算法或者DFS，我们这里使用DFS
//        ACO aco;
//        aco = new ACO();
        Graph graph = new Graph();
        int[][] arr = graph.ALGraph.returnGraph();

        String[] noNull = new String[graph.ALGraph.getAmountOfVertex()];
        int count = 0;
        for (int i = 0; i < graph.ALGraph.getVertexList().length; i++) {
            if (graph.ALGraph.getVertexList()[i] != null) {
                noNull[count] = graph.ALGraph.getVertexList()[i].getName();
                count++;
            }
        }
        int start = 0;
        int end = 0;
        for (int i = 0; i < noNull.length; i++) {
            if (noNull[i].equals(oneName)) {
                start = i;
            } else if (noNull[i].equals(otherName)) {
                end = i;
            }
        }
//        Floyd floyd = new Floyd();
//        int[][] accessibleMatrix = floyd.floyd(noNull, arr);
        DFS dfs = new DFS();
        dfs.creatTourSortGraph(graph.ALGraph, arr, start, end);
        ArrayList<String> result = dfs.getResult();

        String[] finalResult;
        if (start == end){
            finalResult = new String[noNull.length + 1];
            for (int i = 0; i < arr.length + 1; i++) {
                finalResult[i] = result.get(i);
            }
        }else {
            finalResult = new String[noNull.length];
            for (int i = 0; i < arr.length; i++) {
                finalResult[i] = result.get(i);
            }
        }
        return finalResult;
//        aco.init(accessibleMatrix, 50, noNull,"北门");
//        aco.run(2000);
//        aco.ReportResult();
    }
    public void findSho() {
        Graph graph = new Graph();
        int[][] arr = graph.ALGraph.returnGraph();
        String[] noNull = new String[graph.ALGraph.getAmountOfVertex()];
        int count = 0;
        for (int i = 0; i < graph.ALGraph.getVertexList().length; i++) {
            if (graph.ALGraph.getVertexList()[i] != null) {
                noNull[count] = graph.ALGraph.getVertexList()[i].getName();
                count++;
            }
        }

        Floyd floyd = new Floyd();
        floyd.floyd(noNull, arr);
    }

    /**
     * 添加公告
     * @param content
     */
    public void addNotice(String content) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd: HH:mm:ss");
        Notice notice = new Notice(sdf.format(new Date()), content);
        notices.add(notice);
        NoticeSrvc noticeSrvc = new NoticeSrvc();
        noticeSrvc.insert(notice.getTime(), notice.getContent());
    }

    /**
     * 更新公告
     */
    public void updateNotice() {
        NoticeSrvc noticeSrvc = new NoticeSrvc();
        notices = noticeSrvc.search();
    }
}