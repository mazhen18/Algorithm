package Algorithm.questions.recursive;

import Algorithm.tools.datastructrue.tree.BinaryTreeNode;

import java.util.HashMap;
import java.util.Map;

public class LowestAncestor {

    public static void main(String[] agrv) {

        int[] data = new int[]{9, 1, 4, 8, 6, 2, 5, -1, -1, 3, 7};
        BinaryTreeNode head = convertArrayToTree(data);
//        BinaryTreeNode head = new BinaryTreeNode(1);
//        head.left = new BinaryTreeNode(2);
//        head.left.left = new BinaryTreeNode(4);
//        head.left.right = new BinaryTreeNode(5);
//
//        head.right = new BinaryTreeNode(3);
//        head.right.left = new BinaryTreeNode(6);
//        head.right.right = new BinaryTreeNode(7);
//        head.right.right.left = new BinaryTreeNode(8);

        System.out.println(getLowestAncestor2(head, head.left.left, head.right.right).data);
    }

    /**
     * 1. head子节点包含o1,o2返回其最近公共祖先
     * 2. head只包含o1,o2其中一个，返回其中一个
     * 3. head不包含o1,o2，返回null
     */

    public static BinaryTreeNode getLowestAncestor(BinaryTreeNode head, BinaryTreeNode o1, BinaryTreeNode o2) {

        if (head == null || head == o1 || head == o2) {
            return head;
        }


        BinaryTreeNode left = getLowestAncestor(head.left, o1, o2);
        BinaryTreeNode right = getLowestAncestor(head.right, o1, o2);

        if (left != null && right != null) {
            return head;
        } else {
            return left != null ? left : right;
        }

    }

    /**
     * 获取o1或者o2的一个祖先
     * @param head
     * @param o1
     * @param o2
     * @return
     */
    public static BinaryTreeNode getLowestAncestor2(BinaryTreeNode head, BinaryTreeNode o1, BinaryTreeNode o2) {
        /**
         * 1.终止条件：遇到null怎么办，遇到o1,o2怎么办
         * 2.递归函数意义探讨：
         * （1）返回最近的公共祖先，那么当根节点左右都返回空的时候说明o1,o2恰好在根节点的两边；但是
         * 如果是左右子递归都返回空的时候，推断是根节点为最近公共祖先，那么当在子问题中，若子问题
         * 根节点的左右都没有o1,o2，也会出现子问题左右子递归都返回空，这显然不能推断出该子问题的根节点
         * 就是最近公共祖先。结论：该递归函数的意义就定义的不对，及该函数不可能直接返回o1,o2的最近公共祖先。
         * （2）直接意义的递归函数不能解决问题的时候，就需要倒推能够解决问题的递归函数的意义。
         * （3）递归的一般过程：
         *      a. 递归的终止条件，终止操作
         *      b. 利用子问题解题的步骤
         *      c. 子问题结果处理
         * （4）本题思路：
         *      a. 先通过常规思路，站在母问题的层次，如果根节点左右子树分别包含一个查询节点，则该根节点就是问题的解，
         * 返回回去；站在母问题的子问题划分能不能也用在子问题的求解。此时：推断的递归函数的第一个功能，能够查询该根节
         * 点下包不包含被查询的节点。（包不包含可以用是不是空表示，而且不是空的时候还可以携带更多信息）
         *      b. 当其中一个子问题返回空的时候，另外一个不为空，返回不为空的那个，而这个不为空的节点可能是被查询节点，
         * 也可能已经是问题的解。而这一结果取决于：终止操作返回的值，子问题结果处理返回的值。期望另外一个返回不为空的的
         * 结果就是母问题的解，也就是最近公共祖先。终止条件之一是根节点等于其中一个被查询的点，这个时候能够回传的信息量
         * 也就是根节点本身，也就是被查询到的点
         */

        if (head == null) {
            return null;
        }
        if(head.left == o1||head.right==o2
        ||head.right == o1||head.right==o2){
            return head;
        }
        BinaryTreeNode leftRet = getLowestAncestor2(head.left, o1, o2);
        BinaryTreeNode rightRet = getLowestAncestor2(head.right, o1, o2);

        if(leftRet == null&&rightRet==null){
            return null;
        }else {
            return head;
        }

    }


    /**
     * @param data 用数组储存记录，只有最后一层不完整的二叉树，顺序：从顶点开始，广度优先的方式
     * @return 根节点
     */
    public static BinaryTreeNode convertArrayToTree(int[] data) {
        BinaryTreeNode root = new BinaryTreeNode(data[0]);
        Map<Integer, BinaryTreeNode> mapNode = new HashMap<>();
        mapNode.put(1, root);
        for (int i = 1; i < data.length; i++) {
            if (data[i] != -1) {
                int nodeNum = i + 1;
                BinaryTreeNode node = new BinaryTreeNode(data[i]);
                int parentNodeNum = nodeNum / 2;
                if (nodeNum % 2 == 0) {
                    mapNode.get(parentNodeNum).left = node;
                } else {
                    mapNode.get(parentNodeNum).right = node;
                }
                mapNode.put(nodeNum, node);
            }
        }
        return root;
    }
}
