package de.ttryy.antiafk.commands;

import de.ttryy.antiafk.main.AntiAfkPlugin;
import de.ttryy.antiafk.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AntiAfkCommand implements CommandExecutor {

	private AntiAfkPlugin plugin;

	public AntiAfkCommand(AntiAfkPlugin plugin) {
		this.plugin = plugin;

		plugin.getCommand("antiafkfarming").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

		if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("antiafkfarming.reload")) {
				Message.NO_PERMISSION.sendMessage(sender);
				return true;
			}
			plugin.reload();
			Message.RELOAD.sendMessage(sender);
		} else {
			Message.UNKNOWN_SUBCOMMAND.sendMessage(sender);
		}

		return true;
	}

}
