package atl.g54314.model.repository;

import atl.g54314.model.config.ConfigManager;
import atl.g54314.model.dao.StopDao;
import atl.g54314.model.dto.StationDto;
import atl.g54314.model.dto.StopDto;
import atl.g54314.model.exception.RepositoryException;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class StopRepositoryTest {

    @Mock
    private StopDao mock;
    private final StopDto etangsNoirs;
    private final List<StopDto> all;

    private StopDao instance;

    public StopRepositoryTest() {
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
        }catch(RepositoryException | IOException e){
            org.junit.jupiter.api.Assertions.fail("Error connection to database",e);
        }
    }

    @BeforeEach
    void init() throws RepositoryException {
        List<StopDto> sameMok = new ArrayList<>();
        sameMok.add(all.get(1));
        sameMok.add(all.get(4));

        List<StopDto> adjLocalMok = new ArrayList<>();
        adjLocalMok.add(all.get(10));
        adjLocalMok.add(all.get(1));
        adjLocalMok.add(all.get(3));

        Mockito.lenient().when(mock.selectAll()).thenReturn(all);
        Mockito.lenient().when(mock.selectSame(all.get(1).getKey().getKey())).thenReturn(sameMok);
        Mockito.lenient().when(mock.selectStopAdjacent(sameMok)).thenReturn(adjLocalMok);
        Mockito.lenient().when(mock.select(new Pair<>(8382, 1))).thenThrow(UnsupportedOperationException.class);
        Mockito.lenient().when(mock.insert(etangsNoirs)).thenThrow(UnsupportedOperationException.class);
        Mockito.lenient().when(mock.selectSame(999)).thenReturn(new ArrayList<>());
    }

    @Test
    public void testGetAll() throws Exception {
        StopRepository repository = new StopRepository(mock);
        List<StopDto> result = repository.getAll();

        assertEquals(all, result);
        Mockito.verify(mock, times(1)).selectAll();
    }

    @Test
    public void testGetAdj() throws Exception {
        StopRepository repository = new StopRepository(mock);
        List<StopDto> sameStation = repository.getSame(all.get(1).getKey().getKey());
        List<StopDto> adjacent = repository.getStopAdjacent(sameStation);

        List<StopDto> adjLocal = new ArrayList<>();
        adjLocal.add(all.get(10));
        adjLocal.add(all.get(1));
        adjLocal.add(all.get(3));

        assertEquals(adjLocal, adjacent);
        Mockito.verify(mock, times(1)).selectStopAdjacent(sameStation);
    }

    @Test
    public void testGetSame() throws Exception {
        StopRepository repository = new StopRepository(mock);
        List<StopDto> sameStation = repository.getSame(all.get(1).getKey().getKey());

        List<StopDto> same = new ArrayList<>();
        same.add(all.get(1));
        same.add(all.get(4));

        assertEquals(same, sameStation);
        Mockito.verify(mock, times(1)).selectSame(all.get(1).getKey().getKey());
    }

    @Test
    public void testGetSameNotExist() throws Exception {
        StopRepository repository = new StopRepository(mock);
        List<StopDto> sameStation = repository.getSame(999);
        List<StopDto> same = new ArrayList<>();

        assertEquals(same, sameStation);
        Mockito.verify(mock, times(1)).selectSame(999);
    }

    @Test
    public void testGet() throws Exception {
        StopRepository repository = new StopRepository(mock);

        assertThrows(UnsupportedOperationException.class, () -> {
            repository.get(new Pair<>(8382, 1));
        });
    }

    @Test
    public void testAdd() throws Exception {
        StopRepository repository = new StopRepository(mock);

        assertThrows(UnsupportedOperationException.class, () -> {
            repository.add(etangsNoirs);
        });
    }

}