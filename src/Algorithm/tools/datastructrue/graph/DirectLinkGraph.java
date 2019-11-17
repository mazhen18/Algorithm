package Algorithm.tools.datastructrue.graph;

import Algorithm.tools.datastructrue.graph.edge.Edge;
import Algorithm.tools.datastructrue.graph.vertext.WeightVertex;
import Algorithm.tools.datastructrue.graph.vertext.Vertex;

import java.util.*;
import java.util.logging.Logger;

public class DirectLinkGraph implements IGraph {

    /**
     * 1. Map<Integer, HashSet<WeightVertex>>数据结构即可以维护
     * 2. map.ketSet()即图中所有点
     */
    private final Logger Log = Logger.getLogger(UndirectLinkGraph.class.getName());

    private int edgeCount = 0;

    private final HashMap<Integer, HashSet<WeightVertex>> inLinkGraph = new HashMap<>();

    private final HashMap<Integer, HashSet<WeightVertex>> outLinkGraph = new HashMap<>();

    public DirectLinkGraph() {
        super();
    }

    public DirectLinkGraph(List<Edge> edges) {
        if (edges == null || edges.size() < 1) {
            throw new RuntimeException("edges input illegal null");
        }
        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void printGraph() {
        printGraph("-(%d)->%d", outLinkGraph);
        printGraph("<-(%d)-%d", inLinkGraph);
    }

    private void printGraph(String formatStr, HashMap<Integer, HashSet<WeightVertex>> linkGraph) {
        for (int key : linkGraph.keySet()) {
            String discription = key + "";
            for (WeightVertex vertex : linkGraph.get(key)) {
                discription += String.format(formatStr, vertex.weight, vertex.id);
            }
            System.out.println(discription);
        }
        System.out.println();
    }

    @Override
    public void depthFirstSearch(int startVertexId) {
        Set<Integer> hasVisitedIdSet = new HashSet<>();
        Stack<Integer> stack = new Stack<>();
        stack.push(startVertexId);
        System.out.print(startVertexId + " ");
        hasVisitedIdSet.add(startVertexId);
        while (!stack.empty()) {
            int currentId = stack.peek();
            boolean hasUnVistitedId = false;
            for (Vertex v : outLinkGraph.get(currentId)) {
                if (!hasVisitedIdSet.contains(v.id)) {
                    stack.push(v.id);
                    System.out.print(v.id + " ");
                    hasVisitedIdSet.add(v.id);
                    hasUnVistitedId = true;
                    break;
                }
            }
            if(!hasUnVistitedId){
                stack.pop();
            }
        }
        System.out.println();
    }

    @Override
    public void breadthFirstSearch(int startVertexId) {

    }

    @Override
    public int getVertexCount() {
        Set<Integer> set = new HashSet<>();
        for (int key : outLinkGraph.keySet()) {
            set.add(key);
        }
        for (int key : inLinkGraph.keySet()) {
            set.add(key);
        }
        return set.size();
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public int getDegree(int vertexId) {
        return getInDegree(vertexId) + getOutDegree(vertexId);
    }

    @Override
    public int getInDegree(int vertexId) {
        if (!inLinkGraph.containsKey(vertexId)) {
            return 0;
        } else {
            return inLinkGraph.get(vertexId).size();
        }
    }

    @Override
    public int getOutDegree(int vertexId) {
        if (!outLinkGraph.containsKey(vertexId)) {
            return 0;
        } else {
            return outLinkGraph.get(vertexId).size();
        }
    }

    @Override
    public boolean isVertexExsit(int vertexId) {
        if (inLinkGraph.containsKey(vertexId)
                || outLinkGraph.containsKey(vertexId)) {
            return true;
        } else {
            Log.severe("vertex does not exsit：" + vertexId);
            return false;
        }
    }

    @Override
    public boolean addEdge(Edge e) {
        if (addWeightVertex(outLinkGraph, e.id1, e.id2, e.weight)
                && addWeightVertex(inLinkGraph, e.id2, e.id1, e.weight)) {
            edgeCount++;
            return true;
        }
        return false;
    }

    private boolean addWeightVertex(HashMap<Integer, HashSet<WeightVertex>> linkGraph,
                                    int id1, int id2, int weight) {
        if (linkGraph.containsKey(id1)) {
            if (!linkGraph.get(id1).add(new WeightVertex(id2, weight))) {
                Log.warning("edge has already exsit: " + getEdgeString(id1, id2, weight));
                return false;
            }
        } else {
            HashSet<WeightVertex> set = new HashSet<>();
            set.add(new WeightVertex(id2, weight));
            linkGraph.put(id1, set);
        }
        return true;
    }

    @Override
    public boolean removeEdge(Edge e) {
        if (removeWeightVertex(outLinkGraph, e.id1, e.id2)
                && removeWeightVertex(inLinkGraph, e.id2, e.id1)) {
            edgeCount--;
            return true;
        } else {
            Log.severe("edge does not exsit：" + getEdgeString(e));
            return false;
        }
    }

    private boolean removeWeightVertex(HashMap<Integer, HashSet<WeightVertex>> linkGraph,
                                       int key, int WeightVertexId) {
        if (!linkGraph.containsKey(key)) {
            return false;
        }
        if (linkGraph.get(key).remove(new WeightVertex(WeightVertexId))) {
            // 当Set中WeightVertex删除完的时候，将key也一并删掉
            if (linkGraph.get(key).size() == 0) {
                linkGraph.remove(key);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeVertex(Vertex v) {
        if (!isVertexExsit(v.id)) {
            return false;
        }
        edgeCount -= getDegree(v.id);
        if (outLinkGraph.containsKey(v.id)) {
            for (Vertex endV : outLinkGraph.get(v.id)) {
                removeWeightVertex(inLinkGraph, endV.id, v.id);
            }
            outLinkGraph.remove(v.id);
        }
        if (inLinkGraph.containsKey(v.id)) {
            for (Vertex endV : inLinkGraph.get(v.id)) {
                removeWeightVertex(outLinkGraph, endV.id, v.id);
            }
            inLinkGraph.remove(v.id);
        }
        return true;
    }


    @Override
    public int getDistance(int startVertexId, int WeightVertexId) {
        return -1;
    }

    private String getEdgeString(Edge e) {
        return String.format("%d-(%d)->%d", e.id1, e.weight, e.id2);
    }

    private String getEdgeString(int id1, int id2, int weight) {
        return String.format("%d-(%d)->%d", id1, weight, id2);
    }
}
