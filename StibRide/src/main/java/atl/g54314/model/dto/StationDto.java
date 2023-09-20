package atl.g54314.model.dto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * The StationDto class represents a station DTO (Data Transfer Object) with a
 * key and name.
 */
public class StationDto extends Dto<Integer> {
    private final String name;
    private Set<Integer> lines;

    /**
     * 
     * Constructs a StationDto object with the specified key and name.
     * 
     * @param key  the key of the station
     * @param name the name of the station
     */
    public StationDto(Integer key, String name) {
        super(key);
        this.name = name;
        this.lines = new HashSet<>();
    }

    /**
     * 
     * Retrieves the name of the station.
     * 
     * @return the name of the station
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * Retrieves the set of lines associated with the station.
     * 
     * @return the set of lines associated with the station
     */
    public Set<Integer> getLines() {
        return lines;
    }

    /**
     * 
     * Sets the set of lines associated with the station.
     * 
     * @param lines the set of lines to set
     */
    public void setLines(Set<Integer> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * 
     * Returns a string representation of the lines associated with the station.
     * 
     * @return a string representation of the lines associated with the station
     */
    public String getLinesToString() {
        var strStops = new StringBuilder("[");
        for (var line : lines) {
            strStops.append(line).append(", ");
        }
        String str = strStops.toString();
        if (strStops.length() > 1) {
            str = str.substring(0, str.length() - 2);
        }
        return str + "]";
    }
}
