package businessLayer.dataStructure;

import businessLayer.algorithm.KMP;
import businessLayer.algorithm.QuickSort;
import businessLayer.graph.Edge;
import businessLayer.graph.ScenicVertex;
import persistenceLayer.connectDB.EdgeSrvc;
import persistenceLayer.connectDB.ScenicSrvc;
import persistenceLayer.ioFile.ReadEdge;
import persistenceLayer.ioFile.ReadVertex;

import java.util.ArrayList;

/**
 * 这是一个邻接表
 * 包含：VNode结点类、ENode边结点类，其中ENode为链式结构
 * 邻接表结构为：用VNode[]来存放结点，每个结点中拥有firstENode，即首边，每一个ENode中都包含下一个ENode
 * 因此便构成了一个邻接表
 * 包含的方法如下：添加删除方法中都调用了数据库
 *
 * @method traverseENode 遍历边, addENode 加边, outputGraph 输出邻接表, outputMatrix 输出邻接矩阵,
 *     returnGraph 返回邻接矩阵, addNewVNode 添加结点, deleteVNode 删除结点, addNewENode 添加新边,
 *     deleteENode 删除边, findScenic 找景点, sortByPop 按照热度排序, sortByWay 按照岔路排序
 *
 * @author 软英1702 马洪升 20175188
 */
public class AdjacencyList {
    /**
     * 这是一个边类
     */
    public class ENode {
        private int toVertexPosition;//另一结点位置
        private Edge data;//数据
        private ENode nextENode;//下一个边

        public ENode(int toVertexPosition, Edge data, ENode nextENode) {
            this.toVertexPosition = toVertexPosition;
            this.data = data;
            this.nextENode = nextENode;
        }

        public int getToVertexPosition() {
            return toVertexPosition;
        }

        public void setToVertexPosition(int toVertexPosition) {
            this.toVertexPosition = toVertexPosition;
        }

        public Edge getData() {
            return data;
        }

        public void setData(Edge data) {
            this.data = data;
        }

        public ENode getNextENode() {
            return nextENode;
        }

        public void setNextENode(ENode nextENode) {
            this.nextENode = nextENode;
        }
    }

    /**
     * 这是一个结点类
     */
    public class VNode {
        private ScenicVertex data;//数据
        private ENode firstENode;//首边

        public VNode(ScenicVertex data, ENode firstENode) {
            this.data = data;
            this.firstENode = firstENode;
        }

        public ScenicVertex getData() {
            return data;
        }

        public void setData(ScenicVertex data) {
            this.data = data;
        }

        public ENode getFirstENode() {
            return firstENode;
        }

        public void setFirstENode(ENode firstENode) {
            this.firstENode = firstENode;
        }
    }

    /*
    我们使用这个VNode[]来存储所有结点，每个结点中包含一个首边，首边为链式结构
    包含所有与该结点相关的边
     */
    private VNode[] wholeVertexNode;

    private ScenicVertex[] vertexList;//结点表
    private Edge[] edgeList;//边表
    private int[][] graphValue;//邻接图
    private int amountOfVertex;//结点数量
    private int amountOfEdge;//边数量
    public static ArrayList<ScenicVertex> findSce = new ArrayList<>();//存储找到的结点
    public static ArrayList<ScenicVertex> sortSce = new ArrayList<>();//存储排序后的结点

    public AdjacencyList(ScenicVertex[] vertexList, Edge[] edgeList) {
        this.wholeVertexNode = new VNode[vertexList.length];
        this.amountOfVertex = vertexList.length;
        this.amountOfEdge = edgeList.length;
        this.vertexList = vertexList;
        this.edgeList = edgeList;
        creatGraph();//初始化开始
    }

    public VNode[] getAdjacencyList() {
        return wholeVertexNode;
    }

    public int getAmountOfVertex() {
        return amountOfVertex;
    }

    public ScenicVertex[] getVertexList() {
        return vertexList;
    }

