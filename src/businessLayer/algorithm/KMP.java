package businessLayer.algorithm;

/**
 * 这是一个KMP算法
 * 我们使用这个算法来快速找到特定字符在整个字符串中的位置并返回该位置
 * @author 软英1702 马洪升 20175188
 */
public class KMP {
    /**
     * @param pattern
     * @param next
     */
    void getNext(String pattern, int next[]) {
        int j = 0;
        int k = -1;
        int len = pattern.length();
        next[0] = -1;
        while (j < len - 1) {
            if (k == -1 || pattern.charAt(k) == pattern.charAt(j)) {
                j++;
                k++;
                next[j] = k;
            } else {
                // 比较到第K个字符，说明p[0——k-1]字符串和p[j-k——j-1]字符串相等，而next[k]表示
                // p[0——k-1]的前缀和后缀的最长共有长度，所接下来可以直接比较p[next[k]]和p[j]
                k = next[k];
            }
        }
    }

    /**
     * @param s
     * @param pattern
     * @return
     */
    public int kmp(String s, String pattern) {
        int i = 0;
        int j = 0;
        int slen = s.length();
        int plen = pattern.length();

        int[] next = new int[plen];
        getNext(pattern, next);
        while (i < slen && j < plen) {
            if (s.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            } else {
                if (next[j] == -1) {
                    i++;
                    j = 0;
                } else {
                    j = next[j];
                }
            }
            if (j == plen) {
                return i - j;
            }
        }
        return -1;
    }

}
