package Algorithm.questions.string;

public class LongestCommonChildString {

    int curMaxLen = 0;
    Point maxLenEndPoint = new Point();
    SearchLine sl = new SearchLine();
    char[] cShort, cLong;

    public String getLongestCommonChildString(String str1, String str2) {
        if (str1.length() == 0 || str2.length() == 0) {
            return "";
        }
        if (str1.length() < str2.length()) {
            cShort = str1.toCharArray();
            cLong = str2.toCharArray();
        } else {
            cLong = str1.toCharArray();
            cShort = str2.toCharArray();
        }

        sl.setBundaryAndMiddle(cLong.length - 1, cShort.length * (-1) + 1);

        for (int lb = sl.bMid; lb >= sl.yMin; lb--) {
            sl.update(lb);
            if (sl.pointCountLimit < curMaxLen) {
                break;
            }
            sl.findLongestChildStr(lb);
            if (lb != sl.rb) {
                sl.findLongestChildStr(sl.rb);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int j = maxLenEndPoint.x - curMaxLen + 1; j <= maxLenEndPoint.x; j++) {
            sb.append(cLong[j]);
        }
        return sb.toString();
    }


    /**
     * x+y=b
     */
    class SearchLine {
        int xMax, yMin;
        Point startPoint = new Point();
        Point endPoint = new Point();
        int pointCountLimit;
        int bMid;
        int rb; // 关于bMid线对称的线的b值

        public void setBundaryAndMiddle(int xMax, int yMin) {
            this.xMax = xMax;
            this.yMin = yMin;
            bMid = (xMax + yMin) / 2;
        }

        private void update(int b) {
            if (b <= 0) {
                pointCountLimit = b - yMin + 1;
                startPoint.update(0, b);
                endPoint.update(b - yMin, yMin);
            } else if (b > 0 && b <= xMax + yMin) {
                pointCountLimit = Math.abs(yMin) + 1;
                startPoint.update(b, 0);
                endPoint.update(b - yMin, yMin);
            } else {
                pointCountLimit = Math.abs(b - xMax) + 1;
                startPoint.update(b, 0);
                endPoint.update(xMax, b - xMax);
            }
            rb = xMax + yMin - b;
        }

        private void findLongestChildStr(int b) {
            update(b);
            Point i = new Point();
            int count = 0;
            for (i.update(startPoint.x, startPoint.y);
                 !isOverEndPoint(i) && isImpossibleToReachCurMaxLen(count, i);
                 i.forward()) {
                if (cShort[i.y * (-1)] == cLong[i.x]) {
                    count++;
                    if (i.equals(endPoint)) {
                        maxLenEndPoint.update(i.x, i.y);
                        curMaxLen = count;
                    }
                } else {
                    if (count > curMaxLen) {
                        i.backward();
                        maxLenEndPoint.update(i.x, i.y);
                        curMaxLen = count;
                    }
                    count = 0;
                }
            }

        }

        private boolean isImpossibleToReachCurMaxLen(int curCount, Point curPoint) {
            return (curCount + endPoint.x - curPoint.x + 1) >= curMaxLen;
        }

        private boolean isOverEndPoint(Point curPoint) {
            if (curPoint.x == endPoint.x + 1 && curPoint.y == endPoint.y - 1) {
                return true;
            }
            return false;
        }
    }

    class Point {
        int x;
        int y;

        public void update(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void forward() {
            update(x + 1, y - 1);
        }

        public void backward() {
            update(x - 1, y + 1);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Point
                    && ((Point) obj).x == this.x
                    && ((Point) obj).y == this.y;
        }
    }
}