    public Edge[] getEdgeList() {
        return edgeList;
    }

    public void setVertexList(ScenicVertex[] vertexList) {
        this.vertexList = vertexList;
    }

    public void setEdgeList(Edge[] edgeList) {
        this.edgeList = edgeList;
    }

    /**
     * 初始化整个邻接表
     */
    public void creatGraph() {
        //首先初始化各头结点
        for (int i = 0; i < vertexList.length; i++) {
            VNode vNode = new VNode(vertexList[i], null);
            wholeVertexNode[i] = vNode;
        }
        //遍历所有边并添加进各头结点
        for (int i = 0; i < edgeList.length; i++) {
            Edge edge = edgeList[i];
            int onePosition = 0;
            int otherPosition = 0;
            for (int j = 0; j < wholeVertexNode.length; j++) {
                VNode vNode = wholeVertexNode[j];
                if (edge.getOneScenic().equals(vNode.getData().getName())) {
                    onePosition = j;
                }
                if (edge.getOtherScenic().equals(vNode.getData().getName())) {
                    otherPosition = j;
                }
            }
            //将边加入
            addENode(onePosition, otherPosition, edge);
            addENode(otherPosition, onePosition, edge);
        }
    }

    /**
     * 输入当前结点的位置，返回最后一个边
     * @param position
     * @return
     */

    public ENode traverseENode(int position) {
        ENode eNode = wholeVertexNode[position].getFirstENode();
        while (eNode.getNextENode() != null) {
            eNode = eNode.getNextENode();
        }
        return eNode;
    }

    /**
     * 输入当前结点的位置，下一个结点的位置和边以加入新边
     * @param currPosition
     * @param nextPosition
     * @param edge
     * @return
     */
    public boolean addENode(int currPosition, int nextPosition, Edge edge) {
        VNode vNode = wholeVertexNode[currPosition];
        ENode newENode = new ENode(nextPosition, edge, null);
        if (vNode.getFirstENode() != null) {
            ENode eNode = traverseENode(currPosition);
            eNode.setNextENode(newENode);
            vNode.getData().setNumOfNode(vNode.getData().getNumOfNode() + 1);
        } else {
            vNode.setFirstENode(newENode);
            vNode.getData().setNumOfNode(vNode.getData().getNumOfNode() + 1);
        }
        return true;
    }

    /**
     * 打印一个邻接表
     */
    public void outputGraph() {
        for (int i = 0; i < wholeVertexNode.length; i++) {
            if (wholeVertexNode[i] != null) {
                System.out.print(wholeVertexNode[i].getData().getName());
                ENode eNode = wholeVertexNode[i].getFirstENode();
                while (eNode != null) {
                    System.out.print(" [" + eNode.getData().getOneScenic() + "," + eNode.getData().getOtherScenic()
                            + "," + eNode.getToVertexPosition() + "]");
                    eNode = eNode.getNextENode();
                }
                System.out.println();
            }
        }
    }

    /**
     * 打印一个邻接矩阵
     * @return
     */
    public int[][] outputMatrix() {
        //创建一个矩阵并赋予结点数大小
        graphValue = new int[amountOfVertex][amountOfVertex];
        //初始化矩阵
        for (int i = 0; i < amountOfVertex; i++) {
            for (int j = 0; j < amountOfVertex; j++) {
                graphValue[i][j] = 32676;
            }
        }
        //遍历结点
        ArrayList<ScenicVertex> s = new ArrayList<>();
        for (int i = 0; i < vertexList.length; i++) {
            if (vertexList[i] != null) {
                s.add(vertexList[i]);
            }
        }
        //遍历所有边
        for (int i = 0; i < edgeList.length; i++) {
            if (edgeList[i] != null) {
                int x = 0;
                int y = 0;
                for (int j = 0; j < s.size(); j++) {
                    if (edgeList[i].getOneScenic().equals(s.get(j).getName())) {
                        x = j;
                    } else if (edgeList[i].getOtherScenic().equals(s.get(j).getName())) {
                        y = j;
                    }
                }
                //设置路长
                graphValue[x][y] = edgeList[i].getWeight();
                graphValue[y][x] = edgeList[i].getWeight();
            }
        }
        //格式化打印输出
        System.out.printf("%-15s", "      ");
        for (int i = 0; i < s.size(); i++) {
            System.out.printf("%-15s", s.get(i).getName());
        }
        System.out.println();
        for (int i = 0; i < amountOfVertex; i++) {
            System.out.printf("%-15s", s.get(i).getName());
            for (int j = 0; j < graphValue[0].length; j++) {
                if (i == j) {
                    System.out.printf("%-15s", 0);
                } else {
                    System.out.printf("%-16s", graphValue[i][j]);
                }
            }
            System.out.println();
        }
        return graphValue;
    }

