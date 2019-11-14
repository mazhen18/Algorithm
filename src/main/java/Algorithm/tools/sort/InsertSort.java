package Algorithm.tools.sort;

public class InsertSort{

    public void sort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int indexToInsert = i;
            int indexToCompare = i - 1;
            if (data[indexToCompare] > data[indexToInsert]) {
                // 有关联的变量最好取一个变量，其他变量都用这个变量表示，不然每个相关变量都要维护更新
                int valueToInsert = data[indexToInsert];
                while (indexToCompare >= 0 && (data[indexToCompare] > valueToInsert)) {
                    /**
                     * data[indexToInsert--] = data[indexToInsert - 1];
                     * 1. indexToInsert = 2
                     * 2. temp = indexToInsert, temp = 2
                     * 3. indexToInsert = indexToInsert - 1, indexToInsert = 1
                     * 4. data[indexToInsert - 1] = data[1 - 1] = data[0]
                     * 5. data[temp] = data[0]
                     * indexToInsert-- 只在本次计算局部备份temp一次，
                     * 后续的含indexToInsert变量的计算式不再享有temp的值。（不在其他区域共享）
                     */
                    data[indexToInsert--] = data[indexToCompare--];
                }
                data[indexToInsert] = valueToInsert;
            }
        }
    }
}
