package Algorithm.questions.string;

public class LongestPalindrome {
    char[] newArr;
    int[] pRadArr;

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
        int maxRadiusIndex = getMaxRadiusIndex(); // 半径最大的回文串对应pRadArr中的脚标
        Palindrome maxR = new Palindrome(maxRadiusIndex, pRadArr[maxRadiusIndex]);
        boolean isOdd = maxR.lB % 2 == 1;
        int capacity = isOdd ? maxR.r + 1 : maxR.r;
        StringBuffer sb = new StringBuffer(capacity);
        for (int i = isOdd ? maxR.lB : maxR.lB + 1; i <= maxR.rB; i = i + 2) {
            sb.append(newArr[i]);
        }
        return sb.toString();
    }

    private int getMaxRadiusIndex() {
        int maxIndex = 0;
        for (int i = 0; i < pRadArr.length; i++) {
            if (pRadArr[i] > pRadArr[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    /**
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        changeRawStringToOddEleCharArr(s);

        /**
         *  1. 需要跟踪的标记变量：i, maxR
         *  2. 为什么要设置maxR标记？如果采用i前面的i-1，就不确定i是在i-1的回文半径内部还是外部，
         *  就必须分情况来讨论。如果用maxR就可以限定i一定在maxR的回文半径之内，接着后面的等量关系以及
         *  每个index的回文半径的信息就可以使用了。用不着尝试从i-1获取信息的场景，因为i-1获取的信息
         *  并不是解决母问题的子解，所以从i-1去分情况，还不如maxR限定场景，因为后面对信息的利用都是一样的。
         *  3. 有时候需要考虑最简单，最一般的场景，看看可以找到的等量关系。然后在设置限定条件，比如maxR
         *  就可以在这种场景之内来考虑问题。最值情况往往可以作为一种很好的限定条件来使用。
         *
         */
        // 以i为中心的回文串
        Palindrome maxRB = new Palindrome(0, 0); // 所有回文串中能达到的最右边界的那个回文串

        for (Palindrome i = new Palindrome(0, 0);
             !i.isOverBundary() && !maxRB.isOverBundary();
             i.updateCenterAndRadius(i.c + 1, 0)) {
            if (i.c < maxRB.rB) { // i在maxR内部右半边
                int iMirrorCenter = maxRB.c * 2 - i.c; // 回文i关于maxR中心的对称回文中心
                Palindrome iMirror = new Palindrome(iMirrorCenter, pRadArr[iMirrorCenter]);
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
            while (i.canPalRadiusExpand()) {
                i.addRadius();
            }

            // 只有扩过半径才需要更新maxR #b#a#b#a#d
            if (i.r > 0) {
                pRadArr[i.c] = i.r;
                maxRB.updateCenterAndRadius(i.c, i.r);
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
            updateCenterAndRadius(center, radius);
        }

        public void updateCenterAndRadius(int center, int radius) {
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

        public boolean isOverBundary() {
            return !(lB >= 0 && rB <= newArr.length - 1);
        }

        public boolean canPalRadiusExpand() {
            return lB > 0 && rB < newArr.length - 1 && newArr[lB - 1] == newArr[rB + 1];
        }
    }
}