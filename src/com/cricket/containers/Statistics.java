package com.cricket.containers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.dom4j.io.STAXEventReader;

import com.cricket.model.StatsType;

@Entity
@Table(name="Statistics")
public class Statistics
{
  @Id
  @Column(name="STATISTICSID")
  private int statistics_id;
  @Column(name="PLAYERID")
  private int player_id;
  @Column(name="STATSTYPEID")
  private int stats_type_id;
  @Transient
  private StatsType stats_type;
  @Column(name="MATCHES")
  private int matches;
  
  @Column(name="INNINGS")
  private int innings;
  @Column(name="NOTOUT")
  private int not_out;
  @Column(name="RUNS")
  private int runs;
  @Column(name="BALLSFACED")
  private int balls_faced;
  
  @Column(name="BESTSCORE")
  private String best_score;
  @Column(name="BESTSCOREAGAINST")
  private String best_score_against;
  @Column(name="BESTSCOREVENUE")
  private String best_score_venue;
  
  @Column(name="100s")
  private int s_100;
  @Column(name="50s")
  private int s_50;
  @Column(name="BALLSBOWLED")
  private int balls_bowled;
  @Column(name="RUNSCONCEDED")
  private int runs_conceded;
  @Column(name="WICKETS")
  private int wickets;
  
  @Column(name="BESTFIGURES")
  private String best_figures;
  @Column(name="BESTFIGURESAGAINST")
  private String best_figures_against;
  @Column(name="BESTFIGURESVENUE")
  private String best_figures_venue;
  
  
  public int getStatistics_id()
  {
    return statistics_id;
  }
  
  public void setStatistics_id(int statistics_id)
  {
    this.statistics_id = statistics_id;
  }
  
  public int getPlayer_id()
  {
    return player_id;
  }
  
  public void setPlayer_id(int player_id)
  {
    this.player_id = player_id;
  }
  
  public int getStats_type_id()
  {
    return stats_type_id;
  }
  
  public void setStats_type_id(int stats_type_id)
  {
    this.stats_type_id = stats_type_id;
  }
  
  public StatsType getStats_type()
  {
    return stats_type;
  }
  
  public void setStats_type(StatsType stats_type)
  {
    this.stats_type = stats_type;
  }
  
  public int getMatches()
  {
    return matches;
  }
  
  public void setMatches(int matches)
  {
    this.matches = matches;
  }

public int getInnings() {
	return innings;
}

public void setInnings(int innings) {
	this.innings = innings;
}

public int getNot_out() {
	return not_out;
}

public void setNot_out(int not_out) {
	this.not_out = not_out;
}

public int getRuns() {
	return runs;
}

public void setRuns(int runs) {
	this.runs = runs;
}

public int getBalls_faced() {
	return balls_faced;
}

public void setBalls_faced(int balls_faced) {
	this.balls_faced = balls_faced;
}

public String getBest_score() {
	return best_score;
}

public void setBest_score(String best_score) {
	this.best_score = best_score;
}

public String getBest_score_against() {
	return best_score_against;
}

public void setBest_score_against(String best_score_against) {
	this.best_score_against = best_score_against;
}

public String getBest_score_venue() {
	return best_score_venue;
}

public void setBest_score_venue(String best_score_venue) {
	this.best_score_venue = best_score_venue;
}

public int getS_100() {
	return s_100;
}

public void setS_100(int s_100) {
	this.s_100 = s_100;
}

public int getS_50() {
	return s_50;
}

public void setS_50(int s_50) {
	this.s_50 = s_50;
}

public int getBalls_bowled() {
	return balls_bowled;
}

public void setBalls_bowled(int balls_bowled) {
	this.balls_bowled = balls_bowled;
}

public int getRuns_conceded() {
	return runs_conceded;
}

public void setRuns_conceded(int runs_conceded) {
	this.runs_conceded = runs_conceded;
}

public int getWickets() {
	return wickets;
}

public void setWickets(int wickets) {
	this.wickets = wickets;
}

public String getBest_figures() {
	return best_figures;
}

public void setBest_figures(String best_figures) {
	this.best_figures = best_figures;
}

public String getBest_figures_against() {
	return best_figures_against;
}

public void setBest_figures_against(String best_figures_against) {
	this.best_figures_against = best_figures_against;
}

public String getBest_figures_venue() {
	return best_figures_venue;
}

public void setBest_figures_venue(String best_figures_venue) {
	this.best_figures_venue = best_figures_venue;
}

}