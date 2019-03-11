package businessLayer.algorithm;

/**
 * 这是一个弗洛伊德算法
 * @author 软英1702 马洪升 20175188
 */
public class Floyd {
    private static final int INF = 32767;
    /**
     * 算法核心
     * path -- 路径。path[i][j]=k表示，"顶点i"到"顶点j"的最短路径会经过顶点k。
     * dist -- 长度数组。即，dist[i][j]=sum表示，"顶点i"到"顶点j"的最短路径的长度是sum。
     * @param mVexs 所有结点的名称
     * @param mMatrix 邻接矩阵
     * @return
     */
    public int[][] floyd(String[] mVexs, int[][]mMatrix) {
        int[][] path = new int[mVexs.length][mVexs.length];
        int[][] dist = new int[mVexs.length][mVexs.length];
        // 初始化
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++) {
                dist[i][j] = mMatrix[i][j];    // "顶点i"到"顶点j"的路径长度为"i到j的权值"。
                path[i][j] = j;                // "顶点i"到"顶点j"的最短路径是经过顶点j。
            }
        }

        // 计算最短路径
        for (int k = 0; k < mVexs.length; k++) {
            for (int i = 0; i < mVexs.length; i++) {
                for (int j = 0; j < mVexs.length; j++) {
                    // 如果经过下标为k顶点路径比原两点间路径更短，则更新dist[i][j]和path[i][j]
                    int tmp = (dist[i][k]==INF || dist[k][j]==INF) ? INF : (dist[i][k] + dist[k][j]);
                    if (dist[i][j] > tmp) {
                        // "i到j最短路径"对应的值设，为更小的一个(即经过k)
                        dist[i][j] = tmp;
                        // "i到j最短路径"对应的路径，经过k
                        path[i][j] = path[i][k];
                    }
                }
            }
        }

        //在命令行界面中调用时会输出一个可达矩阵
        System.out.printf("floyd: \n");
        for (int i = 0; i < mVexs.length; i++) {
            System.out.print(mVexs[i]+"  ");
        }
        System.out.println();
        for (int i = 0; i < mVexs.length; i++) {
            for (int j = 0; j < mVexs.length; j++)
                System.out.printf("%2d  ", dist[i][j]);
            System.out.printf("\n");
        }
        return dist;//返还可达矩阵
    }
}
