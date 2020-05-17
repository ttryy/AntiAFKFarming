package de.ttryy.antiafk.listener.mining;

import de.ttryy.antiafk.manager.MiningManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;

import de.ttryy.antiafk.listener.AbstractListener;

public class BlockBreakListener extends AbstractListener{
	
	private MiningManager manager;
	
	public BlockBreakListener(MiningManager manager) {
		this.manager = manager;
		
		manager.getPlugin().getServer().getPluginManager().registerEvents(this, manager.getPlugin());
	}
	

	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event){
		Player player = (Player) event.getPlayer();
		
		if(player.hasPermission("antiafkfarming.bypass.mining")){
			return;
		}
		
		Material material = event.getBlock().getType();
		
		if(!manager.getBlockList().contains(material)){
			return;
		}
		
		manager.addPoint(player);
		
	}

	@Override
	public void unregister(){
		BlockBreakEvent.getHandlerList().unregister(this);
	}

}
