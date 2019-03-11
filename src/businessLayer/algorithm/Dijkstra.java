package businessLayer.algorithm;

import businessLayer.dataStructure.AdjacencyList;
import businessLayer.graph.ScenicVertex;

import java.util.*;

/**
 * 这是一个迪杰斯特拉算法
 * @author 软英1702 马洪升 20175188
 */
public class Dijkstra {
    //存放已经接触到但是还没有进行遍历的结点
    private Queue<AdjacencyList.VNode> S = new LinkedList<>();
    //按照节点名称存放已经接触到但是还没有进行遍历的结点，这样方便取得
    private HashMap<String, AdjacencyList.VNode> Ss = new HashMap<>();
    //存放未遍历的结点
    private HashMap<String, AdjacencyList.VNode> U = new HashMap<>();
    //存放遍历的步长结果
    private HashMap<String, Integer> result = new HashMap<>();
    //存放路径
    private HashMap<String, String> path = new HashMap<>();

    /**
     * 初始化
     * @param all 邻接表
     * @param first 开始结点
     * @param end 结束结点
     * @return
     */
    public HashMap dijkstra(AdjacencyList all, ScenicVertex first, ScenicVertex end) {
        AdjacencyList.VNode[] adjacencyList = all.getAdjacencyList();
        for (int i = 0; i < adjacencyList.length; i++) {//初始化哈希表和队列
            if (adjacencyList[i] != null) {
                U.put(adjacencyList[i].getData().getName(), adjacencyList[i]);
                path.put(adjacencyList[i].getData().getName(), first.getName());
                result.put(adjacencyList[i].getData().getName(), 0);
            }
        }
        AdjacencyList.VNode fromNow = U.get(first.getName());
        U.remove(first.getName());//移除第一个结点
        S.add(fromNow);//加入第一个结点
        Ss.put(first.getName(), fromNow);//加入第一个结点
        AdjacencyList.ENode eNode = fromNow.getFirstENode();
        while (eNode != null) {//遍历该结点的所有边
            /*
            判断并找出边的另一端，作为另一个结点
             */
            if (eNode.getData().getOneScenic().equals(fromNow.getData().getName())) {
                int weight = eNode.getData().getWeight();
                result.replace(eNode.getData().getOtherScenic(), weight);//加入路长
                S.add(U.get(eNode.getData().getOtherScenic()));//加入队列
                String s = path.get(eNode.getData().getOtherScenic());
                s = s + " " + eNode.getData().getOtherScenic();
                path.replace(eNode.getData().getOtherScenic(), s);//更新路径

            } else {
                int weight = eNode.getData().getWeight();
                result.replace(eNode.getData().getOneScenic(), weight);//加入路长
                S.add(U.get(eNode.getData().getOneScenic()));//加入队列
                String s = path.get(eNode.getData().getOneScenic());
                s = s + " " + eNode.getData().getOneScenic();
                path.replace(eNode.getData().getOneScenic(), s);//更新路径
            }
            eNode = eNode.getNextENode();
        }
        S.poll();//移除刚刚遍历的结点
        String next = S.poll().getData().getName();//取出即将开始遍历的结点
        recursive(end, next);//运行递归
        return path;//返还路径
    }

