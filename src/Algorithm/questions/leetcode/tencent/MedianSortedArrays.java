package Algorithm.questions.leetcode.tencent;

public class MedianSortedArrays {

    int i = 0;
    int j = 0;
    int cur = 0;
    int[] which = null;

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int midCount = (len1 + len2 + 1) / 2;
        boolean oddFlag = (len1 + len2) % 2 != 0;
        int count = 0;
        do {
            if (i < len1 && j < len2) {
                if (nums1[i] <= nums2[j]) {
                    addI(nums1);
                } else {
                    addJ(nums2);
                }
            } else if (i == len1) {
                addJ(nums2);
            } else if (j == len2) {
                addI(nums1);
            }
        } while (++count != midCount);

        if (oddFlag) {
            return which[cur - 1];
        } else {
            int fir = which[cur - 1];
            int sec;
            if (i == len1) {
                sec = nums2[j];
            } else if (j == len2) {
                sec = nums1[i];
            } else {
                sec = Math.min(nums1[i], nums2[j]);
            }
            return (fir + sec) * 1.0 / 2.0;
        }
    }

    private void addJ(int[] num) {
        j++;
        cur = j;
        which = num;
    }

    private void addI(int[] num) {
        i++;
        cur = i;
        which = num;
    }
}
