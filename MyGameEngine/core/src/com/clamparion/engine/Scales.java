package com.clamparion.engine;

import com.badlogic.gdx.math.Vector2;

public class Scales {
	
	private static Vector2 landscape = new Vector2(1920, 1080);
	private static Vector2 portrait = new Vector2(1080, 1920);
	
	public static boolean isPortrait= false;
	public static Vector2 screenSize;
	
	public static void init(boolean isPortrait){
		Scales.isPortrait = isPortrait;
		if(isPortrait)
			Scales.screenSize = Scales.portrait;
		else
			Scales.screenSize = Scales.landscape;
	}
	
	public static int PPM = 100;

}
