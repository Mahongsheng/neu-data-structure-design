package businessLayer.algorithm;

import businessLayer.graph.ScenicVertex;

import java.io.*;
import java.util.Random;

/**
 * 这是一个蚁群优化算法：一种用来在图中寻找优化路径的机率型算法，其灵感来源于蚂蚁在寻找食物过程中发现路径的行为
 * 具有群智能、启发式、分布式的特点
 * @author 软英1702 马洪升 20175188
 */
public class ACO {
    /**
     * 构建一个蚂蚁类，每个蚂蚁都包含自己的“旅途数组”、“路径长度”、“景点是否访问数组”
     */
    public class Ant {
        /**
         * 蚂蚁获得的路径
         */
        public int[] tour;
        //unvisitedCity 取值是0或1，
        //1表示没有访问过，0表示访问过
        int[] unvisited; // 顺序与tour一致
        /**
         * 蚂蚁获得的路径长度
         */
        public int tourlength;
        int amountOfVertex;// 城市总数
        /**
         * 分配蚂蚁到某个城市中
         * 同时完成蚂蚁包含字段的初始化工作
         * @param vertexConut 总的结点数量
         */
        public void selectVertex(int vertexConut, int firstCity) {
            amountOfVertex = vertexConut;
            unvisited = new int[vertexConut];
            tour = new int[vertexConut + 1];
            tourlength = 0;
            for (int i = 0; i < vertexConut; i++) {
                tour[i] = -1;
                unvisited[i] = 1;
            }
            unvisited[firstCity] = 0;
            tour[0] = firstCity;
        }

        /**
         * 选择下一个结点
         * @param index    需要选择第index个结点了
         * @param tao      全局的信息素信息
         * @param distance 全局的距离矩阵信息
         */
        public void selectNextVertex(int index, double[][] tao, int[][] distance) {
            double[] p;
            p = new double[amountOfVertex]; // 每个结点被选择的概率
            double alpha = 1;
            double beta = 2;
            double sum = 0;
            int currentVertex = tour[index - 1];// 当前所在结点
            //计算公式中的分母部分
            for (int i = 0; i < amountOfVertex; i++) {
                if (unvisited[i] == 1 && distance[currentVertex][i] != 32767) {
                    sum += (Math.pow(tao[currentVertex][i], alpha) *
                            Math.pow(1.0 / distance[currentVertex][i], beta)); // 套用公式
                }// ！
            }
            //计算每个结点被选中的概率
            for (int i = 0; i < amountOfVertex; i++) {
                if (unvisited[i] == 0 || distance[currentVertex][i] == 32767) {
                    p[i] = 0.0;
                } else {
                    p[i] = (Math.pow(tao[currentVertex][i], alpha) *
                            Math.pow(1.0 / distance[currentVertex][i], beta)) / sum; // 套用公式
                }
            }
            long r1 = System.currentTimeMillis();
            Random rnd = new Random(r1);
            double selectp = rnd.nextDouble();
            //轮盘赌选择一个结点；
            double sumselect = 0;
            int selectVertex = -1;
            for (int i = 0; i < amountOfVertex; i++) {
                sumselect += p[i];
                if (sumselect >= selectp) {
                    selectVertex = i;
                    break;
                }
            }
            if (selectVertex == -1) {
                System.out.println();
            }
            tour[index] = selectVertex; // 被选择的结点
            unvisited[selectVertex] = 0;
        }

        /**
         * 计算蚂蚁获得的路径的长度
         *
         * @param distance 全局的距离矩阵信息
         */
        public void calTourLength(int[][] distance) {
            tourlength = 0;
            tour[amountOfVertex] = tour[0];
            for (int i = 0; i < amountOfVertex; i++) {
                tourlength += distance[tour[i]][tour[i + 1]];
            }
        }
    }

    //定义蚂蚁群
    Ant[] ants;
    int antcount;//蚂蚁的数量
    int[][] distance;//表示结点间距离
    double[][] tao;//信息素矩阵
    int vertexCount;//结点数量
    int[] besttour;//求解的最佳路径
    int bestlength;//求的最优解的长度
    String[] scenicVertices;
    int firstVertex = 0;

    /**
     * 初始化函数
     *
     * @param
     * @param antnum 系统用到蚂蚁的数量
     * @throws
     */
    public void init(int[][] graph, int antnum, String[] scenicVertice, String first) {
        scenicVertices = scenicVertice;
        antcount = antnum;
        ants = new Ant[antcount];
        //初始化信息素矩阵
        distance = graph;
        vertexCount = graph.length;
        tao = new double[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                tao[i][j] = 0.1;
            }
        }
        bestlength = Integer.MAX_VALUE;
        besttour = new int[vertexCount + 1];

        for (int i = 0; i < scenicVertices.length; i++){
            if (scenicVertices[i].equals(first)){
                firstVertex = i;
            }
        }
        //放置蚂蚁
        for (int i = 0; i < antcount; i++) {
            ants[i] = new Ant();
            ants[i].selectVertex(vertexCount, firstVertex);
        }
    }

    /**
     * ACO的运行过程
     *
     * @param maxgen ACO的最多循环次数
     */
    public void run(int maxgen) {
        for (int runtimes = 0; runtimes < maxgen; runtimes++) {
            //每一只蚂蚁移动的过程
            for (int i = 0; i < antcount; i++) {
                for (int j = 1; j < vertexCount; j++) {
                    ants[i].selectNextVertex(j, tao, distance);
                }
                //计算蚂蚁获得的路径长度
                ants[i].calTourLength(distance);
                if (ants[i].tourlength < bestlength) {
                    //保留最优路径
                    bestlength = ants[i].tourlength;
                    System.out.print("第" + runtimes + "代，发现新的解" + bestlength);

                    for (int j = 0; j < ants[i].tour.length; j++) {
                        System.out.print(" " + scenicVertices[ants[i].tour[j]]);
                    }
                    System.out.println();

                    for (int j = 0; j < vertexCount + 1; j++) {
                        besttour[j] = ants[i].tour[j];
                    }
                }
            }
            //更新信息素矩阵
            updateTao();
            //放置蚂蚁
            for (int i = 0; i < antcount; i++) {
                ants[i].selectVertex(vertexCount, firstVertex);//////
            }
        }
    }

    /**
     * 更新信息素矩阵
     */
    private void updateTao() {
        double rou = 0.5;// 挥发参数
        //信息素挥发
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                tao[i][j] = tao[i][j] * (1 - rou);
                tao[j][i] = tao[j][i] * (1 - rou);
            }
        }
        //信息素更新
        for (int i = 0; i < antcount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                tao[ants[i].tour[j]][ants[i].tour[j + 1]] += 1.0 / ants[i].tourlength;
                tao[ants[i].tour[j + 1]][ants[i].tour[j]] += 1.0 / ants[i].tourlength;
            }
        }
    }
    /**
     * 输出程序运行结果
     */
    public void reportResult() {
        System.out.println("最优路径长度是" + bestlength);
    }
}