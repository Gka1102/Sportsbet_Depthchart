package com.sports.depthchart.tests.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sports.depthchart.controller.SportsDepthChartController;
import com.sports.depthchart.model.SportsDepthChart;
import com.sports.depthchart.service.SportsDepthChartService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(value = SportsDepthChartController.class)
@WithMockUser
public class SportsDepthChartControllerTest {
    @MockBean
    SportsDepthChartService service;
    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper=new ObjectMapper();

    @Test
    public void testAddPlayerToDepthchart_created() throws Exception {
        SportsDepthChart sportsDepthChart = new SportsDepthChart();
        sportsDepthChart.setPlayerName("bob");
        sportsDepthChart.setPositionName("KR");
        when(service.createPlayerInDepthChart(any())).thenReturn(1);
        mockMvc.perform(put("/depthchart/addPlayer")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(sportsDepthChart)))
                .andExpect(status().isCreated());
    }
    @Test
    public void testAddPlayerToDepthchart_unprocessableEntity() throws Exception {
        SportsDepthChart sportsDepthChart = new SportsDepthChart();
        sportsDepthChart.setPlayerName("bob");
        sportsDepthChart.setPositionName("KR");
        when(service.createPlayerInDepthChart(any())).thenReturn(0);
        mockMvc.perform(put("/depthchart/addPlayer")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(sportsDepthChart))).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testRemovePlayerFromDepthChart_removedPalyer() throws Exception {
        when(service.deletePlayerDepth(any(),any())).thenReturn(1);
        mockMvc.perform(delete(
                "/depthchart/remove/playerName/position")).andExpect(status().isOk());
    }
    @Test
    public void testRemovePlayerFromDepthChart_playernotfound() throws Exception {
        when(service.deletePlayerDepth(any(),any())).thenReturn(0);
        mockMvc.perform(delete("/depthchart/remove/alice/WR")).andExpect(status().is4xxClientError());
    }
    @Test
    public void testgetFullDepthchart_emptyDetails() throws Exception {
        when(service.getFullDepthChart()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/depthchart")).andExpect(status().is4xxClientError());
    }
    @Test
    public void testgetFullDepthchart() throws Exception {
        Mockito.when(service.getFullDepthChart()).thenReturn(getFullDepthDetails());
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/depthchart").accept(MediaType.APPLICATION_JSON_VALUE);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        String actualBody =result.getResponse().getContentAsString();

        String expected = "[{\"playerId\":1,\"playerName\":\"bob\",\"positionName\":\"WR\",\"positionDepth\":0},"
                +"{\"playerId\":2,\"playerName\":\"alice\",\"positionName\":\"WR\",\"positionDepth\":0},"
                + "{\"playerId\":3,\"playerName\":\"charlie\",\"positionName\":\"WR\",\"positionDepth\":2}]";
        assertEquals(expected, actualBody);
    }

    @Test
    public void testgetPlayerUnderDepth_emptyDetails() throws Exception {
        when(service.getPlayerListWithPosition(any(),any())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/depthchart/playerName/position")).andExpect(status().is4xxClientError());
    }
    @Test
    public void testgetPlayerUnderDepth() throws Exception {
        when(service.getPlayerListWithPosition(any(),any())).thenReturn(getFullDepthDetails());
        MvcResult result= mockMvc.perform(get("/depthchart/playerName/position")).andReturn();

        String actualBody =result.getResponse().getContentAsString();
        String expected = "[{\"playerId\":1,\"playerName\":\"bob\",\"positionName\":\"WR\",\"positionDepth\":0},"
                +"{\"playerId\":2,\"playerName\":\"alice\",\"positionName\":\"WR\",\"positionDepth\":0},"
                + "{\"playerId\":3,\"playerName\":\"charlie\",\"positionName\":\"WR\",\"positionDepth\":2}]";
        assertEquals(expected, actualBody);
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
