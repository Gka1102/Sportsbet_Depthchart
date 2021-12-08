package com.sports.depthchart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sports.depthchart.exception.UserNotFoundException;
import com.sports.depthchart.model.SportsDepthChart;
import com.sports.depthchart.service.SportsDepthChartService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SportsDepthChartController {

	 @Autowired
	 SportsDepthChartService service;
	 
	 /**
		 * UseCase 1 - Add player to the depth chart
		 * If Player successfully created returns status 201 
		 * otherwise couldn't create player with status 422
	     * @return 
	     * @throws Exception
	     */
	    @PutMapping(path = "/depthchart/addPlayer",consumes = { MediaType.APPLICATION_JSON_VALUE })
	    @ResponseBody
	    public ResponseEntity<Object> addPlayerToDepthchart( @RequestBody SportsDepthChart depthChart)  throws Exception{
	    	Integer playerCreated = service.createPlayerInDepthChart(depthChart);
	    	if(playerCreated == 1)
	    		return new ResponseEntity<Object>("Player added successfully.", HttpStatus.CREATED);
	    	else
	    		return new ResponseEntity<Object>("Couldn't add player.", HttpStatus.UNPROCESSABLE_ENTITY);
	    }

	 /**
     * UseCase 2 - Remove player from depth chart for a position
     * @param playerName
     * @param position
     * @throws Exception
     */
    @DeleteMapping(path = "/depthchart/remove/{playerName}/{position}")
    @ResponseBody
    public ResponseEntity<String> removePlayerFromDepthChart(@PathVariable ("playerName") String playerName, @PathVariable("position") String position) throws Exception{
    	Integer playerDeleted = service.deletePlayerDepth(playerName,position);   
    	if (playerDeleted == 1)
    		return new ResponseEntity<String>("Removed player "+ playerName +" from position "+ position, HttpStatus.OK);
    	else 
    		throw new UserNotFoundException("Player doesn't exist to delete.");
     }
	 
	 /**
	 * UseCase 3 - Request to display all players
     * @return all the positions with list of player id's
     * @throws Exception
     */
    @GetMapping(path = "/depthchart", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SportsDepthChart> getFullDepthchart() throws Exception{
        List<SportsDepthChart> fullDepthDetails = service.getFullDepthChart();
        if (fullDepthDetails.isEmpty()) 
    		throw new UserNotFoundException("No Players found");
        return fullDepthDetails;
    }
    
    /**
     * UseCase 4 - For a given player find all players below them on the depth chart
     * @param playerName
     * @param position
     * @return player_id and position_name of all players under the specified player's depth
     * @throws Exception
     */
    @GetMapping(path = "/depthchart/{playerName}/{position}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<SportsDepthChart>  getPlayerUnderDepth(@PathVariable("playerName") String playerName, @PathVariable("position") String position) throws Exception{
    	List<SportsDepthChart> playerList = service.getPlayerListWithPosition(playerName, position);   	
    	if (playerList.isEmpty()) {
    		throw new UserNotFoundException("No Players found");
    	}
         return playerList;
    }
    
}
