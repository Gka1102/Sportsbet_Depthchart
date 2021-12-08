package com.sports.depthchart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NonNull;

@Data
@Entity
@Table(name = "sports_depth_chart", schema = "public")
public class SportsDepthChart {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "sports_depth_id")
	@JsonIgnore
	private int sportsDepthId;
	@Column(name = "sports_name", nullable = false)
	@NonNull
	@JsonIgnore
    private String sportsName;
	@Column(name = "player_id", nullable = false)
	private int playerId;
	@Column(name = "player_name", nullable = false)
	@ColumnTransformer(read = "UPPER(player_name)")
    private String playerName;
	@Column(name = "position_name", nullable = false)
    private String positionName;   
	@Column(name = "position_depth")
    private Integer positionDepth;
	public SportsDepthChart() {
		super();
	}
	
	public SportsDepthChart(int sportsDepthId, String sportsName, int playerId, String playerName,
			String positionName, Integer positionDepth) {
		super();
		this.sportsDepthId = sportsDepthId;
		this.sportsName = sportsName;
		this.playerId = playerId;
		this.playerName = playerName;
		this.positionName = positionName;
		this.positionDepth = positionDepth;
	}

	public int getSportsDepthId() {
		return sportsDepthId;
	}
	public void setSportsDepthId(int sportsDepthId) {
		this.sportsDepthId = sportsDepthId;
	}
	public String getSportsName() {
		return sportsName;
	}
	public void setSportsName(String sportsName) {
		this.sportsName = sportsName.toUpperCase();
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName.toUpperCase();
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName.toUpperCase();
	}
	public Integer getPositionDepth() {
		return positionDepth;
	}
	public void setPositionDepth(Integer positionDepth) {
		this.positionDepth = positionDepth;
	}
	@Override
	public String toString() {
		return "SportsDepthChart [sportsDepthId=" + sportsDepthId + ", sportsName=" + sportsName + ", playerId="
				+ playerId + ", playerName=" + playerName + ", positionName=" + positionName + ", positionDepth="
				+ positionDepth + "]";
	}
    
    
    
    
    
}
