package Algorithm.tools.datastructrue.tree;

public class BinaryTreeNode {

    public int data;

    public BinaryTreeNode left;

    public BinaryTreeNode right;

    public BinaryTreeNode(int data){

        this.data = data;
    }

    public void preOrderSearch(BinaryTreeNode head){
        if(head == null){
            return;
        }
        System.out.print(head.data + " ");
        preOrderSearch(head.left);
        preOrderSearch(head.right);
    }

}
