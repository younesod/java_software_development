package atl.g54314.model;

import atl.g54314.model.config.ConfigManager;
import atl.g54314.model.dao.StopDao;
import atl.g54314.model.dto.FavoriteDto;
import atl.g54314.model.dto.StationDto;
import atl.g54314.model.dto.StopDto;
import atl.g54314.model.exception.RepositoryException;
import atl.g54314.model.repository.FavoriteRepository;
import atl.g54314.model.repository.StopRepository;
import atl.g54314.model.utilities.MessageType;
import atl.g54314.model.utilities.dijkstra.Dijkstra;
import atl.g54314.model.utilities.dijkstra.Graph;
import atl.g54314.model.utilities.dijkstra.Node;
import atl.g54314.model.utilities.observer.Observable;
import atl.g54314.model.utilities.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * The Model class represents the application's data model and business logic.
 */
public class Model extends Observable {

    private final ObservableList<StationDto> pathStations = FXCollections.observableArrayList();
    private final ObservableSet<StationDto> allStations = FXCollections.observableSet();
    private final ObservableSet<FavoriteDto> allFavorites = FXCollections.observableSet();
    private final StopRepository repository = new StopRepository();
    private final FavoriteRepository favoriteRepository = new FavoriteRepository();

    private final HashMap<Integer, Node> allNodeStations = new HashMap<>();
    private final List<String> changeStation = new ArrayList<>();
    private Graph graph;
    private boolean isDoneSearch = false;
    private MessageType msgFavorite = null;

