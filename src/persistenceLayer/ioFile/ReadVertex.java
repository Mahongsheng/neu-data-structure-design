package persistenceLayer.ioFile;

import businessLayer.graph.ScenicVertex;
import persistenceLayer.connectDB.ScenicSrvc;

import java.io.File;
/**
 * 从文件中读取所有景点
 * @author 软英1702 马洪升 20175188
 */
public class ReadVertex {
    public ScenicVertex[] readVertexFile() {
        File newFile = new File("vertex.txt");
        String[][] vertexList = IOFile.readFile(newFile);
        int lineCount = vertexList.length;
        ScenicVertex[] wholeVertex = new ScenicVertex[lineCount];
        for (int i = 0; i < lineCount; i++) {
            int pop = Integer.parseInt(vertexList[i][2]);
            boolean hasRestArea = Boolean.parseBoolean(vertexList[i][3]);
            boolean hasToilet = Boolean.parseBoolean(vertexList[i][4]);
            int x = Integer.parseInt(vertexList[i][5]);
            int y = Integer.parseInt(vertexList[i][6]);
            ScenicVertex scenicVertex = new ScenicVertex(vertexList[i][0], vertexList[i][1], pop,
                    hasRestArea, hasToilet, x, y);
            wholeVertex[i] = scenicVertex;
        }
        return wholeVertex;
    }


    public static void main(String[] args){
        ReadVertex readVertex = new ReadVertex();
        ScenicVertex[] scenicVertices = readVertex.readVertexFile();
        ScenicSrvc scenicSrvc = new ScenicSrvc();
        for (int i = 0; i < scenicVertices.length; i++){
            scenicSrvc.insert(scenicVertices[i].getName(), scenicVertices[i].getIntroduction(),scenicVertices[i].getPopularity()
            ,scenicVertices[i].isHasRestArea(),scenicVertices[i].isHasToilet(),scenicVertices[i].getX(),scenicVertices[i].getY());
        }
    }
}
