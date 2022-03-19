package com.cricket.functions;

import com.cricket.model.Inning;
import com.cricket.util.CricketUtil;
import com.cricket.model.BowlingCard;

public class Functions 
{
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
	public String bowlerExtras(BowlingCard boc) {
		
		return String.valueOf(boc.getWides()) + String.valueOf(boc.getNoBalls());
		
	}
}