    /**
     * 
     * Constructs a Model object.
     * 
     * @throws RepositoryException if an error occurs in the repository
     * @throws IOException         if an I/O error occurs
     */
    public Model() throws RepositoryException, IOException {
        for (StopDto stopDto : repository.getAll()) {
            allStations.add(stopDto.getStation());
        }
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    /**
     * 
     * Creates the graph representing the stations and their connections.
     * 
     * @throws RepositoryException if an error occurs in the repository
     */
    private void makeGraphPath() throws RepositoryException {
        graph = new Graph();
        List<StopDto> allStops;
        List<StopDto> adjStops;

        allStations.forEach(stationDto -> {
            allNodeStations.put(stationDto.getKey(), new Node(stationDto));
        });

        for (StationDto station : allStations) {
            allStops = repository.getSame(station.getKey());
            Set<Integer> stopLines = allStops.stream().map(StopDto::getLine).collect(Collectors.toSet());
            station.setLines(stopLines);
            // completer pour les lignes adjacente
            adjStops = repository.getStopAdjacent(allStops);
            for (StopDto stops : adjStops) {
                allNodeStations.get(station.getKey()).addDestination(allNodeStations.get(stops.getStation().getKey()),
                        1);
            }
        }
        for (Node node : allNodeStations.values()) {
            graph.addNode(node);
        }
    }

    /**
     * 
     * Searches for the shortest path between two stations.
     * 
     * @param source      the source station
     * 
     * @param destination the destination station
     * 
     * @throws RepositoryException if an error occurs in the repository
     */
    public void search(StationDto source, StationDto destination) throws RepositoryException {
        if (source == null || destination == null) {
            isDoneSearch = false;
            notifyObservers();
            return;
        }
        makeGraphPath();
        pathStations.clear();
        Graph shortestGraph = Dijkstra.calculateShortestPathFromSource(graph, allNodeStations.get(source.getKey()));
        for (Node node : shortestGraph.getNodes()) {
            if (node.getStation().getKey().equals(destination.getKey())) {
                List<Node> shortestPath = node.getShortestPath();
                for (Node pathNode : shortestPath) {
                    StationDto pathStation = pathNode.getStation();
                    pathStations.add(pathStation);
                }
            }
        }
        pathStations.add(destination);

        changeStation.clear();
        Map<Integer, Integer> previousLine = new HashMap<>();
        pathStations.get(0).getLines().forEach(line -> {
            previousLine.put(line, line);
        });

        boolean changeLine;
        for (int i = 0; i < pathStations.size(); i++) {
            changeLine = true;
            for (int line : pathStations.get(i).getLines()) {
                if (previousLine.containsKey(line)) {
                    changeLine = false;
                    break;
                }
            }
            if (changeLine) {
                previousLine.clear();
                changeStation.add(pathStations.get(i - 1).getName());
                for (int line : pathStations.get(i).getLines()) {
                    previousLine.put(line, line);
                }
            }
        }
        isDoneSearch = true;
        notifyObservers();
    }

    /**
     * 
     * Resets the favorites by retrieving them from the repository.
     * 
     * @throws RepositoryException if an error occurs in the repository
     */
    private void resetFavorite() throws RepositoryException {
        allFavorites.clear();
        allFavorites.addAll(favoriteRepository.getAll());
    }

    /**
     * 
     * Adds a favorite route.
     * 
     * @param source      the source station
     * @param destination the destination station
     * @param name        the name of the favorite route
     * @throws RepositoryException if an error occurs in the repository
     */
    public void addFavorite(StationDto source, StationDto destination, String name) throws RepositoryException {
        if (source == null || destination == null) {
            msgFavorite = MessageType.INVALID_DATA;
            notifyObservers();
            return;
        }
        FavoriteDto favorite = favoriteRepository.get(name);
        FavoriteDto newFavorite = new FavoriteDto(name, source, destination);
        if (favorite == null) {
            favoriteRepository.add(newFavorite);
        } else if (!favorite.equals(newFavorite)) {
            favoriteRepository.update(newFavorite);
        } else {
            msgFavorite = MessageType.NO_UPDATE_CHANGE;
        }
        resetFavorite();
        notifyObservers();
    }

    /**
     * 
     * Deletes a favorite route.
     * 
     * @param source      the source station
     * @param destination the destination station
     * @param name        the name of the favorite route
     * @throws RepositoryException if an error occurs in the repository
     */
    public void deleteFavorite(StationDto source, StationDto destination, String name) throws RepositoryException {
        if (source == null || destination == null) {
            msgFavorite = MessageType.INVALID_DATA;
            notifyObservers();
            return;
        }
        FavoriteDto favorite = favoriteRepository.get(name);
        if (favorite != null) {
            favoriteRepository.remove(name);
        } else {
            msgFavorite = MessageType.DELETE_FAIL;
        }
        resetFavorite();
        notifyObservers();
    }

    /**
     * 
     * Returns the observable list of stations in the path.
     * 
     * @return the observable list of stations
     */
    public ObservableList<StationDto> getPathStations() {
        return pathStations;
    }

    /**
     * 
     * Returns the observable set of all stations.
     * 
     * @return the observable set of stations
     */
    public ObservableSet<StationDto> getAllStations() {
        return allStations;
    }

    /**
     * 
     * Returns whether the search is done or not.
     * 
     * @return true if the search is done, false otherwise
     */
    public boolean isDoneSearch() {
        return isDoneSearch;
    }

    /**
     * 
     * Returns the list of stations where a line change occurs.
     * 
     * @return the list of change stations
     */
    public List<String> getChangeStation() {
        return changeStation;
    }

    /**
     * 
     * Sets the message type for favorite operations.
     * 
     * @param msgFavorite the message type
     */
    public void setMsgFavorite(MessageType msgFavorite) {
        this.msgFavorite = msgFavorite;
    }

    /**
     * 
     * Returns the observable set of all favorite routes.
     * 
     * @return the observable set of favorite routes
     */
    public ObservableSet<FavoriteDto> getAllFavorites() {
        return allFavorites;
    }

    /**
     * 
     * Returns the message type for favorite operations.
     * 
     * @return the message type
     */
    public MessageType getMsgFavorite() {
        return msgFavorite;
    }
}
