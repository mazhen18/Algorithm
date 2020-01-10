package Algorithm.test;

import Algorithm.questions.leetcode.tencent.RegularMatch;

class Solution {
    public static void main(String[] args) {
        boolean ret = new RegularMatch().isMatch("","c*c*");
        System.out.println(ret);
    }
}