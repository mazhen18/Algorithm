package Algorithm.tools.datastructrue.graph.vertext;

public class Vertex {
    public int id; // 点的唯一标的

    public Vertex(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (((Vertex) obj).id == this.id) {
            return true;
        } else {
            return false;
        }
    }
}
