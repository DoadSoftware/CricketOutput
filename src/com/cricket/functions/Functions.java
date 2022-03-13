package com.cricket.functions;

import java.util.ArrayList;
import java.util.List;

import com.cricket.containers.BowlingCardFF;
import com.cricket.containers.Bug;
import com.cricket.containers.ScorecardFF;
import com.cricket.model.BattingCard;
import com.cricket.model.Inning;
import com.cricket.model.Match;
import com.cricket.util.CricketUtil;

public class Functions 
{
	public static ScorecardFF scorecardToProcess(String whichBroadcaster, String valueToProcess, Match match)
	{
		ScorecardFF scorecard = new ScorecardFF();
		
		switch (whichBroadcaster.toUpperCase()) {
		case CricketUtil.DOAD:
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == Integer.valueOf(valueToProcess)) {
					scorecard.setInning(inn);
					if(inn.getBattingTeamId() == match.getHomeTeamId()) {
						scorecard.setHeader_text(match.getHomeTeam().getFullname());
						scorecard.setSub_header_text(match.getAwayTeam().getFullname());
					} 
					else if(inn.getBattingTeamId() == match.getAwayTeamId()) {
						scorecard.setSub_header_text(match.getAwayTeam().getFullname());
						scorecard.setHeader_text(match.getHomeTeam().getFullname());
					}
//					return new Scorecard(inn);
				}
			}
			break;
		}
		return scorecard;
	}

	public static BowlingCardFF bowlingcardToProcess(String whichBroadcaster, String valueToProcess, Match match)
	{
		BowlingCardFF bowlingCard = new BowlingCardFF();
		
		switch (whichBroadcaster.toUpperCase()) {
		case CricketUtil.DOAD:
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == Integer.valueOf(valueToProcess)) {
					bowlingCard.setInning(inn);
					if(inn.getBowlingTeamId() == match.getHomeTeamId()) {
						bowlingCard.setHeader_text(match.getHomeTeam().getFullname());
						bowlingCard.setSub_header_text(match.getAwayTeam().getFullname());
					} 
					if(inn.getBowlingTeamId() == match.getAwayTeamId()) {
						bowlingCard.setSub_header_text(match.getAwayTeam().getFullname());
						bowlingCard.setHeader_text(match.getHomeTeam().getFullname());
					}
				}
			}
			break;
		}
		return bowlingCard;
	}

	public Bug bugToProcess(String whichBroadcaster, String valueToProcess, Match match) 
	{
		Bug bug = new Bug();
		int inning_number = 0, player_id = 0;
		String stats_to_proces = "";
		List<String> stats = new ArrayList<String>();
		
		switch (whichBroadcaster) {
		case CricketUtil.DOAD:
			if (valueToProcess.contains("|")) {
				for(String val : valueToProcess.split("|")) {
					if (val.contains(":")) {
						switch (val.split(":")[0].toUpperCase()) {
						case "STATS":
							stats_to_proces = val.split(":")[1];
							break;
						case "PLAYER":
							player_id = Integer.valueOf(val.split(":")[1]);
							break;
						case "INNING":
							inning_number = Integer.valueOf(val.split(":")[1]);
							break;
						}
					}
				}
			}
			break;
		}
		
		switch (stats_to_proces.toUpperCase()) {
		case CricketUtil.BATSMAN + CricketUtil.STATS:
			bug.setStat_option(stats_to_proces);
			for(Inning inn : match.getInning()) {
				if(inn.getInningNumber() == inning_number) {
					for(BattingCard bc : inn.getBattingCard()) {
						if (bc.getPlayerId() == player_id) {
							bug.setHeader_text(bc.getPlayer().getFull_name());
							bug.setSubheader_text("FOURS: " + bc.getFours() + ", SIXES: " + bc.getSixes());
							stats.add(String.valueOf(bc.getRuns()) + " (" + String.valueOf(bc.getBalls() + ")"));
							bug.setStats_text(stats);
						}
					}
				}
			}
			break;
		}
		return bug;
	}

	public String processPowerPlay(String powerplay_return_type, Inning inning, int total_overs, int total_balls) {
		
		int current_over = total_overs;
		if(total_balls > 0) current_over = current_over + 1;
		
		String return_pp_txt = "";
		
		switch (powerplay_return_type) {
		case CricketUtil.FULL:
			return_pp_txt = CricketUtil.POWERPLAY + " ";
			break;
		case CricketUtil.SHORT:
			return_pp_txt = "PP";
			break;
		}
		
		if(current_over >= inning.getFirstPowerplayStartOver() || current_over <= inning.getFirstPowerplayEndOver()) {
			return_pp_txt = return_pp_txt + CricketUtil.ONE;
		} else if(current_over >= inning.getSecondPowerplayStartOver() || current_over <= inning.getSecondPowerplayEndOver()) {
			return_pp_txt = return_pp_txt + CricketUtil.TWO;
		} else if(current_over >= inning.getThirdPowerplayStartOver() || current_over <= inning.getThirdPowerplayEndOver()) {
			return_pp_txt = return_pp_txt + CricketUtil.THREE;
		}
		
		return return_pp_txt;
	}
	
	public final static List<String> processHowOut(BattingCard bc) 
	{
		List<String> how_out_txt = new ArrayList<String>();
		switch (bc.getStatus().toUpperCase()) {
		case CricketUtil.NOT_OUT:
			how_out_txt.add(CricketUtil.NOT_OUT.toLowerCase());
			break;
		default:
			switch (bc.getHowOut().toUpperCase()) {
			case CricketUtil.CAUGHT_AND_BOWLED:
				how_out_txt.add("c & b " + bc.getHowOutBowler().getSurname());
				break;
			case CricketUtil.CAUGHT: case CricketUtil.MANKAD: case CricketUtil.RUN_OUT:
				switch (bc.getHowOut().toUpperCase()) {
				case CricketUtil.CAUGHT: 
					if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES))
						how_out_txt.add("c" + bc.getHowOutFielder().getSurname() + " (SUB)");
					else
						how_out_txt.add("c" + bc.getHowOutFielder().getSurname());
					how_out_txt.add(" b " + bc.getHowOutBowler().getSurname());
					break;
				case CricketUtil.RUN_OUT:
					if(bc.getWasHowOutFielderSubstitute() != null && bc.getWasHowOutFielderSubstitute().equalsIgnoreCase(CricketUtil.YES))
						how_out_txt.add("run out " + bc.getHowOutFielder().getSurname() + " (SUB)");
					else
						how_out_txt.add("run out " + bc.getHowOutFielder().getSurname());
					break;
				case CricketUtil.MANKAD:
					how_out_txt.add("run out " + bc.getHowOutBowler().getSurname());
					break;
				}
				break;
			case CricketUtil.BOWLED:
				how_out_txt.add("b " + bc.getHowOutBowler().getSurname());
				break;
			case CricketUtil.STUMPED:
				how_out_txt.add("st " + bc.getHowOutFielder().getSurname() + " b " + bc.getHowOutBowler().getSurname());
				break;
			case CricketUtil.LBW:
				how_out_txt.add("lbw b " + bc.getHowOutBowler().getSurname());
				break;
			case CricketUtil.HIT_WICKET:
				how_out_txt.add("hit wicket b " + bc.getHowOutBowler().getSurname());
				break;
			case CricketUtil.HANDLED_THE_BALL:
				how_out_txt.add("handled the ball");
				break;
			case CricketUtil.HIT_BALL_TWICE:
				how_out_txt.add("hit the ball twice");
				break;
			case CricketUtil.OBSTRUCTING_FIELDER:
				how_out_txt.add("obstructing the field");
				break;
			case CricketUtil.TIME_OUT:
				how_out_txt.add("timed out");
				break;
			case CricketUtil.RETIRED_HURT:
				how_out_txt.add("retired hurt");
				break;
			case CricketUtil.RETIRED_ABSENT:
				how_out_txt.add("absent hurt");
				break;
			}
			break;
		}
		return how_out_txt;
	}
}
