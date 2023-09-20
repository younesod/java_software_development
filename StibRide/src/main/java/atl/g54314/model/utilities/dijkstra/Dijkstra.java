package atl.g54314.model.utilities.dijkstra;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * 
 * The Dijkstra class implements the Dijkstra's algorithm to calculate the
 * shortest path in a graph.
 * // https://www.baeldung.com/java-dijkstra
 */
public class Dijkstra {

    /**
     * 
     * Calculates the shortest path from the source node in the graph.
     * 
     * @param graph  the graph
     * 
     * @param source the source node
     * 
     * @return the updated graph with the shortest paths
     */
    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            currentNode.getAdjacentNodes().forEach((adjacentNode, edgeWeight) -> {
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            });
            settledNodes.add(currentNode);
        }
        return graph;
    }

    /**
     * 
     * Returns the node with the lowest distance from the set of unsettled nodes.
     * 
     * @param unsettledNodes the set of unsettled nodes
     * @return the node with the lowest distance
     */
    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    /**
     * 
     * Calculates the minimum distance and updates the shortest path of the
     * evaluation node.
     * 
     * @param evaluationNode the evaluation node
     * @param edgeWeight     the weight of the edge between the evaluation node and
     *                       the source node
     * @param sourceNode     the source node
     */
    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}
