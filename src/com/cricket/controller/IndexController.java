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
import com.cricket.model.NameSuper;
import com.cricket.containers.Scene;
import com.cricket.model.BattingCard;
import com.cricket.model.BowlingCard;
import com.cricket.model.Bugs;
import com.cricket.model.EventFile;
import com.cricket.model.InfobarStats;
import com.cricket.model.Inning;
import com.cricket.model.Match;
import com.cricket.model.Statistics;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;

import net.sf.json.JSONArray;
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
	
	List<BattingCard> last_infobar_batsman;
	List<Match> tournament_matches = new ArrayList<Match>();
	List<NameSuper> namesuper = new ArrayList<NameSuper>();
	List<InfobarStats> infobarstats = new ArrayList<InfobarStats>();
	List<Bugs> bugs = new ArrayList<Bugs>();
	List<Statistics> session_statistics = new ArrayList<Statistics>();
	
	BowlingCard last_infobar_bowler;
	int whichInning,player_id,team_id,session_port;
	String session_selected_broadcaster, viz_scene_path, which_graphics_onscreen, info_bar_bottom_left, info_bar_bottom_right, 
	info_bar_bottom,stats_type,top_left_stats,top_right_stats,type_of_profile;
	boolean is_Infobar_on_Screen = false;
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model) throws JAXBException 
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
			@RequestParam(value = "select_sponsors", required = false, defaultValue = "") String select_sponsors,
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess) 
					throws UnknownHostException, IOException, JAXBException, IllegalAccessException, InvocationTargetException 
	{
		info_bar_bottom_left = "";
		info_bar_bottom_right = "";
		info_bar_bottom = "";
		session_port=vizPortNumber;
		which_graphics_onscreen = "";
		stats_type = "";
		top_left_stats = "";
		top_right_stats = "";
		type_of_profile = "";
		whichInning = 0;
		player_id = 0;
		team_id = 0;
		is_Infobar_on_Screen = false;
		this_doad = new Doad();
		last_infobar_batsman = new ArrayList<BattingCard>();
		last_infobar_bowler = new BowlingCard();
		session_selected_broadcaster = select_broadcaster;
		session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
		print_writer = new PrintWriter(session_socket.getOutputStream(), true);
		
		session_Configurations = new Configurations(selectedMatch, select_broadcaster, select_sponsors, vizIPAddresss, vizPortNumber, vizScene);
		
		File files[] = new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY).listFiles(new FileFilter() {
			@Override
		    public boolean accept(File pathname) {
		        String name = pathname.getName().toLowerCase();
		        return name.endsWith(".xml") && pathname.isFile();
		    }
		});
		
		for(File file : files) {
			if(!file.getName().equals(selectedMatch)) {
				tournament_matches.add(CricketFunctions.populateMatchVariables(cricketService, (Match) JAXBContext.newInstance(Match.class).createUnmarshaller().unmarshal(
						new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + file.getName()))));
				
			}
		}
		
		JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.CONFIGURATIONS_DIRECTORY + CricketUtil.OUTPUT_XML));

		session_event_file = (EventFile) JAXBContext.newInstance(EventFile.class).createUnmarshaller().unmarshal(
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.EVENT_DIRECTORY + selectedMatch));
		
		session_match = CricketFunctions.populateMatchVariables(cricketService, (Match) JAXBContext.newInstance(Match.class).createUnmarshaller().unmarshal(
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + selectedMatch)));
		//session_match.setMatchFileName(selectedMatch);
		session_match.setMatchFileTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));	
		
		model.addAttribute("session_match", session_match);
		model.addAttribute("session_port", session_port);
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
		case "BUG_GRAPHICS-OPTIONS": case "HOWOUT_GRAPHICS-OPTIONS": case "BATSMANSTATS_GRAPHICS-OPTIONS": case "BOWLERSTATS_GRAPHICS-OPTIONS": case "NAMESUPER_PLAYER_GRAPHICS-OPTIONS": 
		case "L3PLAYERPROFILE_GRAPHICS-OPTIONS": case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "BOTTOMLEFT_GRAPHICS-OPTIONS": case "BOTTOMRIGHT_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "COMPARISION-GRAPHICS-OPTIONS": case "BOTTOM_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS": case "PROJECTED_GRAPHICS-OPTIONS": case "TARGET_GRAPHICS-OPTIONS": 
		case "PLAYERSUMMARY_GRAPHICS-OPTIONS": case "BUG_DISMISSAL_GRAPHICS-OPTIONS":
			return JSONObject.fromObject(session_match).toString();
		
		case "NAMESUPER_GRAPHICS-OPTIONS": 
			namesuper = cricketService.getNameSupers();
			return JSONArray.fromObject(namesuper).toString();
			
		case "PROMPT_GRAPHICS-OPTIONS":
			infobarstats = cricketService.getInfobarStats();
			return JSONArray.fromObject(infobarstats).toString();
		
		case "BUG_DB_GRAPHICS-OPTIONS":
			bugs = cricketService.getBugs();
			return JSONArray.fromObject(bugs).toString();
			
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
				
				if(is_Infobar_on_Screen == true) {
					String Bow=top_right_stats.toUpperCase();
					last_infobar_batsman = this_doad.populateInfobarTopLeft(true, print_writer, top_left_stats.toUpperCase(), session_match, session_selected_broadcaster, last_infobar_batsman);
					
					last_infobar_bowler = this_doad.populateInfobarTopRight(true, print_writer, Bow, session_match, session_selected_broadcaster,last_infobar_bowler);
					
					this_doad.populateInfobarTopLeft(true, print_writer, top_left_stats.toUpperCase(), session_match, session_selected_broadcaster,last_infobar_batsman);
					this_doad.populateInfobarTopRight(true, print_writer, Bow, session_match, session_selected_broadcaster,last_infobar_bowler);
					
					this_doad.populateInfobarTeamScore(true, print_writer, session_match, session_selected_broadcaster);
					this_doad.populateInfobarBottomLeft(true, print_writer, info_bar_bottom_left, session_match, session_selected_broadcaster);
					this_doad.populateInfobarBottomRight(true, print_writer, info_bar_bottom_right, session_match, session_selected_broadcaster);
					this_doad.populateInfobarBottom(true, print_writer, info_bar_bottom, session_match, session_selected_broadcaster);
					//this_doad.populateInfobarPrompt(true, print_writer, info_bar_bottom_right, session_match, session_selected_broadcaster);
				}
				
				return JSONObject.fromObject(session_match).toString();
			}
			else {
				return JSONObject.fromObject(null).toString();
			}
		
		case "POPULATE-FF-SCORECARD": case "POPULATE-FF-BOWLINGCARD": case "POPULATE-FF-PARTNERSHIP": case "POPULATE-FF-MATCHSUMMARY": case "POPULATE-L3-BUG":  case "POPULATE-L3-HOWOUT":
		case "POPULATE-L3-BATSMANSTATS": case "POPULATE-L3-NAMESUPER": case "POPULATE-L3-NAMESUPER-PLAYER": case "POPULATE-FF-PLAYERPROFILE": case "POPULATE-FF-DOUBLETEAMS": case "POPULATE-L3-INFOBAR": 
		case "POPULATE-FF-LEADERBOARD": case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-FF-MATCHID": case "POPULATE-FF-PLAYINGXI": 
		case "POPULATE-L3-PROJECTED": case "POPULATE-L3-TARGET": case "POPULATE-L3-TEAMSUMMARY": case "POPULATE-L3-PLAYERSUMMARY": case "POPULATE-L3-PLAYERPROFILE": case "POPULATE-L3-FALLOFWICKET": 
		case "POPULATE-L3-COMPARISION": case "POPULATE-INFOBAR-PROMPT": case "POPULATE-LT-MATCHID": case "POPULATE-L3-BOWLERSTATS": case "POPULATE-L3-BUG-DISMISSAL": case "POPULATE-L3-SPLIT":
		case "POPULATE-L3-BUG-DB":
			switch (session_selected_broadcaster.toUpperCase()) {
			
			case "DOAD_IN_HOUSE_VIZ":
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-FF-SCORECARD":
					whichInning = Integer.valueOf(valueToProcess);
					this_doad.populateScorecard(print_writer, viz_scene_path, whichInning, session_match, session_selected_broadcaster);
					break;
				}
				break;
				
			case "DOAD_IN_HOUSE_EVEREST":
				
				switch(whatToProcess.toUpperCase()) {
				case"POPULATE-INFOBAR-BOTTOMLEFT": case"POPULATE-INFOBAR-BOTTOMRIGHT": case"POPULATE-INFOBAR-PROMPT": case "POPULATE-INFOBAR-BOTTOM":
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
					
					this_doad.populateBowlingcard(print_writer, viz_scene_path, false, whichInning, session_match, session_selected_broadcaster);
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
				case "POPULATE-L3-BUG-DISMISSAL":
					this_doad.populateBugDismissal(print_writer, viz_scene_path,Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), session_match, session_selected_broadcaster);
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
				case "POPULATE-L3-BATSMANSTATS":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					stats_type = valueToProcess.split(",")[2];
					player_id = Integer.valueOf(valueToProcess.split(",")[3]);
					
					this_doad.populateBatsmanstats(print_writer, viz_scene_path,whichInning, stats_type, player_id, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BOWLERSTATS":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					stats_type = valueToProcess.split(",")[2];
					player_id = Integer.valueOf(valueToProcess.split(",")[3]);
					
					this_doad.populateBowlerstats(print_writer, viz_scene_path,whichInning, stats_type, player_id, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-BUG-DB":
					for(Bugs bug : bugs) {
						  player_id = Integer.valueOf(valueToProcess.split(",")[1]);
						  
						  if(bug.getBugId() == player_id) {
							  this_doad.populateBugsDB(print_writer, viz_scene_path, bug, session_match, session_selected_broadcaster);
						  }
						}
						break;
				case "POPULATE-L3-NAMESUPER":
					for(NameSuper ns : namesuper) {
					  player_id = Integer.valueOf(valueToProcess.split(",")[1]);
					  
					  if(ns.getNamesuperId() == player_id) {
						  this_doad.populateNameSuper(print_writer, viz_scene_path, ns, session_match, session_selected_broadcaster);
					  }
					}
					break;
				case "POPULATE-L3-NAMESUPER-PLAYER":
					team_id = Integer.valueOf(valueToProcess.split(",")[1]);
					stats_type = valueToProcess.split(",")[2];
					player_id = Integer.valueOf(valueToProcess.split(",")[3]);
					
					this_doad.populateNameSuperPlayer(print_writer, viz_scene_path, team_id, stats_type, player_id, session_match, session_selected_broadcaster);
					break;
					
				case "POPULATE-FF-PLAYERPROFILE":
					session_statistics = cricketService.getAllStats();
					player_id = Integer.valueOf(valueToProcess.split(",")[1]);
					stats_type = valueToProcess.split(",")[2];
					type_of_profile = valueToProcess.split(",")[3];
					
					for(Statistics stats : session_statistics) {
						if(stats.getPlayer_id() == player_id) {
							stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
							stats = updateTournamentDataWithStats(stats, type_of_profile);
							stats = updateStatisticsWithMatchData(stats, session_match, type_of_profile);
							if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(stats_type)) {
								this_doad.populatePlayerProfile(print_writer,viz_scene_path,player_id,
										stats_type,type_of_profile,stats,session_match, session_selected_broadcaster);
							}
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
					
				case "POPULATE-INFOBAR-PROMPT":
					this_doad.processAnimation(print_writer, "Section5_Out", "CONTINUE", session_selected_broadcaster);
					this_doad.processAnimation(print_writer, "Section6_Out", "CONTINUE", session_selected_broadcaster);
					for(Inning inn : session_match.getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							this_doad.populateInfobarBottomLeft(false,print_writer, info_bar_bottom_left, session_match, session_selected_broadcaster);
							
							for(InfobarStats ibs : infobarstats ) {
								player_id = Integer.valueOf(valueToProcess);
								  
								  if(ibs.getOrder() == player_id) {
									  this_doad.populateInfobarPrompt(false,print_writer, ibs, session_match, session_selected_broadcaster);
								  }
							}
						}
					}
					this_doad.processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster);
					break;
					
				case "POPULATE-INFOBAR-BOTTOM":
					this_doad.processAnimation(print_writer, "Section6_Out", "CONTINUE", session_selected_broadcaster);
					for(Inning inn : session_match.getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							this_doad.populateInfobarBottomLeft(false,print_writer, info_bar_bottom_left, session_match, session_selected_broadcaster);
							this_doad.populateInfobarBottom(false, print_writer, valueToProcess, session_match, session_selected_broadcaster);
						}
					}
					this_doad.processAnimation(print_writer, "Section6_In", "START", session_selected_broadcaster);
					info_bar_bottom = valueToProcess;

					break;
				case "POPULATE-FF-MATCHID":
					this_doad.populateMatchId(print_writer,viz_scene_path, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-LT-MATCHID":
					this_doad.populateLTMatchId(print_writer,viz_scene_path, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-PLAYINGXI":
					this_doad.populatePlayingXI(print_writer, viz_scene_path, Integer.valueOf(valueToProcess.split(",")[1]), 
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-FF-LEADERBOARD":
					this_doad.populateLeaderBoard(print_writer, viz_scene_path, valueToProcess.split(",")[1], valueToProcess.split(",")[2],
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-PROJECTED":
					this_doad.populateProjectedScore(print_writer,viz_scene_path, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-TARGET":
					this_doad.populateTarget(print_writer,viz_scene_path, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-TEAMSUMMARY":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					
					this_doad.populateTeamSummary(print_writer, viz_scene_path, whichInning, 
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-PLAYERSUMMARY":
					this_doad.populatePlayerSummary(print_writer, viz_scene_path, Integer.valueOf(valueToProcess.split(",")[1]), Integer.valueOf(valueToProcess.split(",")[2]), 
							session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-PLAYERPROFILE":
					session_statistics = cricketService.getAllStats();
					player_id = Integer.valueOf(valueToProcess.split(",")[1]);
					stats_type = valueToProcess.split(",")[2];
					type_of_profile = valueToProcess.split(",")[3];
					
					for(Statistics stats : session_statistics) {
						if(stats.getPlayer_id() == player_id) {
							stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
							stats = updateTournamentDataWithStats(stats, type_of_profile);
							stats = updateStatisticsWithMatchData(stats, session_match, type_of_profile);
							
							if(stats.getStats_type().getStats_short_name().equalsIgnoreCase(stats_type)) {
								this_doad.populateLTPlayerProfile(print_writer,viz_scene_path,
										stats_type,type_of_profile,stats,session_match, session_selected_broadcaster);
							}
						}
					}
					break;
				case "POPULATE-L3-FALLOFWICKET":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					
					this_doad.populateFallofWicket(print_writer, viz_scene_path, whichInning, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-SPLIT":
					whichInning = Integer.valueOf(valueToProcess.split(",")[1]);
					this_doad.populateSplit(print_writer, viz_scene_path, whichInning, session_match, session_selected_broadcaster);
					break;
				case "POPULATE-L3-COMPARISION":
					this_doad.populateComparision(print_writer,viz_scene_path, session_match, session_selected_broadcaster);
					break;
				}
				return JSONObject.fromObject(this_doad).toString();
			}
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-BATSMANSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-NAMESUPER-PLAYER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-INFOBAR":  
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI": case "ANIMATE-IN-LEADERBOARD": case "ANIMATE-IN-PROJECTED": case "ANIMATE-IN-TARGET": case "ANIMATE-IN-TEAMSUMMARY":
		case "ANIMATE-IN-PLAYERSUMMARY": case "ANIMATE-IN-L3PLAYERPROFILE": case "ANIMATE-IN-FALLOFWICKET": case "ANIMATE-IN-COMPARISION": case "ANIMATE-IN-L3MATCHID": 
		case "ANIMATE-IN-BOWLERSTATS": case "ANIMATE-IN-SPLIT": case "ANIMATE-IN-BUG-DB": case "ANIMATE-OUT":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_VIZ":
			case "ANIMATE-IN-SCORECARD":
				this_doad.AnimateInGraphics(new PrintWriter(session_socket.getOutputStream(), true), "SCORECARD");
				if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
					which_graphics_onscreen = "BATBALLSUMMARY_SCORECARD";
				}
				break;
				
			case "ANIMATE-OUT":
				switch(which_graphics_onscreen) {
				case "BATBALLSUMMARY_SCORECARD":
					this_doad.AnimateOutGraphics(new PrintWriter(session_socket.getOutputStream(), true), "BATBALLSUMMARY_SCORECARD");
					if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "";
					}
					break;
				}
				break;
			
			
			case "DOAD_IN_HOUSE_EVEREST": 
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
				case "ANIMATE-IN-BATSMANSTATS":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "BATSMANSTATS";
					break;
				case "ANIMATE-IN-BOWLERSTATS":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "BOWLERSTATS";
					break;
				case "ANIMATE-IN-BUG-DB":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "BUG-DB";
					break;
				case "ANIMATE-IN-NAMESUPER":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "NAMESUPER";
					break;
				case "ANIMATE-IN-NAMESUPER-PLAYER":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "NAMESUPER-PLAYER";
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
					which_graphics_onscreen = "INFOBAR";
					break;
				case "ANIMATE-IN-MATCHID":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "MATCHID";
					break;
				case "ANIMATE-IN-L3MATCHID":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "L3MATCHID";
					break;
				case "ANIMATE-IN-PLAYINGXI":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "PLAYINGXI";
					break;
				case "ANIMATE-IN-LEADERBOARD":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "LEADERBOARD";
					break;
				case "ANIMATE-IN-PROJECTED":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "PROJECTED";
					break;
				case "ANIMATE-IN-TARGET":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "TARGET";
					break;
				case "ANIMATE-IN-TEAMSUMMARY":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "TEAMSUMMARY";
					break;
				case "ANIMATE-IN-PLAYERSUMMARY":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "PLAYERSUMMARY";
					break;
				case "ANIMATE-IN-L3PLAYERPROFILE":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "L3PLAYERPROFILE";
					break;
				case "ANIMATE-IN-FALLOFWICKET":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "FALLOFWICKET";
					break;
				case "ANIMATE-IN-SPLIT":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "SPLIT";
					break;
				case "ANIMATE-IN-COMPARISION":
					this_doad.processAnimation(print_writer, "In", "START", session_selected_broadcaster);
					which_graphics_onscreen = "COMPARISION";
					break;
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "INFOBAR":
						this_doad.processAnimation(print_writer, "In", "COUNTINUE_REVERSE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						is_Infobar_on_Screen = false;
						break;
					case "SCORECARD":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "BOWLINGCARD":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
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
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "HOWOUT":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "BATSMANSTATS":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "BOWLERSTATS":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "BUG-DB":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "NAMESUPER":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "NAMESUPER-PLAYER":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "PLAYERPROFILE":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "DOUBLETEAMS":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "MATCHID":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "L3MATCHID":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "PLAYINGXI":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "LEADERBOARD":
						this_doad.processAnimation(print_writer, "In", "CONTINUE", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "PROJECTED":
						this_doad.processAnimation(print_writer, "In", "RESET", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "TARGET":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "TEAMSUMMARY":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "PLAYERSUMMARY":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "L3PLAYERPROFILE":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "FALLOFWICKET":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "SPLIT":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
						which_graphics_onscreen = "";
						break;
					case "COMPARISION":
						this_doad.processAnimation(print_writer, "Out", "START", session_selected_broadcaster);
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
	
	public Statistics updateTournamentDataWithStats(Statistics stat,String typeOfProfile ) 
	{
		boolean player_found = false;
		for(Match match : tournament_matches) {
			player_found = false;
			if(stat.getStats_type().getStats_short_name().equalsIgnoreCase(match.getMatchType())) {
				for(Inning inn : match.getInning())
				{
					switch(typeOfProfile.toUpperCase()) {
					case CricketUtil.BATSMAN:
						for(BattingCard bc : inn.getBattingCard()) {
							if(bc.getPlayerId() == stat.getPlayer_id()) {
								player_found = true;
								
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
									player_found = true;
									stat.setWickets(stat.getWickets() + boc.getWickets());
									stat.setBest_figures(stat.getBest_figures());
								}
							}							
						}
						break;
					}
				}
				
				if(player_found == true){
					stat.setMatches(stat.getMatches() + 1);
				}
			}	
		}
		return stat;
	}
	
	public Statistics updateStatisticsWithMatchData(Statistics stat, Match match, String typeOfProfile)
	{
		boolean player_found = false;
		
		player_found = false;
		
		if(stat.getStats_type().getStats_short_name().equalsIgnoreCase(match.getMatchType())) {
			for(Inning inn : match.getInning())
			{
				switch(typeOfProfile.toUpperCase()) {
				case CricketUtil.BATSMAN:
					for(BattingCard bc : inn.getBattingCard()) {
						if(bc.getPlayerId() == stat.getPlayer_id()) {
							player_found = true;
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
								player_found = true;
								stat.setWickets(stat.getWickets() + boc.getWickets());
								stat.setBest_figures(stat.getBest_figures());
							}
						}							
					}
					break;
				}
			}
		}
		if(player_found == true){
			stat.setMatches(stat.getMatches() + 1);
		}
		return stat;
	}
	

}