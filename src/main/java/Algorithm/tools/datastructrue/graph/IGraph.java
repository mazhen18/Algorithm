package Algorithm.tools.datastructrue.graph;

import Algorithm.tools.datastructrue.graph.edge.Edge;
import Algorithm.tools.datastructrue.graph.vertext.Vertex;

public interface IGraph {
    void printGraph();
    void depthFirstSearch(int startVertexId);
    void breadthFirstSearch(int startVertexId);

    int getVertexCount();
    int getEdgeCount();
    int getDegree(int vertexId);
    int getInDegree(int vertexId);
    int getOutDegree(int vertexId);
    int getDistance(int startVertexId, int endVertexId);


    boolean isVertexExsit(int vertexId);
    boolean addEdge(Edge e);
    boolean removeEdge(Edge e);
    boolean removeVertex(Vertex v);

}