    /**
     * 返回邻接矩阵
     * @return
     */

    public int[][] returnGraph() {
        graphValue = new int[amountOfVertex][amountOfVertex];
        for (int i = 0; i < amountOfVertex; i++) {
            for (int j = 0; j < amountOfVertex; j++) {
                if (i == j){
                    graphValue[i][j] = 0;
                }else {
                    graphValue[i][j] = 32676;
                }
            }
        }

        ArrayList<ScenicVertex> s = new ArrayList<>();
        for (int i = 0; i < vertexList.length; i++) {
            if (vertexList[i] != null) {
                s.add(vertexList[i]);
            }
        }
        for (int i = 0; i < edgeList.length; i++) {
            if (edgeList[i] != null) {
                int x = 0;
                int y = 0;
                for (int j = 0; j < s.size(); j++) {
                    if (edgeList[i].getOneScenic().equals(s.get(j).getName())) {
                        x = j;
                    } else if (edgeList[i].getOtherScenic().equals(s.get(j).getName())) {
                        y = j;
                    }
                }
                graphValue[x][y] = edgeList[i].getWeight();
                graphValue[y][x] = edgeList[i].getWeight();
            }
        }
        return graphValue;
    }

    /**
     * 添加一个新的结点
     * @param newVertex 新结点
     * @return
     */
    public boolean addNewVNode(ScenicVertex newVertex) {
        ScenicSrvc scenicSrvc = new ScenicSrvc();
        scenicSrvc.insert(newVertex.getName(), newVertex.getIntroduction(), newVertex.getPopularity()
                , newVertex.isHasRestArea(), newVertex.isHasRestArea(), newVertex.getX(), newVertex.getY());

        if (amountOfVertex == wholeVertexNode.length) {
            int newCapacity = amountOfVertex + 1;
            VNode[] newWholeVertex = new VNode[newCapacity];
            vertexList = new ScenicVertex[newCapacity];
            for (int i = 0; i < wholeVertexNode.length; i++) {
                newWholeVertex[i] = wholeVertexNode[i];
                vertexList[i] = wholeVertexNode[i].getData();
            }
            VNode newVNode = new VNode(newVertex, null);
            newWholeVertex[amountOfVertex] = newVNode;
            vertexList[amountOfVertex] = newVNode.getData();
            this.wholeVertexNode = newWholeVertex;
        } else {
            for (int i = 0; i < wholeVertexNode.length; i++) {
                if (wholeVertexNode[i] == null) {
                    VNode newVNode = new VNode(newVertex, null);
                    wholeVertexNode[i] = newVNode;
                }
            }
        }
        amountOfVertex++;
        return true;
    }

