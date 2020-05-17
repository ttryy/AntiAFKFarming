package de.ttryy.antiafk.listener.fishing;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;

import de.ttryy.antiafk.listener.AbstractListener;
import de.ttryy.antiafk.manager.FishingManager;

public class PlayerFishListener extends AbstractListener{
	
	private FishingManager manager;
	
	public PlayerFishListener(FishingManager manager) {
		this.manager = manager;
		
		manager.getPlugin().getServer().getPluginManager().registerEvents(this, manager.getPlugin());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerFish(PlayerFishEvent event){
		
		Player player = (Player) event.getPlayer();
		
		if(player.hasPermission("antiafkfarming.bypass.fishing")){
			return;
		}
		
		if(event.getCaught() == null){
			return;
		}
		
		manager.addPoint(player);
	}

	@Override
	public void unregister() {
		PlayerFishEvent.getHandlerList().unregister(this);
	}
	
}
