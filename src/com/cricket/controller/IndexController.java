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
import com.cricket.containers.Scene;
import com.cricket.model.Match;
import com.cricket.service.CricketService;
import com.cricket.util.CricketFunctions;
import com.cricket.util.CricketUtil;

import net.sf.json.JSONObject;

@Controller
@SessionAttributes(value={"session_match","session_socket","session_selected_broadcaster", "session_which_graphics_onscreen"})
public class IndexController 
{
	@Autowired
	CricketService cricketService;

	String viz_scene_path;
	
	@RequestMapping(value = {"/","/initialise"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String initialisePage(ModelMap model)  
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
		
		return "initialise";
	}

	@RequestMapping(value = {"/output"}, method={RequestMethod.GET,RequestMethod.POST}) 
	public String outputPage(ModelMap model,
			@ModelAttribute("session_socket") Socket session_socket,
			@ModelAttribute("session_which_graphics_onscreen") String session_which_graphics_onscreen,
			@ModelAttribute("session_match") Match session_match,
			@ModelAttribute("session_selected_broadcaster") String session_selected_broadcaster,
			@RequestParam(value = "select_broadcaster", required = false, defaultValue = "") String select_broadcaster,
			@RequestParam(value = "select_cricket_matches", required = false, defaultValue = "") String selectedMatch,
			@RequestParam(value = "vizIPAddress", required = false, defaultValue = "") String vizIPAddresss,
			@RequestParam(value = "vizPortNumber", required = false, defaultValue = "") String vizPortNumber,
			@RequestParam(value = "vizScene", required = false, defaultValue = "") String vizScene) 
					throws UnknownHostException, IOException, JAXBException, IllegalAccessException, InvocationTargetException 
	{
		session_selected_broadcaster = select_broadcaster;
		viz_scene_path = vizScene; 
		
		session_socket = new Socket(vizIPAddresss, Integer.valueOf(vizPortNumber));
		new Scene(vizScene).scene_load(new PrintWriter(session_socket.getOutputStream(),true));
		
		session_which_graphics_onscreen = "";

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
			@ModelAttribute("session_which_graphics_onscreen") String session_which_graphics_onscreen,
			@RequestParam(value = "whatToProcess", required = false, defaultValue = "") String whatToProcess,
			@RequestParam(value = "valueToProcess", required = false, defaultValue = "") String valueToProcess) 
					throws IOException, IllegalAccessException, InvocationTargetException, JAXBException
	{	
		switch (whatToProcess.toUpperCase()) {
		case "GRAPHICS-OPTIONS":
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
		case "POPULATE-SCORECARD": case "POPULATE-BOWLINGCARD": case "POPULATE-PARTNERSHIP": case "POPULATE-MATCHSUMMARY": case "POPULATE-BUG":
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
	@ModelAttribute("session_which_graphics_onscreen")
	public String session_which_graphics_onscreen(){
		return new String();
	}
}