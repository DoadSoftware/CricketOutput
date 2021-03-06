package com.cricket.broadcaster;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.Event;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Statistics;
import com.cricket.model.Inning;
import com.cricket.model.Match;
import com.cricket.model.NameSuper;
import com.cricket.model.FallOfWicket;
import com.cricket.model.InfobarStats;
import com.cricket.containers.Scene;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;

public class Doad extends Scene{

	private String status;
	private String slashOrDash = "-";
	private String logo_path = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\EVEREST_APL2022\\Logos\\";
	private String photo_path = "D:\\DOAD_In_House_Everest\\Everest_Cricket\\EVEREST_APL2022\\Photos\\MEDIUM\\";
	
	public Doad() {
		super();
	}

	public Doad(String scene_path) {
		super(scene_path);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void populateScorecard(PrintWriter print_writer, String viz_scene, int whichInning, Match match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Scorecard's inning is null";
			} else {

				int row_id = 0, omo_num = 0;
				String cont_name= "";
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBattingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
									match.getHomeTeam().getShortname() + CricketUtil.PNG_EXTENSION + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$MaxSize$BatHeader*GEOM*TEXT SET " + match.getHomeTeam().getFullname().toUpperCase() + "\0");
							} else {
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
									match.getAwayTeam().getShortname() + CricketUtil.PNG_EXTENSION + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$MaxSize$BatHeader*GEOM*TEXT SET " + match.getAwayTeam().getFullname().toUpperCase() + "\0");
							//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						}

						Collections.sort(inn.getBattingCard());
						for (BattingCard bc : inn.getBattingCard()) {
							
							row_id = row_id + 1;
							
							switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.STILL_TO_BAT:
							if(bc.getHowOut() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 0 \0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$LeftPlayerName$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
								}else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo*FUNCTION*Omo*vis_con SET 1 \0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOut() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo$Dehighlight$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								}
								break;
								default:

								switch (bc.getStatus().toUpperCase()) {
								case CricketUtil.OUT:
									omo_num = 1;
									cont_name = "$Dehighlight";
									break;
								case CricketUtil.NOT_OUT:
									omo_num = 2;
									cont_name = "$Highlight";
									break;
								}
								
								if(bc.getHowOut() == null) {
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$RowAll*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
								}
								else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$RowAll*FUNCTION*Omo*vis_con SET " + inn.getBattingCard().size() + " \0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row"+row_id+"$RowOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
									if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
											print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
										}else {
											print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
											print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
										}
								}
								else {
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$WicketPlayerName*GEOM*TEXT SET " + bc.getStatus() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Row" + row_id + "$RowOmo" + cont_name + "$BallPlayerName*GEOM*TEXT SET " + " " + "\0");		
								}
							}
						}
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$ExtrasGrp$Extras*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$OversGrp$Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
						if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
						} else {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
						}
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Battingcard.png In 1.400 BattingCardIn 2.600 \0");
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Scorecard's inning is null";
			} else {
				int row_id = 0 ;
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + inn.getBatting_team().getFullname().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader01 " + "v " + inn.getBowling_team().getFullname() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$Top$Logo$TeamLogo*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");						
						
						//inn.getBattingCard().sort();
						Collections.sort(inn.getBattingCard());
						for (BattingCard bc : inn.getBattingCard()) {
						
							row_id = row_id + 1;

							switch (bc.getStatus().toUpperCase()) {
	
							case CricketUtil.OUT:
								
								if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.CONCUSSED)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 0;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$HowOut_GRP*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Runs*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Balls*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" +row_id + " " +bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vBatsman12Visibility " + "1" + ";");
								}
								else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_OUT)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 0;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$HowOut_GRP*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Runs*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Balls*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" +row_id + " " +bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								}
								else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 0;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$HowOut_GRP*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Runs*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Balls*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" +row_id + " " +bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + bc.getHowOutPartTwo() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
									
								}
							break;
	
							case CricketUtil.NOT_OUT:
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" +row_id + " " +bc.getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "2" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getStatus() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								
							break;
							
							case CricketUtil.STILL_TO_BAT:
							
								if(bc.getHowOut() == null) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_id + " " + bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "0" + ";");
								}
								else if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 0;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$HowOut_GRP*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Runs*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Balls*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" +row_id + " " +bc.getPlayer().getTicker_name() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOut() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								}
								
								break;
						}
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + inn.getTotalExtras() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers "+ CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunRate " + inn.getRunRate() + ";");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + ";");
				}
			}
		}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				//print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/Battingcard.png In 1.400 BattingCardIn 2.600 \0");
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene,boolean is_this_updating, int whichInning,   Match match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Bowlingcard's inning is null";
			} else {
			//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
				//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BallHeader$MatchId$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
				
				int row_id = 0; 
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
										match.getHomeTeam().getShortname() + CricketUtil.PNG_EXTENSION + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$MaxSize$BallHeader*GEOM*TEXT SET " + match.getHomeTeam().getFullname().toUpperCase() + "\0");
								//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
							} else {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
										match.getAwayTeam().getShortname() + CricketUtil.PNG_EXTENSION + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$MaxSize$BallHeader*GEOM*TEXT SET " + match.getAwayTeam().getFullname().toUpperCase() + "\0");
								//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
							}
						
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo*FUNCTION*Omo*vis_con SET " + inn.getBowlingCard().size()+"\0");

						for (BowlingCard boc : inn.getBowlingCard()) {
							row_id = row_id + 1;
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$BowlerName*GEOM*TEXT SET " + boc.getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$OversValue*GEOM*TEXT SET " +  CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls())  + "\0");
							if(match.getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getMatchType().equalsIgnoreCase(CricketUtil.D10)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Row0$Highlight$ScoreGrp$MaidensHead*GEOM*TEXT SET " + "DOTS" +"\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + boc.getDots() +"\0");
							}else {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Row0$Highlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + "MAIDENS" +"\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$MaidensValue*GEOM*TEXT SET " + boc.getMaidens() +"\0");
							}
							
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$RunsValue*GEOM*TEXT SET " +  boc.getRuns()+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$WicketsValue*GEOM*TEXT SET " + boc.getWickets() +"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$BallOmo$Row" + row_id + "$Dehighlight$ScoreGrp$EconomyValue*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
							
						}
						if(inn.getBowlingCard().size()<=7) {
							if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
								print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp*ACTIVE SET 0"+"\0");
							}
							else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
								for(FallOfWicket fow : inn.getFallsOfWickets()) {								
									if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp*ACTIVE SET 1" + "\0");
										for(int fow_id=0;fow_id<=10;fow_id++) {
											if(fow_id <= inn.getFallsOfWickets().size()) {
												print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp$Row10$ScoreGrp$" + fow.getFowNumber() + "*GEOM*TEXT SET "+ fow.getFowRuns()+"\0");
											}else {
												print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp$Row9$ScoreGrp$" + fow_id + "*ACTIVE SET 0" + "\0");
												print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$FowGrp$Row10$ScoreGrp$" + fow_id + "*ACTIVE SET 0" + "\0");
											}
										}	
									}		
								}
							}
						}
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$OversGrp$Overs*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$ExtrasGrp$Extras*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$BowlingCard$Info$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
						}
					}
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Bowlingcard.png In 1.400 BowlingCardIn 2.500 \0");
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Bowlingcard's inning is null";
			} else {
				int row_id = 0, row = 1, max_Strap = 9; 
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
						if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getHomeTeam().getFullname().toUpperCase() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader01 " + "v " + match.getAwayTeam().getFullname() + ";");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getAwayTeam().getFullname().toUpperCase() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader01 " + "v " + match.getHomeTeam().getFullname() + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$Top$Logo$TeamLogo*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBowling_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						
						for (BowlingCard boc : inn.getBowlingCard()) {
							row_id = row_id + 1;
							row = row + 1;

							print_writer.println("LAYER1*EVEREST*TREEVIEW*"+ row + "*CONTAINER SET ACTIVE 1;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_id + " "  + boc.getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "A " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) +";");
							if(match.getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getMatchType().equalsIgnoreCase(CricketUtil.D10) || match.getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHeadB " +"DOTS" +";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "B " + boc.getDots() +";");
							}
							else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "B " + boc.getMaidens() +";");
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "C " + boc.getRuns() +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "D " + boc.getWickets() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "E " + boc.getEconomyRate() + ";");
							
						}
						for(int j = row + 1; j <= max_Strap; j++) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*"+ j + "*CONTAINER SET ACTIVE 0;");
						}
						row_id= 0 ;
						if(inn.getBowlingCard().size()<=8) {
							if(is_this_updating == false) {
								if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
									//System.out.println(inn.getFallsOfWickets());
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$DataAll$BAtting$Bowling_Card$10*CONTAINER SET ACTIVE 0;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$DataAll$BAtting$Bowling_Card$11*CONTAINER SET ACTIVE 0;");
								}
								else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
									for(FallOfWicket fow : inn.getFallsOfWickets()) {								
										if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
											row_id = row_id + 1;
											//System.out.println("row3="+row_id);
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$DataAll$BAtting$Bowling_Card$10*CONTAINER SET ACTIVE 1;");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$DataAll$BAtting$Bowling_Card$11*CONTAINER SET ACTIVE 1;");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vWKTS "+ fow.getFowNumber() + ";");
											print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tWKTRun0" + row_id + " "  + fow.getFowRuns() + ";");
										}		
									}
								}
							}
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tExtras " + inn.getTotalExtras() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRunRate "+ inn.getRunRate() + ";");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}
						else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + ";");
						}
					}
				}
				//print_writer.println("LAYER1*EVEREST*GLOBAL TEMPLATE_SAVE D:/MyFile.txt;");
				//print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}

	public void populatePartnership(PrintWriter print_writer, String viz_scene, int whichInning, Match match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			
			int row_id = 0, omo_num = 0,Top_Score = 50;
			float Mult = 300, ScaleFac1 = 0, ScaleFac2 = 0;
			String cont_name= "",Left_Batsman = "",Right_Batsman="";

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$PartHeader$MatchId$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {

					if (inn.getBattingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
					}
					
					for(int a = 1; a <= inn.getPartnerships().size(); a++){
						ScaleFac1=0;ScaleFac2=0;
						if(inn.getPartnerships().get(a-1).getFirstBatterRuns() > Top_Score) {
							Top_Score = inn.getPartnerships().get(a-1).getFirstBatterRuns();
						}
						if(inn.getPartnerships().get(a-1).getSecondBatterRuns() > Top_Score) {
							Top_Score = inn.getPartnerships().get(a-1).getSecondBatterRuns();
						}
					}

					for (Partnership ps : inn.getPartnerships()) {
						
						row_id = row_id + 1;
						Left_Batsman ="" ; Right_Batsman="";
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == ps.getFirstBatterNo()) {
								Left_Batsman = bc.getPlayer().getSurname();
							}
							else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
								Right_Batsman = bc.getPlayer().getSurname();
							}
						}
						
						if(inn.getPartnerships().size() >= 10) {
							if(ps.getPartnershipNumber()<=inn.getPartnerships().size()) {
								omo_num = 4;
								cont_name = "Highlight";
							}
						}
						else {
							if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
								omo_num = 4;
								cont_name = "Highlight";
							}
							else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
								omo_num = 3;
								cont_name = "Dehighlight";
							}
						}
						
						ScaleFac1 = ((ps.getFirstBatterRuns())*(Mult/Top_Score)) ;
						ScaleFac2 = ((ps.getSecondBatterRuns())*(Mult/Top_Score)) ;

						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET "+String.valueOf(omo_num)+ " \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$LeftPlayeName*GEOM*TEXT SET " + Left_Batsman + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$RightPlayeName*GEOM*TEXT SET " + Right_Batsman + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$BarGrp*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1 + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$BarGrp*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2 + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$ScoreGrp$PlayerRuns*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$ScoreGrp$PlayerBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
					}
					if(inn.getPartnerships().size() >= 10) {
						row_id = row_id + 1; 
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 0"+ " \0");
					}
					else {
						for (BattingCard bc : inn.getBattingCard()) {
							if(row_id < 11) {
								if(row_id == inn.getPartnerships().size()) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 1"+ " \0");
								}
								else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 2"+ " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$StiilToBatPlayerGrp$LeftPlayeName*GEOM*TEXT SET "+bc.getPlayer().getSurname()+" \0");
								}	
							}
							else break;
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$BottomInfoPosition$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$BottomInfoPosition$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
		}
	}
	
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Match Summary's inning is null";
			} else {
			int row_id = 0, max_Strap = 0, total_inn = 0;
				String teamname = "",teamname_logo=""; 
				for(Inning inn : match.getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				/*for(int i = 1; i <= 4 ; i++) {
					if(i == whichInning) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 1 \0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 0 \0");
					}
				}*/
				
				for(int i = 1; i <= whichInning ; i++) {

					if(i == 1) {
						row_id = 0;
						max_Strap = 4;
						
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row6*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row7*ACTIVE SET 0 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row8*ACTIVE SET 0 \0");
						
					} else {
						row_id = 4;
						max_Strap = 8;
						
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row6*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row7*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row8*ACTIVE SET 1 \0");
					}
					row_id = row_id + 1;
					
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$SummaryHeader*GEOM*TEXT SET " + match.getMatchIdent() + "\0");
					
					//Toss
					if(match.getTossWinningTeam() == match.getHomeTeamId()) { 
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row1$RowAni$Highlight$TOSS*GEOM*TEXT SET " + "TOSS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5$RowAni$Highlight$TOSS*ACTIVE SET 0 \0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row1$RowAni$Highlight$TOSS*GEOM*TEXT SET " + "TOSS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row5$RowAni$Highlight$TOSS*ACTIVE SET 0 \0");
					}
					
					if(match.getInning().get(i-1).getBattingTeamId() == match.getHomeTeamId()) {
						teamname = match.getHomeTeam().getFullname();
						teamname_logo  = match.getHomeTeam().getShortname();
					} else {
						teamname = match.getAwayTeam().getFullname();
						teamname_logo = match.getAwayTeam().getShortname();
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Rectangle*TEXTURE*IMAGE SET " + logo_path + 
							teamname_logo + CricketUtil.PNG_EXTENSION + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanName*GEOM*TEXT SET " + teamname.toUpperCase() + "\0");
					
					if(match.getInning().get(i-1).getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + match.getInning().get(i-1).getTotalRuns() + "\0");
					}else {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + match.getInning().get(i-1).getTotalRuns() + slashOrDash + String.valueOf(match.getInning().get(i-1).getTotalWickets()) + "\0");	
					}
					
					print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$Highlight$Overs*GEOM*TEXT SET " + 
							CricketFunctions.OverBalls(match.getInning().get(i-1).getTotalOvers(),match.getInning().get(i-1).getTotalBalls()) + "\0");
					
					if(match.getInning().get(i-1).getBattingCard() != null) {
						Collections.sort(match.getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
						
						for(BattingCard bc : match.getInning().get(i-1).getBattingCard()) {
							if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman*ACTIVE SET 1 \0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$BatsmanName*GEOM*TEXT SET " + bc.getPlayer().getSurname().toUpperCase() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$BatsmanScore*GEOM*TEXT SET " + bc.getRuns() + "\0");
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$NotOut*ACTIVE SET 1 \0");
								} else {
									print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$NotOut*ACTIVE SET 0 \0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Batsman$ScoreGrp$BatsmanBall*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
								
								if(i == 1 && row_id >= 4) {
									break;
								}else if(i == 2 && row_id >= 8) {
									break;
								}
							}
						}
					}

					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + j + "$RowAni$RowOmo$Dehighlight$Batsman*ACTIVE SET 0 \0");
					}
					
					if(i == 1) {
						row_id = 1;
					}
					else {
						row_id = 5;
					}

					if(match.getInning().get(i-1).getBowlingCard() != null) {
						
						Collections.sort(match.getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

						for(BowlingCard boc : match.getInning().get(i-1).getBowlingCard()) {
							
							row_id = row_id + 1;
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler*ACTIVE SET 1 \0");
							
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$BowlerName*GEOM*TEXT SET " + boc.getPlayer().getSurname().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$ScoreGrp$BowlerFigure*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row"+row_id+"$RowAni$RowOmo$Dehighlight$Bowler$ScoreGrp$BowlerOvers*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
							
							if(i == 1 && row_id >= 4) {
								break;
							}
							else if(i == 2 && row_id >= 8) {
								break;
							}
						}
					}
					
					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row" + j + "$RowAni$RowOmo$Dehighlight$Bowler*ACTIVE SET 0 \0");
					}
				}
				print_writer.println("-1 RENDERER*TREE*$Main$Summary$DataAll$Row9$RowAni$Highlight$BatsmanScore*GEOM*TEXT SET " + CricketFunctions.generateMatchSummaryStatus(whichInning, match, CricketUtil.FULL) + " \0");
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/matchsummary.png In 1.400 SummaryIn 2.400 \0");

				this.status = CricketUtil.SUCCESSFUL;	
			
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Match Summary's inning is null";
			} else {
				//String path = "D:\\Everest_Scenes\\Mumbai_Indians\\Textures and Images\\Logos\\" ;
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getTournament() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getMatchIdent() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tMatchName " + match.getHomeTeam().getShortname().toUpperCase() + " v " + match.getAwayTeam().getShortname().toUpperCase() + ";");
				
				int row_id = 0, row = 0, total_inn = 0, max_Strap = 0;
				String teamname = ""; 
				
				for(Inning inn : match.getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				for(int i = 1; i <= whichInning ; i++) {
					if(i == 1) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*SecondInnings*CONTAINER SET ACTIVE 0;");
					}
					else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*SecondInnings*CONTAINER SET ACTIVE 1;");
					}
				}
				
				for(int i = 1; i <= whichInning ; i++) {

					if(i == 1) {
						row = 0;
						row_id = 0;
						max_Strap = 3;
					} else {
						row = 1;
						row_id = 3;
						max_Strap = 6;
					}
					row = row + 1;
					if(match.getInning().get(i-1).getBattingTeamId() == match.getHomeTeamId()) {
						teamname = match.getHomeTeam().getFullname();	
					} else {
						teamname = match.getAwayTeam().getFullname();
					}
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName0"+ row +" "+ teamname + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOverNo0"+ row + " " + 
							CricketFunctions.OverBalls(match.getInning().get(i-1).getTotalOvers(),match.getInning().get(i-1).getTotalBalls()) + ";");
					
					if(match.getInning().get(i-1).getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore0"+ row + " " + match.getInning().get(i-1).getTotalRuns() + ";");
					}
					else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore0"+ row + " " + match.getInning().get(i-1).getTotalRuns() + slashOrDash + match.getInning().get(i-1).getTotalWickets() + ";");	
					}
					if(match.getInning().get(i-1).getBattingCard() != null) {
						//row_id = 0;
						Collections.sort(match.getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
						for(BattingCard bc : match.getInning().get(i-1).getBattingCard()) {
							if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
								row_id = row_id + 1;
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Batsman0"+ row_id + "*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName0"+ row_id + " " + bc.getPlayer().getFirstname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRuns0"+ row_id + " " + bc.getRuns() + ";");
								
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanNotOut0"+ row_id + " " + "*" + ";");
									
								} 
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBalls0"+ row_id + " " + bc.getBalls() + ";");
								
								if(i == 1 && row_id >= 3) {
									break;
								}else if(i == 4 && row_id >= 6) {
									break;
								}
							}
						}
					}

					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Batsman0"+ j + "*CONTAINER SET ACTIVE 0;");
					}
					
					if(i == 1) {
						row_id = 0;
					}
					else {
						row_id = 3;
					}

					if(match.getInning().get(i-1).getBowlingCard() != null) {
						//row_id = 0;
						Collections.sort(match.getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

						for(BowlingCard boc : match.getInning().get(i-1).getBowlingCard()) {
							
							row_id = row_id + 1;
							
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Bowler0"+ row_id + "*CONTAINER SET ACTIVE 1;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName0" + row_id + " " +  boc.getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerRuns0" + row_id + " " + boc.getWickets() + slashOrDash + boc.getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOver0" + row_id + " " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
							
							if(i == 1 && row_id >= 3) {
								break;
							}
							else if(i == 4 && row_id >= 6) {
								break;
							}
						}
					}
					
					for(int j = row_id + 1; j <= max_Strap; j++) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Bowler0"+ j + "*CONTAINER SET ACTIVE 0;");
					}
				}

				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + CricketFunctions.generateMatchSummaryStatus(whichInning, match, CricketUtil.FULL) + ";");
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			
			}
			break;
		}
		
	}

	public void populateBugDismissal(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName1*FUNCTION*ControlText*input SET " + bc.getPlayer().getFirstname() + "\0");

									//print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName1*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
									//print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName2*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name1$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name1$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/BugDismissal.png In 0.714 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getFirstname() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + bc.getHowOutText() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + bc.getRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + bc.getBalls() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "*" +"\0");
									}
									else {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() +"\0");
									}
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + "4s:" + bc.getFours()  + " 6s:"  + bc.getSixes() +"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Runs*GEOM*TEXT SET " + bc.getRuns() +"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Balls*GEOM*TEXT SET " + bc.getBalls() +"\0");
								}
							}
							break;
						case "BOWLER":
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*ACTIVE SET 1 \0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*GEOM*TEXT SET " + "ECON: " + boc.getEconomyRate() + " " + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + " " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info04*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Bug.png In 0.714 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getFirstname() + "*" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getFirstname() + ";");
									}
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "4s:" + bc.getFours()  + " 6s:"  + bc.getSixes() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + bc.getRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + bc.getBalls() + ";");
								}
							}
							break;
						case "BOWLER":
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + boc.getPlayer().getFull_name() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "ECON " + boc.getEconomyRate() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + boc.getWickets() + slashOrDash + boc.getRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	
	public void populateBugBowler(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Bug's inning is null";
			} else {
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case "BOWLER":
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFirstname() +"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Runs*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() +"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) +"\0");
								}
							}
							break;
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/Bug.png In 0.714 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION  + ";");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
								if (bc.getHowOutText().trim().equalsIgnoreCase("")){
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + " " + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatValue01*GEOM*TEXT SET " + bc.getFours() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatHead02*GEOM*TEXT SET " + "6's" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatValue02*GEOM*TEXT SET " + bc.getSixes() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/HowOut.png In 1.000 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION  + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + bc.getBalls() + ";");
								if (bc.getHowOutPartOne().trim().equalsIgnoreCase("")){
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartTwo() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + bc.getHowOutPartTwo() + ";");
								}
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
							}
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	public void populateHowoutWithoutFielder(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION  + ";");
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
								if (bc.getHowOutText().trim().equalsIgnoreCase("")){
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + " " + "\0");
								}else {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HowOut*GEOM*TEXT SET " + " " + "\0");
								}
								
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Balls*GEOM*TEXT SET " + bc.getBalls() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$StatValue01*GEOM*TEXT SET " + bc.getFours() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatHead02*GEOM*TEXT SET " + "6's" + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$StatValue02*GEOM*TEXT SET " + bc.getSixes() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$StatValue03*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
							}
						}
					}
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/HowOut.png In 1.000 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: HowOut's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION  + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + bc.getBalls() + ";");
								if (bc.getHowOutPartOne().trim().equalsIgnoreCase("")){
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartTwo() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + " " + ";");
								}else {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + bc.getHowOutPartTwo() + ";");
								}
								
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
							}
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
	}
	
	public void populateBatsmanstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: PlayerStats's inning is null";
			} else {
				int total_inn = 0;
				
				for(Inning inn : match.getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.PLAYER,match, whichInning, playerId,",", match.getEvents()).split(",");
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + bc.getRuns() + "*" + "-" + bc.getBalls() + "\0");								
									}
									else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + bc.getRuns() + "-" + bc.getBalls() + "\0");								
									}
									/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
									} else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
									}*/
									System.out.println(Count[0]);
									System.out.println(Count[1]);
									System.out.println(Count[2]);
									System.out.println(Count[3]);
									System.out.println(Count[4]);
									System.out.println(Count[6]);
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead03*GEOM*TEXT SET " + Count[0] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1c*GEOM*TEXT SET " + Count[1] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + Count[2] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + Count[3] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + Count[4] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + Count[6] + "\0");								
								}
							}
							break;
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: PlayerStats's inning is null";
			} else {
				int total_inn = 0;
				
				for(Inning inn : match.getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.PLAYER,match, whichInning, playerId,",", match.getEvents()).split(",");
						switch(statsType.toUpperCase()) {
						case CricketUtil.BATSMAN :
							for (BattingCard bc : inn.getBattingCard()) {
								if(bc.getPlayerId()==playerId) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + bc.getPlayer().getFull_name() + ";");
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + bc.getRuns() + "*" + ";");
									}
									else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + bc.getRuns() + ";");
									}
									if (inn.getBattingTeamId() == match.getHomeTeamId()) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
									} else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
									}
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + bc.getBalls() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + Count[0] + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + bc.getStrikeRate() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + bc.getFours() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + bc.getSixes() + ";");
								}
							}
							break;
						}
					}
				}
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populateBowlerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: PlayerStats's inning is null";
			} else {
				int total_inn = 0;
				
				for(Inning inn : match.getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BOWLER:
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead02*GEOM*TEXT SET " + boc.getOvers() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + boc.getDots() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + boc.getRuns() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getWickets() + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
								}
							}
							break;
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: PlayerStats's inning is null";
			} else {
				int total_inn = 0;
				
				for(Inning inn : match.getInning()) {
					if(inn.getInningStatus() != null) {
						total_inn = total_inn + 1;
					}
				}
				
				if(total_inn > 0 && whichInning > total_inn) {
					whichInning = total_inn;
				}
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						switch(statsType.toUpperCase()) {
						case CricketUtil.BOWLER:
							for (BowlingCard boc : inn.getBowlingCard()) {
								if(boc.getPlayerId()==playerId) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + boc.getPlayer().getFull_name() + ";");
									
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "OVERS" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + boc.getOvers() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "DOTS" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + boc.getDots() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "RUNS" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + boc.getRuns() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "WICKETS" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + boc.getWickets() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead4 " + "ECO" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + boc.getEconomyRate() + ";");
								}
							}
							break;
						}
					}
				}
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populateBugsDB(PrintWriter print_writer,String viz_scene, Bugs bug ,Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				if(bug.getText1() != null && bug.getText2() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bug.getText1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*GEOM*TEXT SET " + bug.getText2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info04*ACTIVE SET 0 \0");
				}else if(bug.getText1() != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bug.getText1().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info04*ACTIVE SET 0 \0");
				}else {
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info01*GEOM*TEXT SET " + bug.getText2().toUpperCase() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info02*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info03*ACTIVE SET 0 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All_Grp$PositionY$All$DataGrpAll$Data$PlayerNameGrp$Info04*ACTIVE SET 0 \0");
				}
				
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/BugDB.png In 0.714 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				if(bug.getText1() != null && bug.getText2() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText1().toUpperCase() +";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + bug.getText2().toUpperCase() +";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 0;");
				}else if(bug.getText1() != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText1().toUpperCase() +";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 0;");
				}else {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bug.getText2().toUpperCase() +";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info1*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info2*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$All$Lft_Grp$Data$Info3*CONTAINER SET ACTIVE 0;");
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, NameSuper ns ,Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo "+ logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$FirstName*GEOM*TEXT SET " + ns.getFirstname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamNameGrp$LastName*GEOM*TEXT SET " + ns.getSurname().toUpperCase() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$PlayerNameGrp$TeamName*GEOM*TEXT SET " + ns.getSubLine().toUpperCase() + "\0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$Out$ScoreGrp*ACTIVE SET 0" + "\0");
					
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/NameSuper.png In 1.000 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName " + ns.getFirstname() +";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + ns.getSurname() +";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$MiddleLine*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$Role*CONTAINER SET ACTIVE 0;");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 " + ns.getSubLine() + ";");
				
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}
	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, int TeamId, String captainWicketKeeper, int playerId, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				//String Home_or_Away="";
				
				if(TeamId == match.getHomeTeamId()) {
					for(Player hs : match.getHomeSquad()) {
						if(playerId == hs.getPlayerId()) {
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo "+ logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$tPlayerName*GEOM*TEXT SET " + hs.getFull_name().toUpperCase() + "\0");
						}
					}
				}
				else {
					for(Player as : match.getAwaySquad()) {
						if(playerId == as.getPlayerId()) {
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$tPlayerName*GEOM*TEXT SET " + as.getFull_name().toUpperCase() + "\0");
						}
					}
				}
				
				switch(captainWicketKeeper.toUpperCase())
				{
				case CricketUtil.CAPTAIN: case CricketUtil.WICKET_KEEPER: case "PLAYER OF THE MATCH":
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + captainWicketKeeper + "\0");
					break;
				case CricketUtil.PLAYER:
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*ACTIVE SET 0" + "\0");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Info$PlayerName*GEOM*TEXT SET " + "CAPTAIN & WICKETKEEPER" + "\0");
					break;
				}
				print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene + " C:/Temp/NameSuper.png In 1.000 \0");
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				//String Home_or_Away="";
				
				if(TeamId == match.getHomeTeamId()) {
					for(Player hs : match.getHomeSquad()) {
						if(playerId == hs.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo "+ logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName " + hs.getFirstname() +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + hs.getSurname() +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 " + match.getHomeTeam().getFullname() + ";");
						}
					}
				}
				else {
					for(Player as : match.getAwaySquad()) {
						if(playerId == as.getPlayerId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerFirstName " + as.getFirstname() +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerLastName " + as.getSurname() +";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 " + match.getAwayTeam().getFullname() + ";");
						}
					}
				}
				
				switch(captainWicketKeeper.toUpperCase())
				{
				case CricketUtil.CAPTAIN: case CricketUtil.WICKET_KEEPER: case "PLAYER OF THE MATCH":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 " + captainWicketKeeper + ";");
					break;
				case CricketUtil.PLAYER:
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$MiddleLine*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$Role*CONTAINER SET ACTIVE 0;");
					break;
				case "CAPTAIN-WICKETKEEPER":
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 " + "CAPTAIN & WICKETKEEPER" + ";");
					break;
				}

				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		
		}
	}
	
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats, Match match, String session_selected_broadcaster) {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
		
		
		Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
		if(plyr.getTeamId() == match.getHomeTeamId()) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerImage " + photo_path + match.getHomeTeam().getFullname().toUpperCase()+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
		}
		else {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerImage " + photo_path + match.getAwayTeam().getFullname().toUpperCase()+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
		}
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + stats.getRuns()+ ";");
			if(stats.getFifties() == null &&  stats.getHundreds() == null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 " + "50s/100s" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + "0/0" +";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 " + "50s/100s" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + stats.getFifties()+"/"+stats.getHundreds()+";");
			}
			break;
		case CricketUtil.BOWLER:
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead02 "+"Wickets"+";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 "+stats.getWickets() + ";");
			if(stats.getBest_figures() == null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 "+"Best Figures"+";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 "+ "-" +";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 "+"Best Figures"+";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 "+stats.getBest_figures() +";");
			}
			
			break;
		}
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tEventName " + stats.getStats_type().getStats_short_name() + ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

		this.status = CricketUtil.SUCCESSFUL;

		}
	}
	public void populateLTPlayerProfile(PrintWriter print_writer,String viz_scene,String Profile,String TypeofProfile,Statistics stats, Match match, String session_selected_broadcaster) {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
		
		
		Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
		if(plyr.getTeamId() == match.getHomeTeamId()) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + plyr.getFull_name() + ";");
		}
		else {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + plyr.getFull_name() + ";");
		}
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue "+stats.getMatches()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + stats.getRuns()+ ";");
			if(stats.getFifties() == null &&  stats.getHundreds() == null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "50s/100s" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + "0/0"+";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "50s/100s" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + stats.getFifties()+"/"+stats.getHundreds()+";");
			}
			if(stats.getBest_score() == null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + "-" + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + stats.getBest_score() +";");
			}
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data$5*CONTAINER SET ACTIVE 0;");
			break;
		case CricketUtil.BOWLER:
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue "+stats.getMatches()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 "+"Wickets"+";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 "+stats.getWickets() + ";");
			if(stats.getBest_figures() == null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 "+"Best Figures"+";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 "+ "-" +";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 "+"Best Figures"+";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 "+stats.getBest_figures() +";");
			}
			
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data$4*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data$5*CONTAINER SET ACTIVE 0;");
			break;
		}
		print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + stats.getStats_type().getStats_short_name() + ";");
		
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");

		this.status = CricketUtil.SUCCESSFUL;

		}
	}
	public Player getPlayerFromMatchData(int plyr_id, Match match)
	{
		for(Player plyr : match.getHomeSquad()) {
			if(plyr_id == plyr.getPlayerId()) { 
				return plyr;
			}
		}
		for(Player plyr : match.getAwaySquad()) {
			if(plyr_id == plyr.getPlayerId()) { 
				return plyr;
			}
		}
		for(Player plyr : match.getHomeOtherSquad()) {
			if(plyr_id == plyr.getPlayerId()) { 
				return plyr;
			}
		}
		for(Player plyr : match.getAwayOtherSquad()) {
			if(plyr_id == plyr.getPlayerId()) { 
				return plyr;
			}
		}
		return null;
	}

	public void populateDoubleteams(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: DoubleTeam's inning is null";
		} else {
			
			int row_id = 0;
			for(int i = 1; i <= 2 ; i++) {
				if(i == 1) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getTournament() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamName " + match.getHomeTeam().getFullname().toUpperCase() + ";");
					for(Player hs : match.getHomeSquad()) {
						row_id = row_id + 1;
						
						if(hs.getCaptainWicketKeeper() != null ) {
							if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id +" "+ "1" + ";");
							}
							else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id +" "+ "2" + ";");
							}
							else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id +" "+ "3" + ";");
							}
							else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id +" "+ "0" + ";");
							}						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomePlayerName" + row_id + " " + hs.getFull_name().toUpperCase() + ";");
						}
						}
				} else {
					row_id = 0;
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader01 " + match.getHomeTeam().getFullname().toUpperCase() + " v "+ match.getAwayTeam().getFullname().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamName " + match.getAwayTeam().getFullname().toUpperCase() + ";");
					for(Player as : match.getAwaySquad()) {
						row_id = row_id + 1;
						if(as.getCaptainWicketKeeper() != null ) {
							if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id +" "+ "1" + ";");
							}
							else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id +" "+ "2" + ";");
							}
							else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id +" "+ "3" + ";");
							}
							else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id +" "+ "0" + ";");
							}						
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayPlayerName" + row_id + " " + as.getFull_name().toUpperCase() + ";");
						}
					}
				}
			}		
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	public void populateInfobar(PrintWriter print_writer,String viz_scene,String TopStats, String TopLeftStats, String TopRightStats, 
			String BottomLeftStats, String BottomRightStats,List<BattingCard> infobar_batsman, Match match, String session_selected_broadcaster, BowlingCard last_bowler) throws InterruptedException 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Infobar's inning is null";
			} else {
				
				
				populateInfobarTeamScore(false, print_writer, match, session_selected_broadcaster);
				populateVizInfobarTop(false, print_writer, TopStats, match, session_selected_broadcaster);
				populateVizInfobarBottomLeft(false, print_writer, BottomLeftStats, match, session_selected_broadcaster, infobar_batsman);
				populateVizInfobarBottomRight(false, print_writer, BottomRightStats, match, session_selected_broadcaster, last_bowler);

				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Infobar's inning is null";
			} else {
				
				
				populateInfobarTeamScore(false, print_writer, match, session_selected_broadcaster);
				populateInfobarTopLeft(false, print_writer, TopLeftStats, match, session_selected_broadcaster, infobar_batsman);
				populateInfobarTopRight(false, print_writer, TopRightStats, match, session_selected_broadcaster,last_bowler);
				populateInfobarBottomLeft(false, print_writer, BottomLeftStats, match, session_selected_broadcaster);
				populateInfobarBottomRight(false, print_writer, BottomRightStats, match, session_selected_broadcaster);

				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
		
	}
	
	public void populateInfobarTeamScore(boolean is_this_updating, PrintWriter print_writer, Match match, String session_selected_broadcaster)
	{
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			for(Inning inn : match.getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBallTeamLogo " + logo_path + inn.getBowling_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
					}
					int cuEcoent_over = inn.getTotalOvers();
				    if (inn.getTotalBalls() > 0) {
				      cuEcoent_over += 1;
				      //System.out.println();
				    }
				    if(inn.getFirstPowerplayEndOver() >= cuEcoent_over) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "1" + ";");
				    }
				    else {
				    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "0" + ";");
				    }
				    
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getShortname().toUpperCase() + ";");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + inn.getTotalRuns() + ";");
					}
					else{
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls())+ ";");
				}
			}
			break;
		//case "DOAD_IN_HOUSE_VIZ":
		//	for(Inning inn : match.getInning()) {
			//	if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
				//	if(is_this_updating == false) {
				//		print_writer.println("-1 RENDERER*TREE*$Main$BottomBase_Sides$LogoGrp$BatLogo$BatTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
				//				"Delhi" + CricketUtil.PNG_EXTENSION + "\0");
					//	print_writer.println("-1 RENDERER*TREE*$Main$BottomBase_Sides$LogoGrp$BatLogo$BowlTeamLogo*TEXTURE*IMAGE SET " + logo_path + 
					//			"APL" + CricketUtil.PNG_EXTENSION + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$BottomBase_Sides$LogoGrp$BatLogo$BatTeamLogo*TEXTURE*IMAGE SET /Default/APL/Logos/vizag_warriors \0");
						//print_writer.println("-1 RENDERER*TREE*$Main$BottomBase_Sides$LogoGrp$BatLogo$BowlTeamLogo*TEXTURE*IMAGE SET /Default/APL/Logos/vizag_warriors \0");
						//print_writer.println("-1 RENDERER*TREE*$Main$BottomBase_Sides$LogoGrp$BatLogo$BatTeamLogo SET IMAGE*/Default/APL/Logos/" + "vizag_warriors" + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$BottomBase_Sides$LogoGrp$BowlLogo$BowlTeamLogo SET IMAGE*/Default/APL/Logos/" + "vizag_warriors" + "\0");
					//}
				//	int cuEcoent_over = inn.getTotalOvers();
				//    if (inn.getTotalBalls() > 0) {
				//      cuEcoent_over += 1;
				      //System.out.println();
				//    }
				//    if(inn.getFirstPowerplayEndOver() >= cuEcoent_over) {
				//		print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$PowerPlay$FirstName*GEOM*TEXT SET " + "P" + "\0");
				    	//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PowerPlayIn START \0");
				 //   }
				 //   else {
				//		print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$PowerPlay$FirstName*GEOM*TEXT SET " + " " + "\0");
				    	//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PowerPlayOut START \0");
				 //   }
				 //   print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$FirstName*GEOM*TEXT SET " + inn.getBatting_team().getShortname().toUpperCase() + "\0");
				//	if(inn.getTotalWickets() >= 10) {
				//		print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$Runs*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
				//	}
				//	else{
				//		print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$Runs*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
				//	}
				//	print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$noname$DLS*ACTIVE SET 0 \0");
				//	print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$BatTeamNameAndScoreGrp$noname$noname$Overs*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + "\0");
				//}
			//}
			//break;
		}
	}
	public BowlingCard populateInfobarTopRight(boolean is_this_updating, PrintWriter print_writer, String TopRightStats, Match match, String session_selected_broadcaster,BowlingCard last_bowler) throws InterruptedException
	{
		BowlingCard bowler = new BowlingCard();
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			switch(TopRightStats.toUpperCase()) {
			case "BOWLER":
				
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") || boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								bowler = boc;
							}
						}
					}
				}
				if(last_bowler == null) {
					last_bowler = bowler;
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						processAnimation(print_writer, "BowlerChangeOut", "START", session_selected_broadcaster);
						TimeUnit.SECONDS.sleep(1);
					}
				}
				
				if(bowler != null) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + bowler.getPlayer().getSurname()+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigure " + bowler.getWickets() + "-" + bowler.getRuns() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOvers " + CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + ";");
					//processAnimation(print_writer, "BowlerChangeIn", "START", session_selected_broadcaster);
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						processAnimation(print_writer, "BowlerChangeIn", "START", session_selected_broadcaster);
						TimeUnit.SECONDS.sleep(1);
					}
				}	
				if(is_this_updating == false) {
					processAnimation(print_writer, "BowlerChangeIn", "START", session_selected_broadcaster);
					TimeUnit.SECONDS.sleep(1);
				}
				break;
				//return bowler;
				
			case "THIS_OVER":
				Collections.reverse(match.getEvents());
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") || boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								bowler = boc;
								
							}
						}
					}
				}
				
				if(last_bowler == null) {
					last_bowler = bowler;
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						processAnimation(print_writer, "BowlerChangeOut", "START", session_selected_broadcaster);
						TimeUnit.SECONDS.sleep(1);
					}
				}
				
				
				if(bowler != null) {
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + bowler.getPlayer().getSurname()+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigure " + CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOvers " + CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + ";");
					
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						processAnimation(print_writer, "BowlerChangeIn", "START", session_selected_broadcaster);
						TimeUnit.SECONDS.sleep(1);
					}
				}	
				break;
			}
			break;
		case "DOAD_IN_HOUSE_VIZ":
			switch(TopRightStats.toUpperCase()) {
			case "BOWLER":
				
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") || boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								bowler = boc;
							}
						}
					}
				}
				if(last_bowler == null) {
					last_bowler = bowler;
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section4Out START \0");
						TimeUnit.SECONDS.sleep(1);
					}
				}
				
				if(bowler != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$BowlerData$Bowler$FirstName*GEOM*TEXT SET " + bowler.getPlayer().getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$BowlerData$Bowler$Figure*GEOM*TEXT SET " + bowler.getWickets() + "-" + bowler.getRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$BowlerData$Bowler$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + "\0");
					
					//processAnimation(print_writer, "BowlerChangeIn", "START", session_selected_broadcaster);
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section4In START \0");
						TimeUnit.SECONDS.sleep(1);
					}
				}	
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section4In START \0");
					TimeUnit.SECONDS.sleep(1);
				}
				break;
			case "THIS_OVER":
				Collections.reverse(match.getEvents());
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") || boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								bowler = boc;
								
							}
						}
					}
				}
				
				if(last_bowler == null) {
					last_bowler = bowler;
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						processAnimation(print_writer, "BowlerChangeOut", "START", session_selected_broadcaster);
						TimeUnit.SECONDS.sleep(1);
					}
				}
				
				
				if(bowler != null) {
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + bowler.getPlayer().getSurname()+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigure " + CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOvers " + CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + ";");
					
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section4In START \0");
						TimeUnit.SECONDS.sleep(1);
					}
				}	
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section4In START \0");
					TimeUnit.SECONDS.sleep(1);
				}	
				break;
			}
			break;
		}
		return bowler;
	}
	public List<BattingCard> populateInfobarTopLeft(boolean is_this_updating, PrintWriter print_writer, String TopLeftStats, Match match, String session_selected_broadcaster,List<BattingCard> infobar_batsman) throws InterruptedException
	{
		List<BattingCard> batsman = new ArrayList<BattingCard>();
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			switch (TopLeftStats.toUpperCase()) {
			case CricketUtil.BATSMAN:
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for (BattingCard bc : inn.getBattingCard()) {
							
							if(inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
								if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
									batsman.add(bc);
								}
								else if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
									batsman.add(bc);
								}
							}
						}
						
						
						if(infobar_batsman == null || infobar_batsman.size() <= 0) {
							infobar_batsman = batsman;
						}
						
						if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								processAnimation(print_writer, "Batsman1ChangeOut", "START", session_selected_broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								processAnimation(print_writer, "Batsman2ChangeOut", "START", session_selected_broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
						}
						if(batsman != null && batsman.size() >= 1) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName1 " + batsman.get(0).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanScore1 " + batsman.get(0).getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall1 "+ batsman.get(0).getBalls() + ";");
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									
									processAnimation(print_writer, "Batsman1DeHighlight", "SHOW 250.0", session_selected_broadcaster);
								}
								else if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									
									processAnimation(print_writer, "Batsman1DeHighlight", "SHOW 0.0", session_selected_broadcaster);

								}
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName2 " + batsman.get(1).getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanScore2 " + batsman.get(1).getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall2 "+ batsman.get(1).getBalls() + ";");
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								
								if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {	
									processAnimation(print_writer, "Batsman2DeHighlight", "SHOW 250.0", session_selected_broadcaster);
									
								}
								else if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									processAnimation(print_writer, "Batsman2Highlight", "SHOW 0.0", session_selected_broadcaster);

								}
							}
							if(batsman.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(batsman.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike" + " " + "0" + ";");
								}
							}
							if(batsman.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(batsman.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike" + " " + "1" + ";");
								}	
							}
						}
						
						if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								
								processAnimation(print_writer, "Batsman1ChangeIn", "START", session_selected_broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								
								processAnimation(print_writer, "Batsman2ChangeIn", "START", session_selected_broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
						}
					}
				}
				break;
			}
			break;
		case "DOAD_IN_HOUSE_VIZ":
			switch (TopLeftStats.toUpperCase()) {
			case CricketUtil.BATSMAN:
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for (BattingCard bc : inn.getBattingCard()) {
							
							if(inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
								if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
									batsman.add(bc);
								}
								else if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
									batsman.add(bc);
								}
							}
						}
						
						
						if(infobar_batsman == null || infobar_batsman.size() <= 0) {
							infobar_batsman = batsman;
						}
						
						if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Highlight START \0");
								TimeUnit.SECONDS.sleep(1);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Highlight START \0");
								TimeUnit.SECONDS.sleep(1);
							}
						}
						if(batsman != null && batsman.size() >= 1) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$FirstName*GEOM*TEXT SET " + batsman.get(0).getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$Runs*GEOM*TEXT SET " + batsman.get(0).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$Balls*GEOM*TEXT SET " + batsman.get(0).getBalls() + "\0");
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Dehighlight START \0");
								}
								/*else if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Highlight START \0");

								}*/
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$FirstName*GEOM*TEXT SET " + batsman.get(1).getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$Runs*GEOM*TEXT SET " + batsman.get(1).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$Balls*GEOM*TEXT SET " + batsman.get(1).getBalls() + "\0");
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								
								if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {	
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman2Dehighlight START \0");
									
								}
								/*else if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman2Highlight START \0");

								}*/
							}
							if(batsman.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(batsman.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike$Batman1Strike*ACTIVE SET 1 \0");
								}
							}
							if(batsman.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(batsman.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike$Batman2Strike*ACTIVE SET 1 \0");
								}	
							}
						}
						
						/*if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								
								processAnimation(print_writer, "Batsman1ChangeIn", "START", session_selected_broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								
								processAnimation(print_writer, "Batsman2ChangeIn", "START", session_selected_broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
						}*/
					}
				}
				break;
			}
			break;
		}
		return batsman;
	}
	public void populateInfobarBottomLeft(boolean is_this_updating, PrintWriter print_writer, String BottomLeftStats, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			switch(BottomLeftStats.toUpperCase()) {
			case "VS_BOWLING_TEAM":
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + inn.getBowling_team().getShortname().toUpperCase() + ";");
					}
				}
				break;
			case "CURRENT_RUN_RATE":
				for(Inning inn : match.getInning()) {
					if(is_this_updating == false) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "2" + ";");
					}
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRateText " + "CRR " + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRate " + inn.getRunRate() + ";");
					}
				}
				break;
			case"TARGET":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRateText " + "CRR@ " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetScore " + CricketFunctions.getTargetRuns(match) + ";");
				break;
			case"REQUIRED_RUN_RATE":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "3" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRequiredRunRateText " + "RRR@ " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRequiredRunRate " + 
					CricketFunctions.generateRunRate(CricketFunctions.getRequiredRuns(match), 0, CricketFunctions.getRequiredBalls(match), 2) + ";");
				break;
			}
			break;
		}
		
		
	}
	public void populateInfobarBottomRight(boolean is_this_updating, PrintWriter print_writer, String BottomRightStats, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			switch(BottomRightStats.toUpperCase()) {
			case "TOSS_WINNING":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "0" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossResult " + CricketFunctions.generateTossResult(match, "", "", CricketUtil.SHORT).toUpperCase() + ";");
				break;
			case "EQUATION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "3" + ";");
				}
				if(CricketFunctions.getRequiredRuns(match) == 0) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + 
							CricketFunctions.generateMatchSummaryStatus(2, match, CricketUtil.FULL).toUpperCase() + ";");
				}
				else{
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.getRequiredRuns(match) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.getRequiredBalls(match) + ";");
				}
				break;
			case "LAST_WICKET":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "Last Wicket : " + CricketFunctions.getLastWicket(match) + ";");
				
				break;
			case"COMPARISION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "2" + ";");
				}
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCompHead " + "AT THIS STAGE :" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + match.getInning().get(0).getBatting_team().getFullname().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamScore " + CricketFunctions.compareInningData(match,"/", 1 , match.getEvents()) + ";");
					}
				}
				break;
			}
			break;
		case "DOAD_IN_HOUSE_VIZ":
			switch(BottomRightStats.toUpperCase()) {
			case"TARGET":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRateText " + "CRR@ " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetScore " + CricketFunctions.getTargetRuns(match) + ";");
				break;
			case "DOT_BALL":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "Last Wicket : " + CricketFunctions.getLastWicket(match) + ";");
				
				break;
			case"COMPARISION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "2" + ";");
				}
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCompHead " + "AT THIS STAGE :" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + match.getInning().get(0).getBatting_team().getFullname().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamScore " + CricketFunctions.compareInningData(match,"/", 1 , match.getEvents()) + ";");
					}
				}
				break;
			case "SIX_COUNTER":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "Last Wicket : " + CricketFunctions.getLastWicket(match) + ";");
				
				break;
			case "FOUR_COUNTER":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "Last Wicket : " + CricketFunctions.getLastWicket(match) + ";");
				
				break;
			}
			break;
		}
	}
	
	public void populateInfobarPrompt(boolean is_this_updating, PrintWriter print_writer, InfobarStats ibs, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
			}
			if(ibs.getText1() != null && ibs.getText2() != null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + "-" + ibs.getText2()+ ";");
			}else if(ibs.getText1() != null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText2() + ";");
			}
			break;
		case "DOAD_IN_HOUSE_VIZ":
			
			if(is_this_updating == false) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
			}
			if(ibs.getText1() != null && ibs.getText2() != null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + "-" + ibs.getText2()+ ";");
			}else if(ibs.getText1() != null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText2() + ";");
			}
			break;
		}
	}
	
	public void populateInfobarBottom(boolean is_this_updating, PrintWriter print_writer, String BottomStats, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			switch(BottomStats.toUpperCase()) {
			case"BOUNDARIES":
				for(Inning inn : match.getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "2" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursValue " + inn.getTotalFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixValue " + inn.getTotalSixes() + ";");
					}
				}
				break;
			case"PARTNERSHIP":
				for(Inning inn : match.getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "1" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer1Runs " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer1Balls " + 
								 "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")"  + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipPlayer2Runs " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipPlayer2Balls " + 
								"(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")"  + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipRuns " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipBalls " + 
								 "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")"  + ";");
					}
				}
				break;
			
			case"PROJECTED_SCORE":
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "0" + ";");
				}
				
			    String[] proj_score_rate = new String[projectedScore(match).size()];
			    for (int i = 0; i < projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = projectedScore(match).get(i);
		        }
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedHead1 " + "@" + proj_score_rate[0] +" (CRR)" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedValue1 " + proj_score_rate[1] + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedHead2 " + "@" + proj_score_rate[2] + " (RPO)"+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedValue2 " + proj_score_rate[3] + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedHead3 " + "@" + proj_score_rate[4] + " (RPO)" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedValue3 " + proj_score_rate[5] + ";");
				break;	
			}
			break;
		case "DOAD_IN_HOUSE_VIZ":
			switch(BottomStats.toUpperCase()) {
			case"BOUNDARIES":
				for(Inning inn : match.getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "2" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursValue " + inn.getTotalFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixValue " + inn.getTotalSixes() + ";");
					}
				}
				break;
			case"PARTNERSHIP":
				for(Inning inn : match.getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "1" + ";");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer1Runs " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer1Balls " + 
								 "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterBalls() + ")"  + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipPlayer2Runs " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipPlayer2Balls " + 
								"(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterBalls() + ")"  + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipRuns " + 
								inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalRuns() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipBalls " + 
								 "(" + inn.getPartnerships().get(inn.getPartnerships().size() - 1).getTotalBalls() + ")"  + ";");
					}
				}
				break;
			
			case"PROJECTED_SCORE":
				
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "4" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "0" + ";");
				}
				
			    String[] proj_score_rate = new String[projectedScore(match).size()];
			    for (int i = 0; i < projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = projectedScore(match).get(i);
		        }
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedHead1 " + "@" + proj_score_rate[0] +" (CRR)" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedValue1 " + proj_score_rate[1] + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedHead2 " + "@" + proj_score_rate[2] + " (RPO)"+ ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedValue2 " + proj_score_rate[3] + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedHead3 " + "@" + proj_score_rate[4] + " (RPO)" + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tProjectedValue3 " + proj_score_rate[5] + ";");
				break;	
			}
			break;
		}
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getTournament().toUpperCase() + ";");
			//print_writer.println("LAYER1*EVEREST*TREEVIEW*Subheader1*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getMatchIdent().toUpperCase() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + match.getHomeTeam().getFullname().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + match.getAwayTeam().getFullname().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + "LIVE FROM "+ match.getVenueName().toUpperCase() + ";");
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	
	public void populateLTMatchId(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HomeTeamName$HomeTeamFirstName*GEOM*TEXT SET "+ match.getHomeTeam().getFullname().toUpperCase() + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HomeTeamName$HomeTeamLastName*ACTIVE SET 0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$AwayTeamName$AwayTeamLastName*ACTIVE SET 0" + " \0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$AwayTeamName$AwayTeamFirstName*GEOM*TEXT SET "+ match.getAwayTeam().getFullname().toUpperCase() + " \0");
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET "+ match.getTournament().toUpperCase() + " LIVE FROM " + match.getVenueName().toUpperCase() + " \0");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: MatchId's inning is null";
			} else {
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + match.getHomeTeam().getFullname().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + match.getAwayTeam().getFullname().toUpperCase() + CricketUtil.PNG_EXTENSION + ";");
				
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$HomeTeamName$FirstNAme*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHomeTeamLastName " + match.getHomeTeam().getShortname() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$AwayTeamName$FirstNAme*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tAwayTeamLastName " + match.getAwayTeam().getShortname() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1 " + match.getTournament().toUpperCase() + "-" + 
						match.getMatchIdent().toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo2 " + "LIVE FROM "+ match.getVenueName().toUpperCase() + ";");
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,   Match match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			int row_id = 0;
			if(TeamId == match.getHomeTeamId()) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getHomeTeam().getFullname() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader01 " + "v " + match.getAwayTeam().getFullname() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
				for(Player hs : match.getHomeSquad()) {
					row_id = row_id + 1;
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Image1_GRP$Image*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$Second_Six$Player" + row_id + "_GRP$group$Image1_GRP$Image*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Player Role*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Player NAME01*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " + photo_path + 
							match.getHomeTeam().getFullname().toUpperCase() + CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0"+ row_id + " " + hs.getTicker_name() + ";");
					
					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "1" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + hs.getRole() +" (C)" + ";");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "2" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + hs.getRole() + " (WK)" + ";");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "3" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + "(C & WK)" + ";");
					}
					else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "0" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + hs.getRole() + ";");
					}
					
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Score_GRP*CONTAINER SET ACTIVE 0;");
			}
			
			else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getAwayTeam().getFullname() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader01 " + "v " + match.getHomeTeam().getFullname() + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
				for(Player as : match.getAwaySquad()) {
					row_id = row_id + 1;
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " + photo_path + 
							match.getAwayTeam().getFullname().toUpperCase() + CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + CricketUtil.PNG_EXTENSION + ";");
					
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0"+ row_id + " " + as.getFirstname() + ";");
					//System.out.println(as.getCaptainWicketKeeper());
					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "1" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + as.getRole() +" (C)" + ";");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "2" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + as.getRole() + " (WK)" + ";");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "3" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + "(C & WK)" + ";");
					}
					else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon"+ row_id +" "+ "0" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + as.getRole() + ";");
					}
				}
				
			}
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Score_GRP*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + CricketFunctions.generateTossResult(match, "", "", CricketUtil.SHORT).toUpperCase() + ";");
			}
			
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
	}
	public void populateProjectedScore(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: ProjectedScore's inning is null";
			} else {
				String[] proj_score_rate = new String[projectedScore(match).size()];
			    for (int i = 0; i < projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = projectedScore(match).get(i);
		        }
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$FirstName*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$ProjectedHeader*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data$1*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data$2*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data$3*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data$4*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getFullname() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + "@CURR("+ proj_score_rate[0] +")" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + proj_score_rate[1] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "@(" + proj_score_rate[2] +") RPO"+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + proj_score_rate[3] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead3 " + "@(" + proj_score_rate[4] +") RPO" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + proj_score_rate[5] + ";");
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateTarget(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Runs*GEOM*TEXT SET " + CricketFunctions.getRequiredRuns(match) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Balls*GEOM*TEXT SET " + CricketFunctions.getRequiredBalls(match) + "\0");
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						if(inn.getBattingTeamId() == match.getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HomeTeamName$HomeTeamLastName*GEOM*TEXT SET " + match.getHomeTeam().getFullname().toUpperCase() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HomeTeamName$HomeTeamFirstName*ACTIVE SET 0" + " \0");
						}else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HomeTeamName$HomeTeamFirstName*ACTIVE SET 0" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$HomeTeamName$HomeTeamLastName*GEOM*TEXT SET " + match.getAwayTeam().getFullname().toUpperCase() + "\0");
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Runs*GEOM*TEXT SET " + CricketFunctions.getRequiredRuns(match) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$ball*GEOM*TEXT SET " + CricketFunctions.getRequiredBalls(match) + "\0");
						//print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Runs*GEOM*TEXT SET "+ CricketFunctions.getRequiredRuns(match) + " \0");
						//print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Balls*GEOM*TEXT SET "+ CricketFunctions.getRequiredBalls(match) + " \0");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.getRequiredRuns(match) + ";");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.getRequiredBalls(match) + ";");
						
					}
				}
			}
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populateBugTarget(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 2 && inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Runs*GEOM*TEXT SET "+ CricketFunctions.getRequiredRuns(match) + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Balls*GEOM*TEXT SET "+ CricketFunctions.getRequiredBalls(match) + " \0");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.getRequiredRuns(match) + ";");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.getRequiredBalls(match) + ";");
						
					}
				}
			}
			
			this.status = CricketUtil.SUCCESSFUL;
			
			break;
		}
		
	}
	public void populateTeamSummary(PrintWriter print_writer, String viz_scene, int whichInning, Match match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: TeamSummary's inning is null";
			} else {
				
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, whichInning, 0, ",", match.getEvents()).split(",");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + inn.getBatting_team().getFullname()+ ";");
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}
						else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
						}
						
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + Count[0] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + Count[1] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + Count[2] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + Count[3] + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue5 " + inn.getTotalFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue6 " + inn.getTotalSixes() + ";");
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
			
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: PlayerSummary's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.TEAM, match, whichInning, 0, ",", match.getEvents()).split(",");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + inn.getBatting_team().getFullname() + "\0");								
							if(inn.getTotalWickets() >= 10) {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + "\0");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead03*GEOM*TEXT SET " + Count[0] + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1c*GEOM*TEXT SET " + Count[1] + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + Count[2] + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + Count[3] + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + inn.getTotalFours() + "\0");								
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + inn.getTotalSixes() + "\0");
					}
				}
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateLtBattingSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, Match match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: PlayerSummary's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					for(BattingCard bc : inn.getBattingCard()) {
						if (inn.getInningNumber() == whichInning) {
							String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.PLAYER,match, whichInning, PlayerId,"-", match.getEvents()).split("-");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							if(PlayerId == bc.getPlayerId()) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + bc.getPlayer().getFull_name().toUpperCase()+ ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + "BALLS" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + bc.getBalls() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + Count[0] + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + Count[1] + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + Count[2] + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue4 " + Count[3] + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue5 " + bc.getFours() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue6 " + bc.getSixes() + ";");
							}
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
			case "DOAD_IN_HOUSE_VIZ":
				if (match == null) {
					this.status = "ERROR: Match is null";
				} else if (match.getInning() == null) {
					this.status = "ERROR: PlayerSummary's inning is null";
				} else {
					
					for(Inning inn : match.getInning()) {
						for(BattingCard bc : inn.getBattingCard()) {
							if (inn.getInningNumber() == whichInning) {
								String[] Count = CricketFunctions.getScoreTypeData(CricketUtil.PLAYER,match, whichInning, PlayerId,"-", match.getEvents()).split("-");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
								if(PlayerId == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");								
									if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + bc.getRuns() + "*" + "-" + bc.getBalls() + "\0");								
									}
									else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET " + bc.getRuns() + "-" + bc.getBalls() + "\0");								
									}
									/*if (inn.getBattingTeamId() == match.getHomeTeamId()) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
									} else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
									}*/
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead03*GEOM*TEXT SET " + Count[0] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1c*GEOM*TEXT SET " + Count[1] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + Count[2] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + Count[3] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4$StatValue1B*GEOM*TEXT SET " + Count[4] + "\0");								
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue5$StatValue1B*GEOM*TEXT SET " + Count[6] + "\0");
								}
							}
						}
					}
					this.status = CricketUtil.SUCCESSFUL;
				}
				break;
		}
		
	}
	public void populateLtNextToBat(PrintWriter print_writer, String viz_scene, int FirstPlayerId, int SecondPlayerId, Match match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: PlayerSummary's inning is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					for(BattingCard bc : inn.getBattingCard()) {
						if(FirstPlayerId == bc.getPlayerId()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$Player1FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp1$StatAll1$Player1LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");

							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp2$StatAll$SRPlayer1*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");

						}
						if(SecondPlayerId == bc.getPlayerId()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$Player1FirstName*GEOM*TEXT SET " + bc.getPlayer().getFirstname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$Player1LastName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");

							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatGrp3$StatAll$SRPlayer2*GEOM*TEXT SET " + bc.getStrikeRate() + "\0");
						}
					}
				}
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
		
	}
	public void populateLtBowlerDetails(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, Match match, String session_selected_broadcaster) 
	{
		switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_VIZ":
				if (match == null) {
					this.status = "ERROR: Match is null";
				} else if (match.getInning() == null) {
					this.status = "ERROR: PlayerStats's inning is null";
				} else {
					int total_inn = 0;
					
					for(Inning inn : match.getInning()) {
						if(inn.getInningStatus() != null) {
							total_inn = total_inn + 1;
						}
					}
					if(total_inn > 0 && whichInning > total_inn) {
						whichInning = total_inn;
					}
					for(Inning inn : match.getInning()) {
						if (inn.getInningNumber() == whichInning) {
								for (BowlingCard boc : inn.getBowlingCard()) {
									if(boc.getPlayerId()==PlayerId) {
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + "\0");								
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead02*GEOM*TEXT SET " + boc.getOvers() + "\0");								
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1$StatValue1B*GEOM*TEXT SET " + boc.getDots() + "\0");								
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2$StatValue1B*GEOM*TEXT SET " + boc.getRuns() + "\0");								
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getWickets() + "\0");								
										print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3$StatValue1B*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
									}
								}
								break;
						}
					}
					this.status = CricketUtil.SUCCESSFUL;	
				}
				break;
		}
		
	}
	public void populateFallofWicket(PrintWriter print_writer,String viz_scene,int whichInning, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: inning is null";
			} else {
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Header$All_Name$PlayerName*GEOM*TEXT SET "+ match.getAwayTeam().getFullname().toUpperCase() + " \0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$Header$All_Name$PlayerName*GEOM*TEXT SET "+ match.getHomeTeam().getFullname().toUpperCase() + " \0");
						}
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET "+ inn.getTotalRuns() + " \0");
						}
						else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Score$Score*GEOM*TEXT SET "+ inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + " \0");
						}
						
						
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP*ACTIVE SET 0" + " \0");
						}
						else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {								
								if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
									print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP*ACTIVE SET 1" + " \0");
									for(int fow_id=0;fow_id<=10;fow_id++) {
										if(fow_id <= inn.getFallsOfWickets().size()) {
											print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue" + fow.getFowNumber() + "$StatValue1B*GEOM*TEXT SET "+ fow.getFowRuns() + " \0");
										}/*else {
											print_writer.println("-1 RENDERER*TREE*$Main$BowlingCardAll$Format2$BallData$FowGrp$FOW3$BallOmo$Dehighlight$TextGrp$" + fow_id + "*ACTIVE SET 0" + " \0");
											print_writer.println("-1 RENDERER*TREE*$Main$BowlingCardAll$Format2$BallData$FowGrp$FOW3$BallOmo$Highlight$FOW_ValueGrp$" + fow_id + "*ACTIVE SET 0" + " \0");
										}*/
									}	
								}		
							}
						}
						
					}
				}
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: DoubleTeam's inning is null";
			} else {
				int row_id= 0 ;
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getAwayTeam().getFullname().toUpperCase() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getHomeTeam().getFullname().toUpperCase() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path  + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						}
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}
						else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
						}
						
						
						if(inn.getFallsOfWickets() == null || inn.getFallsOfWickets().size() <= 0) {
							//System.out.println(inn.getFallsOfWickets());
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data*CONTAINER SET ACTIVE 0;");
						}
						else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {								
								if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
									row_id = row_id + 1;
									//System.out.println("row3="+row_id);
									print_writer.println("LAYER1*EVEREST*TREEVIEW*" + (row_id  + 1) + "*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data*CONTAINER SET ACTIVE 1;");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead "+ "WICKET:" + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vWKTS "+ fow.getFowNumber() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue" + row_id + " "  + fow.getFowRuns() + ";");
								}		
							}
						}
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
		
		
	}
	
	public void populateSplit(PrintWriter print_writer,String viz_scene,int whichInning,int splitValue, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getHomeTeamId() && splitValue == 30 || splitValue == 50) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$TeamName*GEOM*TEXT SET "+ match.getAwayTeam().getFullname().toUpperCase() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHeader*GEOM*TEXT SET "+ splitValue + " RUNS PER BALL" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead01*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + " \0");
						
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$TeamName*GEOM*TEXT SET "+ match.getHomeTeam().getFullname().toUpperCase() + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$SubHeader*GEOM*TEXT SET "+ splitValue + " RUNS PER BALL" + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatHead_GRP$StatAll1$StatHead01*GEOM*TEXT SET "+ splitValue + CricketFunctions.Plural(splitValue) + " \0");
							
						}
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue1*ACTIVE SET 0" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue2*ACTIVE SET 0" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue3*ACTIVE SET 0" + " \0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue4*ACTIVE SET 0" + " \0");
						//print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP*ACTIVE SET 0" + " \0");
						/*if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
						}*/
						
						String[] Splitballs = new String[getSplit(whichInning, splitValue,match,match.getEvents()).size()];
					    for (int i = 0; i < getSplit(whichInning, splitValue,match,match.getEvents()).size(); i++) {
					    	Splitballs[i] = getSplit(whichInning, splitValue,match,match.getEvents()).get(i);
					    	
					    	int row_id = i + 1;
					    	if(i <= getSplit(whichInning, splitValue,match,match.getEvents()).size()) {
					    		print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue"+row_id+"*ACTIVE SET 1" + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue"+row_id+"$StatValue1B*GEOM*TEXT SET "+ Splitballs[i] + " \0");

					    	}
					    	/*else {
					    		print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$ROW1$All_StatsVal$StatValues_GRP$StatValue"+row_id+"*ACTIVE SET 0" + " \0");
					    	}*/
					    	
					    	//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+(row_id+1)+"*CONTAINER SET ACTIVE 1;");
				        }
					}
				}
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
			
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else {
				
				for(Inning inn : match.getInning()) {
					if (inn.getInningNumber() == whichInning) {
						if (inn.getBowlingTeamId() == match.getHomeTeamId() && splitValue == 30 || splitValue == 50) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getAwayTeam().getFullname().toUpperCase() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + splitValue + " RUNS PER BALL"  + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + splitValue + CricketFunctions.Plural(splitValue) + " :" + ";");
						} else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName " + match.getHomeTeam().getFullname().toUpperCase() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + splitValue + " RUNS PER BALL"  + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path  + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + splitValue + CricketFunctions.Plural(splitValue) + " :" + ";");
						}
						
						if(inn.getTotalWickets() >= 10) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + ";");
						}else {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + slashOrDash + inn.getTotalWickets() + ";");
						}
						
						String[] Splitballs = new String[getSplit(whichInning, splitValue,match,match.getEvents()).size()];
					    for (int i = 0; i < getSplit(whichInning, splitValue,match,match.getEvents()).size(); i++) {
					    	Splitballs[i] = getSplit(whichInning, splitValue,match,match.getEvents()).get(i);
					    	
					    	int row_id = i + 1;
					    	
					    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+(row_id+1)+"*CONTAINER SET ACTIVE 1;");
					    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue"+ row_id + " "+ Splitballs[i] + ";");
				        }
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;	
			}
			break;
		}
		
	}
	
	public void populateComparision(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead1 " + inn.getBowling_team().getFullname().toUpperCase() + " WERE " + CricketFunctions.compareInningData(match, "-", 1, match.getEvents()) + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHead2 " + inn.getBatting_team().getFullname().toUpperCase() + " ARE " + CricketFunctions.compareInningData(match, "-", 2, match.getEvents()) + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
						
					}
				}
				
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
				print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
				print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
			
		case "DOAD_IN_HOUSE_VIZ":
			if (match == null) {
				this.status = "ERROR: Match is null";
			} else if (match.getInning() == null) {
				this.status = "ERROR: Target's inning is null";
			} else {
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Name$Header*GEOM*TEXT SET " + "AFTER " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls()) + " OVERS" + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$HomeTeamData$HomeTeamLastName*GEOM*TEXT SET " + inn.getBatting_team().getFullname().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$HomeTeamData$HomeTeamScore*GEOM*TEXT SET " + CricketFunctions.compareInningData(match, "-", 2, match.getEvents()) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$AwayTeamData$AwayTeamLastName*GEOM*TEXT SET " + inn.getBowling_team().getFullname().toUpperCase() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$AwayTeamData$AwayTeamScore*GEOM*TEXT SET " + CricketFunctions.compareInningData(match, "-", 1, match.getEvents()) + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$All$DataAll$All_Stats$StatAll1$Info*GEOM*TEXT SET " + inn.getBatting_team().getFullname().toUpperCase() + " TOTAL " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + "\0");
						
					}
				}
				
				this.status = CricketUtil.SUCCESSFUL;
			}
			break;
		}
	}
	public void populateLTPartnership(PrintWriter print_writer, String viz_scene, Match match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$PartHeader$MatchId$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			
			/*for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {

					if (inn.getBattingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
					}
					
					for(int a = 1; a <= inn.getPartnerships().size(); a++){
						ScaleFac1=0;ScaleFac2=0;
						if(inn.getPartnerships().get(a-1).getFirstBatterRuns() > Top_Score) {
							Top_Score = inn.getPartnerships().get(a-1).getFirstBatterRuns();
						}
						if(inn.getPartnerships().get(a-1).getSecondBatterRuns() > Top_Score) {
							Top_Score = inn.getPartnerships().get(a-1).getSecondBatterRuns();
						}
					}

					for (Partnership ps : inn.getPartnerships()) {
						
						row_id = row_id + 1;
						Left_Batsman ="" ; Right_Batsman="";
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == ps.getFirstBatterNo()) {
								Left_Batsman = bc.getPlayer().getSurname();
							}
							else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
								Right_Batsman = bc.getPlayer().getSurname();
							}
						}
						
						if(inn.getPartnerships().size() >= 10) {
							if(ps.getPartnershipNumber()<=inn.getPartnerships().size()) {
								omo_num = 4;
								cont_name = "Highlight";
							}
						}
						else {
							if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
								omo_num = 4;
								cont_name = "Highlight";
							}
							else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
								omo_num = 3;
								cont_name = "Dehighlight";
							}
						}
						
						ScaleFac1 = ((ps.getFirstBatterRuns())*(Mult/Top_Score)) ;
						ScaleFac2 = ((ps.getSecondBatterRuns())*(Mult/Top_Score)) ;

						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET "+String.valueOf(omo_num)+ " \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$LeftPlayeName*GEOM*TEXT SET " + Left_Batsman + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$RightPlayeName*GEOM*TEXT SET " + Right_Batsman + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$BarGrp*FUNCTION*BarValues*Bar_Value__1 SET " + ScaleFac1 + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$BarGrp*FUNCTION*BarValues*Bar_Value__2 SET " + ScaleFac2 + "\0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$ScoreGrp$PlayerRuns*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$ScoreGrp$PlayerBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
					}
					if(inn.getPartnerships().size() >= 10) {
						row_id = row_id + 1; 
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 0"+ " \0");
					}
					else {
						for (BattingCard bc : inn.getBattingCard()) {
							if(row_id < 11) {
								if(row_id == inn.getPartnerships().size()) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 1"+ " \0");
								}
								else if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
									row_id = row_id + 1;
									print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET 2"+ " \0");
									print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$StiilToBatPlayerGrp$LeftPlayeName*GEOM*TEXT SET "+bc.getPlayer().getSurname()+" \0");
								}	
							}
							else break;
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$BottomInfoPosition$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$BottomInfoPosition$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}*/
			
			
			this.status = CricketUtil.SUCCESSFUL;
		}
	}
	public void populateLeaderBoard(PrintWriter print_writer,String viz_scene, String FirstPlayer, String SecondPlayer , Match match, String session_selected_broadcaster) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET PlayerName01 " + FirstPlayer + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamName01 " + SecondPlayer + ";");

		}
			
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
		
		this.status = CricketUtil.SUCCESSFUL;
	}
	
	public BowlingCard populateVizInfobarBottomRight(boolean is_this_updating, PrintWriter print_writer, String BottomRightStats, Match match, String session_selected_broadcaster,BowlingCard last_bowler) throws InterruptedException
	{
		BowlingCard bowler = new BowlingCard();
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			switch(BottomRightStats.toUpperCase()) {
			case "BOWLER":
				
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase("CURRENTBOWLER") || boc.getStatus().toUpperCase().equalsIgnoreCase("LASTBOWLER")) {
								bowler = boc;
							}
						}
					}
				}
				if(last_bowler == null) {
					last_bowler = bowler;
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section4Out START \0");
						TimeUnit.SECONDS.sleep(1);
					}
				}
				
				if(bowler != null) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$BowlerData$Bowler$FirstName*GEOM*TEXT SET " + bowler.getPlayer().getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$BowlerData$Bowler$Figure*GEOM*TEXT SET " + bowler.getWickets() + "-" + bowler.getRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section4_5_All$Sectio4_5$BowlerData$Bowler$Balls*GEOM*TEXT SET " + CricketFunctions.OverBalls(bowler.getOvers(), bowler.getBalls()) + "\0");
					
					//processAnimation(print_writer, "BowlerChangeIn", "START", session_selected_broadcaster);
				}
				if(last_bowler != null && bowler != null) {
					if(last_bowler.getPlayerId() != bowler.getPlayerId()) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section4In START \0");
						TimeUnit.SECONDS.sleep(1);
					}
				}	
				if(is_this_updating == false) {
					print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section4In START \0");
					TimeUnit.SECONDS.sleep(1);
				}
				break;
				
			}
			break;
		}
		return bowler;
	}
	public List<BattingCard> populateVizInfobarBottomLeft(boolean is_this_updating, PrintWriter print_writer, String BottomLeftStats, Match match, String session_selected_broadcaster,List<BattingCard> infobar_batsman) throws InterruptedException
	{
		List<BattingCard> batsman = new ArrayList<BattingCard>();
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			switch (BottomLeftStats.toUpperCase()) {
			case CricketUtil.BATSMAN:
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for (BattingCard bc : inn.getBattingCard()) {
							
							if(inn.getPartnerships() != null && inn.getPartnerships().size() > 0) {
								if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getFirstBatterNo()) {
									batsman.add(bc);
								}
								else if(bc.getPlayerId() == inn.getPartnerships().get(inn.getPartnerships().size() - 1).getSecondBatterNo()) {
									batsman.add(bc);
								}
							}
						}
						
						
						if(infobar_batsman == null || infobar_batsman.size() <= 0) {
							infobar_batsman = batsman;
						}
						
						if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Highlight START \0");
								TimeUnit.SECONDS.sleep(1);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Highlight START \0");
								TimeUnit.SECONDS.sleep(1);
							}
						}
						if(batsman != null && batsman.size() >= 1) {
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$FirstName*GEOM*TEXT SET " + batsman.get(0).getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$Runs*GEOM*TEXT SET " + batsman.get(0).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman1$Balls*GEOM*TEXT SET " + batsman.get(0).getBalls() + "\0");
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
									
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Dehighlight START \0");
								}
								/*else if(infobar_batsman.get(0).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman1Highlight START \0");

								}*/
							}
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$FirstName*GEOM*TEXT SET " + batsman.get(1).getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$Runs*GEOM*TEXT SET " + batsman.get(1).getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$Batsman2$Balls*GEOM*TEXT SET " + batsman.get(1).getBalls() + "\0");
							
							if(infobar_batsman != null && infobar_batsman.size() >= 1) {
								
								if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.OUT)) {	
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman2Dehighlight START \0");
									
								}
								/*else if(infobar_batsman.get(1).getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)){
									print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Batsman2Highlight START \0");

								}*/
							}
							if(batsman.get(0).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(batsman.get(0).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike$Batman1Strike*ACTIVE SET 1 \0");
								}
							}
							if(batsman.get(1).getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								if(batsman.get(1).getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section3-4-5$Section3$BatsmanGrp$OnStrike$Batman2Strike*ACTIVE SET 1 \0");
								}	
							}
						}
						
						/*if(infobar_batsman != null && infobar_batsman.size() >= 1 && batsman != null && batsman.size() >= 1) {
							if(infobar_batsman.get(0).getPlayerId() != batsman.get(0).getPlayerId()) {
								
								processAnimation(print_writer, "Batsman1ChangeIn", "START", session_selected_broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
							if(infobar_batsman.get(1).getPlayerId() != batsman.get(1).getPlayerId()) {
								
								processAnimation(print_writer, "Batsman2ChangeIn", "START", session_selected_broadcaster);
								TimeUnit.SECONDS.sleep(1);
							}
						}*/
					}
				}
				break;
			}
			break;
		}
		return batsman;
	}
	public void populateVizInfobarTop(boolean is_this_updating, PrintWriter print_writer, String TopStats, Match match, String session_selected_broadcaster)
	{
		//System.out.println("TopStats " + TopStats);
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_VIZ":
			switch(TopStats.toUpperCase()) {
			case "CRR":
				for(Inning inn : match.getInning()) {
					if(is_this_updating == false) {
						
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "2" + ";");
					}
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$CurRunRateIn START \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR$CurRunRate$CURRValue*GEOM*TEXT SET " + inn.getRunRate() + "\0");
					}
				}
				break;
			case "VS_BOWLING_TEAM":
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							
							//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
							print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
							//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "0" + ";");
						}
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$BowlingTeamIn START \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp$TeamName2*GEOM*TEXT SET " + inn.getBowling_team().getShortname().toUpperCase() + "\0");
					}
				}
				break;
			case"CRR_RRR":
				if(is_this_updating == false) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection4Selection " + "3" + ";");
				}
				for(Inning inn : match.getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("-1 RENDERER*STAGE*DIRECTOR*CRR_RRRIn START \0");
						print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$CURRValue*GEOM*TEXT SET " + inn.getRunRate() + "\0");
					}
				}
				print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR$ReqRunRate$REQRValue*GEOM*TEXT SET " + 
				                       CricketFunctions.generateRunRate(CricketFunctions.getRequiredRuns(match), 0, CricketFunctions.getRequiredBalls(match), 2) + "\0");
				
				break;
			case "TOSS_WINNING":
				if(is_this_updating == false) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "0" + ";");
				}
				if(match.getTossWinningTeam() == match.getHomeTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Team*GEOM*TEXT SET " + match.getHomeTeam().getShortname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Result*GEOM*TEXT SET " + match.getTossWinningDecision() + "\0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Team*GEOM*TEXT SET " + match.getAwayTeam().getShortname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss$TossGrp$TOSS_Result*GEOM*TEXT SET " + match.getTossWinningDecision() + "\0");
				}
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$TossIn START \0");
				break;
			case "EQUATION":
				if(is_this_updating == false) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "3" + ";");
				}
				if(CricketFunctions.getRequiredRuns(match) == 0) {
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText$FreeTextSmall$FreeTextSmall*GEOM*TEXT SET " + 
											CricketFunctions.generateMatchSummaryStatus(2, match, CricketUtil.FULL).toUpperCase()  + "\0");
					
				}
				else{
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours$1$NEEDRUNS*GEOM*TEXT SET " + CricketFunctions.getRequiredRuns(match) + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation$Equation$noname$Fours$2$NEEDBALLS*GEOM*TEXT SET " + CricketFunctions.getRequiredBalls(match) + "\0");
				}
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$EquationIn START \0");
				break;
			case "LAST_WICKET":
				if(is_this_updating == false) {
					
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BowlingTeamNameGrp*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$RRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$CRR*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Toss*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Equation*ACTIVE SET 0"+"\0");
					//print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Timeline*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Commentator*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FirstInn*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Boundaries*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$BallSinceLastBoundary*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Extras*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Partnership*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$lastXBalls*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$DLSTarget*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$FreeText*ACTIVE SET 0"+"\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$Projected*ACTIVE SET 0"+"\0");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection6Selection " + "4" + ";");
					//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vSection5Selection " + "1" + ";");
				}
				print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Section2$LastWicketIn START \0");
				for(Inning inn : match.getInning()) {
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)){
						for(BattingCard bc : inn.getBattingCard()){
							if(inn.getFallsOfWickets().size() > 0){
								if(inn.getFallsOfWickets().get(inn.getFallsOfWickets().size() - 1).getFowPlayerID() == bc.getPlayerId()) {
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket$LastWicket$LastWicketPlayer*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket$LastWicket$LastWicketBowler*GEOM*TEXT SET " + bc.getHowOutText() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket$LastWicket$LastWicketPlayer$noname$LastWicketGrp$Ball*GEOM*TEXT SET " + bc.getBalls() + "\0");
									print_writer.println("-1 RENDERER*TREE*$Main$AllSection$Section1_2$DataAll$Section2$LastWicket$LastWicket$LastWicketPlayer$noname$LastWicketGrp$Runs*GEOM*TEXT SET " + bc.getRuns() + "\0");
								}
							}								
						}
					}
				}				
				break;
			}
			break;
		}
		
		
	}

	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
	}
	
	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic)
	{
		print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
		switch(whichGraphic) {
		case "SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
			//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn$BatOffsetIn START \0");
			//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardIn START \0");
			//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingRightCardIn$BatRightOffsetIn START \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallOffsetIn START \0");
			//print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallRightOffsetIn START \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "MATCHSUMMARY":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn$SummaryOffsetIn START \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BUG-DISMISSAL": case "BUG": case "BUGBOWLER": case "BUG-DB": case "HOWOUT": case "NAMESUPER": case "NAMESUPER-PLAYER": case "FALLOFWICKET":
		case "L3MATCHID": case "TARGET": case "BUGTARGET": case "COMPARISION": case "LTPARTNERSHIP": case "SPLIT": case "BATSMANSTATS": case "BOWLERSTATS":
		case "BOWLERSUMMARY": case "PLAYERSUMMARY": case "TEAMSUMMARY": case "NEXTTOBAT":
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "SCOREBUG":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*MainIn START \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}	
	}
	
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic) {
		case "BATBALLSUMMARY_SCORECARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardOut CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatOffsetOut CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BatRightOffsetOut CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BATBALLSUMMARY_BOWLINGCARD":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardOut CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallOffsetOut CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BallRightOffsetOut CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BATBALLSUMMARY_MATCHSUMMARY":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOut CONTINUE \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryOffsetOut CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BUG-DISMISSAL":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BUG":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BUG-DB":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "HOWOUT":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "NAMESUPER":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "NAMESUPER-PLAYER":
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*Out CONTINUE \0");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}	
	}
	public List<String> projectedScore(Match match) {
		//rates= 6,8,10 or 8,10,12
		List<String> proj_score = new ArrayList<String>();
		String  PS_Curr="", PS_1 = "",PS_2 = "",RR_1 = "",RR_2 = "",CRR = "";
		//DecimalFormat df = new DecimalFormat("0.00");
		
		int remaining_balls = (match.getMaxOvers()*6 - (match.getInning().get(0).getTotalOvers()*6 + match.getInning().get(0).getTotalBalls()));
		
		PS_Curr = String.valueOf(Math.round(((match.getInning().get(0).getTotalRuns() + (remaining_balls * 
					Double.valueOf(match.getInning().get(0).getRunRate())))/6)));
		CRR = match.getInning().get(0).getRunRate();
		
		proj_score.add(CRR);
		proj_score.add(String.valueOf(PS_Curr));
		
		String[] arr = (match.getInning().get(0).getRunRate().split("\\."));
	    double[] intArr= new double[2];
	    intArr[0]=Integer.parseInt(arr[0]);
	  
		for(int i=2;i<=4;i=i+2) {
			if(i==2) {
				PS_1 = String.valueOf(Math.round(((match.getInning().get(0).getTotalRuns()) + (remaining_balls * (intArr[0] + i)))/6));
				RR_1 = String.valueOf(((int)intArr[0] + i));
				
				proj_score.add(RR_1);
				proj_score.add(PS_1);
			}
			else if(i==4) {
				PS_2 = String.valueOf(Math.round(((match.getInning().get(0).getTotalRuns()) + (remaining_balls * (intArr[0] + i)))/6));
				RR_2 = String.valueOf(((int)intArr[0] + i));
				
				proj_score.add(RR_2);
				proj_score.add(PS_2);
			}
		}
	    //System.out.println(String.valueOf(PS_Curr));
		return proj_score ;
	}
	
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}	
	
	public static List<String> getSplit(int inning_number, int splitvalue, Match match,List<Event> events) {
		int total_runs = 0, total_balls = 0 ;
		List<String> Balls = new ArrayList<String>();
		if((events != null) && (events.size() > 0)) {
			for (Event evnt : events) {
				if(evnt.getEventInningNumber() == inning_number) {
					//System.out.println("Inn Number" + inning_number);
					int max_balls = (match.getMaxOvers() * 6);
					int count_balls = ((match.getInning().get(inning_number-1).getTotalOvers() * 6) + match.getInning().get(inning_number-1).getTotalBalls());
					
					switch (evnt.getEventType()) {
					case CricketUtil.DOT: case CricketUtil.ONE: case CricketUtil.TWO: case CricketUtil.THREE: case CricketUtil.FOUR:  case CricketUtil.FIVE: case CricketUtil.SIX: 
					case CricketUtil.LEG_BYE: case CricketUtil.BYE: case CricketUtil.LOG_WICKET:
						total_balls = total_balls + 1 ;
						total_runs = total_runs + evnt.getEventRuns();
						break;
					
					case CricketUtil.WIDE: case CricketUtil.NO_BALL: case CricketUtil.PENALTY:
						total_runs = total_runs + evnt.getEventRuns();
						break;
					
					case CricketUtil.LOG_ANY_BALL:
						total_runs += evnt.getEventRuns();
			          if (evnt.getEventExtra() != null) {
			        	 total_runs += evnt.getEventExtraRuns();
			          }
			          if (evnt.getEventSubExtra() != null) {
			        	 total_runs += evnt.getEventSubExtraRuns();
			          }
			          break;
					}
					
					if(count_balls <= max_balls && total_runs >= splitvalue) {
						Balls.add(String.valueOf(total_balls));
						total_runs = 0;
						total_balls = 0;
						
						continue;
					}
				}
			}
		}
		return Balls ;
	}
}