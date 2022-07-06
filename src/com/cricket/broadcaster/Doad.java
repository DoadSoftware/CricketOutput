package com.cricket.broadcaster;

import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
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

							/*if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)){
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_id + " " + bc.getPlayer().getFirstname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$HowOut_GRP*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Runs*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Balls*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "0" + ";");
								//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Highlight*CONTAINER SET ACTIVE 0;");
							}
							else {*/
							switch (bc.getStatus().toUpperCase()) {
	
							case CricketUtil.OUT:
								
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
								/*System.out.println("status = "+bc.getHowOut());
								if(bc.getStatus().toUpperCase() != CricketUtil.STILL_TO_BAT || bc.getHowOut().trim() != "") {
									if(bc.getHowOut().toUpperCase().equalsIgnoreCase(CricketUtil.RETIRED_HURT)) {
										System.out.println("if");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Out*CONTAINER SET ACTIVE 1;");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$NotOut*CONTAINER SET ACTIVE 0;");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Details*CONTAINER SET ACTIVE 0;");
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
									else{
										System.out.println("else");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_id + " " + bc.getPlayer().getTicker_name() + ";");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "0" + ";");
									}
									
								}*/
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_id + " " + bc.getPlayer().getTicker_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "0" + ";");
								
								
								break;
						}
							
						//}
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
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene, int whichInning,   Match match, String session_selected_broadcaster) 
	{
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
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH D:\\Temp\\Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
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
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH D:\\Temp\\Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
		}
	}
	
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning, Match match, String session_selected_broadcaster)
	{
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
	}

	public void populateBugDismissal(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Bug's inning is null";
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
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*TeamName SET" + match.getTossWinningTeam()+ " \0");
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
	}
	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Bug's inning is null";
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
				//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*TeamName SET" + match.getTossWinningTeam()+ " \0");
				if (inn.getInningNumber() == whichInning) {
					switch(statsType.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									//System.out.println("Player:" + bc.getPlayer().getFull_name());
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getFirstname() + "*" + ";");
								}
								else {
									//System.out.println("Player:" + bc.getPlayer().getFull_name());
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + bc.getPlayer().getFirstname() + ";");
								}
							}
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "4's " + bc.getFours()  + " 6's "  + bc.getSixes() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + bc.getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + bc.getBalls() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamInitials " + bc.getRuns() + "-" + String.valueOf(bc.getBalls()) + ";");
							
						}
						break;
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName01 " + boc.getPlayer().getFull_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1A " + "ECO " + boc.getEconomyRate() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1B " + boc.getWickets() + slashOrDash + boc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tInfo1C " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
							}
						}
						break;
					}
				}
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Bug.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: HowOut's inning is null";
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
					for (BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId()==playerId) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION  + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + bc.getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + bc.getBalls() + ";");
							if(bc.getHowOutPartOne() == null && bc.getHowOutPartTwo() == null) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$HowOut_GRP*CONTAINER SET ACTIVE 0;");
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
	}
	
	public void populateBatsmanstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
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
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	
	public void populateBowlerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster)
	{
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
	}
	
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, NameSuper ns ,Match match, String session_selected_broadcaster)
	{
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
	}
	
	public void populateNameSuperPlayer(PrintWriter print_writer,String viz_scene, int TeamId, String captainWicketKeeper, int playerId, Match match, String session_selected_broadcaster)
	{
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
	}
	
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats, Match match, String session_selected_broadcaster) {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
		
		
		Player plyr = getPlayerFromMatchData(stats.getPlayer_id(), match);
		if(plyr.getTeamId() == match.getHomeTeamId()) {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerImage " + photo_path + match.getHomeTeam().getShortname().toUpperCase()+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
		}
		else {
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerImage " + photo_path + match.getAwayTeam().getShortname().toUpperCase()+ CricketUtil.DOUBLE_BACKSLASH + plyr.getPhoto() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + plyr.getFirstname() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tLastName " + plyr.getSurname() + ";");
		}
		switch(TypeofProfile.toUpperCase()) {
		case CricketUtil.BATSMAN:
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + stats.getRuns()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 " + "50s/100s" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + stats.getFifties()+"/"+stats.getHundreds()+";");
			break;
		case CricketUtil.BOWLER:
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead02 "+"Wickets"+";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 "+stats.getWickets() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 "+"Best Figures"+";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 "+stats.getBest_figures() +";");
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
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 " + "50s/100s" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 " + stats.getFifties()+"/"+stats.getHundreds()+";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue3 " + stats.getBest_score() +";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$BigBandGrp$Data$5*CONTAINER SET ACTIVE 0;");
			break;
		case CricketUtil.BOWLER:
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue "+stats.getMatches()+ ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 "+"Wickets"+";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 "+stats.getWickets() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead2 "+"Best Figures"+";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue2 "+stats.getBest_figures() +";");
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
							switch(hs.getCaptainWicketKeeper().toUpperCase()) {
							case CricketUtil.CAPTAIN:
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id + " " + "1" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 0;");
								break;
							case CricketUtil.WICKET_KEEPER:
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id + " " + "1" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 0;");
								break;
							case "CAPTAIN_WICKET_KEEPER":
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeIcon"+ row_id + " " + "1" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 1;");
								break;
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
							switch(as.getCaptainWicketKeeper().toUpperCase()) {
							case CricketUtil.CAPTAIN:
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id + " " + "1" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 0;");
								break;
							case CricketUtil.WICKET_KEEPER:
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id + " " + "1" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 0;");
								break;
							case "CAPTAIN_WICKET_KEEPER":
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayIcon"+ row_id + " " + "1" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 1;");
								break;
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
	public void populateInfobar(PrintWriter print_writer,String viz_scene, String TopLeftStats, String TopRightStats, 
			String BottomLeftStats, String BottomRightStats,List<BattingCard> infobar_batsman, Match match, String session_selected_broadcaster, BowlingCard last_bowler) throws InterruptedException 
	{
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
	}
	
	public void populateInfobarTeamScore(boolean is_this_updating, PrintWriter print_writer, Match match, String session_selected_broadcaster)
	{
		
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			for(Inning inn : match.getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					if(is_this_updating == false) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$BatingScoreGrp*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*PowerPlay*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBatTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgBallTeamLogo " + logo_path + inn.getBowling_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
					}
					int cuEcoent_over = inn.getTotalOvers();
				    if (inn.getTotalBalls() > 0) {
				      cuEcoent_over += 1;
				    }
				    if((inn.getFirstPowerplayEndOver() >= cuEcoent_over)) {
				    	
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vPowerPlay_DL " + "1" + ";");
				    	print_writer.println("LAYER1*EVEREST*TREEVIEW*Powerplay*CONTAINER SET ACTIVE 1;");
				    	print_writer.println("LAYER1*EVEREST*TREEVIEW*DL_Powerplay*CONTAINER SET ACTIVE 0;");
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
									processAnimation(print_writer, "Batsman2Highlight", "SHOW 250.0", session_selected_broadcaster);

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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP$group*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP*CONTAINER SET ACTIVE 1;");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + inn.getBowling_team().getShortname().toUpperCase() + ";");
					}
				}
				break;
			case "CURRENT_RUN_RATE":
				for(Inning inn : match.getInning()) {
					if(is_this_updating == false) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP$group*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 1;");
					}
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRateText " + "CRR " + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRate " + inn.getRunRate() + ";");
					}
				}
				break;
			case"TARGET":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP$group*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 1;");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRateText " + "CRR@ " + ";");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetScore " + CricketFunctions.getTargetRuns(match) + ";");
				break;
			case"REQUIRED_RUN_RATE":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP$group*CONTAINER SET ACTIVE 1;");
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$GreenGrp*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$BoundariesHead*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$FourseGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$sixesGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 1;");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossResult " + CricketFunctions.generateTossResult(match, "", "", CricketUtil.SHORT).toUpperCase() + ";");
				break;
			case "EQUATION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$GreenGrp*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$BoundariesHead*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$FourseGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$sixesGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 1;");
				}
				if(CricketFunctions.getRequiredRuns(match) == 0) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + CricketFunctions.generateMatchSummaryStatus(2, match, CricketUtil.FULL).toUpperCase() + ";");
					
				}
				else{
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.getRequiredRuns(match) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.getRequiredBalls(match) + ";");
				}
				break;
			case "LAST_WICKET":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$GreenGrp*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$BoundariesHead*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$FourseGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$sixesGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 1;");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + "Last Wicket : " + CricketFunctions.getLastWicket(match) + ";");
				
				break;
			case"COMPARISION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$GreenGrp*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$BoundariesHead*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$FourseGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$sixesGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 1;");
				}
				for(Inning inn : match.getInning()) {
					if(inn.getInningNumber() == 1 & inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase("NO")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCompHead " + "AT THIS STAGE :" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + match.getInning().get(0).getBatting_team().getFullname().toUpperCase() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamScore " + CricketFunctions.compareInningData(match,"/", 1 , match.getEvents()) + ";");
						//System.out.println("Compare1 = " + compareInningData(match,"/", 1 , match.getEvents()));
					}
					
				}
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
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$GreenGrp*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$BoundariesHead*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$FourseGRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$sixesGRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
			}
			if(ibs.getText1() != null && ibs.getText2() != null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + "-" + ibs.getText2()+ ";");
			}else if(ibs.getText1() != null) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + ibs.getText1() + ";");
			}else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP*CONTAINER SET ACTIVE 1;");
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 1;");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP$group*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$ProjectionHead*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected01*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected02*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected03*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$GreenGrp*CONTAINER SET ACTIVE 1;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$BoundariesHead*CONTAINER SET ACTIVE 1;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$FourseGRP*CONTAINER SET ACTIVE 1;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$sixesGRP*CONTAINER SET ACTIVE 1;");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP$group*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$ProjectionHead*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected01*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected02*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected03*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$GreenGrp*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$BoundariesHead*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$FourseGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$sixesGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 1;");
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP$group*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$FreeTextGRP$FreeTExt*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$GreenGrp*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$BoundariesHead*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$FourseGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP$sixesGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected01*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected02*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$Projected03*CONTAINER SET ACTIVE 1;");
				}
				
			    String[] proj_score_rate = new String[projectedScore(match).size()];
			    for (int i = 0; i < projectedScore(match).size(); i++) {
			    	proj_score_rate[i] = projectedScore(match).get(i);
		        }
			    
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$ProjectionGRP$ProjectionHead*CONTAINER SET ACTIVE 1;");
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
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
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
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + logo_path + match.getHomeTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgAwayTeamLogo " + logo_path + match.getAwayTeam().getFullname() + CricketUtil.PNG_EXTENSION + ";");
			
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " + photo_path + match.getHomeTeam().getShortname().toUpperCase() + CricketUtil.DOUBLE_BACKSLASH + hs.getPhoto() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0"+ row_id + " " + hs.getTicker_name() + ";");
					
					if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon7 "+ "1" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + hs.getRole() +" (C)" + ";");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon7 "+ "1" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + hs.getRole() + " (WK)" + ";");
					}
					else if(hs.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon7 "+ "1" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + "(C & WK)" + ";");
					}
					else {
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " + photo_path + match.getAwayTeam().getShortname().toUpperCase() + CricketUtil.DOUBLE_BACKSLASH + as.getPhoto() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0"+ row_id + " " + as.getFirstname() + ";");
					//System.out.println(as.getCaptainWicketKeeper());
					if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.CAPTAIN)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon7 "+ "1" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + as.getRole() +" (C)" + ";");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase(CricketUtil.WICKET_KEEPER)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon7 "+ "1" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + as.getRole() + " (WK)" + ";");
					}
					else if(as.getCaptainWicketKeeper().equalsIgnoreCase("CAPTAIN_WICKET_KEEPER")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgIcon7 "+ "1" + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Both*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Captain*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Keeper*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " + "(C & WK)" + ";");
					}
					else {
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
					if(inn.getInningNumber() == 2 & inn.getIsCurrentInning().equalsIgnoreCase("YES")) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgTeamLogo " + logo_path + inn.getBatting_team().getFullname() + CricketUtil.PNG_EXTENSION + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName " + "TARGET"+ ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getTournament() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*1*CONTAINER SET ACTIVE 0;");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead " + match.getHomeTeam().getShortname() + ";");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue " + "v" + ";");
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateHead1 " + match.getAwayTeam().getShortname() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStateValue1 " + CricketFunctions.generateMatchSummaryStatus(2, match, CricketUtil.FULL) + ";");
					}
				}
			}
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Preview.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
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
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
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
		}
		
	}
	public void populatePlayerSummary(PrintWriter print_writer, String viz_scene,int whichInning, int PlayerId, Match match, String session_selected_broadcaster) 
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
		}
		
	}
	public void populateFallofWicket(PrintWriter print_writer,String viz_scene,int whichInning, Match match, String session_selected_broadcaster)
	{
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

	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			
			break;
		}
		
	}
	public List<String> projectedScore(Match match) {
		//rates= 6,8,10 or 8,10,12
		List<String> proj_score = new ArrayList<String>();
		int PS_Curr=0;
		String PS_1 = "",PS_2 = "",RR_1 = "",RR_2 = "",CRR = "";
		//DecimalFormat df = new DecimalFormat("0.00");
		
		int remaining_overs = (match.getMaxOvers()*6 - (match.getInning().get(0).getTotalOvers()*6 + match.getInning().get(0).getTotalBalls()));
		
		PS_Curr = (int) ((match.getInning().get(0).getTotalRuns() + remaining_overs * Double.valueOf((match.getInning().get(0).getRunRate())))/6);
		CRR = match.getInning().get(0).getRunRate();
		
		proj_score.add(CRR);
		proj_score.add(String.valueOf(PS_Curr));
		
		String[] arr = (match.getInning().get(0).getRunRate().split("\\."));
	    int[] intArr=new int[2];
	    intArr[0]=Integer.parseInt(arr[0]);
	  
		for(int i=2;i<=4;i=i+2) {
			if(i==2) {
				PS_1 = String.valueOf(((match.getInning().get(0).getTotalRuns()) + remaining_overs * (intArr[0] + i))/6);
				RR_1 = String.valueOf((intArr[0] + i));
				
				proj_score.add(RR_1);
				proj_score.add(PS_1);
			}
			else if(i==4) {
				PS_2 = String.valueOf(((match.getInning().get(0).getTotalRuns()) + remaining_overs * (intArr[0] + i))/6);
				RR_2 = String.valueOf((intArr[0] + i));
				
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
}