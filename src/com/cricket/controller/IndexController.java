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
import com.cricket.model.EventFile;
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
	String session_selected_broadcaster, viz_scene_path, which_graphics_onscreen, info_bar_bottom_left, info_bar_bottom_right, info_bar_bottom;
	boolean is_Infobar_on_Screen = false;
	List<Statistics> stats_to_send = new ArrayList<Statistics>();
	
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
		is_Infobar_on_Screen = false;
		this_doad = new Doad();
		
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
		case "PLAYERPROFILE_GRAPHICS-OPTIONS": case "ANIMATE-OPTIONS": case "ANIMATE_GRAPHICS-OPTIONS": case "INFOBAR_GRAPHICS-OPTIONS": 
		case "ANIMATE_BOTTOM_GRAPHICS-OPTIONS": case "ANIMATE_PLAYINGXI-OPTIONS":
			return JSONObject.fromObject(session_match).toString();
		case "GET_PROFILE-OPTION":
			for(Statistics stats : cricketService.getPlayerStatistics(Integer.valueOf(valueToProcess))) {
				stats.setStats_type(cricketService.getStatsType(stats.getStats_type_id()));
				stats_to_send.add(stats);
			}
			return JSONArray.fromObject(stats_to_send).toString();
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
					this_doad.populateInfobarTeamScore(true, print_writer, session_match, session_selected_broadcaster);
					this_doad.populateInfobarTeamScore(true, print_writer, session_match, session_selected_broadcaster);
					this_doad.populateInfobarTopLeft(true, print_writer, "BATSMAN", session_match, session_selected_broadcaster);
					this_doad.populateInfobarTopRight(true, print_writer, "BOWLER", session_match, session_selected_broadcaster);
					this_doad.populateInfobarBottomLeft(true, print_writer, info_bar_bottom_left, session_match, session_selected_broadcaster);
					this_doad.populateInfobarBottomRight(true, print_writer, info_bar_bottom_right, session_match, session_selected_broadcaster);
					this_doad.populateInfobarBottom(true, print_writer, info_bar_bottom, session_match, session_selected_broadcaster);
				}
				return JSONObject.fromObject(session_match).toString();
			}
			else {
				return JSONObject.fromObject(null).toString();
			}
		
		case "POPULATE-SCORECARD": case "POPULATE-BOWLINGCARD": case "POPULATE-PARTNERSHIP": case "POPULATE-MATCHSUMMARY": case "POPULATE-BUG":  case "POPULATE-HOWOUT":
		case "POPULATE-PLAYERSTATS": case "POPULATE-NAMESUPER": case "POPULATE-PLAYERPROFILE": case "POPULATE-DOUBLETEAMS": case "POPULATE-INFOBAR": 
		case "POPULATE-INFOBAR-BOTTOMLEFT": case "POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM": case "POPULATE-MATCHID": case "POPULATE-PLAYINGXI":
			switch (session_selected_broadcaster.toUpperCase()) {
			case "DOAD_IN_HOUSE_EVEREST": case "DOAD_IN_HOUSE_VIZ":
	
				viz_scene_path = valueToProcess.split(",")[0];
				switch(whatToProcess.toUpperCase()) {
				case"POPULATE-INFOBAR-BOTTOMLEFT": case"POPULATE-INFOBAR-BOTTOMRIGHT": case "POPULATE-INFOBAR-BOTTOM":
					break;
				default:
					new Scene(viz_scene_path).scene_load(new PrintWriter(session_socket.getOutputStream(),true),session_selected_broadcaster,viz_scene_path);
					break;
				}
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-SCORECARD":
					this_doad.populateScorecard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-BOWLINGCARD":
					this_doad.populateBowlingcard(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							session_match, session_selected_broadcaster, viz_scene_path);
					break;
				case "POPULATE-PARTNERSHIP":
					this_doad.populatePartnership(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-MATCHSUMMARY":
					this_doad.populateMatchsummary(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-BUG":
					this_doad.populateBug(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), 
							valueToProcess.split(",")[2],Integer.valueOf(valueToProcess.split(",")[3]), session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-HOWOUT":
					this_doad.populateHowout(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
							Integer.valueOf(valueToProcess.split(",")[3]), session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-PLAYERSTATS":
					this_doad.populatePlayerstats(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]), valueToProcess.split(",")[2], 
							Integer.valueOf(valueToProcess.split(",")[3]), session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-NAMESUPER":
					this_doad.populateNameSuper(print_writer, valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),valueToProcess.split(",")[2],
							Integer.valueOf(valueToProcess.split(",")[3]), session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-PLAYERPROFILE":
					this_doad.populatePlayerProfile(print_writer,valueToProcess.split(",")[0],Integer.valueOf(valueToProcess.split(",")[1]),Integer.valueOf(valueToProcess.split(",")[2]), 
							session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-DOUBLETEAMS":
					this_doad.populateDoubleteams(print_writer,valueToProcess.split(",")[0], session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-INFOBAR":
					info_bar_bottom_left = valueToProcess.split(",")[3];
					info_bar_bottom_right = valueToProcess.split(",")[4];
					this_doad.populateInfobar(print_writer, valueToProcess.split(",")[0],valueToProcess.split(",")[1], valueToProcess.split(",")[2],valueToProcess.split(",")[3],
							valueToProcess.split(",")[4], session_match, session_selected_broadcaster);
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
					//this_doad.processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster);
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
					//this_doad.processAnimation(print_writer, "Section4_In", "START", session_selected_broadcaster);
					this_doad.processAnimation(print_writer, "Section5_In", "START", session_selected_broadcaster);
					info_bar_bottom_right = valueToProcess;
					break;
				case "POPULATE-INFOBAR-BOTTOM":
					//this_doad.processAnimation(print_writer, "Section4_Out", "CONTINUE", session_selected_broadcaster);
					//this_doad.processAnimation(print_writer, "Section5_Out", "CONTINUE", session_selected_broadcaster);
					this_doad.processAnimation(print_writer, "Section6_Out", "CONTINUE", session_selected_broadcaster);
					for(Inning inn : session_match.getInning()) {
						if(inn.getIsCurrentInning().equalsIgnoreCase(CricketUtil.YES)) {
							this_doad.populateInfobarBottom(false, print_writer, valueToProcess, session_match, session_selected_broadcaster);
						}
					}
					this_doad.processAnimation(print_writer, "Section6_In", "START", session_selected_broadcaster);
					info_bar_bottom = valueToProcess;

					break;
				case "POPULATE-MATCHID":
					this_doad.populateMatchId(print_writer,valueToProcess, session_match, session_selected_broadcaster , viz_scene_path);
					break;
				case "POPULATE-PLAYINGXI":
					this_doad.populatePlayingXI(print_writer, valueToProcess.split(",")[0], Integer.valueOf(valueToProcess.split(",")[1]), 
							session_match, session_selected_broadcaster, viz_scene_path);
					break;
				}
				
				return JSONObject.fromObject(this_doad).toString();
			}
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMARRY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": 
		case "ANIMATE-IN-PLAYERSTATS":	case "ANIMATE-IN-NAMESUPER": case "ANIMATE-IN-PLAYERPROFILE": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-IN-INFOBAR": case "ANIMATE-OUT": 
		case "ANIMATE-IN-MATCHID": case "ANIMATE-IN-PLAYINGXI":
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
}