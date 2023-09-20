package atl.g54314.model.dto;

import javafx.util.Pair;

/**
 * 
 * The StopDto class represents a stop DTO (Data Transfer Object) with a
 * composite key of line and station.
 */
public class StopDto extends Dto<Pair<Integer, Integer>> {

    private final StationDto station;
    private final int line;
    private final int order;

    /**
     * 
     * Constructs a StopDto object with the specified line, station, and order.
     * 
     * @param line    the line of the stop
     * @param station the station of the stop
     * @param order   the order of the stop
     */
    public StopDto(int line, StationDto station, int order) {
        super(new Pair<>(station.getKey(), line));
        this.station = station;
        this.line = line;
        this.order = order;
    }

    /**
     * 
     * Retrieves the station of the stop.
     * 
     * @return the station of the stop
     */
    public StationDto getStation() {
        return station;
    }

    /**
     * 
     * Retrieves the line of the stop.
     * 
     * @return the line of the stop
     */
    public int getLine() {
        return line;
    }

    /**
     * 
     * Retrieves the order of the stop.
     * 
     * @return the order of the stop
     */
    public int getOrder() {
        return order;
    }
}
