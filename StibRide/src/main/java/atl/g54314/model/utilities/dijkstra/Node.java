package atl.g54314.model.utilities.dijkstra;

import atl.g54314.model.dto.StationDto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * The Node class represents a node in a graph.
 */
public class Node {

    private final StationDto station;
    private final Map<Node, Integer> adjacentNodes = new HashMap<>();
    private Integer distance = Integer.MAX_VALUE;
    private List<Node> shortestPath = new LinkedList<>();

    /**
     * 
     * Constructs a node with the given station.
     * 
     * @param station the station associated with the node
     */
    public Node(StationDto stop) {
        this.station = stop;
    }

    /**
     * 
     * Returns the map of adjacent nodes and their distances.
     * 
     * @return the map of adjacent nodes and distances
     */
    public Map<Node, Integer> getAdjacentNodes() {
        return adjacentNodes;
    }

    /**
     * 
     * Adds a destination node and its distance to the adjacent nodes of the current
     * node.
     * 
     * @param destination the destination node
     * @param distance    the distance to the destination node
     */
    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    /**
     * 
     * Returns the station associated with the node.
     * 
     * @return the station
     */
    public StationDto getStation() {
        return station;
    }

    /**
     * 
     * Returns the distance of the node from the source node.
     * 
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * 
     * Sets the distance of the node from the source node.
     * 
     * @param distance the distance to set
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * 
     * Returns the shortest path to the node.
     * 
     * @return the shortest path
     */
    public List<Node> getShortestPath() {
        return shortestPath;
    }

    /**
     * 
     * Sets the shortest path to the node.
     * 
     * @param newShortestPath the new shortest path
     */
    public void setShortestPath(LinkedList<Node> newShortestPath) {
        shortestPath = newShortestPath;
    }
}
