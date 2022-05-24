package com.cricket.containers;

import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Scene {
	
	private String scene_path;
	private String broadcaster;
	
	public Scene() {
		super();
	}

	public Scene(String scene_path) {
		super();
		this.scene_path = scene_path;
	}
	
	public String getScene_path() {
		return scene_path;
	}

	public void setScene_path(String scene_path) {
		this.scene_path = scene_path;
	}
	
	public String getBroadcaster() {
		return broadcaster;
	}

	public void setBroadcaster(String broadcaster) {
		this.broadcaster = broadcaster;
	}

	public void scene_load(PrintWriter print_writer, String broadcaster,String scene_path) throws InterruptedException
	{
		switch (broadcaster.toUpperCase()) {
		case "DOAD_IN_HOUSE_EVEREST":
			
			print_writer.println("LAYER1*EVEREST*SINGLE_SCENE LOAD " + scene_path + ";");
			print_writer.println("LAYER1*EVEREST*STAGE*DIRECTOR*In RESET;");
			//TimeUnit.SECONDS.sleep(3);
			//print_writer.println("LAYER1*EVEREST*SINGLE_SCENE CLEAR;");
			break;
		case "DOAD_IN_HOUSE_VIZ":
			print_writer.println("-1 RENDERER SET_OBJECT SCENE*" + scene_path + "\0");
			print_writer.println("-1 RENDERER INITIALIZE \0");
			print_writer.println("-1 RENDERER*SCENE_DATA INITIALIZE \0");
			print_writer.println("-1 RENDERER*UPDATE SET 1");
			break;
		}
	}
}
