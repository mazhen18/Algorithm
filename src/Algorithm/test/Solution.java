package Algorithm.test;

class Solution {
    int maxRadiusIndex = 0; // 半径最大的回文串对应pRadArr中的脚标
    int maxRadius = 0; // 最长回文串半径
    char[] newArr;
    int[] pRadArr;
    Palindrome i = new Palindrome(0, 0); // 以i为中心的回文串
    Palindrome maxRB = new Palindrome(0, 0); // 所有回文串中能达到的最右边界的那个回文串
    Palindrome iMirror = new Palindrome(0, 0);// i以maxRB为中心对称的回文

    // 将原始字符串通过插入字符改变为奇数个的字符数组
    private void changeRawStringToOddEleCharArr(String s) {
        // 初始化回文半径数组，newArr以每个元素为中心的回文串的半径
        pRadArr = new int[s.length() * 2 + 1];
        char symbel = '#';
        char[] chars = s.toCharArray();
        newArr = new char[s.length() * 2 + 1];
        newArr[0] = symbel;
        for (int i = 0; i < chars.length; i++) {
            newArr[i * 2 + 1] = chars[i];
            newArr[i * 2 + 2] = symbel;
        }
    }

    private String getMaxPalindromeFromNewArr() {
        Palindrome maxR = new Palindrome(maxRadiusIndex, pRadArr[maxRadiusIndex]);
        boolean isOdd = maxR.lB % 2 == 1;
        int capacity = isOdd ? maxR.r + 1 : maxR.r;
        StringBuffer sb = new StringBuffer(capacity);
        for (int i = isOdd ? maxR.lB : maxR.lB + 1; i <= maxR.rB; i = i + 2) {
            sb.append(newArr[i]);
        }
        return sb.toString();
    }

    public String longestPalindrome(String s) {
        changeRawStringToOddEleCharArr(s);
        // maxRB.rB == newArr.length - 1就应该结束了
        for (; maxRB.rB < newArr.length - 1; i.update(i.c + 1, 0)) {
            if (i.c < maxRB.rB) { // i在maxR内部右半边
                int iMirrorCenter = maxRB.c * 2 - i.c; // 回文i关于maxR中心的对称回文中心
                iMirror.update(iMirrorCenter, pRadArr[iMirrorCenter]);
                if (iMirror.lB > maxRB.lB) {
                    pRadArr[i.c] = iMirror.r;
                    continue;
                } else if (iMirror.lB < maxRB.lB) {
                    pRadArr[i.c] = iMirror.c - maxRB.lB;
                    continue;
                } else {
                    // 将i更新为半径为iMirror.radius+1的回文开始扩充
                    i.updateRadius(iMirror.r);
                }
            }
            // 要么初始化超前，要么检验条件超前
            while (i.lB >= 1 && i.rB <= newArr.length - 2 && newArr[i.lB - 1] == newArr[i.rB + 1]) {
                i.addRadius();
            }
            // 只有扩过半径才需要更新maxR #b#a#b#a#d
            if (i.r > 0) {
                pRadArr[i.c] = i.r;
                maxRB.update(i.c, i.r);
                if (i.r > maxRadius) {
                    maxRadius = i.r;
                    maxRadiusIndex = i.c;
                }
            }
        }
        return getMaxPalindromeFromNewArr();
    }

    class Palindrome {
        // 不要采用get,set函数，耗时却不一定减小空间
        int c; //center
        int rB; // rightBundary
        int lB; // leftBundary
        int r; // radius

        public Palindrome(int center, int radius) {
            update(center, radius);
        }

        public void update(int center, int radius) {
            c = center;
            rB = c + radius;
            lB = c - radius;
            this.r = radius;
        }

        public void updateRadius(int radius) {
            rB = c + radius;
            lB = c - radius;
            this.r = radius;
        }

        public void addRadius() {
            rB++;
            lB--;
            r++;
        }
    }
}