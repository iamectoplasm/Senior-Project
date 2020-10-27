package com.seniorproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.seniorproject.screens.MainMenuScreen;
import com.seniorproject.screens.PerformanceScreen;
import com.seniorproject.screens.SceneIntroScreen;
import com.seniorproject.screens.SceneSelectScreen;

public class SeniorProject extends Game
{
	public static PerformanceScreen performanceScreen;
	public static MainMenuScreen mainMenuScreen;
	public static SceneSelectScreen sceneSelectScreen;
	public static SceneIntroScreen sceneIntroScreen;
	
	public static enum ScreenType
	{
		MAIN_MENU_SCREEN,
		SCENE_SELECT_SCREEN,
		SCENE_INTRO_SCREEN,
		PERFORMANCE_SCREEN//,
		//Backstage,
		//Ending,
		//Credits
	}
	
	public Screen getScreenType(ScreenType screenType)
	{
		switch(screenType)
		{
			case MAIN_MENU_SCREEN:
				return mainMenuScreen;
			case SCENE_INTRO_SCREEN:
				return sceneIntroScreen;
			case PERFORMANCE_SCREEN:
				return performanceScreen;
			case SCENE_SELECT_SCREEN:
				return sceneSelectScreen;
			//case LoadGame:
			//	return loadGameScreen;
			//case NewGame:
			//	return newGameScreen;
			//case Credits:
			//	return creditScreen;
			default:
				return performanceScreen;
		}
	}

	@Override
	public void create()
	{
		performanceScreen = new PerformanceScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		sceneSelectScreen = new SceneSelectScreen(this);
		sceneIntroScreen = new SceneIntroScreen(this);
		
		//setScreen(performanceScreen);
		setScreen(mainMenuScreen);
		//setScreen(sceneIntroScreen);
	}
	
}
