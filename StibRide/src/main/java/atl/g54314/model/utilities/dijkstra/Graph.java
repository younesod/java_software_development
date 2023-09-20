package atl.g54314.model.utilities.dijkstra;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * The Graph class represents a graph consisting of nodes.
 */
public class Graph {
    private final Set<Node> nodes = new HashSet<>();

    /**
     * 
     * Adds a node to the graph.
     * 
     * @param node the node to add
     */
    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    /**
     * 
     * Returns the set of nodes in the graph.
     * 
     * @return the set of nodes
     */
    public Set<Node> getNodes() {
        return nodes;
    }
}
