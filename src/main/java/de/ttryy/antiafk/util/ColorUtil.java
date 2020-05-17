package de.ttryy.antiafk.util;

import org.bukkit.ChatColor;

public class ColorUtil {
	
	public static String alternateChatColor(String string){
		try {
			string = ChatColor.translateAlternateColorCodes('&', string);
		} catch (Exception e) {
			System.err.println("[EasyAntiAFK] Could not translate alternate color codes for: " + string);
		}
		return string;
	}

}
