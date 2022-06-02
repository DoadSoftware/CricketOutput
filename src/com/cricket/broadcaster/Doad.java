package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.util.Collections;

import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;

import com.cricket.model.Partnership;
import com.cricket.model.Player;
import com.cricket.model.Statistics;
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
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "0" + ";");
							break;
	
							case CricketUtil.NOT_OUT:
								//System.out.println("row_no1 = "+row_id);
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0" +row_id + " " +bc.getPlayer().getSurname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "A" + " " + bc.getStatus() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHowOut0"+ row_id + "B" + " " + " " + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRun0" + row_id + " " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBalls0" + row_id + " " + bc.getBalls() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vHighlight_DeHighlight0" + row_id + " " + "1" + ";");
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
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue" + row + "A " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) +";");
						if(match.getMatchType().equalsIgnoreCase(CricketUtil.DT20) || match.getMatchType().equalsIgnoreCase(CricketUtil.D10) || match.getMatchType().equalsIgnoreCase(CricketUtil.IT20)) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatHeadB " +"DOT" +";");
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
							System.out.println(inn.getFallsOfWickets());
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Bowling_Card$10*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Bowling_Card$11*CONTAINER SET ACTIVE 0;");
						}
						else if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
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
								for(i = 1; i <= whichInning; i++) {
									if(i == 1) {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$" + (row_id + 1) + "$NotOut*CONTAINER SET ACTIVE 1;");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanNotOut0"+ row_id + " " + "*" + ";");
									}
									else {
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$" + (row_id + 2) + "$NotOut*CONTAINER SET ACTIVE 1;");
										print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanNotOut0"+ row_id + " " + "*" + ";");
									}
								}
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
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
								if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRuns " + bc.getRuns() + "*" + ";");
								}
								else if (bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)){
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName " + bc.getPlayer().getFull_name() + ";");
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*LastName*CONTAINER SET ACTIVE 0;");
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
	
	public void populateNameSuper(PrintWriter print_writer,String viz_scene, int TeamId, String captainWicketKeeper, int playerId, Match match, String session_selected_broadcaster, String viz_scene_path)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {
			
			String Home_or_Away="";
			
			if(TeamId == match.getHomeTeamId()) {
				Home_or_Away = match.getHomeTeam().getFullname();
				for(Player hs : match.getHomeSquad()) {
					if(playerId == hs.getPlayerId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + hs.getFull_name() + ";");
					}
				}
			}
			else {
				Home_or_Away = match.getAwayTeam().getFullname();
				for(Player as : match.getAwaySquad()) {
					if(playerId == as.getPlayerId()) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + as.getFull_name() + ";");
					}
				}
			}
			
			switch(captainWicketKeeper.toUpperCase())
			{
			case CricketUtil.CAPTAIN: case CricketUtil.WICKET_KEEPER: case "PLAYER_OF_THE_MATCH":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + captainWicketKeeper +" - "+ Home_or_Away + ";");
				break;
			case CricketUtil.PLAYER:
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " + Home_or_Away + ";");
				break;
			case "CAPTAIN-WICKETKEEPER":
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tSubHeader " +"CAPTAIN & WICKETKEEPER" + " - " + Home_or_Away + ";");
				break;
			}

			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/NameSuper.bmp;");
			print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT 1920 1080;");
			print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW OFF;");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	public void populatePlayerProfile(PrintWriter print_writer,String viz_scene, int playerId,String Profile,String TypeofProfile,Statistics stats, Match match, String session_selected_broadcaster, String viz_scene_path) {
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else {

			String path = "D:\\Mumbai_Indians\\Textures and Images\\Player_Images\\PLAYERS_PHOTO_MEDIUM\\" ;

			for(Player hs : match.getHomeSquad()) {
				if(playerId == hs.getPlayerId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerImage "+ path + hs.getPhoto() +".png"+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "+hs.getFull_name()+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$LastName*CONTAINER SET ACTIVE 0;");
			
					switch(TypeofProfile.toUpperCase()) {
						case CricketUtil.BATSMAN:
						switch(Profile.toUpperCase()) {
							case CricketUtil.DT20: case CricketUtil.ODI: case CricketUtil.IT20: case CricketUtil.TEST:
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 "+stats.getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 "+"50s/100s"+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 "+stats.getFifties()+"/"+stats.getHundreds()+";");
							break;
						}
							break;
						case "BOWLER":
						switch(Profile.toUpperCase()) {
							case CricketUtil.DT20: case CricketUtil.ODI: case CricketUtil.IT20: case CricketUtil.TEST:
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead02 "+"Wickets"+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 "+stats.getWickets() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 "+"Best Figures"+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 "+ stats.getBest_figures() +";");
							break;
						}
							break;
					}
				}
			}
	
			for(Player as : match.getAwaySquad()) {
				if(playerId == as.getPlayerId()) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerImage "+ path + as.getPhoto() +".png"+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFirstName "+as.getFull_name()+ ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$LastName*CONTAINER SET ACTIVE 0;");
			
					switch(TypeofProfile.toUpperCase()) {
						case CricketUtil.BATSMAN:
						switch(Profile.toUpperCase()) {
							case CricketUtil.DT20: case CricketUtil.ODI: case CricketUtil.IT20: case CricketUtil.TEST:
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 "+stats.getRuns() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 "+"50s/100s"+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 "+stats.getFifties()+"/"+stats.getHundreds()+";");
							break;
						}
						break;
						case "BOWLER":
						switch(Profile.toUpperCase()) {
							case CricketUtil.DT20: case CricketUtil.ODI: case CricketUtil.IT20: case CricketUtil.TEST:
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue01 "+stats.getMatches()+ ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead02 "+"Wickets"+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue02 "+stats.getWickets() + ";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatsHead03 "+"Best Figures"+";");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tStatValue03 "+ stats.getBest_figures() +";");
							break;
						}
						break;
					}
				}
			}
		print_writer.println("LAYER1*EVEREST*GLOBAL PREVIEW ON;");
		print_writer.println("LAYER1*EVEREST*GLOBAL SNAPSHOT_PATH C:/Temp/PlayerProfile.bmp;");
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
	public void populateInfobar(PrintWriter print_writer,String viz_scene, String TopLeftStats, String TopRightStats, 
			String BottomLeftStats, String BottomRightStats, Match match, String session_selected_broadcaster)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Infobar's inning is null";
		} else {

			populateInfobarTeamScore(false, print_writer, match, session_selected_broadcaster);
			populateInfobarTopLeft(false, print_writer, TopLeftStats, match, session_selected_broadcaster);
			populateInfobarTopRight(false, print_writer, TopRightStats, match, session_selected_broadcaster);
			
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
					}
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatTeamName " + inn.getBatting_team().getShortname().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamScore " + inn.getTotalRuns() + "-" + inn.getTotalWickets() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamOvers " + CricketFunctions.OverBalls(inn.getTotalOvers(), inn.getTotalBalls())+ ";");
				}
			}
			break;
		}
	}
	public void populateInfobarTopRight(boolean is_this_updating, PrintWriter print_writer, String TopRightStats, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			switch(TopRightStats.toUpperCase()) {
			case "BOWLER":
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(is_this_updating == false) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$BowlingScoreGrp$BowlerName*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$BowlingScoreGrp$BowlerFigure*CONTAINER SET ACTIVE 1;");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$BowlingScoreGrp$Overs*CONTAINER SET ACTIVE 1;");
							}
							if(boc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.CURRENT_BOWLER) || boc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.LAST_BOWLER)) {
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + boc.getPlayer().getSurname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerFigure " + boc.getWickets() + "-" + boc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerOvers " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + ";");
							}
						}
					}
				}
				break;
			case "THIS_OVER":
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						if(is_this_updating == false) {
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$BowlingScoreGrp$BowlerName*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$BowlingScoreGrp$BowlerFigure*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$BowlingScoreGrp$Overs*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$BowlingScoreGrp$BowlerName*CONTAINER SET ACTIVE 1;");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " + CricketFunctions.getEventsText(CricketUtil.OVER, ",",match.getEvents() ) + ";");
						break;
					}
				}
			}
			break;
		}
	}
	public void populateInfobarTopLeft(boolean is_this_updating, PrintWriter print_writer, String TopLeftStats, Match match, String session_selected_broadcaster)
	{
		switch (session_selected_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			switch (TopLeftStats.toUpperCase()) {
			case CricketUtil.BATSMAN:
				int row_id = 0;
				for(Inning inn : match.getInning()) {
					if (inn.getIsCurrentInning().toUpperCase().equalsIgnoreCase(CricketUtil.YES)) {
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								row_id = row_id + 1;
								if(is_this_updating == false) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$TOPGRP$Batsman_Grp$group$Batsman0" + row_id + "Grp*CONTAINER SET ACTIVE 1;");
								}
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanName" + row_id + " " + bc.getPlayer().getSurname() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanScore" + row_id + " " + bc.getRuns() + ";");
								print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBatsmanBall" + row_id + " " + bc.getBalls() + ";");
								
								if(row_id == 1 && bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike" + " " + "0" + ";");
								}
								else if(row_id == 2 && bc.getOnStrike().equalsIgnoreCase(CricketUtil.YES)) {
									print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET vOnStrike" + " " + "1" + ";");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 1;");
						}
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBallTeamName " + inn.getBowling_team().getShortname().toUpperCase() + ";");
					}
				}
				break;
			case "CURRENT_RUN_RATE":
				for(Inning inn : match.getInning()) {
					if(is_this_updating == false) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP*CONTAINER SET ACTIVE 0;");
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 1;");
					}
					if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
						print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tCurrentRunRate " + inn.getRunRate() + ";");
					}
				}
				break;
			case"TARGET":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 1;");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTargetScore " + CricketFunctions.getTargetRuns(match) + ";");
				break;
			case"REQUIRED_RUN_RATE":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP*CONTAINER SET ACTIVE 1;");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tRequiredRunRate " + 
					CricketFunctions.GenerateRunRate(CricketFunctions.getRequiredRuns(match), 0, CricketFunctions.getRequiredBalls(match), 2) + ";");
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 1;");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTossResult " + CricketFunctions.TossResult(match, "", "", CricketUtil.SHORT) + ";");
				break;
			case "EQUATION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP*CONTAINER SET ACTIVE 0;");
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
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFreeTextSmallText " + CricketFunctions.generateMatchSummaryStatus(2, match, CricketUtil.FULL) + ";");
					//System.out.println(CricketFunctions.generateMatchSummaryStatus(2, match, CricketUtil.FULL));
				}
				else{
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedRun " + CricketFunctions.getRequiredRuns(match) + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tNeedBall " + CricketFunctions.getRequiredBalls(match) + ";");
				}
				break;
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP*CONTAINER SET ACTIVE 1;");
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
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 0;");
							print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP*CONTAINER SET ACTIVE 0;");
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
			case"COMPARISION":
				if(is_this_updating == false) {
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TeamBGRP$group*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$TargetGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$CurrentRRGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio4_data_GRP$ReqRRGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$TossGRP$TOSS*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$EquationGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$BoundariesGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio6_data_GRP$PartnershipGRP*CONTAINER SET ACTIVE 0;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Section4_5_6GRP$Sectio5_data_GRP$ComparisonGRP*CONTAINER SET ACTIVE 1;");
				}
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tBowlerName " +   ";");
				break;
			}
			break;
		
		}
		
	}
	public void populateMatchId(PrintWriter print_writer,String viz_scene, Match match, String session_selected_broadcaster, String viz_scene_path)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: MatchId's inning is null";
		} else {
			String path = "C:\\Everest_Scenes\\Mumbai_Indians\\Textures and Images\\Logos\\" ;
			
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tHeader " + match.getMatchIdent().toUpperCase() + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgHomeTeamLogo " + path + match.getHomeTeam().getFullname() + ".png" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgtAwayTeamLogo " + path + match.getAwayTeam().getFullname() + ".png" + ";");
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tFooter " + " " + ";");
			
			this.status = CricketUtil.SUCCESSFUL;	
		}
	}
	public void populatePlayingXI(PrintWriter print_writer,String viz_scene, int TeamId,   Match match, String session_selected_broadcaster, String viz_scene_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: PlayingXI's inning is null";
		} else {
			int row_id = 0;
			String path = "C:\\Everest_Scenes\\Mumbai_Indians\\Textures and Images\\MUMBAI INDIANS PNG - 23 Players\\MI 1024 MEDIUM\\" ;
			if(TeamId == match.getHomeTeamId()) {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getHomeTeam().getFullname() + ";");
				for(Player hs : match.getHomeSquad()) {
					row_id = row_id + 1;
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Image1_GRP$Image*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$Second_Six$Player" + row_id + "_GRP$group$Image1_GRP$Image*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Player Role*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main$Data$PlayerImage_Name_GRP$First_Five$Player" + row_id + "_GRP$group$Player NAME01*CONTAINER SET ACTIVE 1;");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " + path + hs.getPhoto() + ".png" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0"+ row_id + " " +hs.getAbbreviated_name().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " +hs.getRole() + ";");
				}
			}
			else {
				print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tTeamFirstName " + match.getAwayTeam().getFullname() + ";");
				for(Player as : match.getAwaySquad()) {
					row_id = row_id + 1;
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET lgPlayerImage0"+ row_id + " " + path + as.getPhoto() + ".png" + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerName0"+ row_id + " " +as.getAbbreviated_name().toUpperCase() + ";");
					print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tPlayerRole0"+ row_id + " " +as.getRole() + ";");
				}
				
			}
				
			print_writer.println("LAYER1*EVEREST*TREEVIEW*Main*FUNCTION*TAG_CONTROL SET tResult " + CricketFunctions.TossResult(match, "", "", CricketUtil.SHORT) + ";");
			}
			
			this.status = CricketUtil.SUCCESSFUL;
	}

	public void processAnimation(PrintWriter print_writer, String animationName,String animationCommand, String which_broadcaster)
	{
		switch(which_broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*" + animationName + " " + animationCommand + ";");
			//this.status = CricketUtil.SUCCESSFUL;
			break;
		}
		
	}

	@Override
	public String toString() {
		return "Doad [status=" + status + ", slashOrDash=" + slashOrDash + "]";
	}
}