    /**
     * 按照输入的名字删除一个结点
     * @param name
     * @return
     */
    public boolean deleteVNode(String name) {
        ScenicSrvc scenicSrvc = new ScenicSrvc();
        scenicSrvc.deleteByName(name);

        EdgeSrvc edgeSrvc = new EdgeSrvc();
        for (int i = 0; i < wholeVertexNode.length; i++) {
            if (wholeVertexNode[i] != null) {
                if (wholeVertexNode[i].getData().getName().equals(name)) {
                    wholeVertexNode[i] = null;
                    vertexList[i] = null;
                } else {
                    ENode eNode = wholeVertexNode[i].getFirstENode();
                    if (eNode.getData().getOneScenic().equals(name) || eNode.getData().getOtherScenic().equals(name)) {
                        wholeVertexNode[i].setFirstENode(eNode.getNextENode());
                        wholeVertexNode[i].getData().setNumOfNode(wholeVertexNode[i].getData().getNumOfNode() - 1);
                        for (int j = 0; j < edgeList.length; j++) {
                            if (edgeList[j] != null) {
                                if (edgeList[j].getOneScenic().equals(name) || edgeList[j].getOtherScenic().equals(name)) {
                                    edgeList[j] = null;
                                }
                            }
                        }
                        edgeSrvc.deleteByName(eNode.getData().getOneScenic(), eNode.getData().getOtherScenic());
                        amountOfEdge--;
                    } else {
                        eNode = eNode.getNextENode();
                        ENode lastNode = eNode;
                        while (eNode != null) {
                            if (eNode.getData().getOneScenic().equals(name) || eNode.getData().getOtherScenic().equals(name)) {
                                lastNode.setNextENode(eNode.getNextENode());
                                wholeVertexNode[i].getData().setNumOfNode(wholeVertexNode[i].getData().getNumOfNode() - 1);
                                for (int j = 0; j < edgeList.length; j++) {
                                    if (edgeList[j] != null) {
                                        if (edgeList[j].getOneScenic().equals(name) || edgeList[j].getOtherScenic().equals(name)) {
                                            edgeList[j] = null;
                                        }
                                    }
                                }
                                edgeSrvc.deleteByName(eNode.getData().getOneScenic(), eNode.getData().getOtherScenic());
                                amountOfEdge--;
                            }
                            lastNode = eNode;
                            eNode = eNode.getNextENode();
                        }
                    }
                }
            }
        }
        amountOfVertex--;
        return true;
    }

    /**
     * 加入一个新的边
     * @param newEdge
     * @return
     */
    public boolean addNewENode(Edge newEdge) {
        EdgeSrvc edgeSrvc = new EdgeSrvc();
        edgeSrvc.insert(newEdge.getOneScenic(), newEdge.getOtherScenic(), newEdge.getWeight(), newEdge.getTimeCost());
        if (amountOfEdge <= vertexList.length) {
            Edge[] newEdgeList = new Edge[amountOfEdge + 1];
            for (int i = 0; i < amountOfEdge; i++) {
                newEdgeList[i] = edgeList[i];
            }
            newEdgeList[amountOfEdge] = newEdge;
            this.edgeList = newEdgeList;
        } else {
            for (int i = 0; i < edgeList.length; i++) {
                if (edgeList[i] == null) {
                    edgeList[i] = newEdge;
                }
            }
        }

        int onePosition = 0;
        int otherPosition = 0;
        for (int i = 0; i < wholeVertexNode.length; i++) {
            if (wholeVertexNode[i] != null) {
                VNode vNode = wholeVertexNode[i];
                if (newEdge.getOneScenic().equals(vNode.getData().getName())) {
                    onePosition = i;
//                    System.out.println(onePosition);
                }
                if (newEdge.getOtherScenic().equals(vNode.getData().getName())) {
                    otherPosition = i;
//                    System.out.println(otherPosition);
                }
            }
        }
        addENode(onePosition, otherPosition, newEdge);
        addENode(otherPosition, onePosition, newEdge);
        amountOfEdge++;
        return true;
    }

