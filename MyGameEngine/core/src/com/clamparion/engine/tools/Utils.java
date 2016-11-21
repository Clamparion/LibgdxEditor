package com.clamparion.engine.tools;

public class Utils {
	public static String removeLastChar(String str) {
		if(str.length() > 0){
			return str.substring(0,str.length()-1);
		} 
		return str;
	
	}
}
