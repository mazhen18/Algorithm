package Algorithm.tools.datastructrue.graph.edge;

/**
 * 外部输入的边结构，不论LinkGraph还是ArrayGraph都可以使用，是抽象层面的。
 * 如果点有描述信息请另外通过映射表将点的id值与该点的额外信息建立联系，不要混到图的运算中。
 */
public class Edge {

    public int id1; // 边的第一个点id值
    public int id2; // 边的第二个点id值
    public int weight = 0;

    public Edge(int id1, int id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    public Edge(int id1, int id2, int weight) {
        this.id1 = id1;
        this.id2 = id2;
        this.weight = weight;
    }

}
