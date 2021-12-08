package com.sports.depthchart.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sports.depthchart.model.SportsDepthChart;

@Repository
public interface SportsDepthChartRepository extends JpaRepository<SportsDepthChart, Long>{
	//Get list of all players
	@Query("select sd from SportsDepthChart sd order by positionDepth,positionName")   
	List<SportsDepthChart> getAllPlayersList();
	
	//Get existing players for a given position
	@Query("select sd from SportsDepthChart sd where positionName = UPPER(?1) and  playerName = UPPER(?2)")   
	SportsDepthChart getExistingPlayerSamePosition(String positionName, String playerName);
	
	//For a given player find all players below them on the depth chart
	@Query("select sd from SportsDepthChart sd where positionName = UPPER(?1) and  positionDepth >= (select positionDepth from SportsDepthChart where playerName = UPPER(?2) and positionName = ?1) and playerName != UPPER(?2) order by positionDepth")   
	List<SportsDepthChart> getPlayerListWithPos(String positionName, String playerName);
	
	//Get existing players list for same position
	@Query("select sd from SportsDepthChart sd where positionName = UPPER(?1) and  positionDepth >= ?2 order by positionDepth")   
	List<SportsDepthChart> getExistingPlayersListForSamePosition(String positionName,int positionDepth);
	
	//Get existing players list for same position
	@Query("select playerId from SportsDepthChart sd where playerName = UPPER(?1)")   
	Long getExistingPlayer(String playerName);
	
	//Get max positionDepth for a given position
	@Query("select max(positionDepth) from SportsDepthChart sd where positionName = UPPER(?1)")   
	Integer getMaxPositionDepth(String positionName); 
	
	//Remove a player from depthchart for a given position
	@Transactional
	@Modifying
	@Query("delete from SportsDepthChart where positionName = UPPER(?1) and playerName = UPPER(?2)")
	Integer removePlayerFromDepthChart(String positionName, String playerName);
	
	//Add new player to the depthchart
	@Transactional
	@Modifying
	@Query(value="insert into sports_depth_chart(player_id,position_name, player_name, position_depth,sports_name) values(?1,?2,?3,?4,?5)",nativeQuery = true)
	Integer createNewPlayer(Long playerId,String positionName, String playerName, int positionDepth,String sportsName);
	
	//Update positionDepth of players with same positionDepth so last player entered gets priority
	@Transactional
	@Modifying
	@Query(value="Update sports_depth_chart set position_depth = position_depth+1 where  player_id = ?1 and position_name = ?2",nativeQuery = true)
	Integer UpdateExistingPlayers(int playerId,String positionName);
		
}
