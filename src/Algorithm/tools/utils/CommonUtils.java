package Algorithm.tools.utils;

public class CommonUtils {

    public static void swap(int i, int j, int[] data) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static String milTimeSpan(long begin) {
        long end = System.nanoTime();
        return (end - begin)/1000000 + " ms";
    }

    public static String nanoTimeSpan(long begin) {
        long end = System.nanoTime();
        return (end - begin) + " ns";
    }

}
