package com.cricket.controller;

import java.io.File;
import java.io.FileFilter;

import java.io.IOException;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cricket.broadcaster.Doad;
import com.cricket.containers.Configurations;
//import com.cricket.containers.Configurations;
import com.cricket.containers.Scene;
import com.cricket.model.Match;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes(value={"session_match","session_socket","session_selected_broadcaster"})
public class IndexController 
{
	@Autowired
	CricketService cricketService;
	public static Configurations session_Configurations;

	String viz_scene_path, which_graphics_onscreen, CONFIGURATIONS_DIRECTORY = "Configurations/", OUTPUT_CONFIG= "OUTPUT.XML" ;
	
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
		
		if(new File(CricketUtil.CRICKET_DIRECTORY + CONFIGURATIONS_DIRECTORY + OUTPUT_CONFIG).exists()) {
			session_Configurations = (Configurations)JAXBContext.newInstance(Configurations.class).createUnmarshaller().unmarshal(
					new File(CricketUtil.CRICKET_DIRECTORY + CONFIGURATIONS_DIRECTORY + OUTPUT_CONFIG));
		}
		else {
			session_Configurations = new Configurations();
			System.out.println(CricketUtil.CRICKET_DIRECTORY + CONFIGURATIONS_DIRECTORY + OUTPUT_CONFIG);
			JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
					new File(CricketUtil.CRICKET_DIRECTORY + CONFIGURATIONS_DIRECTORY + OUTPUT_CONFIG));
		}
		return "initialise";
	}

	@RequestMapping(value = {"/output"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String outputPage(ModelMap model,
			@ModelAttribute("session_socket") Socket session_socket,
			@ModelAttribute("session_match") Match session_match,
			@ModelAttribute("session_selected_broadcaster") String session_selected_broadcaster,
			@RequestParam(value = "select_broadcaster", required = false, defaultValue = "") String select_broadcaster,
			@RequestParam(value = "select_cricket_matches", required = false, defaultValue = "") String selectedMatch,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") int vizPortNumber,
			@RequestParam(value = "vizScene", required = false, defaultValue = "") String vizScene,
			@RequestParam(value = "select_sponsors", required = false, defaultValue = "") String select_sponsors) 
					throws UnknownHostException, IOException, JAXBException, IllegalAccessException, InvocationTargetException 
	{
		session_selected_broadcaster = select_broadcaster;
		viz_scene_path = vizScene;
		
		session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
		new Scene(vizScene).scene_load(new PrintWriter(session_socket.getOutputStream(),true));
		
		which_graphics_onscreen = "";

		session_Configurations = new Configurations(selectedMatch, select_broadcaster, select_sponsors, vizIPAddresss, vizPortNumber, vizScene);
		
		JAXBContext.newInstance(Configurations.class).createMarshaller().marshal(session_Configurations, 
				new File(CricketUtil.CRICKET_DIRECTORY + CONFIGURATIONS_DIRECTORY + OUTPUT_CONFIG));

		session_match = CricketFunctions.populateMatchVariables(cricketService, (Match) JAXBContext.newInstance(Match.class).createUnmarshaller().unmarshal(
				new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + selectedMatch)));
		session_match.setMatchFileName(selectedMatch);
		session_match.setMatchFileTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));

		model.addAttribute("session_match", session_match);
		model.addAttribute("session_socket", session_socket);
		model.addAttribute("session_selected_broadcaster", session_selected_broadcaster);
		
		return "output";
	}

	@RequestMapping(value = {"/processCricketProcedures"}, method={RequestMethod.GET,RequestMethod.POST})    
	public @ResponseBody String processCricketProcedures(
			@ModelAttribute("session_match") Match session_match,
			@ModelAttribute("session_socket") Socket session_socket,
			@ModelAttribute("session_selected_broadcaster") String session_selected_broadcaster,
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess) 
					throws IOException, IllegalAccessException, InvocationTargetException, JAXBException
	{	
		switch (whatToProcess.toUpperCase()) {
		case "GRAPHICS-OPTIONS":
			return JSONObject.fromObject(session_match).toString();
		case "GRAPHIC-OPTIONS":
			return JSONObject.fromObject(session_match).toString();
		case "GRAPHIC1-OPTIONS":
			return JSONObject.fromObject(session_match).toString();
		case "READ-MATCH-AND-POPULATE":
			if(!valueToProcess.equalsIgnoreCase(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
					new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()).lastModified())))
			{
				session_match = CricketFunctions.populateMatchVariables(cricketService, (Match) JAXBContext.newInstance(Match.class).createUnmarshaller().unmarshal(
						new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + session_match.getMatchFileName())));
				session_match.setMatchFileTimeStamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(
						new File(CricketUtil.CRICKET_DIRECTORY + CricketUtil.MATCHES_DIRECTORY + session_match.getMatchFileName()).lastModified()));
				return JSONObject.fromObject(session_match).toString();
			} else {
				return JSONObject.fromObject(null).toString();
			}
		case "POPULATE-SCORECARD": case "POPULATE-BOWLINGCARD": case "POPULATE-PARTNERSHIP": case "POPULATE-MATCHSUMMARY": case "POPULATE-BUG": case "POPULATE-HOWOUT": case "POPULATE-DOUBLETEAMS":
			switch (session_selected_broadcaster.toUpperCase()) {
			case CricketUtil.DOAD:
				Doad this_doad = new Doad();
				switch (whatToProcess.toUpperCase()) {
				case "POPULATE-SCORECARD": 
					this_doad.populateScorecard(new PrintWriter(session_socket.getOutputStream(), true), 
						Integer.valueOf(valueToProcess), session_match, viz_scene_path);
					break;
				case "POPULATE-BOWLINGCARD":
					this_doad.populateBowlingcard(new PrintWriter(session_socket.getOutputStream(), true), 
						Integer.valueOf(valueToProcess), session_match, viz_scene_path);
					break;
				case "POPULATE-PARTNERSHIP":
					this_doad.populatePartnership(new PrintWriter(session_socket.getOutputStream(), true), 
							Integer.valueOf(valueToProcess), session_match, viz_scene_path);
					break;
				case "POPULATE-MATCHSUMMARY":
					this_doad.populateMatchsummary(new PrintWriter(session_socket.getOutputStream(), true), 
							Integer.valueOf(valueToProcess), session_match, viz_scene_path);
					break;
				case "POPULATE-BUG":
					this_doad.populateBug(new PrintWriter(session_socket.getOutputStream(), true), 
							Integer.valueOf(valueToProcess.split(",")[0]), valueToProcess.split(",")[1],Integer.valueOf(valueToProcess.split(",")[2]), session_match, viz_scene_path);
					break;
				case "POPULATE-HOWOUT":
					this_doad.populateHowout(new PrintWriter(session_socket.getOutputStream(), true), 
							Integer.valueOf(valueToProcess.split(",")[0]), valueToProcess.split(",")[1], Integer.valueOf(valueToProcess.split(",")[2]), session_match, viz_scene_path);
					break;
				case "POPULATE-DOUBLETEAMS":
					this_doad.populateDoubleteams(new PrintWriter(session_socket.getOutputStream(), true), 
							valueToProcess.split(",")[0], valueToProcess.split(",")[1], session_match, viz_scene_path);
					break;
				}
				
				return JSONObject.fromObject(this_doad).toString();
			}
		case "ANIMATE-IN-SCORECARD": case "ANIMATE-IN-BOWLINGCARD": case "ANIMATE-IN-PARTNERSHIP": case "ANIMATE-IN-MATCHSUMMARY": case "ANIMATE-IN-BUG": case "ANIMATE-IN-HOWOUT": case "ANIMATE-IN-DOUBLETEAMS": case "ANIMATE-OUT":
			switch (session_selected_broadcaster.toUpperCase()) {
			case CricketUtil.DOAD:
				Doad this_doad = new Doad();
				switch (whatToProcess.toUpperCase()) {
				case "ANIMATE-IN-SCORECARD":
					this_doad.AnimateInGraphics(new PrintWriter(session_socket.getOutputStream(), true), "SCORECARD");
					if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "BATBALLSUMMARY_SCORECARD";
					}
					break;
				case "ANIMATE-IN-BOWLINGCARD":
					this_doad.AnimateInGraphics(new PrintWriter(session_socket.getOutputStream(), true), "BOWLINGCARD");
					if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "BATBALLSUMMARY_BOWLINGCARD";
					}
					break;
				case "ANIMATE-IN-PARTNERSHIP":
					this_doad.AnimateInGraphics(new PrintWriter(session_socket.getOutputStream(), true), "PARTNERSHIP");
					if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "BATBALLSUMMARY_PARTNERSHIP";
					}
					break;
				case "ANIMATE-IN-MATCHSUMARRY":
					this_doad.AnimateInGraphics(new PrintWriter(session_socket.getOutputStream(), true), "MATCHSUMMARY");
					if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "BATBALLSUMMARY_MATCHSUMMARY";
					}
					break;
				case "ANIMATE-IN-BUG":
					this_doad.AnimateInGraphics(new PrintWriter(session_socket.getOutputStream(), true), "BUG");
					if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "BUG";
					}
					break;
				case "ANIMATE-IN-HOWOUT":
					this_doad.AnimateInGraphics(new PrintWriter(session_socket.getOutputStream(), true), "HOWOUT");
					if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "HOWOUT";
					}
					break;
				case "ANIMATE-IN-DOUBLETEAMS":
					this_doad.AnimateInGraphics(new PrintWriter(session_socket.getOutputStream(), true), "DOUBLETEAMS");
					if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
						which_graphics_onscreen = "DOUBLETEAMS";
					}
					break;
				case "ANIMATE-OUT":
					switch(which_graphics_onscreen) {
					case "BUG":
						this_doad.AnimateOutGraphics(new PrintWriter(session_socket.getOutputStream(), true), "BUG");
						if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
							which_graphics_onscreen = "";
						}
						break;
					case "BATBALLSUMMARY_SCORECARD":
						this_doad.AnimateOutGraphics(new PrintWriter(session_socket.getOutputStream(), true), "BATBALLSUMMARY_SCORECARD");
						if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
							which_graphics_onscreen = "";
						}
						break;
					case "BATBALLSUMMARY_BOWLINGCARD":
						this_doad.AnimateOutGraphics(new PrintWriter(session_socket.getOutputStream(), true), "BATBALLSUMMARY_BOWLINGCARD");
						if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
							which_graphics_onscreen = "";
						}
						break;
					case "BATBALLSUMMARY_PARTNERSHIP":
						this_doad.AnimateOutGraphics(new PrintWriter(session_socket.getOutputStream(), true), "BATBALLSUMMARY_PARTNERSHIP");
						if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
							which_graphics_onscreen = "";
						}
						break;
					case "BATBALLSUMMARY_MATCHSUMMARY":
						this_doad.AnimateOutGraphics(new PrintWriter(session_socket.getOutputStream(), true), "BATBALLSUMMARY_MATCHSUMMARY");
						if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
							which_graphics_onscreen = "";
						}
						break;
					case "HOWOUT":
						this_doad.AnimateOutGraphics(new PrintWriter(session_socket.getOutputStream(), true), "HOWOUT");
						if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
							which_graphics_onscreen = "";
						}
						break;
					case "DOUBLETEAMS":
						this_doad.AnimateOutGraphics(new PrintWriter(session_socket.getOutputStream(), true), "DOUBLETEAMS");
						if(this_doad.getStatus().equalsIgnoreCase(CricketUtil.SUCCESSFUL)) {
							which_graphics_onscreen = "";
						}
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

	@ModelAttribute("session_socket")
	public Socket session_socket(){
		return new Socket();
	}
	@ModelAttribute("session_selected_broadcaster")
	public String session_selected_broadcaster(){
		return new String();
	}
	@ModelAttribute("session_match")
	public Match session_match(){
		return new Match();
	}
}