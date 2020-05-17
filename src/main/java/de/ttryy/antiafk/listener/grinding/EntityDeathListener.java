package de.ttryy.antiafk.listener.grinding;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;

import de.ttryy.antiafk.listener.AbstractListener;
import de.ttryy.antiafk.manager.GrindingManager;

public class EntityDeathListener extends AbstractListener{
	
	private GrindingManager manager;
	
	public EntityDeathListener(GrindingManager manager) {
		this.manager = manager;
		
		manager.getPlugin().getServer().getPluginManager().registerEvents(this, manager.getPlugin());
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent event){
		if(!(event.getEntity().getKiller() instanceof Player)){
			return;
		}
		
		Player player = (Player) event.getEntity().getKiller();
		
		if(event.getEntity() instanceof Player){
			return;
		}
		
		if(player.hasPermission("antiafkfarming.bypass.grinding")){
			return;
		}
		
		manager.addPoint(player);
		
	}

	@Override
	public void unregister() {
		EntityDeathEvent.getHandlerList().unregister(this);
	}

}
