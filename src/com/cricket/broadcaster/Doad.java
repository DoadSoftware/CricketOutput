package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


import com.cricket.containers.Bug;
import com.cricket.functions.Functions;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Inning;
import com.cricket.model.Match;
import com.cricket.model.FallOfWicket;
import com.cricket.containers.Scene;
import com.cricket.util.CricketUtil;

@SuppressWarnings("unused")
public class Doad extends Scene{

	public Doad() {
		super();
	}

	public Doad(String scene_path) {
		super(scene_path);
	}

	public String populateScorecard(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path) 
	{
		String status = "";
		if (match == null) {
			status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			status = "ERROR: Scorecard's inning is null";
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
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$WicketPlayerName*GEOM*TEXT SET " + bc.getHowOutPartOne() + "\0");
							print_writer.println("-1 RENDERER*TREE*$Main$BattingData$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$BallPlayerName*GEOM*TEXT SET " + bc.getHowOutPartTwo() + "\0");

						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + inn.getTotalOvers() + "\0");
					if(inn.getTotalWickets() >= 10) {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + String.valueOf(inn.getTotalWickets()) + "\0");
					}
				}
			}
			
			status = "SUCCESS";
		}
		
		return status;
	}
	public String populateBowlingcard(PrintWriter print_writer, int whichInning, Match match, String viz_scene_path) 
	{
		String status = "" ;
		if (match == null) {
			status = "ERROR: Match is null";
		} else if (match.getInning() == null) {
			status = "ERROR: Bowlingcard's inning is null";
		} else {

			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$SubHeaderGrp$SubHeaderText$LanguageGrp$Langauage1*GEOM*TEXT SET " + match.getTournament() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$TopPart$HeaderGrp$BallHeader$MatchId$LanguageGrp$Langauage1*GEOM*TEXT SET " + match.getMatchIdent() + "\0");
			
			int row_id = 0; 
			Functions FC = new Functions();
			for(Inning inn : match.getInning()) {
				if (inn.getInningNumber() == whichInning) {

					if (inn.getBowlingTeamId() == match.getHomeTeamId()) {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$LanguageGrp$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$LanguageGrp$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
					} else {
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$LanguageGrp$Language1$BattingTeamName*FUNCTION*ControlDatapool*input SET " + match.getAwayTeam().getFullname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$TopPart$BatHeader$LanguageGrp$Language1$BowlingTeamName*FUNCTION*ControlDatapool*input SET " + match.getHomeTeam().getFullname() + "\0");
					}

					print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$BowlingData$DataAll$DataAllGrp*FUNCTION*Omo*vis_con SET " + inn.getBowlingCard().size()+"\0");

					for (BowlingCard boc : inn.getBowlingCard()) {
						
						row_id = row_id + 1;
						
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$LeftText$LanguageGrp$Language1$PlayerName*GEOM*TEXT SET " + boc.getPlayer().getSurname() + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$LeftText$LanguageGrp$Language1$PlayerRuns*GEOM*TEXT SET " + boc.getWickets() + '-' + String.valueOf(boc.getRuns())  + "\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$RightText$LanguageGrp$Value$OverValue*GEOM*TEXT SET " + FC.OverBalls(boc.getOvers(),boc.getBalls()) +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$RightText$LanguageGrp$Value$MaidensValue*GEOM*TEXT SET " + boc.getDots() +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$RightText$LanguageGrp$Value$ExtraValue*GEOM*TEXT SET " + FC.bowlerExtras(boc.getWides(), boc.getNoBalls()) +"\0");
						print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$Row" + row_id + "$RowAnim$BallOmo$Dehighlight$RightText$LanguageGrp$Value$EconomyValue*GEOM*TEXT SET " + FC.Economy(boc.getRuns(),FC.OverBalls(boc.getOvers(),boc.getBalls())) + "\0");
						
					}
					
					if(inn.getBattingCard().size()<=8) {
						if(inn.getFallsOfWickets() != null & inn.getFallsOfWickets().size() > 0) {
							for(FallOfWicket fow : inn.getFallsOfWickets()) {
								if(inn.getTotalWickets() < 10) {
									print_writer.println("-1 RENDERER*TREE*$Main$BowlingData$DataAll$FowGrp$Fow2$RowAnim$FowValues1$FowValue1*GEOM*TEXT SET "+fow.getFowNumber()+"\0");
								}
							}
						}
					}
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$BottomInfoPosition$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + inn.getTotalExtras() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$BottomInfoPosition$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + inn.getTotalOvers() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$BottomInfoPosition$TotalGrp$TotalScore*GEOM*TEXT SET " + inn.getTotalRuns() + "-" + String.valueOf(inn.getTotalWickets()) + "\0");
				}
			}
			
			status = "SUCCESS";
		}
		
		return status;
	}

}

