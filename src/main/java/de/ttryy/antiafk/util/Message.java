package de.ttryy.antiafk.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import de.ttryy.antiafk.main.AntiAfkPlugin;

public enum Message {

	RELOAD,
	NO_PERMISSION,
	UNKNOWN_SUBCOMMAND;

	private String message;

	private Message() {
		message = ChatColor.translateAlternateColorCodes('&',
				AntiAfkPlugin.getInstance().getConfig().getString("messages." + this.toString().toLowerCase()));
	}

	public void sendMessage(CommandSender sender) {
		sender.sendMessage(message);
	}
	
	public void broadcast(){
		Bukkit.broadcastMessage(message);
	}

	public String getMessage() {
		return message;
	}
	
}
