package Algorithm.tools.datastructrue.graph;

import Algorithm.tools.datastructrue.graph.edge.Edge;
import Algorithm.tools.datastructrue.graph.vertext.FrontVertex;
import Algorithm.tools.datastructrue.graph.vertext.WeightVertex;
import Algorithm.tools.datastructrue.graph.vertext.Vertex;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Logger;

public class UndirectLinkGraph implements IGraph {

    /**
     * 1. Map<Integer, HashSet<WeightVertex>>数据结构即可以维护
     * 2. map.ketSet()即图中所有点
     * 3. 前提是连通图
     */
    private final Logger Log = Logger.getLogger(UndirectLinkGraph.class.getName());

    private int edgeCount = 0;

    private final HashMap<Integer, HashSet<WeightVertex>> linkGraph = new HashMap<>();

    public UndirectLinkGraph() {
        super();
    }

    public UndirectLinkGraph(List<Edge> edges) {
        if (edges == null || edges.size() < 1) {
            throw new RuntimeException("edges input illegal null");
        }
        for (Edge edge : edges) {
            addEdge(edge);
        }
    }

    @Override
    public void printGraph() {
        for (int key : linkGraph.keySet()) {
            String discription = key + "";
            for (WeightVertex vertex : linkGraph.get(key)) {
                discription += String.format("-(%d)->%d", vertex.weight, vertex.id);
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
            for (Vertex v : linkGraph.get(currentId)) {
                if (!hasVisitedIdSet.contains(v.id)) {
                    stack.push(v.id);
                    System.out.print(v.id + " ");
                    hasVisitedIdSet.add(v.id);
                    hasUnVistitedId = true;
                    break;
                }
            }
            if (!hasUnVistitedId) {
                stack.pop();
            }
        }
        System.out.println();
    }


    @Override
    public void breadthFirstSearch(int startVertexId) {
        Set<Integer> hasVisitedIdSet = new HashSet<>();
        Queue<Integer> idQueue = new LinkedBlockingDeque<>();
        idQueue.add(startVertexId);
        while (!idQueue.isEmpty()) {
            int currentId = idQueue.remove();
            if (!hasVisitedIdSet.contains(currentId)) {
                System.out.print(currentId + " ");
                hasVisitedIdSet.add(currentId);
                for (Vertex v : linkGraph.get(currentId)) {
                    if (!hasVisitedIdSet.contains(v.id)) {
                        idQueue.add(v.id);
                    }
                }
            }
        }
        System.out.println();
    }

    @Override
    public int getVertexCount() {
        return linkGraph.size();
    }

    @Override
    public int getEdgeCount() {
        return edgeCount;
    }

    @Override
    public int getDegree(int vertexId) {
        if (!isVertexExsit(vertexId)) {
            return 0;
        }
        return linkGraph.get(vertexId).size();
    }

    @Override
    public int getInDegree(int vertexId) {
        return getDegree(vertexId);
    }

    @Override
    public int getOutDegree(int vertexId) {
        return getDegree(vertexId);
    }

    @Override
    public boolean isVertexExsit(int vertexId) {
        if (!linkGraph.containsKey(vertexId)) {
            Log.severe("vertex does not exsit：" + vertexId);
            return false;
        }
        return true;
    }

    @Override
    public boolean addEdge(Edge e) {
        if (addWeightVertex(e.id1, e.id2, e.weight)
                && addWeightVertex(e.id2, e.id1, e.weight)) {
            edgeCount++;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEdge(Edge e) {
        if (removeWeightVertex(e.id1, e.id2) && removeWeightVertex(e.id2, e.id1)) {
            edgeCount--;
            return true;
        } else {
            Log.severe("edge does not exsit：" + getEdgeString(e));
            return false;
        }
    }

    @Override
    public boolean removeVertex(Vertex v) {
        if (!isVertexExsit(v.id)) {
            return false;
        }
        edgeCount -= getDegree(v.id);
        for (WeightVertex endV : linkGraph.get(v.id)) {
            removeWeightVertex(endV.id, v.id);
        }
        linkGraph.remove(v.id);
        return true;
    }

    /**
     * 寻找连点间最短距离
     *
     * @param startVertexId 起点
     * @param endVertexId   终点
     * @return -1：两点不连通
     */
    @Override
    public int getDistance(int startVertexId, int endVertexId) {
        Set<Integer> hasVisitedIdSet = new HashSet<>();
        Queue<FrontVertex> idQueue = new LinkedBlockingDeque<>();
        int level = 0;
        idQueue.add(new FrontVertex(startVertexId, level, -1));
        Map<Integer, FrontVertex> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        while (!idQueue.isEmpty()) {
            FrontVertex currentVertex = idQueue.remove();
            map.put(currentVertex.id, currentVertex);
            if (currentVertex.id == endVertexId) {
                stack.push(currentVertex.id);
                int frontVertexId = currentVertex.frontVertextId;
                while (frontVertexId != -1) {
                    FrontVertex temp = map.get(frontVertexId);
                    stack.push(temp.id);
                    frontVertexId = temp.frontVertextId;
                }
                System.out.print(String.format("%d->%d最短路径: %d", startVertexId, endVertexId, stack.pop()));
                while (!stack.isEmpty()) {
                    System.out.print("->" + stack.pop());
                }
                System.out.println();
                System.out.println(String.format("%d->%d最短距离: %d",
                        startVertexId, endVertexId, currentVertex.level));
                return currentVertex.level;
            }
            if (!hasVisitedIdSet.contains(currentVertex.id)) {
                hasVisitedIdSet.add(currentVertex.id);
                for (Vertex v : linkGraph.get(currentVertex.id)) {
                    if (!hasVisitedIdSet.contains(v.id)) {
                        idQueue.add(new FrontVertex(v.id,
                                currentVertex.level + 1, currentVertex.id));
                    }
                }
            }
        }
        return -1;
    }


    private boolean removeWeightVertex(int key, int WeightVertexId) {
        if (!isVertexExsit(key)) {
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

    private boolean addWeightVertex(int id1, int id2, int weight) {
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

    private String getEdgeString(Edge e) {
        return String.format("%d-(%d)-%d", e.id1, e.weight, e.id2);
    }

    private String getEdgeString(int id1, int id2, int weight) {
        return String.format("%d-(%d)-%d", id1, weight, id2);
    }
}
