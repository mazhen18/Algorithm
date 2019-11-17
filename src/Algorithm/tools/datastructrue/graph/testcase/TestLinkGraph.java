package Algorithm.tools.datastructrue.graph.testcase;

import Algorithm.tools.datastructrue.graph.IGraph;
import Algorithm.tools.datastructrue.graph.UndirectLinkGraph;
import Algorithm.tools.datastructrue.graph.edge.Edge;

import java.util.ArrayList;
import java.util.List;

public class TestLinkGraph {

    public static void main(String[] argv) {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 2));
        edges.add(new Edge(2, 3));
        edges.add(new Edge(3, 5));
        edges.add(new Edge(3, 6));
        edges.add(new Edge(3, 9));
        edges.add(new Edge(4, 8));
        edges.add(new Edge(4, 9));
        edges.add(new Edge(5, 6));
        edges.add(new Edge(5, 7));
        edges.add(new Edge(7, 8));
        edges.add(new Edge(8, 9));
        IGraph linkGraph = new UndirectLinkGraph();
//        IGraph linkGraph = new DirectLinkGraph();
        linkGraph.printGraph();
        for (Edge edge : edges) {
            linkGraph.addEdge(edge);
        }
        System.out.println("edgesCount:" + linkGraph.getEdgeCount());
        linkGraph.printGraph();
//        System.out.println("4 degree:" + linkGraph.getDegree(4));
//        System.out.println("5 indegree:" + linkGraph.getInDegree(5));
//        System.out.println("2 outdegree:" + linkGraph.getOutDegree(2));
//
//        linkGraph.removeVertex(new WeightVertex(5));
//        linkGraph.printGraph();
//        System.out.println("edgesCount:" + linkGraph.getEdgeCount());
//        linkGraph.removeEdge(new Edge(1, 4));
//        System.out.println("edgesCount:" + linkGraph.getEdgeCount());
//        linkGraph.printGraph();
//        linkGraph.removeEdge(new Edge(4, 5));
//        System.out.println("edgesCount:" + linkGraph.getEdgeCount());
//        linkGraph.printGraph();
        linkGraph.breadthFirstSearch(2);
//        linkGraph.depthFirstSearch(2);
        System.out.println("findDistance 2->8: " + linkGraph.getDistance(2, 8));
    }

}