    /**
     * 进行递归
     * @param end
     * @param next
     */
    public void recursive(ScenicVertex end, String next) {
        if (U.size() == 1) {// 当到达最后一个结点时停止递归
            return;
        }
        if (!U.containsKey(next)){
            next = S.poll().getData().getName();
            recursive(end, next);
            return;
        }
        AdjacencyList.VNode fromNow = U.get(next);
        U.remove(next);
        AdjacencyList.ENode eNode = fromNow.getFirstENode();
        while (eNode != null) {//遍历该结点的所有边
            int preWeight = result.get(next);//得到原本的路长，以便后续相加
            /*
            判断并找出边的另一端，作为另一个结点
             */
            if (eNode.getData().getOneScenic().equals(fromNow.getData().getName())) {
                int weight = eNode.getData().getWeight();
                String name = eNode.getData().getOtherScenic();
                if (!name.equals(S.peek().getData().getName())){//排除该边是否和上一个结点连接的情况
                    Ss.remove(S.peek().getData().getName());
                    if (Ss.containsKey(name)){//判断是否该结点已经遍历过，如果遍历过则进行判断
                        if (weight + preWeight < result.get(name)) {//若新值更小，则替换
                            result.replace(eNode.getData().getOtherScenic(), weight + preWeight);//更新路长结果
                            String str = path.get(name);
                            String[] strs = str.split(" ");
                            String newStr = "";
                            for (int m = 0; m < strs.length; m++){//做路径的更新
                                if (strs[m].equals(name)){
                                    newStr += strs[m];
                                    break;
                                }
                                newStr += strs[m];
                            }
                            newStr = newStr + eNode.getData().getOneScenic();
                            path.put(eNode.getData().getOneScenic(), newStr);//更新路径
                        }
                    }else {//当前结点没有被遍历过
                        if (result.get(name) == 0) {//路长是否为初始化时候的值
                            result.replace(eNode.getData().getOtherScenic(), weight+preWeight);//更新路长
                            String str = path.get(eNode.getData().getOneScenic());
                            str = str + " " + name;
                            path.put(name, str); //更新路径
                            S.add(U.get(eNode.getData().getOtherScenic()));
                        } else if (weight + preWeight < result.get(name)) {//新路长是否更短
                            S.add(U.get(eNode.getData().getOtherScenic()));
                            result.replace(eNode.getData().getOtherScenic(), weight + preWeight);//更新路长
                            String str = path.get(eNode.getData().getOneScenic());
                            str = str + " " + name;
                            path.put(name, str);//更新路径
                        }
                    }
                    Ss.put(S.peek().getData().getName(), S.peek());//将该结点放入哈希表中
                }
            } else {
                /*
                该部分内容与上一个if中一模一样，因为每条边有两个结点，所以我们只是来判断哪个结点是当前结点
                哪一个是想要遍历的结点
                 */
                int weight = eNode.getData().getWeight();
                String name = eNode.getData().getOneScenic();
                if (!name.equals(S.peek().getData().getName())){
                    Ss.remove(S.peek().getData().getName());
                    if (Ss.containsKey(name)){
                        if (weight + preWeight < result.get(name)) {
                            result.replace(eNode.getData().getOneScenic(), weight + preWeight);
                            String str = path.get(name);
                            String[] strs = str.split(" ");
                            String newStr = "";
                            for (int m = 0; m < strs.length; m++){
                                if (strs[m].equals(name)){
                                    newStr += strs[m];
                                    break;
                                }
                                newStr += strs[m];
                            }
                            newStr = newStr + eNode.getData().getOtherScenic();
                            path.put(eNode.getData().getOtherScenic(), newStr);
                        }
                    }else {
                        if (result.get(name) == 0) {
                            result.replace(eNode.getData().getOneScenic(), weight + preWeight);
                            S.add(U.get(eNode.getData().getOneScenic()));
                            String str = path.get(eNode.getData().getOtherScenic());
                            str = str + " " + name;
                            path.put(name, str);
                        } else if (weight + preWeight < result.get(name)) {
                            result.replace(eNode.getData().getOtherScenic(), weight + preWeight);
                            String str = path.get(name);
                            str = str + " " + name;
                            path.put(name, str);
                            S.add(U.get(eNode.getData().getOneScenic()));
                        }
                    }
                    Ss.put(S.peek().getData().getName(), S.peek());
                }
            }
            eNode = eNode.getNextENode();
        }
        next = S.poll().getData().getName();//取出下一个要遍历的结点
        Ss.put(fromNow.getData().getName(), fromNow);
        recursive(end, next);//递归
    }
}
