package Algorithm.tools.datastructrue.graph.vertext;

/**
 * 由某节点出发该点结构表示的终点，含权重
 */
public class WeightVertex extends Vertex{

    public int weight = 0; // 起始点到该终点边的权重值

    public WeightVertex(int id) {
        super(id);
    }

    public WeightVertex(int id, int weight) {
        super(id);
        this.weight = weight;
    }
}
