package Algorithm.tools.datastructrue.graph.vertext;

public class FrontVertex extends Vertex{

    public int level; // 单源最短路径点到原点距离

    public int frontVertextId;

    public FrontVertex(int id, int level, int frontVertextId){
        super(id);
        this.level = level;
        this.frontVertextId = frontVertextId;
    }
}
