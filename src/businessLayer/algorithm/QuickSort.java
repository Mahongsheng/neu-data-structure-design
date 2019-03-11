package businessLayer.algorithm;

import businessLayer.graph.ScenicVertex;

/**
 * 这是一个快速排序算法
 * 我们输入一个数组和一个结点组
 * 之后我们进行并行的排序，将数组和结点组同时改变
 * @author 软英1702 马洪升 20175188
 */
public class QuickSort {
    /**
     * 初始化（从低到高排序）
     * @param arr 要排序的数组
     * @param scenicVertices 要排序的结点
     * @param low
     * @param high
     * @return
     */
    public ScenicVertex[] qsortBylow(int[] arr, ScenicVertex[] scenicVertices, int low, int high){
        if (low < high){
            int pivot=partition(arr, scenicVertices, low, high);//将数组分为两部分
            qsortBylow(arr, scenicVertices, low, pivot-1); //递归排序左子数组
            qsortBylow(arr, scenicVertices, pivot+1, high); //递归排序右子数组
        }
        return scenicVertices;
    }

    /**
     * @param arr 要排序的数组
     * @param scenicVertices 要排序的结点
     * @param low
     * @param high
     * @return
     */
    private static int partition(int[] arr, ScenicVertex[] scenicVertices, int low, int high){
        int pivot = arr[low];//枢轴记录
        ScenicVertex pivotV = scenicVertices[low];
        while (low<high){
            while (low<high && arr[high]>=pivot) --high;
            scenicVertices[low] = scenicVertices[high];
            arr[low]=arr[high];//交换比枢轴小的记录到左端
            while (low<high && arr[low]<=pivot) ++low;
            scenicVertices[high] = scenicVertices[low];
            arr[high] = arr[low];//交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        scenicVertices[low] = pivotV;
        //返回的是枢轴的位置
        return low;
    }

    /**
     * 从高到低排序
     * @param arr 要排序的数组
     * @param scenicVertices 要排序的结点
     * @param low
     * @param high
     * @return
     */
    public ScenicVertex[] qsortByhigh(int[] arr, ScenicVertex[] scenicVertices, int low, int high){
        if (low < high){
            int pivot=partition1(arr, scenicVertices, low, high);//将数组分为两部分
            qsortByhigh(arr, scenicVertices, low, pivot-1);//递归排序左子数组
            qsortByhigh(arr, scenicVertices, pivot+1, high);//递归排序右子数组
        }
        return scenicVertices;
    }

    /**
     * @param arr 要排序的数组
     * @param scenicVertices 要排序的结点
     * @param low
     * @param high
     * @return
     */
    private static int partition1(int[] arr, ScenicVertex[] scenicVertices, int low, int high){
        int pivot = arr[low];//枢轴记录
        ScenicVertex pivotV = scenicVertices[low];
        while (low<high){
            while (low<high && arr[high]<=pivot) --high;
            scenicVertices[low] = scenicVertices[high];
            arr[low]=arr[high];//交换比枢轴小的记录到左端
            while (low<high && arr[low]>=pivot) ++low;
            scenicVertices[high] = scenicVertices[low];
            arr[high] = arr[low];//交换比枢轴小的记录到右端
        }
        //扫描完成，枢轴到位
        arr[low] = pivot;
        scenicVertices[low] = pivotV;
        //返回的是枢轴的位置
        return low;
    }
}
