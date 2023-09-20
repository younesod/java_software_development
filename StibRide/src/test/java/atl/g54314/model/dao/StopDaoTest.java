package atl.g54314.model.dao;

import atl.g54314.model.config.ConfigManager;
import atl.g54314.model.dao.StopDao;
import atl.g54314.model.dto.StationDto;
import atl.g54314.model.dto.StopDto;
import atl.g54314.model.exception.RepositoryException;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StopDaoTest {

    private final StopDto etangsNoirs;
    private final List<StopDto> all;

    private StopDao instance;

    public StopDaoTest() {
        all=new ArrayList<>();
        all.add(new StopDto(1, new StationDto(8382, "GARE DE L'OUEST"), 1));
        all.add(new StopDto(1, new StationDto(8742, "BEEKKANT"), 2));
        all.add(new StopDto(2, new StationDto(8764, "SIMONIS"), 1));
        all.add(new StopDto(2, new StationDto(8754, "OSSEGHEM"), 2));
        all.add(new StopDto(2, new StationDto(8742, "BEEKKANT"), 3));
        all.add(new StopDto(2, new StationDto(8382, "GARE DE L'OUEST"), 4));
        all.add(new StopDto(5, new StationDto(8641, "ERASME"), 1));
        all.add(new StopDto(5, new StationDto(8652, "EDDY MERCKX"), 2));
        all.add(new StopDto(6, new StationDto(8833, "ROI BAUDOUIN"), 1));
        all.add(new StopDto(6, new StationDto(8824, "HEYSEL"), 2));
        etangsNoirs = new StopDto(1, new StationDto(8292, "ETANGS NOIRS"), 3);
        all.add(etangsNoirs);

        try{
            ConfigManager.getInstance().load();
            instance= StopDao.getInstance();
            System.out.println("JLC DEBUG " +ConfigManager.getInstance().getProperties("db.url"));

            System.out.println("JLC DEBUG " +ConfigManager.getInstance().getProperties("app.name"));
        }catch(RepositoryException | IOException e){
            org.junit.jupiter.api.Assertions.fail("Error connection to database",e);
        }
    }

    @Test
    public void testSelectNotImplemented() throws Exception{
        assertThrows(UnsupportedOperationException.class,()->{
            instance.select(new Pair<>(8382,1));
        });
    }

    @Test
    public void testInsertNotImplemented() throws  Exception{
        assertThrows(UnsupportedOperationException.class,()->{
            instance.insert(new StopDto(1,new StationDto(10000,"STATION ESI"),1));
        });
    }

    @Test
    public void testUpdateNotImplemented() throws Exception{
        assertThrows(UnsupportedOperationException.class,()->{
            instance.update(new StopDto(1,new StationDto(8382,"STATION ESI"),1));
        });
    }

    @Test
    public void testSelectAll() throws Exception{
        List<StopDto> result = instance.selectAll();
        boolean found = false;
        for (StopDto item : all) {
            found = false;
            for (var itemBdd : result) {
                if (itemBdd.equals(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    public void testSelectSameEmptyGivenParam(){
        assertThrows(RepositoryException.class,()->{
            instance.selectSame(null);
        });
    }

    @Test
    public void testSelectSameNotExist() throws RepositoryException {
        List<StopDto> result = instance.selectSame(999);
        assertEquals(new ArrayList<>(), result);
        assertEquals(0, result.size());
    }

    @Test
    public void testSelectSame() throws RepositoryException {
        List<StopDto> result = instance.selectSame(all.get(1).getKey().getKey());
        List<StopDto> same = new ArrayList<>();
        same.add(all.get(1));
        same.add(all.get(4));
        boolean found = false;
        for (StopDto item : same) {
            found = false;
            for (var itemBdd : result) {
                if (itemBdd.getStation().equals(item.getStation()) && itemBdd.getLine() != item.getLine()) {
                    found = true;
                    break;
                }
            }
        }
        assertTrue(found);
    }

    @Test
    public void testSelectStopAdjacentEmptyGivenParam(){
        assertThrows(RepositoryException.class,()->{
            instance.selectStopAdjacent(null);
        });
    }

    @Test
    public void testSelectStopAdjacent() throws Exception {
        List<StopDto> sameStation = instance.selectSame(all.get(1).getKey().getKey());
        List<StopDto> adjacent = instance.selectStopAdjacent(sameStation);

        List<StopDto> same = new ArrayList<>();
        same.add(all.get(10));
        same.add(all.get(1));
        same.add(all.get(3));

        boolean found = false;
        for (StopDto item : same) {
            found = false;
            for (StopDto itemBdd : adjacent) {
                if (itemBdd.getStation().equals(item.getStation())) {
                    found = true;
                    break;
                }
            }
        }
        assertTrue(found);
    }




}
