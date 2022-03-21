package com.cricket.functions;

import com.cricket.model.Inning;
import com.cricket.util.CricketUtil;

import java.util.Collections;
import java.util.List;

public class Functions 
{
	public String processPowerPlay(String powerplay_return_type, Inning inning, int total_overs, int total_balls) {
		
		int cuEcoent_over = total_overs;
		if(total_balls > 0) cuEcoent_over = cuEcoent_over + 1;
		
		String return_pp_txt = "";
		
		switch (powerplay_return_type) {
		case CricketUtil.FULL:
			return_pp_txt = CricketUtil.POWERPLAY + " ";
			break;
		case CricketUtil.SHORT:
			return_pp_txt = "PP";
			break;
		}
		
		if(cuEcoent_over >= inning.getFirstPowerplayStartOver() || cuEcoent_over <= inning.getFirstPowerplayEndOver()) {
			return_pp_txt = return_pp_txt + CricketUtil.ONE;
		} else if(cuEcoent_over >= inning.getSecondPowerplayStartOver() || cuEcoent_over <= inning.getSecondPowerplayEndOver()) {
			return_pp_txt = return_pp_txt + CricketUtil.TWO;
		} else if(cuEcoent_over >= inning.getThirdPowerplayStartOver() || cuEcoent_over <= inning.getThirdPowerplayEndOver()) {
			return_pp_txt = return_pp_txt + CricketUtil.THREE;
		}
		
		return return_pp_txt;
	}
	
	public String getThisOver(String seperatorType, List<com.cricket.model.Event> events) {
		
		String this_over = "", this_ball_data = "";
		
		Collections.reverse(events);
		
		for(com.cricket.model.Event evnt : events) {
			System.out.println("evnt number = " + evnt.getEventNumber() + " evnt type = " + evnt.getEventType());
			this_ball_data = "";
			switch (evnt.getEventType()) {
			case CricketUtil.CHANGE_BOWLER: case CricketUtil.NEW_BATSMAN:
				break;
			case CricketUtil.DOT: case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: 
			case CricketUtil.FIVE: case CricketUtil.FOUR: case CricketUtil.SIX:
				this_ball_data = String.valueOf(evnt.getEventRuns());
				break;
			case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.BYE: case CricketUtil.LEG_BYE: case CricketUtil.PENALTY: 
				this_ball_data = String.valueOf(evnt.getEventRuns()) + evnt.getEventType();
				break;
			case CricketUtil.LOG_WICKET: 
				if(evnt.getEventRuns() > 0) {
					this_ball_data = String.valueOf(evnt.getEventRuns()) + "+" + evnt.getEventType();
				} else {
					this_ball_data = evnt.getEventType();
				}
				break;
			case CricketUtil.LOG_ANY_BALL: 
				if(evnt.getEventExtra() != null) {
					this_ball_data = evnt.getEventExtra();
				}
				if(evnt.getEventSubExtra() != null) {
					if(this_ball_data.isEmpty()) {
						this_ball_data = evnt.getEventSubExtra();
					} else {
						this_ball_data = this_ball_data + "+" + evnt.getEventSubExtra();
					}
					if(evnt.getEventExtraRuns() > 0) {
						if(this_ball_data.isEmpty()) {
							this_ball_data = String.valueOf(evnt.getEventExtraRuns());
						} else {
							this_ball_data = this_ball_data + String.valueOf(evnt.getEventExtraRuns());
						}
					}
				}
				if(evnt.getEventRuns() > 0) {
					if(this_ball_data.isEmpty()) {
						this_ball_data = String.valueOf(evnt.getEventRuns());
					} else {
						this_ball_data = this_ball_data + "+" + String.valueOf(evnt.getEventRuns());
					}
				}
				if(evnt.getEventHowOut() != null) {
					if(this_ball_data.isEmpty()) {
						this_ball_data = CricketUtil.WICKET;
					} else {
						this_ball_data = this_ball_data + "+" + CricketUtil.WICKET;
					}
				}
				break;
			}
			if(this_over.isEmpty()) {
				this_over = this_ball_data;
			} else {
				this_over = this_over + seperatorType + this_ball_data;
			}
		}
		
		this_over = this_over.replace(CricketUtil.WIDE, "wd");
		this_over = this_over.replace(CricketUtil.NO_BALL, "nb");
		this_over = this_over.replace(CricketUtil.BYE, "b");
		this_over = this_over.replace(CricketUtil.LEG_BYE, "lb");
		this_over = this_over.replace(CricketUtil.PENALTY, "pen");
		this_over = this_over.replace(CricketUtil.LOG_WICKET, "w");
		this_over = this_over.replace(CricketUtil.WICKET, "w");
		
		return this_over;
		
	}

	public String bowlerExtras(int Wides,int No_Balls) {
		
		return String.valueOf(Wides + No_Balls) ;
		
	}
	
	public String Economy(int Runs,String Overs) {
		
		String Eco ;
		if(Double.valueOf(Overs) == 0) {
			Eco = "0.00";
		}
		else if(Runs < 0) {
			Eco = "-";
		}
		else
			Eco = String.valueOf(String.format("%.2f",(Double.valueOf(Runs)/Double.valueOf(Overs))));
		
		return Eco;
	}
	
	public String OverBalls(int Overs,int Balls) {
		
		String To = null ;
		int TotalBalls=0, WholeOv, OddBalls;
		
		TotalBalls = 6 * Overs + Balls ;
		if(TotalBalls > 0) {
			WholeOv = ((TotalBalls)/6);
			OddBalls = (TotalBalls - 6 * (WholeOv));
			
				if(OddBalls == 0) {
					To = String.valueOf(WholeOv);
				}
				else {
					To = String.valueOf(WholeOv)+"."+String.valueOf(OddBalls);
				}
		}
		
		return To;
		
	}
}
 
