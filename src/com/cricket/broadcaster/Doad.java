package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

	private static final String BottomLine = null;
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
	String slashOrDash = "-";
	public void populateScorecard(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Scorecard's inning is null";
		} else {

			int row_id = 0, omo_num = 0;
			String cont_name= "";

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Langauage1*GEOM*TEXT SET " + match.getTournament() + "/0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BatHeader$MatchId$Langauage1*GEOM*TEXT SET " + match.getMatchIdent() + "/0");
			
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {
					if (inn.getBattingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
					}
					for (BattingCard bc : inn.getBattingCard()) {
						
						row_id = row_id + 1;
						
						switch (bc.getStatus().toUpperCase()) {
						case CricketUtil.STILL_TO_BAT:
						
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row"+row_id+"$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row"+row_id+"$PlayerGrp$LeftText$Language1$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							
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

							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$" + cont_name + "$LeftText$Language1$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$" + cont_name + "$LeftText$Language1$PlayerRuns*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$" + cont_name + "$LeftText$Language1$PlayerBalls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$" + cont_name + "$RightText$Language1$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$" + cont_name + "$RightText$Language1$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$" + cont_name + "$RightText$Language1$WicketPlayerName*GEOM*TEXT SET " + bc.getStatus() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$" + cont_name + "$RightText$Language1$BallPlayerName*GEOM*TEXT SET " + " " + "\0");		
							}
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$BattingData$BottomInfoPosition$ExtrasGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$BattingData$BottomInfoPosition$OversGrp$Language1$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingData$BottomInfoPosition$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingData$BottomInfoPosition$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
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

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BallHeader$MatchId$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			
			int row_id = 0; 
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {

					if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BallHeader$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
					}

					print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAllGrp*FUNCTION*Omo*vis_con SET " + inn.getBowlingCard().size()+"\0");

					for (BowlingCard boc : inn.getBowlingCard()) {
						row_id = row_id + 1;
					
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$Row" + row_id + "$Dehighlight$LeftText$Language1$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getSurname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$Row" + row_id + "$Dehighlight$LeftText$Language1$PlayerRuns*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + String.valueOf(boc.getRuns())  + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$Row" + row_id + "$Dehighlight$RightText$Value$OverValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(),boc.getBalls()) +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$Row" + row_id + "$Dehighlight$RightText$Value$MaidensValue*GEOM*TEXT SET " + boc.getDots() +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$Row" + row_id + "$Dehighlight$RightText$Value$ExtraValue*GEOM*TEXT SET " + String.valueOf(boc.getWides() + boc.getNoBalls()) +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$Row" + row_id + "$Dehighlight$RightText$Value$EconomyValue*GEOM*TEXT SET " + boc.getEconomyRate() + "\0");
						
					}
					
					if(inn.getBowlingCard().size()<=8) {
						if(inn.getFallsOfWickets() != null || inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {								
								if(inn.getTotalWickets()>=0 && inn.getTotalWickets() <= 10) {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow2$FowValues1$FowValue"+fow.getFowNumber()+"*GEOM*TEXT SET "+fow.getFowNumber()+slashOrDash+fow.getFowRuns()+"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow3$FowValues2$FowValue"+fow.getFowNumber()+"*GEOM*TEXT SET "+fow.getFowNumber()+slashOrDash+fow.getFowRuns()+"\0");
									
									//remove the best over from the bowling card for now
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$group*Active SET 0"+" "+"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$BestHead$Language1*GEOM*TEXT SET "+" "+"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$BestValue*GEOM*TEXT SET "+" "+"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$BestOver$Language1*GEOM*TEXT SET "+" "+"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$PlayerFirstName$Language1*GEOM*TEXT SET "+" "+"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$PlayerLastName$Language1*GEOM*TEXT SET "+" "+"\0");
									
									for(int value=10; inn.getTotalWickets() < value;value--) {
										if(value < 6) {
											print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow2$FowValues1$FowValue"+value+"*GEOM*TEXT SET "+" "+"\0");
										}
										else {
											print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow3$FowValues2$FowValue"+value+"*GEOM*TEXT SET "+" "+"\0");
										}
										
									}	
								}		
							}
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$BottomInfoPosition$ExtrasGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$BottomInfoPosition$OversGrp$Language1$OversValue*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$BottomInfoPosition$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$BottomInfoPosition$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");
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
						
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET "+String.valueOf(omo_num)+ " \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$LeftPlayeName*GEOM*TEXT SET " + Left_Batsman + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$RightPlayeName*GEOM*TEXT SET " + Right_Batsman + "\0");
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
								else if(bc.getStatus().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
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
			this.status = "SUCCESS";
		}
	}
	
	public void populateMatchsummary(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path)
	{
		//print_writer.println("RENDERER PREVIEW SCENE*"<viz_scene_path>+ " " & "C:/Temp/match.png " & " " & "anim_Infobar$Change$Left 0.700");
		
		print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " " + "C:/Temp/matchsummary.png " + " " + "In 1.400 SummaryIn 2.400 \0");
		if (match == null) {
			this.status = "ERROR: Match is null";
		} 
		else if (match.getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
		} 
		else {
			int total_inn = 0;
			for(Inning inn : match.getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
				if(whichInning > total_inn) {
					whichInning = total_inn;
				}
			
			int row_id = 0; String teamname = ""; int total_runs = 0; String overs_text = "" ; int max_Strap = 0;
			/*print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$SummaryData$DataAll$1Innings*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$SummaryData$DataAll$2Innings*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$SummaryData$DataAll$3Innings*ACTIVE SET 0 \0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$SummaryData$DataAll$4Innings*ACTIVE SET 0 \0");*/
			//print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET " + whichInning + "\0");
			for(int i=1; i<=4;i++) {
				if(i == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$SummaryData$DataAll$"+ i +"Innings*ACTIVE SET 1 \0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$SummaryData$DataAll$"+ i +"Innings*ACTIVE SET 0 \0");
				}
			}
			//System.out.println("Inn"+ whichInning);
			for(int i = 1; i <= whichInning ; i++) {
				if(i == 1) {
					row_id = 0;
					//print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$BottomInfoPosition$InfoTextAll$InfoText*GEOM*TEXT SET " + generateMatchSummaryStatus(whichInning, match, total_inn, i, i, whichInning) + " \0");
					
				}
				else {
					row_id = 5;
					//print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 2 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$BottomInfoPosition$InfoTextAll$InfoText*GEOM*TEXT SET " + generateMatchSummaryStatus(whichInning, match, total_inn, i, i, whichInning) + " \0");
				}
				row_id = row_id + 1;
				//Toss
				if(match.getTossWinningTeam() == match.getInning().get(i-1).getBattingTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$" + whichInning + "Innings$Row" + row_id + "$Toss*FUNCTION*Omo*vis_con SET 1 \0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$" + whichInning + "Innings$Row" + row_id + "$Toss*FUNCTION*Omo*vis_con SET 0 \0");
				}
				overs_text = CricketFunctions.OverBalls(match.getInning().get(i-1).getTotalOvers(),match.getInning().get(i-1).getTotalBalls());
				total_runs = match.getInning().get(i-1).getTotalRuns();
				
				if(match.getInning().get(i-1).getBattingTeamId() == match.getHomeTeamId()) {
					
					teamname = match.getHomeTeam().getFullname();	
				}
				else {
					
					teamname = match.getAwayTeam().getFullname();
				}
				print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TeamName1*GEOM*TEXT SET " + teamname + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$Innings1*GEOM*TEXT SET " + "Over " + overs_text + "\0");
				if(match.getInning().get(i-1).getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TotalGrp$TotalScore*GEOM*TEXT SET " + total_runs + "\0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TotalGrp$TotalScore*GEOM*TEXT SET " + total_runs + slashOrDash + String.valueOf(match.getInning().get(i-1).getTotalWickets()) + "\0");	
				}
				
				Collections.sort(match.getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
				for(BattingCard bc : match.getInning().get(i-1).getBattingCard()) {
					row_id = row_id + 1;
					//print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$LeftPlayer$LeftText*ACTIVE SET 1 \0");
					if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+whichInning+"Innings$Row"+row_id+"$LeftPlayer$LeftText$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() +"*"+ "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+whichInning+"Innings$Row"+row_id+"$LeftPlayer$LeftText$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+whichInning+"Innings$Row"+row_id+"$LeftPlayer$LeftText$PlayerRuns*GEOM*TEXT SET " + bc.getRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+whichInning+"Innings$Row"+row_id+"$LeftPlayer$LeftText$PlayerBalls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
					
					if(i == 1 && row_id >= 5) {
						break;
					}
					else if(i == 2 && row_id >= 10) {
						break;
					}
				}
				/*for(i = match.getInning().get(i - 1).getBattingCard().size() + 1; i <= 4; i++) {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + i + "Innings$Row"+ row_id + "$LeftPlayer$LeftText*GEOM*TEXT SET" + " " + "\0");
				}*/
				if(i == 1) {
					row_id = 1;
					max_Strap = 5;
				}
				else {
					row_id = 6;
					max_Strap = 10;
				}
				
				Collections.sort(match.getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
				for(BowlingCard boc : match.getInning().get(i-1).getBowlingCard()) {
					row_id = row_id + 1;
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer*ACTIVE SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer$RightText$LanguageGrp$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer$RightText$PlayerFigures*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer$RightText$PlayerBalls*GEOM*TEXT SET " + boc.getOvers() + "\0");
					
					if(i == 1 && row_id >= 5) {
						break;
					}
					else if(i == 2 && row_id >= 10) {
						break;
					}
				}
				System.out.println("row=" + row_id);
				for(i = row_id + 1; i <= max_Strap; i++) {
					System.out.println("Ival=" + i);
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + i + "$RightPlayer*ACTIVE SET 0 \0");

				}
			}
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START");
		}
		this.status = "SUCCESS";	
}
	public static String TossResult(Match match,int Inning,String TossType,String DecisionType) {
		String teamname="",Decision="";
		for(Inning inn : match.getInning()) {
			if(match.getTossWinningTeam() == match.getHomeTeamId()) {
				teamname = match.getHomeTeam().getShortname();
				if(match.getTossWinningDecision().equalsIgnoreCase(CricketUtil.BAT)) {
					Decision = "BAT";
				}
				else {
					Decision = "FIELD";
				}
			}
			else {
				teamname = match.getAwayTeam().getFullname();
				if(match.getTossWinningDecision().equalsIgnoreCase(CricketUtil.BAT)) {
					Decision = "BAT";
				}
				else {
					Decision = "FIELD";
				}
			}
		}
		return teamname+" " + "WON THE TOSS AND CHOOSE TO " + Decision;
	}
	
	public static String Plural(int Num){
	String Plural = "";

	if (Num == 1){
		Plural = "";
	}
	else{
		Plural = "s";
	}
	return Plural;
	}

	
	public static String generateMatchSummaryStatus(int whichInning,Match match,int TargetOvers, int RequiredRuns, int RequiredBalls,int TargetRuns) {
		int WicketsLeft = 0;
		String MoreTxt = "",NeedTxtToUse = "",TeamNameToUse = "",BottomLineText = "",BottomLine="";
		WicketsLeft = 10 - (match.getInning().get(1).getTotalWickets());
		
		TargetRuns = match.getInning().get(0).getTotalRuns() + 1;
		if(match.getTargetRuns() > 0) {
			TargetRuns = match.getTargetRuns();
		}
		TargetOvers = match.getMaxOvers();
		if(match.getTargetOvers() > 0) {
			TargetOvers = match.getTargetOvers();
		}
		RequiredRuns = TargetRuns - match.getInning().get(1).getTotalRuns();
		if(RequiredRuns <= 0) {
			RequiredRuns = 0;
		}
		RequiredBalls = (TargetOvers * 6) - (match.getInning().get(1).getTotalOvers())*6 - match.getInning().get(1).getTotalBalls();
		if(RequiredBalls <= 0) {
			RequiredBalls = 0;
		}
		if (RequiredRuns > 0 && RequiredBalls > 0 && WicketsLeft > 0)
		  {
			 if (whichInning == 2)
			 {
				if (match.getInning().get(1).getTotalRuns() >= 0)
				{
				   MoreTxt = " more";
				   NeedTxtToUse = " need ";
				   TeamNameToUse = match.getInning().get(1).getBatting_team().getShortname(); 
				   BottomLineText = TeamNameToUse + NeedTxtToUse + RequiredRuns + MoreTxt + " run" + Plural(RequiredRuns)+ " to win from ";
				}
			 }
			 else {
				 BottomLineText = "Current RunRate "+ match.getInning().get(0).getRunRate();
			 }
			 switch(whichInning) {
				case 1: case 2: case 3: case 4:
					break;
				default:
					BottomLineText = "Please Select Valid Inning";
					return BottomLineText;
				}
			 switch(whichInning) {
			 case 1:
				 if(match.getInning().get(whichInning-1).getTotalRuns() > 0 
						 || match.getInning().get(whichInning-1).getTotalOvers() > 0 
						 || match.getInning().get(whichInning-1).getTotalBalls() > 0) {
					 return BottomLineText;
				 }
				 else {
					 return TossResult(match, whichInning, TeamNameToUse, BottomLineText);
				 }
			 case 2:
				 BottomLine = BottomLineText;
				 
			 }
			
			 if (RequiredBalls >= 150)
			 {
				BottomLineText = BottomLine + CricketFunctions.OverBalls(match.getInning().get(1).getTotalOvers(),match.getInning().get(1).getTotalBalls()) + " over";
				  
			}
			 else {
					  BottomLineText = BottomLine + RequiredBalls + " ball" + Plural(RequiredBalls);
			}
			return BottomLineText;
		 }
		  else
		  {
			 if (RequiredRuns <= 0)
			 {
				TeamNameToUse = match.getInning().get(1).getBatting_team().getShortname();
				BottomLineText = TeamNameToUse + " win by " + WicketsLeft + " wicket" + Plural(WicketsLeft);
			}
			 else // (Runs required still greater than one)
			 {
				 TeamNameToUse = match.getInning().get(1).getBowling_team().getShortname();
				 BottomLineText = TeamNameToUse + " win by " + (RequiredRuns - 1) + " run" + Plural(RequiredRuns - 1);
			 }
	
			return BottomLineText;
		 }
		
	}

	
}