    /**
     * 根据输入的边的两个结点的名称来删除改边
     * @param oneName
     * @param otherName
     * @return
     */
    public boolean deleteENode(String oneName, String otherName) {
        EdgeSrvc edgeSrvc = new EdgeSrvc();
        edgeSrvc.deleteByName(oneName, otherName);
        int onePosition = 0;
        int otherPosition = 0;
        for (int i = 0; i < wholeVertexNode.length; i++) {
            if (wholeVertexNode[i] != null) {
                VNode vNode = wholeVertexNode[i];
                if (oneName.equals(vNode.getData().getName())) {
                    onePosition = i;
//                    System.out.println(onePosition);
                }
                if (otherName.equals(vNode.getData().getName())) {
                    otherPosition = i;
//                    System.out.println(otherPosition);
                }
            }
        }
        ENode eNode = wholeVertexNode[onePosition].getFirstENode();
        if ((eNode.getData().getOneScenic().equals(oneName) && eNode.getData().getOtherScenic().equals(otherName))
                || eNode.getData().getOneScenic().equals(otherName) && eNode.getData().getOtherScenic().equals(oneName)) {
            wholeVertexNode[onePosition].setFirstENode(eNode.getNextENode());
            wholeVertexNode[onePosition].getData().setNumOfNode(wholeVertexNode[onePosition].getData().getNumOfNode() - 1);
            for (int j = 0; j < edgeList.length; j++) {
                if ((edgeList[j].getOneScenic().equals(oneName) && edgeList[j].getOtherScenic().equals(otherName)) ||
                        (edgeList[j].getOtherScenic().equals(oneName) && edgeList[j].getOneScenic().equals(otherName))) {
                    edgeList[j] = null;
                }
            }
            amountOfEdge--;
        } else {
            eNode = eNode.getNextENode();
            ENode lastNode = eNode;
            while (eNode != null) {
                if ((eNode.getData().getOneScenic().equals(oneName) && eNode.getData().getOtherScenic().equals(otherName))
                        || eNode.getData().getOneScenic().equals(otherName) && eNode.getData().getOtherScenic().equals(oneName)) {
                    lastNode.setNextENode(eNode.getNextENode());
                    wholeVertexNode[onePosition].getData().setNumOfNode(wholeVertexNode[onePosition].getData().getNumOfNode() - 1);
                    for (int j = 0; j < edgeList.length; j++) {
                        if ((edgeList[j].getOneScenic().equals(oneName) && edgeList[j].getOtherScenic().equals(otherName)) ||
                                (edgeList[j].getOtherScenic().equals(oneName) && edgeList[j].getOneScenic().equals(otherName))) {
                            edgeList[j] = null;
                        }
                    }

                    amountOfEdge--;
                }
                lastNode = eNode;
                eNode = eNode.getNextENode();
            }
        }

        ENode otherENode = wholeVertexNode[otherPosition].getFirstENode();
        if ((otherENode.getData().getOneScenic().equals(oneName) && otherENode.getData().getOtherScenic().equals(otherName))
                || otherENode.getData().getOneScenic().equals(otherName) && otherENode.getData().getOtherScenic().equals(oneName)) {
            wholeVertexNode[otherPosition].setFirstENode(otherENode.getNextENode());
            wholeVertexNode[otherPosition].getData().setNumOfNode(wholeVertexNode[otherPosition].getData().getNumOfNode() - 1);
        } else {
            otherENode = otherENode.getNextENode();
            ENode lastNode = otherENode;
            while (otherENode != null) {
                if ((otherENode.getData().getOneScenic().equals(oneName) && otherENode.getData().getOtherScenic().equals(otherName))
                        || otherENode.getData().getOneScenic().equals(otherName) && otherENode.getData().getOtherScenic().equals(oneName)) {
                    lastNode.setNextENode(otherENode.getNextENode());
                    wholeVertexNode[otherPosition].getData().setNumOfNode(wholeVertexNode[otherPosition].getData().getNumOfNode() - 1);
                }
                lastNode = otherENode;
                otherENode = otherENode.getNextENode();
            }
        }
        return true;
    }

