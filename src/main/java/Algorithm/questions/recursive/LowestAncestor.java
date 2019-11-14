package Algorithm.questions.recursive;

import Algorithm.tools.datastructrue.tree.BinaryTreeNode;

public class LowestAncestor {

    public static void main(String[] agrv) {

        BinaryTreeNode head = new BinaryTreeNode(1);
        head.left = new BinaryTreeNode(2);
        head.left.left = new BinaryTreeNode(4);
        head.left.right = new BinaryTreeNode(5);

        head.right = new BinaryTreeNode(3);
        head.right.left = new BinaryTreeNode(6);
        head.right.right = new BinaryTreeNode(7);
        head.right.right.left = new BinaryTreeNode(8);

        System.out.println(getLowestAncestor(head, head.left.left, head.right.right).data);
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

}
