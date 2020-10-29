package com.seniorproject.game.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.seniorproject.game.SeniorProject;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		 
		config.title = "Senior Project";
		config.useGL30 = false;	// This lets us use OpenGL 2.0 
		config.width = 800;
		config.height = 600;
		config.x = 20;
		config.y = 20;
		
		Application app = new LwjglApplication(new SeniorProject(), config);
		
		Gdx.app = app;
		
		//Gdx.app.setLogLevel(Application.LOG_INFO);
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Gdx.app.setLogLevel(Application.LOG_ERROR);
		//Gdx.app.setLogLevel(Application.LOG_NONE);
	}
}
