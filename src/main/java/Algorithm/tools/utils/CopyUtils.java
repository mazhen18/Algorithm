package Algorithm.tools.utils;

public class CopyUtils {

    public static int[] copyIntArray(int[] data){
        if(data == null || data.length == 0){
            throw new RuntimeException("input illegal null");
        }
        int[] copyData = new int[data.length];
        int i = 0;
        for (int ele : data) {
            copyData[i++] = ele;
        }
        return copyData;
    }
}
