package Algorithm.tools.utils;

import java.util.HashSet;
import java.util.Set;

public class RandomUtils {

    /**
     * 随机指定范围内N个不重复的数
     * 利用HashSet的特征，只能存放不同的值
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    private static Set<Integer> randomSet(int min, int max, int n) {
        if (min > max || n > (max - min + 1) || n < 1) {
            throw new RuntimeException("input illegal");
        }

        Set<Integer> set = new HashSet<>();
        while (set.size() != n) {
            int num = (int) (Math.random() * (max - min)) + min;
            set.add(num);// 将不同的数存入HashSet中
        }
        return set;
    }

    public static int[] getRandomDiffIntArray(int min, int max, int n) {
        Set<Integer> set = randomSet(min, max, n);
        int[] data = new int[set.size()];
        int i = 0;
        for (int ele : set) {
            data[i++] = ele;
        }
        return data;
    }

    public static int[] getRandomIntArray(int min, int max, int n) {
        if (min > max || n > (max - min + 1) || n < 1) {
            throw new RuntimeException("input illegal");
        }
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = (int) (Math.random() * (max - min)) + min;
        }
        return data;
    }
}
