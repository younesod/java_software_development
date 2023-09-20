package atl.g54314.model.dao;

import atl.g54314.model.config.ConfigManager;
import atl.g54314.model.dto.FavoriteDto;
import atl.g54314.model.dto.StationDto;
import atl.g54314.model.exception.RepositoryException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FavoriteDaoTest {
    private final FavoriteDto home;
    private final FavoriteDto notExist;

    private final List<FavoriteDto> all;

    private FavoriteDao instance;

    public FavoriteDaoTest() throws RepositoryException {
        notExist = new FavoriteDto("notExist", new StationDto(8292, "DE BROUCKERE"), new StationDto(8382, "GARE DE L'OUEST"));
        all = new ArrayList<>();
        all.add(new FavoriteDto("other", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM")));
        home = new FavoriteDto("home", new StationDto(8641, "ERASME"), new StationDto(8742, "BEEKKANT"));
        all.add(home);

        try {
            ConfigManager.getInstance().load();
            instance = FavoriteDao.getInstance();

        } catch (RepositoryException |IOException ex ) {
            org.junit.jupiter.api.Assertions.fail("Error connection to database", ex);
        }
    }

    @BeforeEach
    public void setUpDeleteAll() throws RepositoryException {
        instance.deleteAll();
    }
    @Test
    public void testSelect() throws Exception {
        setUpDeleteAll();
        instance.insert(home);
        FavoriteDto result = instance.select("home");
        assertEquals(home, result);
    }

    @Test
    public void testSelectEmptyGivenValue() throws Exception {
        setUpDeleteAll();
        assertThrows(RepositoryException.class, () -> {
            instance.select(null);
        });
    }
    @Test
    public void testSelectNotExist() throws Exception {
        setUpDeleteAll();
        FavoriteDto result = instance.select(notExist.getKey());
        assertNull(result);
    }
    @Test
    public void testInsert() throws Exception {
        setUpDeleteAll();
        var newFavorite = new FavoriteDto("added0", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM"));
        List<FavoriteDto> resultBefore = instance.selectAll();
        instance.insert(newFavorite);
        List<FavoriteDto> resultAfter = instance.selectAll();
        assertEquals(resultBefore.size(), resultAfter.size() - 1);
        assertEquals(newFavorite, instance.select("added0"));
    }

    @Test
    public void testInsertEmptyGivenValue() throws Exception {
        setUpDeleteAll();
        assertThrows(RepositoryException.class, () -> {
            instance.insert(null);
        });
    }

    @Test
    public void testDelete() throws Exception {
        setUpDeleteAll();
        var newFavorite = new FavoriteDto("added2", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM"));
        instance.insert(newFavorite);
        List<FavoriteDto> resultBefore = instance.selectAll();
        instance.delete("added2");
        List<FavoriteDto> resultAfter = instance.selectAll();
        assertEquals(resultBefore.size() - 1, resultAfter.size());
        assertNull(instance.select("added2"));
    }

    @Test
    public void testDeleteEmptyGivenValue() throws Exception {
        setUpDeleteAll();
        assertThrows(RepositoryException.class, () -> {
            instance.delete(null);
        });
    }

    @Test
    public void testUpdate() throws Exception {
        setUpDeleteAll();
        var newFavorite = new FavoriteDto("added3", new StationDto(8652, "EDDY MERCKX"), new StationDto(8754, "OSSEGHEM"));
        instance.insert(newFavorite);
        FavoriteDto resultBefore = instance.select("added3");

        var editedFavorite = new FavoriteDto("added3", new StationDto(8292, "DE BROUCKERE"), new StationDto(8382, "GARE DE L'OUEST"));
        instance.update(editedFavorite);
        FavoriteDto resultAfter = instance.select("added3");

        assertEquals(resultBefore.getDestination(), newFavorite.getDestination());
        assertEquals(resultBefore.getSource(), newFavorite.getSource());
        assertEquals(resultAfter.getDestination(), editedFavorite.getDestination());
        assertEquals(resultAfter.getSource(), editedFavorite.getSource());
        instance.delete("added3");
    }

    @Test
    public void testUpdateEmptyGivenValue() throws Exception {
        setUpDeleteAll();
        assertThrows(RepositoryException.class, () -> {
            instance.update(null);
        });
    }

}