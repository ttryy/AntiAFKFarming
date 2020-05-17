package de.ttryy.antiafk.main;

import java.util.Map;
import java.util.UUID;

import de.ttryy.antiafk.manager.MiningManager;
import de.ttryy.antiafk.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Maps;
import de.ttryy.antiafk.commands.AntiAfkCommand;
import de.ttryy.antiafk.listener.PlayerQuitListener;
import de.ttryy.antiafk.manager.FishingManager;
import de.ttryy.antiafk.manager.GrindingManager;

import lombok.Getter;

public class AntiAfkPlugin extends JavaPlugin{
	
	private static @Getter AntiAfkPlugin instance;
	
	private @Getter
	MenuManager menuManager;
	
	private @Getter Map<UUID, Boolean> needToRespond; 
	
	private @Getter FishingManager fishingManager;
	private @Getter GrindingManager grindingManager;
	private @Getter
	MiningManager miningManger;
	
	@Override
	public void onEnable() {
		instance = this;
		
		saveDefaultConfig();
		
		menuManager = new MenuManager(this);
		
		needToRespond = Maps.newHashMap();
		
		//Register Commands
		new AntiAfkCommand(this);
		
		//Register Listener
    	new PlayerQuitListener(this);
		
		reload();
	}
	
	@Override
	public void onDisable() {
		needToRespond.keySet().forEach(uuid -> Bukkit.getPlayer(uuid).closeInventory());
	}

	/**
	 * Reload/load the config and AFKManager
	 */
	public void reload(){
		
		needToRespond.keySet().forEach(uuid -> Bukkit.getPlayer(uuid).closeInventory());
		needToRespond = Maps.newHashMap();
		
		reloadConfig();
		
		if(fishingManager != null){
			fishingManager.unregister();
		}
		
		if(grindingManager != null){
			grindingManager.unregister();
		}
		
		if(miningManger != null){
			miningManger.unregister();
		}
		
		if(!getConfig().getBoolean("enabled")){
			System.out.println("[EasyAntiAfk] Disabled by config!");
			return;
		}

		if(FishingManager.isEnabled()){
			fishingManager = new FishingManager(this);
		}

		if(GrindingManager.isEnabled()){
			grindingManager = new GrindingManager(this);
		}

		if(MiningManager.isEnabled()){
			miningManger = new MiningManager(this);
		}
	}
	
}
