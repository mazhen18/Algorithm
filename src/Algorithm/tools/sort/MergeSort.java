package Algorithm.tools.sort;

public class MergeSort{

    /**
     * 分治：分解，解决，合并子问题。利用分解子问题的思想将输入规模减小，
     * 并利用递归的手段解决子问题，最后再在子问题已经解决的基础上合并子问题
     */
    public int[] sort(int[] data) {
        if (data == null || data.length < 1) {
            throw new RuntimeException("input illegal");
        }
        return mergeSort(0, data.length - 1, data);
    }

    private int[] mergeSort(int begin, int end, int[] data) {
        if (begin == end) {
            return new int[]{data[begin]};
        }
        int mid = (int) Math.floor((begin + end) / 2.0);
        int[] left = mergeSort(begin, mid, data);
        int[] right = mergeSort(mid + 1, end, data);
        return mergeArray(left, right);
    }

    private int[] mergeArray(int[] left, int[] right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int i = 0;
        int[] result = new int[left.length + right.length];
        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] < right[rightIndex]) {
                result[i++] = left[leftIndex++];
            } else {
                result[i++] = right[rightIndex++];
            }
        }
        while (leftIndex < left.length) {
            result[i++] = left[leftIndex++];
        }
        while (rightIndex < right.length) {
            result[i++] = right[rightIndex++];
        }
        return result;
    }
}
