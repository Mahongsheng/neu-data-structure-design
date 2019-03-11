package persistenceLayer.ioFile;

import businessLayer.graph.Edge;
import persistenceLayer.connectDB.EdgeSrvc;

import java.io.File;

/**
 * 从文件中读取所有边
 * @author 软英1702 马洪升 20175188
 */
public class ReadEdge {
    public Edge[] readEdgeFile(){
        File newFile = new File("edge.txt");
        String[][] edgeList = IOFile.readFile(newFile);
        int lineCount = edgeList.length;
        Edge[] wholeEdge = new Edge[lineCount];
        for (int i = 0; i < lineCount; i++){
            Edge edge = new Edge(edgeList[i][0], edgeList[i][1], Integer.parseInt(edgeList[i][2]),
                    Float.parseFloat(edgeList[i][3]));
            wholeEdge[i] = edge;
        }
        return wholeEdge;
    }

    public static void main(String[] args){
        ReadEdge readEdge = new ReadEdge();
        Edge[] edges = readEdge.readEdgeFile();
        for (int i = 0; i < edges.length; i++){
            EdgeSrvc edgeSrvc = new EdgeSrvc();
            edgeSrvc.insert(edges[i].getOneScenic(),edges[i].getOtherScenic(),edges[i].getWeight(),edges[i].getTimeCost());
        }
    }
}
