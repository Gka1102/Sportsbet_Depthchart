package com.sports.depthchart.tests.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.sports.depthchart.exception.GenericExceptions;
import com.sports.depthchart.model.SportsDepthChart;
import com.sports.depthchart.repo.SportsDepthChartRepository;
import com.sports.depthchart.service.SportsDepthChartService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SportsDepthChartServiceTest {
    @Mock
    SportsDepthChartRepository depthChartRepo;
    @InjectMocks
    SportsDepthChartService sportsDepthChartService;

    @Test
    public void test_createPlayerInDepthChart(){
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn(getFullDepthDetails().get(0));
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(getFullDepthDetails());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);
        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(getSportsDepthChart());
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }

    @Test
    public void test_createPlayerInDepthChart_no_existingPlayer(){
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(null);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn(getFullDepthDetails().get(0));
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(getFullDepthDetails());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);
        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(getSportsDepthChart());
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }
    @Test
    public void test_createPlayerInDepthChart_no_depthposition(){
        SportsDepthChart sportsDepthChart = getSportsDepthChart();
        sportsDepthChart.setPositionDepth(null);
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( getFullDepthDetails().get(0));
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(getFullDepthDetails());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);
        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(sportsDepthChart);
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }
    @Test
    public void test_createPlayerInDepthChart_no_existing_sameplayer_pos(){
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( null);
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(getFullDepthDetails());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);
        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(getSportsDepthChart());
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }
    @Test
    public void test_createPlayerInDepthChart_existing_sameplayer_pos_with_nullDepth(){
        SportsDepthChart sportsDepthChart = getFullDepthDetails().get(0);
        sportsDepthChart.setPositionDepth(null);
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( sportsDepthChart);
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(getFullDepthDetails());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);
        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(getSportsDepthChart());
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }
    @Test
    public void test_createPlayerInDepthChart_null_existing_sameplayer_pos(){
        SportsDepthChart sportsDepthChart = getFullDepthDetails().get(0);
        sportsDepthChart.setPositionDepth(null);
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( sportsDepthChart);
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(null);
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);
        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(getSportsDepthChart());
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }
    @Test
    public void test_createPlayerInDepthChart_emptyList_existing_sameplayer_pos(){
        SportsDepthChart sportsDepthChart = getFullDepthDetails().get(0);
        sportsDepthChart.setPositionDepth(null);
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( sportsDepthChart);
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(new ArrayList<>());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);
        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(getSportsDepthChart());
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }
    @Test
    public void test_createPlayerInDepthChart_emptyExistingSameplayerPos_nullPosDepth(){
        SportsDepthChart sportsDepthChartRequest = getSportsDepthChart();
        sportsDepthChartRequest.setPositionDepth(null);
        SportsDepthChart sportsDepthChart = getFullDepthDetails().get(0);
        sportsDepthChart.setPositionDepth(null);
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( sportsDepthChart);
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(null);
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);

        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(sportsDepthChartRequest);
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }
    @Test
    public void test_createPlayerInDepthChart_null_MaxPositionDepth(){
        SportsDepthChart sportsDepthChartRequest = getSportsDepthChart();
        sportsDepthChartRequest.setPositionDepth(null);
        SportsDepthChart sportsDepthChart = getFullDepthDetails().get(0);
        sportsDepthChart.setPositionDepth(null);
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( sportsDepthChart);
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(null);
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(null);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);

        Integer playerCreated = sportsDepthChartService.createPlayerInDepthChart(sportsDepthChartRequest);
        assertEquals(java.util.Optional.of(1),java.util.Optional.of(playerCreated));
    }
    @Test
    public void test_createPlayerInDepthChart_exception_createNewPlayer(){
        SportsDepthChart sportsDepthChartRequest = getSportsDepthChart();
        sportsDepthChartRequest.setPositionDepth(null);
        SportsDepthChart sportsDepthChart = getFullDepthDetails().get(0);
        sportsDepthChart.setPositionDepth(null);
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( sportsDepthChart);
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(null);
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(null);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(0);

        assertThrows(GenericExceptions.class,()->sportsDepthChartService.createPlayerInDepthChart(sportsDepthChartRequest));
    }
    @Test
    public void test_createPlayerInDepthChart_existSamePlayerName(){
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn(getFullDepthDetails().get(0));
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(getFullDepthDetails());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(1);
        assertThrows(GenericExceptions.class,()->sportsDepthChartService.createPlayerInDepthChart(getFullDepthDetails().get(0)));
    }
    @Test
    public void test_createPlayerInDepthChart_no_existing_sameplayer_pos_create_failure(){
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(null);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn( getFullDepthDetails().get(0));
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(getFullDepthDetails());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getSportsDepthChart().getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(0);
        assertThrows(GenericExceptions.class,()->sportsDepthChartService.createPlayerInDepthChart(getSportsDepthChart()));
    }
    @Test
    public void test_createPlayerInDepthChart_existSamePlayerName_diffPosName(){
        SportsDepthChart sportsDepthChartRequest = getSportsDepthChart();
        sportsDepthChartRequest.setPlayerName(getFullDepthDetails().get(0).getPlayerName());
        sportsDepthChartRequest.setPositionName("WR1");
        when(depthChartRepo.getExistingPlayer(any())).thenReturn(1l);
        when(depthChartRepo.getExistingPlayerSamePosition(any(),any())).thenReturn(getFullDepthDetails().get(0));
        when(depthChartRepo.getExistingPlayersListForSamePosition(anyString(),anyInt())).thenReturn(getFullDepthDetails());
        when(depthChartRepo.UpdateExistingPlayers(anyInt(),anyString())).thenReturn(getFullDepthDetails().get(0).getPlayerId());
        when(depthChartRepo.getMaxPositionDepth(anyString())).thenReturn(0);
        when(depthChartRepo.createNewPlayer(anyLong(),anyString(),anyString(),anyInt(),anyString())).thenReturn(2);
        assertThrows(GenericExceptions.class,()->sportsDepthChartService.createPlayerInDepthChart(sportsDepthChartRequest));
    }
    @Test
    public void test_getFullDepthChart(){
        when(depthChartRepo.getAllPlayersList()).thenReturn(getFullDepthDetails());
        List<SportsDepthChart> response =sportsDepthChartService.getFullDepthChart();
        assertEquals(3,response.size());
        assertEquals("bob",response.get(0).getPlayerName());
        assertEquals("WR",response.get(0).getPositionName());
        assertEquals(1,response.get(0).getPlayerId());
        assertEquals(1,response.get(0).getSportsDepthId());
    }
    @Test
    public void test_deletePlayerDepth(){
        when(depthChartRepo.removePlayerFromDepthChart(anyString(),anyString())).thenReturn(2);
        Integer playerDeleted =sportsDepthChartService.deletePlayerDepth("alice","WR");
        assertEquals(2,(int)playerDeleted);
    }
    @Test
    public void test_getPlayerListWithPosition(){
        List<SportsDepthChart> sportsDepthCharts = new ArrayList<>();
        sportsDepthCharts.add(getFullDepthDetails().get(0));
        when(depthChartRepo.getPlayerListWithPos(anyString(),anyString())).thenReturn(sportsDepthCharts);
        List<SportsDepthChart> response =sportsDepthChartService.getPlayerListWithPosition("bob","WR");
        assertEquals(1,response.size());
        assertEquals("bob",response.get(0).getPlayerName());
        assertEquals("WR",response.get(0).getPositionName());
        assertEquals(1,response.get(0).getPlayerId());
        assertEquals(1,response.get(0).getSportsDepthId());
    }

    public SportsDepthChart getSportsDepthChart(){
        return new SportsDepthChart(1,"NFL",0,"bob1","WR",0);
    }
    
    private List<SportsDepthChart> getFullDepthDetails(){
        SportsDepthChart record_1 = new SportsDepthChart(1,"NFL",1,"bob","WR",0);
        SportsDepthChart record_2 = new SportsDepthChart(2,"NFL",2,"alice","WR",0);
        SportsDepthChart record_3 = new SportsDepthChart(3,"NFL",3,"charlie","WR",2);
        List<SportsDepthChart> mockList = new ArrayList<>();
        mockList.add(record_1);
        mockList.add(record_2);
        mockList.add(record_3);
        return mockList;
    }
}
