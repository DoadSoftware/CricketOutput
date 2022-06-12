package com.cricket.controller;

import java.io.File;
import java.io.FileFilter;

import java.io.IOException;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cricket.broadcaster.Doad;
import com.cricket.containers.Configurations;
//import com.cricket.containers.Configurations;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.EventFile;
import com.cricket.model.Inning;
import com.cricket.model.Match;
import com.cricket.model.Statistics;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;

import net.sf.json.JSONObject;

@Controller
public class IndexController 
{
	@Autowired
	CricketService cricketService;
	public static Configurations session_Configurations;
	public static Match session_match;
	public static EventFile session_event_file;
	public static Socket session_socket;
	public static Doad this_doad;
	public static PrintWriter print_writer;
	 //string static png_extension = '.png';
	List<BattingCard> last_infobar_batsman;
	BowlingCard last_infobar_bowler;
	int whichInning,player_id;
	String session_selected_broadcaster, viz_scene_path, which_graphics_onscreen, info_bar_bottom_left, info_bar_bottom_right, 
	info_bar_bottom,stats_type,top_left_stats,top_right_stats,type_of_profile;
	boolean is_Infobar_on_Screen = false;
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model) throws JAXBException, IOException 
	{
		model.addAttribute("session_viz_scenes", new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.SCENES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".via") && pathname.isFile();
		    }
		}));
		model.addAttribute("match_files", new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".xml") && pathname.isFile();
		    }
		}));
		
		if(new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.OUTPUT_XML).exists()) {
			session_Configurations = (Configurations)JAXBContext.newInstance(Configurations.class).createUnmarshaller().unmarshal(
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.OUTPUT_XML));
		} else {
			session_Configurations = new Configurations();
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.OUTPUT_XML));
		}
	
		model.addAttribute("session_Configurations",session_Configurations);
		
		return "initialise";
	
	}

	@RequestMapping(value = {"/output"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String outputPage(ModelMap model,
			@RequestParam(value = "select_broadcaster", required = false, defaultValue = "") String select_broadcaster,
			@RequestParam(value = "select_cricket_matches", required = false, defaultValue = "") String selectedMatch,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") int vizPortNumber,
			@RequestParam(value = "vizScene", required = false, defaultValue = "") String vizScene,
			@RequestParam(value = "select_sponsors", required = false, defaultValue = "") String select_sponsors) 
					throws UnknownHostException, IOException, JAXBException, IllegalAccessException, InvocationTargetException 
	{
		info_bar_bottom_left = "";
		info_bar_bottom_right = "";
		info_bar_bottom = "";
		which_graphics_onscreen = "";
		stats_type = "";
		top_left_stats = "";
		top_right_stats = "";
		type_of_profile = "";
		whichInning = 0;
		player_id = 0;
		is_Infobar_on_Screen = false;
		this_doad = new Doad();
		last_infobar_batsman = new ArrayList<BattingCard>();
		last_infobar_bowler = new BowlingCard();
		session_selected_broadcaster = select_broadcaster;
		session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
		print_writer = new PrintWriter(session_socket.getOutputStream(), true);
		session_Configurations = new Configurations(selectedMatch, select_broadcaster, select_sponsors, vizIPAddresss, vizPortNumber, vizScene);
		
		JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.OUTPUT_XML));

		session_event_file = (EventFile) JAXBContext.newInstance(EventFile.class).createUnmarshaller().unmarshal(
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + selectedMatch));
		
		session_match = CricketFunctions.populateMatchVariables(cricketService, (Match) JAXBContext.newInstance(Match.class).createUnmarshaller().unmarshal(
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + selectedMatch)));
		//session_match.setMatchFileName(selectedMatch);
		session_match.setMatchFileTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
		
		//session_match.setEvents(session_event_file.getEvents());
		
		model.addAttribute("session_match", session_match);
		model.addAttribute("session_socket", session_socket);
		model.addAttribute("session_selected_broadcaster", session_selected_broadcaster);
		
		return "output";
	}

	@RequestMapping(value = {"/processCricketProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processCricketProcedures(
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess) 
					throws IOException, IllegalAccessException, InvocationTargetException, JAXBException, InterruptedException 
	{
		
		switch (whatToProcess.toUpperCase()) {
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS": case "PLAYERSTATS_GRAPHICS-OPTIONS": case "NAMESUPER_GRAPHICS-OPTIONS": 
		case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "BOTTOMLEFT_GRAPHICS-OPTIONS": case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "BOTTOM_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS":
			return JSONObject.fromObject(session_match).toString();
		case "READ-MATCH-AND-POPULATE":
			if(!valueToProcess.equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()).lastModified())))
			{
				session_match = CricketFunctions.populateMatchVariables(cricketService, (Match) JAXBContext.newInstance(Match.class).createUnmarshaller().unmarshal(
						new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + session_match.getMatchFileName())));
				session_match.setMatchFileTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
						new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()).lastModified()));
				session_event_file = (EventFile) JAXBContext.newInstance(EventFile.class).createUnmarshaller().unmarshal(
						new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + session_match.getMatchFileName()));
				
				session_match.setEvents(session_event_file.getEvents());
				//System.out.println("Inning ="+ whichInning);
				switch(which_graphics_onscreen) {
				case "SCORECARD":
					this_doad.populateScorecard(print_writer, viz_scene_path , whichInning , session_match, session_selected_broadcaster);
					break;
				case "BOWLINGCARD":
					this_doad.populateBowlingcard(print_writer, viz_scene_path, whichInning, session_match, session_selected_broadcaster);
					break;
				case "PARTNERSHIP":
					this_doad.populatePartnership(print_writer, viz_scene_path, whichInning, session_match, session_selected_broadcaster);
					break;
				case "SUMARRY":
					this_doad.populateMatchsummary(print_writer, viz_scene_path, whichInning, session_match, session_selected_broadcaster);
					break;
				case "HOWOUT":
					this_doad.populateHowout(print_writer, viz_scene_path, whichInning, stats_type, player_id, session_match, session_selected_broadcaster);
					break;
				case "PLAYERSTATS":
					this_doad.populatePlayerstats(print_writer, viz_scene_path, whichInning, stats_type, player_id, session_match, session_selected_broadcaster);
					break;
				/*case "PLAYERPROFILE":
					this_doad.populatePlayerProfile(print_writer, viz_scene_path, player_id, stats_type, type_of_profile, null, session_match, session_selected_broadcaster);
					break;*/
					
				}
				
				if(is_Infobar_on_Screen == true) {
					
					last_infobar_batsman = this_doad.populateInfobarTopLeft(true, print_writer, top_left_stats.toUpperCase(), session_match, session_selected_broadcaster, last_infobar_batsman);
					last_infobar_bowler = this_doad.populateInfobarTopRight(true, print_writer, top_right_stats.toUpperCase(), session_match, session_selected_broadcaster,last_infobar_bowler);
					
					this_doad.populateInfobarTopLeft(true, print_writer, top_left_stats.toUpperCase(), session_match, session_selected_broadcaster,last_infobar_batsman);
					this_doad.populateInfobarTeamScore(true, print_writer, session_match, session_selected_broadcaster);
					this_doad.populateInfobarTopRight(true, print_writer, top_right_stats.toUpperCase() , session_match, session_selected_broadcaster,last_infobar_bowler);
					this_doad.populateInfobarBottomLeft(true, print_writer, info_bar_bottom_left, session_match, session_selected_broadcaster);
					this_doad.populateInfobarBottomRight(true, print_writer, info_bar_bottom_right, session_match, session_selected_broadcaster);
					this_doad.populateInfobarBottom(true, print_writer, info_bar_bottom, session_match, session_selected_broadcaster);
					
					
				}
				return JSONObject.fromObject(session_match).toString();
			}
			else {
				return JSONObject.fromObject(null).toString();
			}
		
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-L3-BUG":  case "POPULATE-L3-HOWOUT":
		case "POPULATE-L3-PLAYERSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-L3-INFOBAR": case "POPULATE-FF-LEADERBOARD":
		case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI":
			switch (session_selected_broadcaster.toUpperCase()) {
			
			case "DOAD_IN_HOUSE_VIZ":
				break;
				
			case "DOAD_IN_HOUSE_EVEREST":
				
				switch(whatToProcess.toUpperCase()) {
				case"POPULATE-INFOBAR-BOTTOMLEFT": case"POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM":
					break;
				default:
					viz_scene_path = valueToProcess.split(",")[0];
					new Scene(viz_scene_path).scene_load(new PrintWriter(session_socket.getOutputStream(),true),session_selected_broadcaster,viz_scene_path);
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-FF-SCORECARD":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					
					this_doad.populateScorecard(print_writer, viz_scene_path, whichInning, 
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-BOWLINGCARD":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					
					this_doad.populateBowlingcard(print_writer, viz_scene_path, whichInning, 
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-PARTNERSHIP":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					
					this_doad.populatePartnership(print_writer, viz_scene_path, whichInning, 
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-MATCHSUMMARY":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					
					this_doad.populateMatchsummary(print_writer, viz_scene_path, whichInning, 
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BUG":
					this_doad.populateBug(print_writer, viz_scene_path,Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-HOWOUT":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					stats_type = valueToProcess.split(",")[2];
					player_id = Integer.valueOf(valueToProcess.split(",")[3]);
					
					this_doad.populateHowout(print_writer, viz_scene_path,whichInning, stats_type, player_id, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-PLAYERSTATS":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					stats_type = valueToProcess.split(",")[2];
					player_id = Integer.valueOf(valueToProcess.split(",")[3]);
					
					this_doad.populatePlayerstats(print_writer, viz_scene_path,whichInning, stats_type, 
							player_id, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-NAMESUPER":
					this_doad.populateNameSuper(print_writer, viz_scene_path,Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							Integer.valueOf(valueToProcess.split(",")[3]), session_match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-PLAYERPROFILE":
					player_id = Integer.valueOf(valueToProcess.split(",")[1]);
					stats_type = valueToProcess.split(",")[2];
					type_of_profile = valueToProcess.split(",")[3];
					
					for(Statistics stats : cricketService.getPlayerStatistics(player_id)) {
						stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
						stats = updateStatisticsWithMatchData(stats, session_match, type_of_profile);
						if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(stats_type)) {
							this_doad.populatePlayerProfile(print_writer,viz_scene_path,player_id,
									stats_type,type_of_profile,stats,session_match, session_selected_broadcaster);
						}
					}
					break;
				case "POPULATE-FF-DOUBLETEAMS":
					this_doad.populateDoubleteams(print_writer,viz_scene_path, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-INFOBAR":
					
					top_left_stats = valueToProcess.split(",")[1];
					top_right_stats = valueToProcess.split(",")[2];
					info_bar_bottom_left = valueToProcess.split(",")[3];
					info_bar_bottom_right = valueToProcess.split(",")[4];
					this_doad.populateInfobar(print_writer, viz_scene_path,top_left_stats,top_right_stats,info_bar_bottom_left,
							info_bar_bottom_right,last_infobar_batsman, session_match, session_selected_broadcaster,last_infobar_bowler);
					break;
				case "POPULATE-INFOBAR-BOTTOMLEFT":
					this_doad.processAnimation(print_writer, "Section4_Out", "CONTINUE", session_selected_broadcaster);
					this_doad.processAnimation(print_writer, "Section6_Out", "CONTINUE", session_selected_broadcaster);
					for(Inning inn : session_match.getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							this_doad.populateInfobarBottomLeft(false,print_writer, valueToProcess, session_match, session_selected_broadcaster);
							this_doad.populateInfobarBottomRight(false,print_writer, info_bar_bottom_right, session_match, session_selected_broadcaster);
						}
					}
					this_doad.processAnimation(print_writer, "Section4_In", "START", session_selected_broadcaster);
					info_bar_bottom_left = valueToProcess;
					break;
				case "POPULATE-INFOBAR-BOTTOMRIGHT":
					this_doad.processAnimation(print_writer, "Section5_Out", "CONTINUE", session_selected_broadcaster);
					this_doad.processAnimation(print_writer, "Section6_Out", "CONTINUE", session_selected_broadcaster);
					for(Inning inn : session_match.getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							this_doad.populateInfobarBottomLeft(false,print_writer, info_bar_bottom_left, session_match, session_selected_broadcaster);
							this_doad.populateInfobarBottomRight(false,print_writer, valueToProcess, session_match, session_selected_broadcaster);
						}
					}
					this_doad.processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster);
					info_bar_bottom_right = valueToProcess;
					break;
				case "POPULATE-INFOBAR-BOTTOM":
					this_doad.processAnimation(print_writer, "Section6_Out", "CONTINUE", session_selected_broadcaster);
					for(Inning inn : session_match.getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							this_doad.populateInfobarBottom(false, print_writer, valueToProcess, session_match, session_selected_broadcaster);
						}
					}
					this_doad.processAnimation(print_writer, "Section6_In", "START", session_selected_broadcaster);
					info_bar_bottom = valueToProcess;
					//System.out.println("info_bar_bottom = "+valueToProcess);

					break;
				case "POPULATE-FF-MATCHID":
					this_doad.populateMatchId(print_writer,viz_scene_path, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-PLAYINGXI":
					this_doad.populatePlayingXI(print_writer, viz_scene_path, Integer.valueOf(valueToProcess.split(",")[1]), 
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-LEADERBOARD":
					this_doad.populateLeaderBoard(print_writer, viz_scene_path, valueToProcess.split(",")[1], valueToProcess.split(",")[2],
							session_match, session_selected_broadcaster);
					break;
				}
				return JSONObject.fromObject(this_doad).toString();
			}
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-PLAYERSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-INFOBAR": case "ANIMATE-OUT": 
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_EVEREST": case "DOAD_IN_HOUSE_VIZ":
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-SCORECARD":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "SCORECARD";
					break;
				case "ANIMATE-IN-BOWLINGCARD":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "BOWLINGCARD";
					break;
				case "ANIMATE-IN-PARTNERSHIP":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "PARTNERSHIP";
					break;
				case "ANIMATE-IN-MATCHSUMARRY":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "SUMARRY";
					break;
				case "ANIMATE-IN-BUG":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "BUG";
					break;
				case "ANIMATE-IN-HOWOUT":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "HOWOUT";
					break;
				case "ANIMATE-IN-PLAYERSTATS":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "PLAYERSTATS";
					break;
				case "ANIMATE-IN-NAMESUPER":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "NAMESUPER";
					break;
				case "ANIMATE-IN-PLAYERPROFILE":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "PLAYERPROFILE";
					break;
				case "ANIMATE-IN-DOUBLETEAMS":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "DOUBLETEAMS";
					break;
				case "ANIMATE-IN-INFOBAR":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					is_Infobar_on_Screen = true;
					break;
				case "ANIMATE-IN-MATCHID":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "MATCHID";
					break;
				case "ANIMATE-IN-PLAYINGXI":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "PLAYINGXI";
					break;
				case "ANIMATE-IN-LEADERBOARD":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "LEADERBOARD";
					break;
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "INFOBAR":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						is_Infobar_on_Screen = false;
						break;
					case "SCORECARD":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "BOWLINGCARD":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "PARTNERSHIP":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "SUMARRY":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "BUG":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "HOWOUT":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "PLAYERSTATS":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "NAMESUPER":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "PLAYERPROFILE":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "DOUBLETEAMS":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "MATCHID":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "PLAYINGXI":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "LEADERBOARD":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					}
					break;
				}
				return JSONObject.fromObject(this_doad).toString();
			}
		case "POPULATE-SELECT-PLAYER": 
			return JSONObject.fromObject(session_match).toString();
		default:
			return JSONObject.fromObject(null).toString();
		}
	}
	public Statistics updateStatisticsWithMatchData(Statistics stat, Match match, String typeOfProfile)
	{
		if(stat.getStats_type().getStats_short_name().equalsIgnoreCase(match.getMatchType())) {
			stat.setMatches(stat.getMatches() + 1);
			for(Inning inn : match.getInning())
			{
				switch(typeOfProfile.toUpperCase()) {
				case CricketUtil.BATSMAN:
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId() == stat.getPlayer_id()) {
							if(bc.getBatsmanInningStarted() == CricketUtil.YES) {
								stat.setInnings(stat.getInnings() + 1);
							}
							stat.setRuns(stat.getRuns() + bc.getRuns());
							if(bc.getRuns() < 100 && bc.getRuns() >= 50) {
								stat.setFifties(stat.getFifties() + 1);
							}else if(bc.getRuns() >= 100){
								stat.setHundreds(stat.getHundreds() + 1);
							}
						}
					}
					break;
				case CricketUtil.BOWLER:
					if(inn.getBowlingCard() != null && inn.getBowlingCard().size()>0) {
						for(BowlingCard boc : inn.getBowlingCard()) {
							if(boc.getPlayerId() == stat.getPlayer_id()) {
								stat.setWickets(stat.getWickets() + boc.getWickets());
								stat.setBest_figures(stat.getBest_figures());
							}
						}							
					}
					break;
				
				}
			}
		}
		return stat;
	}
}