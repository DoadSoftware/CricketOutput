package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.util.Collections;

import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;

import com.cricket.model.EventFile;
import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Inning;
import com.cricket.model.Match;
import com.cricket.model.FallOfWicket;
import com.cricket.containers.Scene;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;

public class Doad extends Scene{

	private String status;
	private String slashOrDash = "-";
	
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
	
	public void populateScorecard(PrintWriter print_writer, String viz_scene, int whichInning, Match match, String session_selected_broadcaster, String viz_scene_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Scorecard's inning is null";
		} else {

			int row_id = 0 ;
			//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Language1*GEOM*TEXT SET " + match.getTournament() + "\0");
			//print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BatHeader$MatchId$Language1*GEOM*TEXT SET " + match.getMatchIdent() + "\0");
			
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getAwayTeam().getFullname() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getHomeTeam().getFullname() + ";");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						
						row_id = row_id + 1;

						if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)){
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_id + " " + bc.getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$HowOut_GRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Runs*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Balls*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Highlight*CONTAINER SET ACTIVE 0;");
						}
						else {
							switch (bc.getStatus().toUpperCase()) {
	
							case CricketUtil.OUT:
								//System.out.println("row_no = "+row_id);
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" +row_id + " " +bc.getPlayer().getSurname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getHowOutPartOne() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + bc.getHowOutPartTwo() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Highlight$Orange*CONTAINER SET ACTIVE 0;");
							break;
	
							case CricketUtil.NOT_OUT:
								//System.out.println("row_no1 = "+row_id);
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" +row_id + " " +bc.getPlayer().getSurname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getStatus() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$"+row_id+"$Highlight$Orange*CONTAINER SET ACTIVE 1;");
							break;
						}
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
			
			//print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/Battingcard.png In 1.400 BattingCardIn 2.600 \0");
			this.status = CricketUtil.SUCCESSFUL;
		}
	}
	public void populateBowlingcard(PrintWriter print_writer,String viz_scene, int whichInning,   Match match, String session_selected_broadcaster, String viz_scene_path) 
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
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getHomeTeam().getFullname() + ";");
					} else {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getAwayTeam().getFullname() + ";");
					}

					for (BowlingCard boc : inn.getBowlingCard()) {
						row_id = row_id + 1;
						row = row + 1;

						print_writer.println("LAYER1*EVEREST*TREEVIEW*"+ row + "*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" + row_id + " "  + boc.getPlayer().getSurname() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "A " + boc.getOvers() +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "B " + boc.getMaidens() +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "C " + boc.getRuns() +";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "D " + boc.getWickets() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "E " + boc.getEconomyRate() + ";");
						
					}
					for(int j = row + 1; j <= max_Strap; j++) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*"+ j + "*CONTAINER SET ACTIVE 0;");
					}
					row_id= 0 ;
					if(inn.getBowlingCard().size()<=8) {
						if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {								
								if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
									row_id = row_id + 1;
									//System.out.println("row3="+row_id);
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vWKTS "+ fow.getFowNumber() + ";");
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tWKTRun0" + row_id + " "  + fow.getFowRuns() + ";");
								}		
							}
						}
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tOvers " + inn.getTotalOvers() + ";");
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
			
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Bowling_Card.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;
		}
	}

	public void populatePartnership(PrintWriter print_writer, String viz_scene, int whichInning, Match match, String session_selected_broadcaster, String viz_scene_path) 
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
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/partnership.png In 1.400 PartnershipIn 2.800 \0");
			
			this.status = CricketUtil.SUCCESSFUL;
		}
	}
	
	public void populateMatchsummary(PrintWriter print_writer, String viz_scene, int whichInning, Match match, String session_selected_broadcaster, String viz_scene_path)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
		} else {
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getTournament() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + match.getMatchIdent() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tMatchName " + match.getHomeTeam().getShortname().toUpperCase() + " V " + match.getAwayTeam().getShortname().toUpperCase() + ";");
			
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName0"+ row_id + " " + bc.getPlayer().getSurname() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanRuns0"+ row_id + " " + bc.getRuns() + ";");
							
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*NotOut*CONTAINER SET ACTIVE 1;");
							} else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*NotOut*CONTAINER SET ACTIVE 0;");
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
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Summary.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		
		}
	}

	public void populateBug(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster, String viz_scene_path)
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamInitials " + bc.getRuns() + "-" + String.valueOf(bc.getBalls()) + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDesicion " + "4s -" + bc.getFours() + " | "  + "6s -"  + bc.getSixes() + ";");
							if(bc.getPlayerId()==playerId) {
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									//System.out.println("Player:" + bc.getPlayer().getFull_name());
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossResult " + bc.getPlayer().getFull_name() + "*" + ";");
								}
								else {
									//System.out.println("Player:" + bc.getPlayer().getFull_name());
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossResult " + bc.getPlayer().getFull_name() + ";");
								}
							}
						}
						break;
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamInitials " + boc.getWickets() + slashOrDash + boc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossResult " + boc.getPlayer().getFull_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tDesicion " + "Overs:" + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
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
	
	public void populateHowout(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster, String viz_scene_path)
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + bc.getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + bc.getBalls() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut " + bc.getHowOutPartOne() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowler " + bc.getHowOutPartTwo() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
						}
					}
				}
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/HowOut.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	
	public void populatePlayerstats(PrintWriter print_writer,String viz_scene, int whichInning, String statsType, int playerId, Match match, String session_selected_broadcaster, String viz_scene_path)
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
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId()==playerId) {
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name()+"*" + ";");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + bc.getBalls() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*HowOut_GRP*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + bc.getFours() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + bc.getSixes() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + bc.getStrikeRate() + ";");
							}
						}
						break;
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + boc.getPlayer().getFull_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + boc.getWickets() + slashOrDash + boc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*HowOut_GRP*CONTAINER SET ACTIVE 0;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead01 " + "0s" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 " + boc.getDots() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead02 " + "Ext" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 " + (boc.getWides() + boc.getNoBalls()) + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHead03 " + "Economy:" + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 " + boc.getEconomyRate() + ";");
							}
						}
						break;
					}
				}
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/PlayerStats.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	
	public void populatenamesuper(PrintWriter print_writer,String viz_scene, int TeamId, String captainWicketKeeper, int playerId, Match match, String session_selected_broadcaster, String viz_scene_path)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			if(TeamId == match.getHomeTeamId()) {
				for(Player hs : match.getHomeSquad()) {
					if(playerId == hs.getPlayerId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + hs.getFull_name() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + captainWicketKeeper +" - "+ match.getHomeTeam().getFullname() + ";");
			}
			else {
				for(Player as : match.getAwaySquad()) {
					if(playerId == as.getPlayerId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + as.getFull_name() + ";");
					}
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + captainWicketKeeper +" - "+ match.getAwayTeam().getFullname() + ";");
			}

			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/NameSuper.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	public void populateDoubleteams(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster, String viz_scene_path)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: DoubleTeam's inning is null";
		} else {
			
			int row_id = 0;
			String cont="";
			
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Language1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$HeaderTextAll$BattingTeamName*GEOM*TEXT SET " + "TEAMS" + "\0");
			
			for(int i = 1; i <= 2 ; i++) {
				if(i == 1) {
					for(Player hs : match.getHomeSquad()) {
						row_id = row_id + 1;
						cont = "";
						if(hs.getCaptainWicketKeeper() != null ) {
							switch(hs.getCaptainWicketKeeper().toUpperCase()) {
							case CricketUtil.CAPTAIN:
								cont = "(c)";
								break;
							case CricketUtil.WICKET_KEEPER:
								cont = "(wk)";
								break;
							case "CAPTAIN_WICKET_KEEPER":
								cont = "(c/wk)";
								break;
							}
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAnim$BatOmo$Dehighlight$LeftText$LanguageGrp$Language1$PlayerName*GEOM*TEXT SET " + hs.getFull_name().toUpperCase() + " " + cont + "\0");
						}
						}
				} else {
					row_id = 0;
					for(Player as : match.getAwaySquad()) {
						row_id = row_id + 1;
						cont = "";
						if(as.getCaptainWicketKeeper() != null ) {
							switch(as.getCaptainWicketKeeper().toUpperCase()) {
							case CricketUtil.CAPTAIN:
								cont = "(c)";
								break;
							case CricketUtil.WICKET_KEEPER:
								cont = "(wk)";
								break;
							case "CAPTAIN_WICKET_KEEPER":
								cont = "(c/wk)";
								break;
							}
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAnim$BatOmo$Dehighlight$RightText$LanguageGrp$Language1$PlayerDesignation*GEOM*TEXT SET " + as.getFull_name().toUpperCase() +" " + cont + "\0");
						}
					}
				}
			}
			print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$noname1$TotalGrp$TotalScore*GEOM*TEXT SET " +"Extras - " +match.getInning().get(0).getTotalExtras() +" Total - "+ match.getInning().get(0).getTotalRuns() + "\0");
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/Doubleteams.png In 2.400 \0");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	public void populateInfobar(PrintWriter print_writer,String viz_scene, String TopLeftStats, String TopRightStats, String BottomLeftStats, String BottomRightStats, Match match, String session_selected_broadcaster, EventFile event, String viz_scene_path)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Infobar's inning is null";
		} else {
			int total_inn = 0,row_id = 0;
			
			for(Inning inn : match.getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			for(Inning inn : match.getInning()) {
				if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getShortname().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls())+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*PowerPlay*CONTAINER SET ACTIVE 0;");
					
					switch(TopLeftStats.toUpperCase()) {
					case CricketUtil.BATSMAN :
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								row_id = row_id + 1;
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName" + row_id + " " + bc.getPlayer().getFull_name() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanScore" + row_id + " " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall" + row_id + " " + bc.getBalls() + ";");
							}
							if(bc.getOnStrike() == CricketUtil.YES) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*StrikeGrp*CONTAINER SET ACTIVE 1;");
							}
							else {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*StrikeGrp*CONTAINER SET ACTIVE 0;");
							}
							
						}
						break;
					}
					populateInfobarBottomLeft(print_writer, BottomLeftStats, inn, session_selected_broadcaster);
					populateInfobarBottomRight(print_writer, BottomRightStats, match, session_selected_broadcaster);
					/*switch(TopRightStats.toUpperCase()) {
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.CURRENT_BOWLER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + boc.getPlayer().getSurname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigure " + boc.getWickets() + "-" + boc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOvers " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
							}
						}
						break;
					case "THISOVER":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + CricketFunctions.getEventsText(CricketUtil.OVER, ",",event.getEvents() ) + ";");
						break;
					}
					switch(BottomLeftStats.toUpperCase()) {
					case"CRR":
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*CurrentRRGRP*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRate " + inn.getRunRate() + ";");
						break;
					case"TEAMB":
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*group*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + inn.getBowling_team().getFullname() + ";");
						break;
					case"TARGET":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*TargetGRP*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetScore " + CricketFunctions.getTargetRuns(match) + ";");
						break;
					case"RRR":
						//print_writer.println("LAYER1*EVEREST*TREEVIEW*ReqRRGRP*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRequiredRunRate " + getRequiredRunRate(match) + ";");
						break;
					}
					switch(BottomRightStats.toUpperCase()) {
					case"TOSS WINNING":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*TOSS*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossResult " + CricketFunctions.TossResult(match, "SHORT", "FIELD", "FULL") + ";");
						break;
					case"BOUNDARIES":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*BoundariesGRP*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFoursValue " + inn.getTotalFours() + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSixValue " + inn.getTotalSixes() + ";");
						break;
					case"PARTNERSHIP":
						for (Partnership ps : inn.getPartnerships()) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*PartnershipGRP*CONTAINER SET ACTIVE 1;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer1Runs " + ps.getFirstBatterRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer1Balls " + ps.getFirstBatterBalls() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipPlayer2Runs " + ps.getSecondBatterRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershiplPlayer2Balls " + ps.getSecondBatterBalls() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipRuns " + ps.getTotalRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPartnershipBalls " + ps.getTotalBalls() + ";");
						}
						break;
					case"COMPARISION":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*ComparisionGRP*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +   ";");
						break;
					case"EQUATION":
						print_writer.println("LAYER1*EVEREST*TREEVIEW*EquationGRP*CONTAINER SET ACTIVE 1;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.getRequiredRuns(match) + ";");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.getRequiredBalls(match) + ";");
						break;
					}*/
				}
			}
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/Infobar.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	

	public void populateInfobarBottomLeft(PrintWriter print_writer, String BottomLeftStats, Inning inning, String session_selected_broadcaster)
	{
		switch(BottomLeftStats.toUpperCase()) {
		case "VS_BOWLING_TEAM":
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + inning.getBowling_team().getShortname().toUpperCase() + ";");
			break;
		case "CURRENT_RUN_RATE":
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRate " + inning.getRunRate() + ";");
			break;
		}
	
	}
	public void populateInfobarBottomRight(PrintWriter print_writer, String BottomRightStats, Match match, String session_selected_broadcaster)
	{
	
		switch(BottomRightStats.toUpperCase()) {
		case "TOSS_WINNING":
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$group*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossResult " + CricketFunctions.TossResult(match, "SHORT", "FIELD", "FULL") + ";");
			break;
		case "EQUATION":
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$group*CONTAINER SET ACTIVE 0;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 1;");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.getRequiredRuns(match) + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.getRequiredBalls(match) + ";");
			break;
		}

	}
	public void AnimationProcess(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster) {
		case "DOAD_IN_HOUSE_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			break;
		}
		
	}
	public void AnimateInGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic) {
		case "SCORECARD":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BOWLINGCARD":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "PARTNERSHIP":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "MATCHSUMMARY":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BUG":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "HOWOUT":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "PLAYERSTATS":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "NAMESUPER":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "DOUBLETEAMS":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "INFOBAR":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In START;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}	
	}
	public void AnimateOutGraphics(PrintWriter print_writer, String whichGraphic)
	{
		switch(whichGraphic) {
		case "BATBALLSUMMARY_SCORECARD":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BATBALLSUMMARY_BOWLINGCARD":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BATBALLSUMMARY_PARTNERSHIP":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BATBALLSUMMARY_MATCHSUMMARY":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "BUG":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "HOWOUT":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "PLAYERSTATS":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;	
		case "NAMESUPER":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "DOUBLETEAMS":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		case "INFOBAR":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In CONTINUE;");
			this.status = CricketUtil.SUCCESSFUL;
			break;
		}	
	}
	public static int getRequiredRunRate(Match match) {
		int requiredRunRate = CricketFunctions.getRequiredRuns(match) / CricketFunctions.getRequiredBalls(match) * 6;
	    if (requiredRunRate <= 0) {
	    	requiredRunRate = 0;
	    }
	    return requiredRunRate;
	}
}