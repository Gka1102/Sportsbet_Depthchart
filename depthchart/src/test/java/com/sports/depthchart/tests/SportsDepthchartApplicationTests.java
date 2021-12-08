package com.sports.depthchart.tests;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import com.sports.depthchart.controller.SportsDepthChartController;
import com.sports.depthchart.model.SportsDepthChart;
import com.sports.depthchart.service.SportsDepthChartService;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(value = SportsDepthChartController.class)
@WithMockUser
class SportsDepthchartApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private SportsDepthChartService mockDepthChartService;
	private static final org.jboss.logging.Logger log = LoggerFactory
            .logger(SportsDepthchartApplicationTests.class);
	Gson gson = new Gson();	
	
	
	@Test
	public void mockGetFullDepthChart() throws Exception{
		
		SportsDepthChart record_1 = new SportsDepthChart(1,"NFL",1,"bob","WR",0);
		SportsDepthChart record_2 = new SportsDepthChart(2,"NFL",2,"alice","WR",0);
		SportsDepthChart record_3 = new SportsDepthChart(3,"NFL",3,"charlie","WR",2); 
		List<SportsDepthChart> mockList = new ArrayList<>();
		mockList.add(record_1);
		mockList.add(record_2);
		mockList.add(record_3);

		Mockito.when(mockDepthChartService.getFullDepthChart()).thenReturn(mockList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/depthchart").accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
				
		// convert mockList to json
	     String jsonList = gson.toJson(result.getResponse().getContentAsString()).replaceAll("\\\\","");
			
	     String expected = "\"[{\"playerId\":1,\"playerName\":\"bob\",\"positionName\":\"WR\",\"positionDepth\":0},"
	    		 +"{\"playerId\":2,\"playerName\":\"alice\",\"positionName\":\"WR\",\"positionDepth\":0},"
		     		+ "{\"playerId\":3,\"playerName\":\"charlie\",\"positionName\":\"WR\",\"positionDepth\":2}]\"";	
		log.info("Mock Usecase3 - Print full depthchart:"+"\n expected= "+expected+"\n jsonList= " + jsonList);
		JSONAssert.assertEquals(expected, jsonList, true);
	}
	
	@Test
	public void mockGetPlayerUnderDepth() throws Exception{
		
		SportsDepthChart record_1 = new SportsDepthChart(1,"NFL",1,"bob","WR",1);
		SportsDepthChart record_3 = new SportsDepthChart(3,"NFL",3,"charlie","WR",2); 
		List<SportsDepthChart> mockList = new ArrayList<>();
		mockList.add(record_1); 
		mockList.add(record_3);
		
		String playerName = "alice";
        String position = "WR"; 

		Mockito.when(mockDepthChartService.getPlayerListWithPosition(playerName,position)).thenReturn(mockList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/depthchart/alice/WR")			
				.accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
				
	     // convert mockList to json
	     String jsonList = gson.toJson(result.getResponse().getContentAsString()).replaceAll("\\\\","");	  
			
	     String expected = "\"[{\"playerId\":1,\"playerName\":\"bob\",\"positionName\":\"WR\",\"positionDepth\":1},"
	     		+ "{\"playerId\":3,\"playerName\":\"charlie\",\"positionName\":\"WR\",\"positionDepth\":2}]\"";	
	     log.info("Mock Usecase4 - Players under player in depthchart:"+"\n expected= "+expected+"\n jsonList= " + jsonList);
		JSONAssert.assertEquals(expected, jsonList, true);
	}
	
	@Test 
	public void mockRemovePlayerFromDepthChart() throws Exception{
		String playerName = "alice";
        String position = "WR"; 
		
		Mockito.when(mockDepthChartService.deletePlayerDepth(playerName,position)).thenReturn(1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/depthchart/remove/alice/WR")			
				.accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();		
		assertEquals(HttpStatus.OK.value(), response.getStatus()); 
	}

}
