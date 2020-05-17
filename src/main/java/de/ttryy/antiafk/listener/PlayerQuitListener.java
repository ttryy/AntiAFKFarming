package de.ttryy.antiafk.listener;

import de.ttryy.antiafk.main.AntiAfkPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener{
	
	private AntiAfkPlugin plugin;
	
	public PlayerQuitListener(AntiAfkPlugin plugin) {
		this.plugin = plugin;
		
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event){
		if(plugin.getNeedToRespond().containsKey(event.getPlayer().getUniqueId())){
			plugin.getNeedToRespond().remove(event.getPlayer().getUniqueId());
		}
	}

}
