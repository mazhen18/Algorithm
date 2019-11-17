package Algorithm.tools.datastructrue.Heap;

import Algorithm.tools.utils.CommonUtils;

/**
 * 二叉堆：元素的排列顺序，0-根节点，1-左子节点，2-右子节点，类似完全二叉树的排列
 */
public class ArrayHeap {
    /**
     * 必须知道的知识点：
     * 1. 完全二叉树性质：
     * 叶节点个数：Math.ceil(n/2),
     * leftNode = 2*i + 1, rightNode = 2*(i+1), parentNode = Math.floor((i - 1)/2)
     * 2. 堆维护函数前提是：i元素的左右子树均已经是最大堆，从最底层开始逐层往上满足最大堆性质
     */
    private int heapSize = 0;
    private int[] data;

    public ArrayHeap(int[] data) {
        if (data == null || data.length < 1) {
            throw new RuntimeException("input illegal null");
        }
        this.data = data;
        heapSize = data.length;
        buildHeap();
    }

    public void sort() {
        while (heapSize > 1) {
            CommonUtils.swap(0, heapSize - 1, data);
            heapSize--;
            updateHeap(0);
        }
    }

    private void buildHeap() {
        for (int i = getMinLeafIndex() - 1; i >= 0; i--) {
            updateHeap(i);
        }
    }

    /**
     * 从index开始逐层向下将index与其左右子树的最大值调换位置，直到自己最大
     */
    private void updateHeap(int index) {
        if (index >= getMinLeafIndex()) {
            return;
        }
        int leftChild = getLeftChild(index);
        int rightChild = getRightChild(index);
        int largestIndex = index;
        if (leftChild < heapSize && data[index] < data[leftChild]) {
            largestIndex = leftChild;
        }
        if (rightChild < heapSize && data[largestIndex] < data[rightChild]) {
            largestIndex = rightChild;
        }
        if (largestIndex != index) {
            CommonUtils.swap(index, largestIndex, data);
            updateHeap(largestIndex);
        }
    }


    private static int getLeftChild(int i) {
        return 2 * i + 1;
    }

    private static int getRightChild(int i) {
        return 2 * (i + 1);
    }

    private static int getParent(int i) {
        return (int) Math.floor((i - 1) / 2);
    }

    private int getMinLeafIndex() {
        return (int) Math.ceil(heapSize / 2);
    }
}
