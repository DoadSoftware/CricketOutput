package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.util.Collections;

import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Partnership;
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
	
	public void populateScorecard(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path) 
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Scorecard's inning is null";
		} else {

			int row_id = 0, omo_num = 0;
			String cont_name= "";

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BatHeader$MatchId$Langauage1*GEOM*TEXT SET " + match.getMatchIdent() + "\0");
			
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
							
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.OUT)) {
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
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/Battingcard.png In 1.400 BattingCardIn 2.600 \0");

			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BattingCardIn START \0");
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
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow2$FowValues1$FowValue" + fow.getFowNumber() + "*GEOM*TEXT SET "+fow.getFowNumber()+slashOrDash+fow.getFowRuns()+"\0");
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow3$FowValues2$FowValue" + fow.getFowNumber() + "*GEOM*TEXT SET "+fow.getFowNumber()+slashOrDash+fow.getFowRuns()+"\0");
									
									//remove the best over from the bowling card for now
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$FowGrp$Fow1$BestOver*ACTIVE SET 0"+"\0");
									
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
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/Bowlingcard.png In 1.400 BowlingCardIn 2.500 \0");

			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*BowlingCardIn START \0");
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

			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*PartnershipIn START \0");
			
			this.status = "SUCCESS";
		}
	}
	
	public void populateMatchsummary(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
		} else {
			
			int row_id = 0, max_Strap = 0, total_inn = 0;
			String teamname = ""; 
			
			for(Inning inn : match.getInning()) {
				if(inn.getInningStatus() != null) {
					total_inn = total_inn + 1;
				}
			}
			
			if(total_inn > 0 && whichInning > total_inn) {
				whichInning = total_inn;
			}
			for(int i = 1; i <= 4 ; i++) {
				if(i == whichInning) {
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 1 \0");
				} else {
					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$SummaryData$DataAll$" +  i + "Innings*ACTIVE SET 0 \0");
				}
			}
			
			for(int i = 1; i <= whichInning ; i++) {

				if(i == 1) {
					row_id = 0;
					max_Strap = 5;
				} else {
					row_id = 5;
					max_Strap = 10;
				}
				row_id = row_id + 1;
				
				//Toss
				if(match.getTossWinningTeam() == match.getInning().get(i-1).getBattingTeamId()) {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$" + whichInning + "Innings$Row" + row_id + "$Toss*FUNCTION*Omo*vis_con SET 1 \0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$" + whichInning + "Innings$Row" + row_id + "$Toss*FUNCTION*Omo*vis_con SET 0 \0");
				}
				
				if(match.getInning().get(i-1).getBattingTeamId() == match.getHomeTeamId()) {
					teamname = match.getHomeTeam().getFullname();	
				} else {
					teamname = match.getAwayTeam().getFullname();
				}
				
				print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TeamName1*GEOM*TEXT SET " + teamname + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$Innings1*GEOM*TEXT SET " + 
						"Over " + CricketFunctions.OverBalls(match.getInning().get(i-1).getTotalOvers(),match.getInning().get(i-1).getTotalBalls()) + "\0");
				
				if(match.getInning().get(i-1).getTotalWickets() >= 10) {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TotalGrp$TotalScore*GEOM*TEXT SET " + match.getInning().get(i-1).getTotalRuns() + "\0");
				}
				else {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+ whichInning +"Innings$Row"+row_id+"$TotalGrp$TotalScore*GEOM*TEXT SET " + match.getInning().get(i-1).getTotalRuns() + slashOrDash + String.valueOf(match.getInning().get(i-1).getTotalWickets()) + "\0");	
				}
				if(match.getInning().get(i-1).getBattingCard() != null) {
					
					Collections.sort(match.getInning().get(i-1).getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					for(BattingCard bc : match.getInning().get(i-1).getBattingCard()) {
						if(!bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.STILL_TO_BAT)) {
							row_id = row_id + 1;
							print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$LeftPlayer*ACTIVE SET 1 \0");
							if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+whichInning+"Innings$Row"+row_id+"$LeftPlayer$LeftText$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() +"*"+ "\0");
							} else {
								print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+whichInning+"Innings$Row"+row_id+"$LeftPlayer$LeftText$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							}
							print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+whichInning+"Innings$Row"+row_id+"$LeftPlayer$LeftText$PlayerRuns*GEOM*TEXT SET " + bc.getRuns() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+whichInning+"Innings$Row"+row_id+"$LeftPlayer$LeftText$PlayerBalls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							
							if(i == 1 && row_id >= 5) {
								break;
							}else if(i == 2 && row_id >= 10) {
								break;
							}
						}
					}
				}

				for(int j = row_id + 1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row"+ j + "$LeftPlayer*ACTIVE SET 0 \0");
				}
				
				if(i == 1) {
					row_id = 1;
				}
				else {
					row_id = 6;
				}

				if(match.getInning().get(i-1).getBowlingCard() != null) {
					
					Collections.sort(match.getInning().get(i-1).getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());

					for(BowlingCard boc : match.getInning().get(i-1).getBowlingCard()) {
						
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer*ACTIVE SET 1 \0");
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer$RightText$LanguageGrp$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getSurname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer$RightText$PlayerFigures*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + row_id + "$RightPlayer$RightText$PlayerBalls*GEOM*TEXT SET " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
						
						if(i == 1 && row_id >= 5) {
							break;
						}
						else if(i == 2 && row_id >= 10) {
							break;
						}
					}
				}
				
				for(int j = row_id + 1; j <= max_Strap; j++) {
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$" + whichInning + "Innings$Row" + j + "$RightPlayer*ACTIVE SET 0 \0");
				}
			}

			print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$BottomInfoPosition$InfoTextAll$InfoText*GEOM*TEXT SET " + CricketFunctions.generateMatchSummaryStatus(whichInning, match, CricketUtil.FULL) + " \0");
			
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/matchsummary.png In 1.400 SummaryIn 2.400 \0");

			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*SummaryIn START \0");

			this.status = "SUCCESS";	
		
		}
	}

	public void populateBug(PrintWriter print_writer, int whichInning, String statsType, int playerId, Match match, String viz_scene_path)
	{
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
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
								if(bc.getStatus().toUpperCase().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
									System.out.println("Player:" + bc.getPlayer().getFull_name());
									print_writer.println("-1 RENDERER*TREE*$Main$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$LanguageGrp$Language1*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + "*" + " \0");
								}
								else {
									System.out.println("Player:" + bc.getPlayer().getFull_name());
									print_writer.println("-1 RENDERER*TREE*$Main$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$LanguageGrp$Language1*GEOM*TEXT SET " + bc.getPlayer().getFull_name() + " \0");
								}
								print_writer.println("-1 RENDERER*TREE*$Main$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$LanguageGrp1$Language1*GEOM*TEXT SET " + "Fours: " + bc.getFours() + "," + "Sixes: " + bc.getSixes()  + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$Lt_Position$LINES$1Line$Dehighlight$StatHeadHrp$StatHead1$StatHead1*GEOM*TEXT SET " + bc.getRuns() + "( " + bc.getBalls() + " )" + "\0");
							}
						}
						break;
					case "BOWLER":
						for (BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId()==playerId) {
								print_writer.println("-1 RENDERER*TREE*$Main$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$LanguageGrp$Language1*GEOM*TEXT SET " + boc.getPlayer().getFull_name() + " \0");
								print_writer.println("-1 RENDERER*TREE*$Main$Lt_Position$LINES$1Line$SubHeaderGrp$SubHeaderText$LanguageGrp1$Language1*GEOM*TEXT SET " + "Overs: " + CricketFunctions.OverBalls(boc.getOvers(), boc.getBalls()) + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$Lt_Position$LINES$1Line$Dehighlight$StatHeadHrp$StatHead1$StatHead1*GEOM*TEXT SET " + boc.getWickets() + slashOrDash + boc.getRuns() + "\0");
							}
						}
						break;
					}
				}
			}
			print_writer.println("-1 RENDERER PREVIEW SCENE*" + viz_scene_path + " C:/Temp/Bug.png In 1.040 \0");

			print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START \0");
			
			this.status = "SUCCESS";	
		}
	}
}