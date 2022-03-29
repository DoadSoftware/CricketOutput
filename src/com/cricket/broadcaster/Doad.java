package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Partnership;
import com.cricket.model.Inning;
import com.cricket.model.Match;
import com.cricket.model.FallOfWicket;
import com.cricket.containers.Scene;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;

@SuppressWarnings("unused")
public class Doad extends Scene{

	private String status;
	
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
	
	public void populateScorecard(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Scorecard's inning is null";
		} else {

			int row_id = 0, omo_num = 0;
			String cont_name= "";

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$LanguageGrp$Langauage1*GEOM*TEXT SET " + match.getTournament() + "/0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BatHeader$MatchId$LanguageGrp$Langauage1*GEOM*TEXT SET " + match.getMatchIdent() + "/0");
			
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if (inn.getBattingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$LanguageGrp$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$LanguageGrp$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$LanguageGrp$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$LanguageGrp$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						
						row_id = row_id + 1;
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
						
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row"+row_id+"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row"+row_id+"$PlayerGrp$LeftText$LanguageGrp$Language1$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							
							break;

						default:

							switch (bc.getStatus().toUpperCase()) {
							case CricketUtil.OUT:
								omo_num = 2;
								cont_name = "Dehighlight";
								break;
							case CricketUtil.NOT_OUT:
								omo_num = 3;
								cont_name = "Highlight";
								break;
							}

							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$LeftText$LanguageGrp$Language1$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$LeftText$LanguageGrp$Language1$PlayerRuns*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$LeftText$LanguageGrp$Language1$PlayerBalls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$WicketPlayerName*GEOM*TEXT SET " + bc.getStatus() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$BallPlayerName*GEOM*TEXT SET " + " " + "\0");
								
							}
								

						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			
			this.status = "SUCCESS";
		}
		
	}
	public void populateBowlingcard(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Bowlingcard's inning is null";
		} else {

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$LanguageGrp$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BallHeader$MatchId$LanguageGrp$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			
			int row_id = 0; 
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {

					if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$LanguageGrp$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$LanguageGrp$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$LanguageGrp$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$LanguageGrp$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
					}

					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$BowlingData$DataAll$DataAllGrp*FUNCTION*Omo*vis_con SET " + inn.getBowlingCard().size()+"\0");

					for (BowlingCard boc : inn.getBowlingCard()) {
						
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$LeftText$LanguageGrp$Language1$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getSurname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$LeftText$LanguageGrp$Language1$PlayerRuns*GEOM*TEXT SET " + boc.getWickets() + '-' + String.valueOf(boc.getRuns())  + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$RightText$LanguageGrp$Value$OverValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$RightText$LanguageGrp$Value$MaidensValue*GEOM*TEXT SET " + boc.getDots() +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$RightText$LanguageGrp$Value$ExtraValue*GEOM*TEXT SET " + String.valueOf(boc.getWides() + boc.getNoBalls()) +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$RightText$LanguageGrp$Value$EconomyValue*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
						
					}
					
					if(inn.getBowlingCard().size()<=8) {
						if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {
								if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$FowGrp$Fow2$RowAnim$FowValues1$FowValue"+fow.getFowNumber()+"*GEOM*TEXT SET "+fow.getFowNumber()+"-"+fow.getFowRuns()+"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$FowGrp$Fow3$RowAnim$FowValues2$FowValue"+fow.getFowNumber()+"*GEOM*TEXT SET "+fow.getFowNumber()+"-"+fow.getFowRuns()+"\0");
									
									for(int value=10; inn.getTotalWickets() < value;value--) {
										if(value < 6) {
											print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$FowGrp$Fow2$RowAnim$FowValues1$FowValue"+value+"*GEOM*TEXT SET "+" "+"\0");
										}
										else {
											print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$FowGrp$Fow3$RowAnim$FowValues2$FowValue"+value+"*GEOM*TEXT SET "+" "+"\0");
										}
										
									}	
								}		
							}
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$BottomInfoPosition$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$BottomInfoPosition$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			this.status = "SUCCESS";
		}
	}
	
	public void populatePartnership(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			
			int row_id = 0, omo_num = 0;
			String cont_name= "",Left_Batsman = "",Right_Batsman="";

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$LanguageGrp$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$PartHeader$MatchId$LanguageGrp$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			
 
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {

					if (inn.getBattingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$LanguageGrp$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$LanguageGrp$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$LanguageGrp$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$PartHeader$LanguageGrp$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
					}

					for (Partnership ps : inn.getPartnerships()) {
						
						row_id = row_id + 1;
						
						for (BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == ps.getFirstBatterNo()) {
								Left_Batsman = bc.getPlayer().getSurname();
							}
							else if(bc.getPlayerId() == ps.getSecondBatterNo()) {
								Right_Batsman = bc.getPlayer().getSurname();
							}
						}
						
						if(ps.getPartnershipNumber()<=inn.getTotalWickets()) {
							omo_num = 4;
							cont_name = "Highlight";
						}
						else if(ps.getPartnershipNumber() > inn.getTotalWickets()) {
							omo_num = 3;
							cont_name = "Dehighlight";
						}
						
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$RowAnim$PartOmo*FUNCTION*Omo*vis_con SET "+String.valueOf(omo_num)+ " \0");
						
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$RowAnim$PartOmo$"+cont_name+"$LeftPlayeNameAll$LeftPlayeName*GEOM*TEXT SET " + Left_Batsman + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$RowAnim$PartOmo$"+cont_name+"$RightPlayeNameAll$RightPlayeName*GEOM*TEXT SET " + Right_Batsman + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$RowAnim$PartOmo$"+cont_name+"$ScoreGrp$PlayerRuns*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$RowAnim$PartOmo$"+cont_name+"$ScoreGrp$PlayerBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
					}
					
					for (BattingCard bc : inn.getBattingCard()) {
						row_id = row_id + 1;
						if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$RowAnim$PartOmo*FUNCTION*Omo*vis_con SET 2"+ " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$RowAnim$PartOmo$StillToBatPlayerGrp$LeftPlayeNameAll$LeftPlayeName*GEOM*TEXT SET"+bc.getPlayer().getSurname()+" \0");
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$PartnershipData$BottomInfoPosition$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$PartnershipData$BottomInfoPosition$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$PartnershipData$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$PartnershipData$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			this.status = "SUCCESS";
		}
	}
	
	public void populateMatchsummary(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Partnership's inning is null";
		} else {
			
			int row_id = 0,a;
			String Highest1="",Highest2="";

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$LanguageGrp$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$PartHeader$MatchId$LanguageGrp$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning && whichInning == 1) {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 1 \0");
					
					for (BattingCard bc : inn.getBattingCard()) {
						List<Integer> Run_List=Arrays.asList(bc.getRuns());  
						Collections.sort(Run_List);
						System.out.println(Run_List);
					}
					for (BowlingCard boc : inn.getBowlingCard()) {
						
					}
					
					if (inn.getBattingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$1Innings$Row1$RowAnim$TeamTextAll1$TeamLanguageGrp$TeamName1*GEOM*TEXT SET " + match.getHomeTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$1Innings$Row1$RowAnim$TeamTextAll1$TeamLanguageGrp$TeamName1*GEOM*TEXT SET " + match.getAwayTeam().getFullname() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$1Innings$Row1$RowAnim$TeamTextAll1$InningsLanguageGrp$Innings1*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$1Innings$Row1$RowAnim$TeamTextAll1$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
						
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$1Innings$Row1$RowAnim$TeamTextAll1$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + String.valueOf(inn.getTotalWickets()) + "\0");
						
					}
					
					
				}
				
			}
 
			
			this.status = "SUCCESS";
		}
	}
}

