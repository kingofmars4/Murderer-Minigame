package me.kingofmars4.murderer.utils;

import java.util.Random;

public class U {
	
	public static String color(String s) {
		
		return s.replaceAll("&", "§");
	}
	
	public static int rand(int min, int max){
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
	
	public static boolean isInt(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}
