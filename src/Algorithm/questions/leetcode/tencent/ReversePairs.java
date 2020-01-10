package Algorithm.questions.leetcode.tencent;

public class ReversePairs {
    public int reversePairs(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        long[] numsL = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numsL[i] = nums[i];
        }
        return reversePairs(numsL, 0, nums.length - 1);
    }

    private int reversePairs(long[] nums, int s, int e) {
        if (e == s) {
            return 0;
        }
        int mid = (s + e) / 2;
        int leftCount = reversePairs(nums, s, mid);
        int rightCount = reversePairs(nums, mid + 1, e);
        int countBoth = getCount(mergeSort(s, mid, nums), mergeSort(mid + 1, e, nums));
        return leftCount + rightCount + countBoth;
    }


    // 求arr1中大于arr2中数两倍的个数
    private int getCount(long[] arrI, long[] arrJ) {
        int sI = 0, eI = arrI.length - 1, sJ = 0, eJ = arrJ.length - 1;
        if (arrI[eI] <= 2 * arrJ[sJ]) {
            return 0;
        }
        if (arrI[sI] > 2 * arrJ[eJ]) {
            return arrI.length * arrJ.length;
        }
        int count = 0;

        if (arrI[sI] <= 2 * arrJ[sJ]) {
            while (arrI[sI] <= 2 * arrJ[sJ]) {
                sI++;
            }
            if (arrI[eI] <= 2 * arrJ[eJ]) {
                while (arrI[eI] <= 2 * arrJ[eJ]) {
                    eJ--;
                }
            } else {
                while (arrI[eI] <= 2 * arrJ[eJ]) {
                    eI--;
                }
                count = (arrI.length - 1 - eI) * arrJ.length;
            }
        } else {
            while (arrI[sI] <= 2 * arrJ[sJ]) {
                sJ++;
            }
            if (arrI[eI] <= 2 * arrJ[eJ]) {
                while (arrI[eI] <= 2 * arrJ[eJ]) {
                    eJ--;
                }
                count = sJ * arrI.length;
            } else {
                while (arrI[eI] <= 2 * arrJ[eJ]) {
                    eI--;
                }
                ;
                count = (arrI.length - 1 - eI) * arrJ.length + sJ * arrI.length;
            }
        }

        for (int j = sJ; j <= eJ; j++) {
            for (int i = sI; i <= eI; i++) {
                if (arrI[i] > 2 * arrJ[j]) {
                    count += (arrI.length - i);
                    break;
                }
            }
        }
        return count;
    }

    private long[] mergeSort(int begin, int end, long[] data) {
        if (begin == end) {
            return new long[]{data[begin]};
        }
        int mid = (int) Math.floor((begin + end) / 2.0);
        long[] left = mergeSort(begin, mid, data);
        long[] right = mergeSort(mid + 1, end, data);
        return mergeArray(left, right);
    }

    private long[] mergeArray(long[] left, long[] right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int i = 0;
        long[] result = new long[left.length + right.length];
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
