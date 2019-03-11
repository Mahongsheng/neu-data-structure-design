package businessLayer.algorithm;

import businessLayer.dataStructure.AdjacencyList;
import businessLayer.graph.ScenicVertex;

import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个深度优先的遍历方法
 * @author 软英1702 马洪升 20175188
 */
public class DFS {
    private AdjacencyList.VNode[] viewMap;//所有结点
    private int viewNum;//结点数目
    private ArrayList<String> result = new ArrayList<>();//结果

    public ArrayList<String> getResult() {
        return result;
    }

    /**
     * 进行初始化
     * @param adjacencyList 邻接表
     * @param adjMatrix 邻接矩阵
     * @param start 开始结点位置
     * @param end 最终节点位置
     */
    public void creatTourSortGraph(AdjacencyList adjacencyList, int[][] adjMatrix, int start, int end) {
        this.viewMap = adjacencyList.getAdjacencyList();
        this.viewNum = adjMatrix.length;
        result.add(viewMap[start].getData().getName());
        boolean[] used = new boolean[viewNum];//用于标记图中顶点是否被访问
        int[] path = new int[viewNum];//记录哈密顿回路路径
        for (int i = 0; i < viewNum; i++) {
            used[i] = false;//初始化，所有顶点均未被遍历
            path[i] = -1;//初始化，未选中起点及到达任何顶点
        }
        used[start] = true;
        used[end] = true;//表示从第1个顶点开始遍历
        path[0] = start;//表示哈密顿回路起点为第0个顶点
        if (start != end) {
            path[viewNum - 1] = end;//最开始的时候把他赋值导致形成回路时结果出错
        }
        dfs(adjMatrix, path, used, 1, start, end);//从第0个顶点开始进行深度优先遍历,如果存在哈密顿回路，输出一条回路，否则无输出
    }

    /**
     * 进行迭代遍历
     * 参数信息同上
     * 当使用命令行调用该方法时可以取消注释所有的System以输出结果
     * @param adjMatrix
     * @param path
     * @param used
     * @param step 步数
     * @param start
     * @param end
     * @return
     */
    public boolean dfs(int[][] adjMatrix, int[] path, boolean[] used, int step, int start, int end) {
        if (step == viewNum && start == end) {//当已经遍历完图中所有顶点
            if (adjMatrix[path[step - 1]][start] != 32676) { //最后一步到达的顶点能够回到起点
//                System.out.print(viewMap[path[0]].getData().getName() + "—>");
                for (int i = 1; i < path.length; i++) {
//                    System.out.print(viewMap[path[i]].getData().getName() + "—>");
                    result.add(viewMap[path[i]].getData().getName());
                }
//                System.out.print(viewMap[path[0]].getData().getName());
                result.add(viewMap[path[0]].getData().getName());
                return false;
            }
            return false;
        } else if (start != end && step == (viewNum - 1)) {
            if (adjMatrix[path[step - 1]][end] != 32676) {
//                System.out.print(viewMap[path[0]].getData().getName() + "—>");
                for (int i = 1; i < path.length - 1; i++) {
//                    System.out.print(viewMap[path[i]].getData().getName() + "—>");
                    result.add(viewMap[path[i]].getData().getName());
                }
//                System.out.print(viewMap[path[viewNum - 1]].getData().getName());
                result.add(viewMap[path[viewNum - 1]].getData().getName());
                return false;
            }
            return false;
        } else {
            for (int i = 0; i < viewNum; i++) {
                if (!used[i] && adjMatrix[path[step - 1]][i] != 32676) {
                    used[i] = true;
                    path[step] = i;
                    if (dfs(adjMatrix, path, used, step + 1, start, end))
                        return true;
                    else {
                        used[i] = false;//进行回溯处理
                        path[step] = -1;
                    }
                }
            }
        }
        return false;
    }
}

