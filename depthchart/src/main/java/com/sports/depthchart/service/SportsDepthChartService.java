package com.sports.depthchart.service;

import java.util.*;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.sports.depthchart.exception.*;
import com.sports.depthchart.model.*;
import com.sports.depthchart.repo.*;

import lombok.extern.slf4j.*;

@Slf4j
@Component
public class SportsDepthChartService {
	 	@Autowired
	    SportsDepthChartRepository depthChartRepo;
	    SportsPositionReference sport = new SportsPositionReference();
	 	private static final org.jboss.logging.Logger log = LoggerFactory
	            .logger(SportsDepthChartService.class);
	 	
 	/**
     * Create player in SPORT_POSITION_DEPTH table
     * @param playerName
     * @param position
     * @param positionDepth
     * @return 
     * @throws DataAccessException
     */
	public Integer createPlayerInDepthChart(SportsDepthChart requestDepthChartRecord){
		int depthPosition =-1;		
		int maxDepth;
		long playerId = -1;

		String requestplayer = requestDepthChartRecord.getPlayerName();
		String positionName = requestDepthChartRecord.getPositionName();	
		
		//Get sportsName from SportsPositionReference
		String sportsName = getSportPositionDetails(positionName).getSportsName();
		
		if(requestDepthChartRecord.getPositionDepth()!=null)
			depthPosition = requestDepthChartRecord.getPositionDepth();
		
		Long existingPlayerId;
		
		// get playerId of an existing player
		if(depthChartRepo.getExistingPlayer(requestplayer)!=null)
		playerId = depthChartRepo.getExistingPlayer(requestplayer);

		//Get existing player for a given position
		SportsDepthChart existingSamePlayerPosRecord = depthChartRepo.getExistingPlayerSamePosition(positionName,requestplayer);
		
		//Get existing players list for given position
		List<SportsDepthChart> list = depthChartRepo.getExistingPlayersListForSamePosition( positionName,depthPosition);
		
		//Update positionDepth for players under a given player
		if(depthPosition >-1) {
			if(list!=null && list.size()>0){			
				Iterator<SportsDepthChart> sdIterator = list.iterator();
			    while (sdIterator.hasNext()) {
			        SportsDepthChart playerRecord = sdIterator.next();
			        depthChartRepo.UpdateExistingPlayers(playerRecord.getPlayerId(),playerRecord.getPositionName());
			    }
			}
		}
		
		//For a given position find max depth of existing players
		if(existingSamePlayerPosRecord!=null && existingSamePlayerPosRecord.getPositionDepth() != null) {
			maxDepth = depthChartRepo.getMaxPositionDepth(positionName);
		}
		else if(requestDepthChartRecord.getPositionDepth()==null){
			maxDepth = -1;
			if(depthChartRepo.getMaxPositionDepth(positionName) != null) {
			maxDepth = depthChartRepo.getMaxPositionDepth(positionName);
			depthPosition = maxDepth+1;
			}
		}
		else
			maxDepth = -1;
		
		if(requestDepthChartRecord.getPositionDepth() == null && maxDepth >=0) {
			//For a given player with no positionDepth add player to end of depth chart
			depthPosition = maxDepth+1;	
		} else if(requestDepthChartRecord.getPositionDepth() != null){
			//For a given player with positionDepth greater than existing
			depthPosition = requestDepthChartRecord.getPositionDepth();
		} else {
			//player to enter a position with no existing players
			depthPosition = 0;
		}
		
		//Existing player for a position when tried to re-enter the same position
		if(existingSamePlayerPosRecord != null && (requestplayer.equalsIgnoreCase(existingSamePlayerPosRecord.getPlayerName()) 
				&& positionName.equalsIgnoreCase(existingSamePlayerPosRecord.getPositionName()))) {	
			throw new GenericExceptions("Player "+ existingSamePlayerPosRecord.getPlayerName()+" already exists at position "+existingSamePlayerPosRecord.getPositionName());				
		}
		if (playerId == -1){
			//New player not in the existing depthchart 
				existingPlayerId = depthChartRepo.findAll().stream().mapToLong(SportsDepthChart::getPlayerId).max().orElse(0);
				Integer playerCreated = depthChartRepo.createNewPlayer(existingPlayerId+1,positionName, requestplayer, depthPosition,sportsName);		
				if(playerCreated == 1) {
					return playerCreated;
				}
				else {		
					throw new GenericExceptions("New Player couldn't be added");	
				}			
		} else {
			//Existing player enters position of not their own
			Integer playerCreated = depthChartRepo.createNewPlayer(playerId,positionName, requestplayer, depthPosition,sportsName);		
			if(playerCreated == 1) {
				return playerCreated;
			}
			else {				
				log.debug("Player couldn't be added for a given position");
				throw new GenericExceptions("Player couldn't be added for a given position");	
			}
		}
		
   } 

 	/**
     * List all players in SPORT_POSITION_DEPTH table
     * @return 
     * @throws DataAccessException
     */
	public List<SportsDepthChart> getFullDepthChart(){
        return depthChartRepo.getAllPlayersList();
   }
	
	/**
     * Remove the player in SPORT_POSITION_DEPTH table for particular position and player
     * @param playerName
     * @param position
     * @throws DataAccessException
     */
    public Integer deletePlayerDepth(String playerName , String position) throws DataAccessException {
    	Integer playerdeleted = depthChartRepo.removePlayerFromDepthChart(position, playerName);
    	log.info("Player "+ playerName+ " removed from position "+position);
    	return playerdeleted;
    }
	
    /**
     * For a given player find of all players under the specified player's depth
     * @param playerName
     * @param position
     * @return
     * @throws DataAccessException
     */
	public List<SportsDepthChart>  getPlayerListWithPosition(String playerName, String position) throws DataAccessException{
        List<SportsDepthChart> playerList = depthChartRepo.getPlayerListWithPos(position, playerName);
      return playerList;
   }
	
	 /**
     * SportsPositionReference is static reference data for Sports, positions of each sport
     * @param position
     * @return SportsDepthChart
     */
	public SportsDepthChart getSportPositionDetails(String positionName)
 	{
 		SportsDepthChart sportPosDetails = new SportsDepthChart();
 		EnumMap sportsMap = sport.getSportPosMap();
 		Iterator it = sportsMap.keySet().iterator();
 		while (it.hasNext()){
 			Enum sportsName = (SportsPositionReference.SportsEnum)it.next();
 			Set positionsList = (HashSet)sportsMap.get(sportsName);
 			if (positionsList.contains(positionName)) {
 				sportPosDetails.setSportsName(sportsName.name());
 				sportPosDetails.setPositionName(positionName);
 			}
 		}
 		return sportPosDetails;
 	}	
}