    /**
     * 根据输入的名称来找到该结点，也可能是很多结点
     * @param name
     * @return
     */
    public boolean findScenic(String name) {
        findSce.clear();
        boolean exist = false;
        boolean notNull;
        if (name.equals("")){
            notNull = false;
        }else {
            notNull = true;
        }
        KMP kmpAl = new KMP();
        if (notNull){
            for (int i = 0; i < wholeVertexNode.length; i++) {
                if (wholeVertexNode[i] != null) {
                    if (kmpAl.kmp(wholeVertexNode[i].getData().getName(), name) != -1) {
                        System.out.println("景区名称: " + wholeVertexNode[i].getData().getName());
                        System.out.println("景区描述: " + wholeVertexNode[i].getData().getIntroduction());
                        System.out.println("景区人气: " + wholeVertexNode[i].getData().getPopularity());
                        System.out.println("是否有卫生间: " + wholeVertexNode[i].getData().isHasToilet());
                        System.out.println("是否有休息区: " + wholeVertexNode[i].getData().isHasRestArea());
                        System.out.println();
                        findSce.add(wholeVertexNode[i].getData());
                        exist = true;
                    }
                }
            }
            if (!exist) {
                for (int i = 0; i < wholeVertexNode.length; i++) {
                    if (wholeVertexNode[i] != null) {
                        findSce.add(wholeVertexNode[i].getData());
                    }
                }
            }
        }else {
            for (int i = 0; i < wholeVertexNode.length; i++) {
                if (wholeVertexNode[i] != null) {
                    findSce.add(wholeVertexNode[i].getData());
                }
            }
        }
        return exist;
    }

    /**
     * 按照热度进行排序并输出结果
     */
    public void sortByPop() {
        sortSce.clear();
        ScenicVertex[] scenicVertices = new ScenicVertex[amountOfVertex];
        int[] arr = new int[amountOfVertex];
        int count = 0;
        for (int i = 0; i < wholeVertexNode.length; i++) {
            if (wholeVertexNode[i] != null) {
                scenicVertices[count] = wholeVertexNode[i].getData();
                arr[count] = wholeVertexNode[i].getData().getPopularity();
                count++;
            }
        }
        QuickSort quickSort = new QuickSort();
        ScenicVertex[] newOne = quickSort.qsortByhigh(arr, scenicVertices, 0, arr.length - 1);
        for (ScenicVertex scenicVertex : newOne) {
            System.out.println("景区名称: " + scenicVertex.getName());
            System.out.println("景区描述: " + scenicVertex.getIntroduction());
            System.out.println("景区人气: " + scenicVertex.getPopularity());
            System.out.println("是否有卫生间: " + scenicVertex.isHasToilet());
            System.out.println("是否有休息区: " + scenicVertex.isHasRestArea());
            System.out.println();
            sortSce.add(scenicVertex);
        }
    }

    /**
     * 按照岔路数进行排序并输出结果
     */
    public void sortByWay() {
        sortSce.clear();
        ScenicVertex[] scenicVertices = new ScenicVertex[amountOfVertex];
        int[] arr = new int[amountOfVertex];
        int count = 0;
        for (int i = 0; i < wholeVertexNode.length; i++) {
            if (wholeVertexNode[i] != null) {
                scenicVertices[count] = wholeVertexNode[i].getData();
                arr[count] = wholeVertexNode[i].getData().getNumOfNode();
                count++;
            }
        }
        QuickSort quickSort = new QuickSort();
        ScenicVertex[] newOne = quickSort.qsortBylow(arr, scenicVertices, 0, arr.length - 1);
        for (ScenicVertex scenicVertex : newOne) {
            System.out.println("景区名称: " + scenicVertex.getName());
            System.out.println("景区描述: " + scenicVertex.getIntroduction());
            System.out.println("景区人气: " + scenicVertex.getPopularity());
            System.out.println("是否有卫生间: " + scenicVertex.isHasToilet());
            System.out.println("是否有休息区: " + scenicVertex.isHasRestArea());
            System.out.println();
            sortSce.add(scenicVertex);
        }
    }
}