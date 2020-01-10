package Algorithm.questions.leetcode.tencent;

public class RegularMatch {

    public boolean isMatch(String s, String p) {
        char[] sa = s.toCharArray();
        char[] pa = p.toCharArray();
        return isMatch3(sa, 0, pa, 0);
    }

    private boolean isMatch3(char[] s, int si, char[] p, int pi) {
        if (pi == p.length) {
            return si == s.length;
        }

        if (si == s.length || (p[pi] != '.' && s[si] != p[pi])) {
            return pi + 1 < p.length && p[pi + 1] == '*' ? isMatch3(s, si, p, pi + 2) : false;
        }

        if (pi + 1 < p.length && p[pi + 1] == '*') {
            while (si < s.length && (p[pi] == '.' || s[si] == s[si])) {
                if (isMatch3(s, si, p, pi + 2)) {
                    return true;
                }
                ++si;
            }
            return isMatch3(s, si, p, pi + 2);
        }
        return isMatch3(s, si + 1, p, pi + 1);
    }
}
