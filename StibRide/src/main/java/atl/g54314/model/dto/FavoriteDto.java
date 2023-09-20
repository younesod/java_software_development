package atl.g54314.model.dto;

import java.util.Objects;

/**
 * 
 * The FavoriteDto class represents a favorite DTO (Data Transfer Object) with a
 * key, source station, and destination station.
 */
public class FavoriteDto extends Dto<String> {
    private final StationDto source;
    private final StationDto destination;

    /**
     * 
     * Constructs a FavoriteDto object with the specified key, source station, and
     * destination station.
     * 
     * @param key         the key of the favorite
     * @param source      the source station
     * @param destination the destination station
     */
    public FavoriteDto(String key, StationDto source, StationDto destination) {
        super(key);
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String getKey() {
        return super.getKey();
    }

    /**
     * 
     * Retrieves the source station of the favorite.
     * 
     * @return the source station of the favorite
     */
    public StationDto getSource() {
        return source;
    }

    /**
     * 
     * Retrieves the destination station of the favorite.
     * 
     * @return the destination station of the favorite
     */
    public StationDto getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        FavoriteDto that = (FavoriteDto) o;
        return source.equals(that.source) && destination.equals(that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), source, destination);
    }
}
