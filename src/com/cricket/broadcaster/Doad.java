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
						
						if(ps.getPartnershipNumber()<inn.getPartnerships().size()) {
							omo_num = 4;
							cont_name = "Highlight";
						}
						else if(ps.getPartnershipNumber() >= inn.getPartnerships().size()) {
							omo_num = 3;
							cont_name = "Dehighlight";
						}
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$Row" + row_id  + "$PartOmo*FUNCTION*Omo*vis_con SET "+String.valueOf(omo_num)+ " \0");
						
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$LeftPlayeName*GEOM*TEXT SET " + Left_Batsman + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$RightPlayeName*GEOM*TEXT SET " + Right_Batsman + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$ScoreGrp$PlayerRuns*GEOM*TEXT SET " + ps.getTotalRuns() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$PartnershipData$DataAll$Row" + row_id  + "$"+cont_name+"$ScoreGrp$PlayerBalls*GEOM*TEXT SET " + ps.getTotalBalls() + "\0");		
					}
					if(inn.getPartnerships().size() >= 10) {
						//hide 11 strap in partnership
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
		if (match == null) {
			this.status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			this.status = "ERROR: Match Summary's inning is null";
		} else {
			
			int row_id_a = 0,row_id_b=0,s_inn=0;

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$LanguageGrp$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$PartHeader$MatchId$LanguageGrp$Langauage1*FUNCTION*ControlDatapool*input SET " + match.getMatchIdent() + "\0");
			
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {
					//System.out.println(whichInning+" "+inn.getInningNumber());
					if(whichInning==1) {
						s_inn=1;
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 1 \0");
					}
					else if(whichInning==2) {
						s_inn=2;
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll*FUNCTION*Omo*vis_con SET 2 \0");
					}
					if (inn.getBattingTeamId() == match.getHomeTeamId()) {
						row_id_a +=1;
						row_id_b +=1;
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$TeamTextAll1$TeamLanguageGrp$TeamName1*GEOM*TEXT SET " + match.getHomeTeam().getFullname() + "\0");
					} else {
						row_id_a +=1;
						row_id_b +=1;
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$TeamTextAll1$TeamLanguageGrp$TeamName1*GEOM*TEXT SET " + match.getAwayTeam().getFullname() + "\0");
					}
					print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$TeamTextAll1$InningsLanguageGrp$Innings"+s_inn+"*GEOM*TEXT SET " + CricketFunctions.OverBalls(inn.getTotalOvers(),inn.getTotalBalls()) + "\0");
					
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$TeamTextAll1$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					}
					else {
						print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$TeamTextAll1$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + slashOrDash + String.valueOf(inn.getTotalWickets()) + "\0");	
					}
					Collections.sort(inn.getBattingCard(),new CricketFunctions.BatsmenScoreComparator());
					Collections.reverse(inn.getBattingCard());
					for(BattingCard bc : inn.getBattingCard()) {
						row_id_a = row_id_a + 1;
						if(row_id_a <=5) {
							if(bc.getStatus().equalsIgnoreCase(CricketUtil.NOT_OUT)) {
								print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$PlayerAllGrp$LeftPlayer$LeftText$LanguageGrp$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() +"*"+ "\0");
							}
							else {
								print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$PlayerAllGrp$LeftPlayer$LeftText$LanguageGrp$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
							}
								print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$PlayerAllGrp$LeftPlayer$LeftText$PlayerRuns*GEOM*TEXT SET " + bc.getRuns() + "\0");
								print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$"+s_inn+"Innings$Row"+row_id_a+"$RowAnim$PlayerAllGrp$LeftPlayer$LeftText$PlayerBalls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");
							}
					}
					Collections.sort(inn.getBowlingCard(),new CricketFunctions.BowlerFiguresComparator());
					Collections.reverse(inn.getBowlingCard());
					for(BowlingCard boc : inn.getBowlingCard()) {
						row_id_b = row_id_b + 1;
						if(row_id_b <= 5) {
							print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$"+s_inn+"Innings$Row"+row_id_b+"$RowAnim$PlayerAllGrp$RightPlayer$RightText$LanguageGrp$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getSurname() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$"+s_inn+"Innings$Row"+row_id_b+"$RowAnim$PlayerAllGrp$RightPlayer$RightText$PlayerFigures*GEOM*TEXT SET " + boc.getWickets() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$SummaryData$DataAll$"+s_inn+"Innings$Row"+row_id_b+"$RowAnim$PlayerAllGrp$RightPlayer$RightText$PlayerBalls*GEOM*TEXT SET " + String.valueOf(boc.getRuns()) + "\0");
						}
					}
				}
				
			}
			this.status = "SUCCESS";
		}
	}
}

