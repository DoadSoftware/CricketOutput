package com.cricket.broadcaster;

import java.io.PrintWriter;
import java.util.List;

import com.cricket.containers.BowlingCardFF;
import com.cricket.containers.Bug;
import com.cricket.containers.ScorecardFF;
import com.cricket.functions.Functions;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Inning;
import com.cricket.containers.Scene;
import com.cricket.util.CricketUtil;

@SuppressWarnings("unused")
public class Doad extends Scene {

	public Doad() {
		super();
	}

	public Doad(String scene_path) {
		super(scene_path);
	}

	public String populateScorecard(PrintWriter print_writer, ScorecardFF scorecard,String viz_scene_path) 
	{
		String status = "";
		if (scorecard == null) {
			status = "ERROR: Scorcard is null";
		} else if (scorecard.getInning() == null) {
			status = "ERROR: Scorcard's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$HeaderGrp$BatHeader$HeaderTextAll$LanguageGrp$Language1$BattingTeamName*FUNCTION*controlDatapool*input SET " + scorecard.getHeader_text() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$HeaderGrp$BatHeader$HeaderTextAll$LanguageGrp$Language1$BowlingTeamName*FUNCTION*controlDatapool*input SET " + scorecard.getSub_header_text() + "\0");
						
			int row_id = 0, omo_num = 0;
			String cont_name= "";
				
			for (BattingCard bc : scorecard.getInning().getBattingCard()) {
				
				row_id = row_id + 1;
				
				switch (bc.getStatus().toUpperCase()) {
				case CricketUtil.STILL_TO_BAT:
				
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row"+row_id+"$RowAnim$BatOmo*FUNCTION*Omo*vis_con SET 1 \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row"+row_id+"$RowAnim$BatOmo$PlayerGrp$LeftText$LanguageGrp$Language1$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
					
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

					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAnim$BatOmo*FUNCTION*Omo*vis_con SET " + String.valueOf(omo_num) + " \0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$LeftText$LanguageGrp$Language1$PlayerName*GEOM*TEXT SET " + bc.getPlayer().getSurname() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$LeftText$LanguageGrp$Language1$PlayerRuns*GEOM*TEXT SET " + bc.getRuns() + "\0");
					print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row" + row_id + "$RowAnim$BatOmo$" + cont_name + "$LeftText$LanguageGrp$Language1$PlayerBalls*GEOM*TEXT SET " + String.valueOf(bc.getBalls()) + "\0");

					List<String> how_out_text = Functions.processHowOut(bc);
					if(how_out_text.size() > 0) {
						print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row"+row_id+"$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$WicketPlayerName*GEOM*TEXT SET " + how_out_text.get(0) + "\0");
						if(how_out_text.size() >= 2) {
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row"+row_id+"$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$BallPlayerName*GEOM*TEXT SET " + how_out_text.get(1) + "\0");
						} else {
							print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BattingData$DataAll$DataGrp$Row"+row_id+"$RowAnim$BatOmo$" + cont_name + "$RightText$LanguageGrp$Language1$BallPlayerName*GEOM*TEXT SET \0");
						}
					}
					
				}
			}

			print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + scorecard.getInning().getTotalExtras() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + scorecard.getInning().getTotalOvers() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$AllDataGrp$BattingData$DataAll$BottomInfoPosition$BottomInfoBand$TotalGrp$TotalScore*GEOM*TEXT SET " + scorecard.getInning().getTotalRuns() + "-" + String.valueOf(scorecard.getInning().getTotalWickets()) + "\0");
			
			status = "SUCCESS";
		}
		
		return status;
	}
	public String populateBowlingcard(PrintWriter print_writer, BowlingCardFF bowlingcard, String viz_scene_path) 
	{
		String status = "";
		if (bowlingcard == null) {
			status = "ERROR: Bowlingcard is null";
		} else if (bowlingcard.getInning() == null) {
			status = "ERROR: Bowlingcard's inning is null";
		} else {
			
			print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$HeaderGrp$BallHeader$HeaderTextAll$LanguageGrp$Language1$BattingTeamName*FUNCTION*controlDatapool*input SET " + bowlingcard.getHeader_text() + "\0");
			print_writer.println("-1 RENDERER*TREE*$Main$All$TopPart$HeaderGrp$BallHeader$HeaderTextAll$LanguageGrp$Language1$BowlingTeamName*FUNCTION*controlDatapool*input SET " + bowlingcard.getSub_header_text() + "\0");
					
			int row_id = 0; 	
			for (BowlingCard boc : bowlingcard.getInning().getBowlingCard()) {
				
				row_id = row_id + 1;
				
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$DataGrp$DataAllGrp$Row"+row_id+"$RowAnim$BatOmo$Dehighlight$LeftText$LanguageGrp$Language1$PlayerName*GEOM*TEXT SET " +boc.getPlayer().getSurname() +"\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataAll$DataGrp$DataAllGrp$Row"+row_id+"$RowAnim$BatOmo$Dehighlight$LeftText$LanguageGrp$Language1$PlayerRuns*GEOM*TEXT SET " +boc.getWickets()+'-'+String.valueOf(boc.getRuns())  +"\0");
				
			} 
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataGrp$BottomInfoPosition$BottomInfoBand$ExtrasGrp$LanguageGrp$Language1$ExtrasValue*GEOM*TEXT SET " + bowlingcard.getInning().getTotalExtras() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataGrp$BottomInfoPosition$BottomInfoBand$OversGrp$LanguageGrp$Language1$OversValue*GEOM*TEXT SET " + bowlingcard.getInning().getTotalOvers() + "\0");
				print_writer.println("-1 RENDERER*TREE*$Main$All$AllDataGrp$BowlingData$DataGrp$BottomInfoPosition$BottomInfoBand$TotalGrp$TotalScore*GEOM*TEXT SET " + bowlingcard.getInning().getTotalRuns()+"-"+String.valueOf(bowlingcard.getInning().getTotalWickets()) + "\0");
				
			status = "SUCCESS";
		}
		
		return status;
	}
	
	public void populateBugs(PrintWriter print_writer, Bug bug)
	{
		print_writer.println("-1 RENDERER*TREE*$Main$LINES$SubHeaderGrp$SubHeaderText$LanguageGrp$Language1*GEOM*TEXT SET " 
				+ bug.getHeader_text() + "\0");
		if(bug.getStats_text() != null && bug.getStats_text().size() >= 1) 
			print_writer.println("-1 RENDERER*TREE*$Main$LINES$Dehighlight$StatHeadHrp$StatHead1$StatHead1*GEOM*TEXT SET " 
					+ bug.getStats_text().get(0) + " " + bug.getStats_text().get(1) +  "\0");
		print_writer.println("-1 RENDERER*STAGE*DIRECTOR*In START\0");
	}
	
}
