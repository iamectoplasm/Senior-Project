package com.seniorproject.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.seniorproject.screens.IntroScreen;
import com.seniorproject.screens.PerformanceScreen;
import com.seniorproject.screens.SceneSelectScreen;

public class SeniorProject extends Game
{
	public static PerformanceScreen performanceScreen;
	public static IntroScreen introScreen;
	public static SceneSelectScreen sceneSelectScreen;
	
	public static enum ScreenType
	{
		IntroScreen,
		SceneSelectScreen,
		Performance,
		Backstage,
		Ending,
		Credits
	}
	
	public Screen getScreenType(ScreenType screenType)
	{
		switch(screenType)
		{
			case IntroScreen:
				return introScreen;
			case Performance:
				return performanceScreen;
			case SceneSelectScreen:
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
		introScreen = new IntroScreen(this);
		sceneSelectScreen = new SceneSelectScreen(this);
		
		//setScreen(performanceScreen);
		setScreen(introScreen);
	}
	
}
